/**
 * GPL v3
 */

package org.kleini;

/**
 *
 *
 * @author <a href="mailto:himself@kleini.org">Marcus Klein</a>
 */
public enum Face {
    LEFT(0),
    FRONT(1),
    RIGHT(2),
    BACK(3),
    UPPER(4),
    DOWN(5);

    public final int index;

    Face(final int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
