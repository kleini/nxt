/*
 * GPL v3
 */

package org.kleini.solver.slaveA;

import org.kleini.nxt.Log;
import org.kleini.nxt.LogFactory;
import org.kleini.nxt.bluetooth.BtClient;

import lejos.nxt.Button;

/**
 * @author <a href="mailto:himself@kleini.org">Marcus Klein</a>
 */
public class SlaveA {

    private static final Log LOG = LogFactory.getLog();

    private SlaveA() {
        super();
    }

    public static void main(String[] args) {
        BtClient client = new BtClient("Controller");
        Button.waitForAnyPress();
        client.stop();
        System.exit(0);
        LOG.info("Slave A terminated");
    }
}
