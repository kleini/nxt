/**
 * GPL v3
 */

package org.kleini;

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
    private final char[] cube = new char[6*9];
    private final char[] tmpCube = new char[6*9];
    private final char[] moves = new char[MAXMOVES];
    private int movesCount = 0;
    private char[] solution = new char[MAXMOVES];
    private int solutionCount;
    private int solutionTwists;
    private int twists;
    private final char[] staticfaces = {'L','F','R','B','U','D'};
    private final char[] faces = {'L','F','R','B','U','D'};
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

//    inline void ColorSet(int face, int pos,int col)
//    {
//      color[face*9+pos]=col;
//    }

//    inline void CostMatrixSet(int x, int y, unsigned long cost)
//    {
//      costMatrix[x*12+y]=cost;
//    }


//    inline unsigned long CostMatrixGet(int x, int y)
//    {
//      return costMatrix[x*12+y];
//    }


//    inline void TwistMatrixSet(int x, int y, int twist)
//    {
//      twistMatrix[x*12+y]=twist;
//    }


//    inline int TwistMatrixGet(int x, int y)
//    {
//      return twistMatrix[x*12+y];
//    }


//    inline int Tile(int face, int pos)
//    {
//      return face*9+pos;
//    }


//    inline int OrientationGet(int orientation, int face)
//    {
//      return staticOrientations[orientation * 6 + face];
//    }


//    inline int CornerGet(int corner, int side)
//    {
//      return staticCorners[corner * 3 + side];
//    }


//    inline int EdgeGet(int edge, int side)
//    {
//      return staticEdges[edge * 2 + side];
//    }


//    void Tilt()
//    {
//      long tachoPrevious,tachoNow;
//      unsigned long tick,time;
//      Coast(OUT_A);
//      Wait(200);
//      tick=CurrentTick();
//      OnFwd(OUT_B,tiltPower);
//      while(MotorRotationCount(OUT_B)<65);
//      time=CurrentTick()-tick;
//      Off(OUT_B);
//      if(time > 215)
//      {
//        tiltPower += 1 + (time-215)/10;
//        if(tiltPower>100)
//          tiltPower=100;
//      }
//      else
//        tiltPower--;
//      grabPower=tiltPower/11;
//      Wait(TILTREST);
//      OnFwd(OUT_B,-100);
//      while(MotorRotationCount(OUT_B)>40);
//      OnFwd(OUT_B,-20);
//      while(MotorTachoCount(OUT_B)>20);
//      tachoNow=MotorTachoCount(OUT_B);
//      do{
//        tachoPrevious=tachoNow;
//        Wait(10);
//        tachoNow=MotorTachoCount(OUT_B);
//      }while(tachoNow!=tachoPrevious);
//      Coast(OUT_B);
//      char uf=faces[UPPERFACE];
//      faces[UPPERFACE]=faces[LEFTFACE];
//      faces[LEFTFACE]=faces[DOWNFACE];
//      faces[DOWNFACE]=faces[RIGHTFACE];
//      faces[RIGHTFACE]=uf;
//    }


//    void GrabCube()
//    {
//      Coast(OUT_A);
//      OnFwd(OUT_B,100);
//      while(MotorRotationCount(OUT_B)<20);
//      OnFwd(OUT_B,20);
//      while(MotorRotationCount(OUT_B)<30);
//      OnFwd(OUT_B,grabPower);
//      Wait(300);
//      cubeGrabbed=true;
//    }


//    void ReleaseCube()
//    {
//      int tachoNow,tachoPrevious;
//      Off(OUT_A);
//      Off(OUT_B);
//      OnFwd(OUT_B,-100);
//      while(MotorRotationCount(OUT_B)>20);
//      OnFwd(OUT_B,-20);
//      while(MotorTachoCount(OUT_B)>20);
//      tachoNow=MotorTachoCount(OUT_B);
//      do
//      {
//        tachoPrevious=tachoNow;
//        Wait(10);
//        tachoNow=MotorTachoCount(OUT_B);
//      }while(tachoNow!=tachoPrevious);
//      Coast(OUT_B);
//      cubeGrabbed=false;
//    }


//    void TurnTo(int newAngle)
//    {
//      int direction;
//      int angle1,angle2;
//      long pwr;
//      int speed;
//      int distance;
//      if(newAngle!=currentAngle)
//      {
//        if(newAngle==0) //home
//        {
//          OnFwd(OUT_A,100);
//          do
//          {
//            angle1=MotorRotationCount(OUT_A);
//            Wait(20);
//            angle2=MotorRotationCount(OUT_A);
//            speed=abs(angle2-angle1);
//            distance=abs(newAngle-angle2);
//            if(speed>11)
//            {
//              pwr=20+distance;
//              if(pwr<100)
//                OnFwd(OUT_A,pwr);
//            }
//          }while(distance>30 || angle2!=angle1);
//          Off(OUT_A);
//          Wait(300);
//        }
//        else
//        {
//          int angle=newAngle - MotorRotationCount(OUT_A);
//          ResetTachoCount(OUT_A);
//          Wait(50);
//          RotateMotor(OUT_A,60,angle);
//          Wait(300);
//        }
//        int angle=MotorRotationCount(OUT_A);
//        if(!cubeGrabbed)
//        {
//          int num=(4+(currentAngle-newAngle)/90)%4;
//          for(int i=0;i<num;i++)
//          {
//            char lf=faces[LEFTFACE];
//            faces[LEFTFACE]=faces[FRONTFACE];
//            faces[FRONTFACE]=faces[RIGHTFACE];
//            faces[RIGHTFACE]=faces[BACKFACE];
//            faces[BACKFACE]=lf;
//          }
//        }
//        currentAngle=newAngle;
//      }
//    }


//    inline void TurnQuarter(int num)
//    {
//      TurnTo((currentAngle-num*90)%360);
//    }


//    void Twist(int num)
//    {
//      int angle=num * -90;
//      if(angle==currentAngle)
//        Wait(TILTREST);
//      else
//        TurnTo(angle);
//      GrabCube();
//      TurnTo(0);
//      ReleaseCube();
//    }


//    //*****************************************************************************
//    // Initialisation

//    void InitTurntable()
//    {
//      int tachoNow,tachoPrevious;
//      OnFwd(OUT_A,15);
//      do
//      {
//        tachoPrevious=tachoNow;
//        Wait(100);
//        tachoNow=MotorTachoCount(OUT_A);
//      }while(tachoNow!=tachoPrevious);
//      Coast(OUT_A);
//      Wait(500);
//      ResetAllTachoCounts(OUT_A);
//      Wait(500);
//      currentAngle=0;
//    }


