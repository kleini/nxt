/**
 * GPL v3
 */

package org.kleini;

import lejos.nxt.Button;
import lejos.nxt.LCD;

/**
 *
 *
 * @author <a href="mailto:m@kleini.org">Marcus Klein</a>
 */
public class TiltedTwister implements Solver {

    private static final int UPPERLEFT = 0;
    private static final int UPPERMID = 1;
    private static final int UPPERRIGHT = 2;
    private static final int MIDLEFT = 3;
    private static final int CENTER = 4;
    private static final int MIDRIGHT = 5;
    private static final int DOWNLEFT = 6;
    private static final int DOWNMID = 7;
    private static final int DOWNRIGHT = 8;

    private static final int LEFTFACE_UPPERLEFT = 0;
    private static final int LEFTFACE_UPPERMID = 1;
    private static final int LEFTFACE_UPPERRIGHT = 2;
    private static final int LEFTFACE_MIDLEFT = 3;
    private static final int LEFTFACE_CENTER = 4;
    private static final int LEFTFACE_MIDRIGHT = 5;
    private static final int LEFTFACE_DOWNLEFT = 6;
    private static final int LEFTFACE_DOWNMID = 7;
//    #define  LEFTFACE_DOWNRIGHT   8

    private static final int FRONTFACE_UPPERLEFT = 9;
    private static final int FRONTFACE_UPPERMID = 10;
    private static final int FRONTFACE_UPPERRIGHT = 11;
    private static final int FRONTFACE_MIDLEFT = 12;
    private static final int FRONTFACE_CENTER = 13;
    private static final int FRONTFACE_MIDRIGHT = 14;
    private static final int FRONTFACE_DOWNLEFT = 15;
    private static final int FRONTFACE_DOWNMID = 16;
//    #define  FRONTFACE_DOWNRIGHT  17

    private static final int RIGHTFACE_UPPERLEFT = 18;
    private static final int RIGHTFACE_UPPERMID = 19;
    private static final int RIGHTFACE_UPPERRIGHT = 20;
    private static final int RIGHTFACE_MIDLEFT = 21;
    private static final int RIGHTFACE_CENTER = 22;
    private static final int RIGHTFACE_MIDRIGHT = 23;
    private static final int RIGHTFACE_DOWNLEFT = 24;
    private static final int RIGHTFACE_DOWNMID = 25;
//    #define  RIGHTFACE_DOWNRIGHT  26

    private static final int BACKFACE_UPPERLEFT = 27;
    private static final int BACKFACE_UPPERMID = 28;
    private static final int BACKFACE_UPPERRIGHT = 29;
    private static final int BACKFACE_MIDLEFT = 30;
    private static final int BACKFACE_CENTER = 31;
    private static final int BACKFACE_MIDRIGHT = 32;
    private static final int BACKFACE_DOWNLEFT = 33;
    private static final int BACKFACE_DOWNMID = 34;
    private static final int BACKFACE_DOWNRIGHT = 35;

    private static final int UPPERFACE_UPPERLEFT = 36;
    private static final int UPPERFACE_UPPERMID = 37;
    private static final int UPPERFACE_UPPERRIGHT = 38;
    private static final int UPPERFACE_MIDLEFT = 39;
    private static final int UPPERFACE_CENTER = 40;
    private static final int UPPERFACE_MIDRIGHT = 41;
    private static final int UPPERFACE_DOWNLEFT = 42;
    private static final int UPPERFACE_DOWNMID = 43;
    private static final int UPPERFACE_DOWNRIGHT = 44;

    private static final int DOWNFACE_UPPERLEFT = 45;
    private static final int DOWNFACE_UPPERMID = 46;
    private static final int DOWNFACE_UPPERRIGHT = 47;
    private static final int DOWNFACE_MIDLEFT = 48;
    private static final int DOWNFACE_CENTER = 49;
    private static final int DOWNFACE_MIDRIGHT = 50;
    private static final int DOWNFACE_DOWNLEFT = 51;
    private static final int DOWNFACE_DOWNMID = 52;
    private static final int DOWNFACE_DOWNRIGHT = 53;

    private static final int MAXMOVES = 300;

    /**
     * the cube during the calculation of the solution.
     */
    private Cube cube;
    private Cube tmpCube;

    private final char[] moves = new char[MAXMOVES];
    private int movesCount = 0;

    private char[] solution = new char[MAXMOVES];
    private int solutionCount;
    private int solutionTwists;
    private int twists;

    //*****************************************************************************
    // Color resolving

//    private void Sorry() {
//        System.out.println("SORRY");
//        System.out.println("Can't resolve");
//        System.out.println("colors");
//    }

    private void Sorry() {
        LCD.clearDisplay();
        LCD.drawString("SORRY", 5, 1); //LCD_LINE2
        LCD.drawString("Can't resolve", 5, 3);
        LCD.drawString("colors", 5, 4);
//        PlayFile("Sorry.rso");
    }

    //*****************************************************************************
    // Solve functions

    private void CopyCube() {
        tmpCube = new Cube(cube);
    }

    private void CopyFace(final Face from, final Face to) {
        for (int i = 0; i < 9; i++) {
            cube.set(to, i, tmpCube.get(from, i));
        }
    }

