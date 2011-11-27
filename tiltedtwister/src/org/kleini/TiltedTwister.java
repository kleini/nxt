/**
 * GPL v3
 */

package org.kleini;

import lejos.nxt.LCD;

/**
 *
 *
 * @author <a href="mailto:m@kleini.org">Marcus Klein</a>
 */
public class TiltedTwister implements Solver {

    private static final int LEFTFACE = 0;
    private static final int FRONTFACE = 1;
    private static final int RIGHTFACE = 2;
    private static final int BACKFACE = 3;
    private static final int UPPERFACE = 4;
    private static final int DOWNFACE = 5;

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

//    #define SENSORPOS_HOME      50
//    #define SENSORPOS_CENTER    150
//    #define SENSORPOS_CORNERS   177
//    #define SENSORPOS_EDGES     172
//    #define SENSORPOS_LASTEDGE  130

//    #define OFFSET_EDGE       35
//    #define OFFSET_LASTEDGE   200

//    #define TILTREST  800   // Time (ms) to rest before return tilterarm
//    #define SCANSPEED 40

    private static final int MAXMOVES = 300;

//    #define BT_CONN 2
//    #define INBOX 5
//    #define OUTBOX 1

//    #define REMOTE_MAXTIME 10000

//    #define REMOTESOLVE_OK      0
//    #define REMOTESOLVE_ERROR   1
//    #define REMOTESOLVE_ABSENT  2

//    int staticOrientations[]={
//    LEFTFACE,FRONTFACE,RIGHTFACE,BACKFACE,UPPERFACE,DOWNFACE,
//    FRONTFACE,RIGHTFACE,BACKFACE,LEFTFACE,UPPERFACE,DOWNFACE,
//    RIGHTFACE,BACKFACE,LEFTFACE,FRONTFACE,UPPERFACE,DOWNFACE,
//    BACKFACE,LEFTFACE,FRONTFACE,RIGHTFACE,UPPERFACE,DOWNFACE,

//    DOWNFACE,FRONTFACE,UPPERFACE,BACKFACE,LEFTFACE,RIGHTFACE,
//    FRONTFACE,UPPERFACE,BACKFACE,DOWNFACE,LEFTFACE,RIGHTFACE,
//    UPPERFACE,BACKFACE,DOWNFACE,FRONTFACE,LEFTFACE,RIGHTFACE,
//    BACKFACE,DOWNFACE,FRONTFACE,UPPERFACE,LEFTFACE,RIGHTFACE,

//    LEFTFACE,DOWNFACE,RIGHTFACE,UPPERFACE,FRONTFACE,BACKFACE,
//    DOWNFACE,RIGHTFACE,UPPERFACE,LEFTFACE,FRONTFACE,BACKFACE,
//    RIGHTFACE,UPPERFACE,LEFTFACE,DOWNFACE,FRONTFACE,BACKFACE,
//    UPPERFACE,LEFTFACE,DOWNFACE,RIGHTFACE,FRONTFACE,BACKFACE,

//    FRONTFACE,DOWNFACE,BACKFACE,UPPERFACE,RIGHTFACE,LEFTFACE,
//    DOWNFACE,BACKFACE,UPPERFACE,FRONTFACE,RIGHTFACE,LEFTFACE,
//    BACKFACE,UPPERFACE,FRONTFACE,DOWNFACE,RIGHTFACE,LEFTFACE,
//    UPPERFACE,FRONTFACE,DOWNFACE,BACKFACE,RIGHTFACE,LEFTFACE,

//    LEFTFACE,UPPERFACE,RIGHTFACE,DOWNFACE,BACKFACE,FRONTFACE,
//    UPPERFACE,RIGHTFACE,DOWNFACE,LEFTFACE,BACKFACE,FRONTFACE,
//    RIGHTFACE,DOWNFACE,LEFTFACE,UPPERFACE,BACKFACE,FRONTFACE,
//    DOWNFACE,LEFTFACE,UPPERFACE,RIGHTFACE,BACKFACE,FRONTFACE,

//    LEFTFACE,BACKFACE,RIGHTFACE,FRONTFACE,DOWNFACE,UPPERFACE,
//    BACKFACE,RIGHTFACE,FRONTFACE,LEFTFACE,DOWNFACE,UPPERFACE,
//    RIGHTFACE,FRONTFACE,LEFTFACE,BACKFACE,DOWNFACE,UPPERFACE,
//    FRONTFACE,LEFTFACE,BACKFACE,RIGHTFACE,DOWNFACE,UPPERFACE};

//    int staticCorners[]={
//    UPPERFACE_UPPERLEFT,LEFTFACE_UPPERLEFT,BACKFACE_UPPERRIGHT,
//    UPPERFACE_UPPERRIGHT,BACKFACE_UPPERLEFT,RIGHTFACE_UPPERRIGHT,
//    UPPERFACE_DOWNLEFT,FRONTFACE_UPPERLEFT,LEFTFACE_UPPERRIGHT,
//    UPPERFACE_DOWNRIGHT,RIGHTFACE_UPPERLEFT,FRONTFACE_UPPERRIGHT,
    // DOWNFACE_UPPERLEFT,LEFTFACE_DOWNRIGHT,FRONTFACE_DOWNLEFT,
//    DOWNFACE_UPPERRIGHT,FRONTFACE_DOWNRIGHT,RIGHTFACE_DOWNLEFT,
//    DOWNFACE_DOWNLEFT,BACKFACE_DOWNRIGHT,LEFTFACE_DOWNLEFT,
//    DOWNFACE_DOWNRIGHT,RIGHTFACE_DOWNRIGHT,BACKFACE_DOWNLEFT};

//    int staticEdges[]={
//    UPPERFACE_UPPERMID,BACKFACE_UPPERMID,
//    UPPERFACE_MIDLEFT,LEFTFACE_UPPERMID,
//    UPPERFACE_MIDRIGHT,RIGHTFACE_UPPERMID,
//    UPPERFACE_DOWNMID,FRONTFACE_UPPERMID,
//    LEFTFACE_MIDRIGHT,FRONTFACE_MIDLEFT,
//    FRONTFACE_MIDRIGHT,RIGHTFACE_MIDLEFT,
//    RIGHTFACE_MIDRIGHT,BACKFACE_MIDLEFT,
//    BACKFACE_MIDRIGHT,LEFTFACE_MIDLEFT,
//    DOWNFACE_UPPERMID,FRONTFACE_DOWNMID,
//    DOWNFACE_MIDLEFT,LEFTFACE_DOWNMID,
//    DOWNFACE_MIDRIGHT,RIGHTFACE_DOWNMID,
//    DOWNFACE_DOWNMID,BACKFACE_DOWNMID};

//    struct colorType
//    {
//      int colorval;
//      unsigned int rawRed;
//      unsigned int rawGreen;
//      unsigned int rawBlue;
//      unsigned int normRed;
//      unsigned int normGreen;
//      unsigned int normBlue;
//    };

//    colorType cubeColor[6*9];

