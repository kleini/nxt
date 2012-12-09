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

    private Thread thread;
    private boolean running = true;
    private List<BtSender> senders = new LinkedList<BtSender>();

    public BtServer() {
        super();
        thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        running = false;
        try {
            thread.join(1000);
        } catch (InterruptedException e) {
            LOG.info(e.getMessage());
        }
        for (BtSender sender : senders) {
            sender.stop();
        }
    }

    @Override
    public void run() {
        while (running) {
            BTConnection connection = Bluetooth.waitForConnection(0, NXTConnection.PACKET);
            if (null != connection) {
                String address = connection.getAddress();
                LOG.info("Client " + address + " connected.");
                senders.add(new BtSender(connection));
            } else {
                LOG.info("No client connected.");
            }
        }
    }
}