    private void CopyFaceClockwise(final Face from, final Face to, final int turns) {
        if (turns == 1) {
            cube.set(to, UPPERLEFT, tmpCube.get(from, DOWNLEFT));
            cube.set(to, UPPERMID, tmpCube.get(from, MIDLEFT));
            cube.set(to, UPPERRIGHT, tmpCube.get(from, UPPERLEFT));
            cube.set(to, MIDLEFT, tmpCube.get(from, DOWNMID));
            cube.set(to, CENTER, tmpCube.get(from, CENTER));
            cube.set(to, MIDRIGHT, tmpCube.get(from, UPPERMID));
            cube.set(to, DOWNLEFT, tmpCube.get(from, DOWNRIGHT));
            cube.set(to, DOWNMID, tmpCube.get(from, MIDRIGHT));
            cube.set(to, DOWNRIGHT, tmpCube.get(from, UPPERRIGHT));
        } else if (turns == 2) {
            cube.set(to, UPPERLEFT, tmpCube.get(from, DOWNRIGHT));
            cube.set(to, UPPERMID, tmpCube.get(from, DOWNMID));
            cube.set(to, UPPERRIGHT, tmpCube.get(from, DOWNLEFT));
            cube.set(to, MIDLEFT, tmpCube.get(from, MIDRIGHT));
            cube.set(to, CENTER, tmpCube.get(from, CENTER));
            cube.set(to, MIDRIGHT, tmpCube.get(from, MIDLEFT));
            cube.set(to, DOWNLEFT, tmpCube.get(from, UPPERRIGHT));
            cube.set(to, DOWNMID, tmpCube.get(from, UPPERMID));
            cube.set(to, DOWNRIGHT, tmpCube.get(from, UPPERLEFT));
        } else {
            // turns == 3
            cube.set(to, UPPERLEFT, tmpCube.get(from, UPPERRIGHT));
            cube.set(to, UPPERMID, tmpCube.get(from, MIDRIGHT));
            cube.set(to, UPPERRIGHT, tmpCube.get(from, DOWNRIGHT));
            cube.set(to, MIDLEFT, tmpCube.get(from, UPPERMID));
            cube.set(to, CENTER, tmpCube.get(from, CENTER));
            cube.set(to, MIDRIGHT, tmpCube.get(from, DOWNMID));
            cube.set(to, DOWNLEFT, tmpCube.get(from, UPPERLEFT));
            cube.set(to, DOWNMID, tmpCube.get(from, MIDLEFT));
            cube.set(to, DOWNRIGHT, tmpCube.get(from, DOWNLEFT));
        }
    }

    private void TurnCube(final int turns) {
        CopyCube();
        if (turns == 1) {
            CopyFaceClockwise(Face.UPPER, Face.UPPER, 1);
            CopyFace(Face.LEFT, Face.BACK);
            CopyFace(Face.BACK, Face.RIGHT);
            CopyFace(Face.RIGHT, Face.FRONT);
            CopyFace(Face.FRONT, Face.LEFT);
            CopyFaceClockwise(Face.DOWN, Face.DOWN, 3);
        } else if (turns == 2) {
            CopyFaceClockwise(Face.UPPER, Face.UPPER, 2);
            CopyFace(Face.LEFT, Face.RIGHT);
            CopyFace(Face.BACK, Face.FRONT);
            CopyFace(Face.RIGHT, Face.LEFT);
            CopyFace(Face.FRONT, Face.BACK);
            CopyFaceClockwise(Face.DOWN, Face.DOWN, 2);
        } else {
            // turns==3
            CopyFaceClockwise(Face.UPPER, Face.UPPER, 3);
            CopyFace(Face.LEFT, Face.FRONT);
            CopyFace(Face.BACK, Face.LEFT);
            CopyFace(Face.RIGHT, Face.BACK);
            CopyFace(Face.FRONT, Face.RIGHT);
            CopyFaceClockwise(Face.DOWN, Face.DOWN, 1);
        }
    }

    private void TiltCube(final int turns) {
        CopyCube();
        if (turns==1) {
            CopyFaceClockwise(Face.UPPER, Face.RIGHT, 1);
            CopyFaceClockwise(Face.RIGHT, Face.DOWN, 1);
            CopyFaceClockwise(Face.DOWN, Face.LEFT, 1);
            CopyFaceClockwise(Face.LEFT, Face.UPPER, 1);
            CopyFaceClockwise(Face.FRONT, Face.FRONT, 1);
            CopyFaceClockwise(Face.BACK, Face.BACK, 3);
        } else if (turns == 2) {
            CopyFaceClockwise(Face.UPPER, Face.DOWN, 2);
            CopyFaceClockwise(Face.RIGHT, Face.LEFT, 2);
            CopyFaceClockwise(Face.DOWN, Face.UPPER, 2);
            CopyFaceClockwise(Face.LEFT, Face.RIGHT, 2);
            CopyFaceClockwise(Face.FRONT, Face.FRONT, 2);
            CopyFaceClockwise(Face.BACK, Face.BACK, 2);
        } else {
            // turns == 3
            CopyFaceClockwise(Face.UPPER, Face.LEFT, 3);
            CopyFaceClockwise(Face.RIGHT, Face.UPPER, 3);
            CopyFaceClockwise(Face.DOWN, Face.RIGHT, 3);
            CopyFaceClockwise(Face.LEFT, Face.DOWN, 3);
            CopyFaceClockwise(Face.FRONT, Face.FRONT, 3);
            CopyFaceClockwise(Face.BACK, Face.BACK, 1);
        }
    }

    private void TwistCube(final int turns) {
        if (movesCount + turns >= MAXMOVES) {
            Sorry();
            Button.waitForPress();
            System.exit(0);
        }
        for (int twists = 0; twists < turns; twists++) {
            CopyCube();
            CopyFaceClockwise(Face.DOWN, Face.DOWN, 1);
            for (int i = 6; i < 9; i++) {
                cube.set(Face.LEFT, i, tmpCube.get(Face.BACK, i));
                cube.set(Face.FRONT, i, tmpCube.get(Face.LEFT, i));
                cube.set(Face.RIGHT, i, tmpCube.get(Face.FRONT, i));
                cube.set(Face.BACK, i, tmpCube.get(Face.RIGHT, i));
            }
            moves[movesCount++] = cube.get(DOWNFACE_CENTER).getLetter();
        }
        // PlayTone(200+movesCount*20,1);
    }

