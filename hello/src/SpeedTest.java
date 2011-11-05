/**
 * 
 */

import java.util.Random;

import lejos.nxt.ColorSensor;
import lejos.nxt.ColorSensor.Color;
import lejos.nxt.LCD;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.EncoderMotor;

/**
 * Testing the speed of lejos.
 *
 * @author marcus
 */
public final class SpeedTest {

	private static final long T_MAX = 60000l;

	public static void main(String[] args) throws InterruptedException {
		ColorSensor cls = new ColorSensor(SensorPort.S3, Color.NONE);
		cls.setFloodlight(Color.WHITE);
		UltrasonicSensor uss = new UltrasonicSensor(SensorPort.S4);
		EncoderMotor motorA = new NXTMotor(MotorPort.A);
		EncoderMotor motorB = new NXTMotor(MotorPort.B);
		Random rand = new Random(System.currentTimeMillis());

		long timeout = System.currentTimeMillis() + T_MAX;
		int a = 0;
		int light;
		int dist;
		int rot;
		int rnd;
		int val;
		int loopCount = 0;
		while (System.currentTimeMillis() < timeout) {
			Color color = cls.getRawColor();
			light = color.getBackground();
			dist = uss.getDistance();
			rot = motorB.getTachoCount();
			LCD.drawInt(rot, 10, 7);
			rnd = rand.nextInt(100) + 1;
			val = ((light + dist + rot) * 100) / rnd;
			LCD.drawInt(val, 10, 6);
			motorB.setPower(rnd);
			motorB.forward();
			if (rnd < 50) {
				a++;
			} else if (rnd > 50) {
				a--;
			}
			LCD.drawInt(a, 10, 5);
			motorA.setPower(a);
			motorA.forward();
			LCD.drawInt(loopCount, 10, 4);
			loopCount++;
		}
		motorA.stop();
		motorB.stop();
		Thread.sleep(10000);
	}
}
