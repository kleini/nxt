/*
 * GPL v3
 */

package org.kleini.nxt.internal;

import lejos.nxt.LCD;

import org.kleini.nxt.Log;

/**
 * @author <a href="mailto:himself@kleini.org">Marcus Klein</a>
 */
public class LogImpl implements Log {

	public LogImpl() {
		super();
	}

	@Override
	public void info(String message) {
		LCD.scroll();
		LCD.drawString(message, 0, 7);
	}
}