    private void RotateFace(final Face face, final int turns) {
        switch (face) {
        case UPPER:
            TiltCube(2); TwistCube(turns); TiltCube(2); break;
        case LEFT:
            TiltCube(3); TwistCube(turns); TiltCube(1); break;
        case FRONT:
            TurnCube(1); TiltCube(3); TwistCube(turns); TiltCube(1); TurnCube(3); break;
        case RIGHT:
            TiltCube(1); TwistCube(turns); TiltCube(3); break;
        case BACK:
            TurnCube(3); TiltCube(3); TwistCube(turns); TiltCube(1); TurnCube(1); break;
        case DOWN:
            TwistCube(turns); break;
        }
    }

    private void RotateFaces(final String faces) {
        char faceturn;
        for (int i = 0; i < faces.length(); i++) {
            faceturn = faces.charAt(i);
            switch (faceturn) {
            case 'U': RotateFace(Face.UPPER, 1); break;
            case 'L': RotateFace(Face.LEFT, 1); break;
            case 'F': RotateFace(Face.FRONT, 1); break;
            case 'R': RotateFace(Face.RIGHT, 1); break;
            case 'B': RotateFace(Face.BACK, 1); break;
            case 'D': RotateFace(Face.DOWN, 1); break;
            case 'u': RotateFace(Face.UPPER, 3); break;
            case 'l': RotateFace(Face.LEFT, 3); break;
            case 'f': RotateFace(Face.FRONT, 3); break;
            case 'r': RotateFace(Face.RIGHT, 3); break;
            case 'b': RotateFace(Face.BACK, 3); break;
            case 'd': RotateFace(Face.DOWN, 3); break;
            }
        }
    }

    private boolean CornerColorOk(final int position, final Color color1, final Color color2) {
        return cube.get(position) == color1 || cube.get(position) == color2;
    }

