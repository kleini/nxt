/*
 * GPL v3
 */

package org.kleini.solver.slaveA;

import lejos.nxt.Button;

import org.kleini.nxt.Log;
import org.kleini.nxt.LogFactory;

/**
 * @author <a href="mailto:himself@kleini.org">Marcus Klein</a>
 */
public class SlaveA {

	public SlaveA() {
		super();
	}

	public static void main(String[] args) {
		Log log = LogFactory.getLog();
		BTReceiver receiver = new BTReceiver();
		if (!receiver.connect()) {
			log.info("Failed to connect to controller.");
			Button.waitForAnyPress();
			System.exit(1);
		}
		Thread receiverThread = new Thread(receiver);
		receiverThread.start();
		Button.waitForAnyPress();
		receiver.stop();
		try {
			receiverThread.join();
		} catch (InterruptedException e) {
			log.info(e.getMessage());
		}
		System.exit(0);
	}
}
