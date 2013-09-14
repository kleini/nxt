/*
 * GPL v3
 */

package org.kleini.nxt;

/**
 * @author <a href="mailto:himself@kleini.org">Marcus Klein</a>
 */
public interface Log {

    void trace(String message);

    void info(String message);

    void error(String message);
}
