/**
 * GPL v3
 */

package org.kleini;

/**
 *
 * @author <a href="mailto:m@kleini.org">Marcus Klein</a>
 */
public enum Color {
    /** white */
    W('W'),
    /** blue */
    B('B'),
    /** red */
    R('R'),
    /** green */
    G('G'),
    /** orange */
    O('O'),
    /** yellow */
    Y('Y');

    private final char letter;

    private Color(final char letter) {
        this.letter = letter;
    }

    public char getLetter() {
        return letter;
    }

    public static Color byLetter(final char letter) {
        for (Color color : values()) {
            if (letter == color.letter) {
                return color;
            }
        }
        return null;
    }
}