    boolean TryBottomFace(final Color c1, final Color c2, int twists) {
        for (int i = 0; i < 4; i++) {
            if (twists == 0) {
                if (CornerColorOk(DOWNFACE_UPPERLEFT, c1, c2) && CornerColorOk(DOWNFACE_UPPERRIGHT, c1, c2) && CornerColorOk(DOWNFACE_DOWNRIGHT, c1, c2) && !CornerColorOk(DOWNFACE_DOWNLEFT, c1, c2)) {
                    return true;
                }
            } else if (twists == 1) {
                if (CornerColorOk(DOWNFACE_UPPERLEFT, c1, c2) && CornerColorOk(DOWNFACE_UPPERRIGHT, c1, c2)) {
                    for (int j = 0; j < 4; j++) {
                        RotateFaces("B");
                        if (CornerColorOk(DOWNFACE_DOWNRIGHT, c1, c2) && !CornerColorOk(DOWNFACE_DOWNLEFT, c1, c2)) {
                            return true;
                        } else if (!CornerColorOk(DOWNFACE_DOWNRIGHT, c1, c2) && CornerColorOk(DOWNFACE_DOWNLEFT, c1, c2)) {
                            TurnCube(3);
                            return true;
                        } else if (CornerColorOk(DOWNFACE_DOWNRIGHT, c1, c2) && CornerColorOk(DOWNFACE_DOWNLEFT, c1, c2)) {
                            if (CornerColorOk(UPPERFACE_UPPERLEFT, c1, c2) && CornerColorOk(UPPERFACE_UPPERRIGHT, c1, c2) && CornerColorOk(UPPERFACE_DOWNLEFT, c1, c2) && CornerColorOk(UPPERFACE_DOWNRIGHT, c1, c2)) {
                                return true;
                            }
                        }
                    }
                }
            } else if (twists == 2) {
                if (CornerColorOk(DOWNFACE_UPPERLEFT, c1, c2)) {
                    for (int j = 0; j < 4; j++) {
                        RotateFaces("R");
                        if (CornerColorOk(DOWNFACE_UPPERRIGHT, c1, c2)) {
                            for (int k = 0; k < 4; k++) {
                                RotateFaces("B");
                                if (CornerColorOk(DOWNFACE_DOWNRIGHT, c1, c2) && !CornerColorOk(DOWNFACE_DOWNLEFT, c1, c2)) {
                                    return true;
                                } else if (!CornerColorOk(DOWNFACE_DOWNRIGHT, c1, c2) && CornerColorOk(DOWNFACE_DOWNLEFT, c1, c2)) {
                                    TurnCube(3);
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
            TurnCube(1);
        }
        return false;
    }


    boolean PrepareBottomFace(final Color c1, final Color c2, int twists) {
        if (TryBottomFace(c1, c2, twists)) {
            return true;
        }
        TiltCube(1);
        if (TryBottomFace(c1, c2, twists)) {
            return true;
        }
        TiltCube(1);
        if (TryBottomFace(c1, c2, twists)) {
            return true;
        }
        TiltCube(1);
        if (TryBottomFace(c1, c2, twists)) {
            return true;
        }
        TurnCube(1);
        TiltCube(1);
        if (TryBottomFace(c1, c2, twists)) {
            return true;
        }
        TiltCube(2);
        if (TryBottomFace(c1, c2, twists)) {
            return true;
        }
        return false;
    }

    private boolean MoveCorners(final int corner1, final int corner2, final int corner3, final int corner4, final Color color1, final Color color2, final String moves) {
        if (CornerColorOk(corner1, color1, color2) && CornerColorOk(corner2, color1, color2) && CornerColorOk(corner3, color1, color2) && CornerColorOk(corner4, color1, color2)) {
            RotateFaces(moves);
            return true;
        }
        return false;
    }

    private void OrientAllCorners(final Color c1, final Color c2) {
        if (!PrepareBottomFace(c1, c2, 0)) {
            if (!PrepareBottomFace(c1, c2, 1)) {
                PrepareBottomFace(c1, c2, 2);
            }
        }
        if (CornerColorOk(DOWNFACE_DOWNLEFT, c1, c2)) {
            return;
        } else if (CornerColorOk(LEFTFACE_DOWNLEFT, c1, c2)) {
            for (int i = 0; i < 4; i++) {
                if (MoveCorners(BACKFACE_UPPERRIGHT, RIGHTFACE_UPPERRIGHT, UPPERFACE_DOWNLEFT, UPPERFACE_DOWNRIGHT, c1, c2, "Lul")) {
                    return;
                } else if (MoveCorners(BACKFACE_UPPERRIGHT, UPPERFACE_UPPERRIGHT, UPPERFACE_DOWNLEFT, FRONTFACE_UPPERRIGHT, c1, c2, "flF")) {
                    return;
                } else if (MoveCorners(LEFTFACE_UPPERLEFT, UPPERFACE_UPPERRIGHT, LEFTFACE_UPPERRIGHT, RIGHTFACE_UPPERLEFT, c1, c2, "fLLF")) {
                    return;
                } else if (MoveCorners(BACKFACE_UPPERRIGHT, UPPERFACE_UPPERRIGHT, FRONTFACE_UPPERLEFT, RIGHTFACE_UPPERLEFT, c1, c2, "LLDF")) {
                    return;
                } else if (MoveCorners(UPPERFACE_UPPERLEFT, UPPERFACE_UPPERRIGHT, FRONTFACE_UPPERLEFT, UPPERFACE_DOWNRIGHT, c1, c2, "LfLf")) {
                    return;
                } else if (MoveCorners(LEFTFACE_UPPERLEFT, BACKFACE_UPPERLEFT, LEFTFACE_UPPERRIGHT, UPPERFACE_DOWNRIGHT, c1, c2, "bDDLdl")) {
                    return;
                } else if (MoveCorners(BACKFACE_UPPERRIGHT, RIGHTFACE_UPPERRIGHT, FRONTFACE_UPPERLEFT, FRONTFACE_UPPERRIGHT, c1, c2, "fLfDDb")) {
                    return;
                } else if (MoveCorners(LEFTFACE_UPPERLEFT, BACKFACE_UPPERLEFT, FRONTFACE_UPPERLEFT, RIGHTFACE_UPPERLEFT, c1, c2, "lULfLLF")) {
                    return;
                }
                RotateFaces("U");
            }
        } else if (CornerColorOk(BACKFACE_DOWNRIGHT, c1, c2)) {
            for (int i = 0; i < 4; i++) {
                if (MoveCorners(LEFTFACE_UPPERLEFT, UPPERFACE_UPPERRIGHT, FRONTFACE_UPPERLEFT, UPPERFACE_DOWNRIGHT, c1, c2, "bUB")) {
                    return;
                } else if (MoveCorners(LEFTFACE_UPPERLEFT, UPPERFACE_UPPERRIGHT, UPPERFACE_DOWNLEFT, RIGHTFACE_UPPERLEFT, c1, c2, "LDF")) {
                    return;
                } else if (MoveCorners(BACKFACE_UPPERRIGHT, BACKFACE_UPPERLEFT, UPPERFACE_DOWNLEFT, FRONTFACE_UPPERRIGHT, c1, c2, "RBBr")) {
                    return;
                } else if (MoveCorners(LEFTFACE_UPPERLEFT, RIGHTFACE_UPPERRIGHT, UPPERFACE_DOWNLEFT, FRONTFACE_UPPERRIGHT, c1, c2, "FFdB")) {
                    return;
                } else if (MoveCorners(UPPERFACE_UPPERLEFT, RIGHTFACE_UPPERRIGHT, UPPERFACE_DOWNLEFT, UPPERFACE_DOWNRIGHT, c1, c2, "bRbr")) {
                    return;
                } else if (MoveCorners(BACKFACE_UPPERRIGHT, BACKFACE_UPPERLEFT, LEFTFACE_UPPERRIGHT, UPPERFACE_DOWNRIGHT, c1, c2, "LUUfDF")) {
                    return;
                } else if (MoveCorners(LEFTFACE_UPPERLEFT, RIGHTFACE_UPPERRIGHT, FRONTFACE_UPPERLEFT, RIGHTFACE_UPPERLEFT, c1, c2, "RbRDDL")) {
                    return;
                } else if (MoveCorners(BACKFACE_UPPERRIGHT, RIGHTFACE_UPPERRIGHT, LEFTFACE_UPPERRIGHT, FRONTFACE_UPPERRIGHT, c1, c2, "FluRUUR")) {
                    return;
                }
                RotateFaces("U");
            }
        }
    }

    private void SplitCorners(final Color color, final Color opposite) {
        int count = 0;
        for (int i = 0; i < 4; i++) {
            if (cube.get(DOWNFACE_UPPERLEFT) == color) {
                count++;
            }
            TurnCube(1);
        }
        if (count == 1 || count == 3) {
            Color singleColor;
            if (count == 1) {
                singleColor = color;
            } else {
                singleColor = opposite;
            }
            while (cube.get(DOWNFACE_UPPERLEFT) != singleColor) {
                TurnCube(1);
            }
            while (cube.get(UPPERFACE_DOWNRIGHT) == singleColor) {
                RotateFaces("U");
            }
            if (cube.get(UPPERFACE_DOWNRIGHT) == cube.get(UPPERFACE_CENTER)) {
                RotateFaces("RRDLL");
            } else {
                RotateFaces("RRDRR");
            }
        } else if (count == 2) {
            if (cube.get(DOWNFACE_UPPERLEFT) != cube.get(DOWNFACE_DOWNRIGHT)) {
                TiltCube(2);
            }
            if (cube.get(DOWNFACE_UPPERLEFT) != cube.get(DOWNFACE_DOWNRIGHT)) {
                while (cube.get(DOWNFACE_UPPERLEFT) != cube.get(DOWNFACE_UPPERRIGHT)) {
                    TurnCube(1);
                }
                while (cube.get(UPPERFACE_UPPERLEFT) != cube.get(DOWNFACE_UPPERLEFT) || cube.get(UPPERFACE_UPPERRIGHT) != cube.get(DOWNFACE_UPPERRIGHT)) {
                    RotateFaces("U");
                }
                if (cube.get(UPPERFACE_UPPERLEFT) == cube.get(UPPERFACE_CENTER)) {
                    RotateFaces("FF");
                } else {
                    RotateFaces("BB");
                }
            } else if (cube.get(UPPERFACE_UPPERLEFT) == cube.get(UPPERFACE_DOWNRIGHT)) {
                if (cube.get(UPPERFACE_UPPERLEFT) != cube.get(DOWNFACE_DOWNLEFT)) {
                    RotateFaces("U");
                }
                if (cube.get(UPPERFACE_UPPERRIGHT) == cube.get(UPPERFACE_CENTER)) {
                    TurnCube(1);
                }
                RotateFaces("RRDDFF");
            } else {
                while (cube.get(UPPERFACE_UPPERLEFT) != cube.get(DOWNFACE_DOWNLEFT) || cube.get(UPPERFACE_DOWNLEFT) != cube.get(DOWNFACE_DOWNLEFT)) {
                    TurnCube(1);
                }
                if (cube.get(UPPERFACE_UPPERLEFT) != cube.get(UPPERFACE_CENTER)) {
                    RotateFaces("RRDRRDLL");
                } else {
                    RotateFaces("RRDRRDRR");
                }
            }
        }
        if (cube.get(UPPERFACE_UPPERLEFT) == cube.get(LEFTFACE_CENTER)) {
            TiltCube(1);
        } else if (cube.get(UPPERFACE_UPPERLEFT) == cube.get(FRONTFACE_CENTER)) {
            TurnCube(1);
            TiltCube(1);
        } else if (cube.get(UPPERFACE_UPPERLEFT) == cube.get(RIGHTFACE_CENTER)) {
            TiltCube(3);
        } else if (cube.get(UPPERFACE_UPPERLEFT) == cube.get(BACKFACE_CENTER)) {
            TurnCube(3);
            TiltCube(1);
        } else if (cube.get(UPPERFACE_UPPERLEFT) == cube.get(DOWNFACE_CENTER)) {
            TiltCube(2);
        }
        while (cube.get(UPPERFACE_UPPERLEFT) != cube.get(UPPERFACE_CENTER)) {
            RotateFaces("B");
        }
        while (cube.get(UPPERFACE_DOWNLEFT) != cube.get(UPPERFACE_CENTER)) {
            RotateFaces("F");
        }
    }

    //Step 3 Position all corners
    private void PositionAllCorners() {
        int count = 0;
        int topCount = 0;
        int bottomCount = 0;
        for (int i = 0; i < 4; i++) {
            if (cube.get(BACKFACE_DOWNLEFT) == cube.get(BACKFACE_DOWNRIGHT)) {
                bottomCount++;
            }
            if (cube.get(BACKFACE_UPPERLEFT) == cube.get(BACKFACE_UPPERRIGHT)) {
                topCount++;
            }
            TurnCube(1);
        }
        if (topCount > bottomCount) {
            TiltCube(2);
        }
        count = topCount + bottomCount;
        if (count == 0) {
            RotateFaces("RRFFRR");
        } else if (count == 1) {
            while (cube.get(BACKFACE_DOWNLEFT) != cube.get(BACKFACE_DOWNRIGHT)) {
                TurnCube(1);
            }
            RotateFaces("RuFUUfUr");
        } else if (count == 2) {
            while (cube.get(BACKFACE_DOWNLEFT) != cube.get(BACKFACE_DOWNRIGHT)) {
                TurnCube(1);
            }
            while (cube.get(BACKFACE_UPPERLEFT) != cube.get(BACKFACE_UPPERRIGHT)) {
                RotateFaces("U");
            }
            RotateFaces("RRUFFUURRURR");
        } else if (count == 4) {
            RotateFaces("FFuRurUFFURUr");
        } else if (count == 5) {
            while (cube.get(BACKFACE_UPPERLEFT) != cube.get(BACKFACE_UPPERRIGHT)) {
                TurnCube(1);
            }
            RotateFaces("RuRFFrURFFRR");
        }
    }

    private int TopEdgesSolved() {
        int solved = 0;
        if (cube.get(UPPERFACE_UPPERMID) == cube.get(UPPERFACE_CENTER) && cube.get(BACKFACE_UPPERMID) == cube.get(BACKFACE_UPPERLEFT)) {
            solved++;
        }
        if (cube.get(UPPERFACE_MIDLEFT) == cube.get(UPPERFACE_CENTER) && cube.get(LEFTFACE_UPPERMID) == cube.get(LEFTFACE_UPPERLEFT)) {
            solved++;
        }
        if (cube.get(UPPERFACE_MIDRIGHT) == cube.get(UPPERFACE_CENTER) && cube.get(RIGHTFACE_UPPERMID) == cube.get(RIGHTFACE_UPPERLEFT)) {
            solved++;
        }
        if (cube.get(UPPERFACE_DOWNMID) == cube.get(UPPERFACE_CENTER) && cube.get(FRONTFACE_UPPERMID) == cube.get(FRONTFACE_UPPERLEFT)) {
            solved++;
        }
        return solved;
    }

    private int BottomEdgesSolved() {
        int solved = 0;
        if (cube.get(DOWNFACE_UPPERMID) == cube.get(DOWNFACE_CENTER) && cube.get(FRONTFACE_DOWNMID) == cube.get(FRONTFACE_DOWNLEFT)) {
            solved++;
        }
        if (cube.get(DOWNFACE_MIDLEFT) == cube.get(DOWNFACE_CENTER) && cube.get(LEFTFACE_DOWNMID) == cube.get(LEFTFACE_DOWNLEFT)) {
            solved++;
        }
        if (cube.get(DOWNFACE_MIDRIGHT) == cube.get(DOWNFACE_CENTER) && cube.get(RIGHTFACE_DOWNMID) == cube.get(RIGHTFACE_DOWNLEFT)) {
            solved++;
        }
        if (cube.get(DOWNFACE_DOWNMID) == cube.get(DOWNFACE_CENTER) && cube.get(BACKFACE_DOWNMID) == cube.get(BACKFACE_DOWNLEFT)) {
            solved++;
        }
        return solved;
    }

    private void SetBottomFace(final Face downface, final int downpos, final Face sideface, final int sidepos) {
        if (cube.get(downface, downpos) == cube.get(DOWNFACE_CENTER)) {
            while (cube.get(RIGHTFACE_DOWNLEFT) != cube.get(sideface, sidepos)) {
                RotateFaces("D");
            }
        } else {
            for (int i = 0; i < 4; i++) {
                if (cube.get(DOWNFACE_MIDRIGHT) != cube.get(DOWNFACE_CENTER) || cube.get(RIGHTFACE_DOWNMID) != cube.get(RIGHTFACE_DOWNLEFT)) {
                    break;
                }
                RotateFaces("D");
            }
        }
    }

    private void TopEdgeMoveOut() {
        for (int i = 0; i < 4; i++) {
            if (cube.get(UPPERFACE_MIDRIGHT) != cube.get(UPPERFACE_CENTER) || cube.get(RIGHTFACE_UPPERMID) != cube.get(RIGHTFACE_UPPERLEFT)) {
                SetBottomFace(Face.LEFT, 0, Face.LEFT, 0);
                RotateFaces("rUdF");
                return;
            }
            TurnCube(1);
        }
    }

    private boolean TopEdgeShort() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (cube.get(LEFTFACE_MIDRIGHT) == cube.get(UPPERFACE_CENTER) && cube.get(FRONTFACE_MIDLEFT) == cube.get(RIGHTFACE_UPPERLEFT)) {
                    SetBottomFace(Face.RIGHT, UPPERMID, Face.UPPER, MIDRIGHT);
                    RotateFaces("rUdF");
                    return true;
                }
                if (cube.get(LEFTFACE_MIDLEFT) == cube.get(UPPERFACE_CENTER) && cube.get(BACKFACE_MIDRIGHT) == cube.get(RIGHTFACE_UPPERLEFT)) {
                    SetBottomFace(Face.RIGHT, UPPERMID, Face.UPPER, MIDRIGHT);
                    RotateFaces("RuDb");
                    return true;
                }
                if (cube.get(FRONTFACE_MIDLEFT) == cube.get(UPPERFACE_CENTER) && cube.get(LEFTFACE_MIDRIGHT) == cube.get(RIGHTFACE_UPPERLEFT)) {
                    SetBottomFace(Face.BACK, MIDRIGHT, Face.LEFT, MIDLEFT);
                    RotateFaces("RUUddl");
                    return true;
                }
                if (cube.get(BACKFACE_MIDRIGHT) == cube.get(UPPERFACE_CENTER) && cube.get(LEFTFACE_MIDLEFT) == cube.get(RIGHTFACE_UPPERLEFT)) {
                    SetBottomFace(Face.FRONT, MIDLEFT, Face.LEFT, MIDRIGHT);
                    RotateFaces("ruuDDL");
                    return true;
                }
                if (cube.get(RIGHTFACE_DOWNMID) == cube.get(UPPERFACE_CENTER) && cube.get(DOWNFACE_MIDRIGHT) == cube.get(RIGHTFACE_UPPERLEFT)) {
                    RotateFaces("RUdf");
                    return true;
                }
                TurnCube(1);
            }
            RotateFaces("U");
        }
        return false;
    }

    private boolean TopEdgeLong() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (cube.get(DOWNFACE_MIDRIGHT) == cube.get(UPPERFACE_CENTER) && cube.get(RIGHTFACE_DOWNMID) == cube.get(RIGHTFACE_UPPERLEFT)) {
                    SetBottomFace(Face.BACK, MIDRIGHT, Face.LEFT, MIDLEFT);
                    RotateFaces("RuDBBUdR");
                    return true;
                }
                if (cube.get(RIGHTFACE_UPPERMID) == cube.get(UPPERFACE_CENTER) && cube.get(UPPERFACE_MIDRIGHT) == cube.get(RIGHTFACE_UPPERLEFT)) {
                    SetBottomFace(Face.LEFT, MIDRIGHT, Face.FRONT, MIDLEFT);
                    RotateFaces("ruDBBuuDDf");
                    return true;
                }
                TurnCube(1);
            }
            RotateFaces("U");
        }
        return false;
    }

