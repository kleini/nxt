/*
 * GPL v3
 */

package org.kleini.solver.slaveA;

import lejos.nxt.comm.BTConnection;

public class BtReceiver implements Runnable {

	private final Thread thread;

	private BTConnection connection;

	BtReceiver(BTConnection connection) {
		super();
		this.connection = connection;
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		byte[] buf = new byte[256];
		int length = -1;
		while (true) {
			length = connection.readPacket(buf, buf.length);
			String text = new String(buf, 0, length, "ASCII");
		}
	}
}
