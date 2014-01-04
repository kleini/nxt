/*
 * GPL v3
 */

package org.kleini.nxt.internal;

import lejos.nxt.LCD;
import lejos.nxt.comm.RConsole;

import org.kleini.nxt.Log;

/**
 * @author <a href="mailto:himself@kleini.org">Marcus Klein</a>
 */
public class LogImpl implements Log {

    public LogImpl() {
        super();
        RConsole.openUSB(0);
    }

    @Override
    public void trace(String message) {
        RConsole.println(message);
    }

    @Override
    public void info(String message) {
        RConsole.println(message);
        LCD.scroll();
        LCD.drawString(message, 0, 7);
    }

    @Override
    public void error(String message) {
        RConsole.println(message);
    }
}