    //Step 4 Solve top and bottom edges
    private void SolveTopAndBottomEdges() {
        int topEdgesSolved;
        int bottomEdgesSolved;
        while (true) {
            topEdgesSolved = TopEdgesSolved();
            bottomEdgesSolved = BottomEdgesSolved();
            if (topEdgesSolved + bottomEdgesSolved >= 7) {
                break;
            }
            if (topEdgesSolved < 3 || bottomEdgesSolved == 3) {
                if (TopEdgeShort()) {
                    continue;
                }
            }
            if (bottomEdgesSolved < 3 || topEdgesSolved == 3) {
                TiltCube(2);
                if (TopEdgeShort()) {
                    continue;
                }
            }
            if (topEdgesSolved < 3 || bottomEdgesSolved == 3) {
                if (TopEdgeLong()) {
                    continue;
                }
            }
            if (bottomEdgesSolved < 3 || topEdgesSolved == 3) {
                TiltCube(2);
                if (TopEdgeLong()) {
                    continue;
                }
            }
            if (topEdgesSolved >= 3) {
                TiltCube(2);
            }
            TopEdgeMoveOut(); // If two edges are swapped on upperface
        }
        if (bottomEdgesSolved < 4) {
            TiltCube(2);
        }
    }

    private void PrepareMiddleEdges() {
        for (int i = 0; i < 4; i++) {
            if (cube.get(LEFTFACE_MIDRIGHT) == cube.get(UPPERFACE_CENTER) || cube.get(LEFTFACE_MIDLEFT) == cube.get(UPPERFACE_CENTER)) {
                while (cube.get(UPPERFACE_MIDRIGHT) == cube.get(UPPERFACE_CENTER)) {
                    RotateFaces("U");
                }
                break;
            }
            TurnCube(1);
        }
        for (int i = 0; i < 4; i++) {
            if (cube.get(UPPERFACE_MIDRIGHT) != cube.get(UPPERFACE_CENTER) || cube.get(RIGHTFACE_UPPERMID) != cube.get(RIGHTFACE_UPPERLEFT)) {
                break;
            }
            TurnCube(1);
        }
    }

