/*
 * GPL v3
 */

package org.kleini.solver.slaveA;

import org.kleini.nxt.Log;
import org.kleini.nxt.LogFactory;

import lejos.nxt.comm.BTConnection;

public class BtReceiver implements Runnable {

    private static final Log LOG = LogFactory.getLog();

    private final Thread thread;

    private BTConnection connection;
    private boolean running = true;

    BtReceiver(BTConnection connection) {
        super();
        this.connection = connection;
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
        connection.close();
    }

    @Override
    public void run() {
        byte[] buf = new byte[256];
        int length = -1;
        while (running) {
            length = connection.readPacket(buf, buf.length);
            if (length > 0) {
                String text = new String(buf, 0, length, "ASCII");
                LOG.info(text);
            }
        }
    }
}
