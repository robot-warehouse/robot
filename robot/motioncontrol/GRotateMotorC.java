package motioncontrol;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;
import rp.config.WheeledRobotConfiguration;
import rp.systems.WheeledRobotSystem;

public class GRotateMotorC implements Behavior {

	private WheeledRobotSystem robot;
	private DifferentialPilot pilot;
	protected int average;
	
	public int counter;

	protected LightSensor lightSensor1;
	protected LightSensor lightSensor2;

	protected final double MAXSPEED;

	public GRotateMotorC(WheeledRobotConfiguration robot, int average, int counter) {
		this.robot = new WheeledRobotSystem(robot);
		pilot = this.robot.getPilot();
		MAXSPEED = pilot.getMaxTravelSpeed();
		pilot.setTravelSpeed(MAXSPEED * 0.1);
		this.average = average;
		lightSensor1 = new LightSensor(SensorPort.S1);
		lightSensor2 = new LightSensor(SensorPort.S4);
		this.counter = counter;
	}

	@Override
	public boolean takeControl() {
		//Delay.msDelay(100);
		if (lightSensor1.getNormalizedLightValue() < average && lightSensor2.getNormalizedLightValue() > average && counter <1) {
			// TODO Auto-generated method stub
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		Delay.msDelay(50);
		Motor.A.rotate(-60);
		//System.out.println("Is it executing MotorC?");
		counter++;
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub

	}

}
