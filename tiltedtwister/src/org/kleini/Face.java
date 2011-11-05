/**
 * GPL v3
 */

package org.kleini;

/**
 *
 *
 * @author <a href="mailto:m@kleini.org">Marcus Klein</a>
 */
public enum Face {
    LEFTFACE(0),
    FRONTFACE(1),
    RIGHTFACE(2),
    BACKFACE(3),
    UPPERFACE(4),
    DOWNFACE(5);

    public final int ord;

    Face(final int ord) {
        this.ord = ord;
    }
}
