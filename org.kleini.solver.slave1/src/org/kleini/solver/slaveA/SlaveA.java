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

import lejos.nxt.Button;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;

import org.kleini.nxt.Log;
import org.kleini.nxt.LogFactory;

/**
 * @author <a href="mailto:himself@kleini.org">Marcus Klein</a>
 */
public class SlaveA implements DiscoveryListener {

    private static final Log LOG = LogFactory.getLog();

    public SlaveA() {
        super();
    }

    public static void main(String[] args) {
        new SlaveA().run();
//        BTReceiver receiver = new BTReceiver();
//        if (!receiver.connect()) {
//            log.info("Failed to connect to controller.");
//            Button.waitForAnyPress();
//            System.exit(1);
//        }
//        Thread receiverThread = new Thread(receiver);
//        receiverThread.start();
//        Button.waitForAnyPress();
//        receiver.stop();
//        try {
//            receiverThread.join();
//        } catch (InterruptedException e) {
//            log.info(e.getMessage());
//        }
        System.exit(0);
    }

    private void run() {
        try {
            LocalDevice local = LocalDevice.getLocalDevice();
            LOG.info(local.getFriendlyName());
            DiscoveryAgent agent = local.getDiscoveryAgent();
            agent.startInquiry(DiscoveryAgent.GIAC, this);
        } catch (BluetoothStateException e) {
            LOG.info(e.getMessage());
        }
        Button.waitForAnyPress();
    }

    @Override
    public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
        LOG.info("Connecting to " + btDevice.getFriendlyName(true));
        BTConnection connection = Bluetooth.connect(btDevice);
        LOG.info("Connected to " + connection.getAddress());
    }

    @Override
    public void inquiryCompleted(int discType) {
        LOG.info("Inquiry complete " + discType);
    }
}