    private int TopEdgesInMiddleLayerOrientation() {
        int orientation = 0;
        if (cube.get(RIGHTFACE_MIDLEFT) != cube.get(LEFTFACE_CENTER) && cube.get(RIGHTFACE_MIDLEFT) != cube.get(RIGHTFACE_CENTER)) {
            orientation = 4;
        }
        if (cube.get(RIGHTFACE_UPPERMID) != cube.get(LEFTFACE_CENTER) && cube.get(RIGHTFACE_UPPERMID) != cube.get(RIGHTFACE_CENTER)) {
            orientation += 2;
        }
        if (cube.get(RIGHTFACE_MIDRIGHT) != cube.get(LEFTFACE_CENTER) && cube.get(RIGHTFACE_MIDRIGHT) != cube.get(RIGHTFACE_CENTER)) {
            orientation += 1;
        }
        return orientation;
    }

    private boolean MiddleEdgeTwisted(final Face face, final int pos) {
        return cube.get(face, pos) != cube.get(FRONTFACE_CENTER) && cube.get(face, pos) != cube.get(BACKFACE_CENTER);
    }

    private int TwistedMiddleEdges() {
        int twisted = 0;
        for (int i = 0; i < 4; i++) {
            if (MiddleEdgeTwisted(Face.FRONT, MIDRIGHT)) {
                twisted++;
            }
            TurnCube(1);
        }
        return twisted;
    }