//    void InitTilter()
//    {
//      int tachoNow,tachoPrevious;
//      OnFwd(OUT_B,-20);
//      tachoNow=MotorTachoCount(OUT_B);
//      do
//      {
//        tachoPrevious=tachoNow;
//        Wait(100);
//        tachoNow=MotorTachoCount(OUT_B);
//      }while(tachoNow!=tachoPrevious);
//      Coast(OUT_B);
//      Wait(500);
//      ResetAllTachoCounts(OUT_B);
//    }


//    void InitScanner()
//    {
//      int tachoNow,tachoPrevious;
//      OnFwd(OUT_C,-15);
//      tachoNow=MotorTachoCount(OUT_C);
//      do
//      {
//        tachoPrevious=tachoNow;
//        Wait(100);
//        tachoNow=MotorTachoCount(OUT_C);
//      }while(tachoNow!=tachoPrevious);
//      Off(OUT_C);
//      Wait(500);
//      ResetAllTachoCounts(OUT_C);
//      Wait(500);
//      RotateMotor(OUT_C,10,SENSORPOS_HOME);
//    }


//    void Initialize()
//    {
//      InitTilter();
//      InitScanner();
//      InitTurntable();
//      SetSensorLowspeed(IN_2);
//    }


//    //*****************************************************************************
//    // Scan cube

//    inline void DrawFacelet(int pos)
//    {
//      RectOut(59-(pos/3)*17,42-(pos%3)*17,15,15);
//    }


//    void ReadColor(int face, int pos)
//    {
//      int colorval;
//      unsigned int raw[];
//      unsigned int norm[];
//      int scaled[];
//      while(!ReadSensorColorEx(IN_1,colorval,raw,norm,scaled));
//      int tile=Tile(face,pos);
//      cubeColor[tile].colorval=colorval;
//      cubeColor[tile].normRed=norm[0];
//      cubeColor[tile].normGreen=norm[1];
//      cubeColor[tile].normBlue=norm[2];
//      cubeColor[tile].rawRed=raw[0];
//      cubeColor[tile].rawGreen=raw[1];
//      cubeColor[tile].rawBlue=raw[2];
//    }


//    void ScanFace(int face,int c1,int c2,int c3,int c4,int c5,int c6,int c7,int c8,int endAngle)
//    {
//      TurnTo(0);
//      ResetTachoCount(OUT_A);
//      ClearScreen();
//      RotateMotorEx(OUT_C,20,SENSORPOS_CENTER-SENSORPOS_HOME,0,false,false);
//      ReadColor(face,CENTER);
//      DrawFacelet(CENTER);
//      RotateMotor(OUT_C,20,SENSORPOS_CORNERS-SENSORPOS_CENTER);
//      ReadColor(face,c1);
//      DrawFacelet(UPPERRIGHT);
//      RotateMotorEx(OUT_A,SCANSPEED,-90,0,false,false);
//      ReadColor(face,c2);
//      DrawFacelet(DOWNRIGHT);
//      RotateMotorEx(OUT_A,SCANSPEED,-90,0,false,false);
//      ReadColor(face,c3);
//      DrawFacelet(DOWNLEFT);
//      RotateMotor(OUT_A,SCANSPEED,-90);
//      ReadColor(face,c4);
//      DrawFacelet(UPPERLEFT);
//      RotateMotor(OUT_C,10,SENSORPOS_EDGES-SENSORPOS_CORNERS);
//      RotateMotorEx(OUT_A,SCANSPEED,OFFSET_EDGE,0,false,false);
//      ReadColor(face,c5);
//      DrawFacelet(MIDLEFT);
//      RotateMotorEx(OUT_A,SCANSPEED,90,0,false,false);
//      ReadColor(face,c6);
//      DrawFacelet(UPPERMID);
//      RotateMotorEx(OUT_A,SCANSPEED,90,0,false,false);
//      ReadColor(face,c7);
//      DrawFacelet(MIDRIGHT);
//      RotateMotor(OUT_A,SCANSPEED,OFFSET_LASTEDGE - 270 - OFFSET_EDGE);
//      RotateMotorEx(OUT_C,20,SENSORPOS_LASTEDGE-SENSORPOS_EDGES,0,false,false);
//      ReadColor(face,c8);
//      DrawFacelet(DOWNMID);
//      RotateMotor(OUT_C,20,SENSORPOS_HOME-SENSORPOS_LASTEDGE);
//      currentAngle=-1;
//      TurnTo(endAngle);
//    }


//    void ScanCube()
//    {
//      SetSensorColorFull(IN_1);
//      ScanFace(BACKFACE,UPPERRIGHT,UPPERLEFT,DOWNLEFT,DOWNRIGHT,DOWNMID,MIDLEFT,UPPERMID,MIDRIGHT,0);
//      Tilt();
//      ScanFace(LEFTFACE,UPPERRIGHT,UPPERLEFT,DOWNLEFT,DOWNRIGHT,DOWNMID,MIDLEFT,UPPERMID,MIDRIGHT,0);
//      Tilt();
//      ScanFace(FRONTFACE,UPPERRIGHT,UPPERLEFT,DOWNLEFT,DOWNRIGHT,DOWNMID,MIDLEFT,UPPERMID,MIDRIGHT,-270);
//      Tilt();
//      ScanFace(DOWNFACE,UPPERRIGHT,UPPERLEFT,DOWNLEFT,DOWNRIGHT,DOWNMID,MIDLEFT,UPPERMID,MIDRIGHT,0);
//      Tilt();
//      ScanFace(RIGHTFACE,UPPERLEFT,DOWNLEFT,DOWNRIGHT,UPPERRIGHT,MIDRIGHT,DOWNMID,MIDLEFT,UPPERMID,0);
//      Tilt();
//      ScanFace(UPPERFACE,DOWNLEFT,DOWNRIGHT,UPPERRIGHT,UPPERLEFT,UPPERMID,MIDRIGHT,DOWNMID,MIDLEFT,0);
//      SetSensorType(IN_1,SENSOR_TYPE_NONE);
//    }

    //*****************************************************************************
    // Color resolving

    private void Sorry() {
    	System.out.println("SORRY"); // TextOut(5,LCD_LINE2,"SORRY",true);
    	System.out.println("Can't resolve"); // TextOut(5,LCD_LINE4,"Can't resolve" );
    	System.out.println("colors"); // TextOut(5,LCD_LINE5,"colors" );
    	// PlayFile("Sorry.rso");
    }

