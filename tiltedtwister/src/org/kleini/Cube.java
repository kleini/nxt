/**
 * GPL v3
 */

package org.kleini;

/**
 *
 *
 * @author <a href="mailto:m@kleini.org">Marcus Klein</a>
 */
public final class Cube {

    public final Color[] cube = new Color[9 * 6];

    public Cube(final String faceList) {
        super();
        parseFaceList(faceList);
    }

    public Cube(final Cube other) {
        System.arraycopy(other.cube, 0, cube, 0, cube.length);
    }
    private void parseFaceList(final String faceList) {
        for (int i = 0; i < cube.length; i++) {
            cube[i] = Color.byLetter(faceList.charAt(i));
        }
    }

    public void setFace(final int pos, final Color color) {
        cube[pos] = color;
    }

    public void setFace(final int face, final int pos, final Color color) {
        cube[face * 9 + pos] = color;
    }

    public Color getColor(final int pos) {
        return cube[pos];
    }

    public Color get(final int face, final int pos) {
        return cube[face * 9 + pos];
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Cube ");
        for (int i = 0; i < cube.length; i++) {
            sb.append(cube[i].getLetter());
        }
        return sb.toString();
    }
}