    /**
     * the cube during the calculation of the solution.
     */
//    private final char[] cube = new char[6*9];
    private Cube cube;
//    private final char[] tmpCube = new char[6*9];
    private Cube tmpCube;
    private final char[] moves = new char[MAXMOVES];
    private int movesCount = 0;
    private char[] solution = new char[MAXMOVES];
    private int solutionCount;
    private int solutionTwists;
    private int twists;
//    private final char[] staticfaces = {'L','F','R','B','U','D'};
//    private final char[] faces = {'L','F','R','B','U','D'};
//    bool cubeGrabbed;
//    int  currentAngle; //Current position of turntable
//    int  tiltPower=85; //Initial tiltpower. Continuously adjusted depending on actual speed
//    int  grabPower;

//    struct rgb
//    {
//      int red;
//      int green;
//      int blue;
//    };

//    rgb refColor[6];
//    rgb sensorColor[6*9];
//    unsigned long costMatrix[12*12];
//    int twistMatrix[12*12];

    /** Found colors on the cube */ 
    private final char[] color = new char[6 * 9];

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

    private void CopyFace(final int fromFace, final int toFace) {
    	final int fromFaceOffset = fromFace * 9;
    	final int toFaceOffset = toFace * 9;
    	for(int i=0; i<9; i++) {
    	    cube.setFace(toFaceOffset + i, tmpCube.getColor(fromFaceOffset + i));
    	}
    }

