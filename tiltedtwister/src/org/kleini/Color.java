/**
 * GPL v3
 */

package org.kleini;

/**
 *
 * @author <a href="mailto:himself@kleini.org">Marcus Klein</a>
 */
public enum Color {
    /** white */
    WHITE('W'),
    /** blue */
    BLUE('B'),
    /** red */
    RED('R'),
    /** green */
    GREEN('G'),
    /** orange */
    ORANGE('O'),
    /** yellow */
    YELLOW('Y');

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
