/*
 * GPL v3
 */

package org.kleini.nxt;

import org.kleini.nxt.internal.LogImpl;

/**
 * @author <a href="mailto:himself@kleini.org">Marcus Klein</a>
 */
public class LogFactory {

	private LogFactory() {
		super();
	}

	public static Log getLog() {
		return new LogImpl();
	}
}