	private void CopyFaceClockwise(final int fromFace, final int toFace, final int turns) {
		final int fromFaceOffset = fromFace * 9;
		final int toFaceOffset = toFace * 9;
		if (turns == 1) {
		    cube.setFace(toFaceOffset + UPPERLEFT, tmpCube.getColor(fromFaceOffset + DOWNLEFT));
		    cube.setFace(toFaceOffset + UPPERMID, tmpCube.getColor(fromFaceOffset + MIDLEFT));
			cube.setFace(toFaceOffset + UPPERRIGHT, tmpCube.getColor(fromFaceOffset + UPPERLEFT));
			cube.setFace(toFaceOffset + MIDLEFT, tmpCube.getColor(fromFaceOffset + DOWNMID));
			cube.setFace(toFaceOffset + CENTER, tmpCube.getColor(fromFaceOffset + CENTER));
			cube.setFace(toFaceOffset + MIDRIGHT, tmpCube.getColor(fromFaceOffset + UPPERMID));
			cube.setFace(toFaceOffset + DOWNLEFT, tmpCube.getColor(fromFaceOffset + DOWNRIGHT));
			cube.setFace(toFaceOffset + DOWNMID, tmpCube.getColor(fromFaceOffset + MIDRIGHT));
			cube.setFace(toFaceOffset + DOWNRIGHT, tmpCube.getColor(fromFaceOffset + UPPERRIGHT));
		} else if (turns == 2) {
			cube.setFace(toFaceOffset + UPPERLEFT, tmpCube.getColor(fromFaceOffset + DOWNRIGHT));
			cube.setFace(toFaceOffset + UPPERMID, tmpCube.getColor(fromFaceOffset + DOWNMID));
			cube.setFace(toFaceOffset + UPPERRIGHT, tmpCube.getColor(fromFaceOffset + DOWNLEFT));
			cube.setFace(toFaceOffset + MIDLEFT, tmpCube.getColor(fromFaceOffset + MIDRIGHT));
			cube.setFace(toFaceOffset + CENTER, tmpCube.getColor(fromFaceOffset + CENTER));
			cube.setFace(toFaceOffset + MIDRIGHT, tmpCube.getColor(fromFaceOffset + MIDLEFT));
			cube.setFace(toFaceOffset + DOWNLEFT, tmpCube.getColor(fromFaceOffset + UPPERRIGHT));
			cube.setFace(toFaceOffset + DOWNMID, tmpCube.getColor(fromFaceOffset + UPPERMID));
			cube.setFace(toFaceOffset + DOWNRIGHT, tmpCube.getColor(fromFaceOffset + UPPERLEFT));
		} else {
			// turns == 3
			cube.setFace(toFaceOffset + UPPERLEFT, tmpCube.getColor(fromFaceOffset + UPPERRIGHT));
			cube.setFace(toFaceOffset + UPPERMID, tmpCube.getColor(fromFaceOffset + MIDRIGHT));
			cube.setFace(toFaceOffset + UPPERRIGHT, tmpCube.getColor(fromFaceOffset + DOWNRIGHT));
			cube.setFace(toFaceOffset + MIDLEFT, tmpCube.getColor(fromFaceOffset + UPPERMID));
			cube.setFace(toFaceOffset + CENTER, tmpCube.getColor(fromFaceOffset + CENTER));
			cube.setFace(toFaceOffset + MIDRIGHT, tmpCube.getColor(fromFaceOffset + DOWNMID));
			cube.setFace(toFaceOffset + DOWNLEFT, tmpCube.getColor(fromFaceOffset + UPPERLEFT));
			cube.setFace(toFaceOffset + DOWNMID, tmpCube.getColor(fromFaceOffset + MIDLEFT));
			cube.setFace(toFaceOffset + DOWNRIGHT, tmpCube.getColor(fromFaceOffset + DOWNLEFT));
		}
	}

	private void TurnCube(final int turns) {
		CopyCube();
		if (turns == 1) {
			CopyFaceClockwise(UPPERFACE, UPPERFACE, 1);
			CopyFace(LEFTFACE, BACKFACE);
			CopyFace(BACKFACE, RIGHTFACE);
			CopyFace(RIGHTFACE, FRONTFACE);
			CopyFace(FRONTFACE, LEFTFACE);
			CopyFaceClockwise(DOWNFACE, DOWNFACE, 3);
		} else if (turns == 2) {
			CopyFaceClockwise(UPPERFACE, UPPERFACE, 2);
			CopyFace(LEFTFACE, RIGHTFACE);
			CopyFace(BACKFACE, FRONTFACE);
			CopyFace(RIGHTFACE, LEFTFACE);
			CopyFace(FRONTFACE, BACKFACE);
			CopyFaceClockwise(DOWNFACE, DOWNFACE, 2);
		} else {
			// turns==3
			CopyFaceClockwise(UPPERFACE, UPPERFACE, 3);
			CopyFace(LEFTFACE, FRONTFACE);
			CopyFace(BACKFACE, LEFTFACE);
			CopyFace(RIGHTFACE, BACKFACE);
			CopyFace(FRONTFACE, RIGHTFACE);
			CopyFaceClockwise(DOWNFACE, DOWNFACE, 1);
		}
	}

