/*
 * GPL v3
 */

package org.kleini.solver.controller;

import java.util.LinkedList;
import java.util.List;

import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;

import org.kleini.nxt.Log;
import org.kleini.nxt.LogFactory;

/**
 * Bluetooth server that waits for the slaves to connect.
 *
 * @author <a href="mailto:himself@kleini.org">Marcus Klein</a>
 */
public class BtServer implements Runnable {

    private static final Log LOG = LogFactory.getLog();

    private final BtReceiver[] receivers;

    private Thread thread;
    private boolean running = true;
    private List<BtSender> senders = new LinkedList<BtSender>();

    public BtServer(BtReceiver[] receivers) {
        super();
        this.receivers = receivers;
        thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        running = false;
        try {
            thread.join(1000);
        } catch (InterruptedException e) {
            LOG.error(e.getMessage());
        }
        for (BtSender sender : senders) {
            sender.stop();
        }
    }

    @Override
    public void run() {
        while (running) {
            if (connectionMissing()) {
                LOG.trace("Waiting for connection");
                BTConnection connection = Bluetooth.waitForConnection(1000, NXTConnection.RAW);
                if (null != connection) {
                    String address = connection.getAddress();
                    LOG.trace("Device " + address + " connected.");
                    boolean found = false;
                    for (BtReceiver receiver : receivers) {
                        if (receiver.getName().equals(address) || receiver.getIdentifier().equals(address)) {
                            LOG.info(receiver.getName() + " connected.");
                            receiver.setConnected(true);
                            receiver.setSender(new BtSender(connection, receiver));
                            found = true;
                        }
                    }
                    if (!found) {
                        LOG.trace(address);
                        connection.close();
                    }
                } else {
                    LOG.trace("No connection");
                }
            } else {
                LOG.trace("All slaves connected.");
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                LOG.error(e.getMessage());
            }
        }
    }

    public boolean connectionMissing() {
        boolean retval = false;
        for (BtReceiver receiver : receivers) {
            retval |= !receiver.isConnected();
        }
        return retval;
    }
}
