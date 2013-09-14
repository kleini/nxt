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
        BtServer server = new BtServer();
        Button.waitForAnyPress();
        server.stop();
        System.exit(0);
    }
}