    private void TiltCube(final int turns) {
        CopyCube();
        if (turns==1) {
            CopyFaceClockwise(UPPERFACE,RIGHTFACE,1);
	        CopyFaceClockwise(RIGHTFACE,DOWNFACE,1);
	        CopyFaceClockwise(DOWNFACE,LEFTFACE,1);
	        CopyFaceClockwise(LEFTFACE,UPPERFACE,1);
	        CopyFaceClockwise(FRONTFACE,FRONTFACE,1);
	        CopyFaceClockwise(BACKFACE,BACKFACE,3);
		} else if (turns == 2) {
			CopyFaceClockwise(UPPERFACE, DOWNFACE, 2);
			CopyFaceClockwise(RIGHTFACE, LEFTFACE, 2);
			CopyFaceClockwise(DOWNFACE, UPPERFACE, 2);
			CopyFaceClockwise(LEFTFACE, RIGHTFACE, 2);
			CopyFaceClockwise(FRONTFACE, FRONTFACE, 2);
			CopyFaceClockwise(BACKFACE, BACKFACE, 2);
		} else {
			// turns == 3
			CopyFaceClockwise(UPPERFACE, LEFTFACE, 3);
			CopyFaceClockwise(RIGHTFACE, UPPERFACE, 3);
			CopyFaceClockwise(DOWNFACE, RIGHTFACE, 3);
			CopyFaceClockwise(LEFTFACE, DOWNFACE, 3);
			CopyFaceClockwise(FRONTFACE, FRONTFACE, 3);
			CopyFaceClockwise(BACKFACE, BACKFACE, 1);
		}
	}

	private void TwistCube(final int turns) {
		if (movesCount + turns >= MAXMOVES) {
			Sorry();
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.exit(0);
		}
		for (int twists = 0; twists < turns; twists++) {
			CopyCube();
			CopyFaceClockwise(DOWNFACE, DOWNFACE, 1);
			for (int i = 6; i < 9; i++) {
			    cube.setFace(LEFTFACE, i, tmpCube.get(BACKFACE, i));
			    cube.setFace(FRONTFACE, i, tmpCube.get(LEFTFACE, i));
			    cube.setFace(RIGHTFACE, i, tmpCube.get(FRONTFACE, i));
			    cube.setFace(BACKFACE, i, tmpCube.get(RIGHTFACE, i));
			}
            moves[movesCount++] = cube.getColor(DOWNFACE_CENTER).getLetter();
		}
		// PlayTone(200+movesCount*20,1);
    }

    private void RotateFace(final int face, final int turns) {
        switch (face) {
        case UPPERFACE:
            TiltCube(2); TwistCube(turns); TiltCube(2); break;
        case LEFTFACE:
        	TiltCube(3); TwistCube(turns); TiltCube(1); break;
        case FRONTFACE:
        	TurnCube(1); TiltCube(3); TwistCube(turns); TiltCube(1); TurnCube(3); break;
        case RIGHTFACE:
        	TiltCube(1); TwistCube(turns); TiltCube(3); break;
        case BACKFACE:
        	TurnCube(3); TiltCube(3); TwistCube(turns); TiltCube(1); TurnCube(1); break;
        case DOWNFACE:
        	TwistCube(turns); break;
        }
    }

    private void RotateFaces(final String faces) {
        char faceturn;
        for (int i = 0; i < faces.length(); i++) {
            faceturn = faces.charAt(i);
            switch (faceturn) {
            case 'U': RotateFace(UPPERFACE, 1); break;
            case 'L': RotateFace(LEFTFACE, 1); break;
            case 'F': RotateFace(FRONTFACE, 1); break;
            case 'R': RotateFace(RIGHTFACE, 1); break;
            case 'B': RotateFace(BACKFACE, 1); break;
            case 'D': RotateFace(DOWNFACE, 1); break;
            case 'u': RotateFace(UPPERFACE, 3); break;
            case 'l': RotateFace(LEFTFACE, 3); break;
            case 'f': RotateFace(FRONTFACE, 3); break;
            case 'r': RotateFace(RIGHTFACE, 3); break;
            case 'b': RotateFace(BACKFACE, 3); break;
            case 'd': RotateFace(DOWNFACE, 3); break;
            }
        }
    }

