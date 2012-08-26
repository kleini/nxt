/*
 * GPL v3
 */

package org.kleini.solver.slaveA;

import java.io.IOException;

import org.kleini.nxt.Log;
import org.kleini.nxt.LogFactory;

import lejos.nxt.comm.Bluetooth;
import lejos.nxt.remote.RemoteNXT;

/**
 * @author <a href="mailto:himself@kleini.org">Marcus Klein</a>
 */
public class BTReceiver implements Runnable {

    private static final Log LOG = LogFactory.getLog();

    private boolean running = true;
    private RemoteNXT controller = null;

    public BTReceiver() {
        super();
    }

    public boolean connect() {
        boolean successful = false;
        try {
            controller = new RemoteNXT("Controller", Bluetooth.getConnector());
            successful = true;
        } catch (IOException e) {
            LOG.info("Mist:" + e.getMessage());
        }
        return successful;
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        while (running && null != controller) {
            byte[] buf = controller.receiveMessage(0, 0, true);
            if (null != buf) {
                String message = new String(buf, "UTF-8");
                LOG.info(message);
            }
        }
    }
}
