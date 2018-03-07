package motioncontrol;

import java.util.Random;
import java.util.Vector;
import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.TouchSensor;
import lejos.nxt.addon.OpticalDistanceSensor;
import lejos.robotics.RangeFinder;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;
import rp.config.WheeledRobotConfiguration;
import rp.robotics.DifferentialDriveRobot;
import rp.util.Rate;

public class LineFollower {
	
	private final DifferentialDriveRobot robot;
	private final DifferentialPilot pilot;
	private final LightSensor lightSensorLeft;
	private final LightSensor lightSensorRight;
	private final LightSensor lightSensorFront;
	private final int BLACK = 45;
	private final int ROBOT_LENGTH = 23;
	private boolean running = true;

	Rate r = new Rate(40);
	double reflectedLightLeft;
	double reflectedLightRight;
	double reflectedLightFront;
	//String lastTurn = new String();

	int c = 0;

	public LineFollower(DifferentialDriveRobot robot, SensorPort lsl, SensorPort lsr, SensorPort lsf) {
		this.robot = robot;
		this.pilot = this.robot.getDifferentialPilot();
		this.lightSensorLeft = new LightSensor(lsl);
		this.lightSensorRight = new LightSensor(lsr);
		this.lightSensorFront = new LightSensor(lsf);

		pilot.setTravelSpeed(6f);
		pilot.setRotateSpeed(75);
	}
	
	public void run(){
		
		calibrateLight();

		while (running) {
			//0 is dark
			//100 is light
			
			reflectedLightLeft = lightSensorLeft.getLightValue();
			reflectedLightRight = lightSensorRight.getLightValue();
			reflectedLightFront = lightSensorFront.getLightValue();		
			
			if (isWhite(reflectedLightRight) && isWhite(reflectedLightLeft)) {
				
				if (isWhite(reflectedLightFront)) {
					
					pilot.stop();
					pilot.travel(ROBOT_LENGTH/4);
					
					if (isWhite(reflectedLightFront)&&isWhite(reflectedLightLeft)&&isWhite(reflectedLightRight)) {
						pilot.rotate(175);
					}
					
					else {
						if (!isWhite(reflectedLightLeft) && isWhite(reflectedLightRight)) // adjust to left
						{
							turnLeft(reflectedLightLeft, reflectedLightRight);
							//lastTurn = "left";
						}
						
						else if (!isWhite(reflectedLightRight) && isWhite(reflectedLightLeft)) // adjust to right
						{
							turnRight(reflectedLightLeft, reflectedLightRight);
							//lastTurn = "right";
						}
						
						
					}

				}
				
				else {
					pilot.forward();
				}
				
			}
			
			else {
				if (!isWhite(reflectedLightLeft) && isWhite(reflectedLightRight)) // adjust to left
				{
					turnLeft(reflectedLightLeft, reflectedLightRight);
				}
				
				else if (!isWhite(reflectedLightRight) && isWhite(reflectedLightLeft)) // adjust to right
				{
					turnRight(reflectedLightLeft, reflectedLightRight);
				}
				
				
				else if (!isWhite(reflectedLightRight) && !isWhite(reflectedLightLeft)){	// both black?
					randomRotation();
				}
			}		
			r.sleep();
		}
	}
	
	public void run2 (int x, int y) {

	}

	private static int calibrateLight() {
		
		int average;
		
		LightSensor lightSensor1 = new LightSensor(SensorPort.S1);
		LightSensor lightSensor2 = new LightSensor(SensorPort.S4);
		LightSensor lightSensor3 = new LightSensor(SensorPort.S2);
		int white, black, white2, black2, white3, black3 = 0;
		Button.waitForAnyPress();
		System.out.println("Light colour");
		Button.waitForAnyPress();
		white = lightSensor1.getNormalizedLightValue();
		white2 = lightSensor2.getNormalizedLightValue();
		white3 = lightSensor3.getNormalizedLightValue();
		int averageWhite = (white + white2 + white3) / 3;
		System.out.println("White colour: " + averageWhite);
		System.out.println("Dark colour");
		Button.waitForAnyPress();
		lightSensor1.calibrateLow();
		lightSensor2.calibrateLow();
		black = lightSensor1.getNormalizedLightValue();
		black2 = lightSensor2.getNormalizedLightValue();
		black3 = lightSensor3.getNormalizedLightValue();
		int averageBlack = (black + black2+ black3) / 3;
		System.out.println("Black colour: " + averageBlack);

		return average = (averageBlack + averageWhite) / 2;
	}
	
	private boolean isWhite(double value) {
		if (value > BLACK)
		{
			return true;
		}
		
		else {
			return false;
		}	
	}
	
	private void turnLeft(double ls, double rs) {
		while (!isWhite(ls) && isWhite(rs)) {
			robot.getLeftWheel().stop();
			robot.getRightWheel().forward();
			ls = lightSensorLeft.getLightValue();
			rs = lightSensorRight.getLightValue();
		}
	}

	private void turnRight(double ls, double rs) {
		while (isWhite(ls) && !isWhite(rs)) {
			robot.getRightWheel().stop();
			robot.getLeftWheel().forward();
			ls = lightSensorLeft.getLightValue();
			rs = lightSensorRight.getLightValue();
		}
	}
	
	public void randomRotation() {
		pilot.stop();
		pilot.travel(ROBOT_LENGTH/2);
		
		Random r = new Random();
		int randNum = r.nextInt(3);
		
		if (randNum == 0) {
			//rotate the robot left
			pilot.rotate(90);

		}
		
		else if (randNum == 1) {
			//rotate the robot right
			pilot.rotate(-88);
		}
		
		else if (randNum == 2) {
			pilot.forward();

		}

	}
	
	public static void main(String[] args) {
		

		calibrateLight();
		
		Button.waitForAnyPress();
		
		LineFollower dj2 = new LineFollower(
				new DifferentialDriveRobot(new WheeledRobotConfiguration(5.5f, 13.5f, 23f, Motor.A, Motor.B)),
				SensorPort.S4,
				SensorPort.S1,
				SensorPort.S2
				);
		dj2.run();
		
	}
}