    //Step 5 Orient middle edges
    private void OrientMiddleEdges() {
        PrepareMiddleEdges();
        if (cube.get(LEFTFACE_MIDRIGHT) == cube.get(UPPERFACE_CENTER)) {
            switch (TopEdgesInMiddleLayerOrientation()) {
            case 0: //OOO
                RotateFaces("UdFUdluDfUdL"); break;
            case 1: //OOX
                RotateFaces("UdfuDrUdfuDr"); break;
            case 2: //OXO
                RotateFaces("uDBUdRRUdF"); break;
            case 3: //OXX
                RotateFaces("ruDBUdrUdF"); break;
            case 4: //XOO
                RotateFaces("RRUdFuDRUdFuDr"); break;
            case 5: //XOX
                RotateFaces("rUdfuDruDBUdRuDB"); break;
            case 6: //XXO
                RotateFaces("ruDbUdRUdF"); break;
            case 7: // X X X
                RotateFaces("rUdfUdluDFFuDR"); break;
            }
        } else if (cube.get(LEFTFACE_MIDLEFT) == cube.get(UPPERFACE_CENTER)) {
            switch (TopEdgesInMiddleLayerOrientation()) {
            case 0: // OOO
                RotateFaces("uDbuDLUdBuDl"); break;
            case 1: // OOX
                RotateFaces("rruDbUdruDbUdR"); break;
            case 2: // OXO
                RotateFaces("UdfuDrruDb"); break;
            case 3: // OXX
                RotateFaces("RUdFuDruDb"); break;
            case 4: // XOO
                RotateFaces("uDBUdRuDBUdR"); break;
            case 5: // XOX
                RotateFaces("RuDBUdRUdfuDrUdf"); break;
            case 6: // XXO
                RotateFaces("RUdfuDRuDb"); break;
            case 7: // X X X
                RotateFaces("RuDBuDLUdbbUdr"); break;
            }
        } else if (cube.get(RIGHTFACE_UPPERMID) == cube.get(UPPERFACE_CENTER)) {
            switch (TwistedMiddleEdges()) {
            case 1:
                while (!MiddleEdgeTwisted(Face.FRONT, MIDRIGHT)) {
                    TurnCube(1);
                }
                while (cube.get(UPPERFACE_MIDLEFT) == cube.get(UPPERFACE_CENTER)) {
                    RotateFaces("U");
                }
                RotateFaces("RUUrUUddLLuDfUUf");
                break;
            case 3:
                while (MiddleEdgeTwisted(Face.FRONT, MIDRIGHT))
                    TurnCube(1);
                while (cube.get(UPPERFACE_MIDRIGHT) == cube.get(UPPERFACE_CENTER))
                    RotateFaces("U");
                RotateFaces("ruDbuDluDf");
                break;
            }
        } else if (cube.get(UPPERFACE_MIDRIGHT) == cube.get(UPPERFACE_CENTER)) {
            switch (TwistedMiddleEdges()) {
            case 2:
                while (true) {
                    if (MiddleEdgeTwisted(Face.FRONT, MIDLEFT) && MiddleEdgeTwisted(Face.FRONT, MIDRIGHT)) {
                        RotateFaces("RRFlRuRRULrf");
                        return;
                    } else if (MiddleEdgeTwisted(Face.FRONT, MIDLEFT) && MiddleEdgeTwisted(Face.BACK, MIDLEFT)) {
                        RotateFaces("FlRuRRULrfRR");
                        return;
                    }
                    TurnCube(1);
                }
            case 4:
                RotateFaces("RFFRRUdFUUddBRRBBUdR");
                break;
            }
        }
    }