    private boolean CornerColorOk(final int position, final Color color1, final Color color2) {
        return cube.getColor(position) == color1 || cube.getColor(position) == color2;
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
    		if (cube.getColor(DOWNFACE_UPPERLEFT) == color) {
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
    		while (cube.getColor(DOWNFACE_UPPERLEFT) != singleColor) {
    			TurnCube(1);
    		}
			while (cube.getColor(UPPERFACE_DOWNRIGHT) == singleColor) {
				RotateFaces("U");
			}
			if (cube.getColor(UPPERFACE_DOWNRIGHT) == cube.getColor(UPPERFACE_CENTER)) {
				RotateFaces("RRDLL");
			} else {
				RotateFaces("RRDRR");
			}
    	} else if (count == 2) {
			if (cube.getColor(DOWNFACE_UPPERLEFT) != cube.getColor(DOWNFACE_DOWNRIGHT)) {
				TiltCube(2);
			}
			if (cube.getColor(DOWNFACE_UPPERLEFT) != cube.getColor(DOWNFACE_DOWNRIGHT)) {
				while (cube.getColor(DOWNFACE_UPPERLEFT) != cube.getColor(DOWNFACE_UPPERRIGHT)) {
					TurnCube(1);
				}
				while (cube.getColor(UPPERFACE_UPPERLEFT) != cube.getColor(DOWNFACE_UPPERLEFT) || cube.getColor(UPPERFACE_UPPERRIGHT) != cube.getColor(DOWNFACE_UPPERRIGHT)) {
					RotateFaces("U");
				}
				if (cube.getColor(UPPERFACE_UPPERLEFT) == cube.getColor(UPPERFACE_CENTER)) {
					RotateFaces("FF");
				} else {
					RotateFaces("BB");
				}
			} else if (cube.getColor(UPPERFACE_UPPERLEFT) == cube.getColor(UPPERFACE_DOWNRIGHT)) {
				if (cube.getColor(UPPERFACE_UPPERLEFT) != cube.getColor(DOWNFACE_DOWNLEFT)) {
					RotateFaces("U");
				}
				if (cube.getColor(UPPERFACE_UPPERRIGHT) == cube.getColor(UPPERFACE_CENTER)) {
					TurnCube(1);
				}
				RotateFaces("RRDDFF");
			} else {
				while (cube.getColor(UPPERFACE_UPPERLEFT) != cube.getColor(DOWNFACE_DOWNLEFT) || cube.getColor(UPPERFACE_DOWNLEFT) != cube.getColor(DOWNFACE_DOWNLEFT)) {
					TurnCube(1);
				}
				if (cube.getColor(UPPERFACE_UPPERLEFT) != cube.getColor(UPPERFACE_CENTER)) {
					RotateFaces("RRDRRDLL");
				} else {
					RotateFaces("RRDRRDRR");
				}
			}
    	}
		if (cube.getColor(UPPERFACE_UPPERLEFT) == cube.getColor(LEFTFACE_CENTER)) {
			TiltCube(1);
		} else if (cube.getColor(UPPERFACE_UPPERLEFT) == cube.getColor(FRONTFACE_CENTER)) {
			TurnCube(1);
			TiltCube(1);
		} else if (cube.getColor(UPPERFACE_UPPERLEFT) == cube.getColor(RIGHTFACE_CENTER)) {
			TiltCube(3);
		} else if (cube.getColor(UPPERFACE_UPPERLEFT) == cube.getColor(BACKFACE_CENTER)) {
			TurnCube(3);
			TiltCube(1);
		} else if (cube.getColor(UPPERFACE_UPPERLEFT) == cube.getColor(DOWNFACE_CENTER)) {
			TiltCube(2);
		}
		while (cube.getColor(UPPERFACE_UPPERLEFT) != cube.getColor(UPPERFACE_CENTER)) {
			RotateFaces("B");
		}
		while (cube.getColor(UPPERFACE_DOWNLEFT) != cube.getColor(UPPERFACE_CENTER)) {
			RotateFaces("F");
		}
    }

