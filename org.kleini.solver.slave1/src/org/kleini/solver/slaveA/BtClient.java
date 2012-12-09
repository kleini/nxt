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
    private boolean connected = false;

    BtClient() {
    	super();
    	thread = new Thread(this);
    	thread.start();
    }

	@Override
	public void run() {
		while (true) {
			if (!connected) {
		        try {
		            LocalDevice local = LocalDevice.getLocalDevice();
		            LOG.info(local.getFriendlyName());
		            DiscoveryAgent agent = local.getDiscoveryAgent();
		            agent.startInquiry(DiscoveryAgent.GIAC, this);
		        } catch (BluetoothStateException e) {
		            LOG.info(e.getMessage());
		        }
			} else {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					LOG.info(e.getMessage());
				}
			}
		}
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
