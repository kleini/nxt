/*
 * GPL v3
 */

package org.kleini.solver.slaveA;

import lejos.nxt.Button;

/**
 * @author <a href="mailto:himself@kleini.org">Marcus Klein</a>
 */
public class SlaveA {

    public SlaveA() {
        super();
    }

    public static void main(String[] args) {
        new SlaveA();
        Button.waitForAnyPress();
        System.exit(0);
    }
}