    private boolean Rotate3MiddleEdges() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                if (cube.get(LEFTFACE_MIDRIGHT) == cube.get(LEFTFACE_CENTER) && cube.get(FRONTFACE_MIDLEFT) == cube.get(FRONTFACE_CENTER) && cube.get(FRONTFACE_MIDRIGHT) == cube.get(BACKFACE_CENTER) && cube.get(RIGHTFACE_MIDLEFT) == cube.get(LEFTFACE_CENTER) && cube.get(BACKFACE_MIDRIGHT) == cube.get(BACKFACE_CENTER) && cube.get(LEFTFACE_MIDLEFT) == cube.get(RIGHTFACE_CENTER)) {
                    RotateFaces("RRuDBB");
                    return true;
                }
                TurnCube(1);
            }
            TiltCube(2);
        }
        return false;
    }

    private boolean ExchangeMiddleCenters() {
        boolean exchangeCenters = true;
        for (int i = 0; i < 4; i++) {
            if (cube.get(FRONTFACE_CENTER) != cube.get(BACKFACE_MIDLEFT) || cube.get(FRONTFACE_CENTER) != cube.get(BACKFACE_MIDRIGHT)) {
                exchangeCenters = false;
                break;
            }
            TurnCube(1);
        }
        if (exchangeCenters) {
            RotateFaces("LLRRuDFFBB");
        }
        return exchangeCenters;
    }

    private boolean ExchangeMiddleCorners() {
        for (int i = 0; i < 2; i++) {
            if (cube.get(FRONTFACE_MIDLEFT) == cube.get(BACKFACE_CENTER) && cube.get(FRONTFACE_MIDRIGHT) == cube.get(BACKFACE_CENTER) && cube.get(BACKFACE_MIDLEFT) == cube.get(FRONTFACE_CENTER) && cube.get(BACKFACE_MIDRIGHT) == cube.get(FRONTFACE_CENTER) && cube.get(LEFTFACE_MIDLEFT) == cube.get(LEFTFACE_CENTER) && cube.get(LEFTFACE_MIDRIGHT) == cube.get(LEFTFACE_CENTER)) {
                RotateFaces("RRUUddLL");
                return true;
            }
            TurnCube(1);
        }
        return false;
    }

    //Step 6 Position middle edges
    private void PositionMiddleEdges() {
        if (!Rotate3MiddleEdges()) {
            if (!ExchangeMiddleCenters()) {
                ExchangeMiddleCorners();
            }
        }
        while (cube.get(FRONTFACE_UPPERMID) != cube.get(FRONTFACE_CENTER)) {
            RotateFaces("U");
        }
        while (cube.get(FRONTFACE_DOWNMID) != cube.get(FRONTFACE_CENTER)) {
            RotateFaces("D");
        }
    }

    private void Optimize() {
        char move;
        int count;
        int pos;
        int optcount;
        optcount = movesCount;
        do {
            twists = 0;
            movesCount = optcount;
            moves[movesCount] = 0;
            optcount = 0;
            pos = 0;
            while (pos < movesCount) {
                move = moves[pos];
                count = 1;
                while (moves[++pos] == move) {
                    count++;
                }
                count = count % 4;
                for (int i = 0; i < count; i++) {
                    moves[optcount++] = move;
                }
                twists++;
            }
        } while (optcount < movesCount);
        movesCount = optcount;
        moves[movesCount] = 0;
    }

    //*****************************************************************************

    private void LoadCube(final String facelets) {
        cube = new Cube(facelets);
        movesCount = 0;
    }

    private void SaveSolution() {
        for (int i = 0; i < movesCount; i++) {
            solution[i] = moves[i];
        }
        solutionCount = movesCount;
        solutionTwists = twists;
    }

    private void Solve(final Color color, final Color opposite) {
        OrientAllCorners(color, opposite);
        SplitCorners(color, opposite);
        PositionAllCorners();
        SolveTopAndBottomEdges();
        OrientMiddleEdges();
        PositionMiddleEdges();
        Optimize();
    }

//    private boolean SolveCube(final String facelets) {
//        System.out.println("SOLVING");
//        System.out.print("Solution 1 = ");
//        LoadCube(facelets);
//        Solve('W','Y');
//        System.out.println(twists);
//        SaveSolution();
//
//        System.out.print("Solution 2 = ");
//        LoadCube(facelets);
//        Solve('R','O');
//        System.out.println(twists);
//        if (twists < solutionTwists) {
//            SaveSolution();
//        }
//
//        System.out.print("Solution 3 = ");
//        LoadCube(facelets);
//        Solve('G','B');
//        System.out.println(twists);
//        if (twists < solutionTwists) {
//            SaveSolution();
//        }
//        return true;
//    }

    private boolean SolveCube(final String facelets) {
        LCD.clearDisplay();
        LCD.drawString("SOLVING", 20, 1);
        LCD.drawString("Solution 1 =", 0, 3);
        LoadCube(facelets);
        Solve(Color.WHITE, Color.YELLOW);
        LCD.drawInt(twists, 13, 3);
        SaveSolution();

        LCD.drawString("Solution 2 =", 0, 4);
        LoadCube(facelets);
        Solve(Color.RED, Color.ORANGE);
        LCD.drawInt(twists, 13, 4);
        if (twists < solutionTwists) {
            SaveSolution();
        }

        LCD.drawString("Solution 3 =", 0, 5);
        LoadCube(facelets);
        Solve(Color.GREEN, Color.BLUE);
        LCD.drawInt(twists, 13, 5);
        if (twists < solutionTwists) {
            SaveSolution();
        }
        return true;
    }

    private char rotateBack(final String facelets, final char move) {
        if (facelets.charAt(LEFTFACE_CENTER) == move) {
            return 'L';
        }
        if (facelets.charAt(FRONTFACE_CENTER) == move) {
            return 'F';
        }
        if (facelets.charAt(RIGHTFACE_CENTER) == move) {
            return 'R';
        }
        if (facelets.charAt(BACKFACE_CENTER) == move) {
            return 'B';
        }
        if (facelets.charAt(UPPERFACE_CENTER) == move) {
            return 'U';
        }
        if (facelets.charAt(DOWNFACE_CENTER) == move) {
            return 'D';
        }
        return ' ';
    }

    @Override
    public String solution(String facelets) {
        if (facelets.length() != 6 * 9) {
            return null;
        }
        SolveCube(facelets);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < solutionCount; i++) {
            sb.append(rotateBack(facelets, solution[i]));
        }
        return sb.toString();
    }
}
