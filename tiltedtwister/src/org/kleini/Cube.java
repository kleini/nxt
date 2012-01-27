/**
 * GPL v3
 */

package org.kleini;

/**
 *
 *
 * @author <a href="mailto:himself@kleini.org">Marcus Klein</a>
 */
public final class Cube {

    private static final int LEFT_FACE_OFFSET = Face.LEFT.getIndex() * 9;
    private static final int FRONT_FACE_OFFSET = Face.FRONT.getIndex() * 9;
    private static final int RIGHT_FACE_OFFSET = Face.RIGHT.getIndex() * 9;
    private static final int BACK_FACE_OFFSET = Face.BACK.getIndex() * 9;
//    private static final int UP_FACE_OFFSET = Face.UPPER.getIndex() * 9;
//    private static final int DOWN_FACE_OFFSET = Face.DOWN.getIndex() * 9;

    private static final int UPPERLEFT = 0;
    private static final int UPPERMID = 1;
    private static final int UPPERRIGHT = 2;
    private static final int MIDLEFT = 3;
    private static final int CENTER = 4;
    private static final int MIDRIGHT = 5;
    private static final int DOWNLEFT = 6;
    private static final int DOWNMID = 7;
    private static final int DOWNRIGHT = 8;

    public final Color[] cube = new Color[9 * 6];
    public final Color[] tmpCube = new Color[9 * 6];

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

    public void set(final int pos, final Color color) {
        cube[pos] = color;
    }

    public void set(final int face, final int pos, final Color color) {
        cube[face * 9 + pos] = color;
    }

    public void set(final Face face, final int pos, final Color color) {
        cube[face.getIndex() * 9 + pos] = color;
    }

    public Color get(final int pos) {
        return cube[pos];
    }

    public Color get(final int face, final int pos) {
        return cube[face * 9 + pos];
    }

    public Color get(final Face face, final int pos) {
        return cube[face.getIndex() * 9 + pos];
    }

    private void copyFace(final Face from, final Face to) {
        System.arraycopy(tmpCube, from.getIndex() * 9, cube, to.getIndex() * 9, 9);
    }

    private void copyFaceClockwise(final Face from, final Face to, final int turns) {
        final int fromIndexOffset = from.getIndex() * 9;
        final int toIndexOffset = to.getIndex() * 9;
        switch (turns) {
        case 1:
            cube[toIndexOffset + UPPERLEFT] = tmpCube[fromIndexOffset + DOWNLEFT];
            cube[toIndexOffset + UPPERMID] = tmpCube[fromIndexOffset + MIDLEFT];
            cube[toIndexOffset + UPPERRIGHT] = tmpCube[fromIndexOffset + UPPERLEFT];
            cube[toIndexOffset + MIDLEFT] = tmpCube[fromIndexOffset + DOWNMID];
            cube[toIndexOffset + CENTER] = tmpCube[fromIndexOffset + CENTER];
            cube[toIndexOffset + MIDRIGHT] = tmpCube[fromIndexOffset + UPPERMID];
            cube[toIndexOffset + DOWNLEFT] = tmpCube[fromIndexOffset + DOWNRIGHT];
            cube[toIndexOffset + DOWNMID] = tmpCube[fromIndexOffset + MIDRIGHT];
            cube[toIndexOffset + DOWNRIGHT] = tmpCube[fromIndexOffset + UPPERRIGHT];
            break;
        case 2:
            cube[toIndexOffset + UPPERLEFT] = tmpCube[fromIndexOffset + DOWNRIGHT];
            cube[toIndexOffset + UPPERMID] = tmpCube[fromIndexOffset + DOWNMID];
            cube[toIndexOffset + UPPERRIGHT] = tmpCube[fromIndexOffset + DOWNLEFT];
            cube[toIndexOffset + MIDLEFT] = tmpCube[fromIndexOffset + MIDRIGHT];
            cube[toIndexOffset + CENTER] = tmpCube[fromIndexOffset + CENTER];
            cube[toIndexOffset + MIDRIGHT] = tmpCube[fromIndexOffset + MIDLEFT];
            cube[toIndexOffset + DOWNLEFT] = tmpCube[fromIndexOffset + UPPERRIGHT];
            cube[toIndexOffset + DOWNMID] = tmpCube[fromIndexOffset + UPPERMID];
            cube[toIndexOffset + DOWNRIGHT] = tmpCube[fromIndexOffset + UPPERLEFT];
            break;
        case 3:
            cube[toIndexOffset + UPPERLEFT] = tmpCube[fromIndexOffset + UPPERRIGHT];
            cube[toIndexOffset + UPPERMID] = tmpCube[fromIndexOffset + MIDRIGHT];
            cube[toIndexOffset + UPPERRIGHT] = tmpCube[fromIndexOffset + DOWNRIGHT];
            cube[toIndexOffset + MIDLEFT] = tmpCube[fromIndexOffset + UPPERMID];
            cube[toIndexOffset + CENTER] = tmpCube[fromIndexOffset + CENTER];
            cube[toIndexOffset + MIDRIGHT] = tmpCube[fromIndexOffset + DOWNMID];
            cube[toIndexOffset + DOWNLEFT] = tmpCube[fromIndexOffset + UPPERLEFT];
            cube[toIndexOffset + DOWNMID] = tmpCube[fromIndexOffset + MIDLEFT];
            cube[toIndexOffset + DOWNRIGHT] = tmpCube[fromIndexOffset + DOWNLEFT];
            break;
        }
    }

