/*
 * GPL v3
 */

package org.kleini.solver.controller;

import lejos.nxt.Button;

/**
 * @author <a href="mailto:himself@kleini.org">Marcus Klein</a>
 */
public class Controller {

    private Controller() {
        super();
    }

    public static void main(String[] args) {
        BtServer server = new BtServer();
        Button.waitForAnyPress();
        server.stop();
        System.exit(0);
    }
}
