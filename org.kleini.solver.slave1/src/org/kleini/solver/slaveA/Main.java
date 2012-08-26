/*
 * GPL v3
 */

package org.kleini.solver.slaveA;

import lejos.nxt.LCD;
import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;

/**
 * @author <a href="mailto:himself@kleini.org">Marcus Klein</a>
 */
public class Main {

	public Main() {
		super();
	}

	public static void main(String[] args) {
		NXTConnection controller = Bluetooth.waitForConnection(10000, NXTConnection.LCP);
		byte[] buf = new byte[32];
		int length = controller.read(buf, buf.length, true);
		String read = new String(buf, 0, length, "UTF-8");
		LCD.drawString(read, 0, 0);
	}
}
