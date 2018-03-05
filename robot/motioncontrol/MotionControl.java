package motioncontrol;

import java.util.Queue;

import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import motioncontrol.Calibrate;
import rp.config.RobotConfigs;

public class MotionControl {
	
	int average = Calibrate.average;
	
	public MotionControl() {
		Queue<String> path = new Queue<String>();
		Behavior lineFollower = new GForwardLine(RobotConfigs.EXPRESS_BOT, average);
		Behavior rotateC = new GRotateMotorC(RobotConfigs.EXPRESS_BOT, average);
		Behavior rotateA = new GRotateMotorA(RobotConfigs.EXPRESS_BOT, average);
		Behavior conjunction = new GUserConjunct(RobotConfigs.EXPRESS_BOT, average, path);
		Behavior[] bArray = {conjunction, lineFollower,rotateC, rotateA};
		Arbitrator arby = new Arbitrator(bArray);
		arby.start();
	}
}