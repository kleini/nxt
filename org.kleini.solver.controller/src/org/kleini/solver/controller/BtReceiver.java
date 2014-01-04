/*
 * GPL v3
 */

package org.kleini.solver.controller;

/**
 * @author <a href="mailto:himself@kleini.org">Marcus Klein</a>
 */
public final class BtReceiver {

    private final String name;
    private final String identifier;
    private volatile boolean connected = false;
    private BtSender sender = null;

    BtReceiver(String name, String identifier) {
        super();
        this.name = name;
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public synchronized boolean isConnected() {
        return connected;
    }

    public synchronized void setConnected(boolean connected) {
        this.connected = connected;
    }

    public BtSender getSender() {
        return sender;
    }

    public void setSender(BtSender sender) {
        this.sender = sender;
    }

    public boolean sendCommand(String command) {
        if (null == sender) {
            return false;
        }
        sender.sendCommand(command);
        return true;
    }

    public boolean finishedCommand(String command) {
        // FIXME continue here
        return false;
    }
}