    //Step 3 Position all corners
    private void PositionAllCorners() {
    	int count = 0;
    	int topCount = 0;
		int bottomCount = 0;
		for (int i = 0; i < 4; i++) {
			if (cube.getColor(BACKFACE_DOWNLEFT) == cube.getColor(BACKFACE_DOWNRIGHT)) {
				bottomCount++;
			}
			if (cube.getColor(BACKFACE_UPPERLEFT) == cube.getColor(BACKFACE_UPPERRIGHT)) {
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
			while (cube.getColor(BACKFACE_DOWNLEFT) != cube.getColor(BACKFACE_DOWNRIGHT)) {
				TurnCube(1);
			}
			RotateFaces("RuFUUfUr");
		} else if (count == 2) {
			while (cube.getColor(BACKFACE_DOWNLEFT) != cube.getColor(BACKFACE_DOWNRIGHT)) {
				TurnCube(1);
			}
			while (cube.getColor(BACKFACE_UPPERLEFT) != cube.getColor(BACKFACE_UPPERRIGHT)) {
				RotateFaces("U");
			}
			RotateFaces("RRUFFUURRURR");
		} else if (count == 4) {
			RotateFaces("FFuRurUFFURUr");
		} else if (count == 5) {
			while (cube.getColor(BACKFACE_UPPERLEFT) != cube.getColor(BACKFACE_UPPERRIGHT)) {
				TurnCube(1);
			}
			RotateFaces("RuRFFrURFFRR");
		}
    }

    private int TopEdgesSolved() {
		int solved = 0;
		if (cube.getColor(UPPERFACE_UPPERMID) == cube.getColor(UPPERFACE_CENTER) && cube.getColor(BACKFACE_UPPERMID) == cube.getColor(BACKFACE_UPPERLEFT)) {
			solved++;
		}
		if (cube.getColor(UPPERFACE_MIDLEFT) == cube.getColor(UPPERFACE_CENTER) && cube.getColor(LEFTFACE_UPPERMID) == cube.getColor(LEFTFACE_UPPERLEFT)) {
			solved++;
		}
		if (cube.getColor(UPPERFACE_MIDRIGHT) == cube.getColor(UPPERFACE_CENTER) && cube.getColor(RIGHTFACE_UPPERMID) == cube.getColor(RIGHTFACE_UPPERLEFT)) {
			solved++;
		}
		if (cube.getColor(UPPERFACE_DOWNMID) == cube.getColor(UPPERFACE_CENTER) && cube.getColor(FRONTFACE_UPPERMID) == cube.getColor(FRONTFACE_UPPERLEFT)) {
			solved++;
		}
		return solved;
    }

    private int BottomEdgesSolved() {
		int solved = 0;
		if (cube.getColor(DOWNFACE_UPPERMID) == cube.getColor(DOWNFACE_CENTER) && cube.getColor(FRONTFACE_DOWNMID) == cube.getColor(FRONTFACE_DOWNLEFT)) {
			solved++;
		}
		if (cube.getColor(DOWNFACE_MIDLEFT) == cube.getColor(DOWNFACE_CENTER) && cube.getColor(LEFTFACE_DOWNMID) == cube.getColor(LEFTFACE_DOWNLEFT)) {
			solved++;
		}
		if (cube.getColor(DOWNFACE_MIDRIGHT) == cube.getColor(DOWNFACE_CENTER) && cube.getColor(RIGHTFACE_DOWNMID) == cube.getColor(RIGHTFACE_DOWNLEFT)) {
			solved++;
		}
		if (cube.getColor(DOWNFACE_DOWNMID) == cube.getColor(DOWNFACE_CENTER) && cube.getColor(BACKFACE_DOWNMID) == cube.getColor(BACKFACE_DOWNLEFT)) {
			solved++;
		}
		return solved;
    }

	private void SetBottomFace(final int downface, final int downpos, final int sideface, final int sidepos) {
		if (cube.get(downface, downpos) == cube.getColor(DOWNFACE_CENTER)) {
			while (cube.getColor(RIGHTFACE_DOWNLEFT) != cube.get(sideface, sidepos)) {
				RotateFaces("D");
			}
		} else {
			for (int i = 0; i < 4; i++) {
				if (cube.getColor(DOWNFACE_MIDRIGHT) != cube.getColor(DOWNFACE_CENTER) || cube.getColor(RIGHTFACE_DOWNMID) != cube.getColor(RIGHTFACE_DOWNLEFT)) {
					break;
				}
				RotateFaces("D");
			}
		}
	}

	private void TopEdgeMoveOut() {
		for (int i = 0; i < 4; i++) {
			if (cube.getColor(UPPERFACE_MIDRIGHT) != cube.getColor(UPPERFACE_CENTER) || cube.getColor(RIGHTFACE_UPPERMID) != cube.getColor(RIGHTFACE_UPPERLEFT)) {
				SetBottomFace(0, 0, 0, 0);
				RotateFaces("rUdF");
				return;
			}
			TurnCube(1);
		}
	}

	private boolean TopEdgeShort() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (cube.getColor(LEFTFACE_MIDRIGHT) == cube.getColor(UPPERFACE_CENTER) && cube.getColor(FRONTFACE_MIDLEFT) == cube.getColor(RIGHTFACE_UPPERLEFT)) {
					SetBottomFace(RIGHTFACE, UPPERMID, UPPERFACE, MIDRIGHT);
					RotateFaces("rUdF");
					return true;
				}
				if (cube.getColor(LEFTFACE_MIDLEFT) == cube.getColor(UPPERFACE_CENTER) && cube.getColor(BACKFACE_MIDRIGHT) == cube.getColor(RIGHTFACE_UPPERLEFT)) {
					SetBottomFace(RIGHTFACE, UPPERMID, UPPERFACE, MIDRIGHT);
					RotateFaces("RuDb");
					return true;
				}
				if (cube.getColor(FRONTFACE_MIDLEFT) == cube.getColor(UPPERFACE_CENTER) && cube.getColor(LEFTFACE_MIDRIGHT) == cube.getColor(RIGHTFACE_UPPERLEFT)) {
					SetBottomFace(BACKFACE, MIDRIGHT, LEFTFACE, MIDLEFT);
					RotateFaces("RUUddl");
					return true;
				}
				if (cube.getColor(BACKFACE_MIDRIGHT) == cube.getColor(UPPERFACE_CENTER) && cube.getColor(LEFTFACE_MIDLEFT) == cube.getColor(RIGHTFACE_UPPERLEFT)) {
					SetBottomFace(FRONTFACE, MIDLEFT, LEFTFACE, MIDRIGHT);
					RotateFaces("ruuDDL");
					return true;
				}
				if (cube.getColor(RIGHTFACE_DOWNMID) == cube.getColor(UPPERFACE_CENTER) && cube.getColor(DOWNFACE_MIDRIGHT) == cube.getColor(RIGHTFACE_UPPERLEFT)) {
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
				if (cube.getColor(DOWNFACE_MIDRIGHT) == cube.getColor(UPPERFACE_CENTER) && cube.getColor(RIGHTFACE_DOWNMID) == cube.getColor(RIGHTFACE_UPPERLEFT)) {
					SetBottomFace(BACKFACE, MIDRIGHT, LEFTFACE, MIDLEFT);
					RotateFaces("RuDBBUdR");
					return true;
				}
				if (cube.getColor(RIGHTFACE_UPPERMID) == cube.getColor(UPPERFACE_CENTER) && cube.getColor(UPPERFACE_MIDRIGHT) == cube.getColor(RIGHTFACE_UPPERLEFT)) {
					SetBottomFace(LEFTFACE, MIDRIGHT, FRONTFACE, MIDLEFT);
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
			if (cube.getColor(LEFTFACE_MIDRIGHT) == cube.getColor(UPPERFACE_CENTER) || cube.getColor(LEFTFACE_MIDLEFT) == cube.getColor(UPPERFACE_CENTER)) {
				while (cube.getColor(UPPERFACE_MIDRIGHT) == cube.getColor(UPPERFACE_CENTER)) {
					RotateFaces("U");
				}
				break;
			}
			TurnCube(1);
		}
		for (int i = 0; i < 4; i++) {
			if (cube.getColor(UPPERFACE_MIDRIGHT) != cube.getColor(UPPERFACE_CENTER) || cube.getColor(RIGHTFACE_UPPERMID) != cube.getColor(RIGHTFACE_UPPERLEFT)) {
				break;
			}
			TurnCube(1);
		}
	}

	private int TopEdgesInMiddleLayerOrientation() {
		int orientation = 0;
		if (cube.getColor(RIGHTFACE_MIDLEFT) != cube.getColor(LEFTFACE_CENTER) && cube.getColor(RIGHTFACE_MIDLEFT) != cube.getColor(RIGHTFACE_CENTER)) {
			orientation = 4;
		}
		if (cube.getColor(RIGHTFACE_UPPERMID) != cube.getColor(LEFTFACE_CENTER) && cube.getColor(RIGHTFACE_UPPERMID) != cube.getColor(RIGHTFACE_CENTER)) {
			orientation += 2;
		}
		if (cube.getColor(RIGHTFACE_MIDRIGHT) != cube.getColor(LEFTFACE_CENTER) && cube.getColor(RIGHTFACE_MIDRIGHT) != cube.getColor(RIGHTFACE_CENTER)) {
			orientation += 1;
		}
		return orientation;
    }

    private boolean MiddleEdgeTwisted(final int face, final int pos) {
        return cube.get(face, pos) != cube.getColor(FRONTFACE_CENTER) && cube.get(face, pos) != cube.getColor(BACKFACE_CENTER);
    }

    private int TwistedMiddleEdges() {
        int twisted = 0;
        for (int i = 0; i < 4; i++) {
            if (MiddleEdgeTwisted(FRONTFACE, MIDRIGHT)) {
                twisted++;
            }
            TurnCube(1);
        }
        return twisted;
    }

    //Step 5 Orient middle edges
    private void OrientMiddleEdges() {
    	PrepareMiddleEdges();
    	if (cube.getColor(LEFTFACE_MIDRIGHT) == cube.getColor(UPPERFACE_CENTER)) {
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
    		case 7: //XXX
    			RotateFaces("rUdfUdluDFFuDR"); break;
    		}
    	} else if (cube.getColor(LEFTFACE_MIDLEFT) == cube.getColor(UPPERFACE_CENTER)) {
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
			case 7: // XXX
				RotateFaces("RuDBuDLUdbbUdr"); break;
			}
		} else if (cube.getColor(RIGHTFACE_UPPERMID) == cube.getColor(UPPERFACE_CENTER)) {
            switch (TwistedMiddleEdges()) {
            case 1:
                while (!MiddleEdgeTwisted(FRONTFACE, MIDRIGHT)) {
                    TurnCube(1);
                }
                while (cube.getColor(UPPERFACE_MIDLEFT) == cube.getColor(UPPERFACE_CENTER)) {
                    RotateFaces("U");
                }
                RotateFaces("RUUrUUddLLuDfUUf");
                break;
            case 3:
                while (MiddleEdgeTwisted(FRONTFACE, MIDRIGHT))
                    TurnCube(1);
                while (cube.getColor(UPPERFACE_MIDRIGHT) == cube.getColor(UPPERFACE_CENTER))
                    RotateFaces("U");
                RotateFaces("ruDbuDluDf");
                break;
            }
        } else if (cube.getColor(UPPERFACE_MIDRIGHT) == cube.getColor(UPPERFACE_CENTER)) {
            switch (TwistedMiddleEdges()) {
            case 2:
                while (true) {
                    if (MiddleEdgeTwisted(FRONTFACE, MIDLEFT) && MiddleEdgeTwisted(FRONTFACE, MIDRIGHT)) {
                        RotateFaces("RRFlRuRRULrf");
                        return;
                    } else if (MiddleEdgeTwisted(FRONTFACE, MIDLEFT) && MiddleEdgeTwisted(BACKFACE, MIDLEFT)) {
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
                if (cube.getColor(LEFTFACE_MIDRIGHT) == cube.getColor(LEFTFACE_CENTER) && cube.getColor(FRONTFACE_MIDLEFT) == cube.getColor(FRONTFACE_CENTER) && cube.getColor(FRONTFACE_MIDRIGHT) == cube.getColor(BACKFACE_CENTER) && cube.getColor(RIGHTFACE_MIDLEFT) == cube.getColor(LEFTFACE_CENTER) && cube.getColor(BACKFACE_MIDRIGHT) == cube.getColor(BACKFACE_CENTER) && cube.getColor(LEFTFACE_MIDLEFT) == cube.getColor(RIGHTFACE_CENTER)) {
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
            if (cube.getColor(FRONTFACE_CENTER) != cube.getColor(BACKFACE_MIDLEFT) || cube.getColor(FRONTFACE_CENTER) != cube.getColor(BACKFACE_MIDRIGHT)) {
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
            if (cube.getColor(FRONTFACE_MIDLEFT) == cube.getColor(BACKFACE_CENTER) && cube.getColor(FRONTFACE_MIDRIGHT) == cube.getColor(BACKFACE_CENTER) && cube.getColor(BACKFACE_MIDLEFT) == cube.getColor(FRONTFACE_CENTER) && cube.getColor(BACKFACE_MIDRIGHT) == cube.getColor(FRONTFACE_CENTER) && cube.getColor(LEFTFACE_MIDLEFT) == cube.getColor(LEFTFACE_CENTER) && cube.getColor(LEFTFACE_MIDRIGHT) == cube.getColor(LEFTFACE_CENTER)) {
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
        while (cube.getColor(FRONTFACE_UPPERMID) != cube.getColor(FRONTFACE_CENTER)) {
            RotateFaces("U");
        }
        while (cube.getColor(FRONTFACE_DOWNMID) != cube.getColor(FRONTFACE_CENTER)) {
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
        Solve(Color.W, Color.Y);
        LCD.drawInt(twists, 13, 3);
        SaveSolution();

        LCD.drawString("Solution 2 =", 0, 4);
        LoadCube(facelets);
        Solve(Color.R, Color.O);
        LCD.drawInt(twists, 13, 4);
        if (twists < solutionTwists) {
            SaveSolution();
        }

        LCD.drawString("Solution 3 =", 0, 5);
        LoadCube(facelets);
        Solve(Color.G, Color.B);
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
        if (facelets.length() != color.length) {
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
