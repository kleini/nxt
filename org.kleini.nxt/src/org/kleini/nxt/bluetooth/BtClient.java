/*
 * GPL v3
 */

package org.kleini.solver.slaveA;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;

import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;

import org.kleini.nxt.Log;
import org.kleini.nxt.LogFactory;

public class BtClient implements Runnable, DiscoveryListener {

    private static final Log LOG = LogFactory.getLog();

    private Thread thread;
    private boolean running = true;
    private volatile boolean connected = false;
    private volatile boolean connecting = false;
    private BtReceiver receiver = null;

    BtClient() {
        super();
        thread = new Thread(this);
        thread.start();
    }

    void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            LOG.info(e.getMessage());
        }
        if (null != receiver) {
            receiver.stop();
            receiver = null;
        }
    }

    private synchronized boolean isConnected() {
        return connected;
    }

    private synchronized void setConnected(boolean connected) {
        this.connected = connected;
    }

    private synchronized void setConnecting(boolean connecting) {
        this.connecting = connecting;
    }

    private synchronized boolean isConnecting() {
        return connecting;
    }

    void connectionLost() {
        receiver.stop();
        receiver = null;
        setConnected(false);
    }

    @Override
    public void run() {
        while (running) {
            if (!isConnected() && !isConnecting()) {
                try {
                    LocalDevice local = LocalDevice.getLocalDevice();
                    LOG.info(local.getFriendlyName() + " inquiring");
                    DiscoveryAgent agent = local.getDiscoveryAgent();
                    agent.startInquiry(DiscoveryAgent.GIAC, this);
                } catch (BluetoothStateException e) {
                    LOG.info(e.getMessage());
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                LOG.info(e.getMessage());
            }
        }
    }

    @Override
    public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
        setConnecting(true);
        String name = btDevice.getFriendlyName(true);
        LOG.info("CT " + name);
        BTConnection connection = Bluetooth.connect(btDevice);
        if (null == connection) {
            LOG.info("Not connected.");
        } else {
            setConnected(true);
            LOG.info("Connected to " + connection.getAddress());
            receiver = new BtReceiver(connection, this);
        }
        setConnecting(false);
    }

    @Override
    public void inquiryCompleted(int discType) {
        LOG.info("Inquiry complete " + discType);
    }
}