//    int GetRefCornerColor(rgb refColor1,rgb refColor2)
//    {
//      int pos3;
//      unsigned long minCost=$FFFFFFFF;
//      for(int corner=1;corner<8;corner++)
//      {
//        int minCostTwist;
//        for(int twist=0;twist<3;twist++)
//        {
//          int pos1 = CornerGet(corner,twist);
//          int pos2 = CornerGet(corner,(1+twist)%3);
//          rgb color1 = sensorColor[pos1];
//          rgb color2 = sensorColor[pos2];
//          unsigned long cost = ((refColor1.red - color1.red) * (refColor1.red - color1.red) + (refColor1.green - color1.green) * (refColor1.green - color1.green) + (refColor1.blue - color1.blue) * (refColor1.blue - color1.blue))/3;
//          cost += ((refColor2.red - color2.red) * (refColor2.red - color2.red) + (refColor2.green - color2.green) * (refColor2.green - color2.green) + (refColor2.blue - color2.blue) * (refColor2.blue - color2.blue))/3;
//          if(cost<minCost)
//          {
//            minCost=cost;
//            minCostTwist=twist;
//            pos3=CornerGet(corner,(2+twist)%3);
//          }
//        }
//      }
//      return pos3;
//    }


//    void InitColors()
//    {
//      for(int pos=0;pos<6*9;pos++)
//      {
//        sensorColor[pos].red=cubeColor[pos].rawRed;
//        sensorColor[pos].green=cubeColor[pos].rawGreen;
//        sensorColor[pos].blue=cubeColor[pos].rawBlue;
//      }
//      refColor[UPPERFACE]=sensorColor[UPPERFACE_UPPERLEFT];
//      refColor[LEFTFACE]=sensorColor[LEFTFACE_UPPERLEFT];
//      refColor[BACKFACE]=sensorColor[BACKFACE_UPPERRIGHT];
//      refColor[FRONTFACE]=sensorColor[GetRefCornerColor(refColor[LEFTFACE],refColor[UPPERFACE])];
//      refColor[RIGHTFACE]=sensorColor[GetRefCornerColor(refColor[UPPERFACE],refColor[BACKFACE])];
//      refColor[DOWNFACE]=sensorColor[GetRefCornerColor(refColor[BACKFACE],refColor[LEFTFACE])];
//    }


//    int CenterFit(int orientation)
//    {
//      int fit=0;
//      for(int face=0;face<6;face++)
//        if(color[Tile(face,CENTER)] == OrientationGet(orientation, face))
//          fit++;
//      return fit;
//    }


//    void ResolveCenterColors()
//    {
//      TextOut(2,LCD_LINE4,"Center colors...");
//      for(int center=0;center<6;center++)
//      {
//        unsigned long minCost=$FFFFFFFF;
//        int bestCubie;
//        for(int cubie=0;cubie<6;cubie++)
//        {
//          rgb centerColor,cubieColor;
//          centerColor = refColor[center];
//          cubieColor=sensorColor[Tile(cubie,CENTER)];
//          unsigned long cost = (centerColor.red - cubieColor.red) * (centerColor.red - cubieColor.red) + (centerColor.green - cubieColor.green) * (centerColor.green - cubieColor.green) + (centerColor.blue - cubieColor.blue) * (centerColor.blue - cubieColor.blue);
//          if(cost<minCost)
//          {
//            minCost=cost;
//            bestCubie=cubie;
//          }
//        }
//        ColorSet(center,CENTER,bestCubie);
//      }

//      int bestFit=0;
//      int bestOrientation;
//      for(int orientation=0;orientation<24;orientation++)
//      {
//        int fit=CenterFit(orientation);
//        if(fit>bestFit)
//        {
//          bestFit=fit;
//          bestOrientation=orientation;
//        }
//      }
//      rgb tmpRefColor[6];
//      ArraySubset(tmpRefColor,refColor,0,6);
//      for(int face=0;face<6;face++)
//      {
//        rgb col=tmpRefColor[face];
//        refColor[OrientationGet(bestOrientation, face)]=col;
//      }

//      for(int face=0;face<6;face++)
//        ColorSet(face,CENTER,face);
//    }


//    int BestFitCubie(int &cubie, int &twist, int dim)
//    {
//      int bestX,bestY;
//      unsigned long bestDiff=0;
//      for(int x = 0; x < dim;x++)
//      {
//        unsigned long minCost=$FFFFFFFF;
//        unsigned long min2Cost=$FFFFFFFF;
//        int minY;
//        for(int y=0; y < dim; y++)
//          if(CostMatrixGet(x,y) < minCost)
//          {
//            minCost=CostMatrixGet(x,y);
//            minY=y;
//          }
//        for(int y=0; y < dim; y++)
//          if(y != minY && CostMatrixGet(x,y) < min2Cost)
//            min2Cost=CostMatrixGet(x,y);
//        if(min2Cost-minCost > bestDiff)
//        {
//          bestX = x;
//          bestY = minY;
//          bestDiff = min2Cost-minCost;
//        }
//      }
//      for(int x=0;x<dim;x++)
//        CostMatrixSet(x,bestY,$FFFFFFFF);
//      for(int y=0;y<dim;y++)
//        CostMatrixSet(bestX,y,$FFFFFFFF);

//      cubie = bestY;
//      twist = TwistMatrixGet(bestX,bestY);
//      return bestX;
//    }


//    void ResolveCornerColors()
//    {
//      TextOut(2,LCD_LINE5,"Corner colors...");
//      for(int corner=0;corner<8;corner++)
//        for(int cubie=0;cubie<8;cubie++)
//        {
//          unsigned long minCost=$FFFFFFFF;
//          int minCostTwist;
//          for(int twist=0;twist<3;twist++)
//          {
//            unsigned long cost=0;
//            for(int s=0;s<3;s++)
//            {
//              int face = CornerGet(corner,s) / 9;
//              int pos = CornerGet(cubie,(s+twist)%3);
//              rgb color1,color2;
//              color1 = refColor[face];
//              color2 = sensorColor[pos];
//              cost += ((color1.red - color2.red) * (color1.red - color2.red) + (color1.green - color2.green) * (color1.green - color2.green) + (color1.blue - color2.blue) * (color1.blue - color2.blue))/3;
//            }
//            if(cost<minCost)
//            {
//              minCost=cost;
//              minCostTwist=twist;
//            }
//          }
//          CostMatrixSet(corner,cubie,minCost);
//          TwistMatrixSet(corner,cubie,minCostTwist);
//        }
//        for(int face=0;face<6;face++) //calibrate refColor
//        {
//          refColor[face].red=0;
//          refColor[face].green=0;
//          refColor[face].blue=0;
//        }
//        for(int i=0;i<8;i++)
//        {
//          int cubie;
//          int twist;
//          int corner=BestFitCubie(cubie,twist,8);
//          for(int s=0;s<3;s++)
//          {
//            int face = (CornerGet(corner,s))/9;
//            int pos = CornerGet(cubie,(s+twist)%3);
//            color[pos]=face;
//            //calibrate refColor
//            rgb ref=refColor[face];
//            rgb col=sensorColor[pos];
//            ref.red+=col.red;
//            ref.green+=col.green;
//            ref.blue+=col.blue;
//            refColor[face]=ref;
//          }
//        }
//        //calibrate refColor
//        for(int face = 0 ; face < 6 ;face++)
//        {
//          rgb tmp=refColor[face];
//          tmp.red/=4;
//          tmp.green/=4;
//          tmp.blue/=4;
//          refColor[face]=tmp;
//        }
//    }


