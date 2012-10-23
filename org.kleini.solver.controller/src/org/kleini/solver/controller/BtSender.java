/*
 * GPL v3
 */

package org.kleini.solver.controller;

import lejos.nxt.comm.BTConnection;

import org.kleini.nxt.Log;
import org.kleini.nxt.LogFactory;

/**
 * @author <a href="mailto:himself@kleini.org">Marcus Klein</a>
 */
public class BtSender implements Runnable {

    private static final Log LOG = LogFactory.getLog();

    private final BTConnection connection;

    private Thread thread;
    private boolean running;

    public BtSender(BTConnection connection) {
        super();
        this.connection = connection;
    }

    public void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            LOG.info(e.getMessage());
        }
    }

    @Override
    public void run() {
        while (running) {
            byte[] buf = "Hallo".getBytes("UTF-8");
            connection.sendPacket(buf, buf.length);
            connection.close();
        }
    }
}
