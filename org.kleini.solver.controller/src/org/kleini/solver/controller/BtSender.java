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
    private BtReceiver receiver;

    public BtSender(BTConnection connection, BtReceiver receiver) {
        super();
        this.connection = connection;
        this.receiver = receiver;
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
            LOG.info(text);
            byte[] send = text.getBytes("ASCII");
            try {
                int sent = connection.sendPacket(send, send.length);
                if (sent != text.length()) {
                    running = false;
                    continue;
                } else {
                    waitForACK();
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        LOG.info(e.getMessage());
                    }
                }
            } catch (Exception e) {
                running = false;
                LOG.info("Kack" + e.getMessage());
            }
        }
        LOG.info("Stop sending");
        connection.close();
        receiver.setConnected(false);
    }

    private void waitForACK() {
        byte[] buf = new byte[10];
        int length = -1;
        boolean received = false;
        do {
            length = connection.readPacket(buf, buf.length);
            if (length > 0) {
                String text = new String(buf, 0, length, "ASCII");
                received = "ACK".equals(text);
                LOG.info(text);
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                LOG.error(e.getMessage());
            }
        } while (!received);
    }
}