//    void ResolveEdgeColors()
//    {
//      TextOut(2,LCD_LINE6,"Edge colors...");
//      for(int edge=0;edge<12;edge++)
//        for(int cubie=0;cubie<12;cubie++)
//        {
//          unsigned long minCost=$FFFFFFFF;
//          int minCostTwist;
//          for(int twist=0;twist<2;twist++)
//          {
//            unsigned long cost=0;
//            for(int s=0;s<2;s++)
//            {
//              int face = EdgeGet(edge,s)/ 9;
//              int pos = EdgeGet(cubie,(s+twist)%2);
//              rgb color1,color2;
//              color1 = refColor[face];
//              color2 = sensorColor[pos];
//              cost += ((color1.red - color2.red) * (color1.red - color2.red) + (color1.green - color2.green) * (color1.green - color2.green) + (color1.blue - color2.blue) * (color1.blue - color2.blue))/2;
//            }
//            if(cost<minCost)
//            {
//              minCost=cost;
//              minCostTwist=twist;
//            }
//          }
//          CostMatrixSet(edge,cubie,minCost);
//          TwistMatrixSet(edge,cubie,minCostTwist);
//        }
//        for(int i = 0 ; i < 12 ;i++)
//        {
//          int cubie;
//          int twist;
//          int edge=BestFitCubie(cubie,twist,12);
//          for(int s=0;s<2;s++)
//            color[EdgeGet(cubie,(s+twist)%2)]=EdgeGet(edge,s)/9;
//        }
//    }


//    string ColorToStr(int face, int pos)
//    {
//      byte c=color[Tile(face,pos)];
//      return SubStr("LFRBUD",c,1);
//    }


//    void DumpCube()
//    {
//      int handle;
//      DeleteFile("rubikscube.txt");
//      CreateFile("rubikscube.txt", 4000, handle);
//      int count;
//      string msg,c;
//      for(int row=0;row<3;row++)
//      {
//        msg="    ";
//        for(int col=0;col<3;col++)
//        {
//          c=ColorToStr(UPPERFACE,row*3+col);
//          msg=StrCat(msg,c);
//        }
//        WriteLnString(handle,msg,count);
//      }
//      WriteLnString(handle,"",count);
//      for(int row=0;row<3;row++)
//      {
//        msg="";
//        for(int face=LEFTFACE;face<=BACKFACE;face++)
//        {
//          for(int col=0;col<3;col++)
//          {
//            c=ColorToStr(face,row*3+col);
//            msg=StrCat(msg,c);
//          }
//          msg=StrCat(msg," ");
//        }
//        WriteLnString(handle,msg,count);
//      }
//      WriteLnString(handle,"",count);
//      for(int row=0;row<3;row++)
//      {
//        msg="    ";
//        for(int col=0;col<3;col++)
//        {
//          c=ColorToStr(DOWNFACE,row*3+col);
//          msg=StrCat(msg,c);
//        }
//        WriteLnString(handle,msg,count);
//      }
//      WriteLnString(handle,"",count);

//      WriteLnString(handle,"colorval Raw-RGB [Norm-RGB] = Resolved face",count);
//      WriteLnString(handle,"",count);
//      for(int face=0;face<6;face++)
//      {
//        msg=SubStr("LFRBUD",face,1); WriteString(handle,msg,count); WriteString(handle,": ",count);
//        for(int pos=0;pos<9;pos++)
//        {
//          colorType col=cubeColor[Tile(face,pos)];
//          msg=NumToStr(col.colorval); WriteString(handle,msg,count); WriteString(handle," ",count);
//          msg=NumToStr(col.rawRed); WriteString(handle,msg,count); WriteString(handle,".",count);
//          msg=NumToStr(col.rawGreen); WriteString(handle,msg,count); WriteString(handle,".",count);
//          msg=NumToStr(col.rawBlue); WriteString(handle,msg,count); WriteString(handle," [",count);
//          msg=NumToStr(col.normRed); WriteString(handle,msg,count); WriteString(handle,".",count);
//          msg=NumToStr(col.normGreen); WriteString(handle,msg,count); WriteString(handle,".",count);
//          msg=NumToStr(col.normBlue); WriteString(handle,msg,count); WriteString(handle,"]=",count);
//          msg=ColorToStr(face,pos); WriteString(handle,msg,count); WriteString(handle,", ",count);
//        }
//        WriteLnString(handle,"",count);
//      }
//      WriteLnString(handle,"",count);
//      WriteLnString(handle,"Ref RGB",count);
//      WriteLnString(handle,"",count);
//      for(int face=0;face<6;face++)
//      {
//        msg=SubStr("LFRBUD",face,1); WriteString(handle,msg,count); WriteString(handle,": ",count);
//        rgb col=refColor[face];
//        msg=NumToStr(col.red); WriteString(handle,msg,count); WriteString(handle,".",count);
//        msg=NumToStr(col.green); WriteString(handle,msg,count); WriteString(handle,".",count);
//        msg=NumToStr(col.blue); WriteLnString(handle,msg,count);
//      }
//      CloseFile(handle);
//    }


