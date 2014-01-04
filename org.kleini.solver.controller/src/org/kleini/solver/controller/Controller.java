/*
 * GPL v3
 */

package org.kleini.solver.controller;

import lejos.nxt.Button;

import org.kleini.nxt.Log;
import org.kleini.nxt.LogFactory;

/**
 * @author <a href="mailto:himself@kleini.org">Marcus Klein</a>
 */
public class Controller {

    private static final Log LOG = LogFactory.getLog();

    private Controller() {
        super();
    }

    public static void main(String[] args) {
        LOG.info("Started controller");
        BtReceiver[] receivers = new BtReceiver[] { new BtReceiver("SlaveA", "0016530FC2F1"), new BtReceiver("SlaveB", "0016530DD903") };
        BtServer server = new BtServer(receivers);
        while (server.connectionMissing()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                LOG.error(e.getMessage());
            }
        }
        boolean running = true;
        do {
            int count = 0;
            for (BtReceiver receiver : receivers) {
                count++;
                if (!receiver.sendCommand("Hallo " + count)) {
                    running = false;
                }
                LOG.trace("Waiting for finished command " + receiver.getName());
                if (!receiver.finishedCommand("Hallo " + count)) {
                    
                }
            }
        } while (running && 0 == Button.readButtons());
        server.stop();
        System.exit(0);
        LOG.info("Controller terminated");
    }
}
