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
    private boolean running = true;

    public BtSender(BTConnection connection) {
        super();
        this.connection = connection;
        thread = new Thread(this);
        thread.start();
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
        int count = 0;
        while (running) {
            String text = "Hallo" + count++;
            byte[] buf = text.getBytes("ASCII");
            if (buf != null) {
                connection.sendPacket(buf, buf.length);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                LOG.info(e.getMessage());
            }
        }
        connection.close();
    }
}