//    void ResolveColors()
//    {
//      TextOut(2,LCD_LINE2,"RESOLVING COLORS",true);
//      InitColors();
//      ResolveCenterColors();
//      ResolveCornerColors();
//      ResolveEdgeColors();
//      DumpCube();
//    }

    //*****************************************************************************
    // Solve functions

    private void CubeSet(final int face, final int pos, final char value) {
    	cube[face * 9 + pos] = value;
    }

	private char CubeGet(final int face, final int pos) {
		return cube[face * 9 + pos];
	}

    private char TmpCubeGet(final int face, final int pos) {
    	return tmpCube[face * 9 + pos];
    }

    private void CopyCube() {
        System.arraycopy(cube, 0, tmpCube, 0, tmpCube.length);
    }

    private void CopyFace(final int fromFace, final int toFace) {
    	final int fromFaceOffset = fromFace * 9;
    	final int toFaceOffset = toFace * 9;
    	for(int i=0; i<9; i++) {
    		cube[toFaceOffset + i] = tmpCube[fromFaceOffset + i];
    	}
    }

	private void CopyFaceClockwise(final int fromFace, final int toFace, final int turns) {
		final int fromFaceOffset = fromFace * 9;
		final int toFaceOffset = toFace * 9;
		if (turns == 1) {
			cube[toFaceOffset + UPPERLEFT] = tmpCube[fromFaceOffset + DOWNLEFT];
			cube[toFaceOffset + UPPERMID] = tmpCube[fromFaceOffset + MIDLEFT];
			cube[toFaceOffset + UPPERRIGHT] = tmpCube[fromFaceOffset + UPPERLEFT];
			cube[toFaceOffset + MIDLEFT] = tmpCube[fromFaceOffset + DOWNMID];
			cube[toFaceOffset + CENTER] = tmpCube[fromFaceOffset + CENTER];
			cube[toFaceOffset + MIDRIGHT] = tmpCube[fromFaceOffset + UPPERMID];
			cube[toFaceOffset + DOWNLEFT] = tmpCube[fromFaceOffset + DOWNRIGHT];
			cube[toFaceOffset + DOWNMID] = tmpCube[fromFaceOffset + MIDRIGHT];
			cube[toFaceOffset + DOWNRIGHT] = tmpCube[fromFaceOffset + UPPERRIGHT];
		} else if (turns == 2) {
			cube[toFaceOffset + UPPERLEFT] = tmpCube[fromFaceOffset + DOWNRIGHT];
			cube[toFaceOffset + UPPERMID] = tmpCube[fromFaceOffset + DOWNMID];
			cube[toFaceOffset + UPPERRIGHT] = tmpCube[fromFaceOffset + DOWNLEFT];
			cube[toFaceOffset + MIDLEFT] = tmpCube[fromFaceOffset + MIDRIGHT];
			cube[toFaceOffset + CENTER] = tmpCube[fromFaceOffset + CENTER];
			cube[toFaceOffset + MIDRIGHT] = tmpCube[fromFaceOffset + MIDLEFT];
			cube[toFaceOffset + DOWNLEFT] = tmpCube[fromFaceOffset + UPPERRIGHT];
			cube[toFaceOffset + DOWNMID] = tmpCube[fromFaceOffset + UPPERMID];
			cube[toFaceOffset + DOWNRIGHT] = tmpCube[fromFaceOffset + UPPERLEFT];
		} else {
			// turns == 3
			cube[toFaceOffset + UPPERLEFT] = tmpCube[fromFaceOffset + UPPERRIGHT];
			cube[toFaceOffset + UPPERMID] = tmpCube[fromFaceOffset + MIDRIGHT];
			cube[toFaceOffset + UPPERRIGHT] = tmpCube[fromFaceOffset + DOWNRIGHT];
			cube[toFaceOffset + MIDLEFT] = tmpCube[fromFaceOffset + UPPERMID];
			cube[toFaceOffset + CENTER] = tmpCube[fromFaceOffset + CENTER];
			cube[toFaceOffset + MIDRIGHT] = tmpCube[fromFaceOffset + DOWNMID];
			cube[toFaceOffset + DOWNLEFT] = tmpCube[fromFaceOffset + UPPERLEFT];
			cube[toFaceOffset + DOWNMID] = tmpCube[fromFaceOffset + MIDLEFT];
			cube[toFaceOffset + DOWNRIGHT] = tmpCube[fromFaceOffset + DOWNLEFT];
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
				CubeSet(LEFTFACE, i, TmpCubeGet(BACKFACE, i));
				CubeSet(FRONTFACE, i, TmpCubeGet(LEFTFACE, i));
				CubeSet(RIGHTFACE, i, TmpCubeGet(FRONTFACE, i));
				CubeSet(BACKFACE, i, TmpCubeGet(RIGHTFACE, i));
			}
            moves[movesCount++] = cube[DOWNFACE_CENTER];
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

    private boolean CornerColorOk(final int position, final char c1, final char c2) {
        return cube[position] == c1 || cube[position] == c2;
    }

    boolean TryBottomFace(char c1, char c2, int twists) {
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


    boolean PrepareBottomFace(char c1, char c2, int twists) {
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

	private boolean MoveCorners(final int corner1, final int corner2, final int corner3, final int corner4, final char color1, final char color2, final String moves) {
		if (CornerColorOk(corner1, color1, color2) && CornerColorOk(corner2, color1, color2) && CornerColorOk(corner3, color1, color2) && CornerColorOk(corner4, color1, color2)) {
			RotateFaces(moves);
			return true;
		}
		return false;
	}

    private void OrientAllCorners(final char c1, final char c2) {
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

    private void SplitCorners(final char color, final char oppositeColor) {
    	int count = 0;
    	for (int i = 0; i < 4; i++) {
    		if (cube[DOWNFACE_UPPERLEFT] == color) {
    			count++;
    		}
    		TurnCube(1);
    	}
    	if (count == 1 || count == 3) {
    		int singleColor;
    		if (count == 1) {
    			singleColor = color;
    		} else {
    			singleColor = oppositeColor;
    		}
    		while (cube[DOWNFACE_UPPERLEFT] != singleColor) {
    			TurnCube(1);
    		}
			while (cube[UPPERFACE_DOWNRIGHT] == singleColor) {
				RotateFaces("U");
			}
			if (cube[UPPERFACE_DOWNRIGHT] == cube[UPPERFACE_CENTER]) {
				RotateFaces("RRDLL");
			} else {
				RotateFaces("RRDRR");
			}
    	} else if (count == 2) {
			if (cube[DOWNFACE_UPPERLEFT] != cube[DOWNFACE_DOWNRIGHT]) {
				TiltCube(2);
			}
			if (cube[DOWNFACE_UPPERLEFT] != cube[DOWNFACE_DOWNRIGHT]) {
				while (cube[DOWNFACE_UPPERLEFT] != cube[DOWNFACE_UPPERRIGHT]) {
					TurnCube(1);
				}
				while (cube[UPPERFACE_UPPERLEFT] != cube[DOWNFACE_UPPERLEFT] || cube[UPPERFACE_UPPERRIGHT] != cube[DOWNFACE_UPPERRIGHT]) {
					RotateFaces("U");
				}
				if (cube[UPPERFACE_UPPERLEFT] == cube[UPPERFACE_CENTER]) {
					RotateFaces("FF");
				} else {
					RotateFaces("BB");
				}
			} else if (cube[UPPERFACE_UPPERLEFT] == cube[UPPERFACE_DOWNRIGHT]) {
				if (cube[UPPERFACE_UPPERLEFT] != cube[DOWNFACE_DOWNLEFT]) {
					RotateFaces("U");
				}
				if (cube[UPPERFACE_UPPERRIGHT] == cube[UPPERFACE_CENTER]) {
					TurnCube(1);
				}
				RotateFaces("RRDDFF");
			} else {
				while (cube[UPPERFACE_UPPERLEFT] != cube[DOWNFACE_DOWNLEFT] || cube[UPPERFACE_DOWNLEFT] != cube[DOWNFACE_DOWNLEFT]) {
					TurnCube(1);
				}
				if (cube[UPPERFACE_UPPERLEFT] != cube[UPPERFACE_CENTER]) {
					RotateFaces("RRDRRDLL");
				} else {
					RotateFaces("RRDRRDRR");
				}
			}
    	}
		if (cube[UPPERFACE_UPPERLEFT] == cube[LEFTFACE_CENTER]) {
			TiltCube(1);
		} else if (cube[UPPERFACE_UPPERLEFT] == cube[FRONTFACE_CENTER]) {
			TurnCube(1);
			TiltCube(1);
		} else if (cube[UPPERFACE_UPPERLEFT] == cube[RIGHTFACE_CENTER]) {
			TiltCube(3);
		} else if (cube[UPPERFACE_UPPERLEFT] == cube[BACKFACE_CENTER]) {
			TurnCube(3);
			TiltCube(1);
		} else if (cube[UPPERFACE_UPPERLEFT] == cube[DOWNFACE_CENTER]) {
			TiltCube(2);
		}
		while (cube[UPPERFACE_UPPERLEFT] != cube[UPPERFACE_CENTER]) {
			RotateFaces("B");
		}
		while (cube[UPPERFACE_DOWNLEFT] != cube[UPPERFACE_CENTER]) {
			RotateFaces("F");
		}
    }

    //Step 3 Position all corners
    private void PositionAllCorners() {
    	int count = 0;
    	int topCount = 0;
		int bottomCount = 0;
		for (int i = 0; i < 4; i++) {
			if (cube[BACKFACE_DOWNLEFT] == cube[BACKFACE_DOWNRIGHT]) {
				bottomCount++;
			}
			if (cube[BACKFACE_UPPERLEFT] == cube[BACKFACE_UPPERRIGHT]) {
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
			while (cube[BACKFACE_DOWNLEFT] != cube[BACKFACE_DOWNRIGHT]) {
				TurnCube(1);
			}
			RotateFaces("RuFUUfUr");
		} else if (count == 2) {
			while (cube[BACKFACE_DOWNLEFT] != cube[BACKFACE_DOWNRIGHT]) {
				TurnCube(1);
			}
			while (cube[BACKFACE_UPPERLEFT] != cube[BACKFACE_UPPERRIGHT]) {
				RotateFaces("U");
			}
			RotateFaces("RRUFFUURRURR");
		} else if (count == 4) {
			RotateFaces("FFuRurUFFURUr");
		} else if (count == 5) {
			while (cube[BACKFACE_UPPERLEFT] != cube[BACKFACE_UPPERRIGHT]) {
				TurnCube(1);
			}
			RotateFaces("RuRFFrURFFRR");
		}
    }

    private int TopEdgesSolved() {
		int solved = 0;
		if (cube[UPPERFACE_UPPERMID] == cube[UPPERFACE_CENTER] && cube[BACKFACE_UPPERMID] == cube[BACKFACE_UPPERLEFT]) {
			solved++;
		}
		if (cube[UPPERFACE_MIDLEFT] == cube[UPPERFACE_CENTER] && cube[LEFTFACE_UPPERMID] == cube[LEFTFACE_UPPERLEFT]) {
			solved++;
		}
		if (cube[UPPERFACE_MIDRIGHT] == cube[UPPERFACE_CENTER] && cube[RIGHTFACE_UPPERMID] == cube[RIGHTFACE_UPPERLEFT]) {
			solved++;
		}
		if (cube[UPPERFACE_DOWNMID] == cube[UPPERFACE_CENTER] && cube[FRONTFACE_UPPERMID] == cube[FRONTFACE_UPPERLEFT]) {
			solved++;
		}
		return solved;
    }

    private int BottomEdgesSolved() {
		int solved = 0;
		if (cube[DOWNFACE_UPPERMID] == cube[DOWNFACE_CENTER] && cube[FRONTFACE_DOWNMID] == cube[FRONTFACE_DOWNLEFT]) {
			solved++;
		}
		if (cube[DOWNFACE_MIDLEFT] == cube[DOWNFACE_CENTER] && cube[LEFTFACE_DOWNMID] == cube[LEFTFACE_DOWNLEFT]) {
			solved++;
		}
		if (cube[DOWNFACE_MIDRIGHT] == cube[DOWNFACE_CENTER] && cube[RIGHTFACE_DOWNMID] == cube[RIGHTFACE_DOWNLEFT]) {
			solved++;
		}
		if (cube[DOWNFACE_DOWNMID] == cube[DOWNFACE_CENTER] && cube[BACKFACE_DOWNMID] == cube[BACKFACE_DOWNLEFT]) {
			solved++;
		}
		return solved;
    }

	private void SetBottomFace(final int downface, final int downpos, final int sideface, final int sidepos) {
		if (CubeGet(downface, downpos) == cube[DOWNFACE_CENTER]) {
			while (cube[RIGHTFACE_DOWNLEFT] != CubeGet(sideface, sidepos)) {
				RotateFaces("D");
			}
		} else {
			for (int i = 0; i < 4; i++) {
				if (cube[DOWNFACE_MIDRIGHT] != cube[DOWNFACE_CENTER] || cube[RIGHTFACE_DOWNMID] != cube[RIGHTFACE_DOWNLEFT]) {
					break;
				}
				RotateFaces("D");
			}
		}
	}

	private void TopEdgeMoveOut() {
		for (int i = 0; i < 4; i++) {
			if (cube[UPPERFACE_MIDRIGHT] != cube[UPPERFACE_CENTER] || cube[RIGHTFACE_UPPERMID] != cube[RIGHTFACE_UPPERLEFT]) {
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
				if (cube[LEFTFACE_MIDRIGHT] == cube[UPPERFACE_CENTER] && cube[FRONTFACE_MIDLEFT] == cube[RIGHTFACE_UPPERLEFT]) {
					SetBottomFace(RIGHTFACE, UPPERMID, UPPERFACE, MIDRIGHT);
					RotateFaces("rUdF");
					return true;
				}
				if (cube[LEFTFACE_MIDLEFT] == cube[UPPERFACE_CENTER] && cube[BACKFACE_MIDRIGHT] == cube[RIGHTFACE_UPPERLEFT]) {
					SetBottomFace(RIGHTFACE, UPPERMID, UPPERFACE, MIDRIGHT);
					RotateFaces("RuDb");
					return true;
				}
				if (cube[FRONTFACE_MIDLEFT] == cube[UPPERFACE_CENTER] && cube[LEFTFACE_MIDRIGHT] == cube[RIGHTFACE_UPPERLEFT]) {
					SetBottomFace(BACKFACE, MIDRIGHT, LEFTFACE, MIDLEFT);
					RotateFaces("RUUddl");
					return true;
				}
				if (cube[BACKFACE_MIDRIGHT] == cube[UPPERFACE_CENTER] && cube[LEFTFACE_MIDLEFT] == cube[RIGHTFACE_UPPERLEFT]) {
					SetBottomFace(FRONTFACE, MIDLEFT, LEFTFACE, MIDRIGHT);
					RotateFaces("ruuDDL");
					return true;
				}
				if (cube[RIGHTFACE_DOWNMID] == cube[UPPERFACE_CENTER] && cube[DOWNFACE_MIDRIGHT] == cube[RIGHTFACE_UPPERLEFT]) {
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
				if (cube[DOWNFACE_MIDRIGHT] == cube[UPPERFACE_CENTER] && cube[RIGHTFACE_DOWNMID] == cube[RIGHTFACE_UPPERLEFT]) {
					SetBottomFace(BACKFACE, MIDRIGHT, LEFTFACE, MIDLEFT);
					RotateFaces("RuDBBUdR");
					return true;
				}
				if (cube[RIGHTFACE_UPPERMID] == cube[UPPERFACE_CENTER] && cube[UPPERFACE_MIDRIGHT] == cube[RIGHTFACE_UPPERLEFT]) {
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
			if (cube[LEFTFACE_MIDRIGHT] == cube[UPPERFACE_CENTER] || cube[LEFTFACE_MIDLEFT] == cube[UPPERFACE_CENTER]) {
				while (cube[UPPERFACE_MIDRIGHT] == cube[UPPERFACE_CENTER]) {
					RotateFaces("U");
				}
				break;
			}
			TurnCube(1);
		}
		for (int i = 0; i < 4; i++) {
			if (cube[UPPERFACE_MIDRIGHT] != cube[UPPERFACE_CENTER] || cube[RIGHTFACE_UPPERMID] != cube[RIGHTFACE_UPPERLEFT]) {
				break;
			}
			TurnCube(1);
		}
	}

	private int TopEdgesInMiddleLayerOrientation() {
		int orientation = 0;
		if (cube[RIGHTFACE_MIDLEFT] != cube[LEFTFACE_CENTER] && cube[RIGHTFACE_MIDLEFT] != cube[RIGHTFACE_CENTER]) {
			orientation = 4;
		}
		if (cube[RIGHTFACE_UPPERMID] != cube[LEFTFACE_CENTER] && cube[RIGHTFACE_UPPERMID] != cube[RIGHTFACE_CENTER]) {
			orientation += 2;
		}
		if (cube[RIGHTFACE_MIDRIGHT] != cube[LEFTFACE_CENTER] && cube[RIGHTFACE_MIDRIGHT] != cube[RIGHTFACE_CENTER]) {
			orientation += 1;
		}
		return orientation;
    }

    private boolean MiddleEdgeTwisted(final int face, final int pos) {
        return CubeGet(face, pos) != cube[FRONTFACE_CENTER] && CubeGet(face, pos) != cube[BACKFACE_CENTER];
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
    	if (cube[LEFTFACE_MIDRIGHT] == cube[UPPERFACE_CENTER]) {
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
    	} else if (cube[LEFTFACE_MIDLEFT] == cube[UPPERFACE_CENTER]) {
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
		} else if (cube[RIGHTFACE_UPPERMID] == cube[UPPERFACE_CENTER]) {
            switch (TwistedMiddleEdges()) {
            case 1:
                while (!MiddleEdgeTwisted(FRONTFACE, MIDRIGHT)) {
                    TurnCube(1);
                }
                while (cube[UPPERFACE_MIDLEFT] == cube[UPPERFACE_CENTER]) {
                    RotateFaces("U");
                }
                RotateFaces("RUUrUUddLLuDfUUf");
                break;
            case 3:
                while (MiddleEdgeTwisted(FRONTFACE, MIDRIGHT))
                    TurnCube(1);
                while (cube[UPPERFACE_MIDRIGHT] == cube[UPPERFACE_CENTER])
                    RotateFaces("U");
                RotateFaces("ruDbuDluDf");
                break;
            }
        } else if (cube[UPPERFACE_MIDRIGHT] == cube[UPPERFACE_CENTER]) {
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
                if (cube[LEFTFACE_MIDRIGHT] == cube[LEFTFACE_CENTER] && cube[FRONTFACE_MIDLEFT] == cube[FRONTFACE_CENTER] && cube[FRONTFACE_MIDRIGHT] == cube[BACKFACE_CENTER] && cube[RIGHTFACE_MIDLEFT] == cube[LEFTFACE_CENTER] && cube[BACKFACE_MIDRIGHT] == cube[BACKFACE_CENTER] && cube[LEFTFACE_MIDLEFT] == cube[RIGHTFACE_CENTER]) {
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
            if ((cube[FRONTFACE_CENTER] != cube[BACKFACE_MIDLEFT]) || (cube[FRONTFACE_CENTER] != cube[BACKFACE_MIDRIGHT])) {
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
            if (cube[FRONTFACE_MIDLEFT] == cube[BACKFACE_CENTER] && cube[FRONTFACE_MIDRIGHT] == cube[BACKFACE_CENTER] && cube[BACKFACE_MIDLEFT] == cube[FRONTFACE_CENTER] && cube[BACKFACE_MIDRIGHT] == cube[FRONTFACE_CENTER] && cube[LEFTFACE_MIDLEFT] == cube[LEFTFACE_CENTER] && cube[LEFTFACE_MIDRIGHT] == cube[LEFTFACE_CENTER]) {
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
        while (cube[FRONTFACE_UPPERMID] != cube[FRONTFACE_CENTER]) {
            RotateFaces("U");
        }
        while (cube[FRONTFACE_DOWNMID] != cube[FRONTFACE_CENTER]) {
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

    private void SetCubeFace(final char c, final char f) {
        for (int pos = 0; pos < 6*9; pos++) {
            if (color[pos] == c) {
                cube[pos] = f;
            }
        }
    }

    private void LoadCube() {
        SetCubeFace('W', 'U');
        SetCubeFace('B', 'R');
        SetCubeFace('R', 'F');
        SetCubeFace('G', 'L');
        SetCubeFace('O', 'B');
        SetCubeFace('Y', 'D');
        for (int i = 0; i < 6; i++) {
            faces[i] = staticfaces[i];
        }
        movesCount = 0;
    }

    private void SaveSolution() {
        for (int i = 0; i < movesCount; i++) {
            solution[i] = moves[i];
        }
        solutionCount = movesCount;
        solutionTwists = twists;
    }

    private void Solve(final char color, final char oppositeColor) {
		OrientAllCorners(color, oppositeColor);
		SplitCorners(color, oppositeColor);
		PositionAllCorners();
		SolveTopAndBottomEdges();
		OrientMiddleEdges();
		PositionMiddleEdges();
		Optimize();
    }


//    int RemoteSolve()
//    {
//      if(BluetoothStatus(0)==NO_ERR)
//      {
//        TextOut(10,LCD_LINE1,"Connecting...",true);
//        LoadCube();
//        string cubeString=ByteArrayToStr(cube);
//        SendResponseString(OUTBOX,cubeString);
//        string result;
//        unsigned long tick;
//        tick=CurrentTick();
//        while(ReceiveRemoteString(INBOX, true, result) == STAT_MSG_EMPTY_MAILBOX && CurrentTick() < tick + REMOTE_MAXTIME);
//        if(StrLen(result)==0)
//          return REMOTESOLVE_ABSENT;
//        else if(result=="ERROR")
//        {
//          Sorry();
//          return REMOTESOLVE_ERROR;
//        }
//        else
//        {
//          for(int i=0;i<StrLen(result);i++)
//            moves[i]=result[i];
//          movesCount=StrLen(result);
//          Optimize();
//          SaveSolution();
//          return REMOTESOLVE_OK;
//        }
//      }
//      else
//        return REMOTESOLVE_ABSENT;
//    }


    private boolean SolveCube() {
        System.out.println("SOLVING"); // TextOut(20,LCD_LINE2,"SOLVING",true);
        System.out.print("Solution 1 ="); // TextOut(0,LCD_LINE4,"Solution 1 =");
        LoadCube();
        Solve('U','D');
        System.out.println(twists); // NumOut (80, LCD_LINE4, twists);
        SaveSolution();

        System.out.print("Solution 2 ="); // TextOut(0,LCD_LINE5,"Solution 2 =");
        LoadCube();
        Solve('F','B');
        System.out.println(twists); // NumOut (80, LCD_LINE5, twists);
        if (twists < solutionTwists) {
            SaveSolution();
        }

        System.out.print("Solution 3 ="); // TextOut(0,LCD_LINE6,"Solution 3 =");
        LoadCube();
        Solve('L','R');
        System.out.println(twists); // NumOut (80, LCD_LINE6, twists);
        // Wait(500);
        if (twists < solutionTwists) {
            SaveSolution();
        }
        return true;
    }

//    //*****************************************************************************
//    // Execute Moves

//    char FaceFind(char face)
//    {
//      for(int i=0;i<6;i++)
//        if(faces[i] == face)
//          return staticfaces[i];
//    }


//    void FaceDown(char face)
//    {
//       switch(FaceFind(face))
//      {
//        case 'U': Tilt(); Tilt(); break;
//        case 'L': TurnQuarter(2); Tilt(); break;
//        case 'F': TurnQuarter(3);Tilt(); break;
//        case 'R': Tilt(); break;
//        case 'B': TurnQuarter(1); Tilt(); break;
//      }
//    }


//    void DoMoves()
//    {
//      int turns;
//      int pos=0;
//      char face;
//      for(int i=0;i<6;i++)
//        faces[i]=staticfaces[i];

//      while(pos < solutionCount)
//      {
//        face=solution[pos];
//        FaceDown(face);
//        turns=1;
//        while(solution[++pos]==face)
//          turns++;
//        Twist(turns);
//        NumOut(40,LCD_LINE4,--solutionTwists,true);
//      }
//      Wait(1000);
//      TextOut(20,LCD_LINE4,"GAME OVER",true);
//      PlayFile("Game Over.rso");
//      Wait(2000);
//      Coast(OUT_A);
//    }


//    //*****************************************************************************

//    void WaitForCube()
//    {
//      TextOut(7,LCD_LINE4,"GIVE ME A CUBE",true);
//      Wait(2000);
//      unsigned long startTick=CurrentTick();
//      do
//      {
//        if(SensorUS(IN_2) > 10)
//          startTick=CurrentTick();
//      }while(CurrentTick() - startTick < 1000 && !ButtonPressed(BTNCENTER,false));
//      TextOut(20,LCD_LINE5,"THANK YOU !",true);
//      PlayFile("Thank You.rso");
//      Wait(2000);
//    }


//    void WaitForCubeRemove()  //Wait until cube absent for three seconds or button pressed
//    {
//      unsigned long startTick=CurrentTick();
//      do
//      {
//        if(SensorUS(IN_2) < 10)
//          startTick=CurrentTick();
//      }while(CurrentTick() - startTick < 3000 && !ButtonPressed(BTNCENTER,false));
//    }


//    task main()
//    {
//      while(true)
//      {
//        Initialize();
//        WaitForCube();
//        ScanCube();
//        ResolveColors();
//        if(SolveCube())
//          DoMoves();
//        WaitForCubeRemove();
//      }
//    }

    private char transformBack(final char move) {
        char retval = ' ';
        switch (move) {
        case 'U':
            retval = 'W';
            break;
        case 'R':
            retval = 'B';
            break;
        case 'F':
            retval = 'R';
            break;
        case 'L':
            retval = 'G';
            break;
        case 'B':
            retval = 'O';
            break;
        case 'D':
            retval = 'Y';
            break;
        }
        return getFace(retval);
    }

    private char getFace(final char currentColor) {
        if (color[LEFTFACE_CENTER] == currentColor) {
            return 'L'; 
        }
        if (color[FRONTFACE_CENTER] == currentColor) {
            return 'F';
        }
        if (color[RIGHTFACE_CENTER] == currentColor) {
            return 'R';
        }
        if (color[BACKFACE_CENTER] == currentColor) {
            return 'B';
        }
        if (color[UPPERFACE_CENTER] == currentColor) {
            return 'U';
        }
        if (color[DOWNFACE_CENTER] == currentColor) {
            return 'D';
        }
        return ' ';
    }

    @Override
    public String solution(String facelets) {
        if (facelets.length() != color.length) {
            return null;
        }
        for (int i = 0; i < color.length; i++) {
            color[i] = facelets.charAt(i);
        }
        // U -> W
        SolveCube();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < solutionCount; i++) {
            sb.append(transformBack(solution[i]));
        }
        return sb.toString();
    }
}
