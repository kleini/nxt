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

//    private static final int UPPERLEFT = 0;
    private static final int UPPERMID = 1;
//    private static final int UPPERRIGHT = 2;
    private static final int MIDLEFT = 3;
//    private static final int CENTER = 4;
    private static final int MIDRIGHT = 5;
//    private static final int DOWNLEFT = 6;
//    private static final int DOWNMID = 7;
//    private static final int DOWNRIGHT = 8;

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

    private void sorry() {
        LCD.clearDisplay();
        LCD.drawString("SORRY", 5, 1); //LCD_LINE2
        LCD.drawString("Can't resolve", 5, 3);
        LCD.drawString("colors", 5, 4);
//        PlayFile("Sorry.rso");
    }

    //*****************************************************************************
    // Solve functions

    private void twistCube(final int turns) {
        if (movesCount + turns >= MAXMOVES) {
            sorry();
            Button.waitForPress();
            System.exit(0);
        }
        final String addedMoves = cube.twistCube(turns);
        for (int i = 0; i < addedMoves.length(); i++) {
            moves[movesCount++] = addedMoves.charAt(i);
        }
//        Sound.playTone(200 + movesCount * 20, 1);
    }

    private void rotateFace(final Face face, final int turns) {
        switch (face) {
        case UPPER:
            cube.tiltCube(2); twistCube(turns); cube.tiltCube(2); break;
        case LEFT:
            cube.tiltCube(3); twistCube(turns); cube.tiltCube(1); break;
        case FRONT:
            cube.turnCube(1); cube.tiltCube(3); twistCube(turns); cube.tiltCube(1); cube.turnCube(3); break;
        case RIGHT:
            cube.tiltCube(1); twistCube(turns); cube.tiltCube(3); break;
        case BACK:
            cube.turnCube(3); cube.tiltCube(3); twistCube(turns); cube.tiltCube(1); cube.turnCube(1); break;
        case DOWN:
            twistCube(turns); break;
        }
    }

    private void rotateFaces(final String faces) {
        char faceturn;
        for (int i = 0; i < faces.length(); i++) {
            faceturn = faces.charAt(i);
            switch (faceturn) {
            case 'U': rotateFace(Face.UPPER, 1); break;
            case 'L': rotateFace(Face.LEFT, 1); break;
            case 'F': rotateFace(Face.FRONT, 1); break;
            case 'R': rotateFace(Face.RIGHT, 1); break;
            case 'B': rotateFace(Face.BACK, 1); break;
            case 'D': rotateFace(Face.DOWN, 1); break;
            case 'u': rotateFace(Face.UPPER, 3); break;
            case 'l': rotateFace(Face.LEFT, 3); break;
            case 'f': rotateFace(Face.FRONT, 3); break;
            case 'r': rotateFace(Face.RIGHT, 3); break;
            case 'b': rotateFace(Face.BACK, 3); break;
            case 'd': rotateFace(Face.DOWN, 3); break;
            }
        }
    }

    private boolean cornerColorOk(final int position, final Color color1, final Color color2) {
        return cube.get(position) == color1 || cube.get(position) == color2;
    }

    boolean tryBottomFace(final Color c1, final Color c2, int twists) {
        for (int i = 0; i < 4; i++) {
            if (twists == 0) {
                if (cornerColorOk(DOWNFACE_UPPERLEFT, c1, c2) && cornerColorOk(DOWNFACE_UPPERRIGHT, c1, c2) && cornerColorOk(DOWNFACE_DOWNRIGHT, c1, c2) && !cornerColorOk(DOWNFACE_DOWNLEFT, c1, c2)) {
                    return true;
                }
            } else if (twists == 1) {
                if (cornerColorOk(DOWNFACE_UPPERLEFT, c1, c2) && cornerColorOk(DOWNFACE_UPPERRIGHT, c1, c2)) {
                    for (int j = 0; j < 4; j++) {
                        rotateFaces("B");
                        if (cornerColorOk(DOWNFACE_DOWNRIGHT, c1, c2) && !cornerColorOk(DOWNFACE_DOWNLEFT, c1, c2)) {
                            return true;
                        } else if (!cornerColorOk(DOWNFACE_DOWNRIGHT, c1, c2) && cornerColorOk(DOWNFACE_DOWNLEFT, c1, c2)) {
                            cube.turnCube(3);
                            return true;
                        } else if (cornerColorOk(DOWNFACE_DOWNRIGHT, c1, c2) && cornerColorOk(DOWNFACE_DOWNLEFT, c1, c2)) {
                            if (cornerColorOk(UPPERFACE_UPPERLEFT, c1, c2) && cornerColorOk(UPPERFACE_UPPERRIGHT, c1, c2) && cornerColorOk(UPPERFACE_DOWNLEFT, c1, c2) && cornerColorOk(UPPERFACE_DOWNRIGHT, c1, c2)) {
                                return true;
                            }
                        }
                    }
                }
            } else if (twists == 2) {
                if (cornerColorOk(DOWNFACE_UPPERLEFT, c1, c2)) {
                    for (int j = 0; j < 4; j++) {
                        rotateFaces("R");
                        if (cornerColorOk(DOWNFACE_UPPERRIGHT, c1, c2)) {
                            for (int k = 0; k < 4; k++) {
                                rotateFaces("B");
                                if (cornerColorOk(DOWNFACE_DOWNRIGHT, c1, c2) && !cornerColorOk(DOWNFACE_DOWNLEFT, c1, c2)) {
                                    return true;
                                } else if (!cornerColorOk(DOWNFACE_DOWNRIGHT, c1, c2) && cornerColorOk(DOWNFACE_DOWNLEFT, c1, c2)) {
                                    cube.turnCube(3);
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
            cube.turnCube(1);
        }
        return false;
    }


    boolean prepareBottomFace(final Color c1, final Color c2, int twists) {
        if (tryBottomFace(c1, c2, twists)) {
            return true;
        }
        cube.tiltCube(1);
        if (tryBottomFace(c1, c2, twists)) {
            return true;
        }
        cube.tiltCube(1);
        if (tryBottomFace(c1, c2, twists)) {
            return true;
        }
        cube.tiltCube(1);
        if (tryBottomFace(c1, c2, twists)) {
            return true;
        }
        cube.turnCube(1);
        cube.tiltCube(1);
        if (tryBottomFace(c1, c2, twists)) {
            return true;
        }
        cube.tiltCube(2);
        if (tryBottomFace(c1, c2, twists)) {
            return true;
        }
        return false;
    }

    private boolean moveCorners(final int corner1, final int corner2, final int corner3, final int corner4, final Color color1, final Color color2, final String moves) {
        if (cornerColorOk(corner1, color1, color2) && cornerColorOk(corner2, color1, color2) && cornerColorOk(corner3, color1, color2) && cornerColorOk(corner4, color1, color2)) {
            rotateFaces(moves);
            return true;
        }
        return false;
    }

    private void orientAllCorners(final Color c1, final Color c2) {
        if (!prepareBottomFace(c1, c2, 0)) {
            if (!prepareBottomFace(c1, c2, 1)) {
                prepareBottomFace(c1, c2, 2);
            }
        }
        if (cornerColorOk(DOWNFACE_DOWNLEFT, c1, c2)) {
            return;
        } else if (cornerColorOk(LEFTFACE_DOWNLEFT, c1, c2)) {
            for (int i = 0; i < 4; i++) {
                if (moveCorners(BACKFACE_UPPERRIGHT, RIGHTFACE_UPPERRIGHT, UPPERFACE_DOWNLEFT, UPPERFACE_DOWNRIGHT, c1, c2, "Lul")) {
                    return;
                } else if (moveCorners(BACKFACE_UPPERRIGHT, UPPERFACE_UPPERRIGHT, UPPERFACE_DOWNLEFT, FRONTFACE_UPPERRIGHT, c1, c2, "flF")) {
                    return;
                } else if (moveCorners(LEFTFACE_UPPERLEFT, UPPERFACE_UPPERRIGHT, LEFTFACE_UPPERRIGHT, RIGHTFACE_UPPERLEFT, c1, c2, "fLLF")) {
                    return;
                } else if (moveCorners(BACKFACE_UPPERRIGHT, UPPERFACE_UPPERRIGHT, FRONTFACE_UPPERLEFT, RIGHTFACE_UPPERLEFT, c1, c2, "LLDF")) {
                    return;
                } else if (moveCorners(UPPERFACE_UPPERLEFT, UPPERFACE_UPPERRIGHT, FRONTFACE_UPPERLEFT, UPPERFACE_DOWNRIGHT, c1, c2, "LfLf")) {
                    return;
                } else if (moveCorners(LEFTFACE_UPPERLEFT, BACKFACE_UPPERLEFT, LEFTFACE_UPPERRIGHT, UPPERFACE_DOWNRIGHT, c1, c2, "bDDLdl")) {
                    return;
                } else if (moveCorners(BACKFACE_UPPERRIGHT, RIGHTFACE_UPPERRIGHT, FRONTFACE_UPPERLEFT, FRONTFACE_UPPERRIGHT, c1, c2, "fLfDDb")) {
                    return;
                } else if (moveCorners(LEFTFACE_UPPERLEFT, BACKFACE_UPPERLEFT, FRONTFACE_UPPERLEFT, RIGHTFACE_UPPERLEFT, c1, c2, "lULfLLF")) {
                    return;
                }
                rotateFaces("U");
            }
        } else if (cornerColorOk(BACKFACE_DOWNRIGHT, c1, c2)) {
            for (int i = 0; i < 4; i++) {
                if (moveCorners(LEFTFACE_UPPERLEFT, UPPERFACE_UPPERRIGHT, FRONTFACE_UPPERLEFT, UPPERFACE_DOWNRIGHT, c1, c2, "bUB")) {
                    return;
                } else if (moveCorners(LEFTFACE_UPPERLEFT, UPPERFACE_UPPERRIGHT, UPPERFACE_DOWNLEFT, RIGHTFACE_UPPERLEFT, c1, c2, "LDF")) {
                    return;
                } else if (moveCorners(BACKFACE_UPPERRIGHT, BACKFACE_UPPERLEFT, UPPERFACE_DOWNLEFT, FRONTFACE_UPPERRIGHT, c1, c2, "RBBr")) {
                    return;
                } else if (moveCorners(LEFTFACE_UPPERLEFT, RIGHTFACE_UPPERRIGHT, UPPERFACE_DOWNLEFT, FRONTFACE_UPPERRIGHT, c1, c2, "FFdB")) {
                    return;
                } else if (moveCorners(UPPERFACE_UPPERLEFT, RIGHTFACE_UPPERRIGHT, UPPERFACE_DOWNLEFT, UPPERFACE_DOWNRIGHT, c1, c2, "bRbr")) {
                    return;
                } else if (moveCorners(BACKFACE_UPPERRIGHT, BACKFACE_UPPERLEFT, LEFTFACE_UPPERRIGHT, UPPERFACE_DOWNRIGHT, c1, c2, "LUUfDF")) {
                    return;
                } else if (moveCorners(LEFTFACE_UPPERLEFT, RIGHTFACE_UPPERRIGHT, FRONTFACE_UPPERLEFT, RIGHTFACE_UPPERLEFT, c1, c2, "RbRDDL")) {
                    return;
                } else if (moveCorners(BACKFACE_UPPERRIGHT, RIGHTFACE_UPPERRIGHT, LEFTFACE_UPPERRIGHT, FRONTFACE_UPPERRIGHT, c1, c2, "FluRUUR")) {
                    return;
                }
                rotateFaces("U");
            }
        }
    }

    private void splitCorners(final Color color, final Color opposite) {
        int count = 0;
        for (int i = 0; i < 4; i++) {
            if (cube.get(DOWNFACE_UPPERLEFT) == color) {
                count++;
            }
            cube.turnCube(1);
        }
        if (count == 1 || count == 3) {
            Color singleColor;
            if (count == 1) {
                singleColor = color;
            } else {
                singleColor = opposite;
            }
            while (cube.get(DOWNFACE_UPPERLEFT) != singleColor) {
                cube.turnCube(1);
            }
            while (cube.get(UPPERFACE_DOWNRIGHT) == singleColor) {
                rotateFaces("U");
            }
            if (cube.get(UPPERFACE_DOWNRIGHT) == cube.get(UPPERFACE_CENTER)) {
                rotateFaces("RRDLL");
            } else {
                rotateFaces("RRDRR");
            }
        } else if (count == 2) {
            if (cube.get(DOWNFACE_UPPERLEFT) != cube.get(DOWNFACE_DOWNRIGHT)) {
                cube.tiltCube(2);
            }
            if (cube.get(DOWNFACE_UPPERLEFT) != cube.get(DOWNFACE_DOWNRIGHT)) {
                while (cube.get(DOWNFACE_UPPERLEFT) != cube.get(DOWNFACE_UPPERRIGHT)) {
                    cube.turnCube(1);
                }
                while (cube.get(UPPERFACE_UPPERLEFT) != cube.get(DOWNFACE_UPPERLEFT) || cube.get(UPPERFACE_UPPERRIGHT) != cube.get(DOWNFACE_UPPERRIGHT)) {
                    rotateFaces("U");
                }
                if (cube.get(UPPERFACE_UPPERLEFT) == cube.get(UPPERFACE_CENTER)) {
                    rotateFaces("FF");
                } else {
                    rotateFaces("BB");
                }
            } else if (cube.get(UPPERFACE_UPPERLEFT) == cube.get(UPPERFACE_DOWNRIGHT)) {
                if (cube.get(UPPERFACE_UPPERLEFT) != cube.get(DOWNFACE_DOWNLEFT)) {
                    rotateFaces("U");
                }
                if (cube.get(UPPERFACE_UPPERRIGHT) == cube.get(UPPERFACE_CENTER)) {
                    cube.turnCube(1);
                }
                rotateFaces("RRDDFF");
            } else {
                while (cube.get(UPPERFACE_UPPERLEFT) != cube.get(DOWNFACE_DOWNLEFT) || cube.get(UPPERFACE_DOWNLEFT) != cube.get(DOWNFACE_DOWNLEFT)) {
                    cube.turnCube(1);
                }
                if (cube.get(UPPERFACE_UPPERLEFT) != cube.get(UPPERFACE_CENTER)) {
                    rotateFaces("RRDRRDLL");
                } else {
                    rotateFaces("RRDRRDRR");
                }
            }
        }
        if (cube.get(UPPERFACE_UPPERLEFT) == cube.get(LEFTFACE_CENTER)) {
            cube.tiltCube(1);
        } else if (cube.get(UPPERFACE_UPPERLEFT) == cube.get(FRONTFACE_CENTER)) {
            cube.turnCube(1);
            cube.tiltCube(1);
        } else if (cube.get(UPPERFACE_UPPERLEFT) == cube.get(RIGHTFACE_CENTER)) {
            cube.tiltCube(3);
        } else if (cube.get(UPPERFACE_UPPERLEFT) == cube.get(BACKFACE_CENTER)) {
            cube.turnCube(3);
            cube.tiltCube(1);
        } else if (cube.get(UPPERFACE_UPPERLEFT) == cube.get(DOWNFACE_CENTER)) {
            cube.tiltCube(2);
        }
        while (cube.get(UPPERFACE_UPPERLEFT) != cube.get(UPPERFACE_CENTER)) {
            rotateFaces("B");
        }
        while (cube.get(UPPERFACE_DOWNLEFT) != cube.get(UPPERFACE_CENTER)) {
            rotateFaces("F");
        }
    }

    //Step 3 Position all corners
    private void positionAllCorners() {
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
            cube.turnCube(1);
        }
        if (topCount > bottomCount) {
            cube.tiltCube(2);
        }
        count = topCount + bottomCount;
        if (count == 0) {
            rotateFaces("RRFFRR");
        } else if (count == 1) {
            while (cube.get(BACKFACE_DOWNLEFT) != cube.get(BACKFACE_DOWNRIGHT)) {
                cube.turnCube(1);
            }
            rotateFaces("RuFUUfUr");
        } else if (count == 2) {
            while (cube.get(BACKFACE_DOWNLEFT) != cube.get(BACKFACE_DOWNRIGHT)) {
                cube.turnCube(1);
            }
            while (cube.get(BACKFACE_UPPERLEFT) != cube.get(BACKFACE_UPPERRIGHT)) {
                rotateFaces("U");
            }
            rotateFaces("RRUFFUURRURR");
        } else if (count == 4) {
            rotateFaces("FFuRurUFFURUr");
        } else if (count == 5) {
            while (cube.get(BACKFACE_UPPERLEFT) != cube.get(BACKFACE_UPPERRIGHT)) {
                cube.turnCube(1);
            }
            rotateFaces("RuRFFrURFFRR");
        }
    }

    private int topEdgesSolved() {
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

    private int bottomEdgesSolved() {
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

    private void setBottomFace(final Face downface, final int downpos, final Face sideface, final int sidepos) {
        if (cube.get(downface, downpos) == cube.get(DOWNFACE_CENTER)) {
            while (cube.get(RIGHTFACE_DOWNLEFT) != cube.get(sideface, sidepos)) {
                rotateFaces("D");
            }
        } else {
            for (int i = 0; i < 4; i++) {
                if (cube.get(DOWNFACE_MIDRIGHT) != cube.get(DOWNFACE_CENTER) || cube.get(RIGHTFACE_DOWNMID) != cube.get(RIGHTFACE_DOWNLEFT)) {
                    break;
                }
                rotateFaces("D");
            }
        }
    }

    private void topEdgeMoveOut() {
        for (int i = 0; i < 4; i++) {
            if (cube.get(UPPERFACE_MIDRIGHT) != cube.get(UPPERFACE_CENTER) || cube.get(RIGHTFACE_UPPERMID) != cube.get(RIGHTFACE_UPPERLEFT)) {
                setBottomFace(Face.LEFT, 0, Face.LEFT, 0);
                rotateFaces("rUdF");
                return;
            }
            cube.turnCube(1);
        }
    }

    private boolean topEdgeShort() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (cube.get(LEFTFACE_MIDRIGHT) == cube.get(UPPERFACE_CENTER) && cube.get(FRONTFACE_MIDLEFT) == cube.get(RIGHTFACE_UPPERLEFT)) {
                    setBottomFace(Face.RIGHT, UPPERMID, Face.UPPER, MIDRIGHT);
                    rotateFaces("rUdF");
                    return true;
                }
                if (cube.get(LEFTFACE_MIDLEFT) == cube.get(UPPERFACE_CENTER) && cube.get(BACKFACE_MIDRIGHT) == cube.get(RIGHTFACE_UPPERLEFT)) {
                    setBottomFace(Face.RIGHT, UPPERMID, Face.UPPER, MIDRIGHT);
                    rotateFaces("RuDb");
                    return true;
                }
                if (cube.get(FRONTFACE_MIDLEFT) == cube.get(UPPERFACE_CENTER) && cube.get(LEFTFACE_MIDRIGHT) == cube.get(RIGHTFACE_UPPERLEFT)) {
                    setBottomFace(Face.BACK, MIDRIGHT, Face.LEFT, MIDLEFT);
                    rotateFaces("RUUddl");
                    return true;
                }
                if (cube.get(BACKFACE_MIDRIGHT) == cube.get(UPPERFACE_CENTER) && cube.get(LEFTFACE_MIDLEFT) == cube.get(RIGHTFACE_UPPERLEFT)) {
                    setBottomFace(Face.FRONT, MIDLEFT, Face.LEFT, MIDRIGHT);
                    rotateFaces("ruuDDL");
                    return true;
                }
                if (cube.get(RIGHTFACE_DOWNMID) == cube.get(UPPERFACE_CENTER) && cube.get(DOWNFACE_MIDRIGHT) == cube.get(RIGHTFACE_UPPERLEFT)) {
                    rotateFaces("RUdf");
                    return true;
                }
                cube.turnCube(1);
            }
            rotateFaces("U");
        }
        return false;
    }

    private boolean topEdgeLong() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (cube.get(DOWNFACE_MIDRIGHT) == cube.get(UPPERFACE_CENTER) && cube.get(RIGHTFACE_DOWNMID) == cube.get(RIGHTFACE_UPPERLEFT)) {
                    setBottomFace(Face.BACK, MIDRIGHT, Face.LEFT, MIDLEFT);
                    rotateFaces("RuDBBUdR");
                    return true;
                }
                if (cube.get(RIGHTFACE_UPPERMID) == cube.get(UPPERFACE_CENTER) && cube.get(UPPERFACE_MIDRIGHT) == cube.get(RIGHTFACE_UPPERLEFT)) {
                    setBottomFace(Face.LEFT, MIDRIGHT, Face.FRONT, MIDLEFT);
                    rotateFaces("ruDBBuuDDf");
                    return true;
                }
                cube.turnCube(1);
            }
            rotateFaces("U");
        }
        return false;
    }

    //Step 4 Solve top and bottom edges
    private void solveTopAndBottomEdges() {
        int topEdgesSolved;
        int bottomEdgesSolved;
        while (true) {
            topEdgesSolved = topEdgesSolved();
            bottomEdgesSolved = bottomEdgesSolved();
            if (topEdgesSolved + bottomEdgesSolved >= 7) {
                break;
            }
            if (topEdgesSolved < 3 || bottomEdgesSolved == 3) {
                if (topEdgeShort()) {
                    continue;
                }
            }
            if (bottomEdgesSolved < 3 || topEdgesSolved == 3) {
                cube.tiltCube(2);
                if (topEdgeShort()) {
                    continue;
                }
            }
            if (topEdgesSolved < 3 || bottomEdgesSolved == 3) {
                if (topEdgeLong()) {
                    continue;
                }
            }
            if (bottomEdgesSolved < 3 || topEdgesSolved == 3) {
                cube.tiltCube(2);
                if (topEdgeLong()) {
                    continue;
                }
            }
            if (topEdgesSolved >= 3) {
                cube.tiltCube(2);
            }
            topEdgeMoveOut(); // If two edges are swapped on upperface
        }
        if (bottomEdgesSolved < 4) {
            cube.tiltCube(2);
        }
    }

    private void prepareMiddleEdges() {
        for (int i = 0; i < 4; i++) {
            if (cube.get(LEFTFACE_MIDRIGHT) == cube.get(UPPERFACE_CENTER) || cube.get(LEFTFACE_MIDLEFT) == cube.get(UPPERFACE_CENTER)) {
                while (cube.get(UPPERFACE_MIDRIGHT) == cube.get(UPPERFACE_CENTER)) {
                    rotateFaces("U");
                }
                break;
            }
            cube.turnCube(1);
        }
        for (int i = 0; i < 4; i++) {
            if (cube.get(UPPERFACE_MIDRIGHT) != cube.get(UPPERFACE_CENTER) || cube.get(RIGHTFACE_UPPERMID) != cube.get(RIGHTFACE_UPPERLEFT)) {
                break;
            }
            cube.turnCube(1);
        }
    }

    private int topEdgesInMiddleLayerOrientation() {
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

    private boolean middleEdgeTwisted(final Face face, final int pos) {
        return cube.get(face, pos) != cube.get(FRONTFACE_CENTER) && cube.get(face, pos) != cube.get(BACKFACE_CENTER);
    }

    private int twistedMiddleEdges() {
        int twisted = 0;
        for (int i = 0; i < 4; i++) {
            if (middleEdgeTwisted(Face.FRONT, MIDRIGHT)) {
                twisted++;
            }
            cube.turnCube(1);
        }
        return twisted;
    }

    //Step 5 Orient middle edges
    private void orientMiddleEdges() {
        prepareMiddleEdges();
        if (cube.get(LEFTFACE_MIDRIGHT) == cube.get(UPPERFACE_CENTER)) {
            switch (topEdgesInMiddleLayerOrientation()) {
            case 0: // O O O
                rotateFaces("UdFUdluDfUdL"); break;
            case 1: // O O X
                rotateFaces("UdfuDrUdfuDr"); break;
            case 2: // O X O
                rotateFaces("uDBUdRRUdF"); break;
            case 3: // O X X
                rotateFaces("ruDBUdrUdF"); break;
            case 4: // X O O
                rotateFaces("RRUdFuDRUdFuDr"); break;
            case 5: // X O X
                rotateFaces("rUdfuDruDBUdRuDB"); break;
            case 6: // X X O
                rotateFaces("ruDbUdRUdF"); break;
            case 7: // X X X
                rotateFaces("rUdfUdluDFFuDR"); break;
            }
        } else if (cube.get(LEFTFACE_MIDLEFT) == cube.get(UPPERFACE_CENTER)) {
            switch (topEdgesInMiddleLayerOrientation()) {
            case 0: // O O O
                rotateFaces("uDbuDLUdBuDl"); break;
            case 1: // O O X
                rotateFaces("rruDbUdruDbUdR"); break;
            case 2: // O X O
                rotateFaces("UdfuDrruDb"); break;
            case 3: // O X X
                rotateFaces("RUdFuDruDb"); break;
            case 4: // X O O
                rotateFaces("uDBUdRuDBUdR"); break;
            case 5: // X O X
                rotateFaces("RuDBUdRUdfuDrUdf"); break;
            case 6: // X X O
                rotateFaces("RUdfuDRuDb"); break;
            case 7: // X X X
                rotateFaces("RuDBuDLUdbbUdr"); break;
            }
        } else if (cube.get(RIGHTFACE_UPPERMID) == cube.get(UPPERFACE_CENTER)) {
            switch (twistedMiddleEdges()) {
            case 1:
                while (!middleEdgeTwisted(Face.FRONT, MIDRIGHT)) {
                    cube.turnCube(1);
                }
                while (cube.get(UPPERFACE_MIDLEFT) == cube.get(UPPERFACE_CENTER)) {
                    rotateFaces("U");
                }
                rotateFaces("RUUrUUddLLuDfUUf");
                break;
            case 3:
                while (middleEdgeTwisted(Face.FRONT, MIDRIGHT))
                    cube.turnCube(1);
                while (cube.get(UPPERFACE_MIDRIGHT) == cube.get(UPPERFACE_CENTER))
                    rotateFaces("U");
                rotateFaces("ruDbuDluDf");
                break;
            }
        } else if (cube.get(UPPERFACE_MIDRIGHT) == cube.get(UPPERFACE_CENTER)) {
            switch (twistedMiddleEdges()) {
            case 2:
                while (true) {
                    if (middleEdgeTwisted(Face.FRONT, MIDLEFT) && middleEdgeTwisted(Face.FRONT, MIDRIGHT)) {
                        rotateFaces("RRFlRuRRULrf");
                        return;
                    } else if (middleEdgeTwisted(Face.FRONT, MIDLEFT) && middleEdgeTwisted(Face.BACK, MIDLEFT)) {
                        rotateFaces("FlRuRRULrfRR");
                        return;
                    }
                    cube.turnCube(1);
                }
            case 4:
                rotateFaces("RFFRRUdFUUddBRRBBUdR");
                break;
            }
        }
    }

    private boolean rotate3MiddleEdges() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                if (cube.get(LEFTFACE_MIDRIGHT) == cube.get(LEFTFACE_CENTER) && cube.get(FRONTFACE_MIDLEFT) == cube.get(FRONTFACE_CENTER) && cube.get(FRONTFACE_MIDRIGHT) == cube.get(BACKFACE_CENTER) && cube.get(RIGHTFACE_MIDLEFT) == cube.get(LEFTFACE_CENTER) && cube.get(BACKFACE_MIDRIGHT) == cube.get(BACKFACE_CENTER) && cube.get(LEFTFACE_MIDLEFT) == cube.get(RIGHTFACE_CENTER)) {
                    rotateFaces("RRuDBB");
                    return true;
                }
                cube.turnCube(1);
            }
            cube.tiltCube(2);
        }
        return false;
    }

    private boolean exchangeMiddleCenters() {
        boolean exchangeCenters = true;
        for (int i = 0; i < 4; i++) {
            if (cube.get(FRONTFACE_CENTER) != cube.get(BACKFACE_MIDLEFT) || cube.get(FRONTFACE_CENTER) != cube.get(BACKFACE_MIDRIGHT)) {
                exchangeCenters = false;
                break;
            }
            cube.turnCube(1);
        }
        if (exchangeCenters) {
            rotateFaces("LLRRuDFFBB");
        }
        return exchangeCenters;
    }

    private boolean exchangeMiddleCorners() {
        for (int i = 0; i < 2; i++) {
            if (cube.get(FRONTFACE_MIDLEFT) == cube.get(BACKFACE_CENTER) && cube.get(FRONTFACE_MIDRIGHT) == cube.get(BACKFACE_CENTER) && cube.get(BACKFACE_MIDLEFT) == cube.get(FRONTFACE_CENTER) && cube.get(BACKFACE_MIDRIGHT) == cube.get(FRONTFACE_CENTER) && cube.get(LEFTFACE_MIDLEFT) == cube.get(LEFTFACE_CENTER) && cube.get(LEFTFACE_MIDRIGHT) == cube.get(LEFTFACE_CENTER)) {
                rotateFaces("RRUUddLL");
                return true;
            }
            cube.turnCube(1);
        }
        return false;
    }

    //Step 6 Position middle edges
    private void positionMiddleEdges() {
        if (!rotate3MiddleEdges()) {
            if (!exchangeMiddleCenters()) {
                exchangeMiddleCorners();
            }
        }
        while (cube.get(FRONTFACE_UPPERMID) != cube.get(FRONTFACE_CENTER)) {
            rotateFaces("U");
        }
        while (cube.get(FRONTFACE_DOWNMID) != cube.get(FRONTFACE_CENTER)) {
            rotateFaces("D");
        }
    }

    private void optimize() {
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

    private void loadCube(final String facelets) {
        cube = new Cube(facelets);
        movesCount = 0;
    }

    private void saveSolution() {
        for (int i = 0; i < movesCount; i++) {
            solution[i] = moves[i];
        }
        solutionCount = movesCount;
        solutionTwists = twists;
    }

    private void solve(final Color color, final Color opposite) {
        orientAllCorners(color, opposite);
        splitCorners(color, opposite);
        positionAllCorners();
        solveTopAndBottomEdges();
        orientMiddleEdges();
        positionMiddleEdges();
        optimize();
    }

//    private boolean solveCube(final String facelets) {
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

    private boolean solveCube(final String facelets) {
        LCD.clearDisplay();
        LCD.drawString("SOLVING", 20, 1);
        LCD.drawString("Solution 1 =", 0, 3);
        loadCube(facelets);
        solve(Color.WHITE, Color.YELLOW);
        LCD.drawInt(twists, 13, 3);
        saveSolution();

        LCD.drawString("Solution 2 =", 0, 4);
        loadCube(facelets);
        solve(Color.RED, Color.ORANGE);
        LCD.drawInt(twists, 13, 4);
        if (twists < solutionTwists) {
            saveSolution();
        }

        LCD.drawString("Solution 3 =", 0, 5);
        loadCube(facelets);
        solve(Color.GREEN, Color.BLUE);
        LCD.drawInt(twists, 13, 5);
        if (twists < solutionTwists) {
            saveSolution();
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
        solveCube(facelets);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < solutionCount; i++) {
            sb.append(rotateBack(facelets, solution[i]));
        }
        return sb.toString();
    }
}
