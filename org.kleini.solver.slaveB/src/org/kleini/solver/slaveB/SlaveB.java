/*
 * GPL v3
 */

package org.kleini.solver.slaveB;

import org.kleini.nxt.bluetooth.BtClient;

import lejos.nxt.Button;

/**
 * @author <a href="mailto:himself@kleini.org">Marcus Klein</a>
 */
public class SlaveB {

    private SlaveB() {
        super();
    }

    public static void main(String[] args) {
        BtClient client = new BtClient();
        Button.waitForAnyPress();
        client.stop();
        System.exit(0);
    }
}