    public void turnCube(final int turns) {
        System.arraycopy(cube, 0, tmpCube, 0, cube.length);
        switch (turns) {
        case 1:
            copyFaceClockwise(Face.UPPER, Face.UPPER, 1);
            copyFace(Face.LEFT, Face.BACK);
            copyFace(Face.BACK, Face.RIGHT);
            copyFace(Face.RIGHT, Face.FRONT);
            copyFace(Face.FRONT, Face.LEFT);
            copyFaceClockwise(Face.DOWN, Face.DOWN, 3);
            break;
        case 2:
            copyFaceClockwise(Face.UPPER, Face.UPPER, 2);
            copyFace(Face.LEFT, Face.RIGHT);
            copyFace(Face.BACK, Face.FRONT);
            copyFace(Face.RIGHT, Face.LEFT);
            copyFace(Face.FRONT, Face.BACK);
            copyFaceClockwise(Face.DOWN, Face.DOWN, 2);
            break;
        case 3:
            copyFaceClockwise(Face.UPPER, Face.UPPER, 3);
            copyFace(Face.LEFT, Face.FRONT);
            copyFace(Face.BACK, Face.LEFT);
            copyFace(Face.RIGHT, Face.BACK);
            copyFace(Face.FRONT, Face.RIGHT);
            copyFaceClockwise(Face.DOWN, Face.DOWN, 1);
            break;
        }
    }

    public void tiltCube(final int turns) {
        System.arraycopy(cube, 0, tmpCube, 0, cube.length);
        switch (turns) {
        case 1:
            copyFaceClockwise(Face.UPPER, Face.RIGHT, 1);
            copyFaceClockwise(Face.RIGHT, Face.DOWN, 1);
            copyFaceClockwise(Face.DOWN, Face.LEFT, 1);
            copyFaceClockwise(Face.LEFT, Face.UPPER, 1);
            copyFaceClockwise(Face.FRONT, Face.FRONT, 1);
            copyFaceClockwise(Face.BACK, Face.BACK, 3);
            break;
        case 2:
            copyFaceClockwise(Face.UPPER, Face.DOWN, 2);
            copyFaceClockwise(Face.RIGHT, Face.LEFT, 2);
            copyFaceClockwise(Face.DOWN, Face.UPPER, 2);
            copyFaceClockwise(Face.LEFT, Face.RIGHT, 2);
            copyFaceClockwise(Face.FRONT, Face.FRONT, 2);
            copyFaceClockwise(Face.BACK, Face.BACK, 2);
            break;
        case 3:
            copyFaceClockwise(Face.UPPER, Face.LEFT, 3);
            copyFaceClockwise(Face.RIGHT, Face.UPPER, 3);
            copyFaceClockwise(Face.DOWN, Face.RIGHT, 3);
            copyFaceClockwise(Face.LEFT, Face.DOWN, 3);
            copyFaceClockwise(Face.FRONT, Face.FRONT, 3);
            copyFaceClockwise(Face.BACK, Face.BACK, 1);
            break;
        }
    }

    public String twistCube(final int turns) {
        StringBuilder sb = new StringBuilder();
        for (int twists = 0; twists < turns; twists++) {
            System.arraycopy(cube, 0, tmpCube, 0, cube.length);
            copyFaceClockwise(Face.DOWN, Face.DOWN, 1);
            System.arraycopy(tmpCube, BACK_FACE_OFFSET + 6, cube, LEFT_FACE_OFFSET + 6, 3);
            System.arraycopy(tmpCube, LEFT_FACE_OFFSET + 6, cube, FRONT_FACE_OFFSET + 6, 3);
            System.arraycopy(tmpCube, FRONT_FACE_OFFSET + 6, cube, RIGHT_FACE_OFFSET + 6, 3);
            System.arraycopy(tmpCube, RIGHT_FACE_OFFSET + 6, cube, BACK_FACE_OFFSET + 6, 3);
            sb.append(cube[49].getLetter());
        }
        return sb.toString();
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
