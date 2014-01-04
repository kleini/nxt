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
            LOG.error(e.getMessage());
        }
    }

    public void sendCommand(String command) {
        setCommand(command);
    }

    private volatile Object synchronize = new Object();
    private volatile String command = null;

    private synchronized String getCommand() {
        return command;
    }

    private synchronized void setCommand(String command) {
        this.command = command;
        synchronize.notifyAll();
    }

    @Override
    public void run() {
        while (running) {
            String currentCommand = getCommand();
            if (null == currentCommand) {
                try {
                    synchronize.wait();
                } catch (InterruptedException e) {
                    LOG.error(e.getMessage());
                }
            } else {
                LOG.trace("Sending " + currentCommand + " to " + receiver.getName());
                byte[] bytes = currentCommand.getBytes("ASCII");
                boolean sentSuccessfully = false;
                do {
                    int sent = connection.sendPacket(bytes, bytes.length);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        LOG.error(e.getMessage());
                    }
                    if (sent != bytes.length) {
                        running = false;
                    } else {
                        sentSuccessfully = receiveACK(currentCommand);
                    }
                    if (!sentSuccessfully) {
                        LOG.trace(receiver.getName() + " did not confirm command.");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    } else {
                        setCommand(null);
                    }
                } while (!sentSuccessfully && running);
            }
        }
        LOG.trace("Lost connection to " + receiver.getName());
        connection.close();
        receiver.setConnected(false);
    }

//    private void waitForACK() {
//        byte[] buf = new byte[10];
//        int length = -1;
//        boolean received = false;
//        do {
//            length = connection.readPacket(buf, buf.length);
//            if (length > 0) {
//                String text = new String(buf, 0, length, "ASCII");
//                received = "ACK".equals(text);
//                LOG.info("Recv: " + text);
//            }
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                LOG.error(e.getMessage());
//            }
//        } while (!received);
//    }

    private boolean receiveACK(String command) {
        byte[] buf = new byte[32];
        int length = -1;
        boolean received = false;
        length = connection.readPacket(buf, buf.length);
        if (length > 0) {
            String text = new String(buf, 0, length, "ASCII");
            LOG.trace("Recv: " + text + " from " + connection.getAddress());
            received = text.startsWith("ACK") && text.substring(3).equals(command);
        }
        return received;
    }
}
