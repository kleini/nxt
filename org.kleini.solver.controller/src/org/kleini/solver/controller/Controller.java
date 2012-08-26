/*
 * GPL v3
 */

package org.kleini.solver.controller;

import org.kleini.nxt.Log;
import org.kleini.nxt.LogFactory;

import lejos.nxt.Button;

/**
 * @author <a href="mailto:himself@kleini.org">Marcus Klein</a>
 */
public class Controller {

	private Controller() {
		super();
	}

	public static void main(String[] args) {
		Log log = LogFactory.getLog();
		Slaves slaves = new Slaves();
		if (!slaves.connect2SlaveA()) {
			log.info("Connect to Slave A failed.");
			Button.waitForAnyPress();
			System.exit(1);
		}
		slaves.messageA("Hello");
		log.info("Message sent!");
		Button.waitForAnyPress();
		System.exit(0);
	}
}
