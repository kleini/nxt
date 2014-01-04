/*
 * GPL v3
 */

package org.kleini.nxt.bluetooth;

import org.kleini.nxt.Log;
import org.kleini.nxt.LogFactory;

import lejos.nxt.comm.BTConnection;

public class BtReceiver implements Runnable {

    private static final Log LOG = LogFactory.getLog();
    private static final byte[] ACK = new byte[] { 'A', 'C', 'K' };

    private final BTConnection connection;
    private final BtClient client;
    private final Thread thread;

    private boolean running = true;

    BtReceiver(BTConnection connection, BtClient client) {
        super();
        this.connection = connection;
        this.client = client;
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
        byte[] buf = new byte[10];
        int length = -1;
        while (running) {
            length = connection.readPacket(buf, buf.length);
            if (length > 0) {
                String text = new String(buf, 0, length, "ASCII");
                LOG.info(text);
            }
            sendACK();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                LOG.error(e.getMessage());
            }
        }
    }

    private void sendACK() {
        int sent = connection.sendPacket(ACK, ACK.length);
        if (sent != ACK.length) {
            running = false;
            client.connectionLost();
            LOG.trace("Lost connection.");
        }
    }
}
