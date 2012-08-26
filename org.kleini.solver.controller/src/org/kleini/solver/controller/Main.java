/*
 * GPL v3
 */

package org.kleini.solver.controller;

import lejos.nxt.LCD;
import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;

/**
 * @author <a href="mailto:himself@kleini.org">Marcus Klein</a>
 */
public class Main {

	private Main() {
		super();
	}

	public static void main(String[] args) {
		NXTConnection slaveA = Bluetooth.connect("SlaveA", NXTConnection.LCP);
		slaveA.write("Hello".getBytes("UTF-8"), 5, true);
		LCD.clearDisplay();
		LCD.drawString("Sent", 0, 0);
	}
}
