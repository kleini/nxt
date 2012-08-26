/*
 * GPL v3
 */

package org.kleini.solver.controller;

import java.io.IOException;

import org.kleini.nxt.LogFactory;

import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTCommConnector;
import lejos.nxt.remote.RemoteNXT;

/**
 * @author <a href="mailto:himself@kleini.org">Marcus Klein</a>
 */
public class Slaves {

	private NXTCommConnector connector = Bluetooth.getConnector();
	private RemoteNXT slaveA;

	public Slaves() {
		super();
	}

	public boolean connect2SlaveA() {
		boolean successful = false;
		try {
			slaveA = new RemoteNXT("SlaveA", connector);
			successful = true;
		} catch (IOException e) {
			LogFactory.getLog().info(e.getMessage());
		}
		return successful;
	}

	public boolean messageA(String message) {
		return slaveA.sendMessage(message.getBytes("UTF-8"), 0) == 0;
	}
}
