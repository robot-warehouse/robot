package rp.assignments.team.warehouse.robot.motioncontrol;

import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.comm.RConsole;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;

import rp.robotics.DifferentialDriveRobot;
import rp.assignments.team.warehouse.robot.RobotConfiguration;
import rp.assignments.team.warehouse.robot.RobotController;

public class RobotMotionController implements IRobotMotionController {

	private final int TARGET_VALUE = 34; // NAMELESS 35; //TRIHARD 35; //JOHNCENA 34;
	private final int JUNCTION_VALUE = 37; // NAMELESS 34; //TRIHARD 34; //JOHNCENA 45;
	private final int DISTANCE_TO_JUNCTION = 6;

	private DifferentialPilot differentialPilot;
	private LightController lightSensorLeft;
	private LightController lightSensorRight;
	private LightController lightSensorMiddle;
	private PIDController pidController;

	public RobotMotionController() {
		DifferentialDriveRobot robot = new DifferentialDriveRobot(RobotConfiguration.ROBOT_CONFIG);
		this.differentialPilot = robot.getDifferentialPilot();
		this.lightSensorLeft = new LightController(SensorPort.S1);
		this.lightSensorRight = new LightController(SensorPort.S4);
		differentialPilot.setTravelSpeed(250);
		lightSensorMiddle = new LightController(SensorPort.S2);
		this.pidController = new PIDController(this.TARGET_VALUE, lightSensorMiddle,
				(float) this.differentialPilot.getTravelSpeed());
	}

	@Override
	public void moveForwards() {
		try {
			while (!junctionReached()) {
				this.pidController.run();
				this.differentialPilot.forward();
				Motor.A.setSpeed(this.pidController.getRightSpeed());
				Motor.B.setSpeed(this.pidController.getLeftSpeed());
			}
			this.differentialPilot.travel(this.DISTANCE_TO_JUNCTION);
			this.differentialPilot.stop();
		} catch (NullPointerException e) {
			System.out.println("Null in move forwards");
		}

	}

	@Override
	public void takeLeftExit() {
		try {
			
			this.differentialPilot.rotate(-70);
			Delay.msDelay(10);
			moveForwards();
		} catch (NullPointerException e) {
			System.out.println("Null in move left");
		}

	}

	@Override
	public void takeRightExit() {
		try {
		
			this.differentialPilot.rotate(90);
			Delay.msDelay(10);
			moveForwards();
		} catch (NullPointerException e) {
			System.out.println("Null in move right");
		}
	}

	@Override
	public void takeRearExit() {
		try {
	
			this.differentialPilot.rotate(90);
			Delay.msDelay(10);
			this.differentialPilot.rotate(90);
			Delay.msDelay(10);
			moveForwards();
		} catch (NullPointerException e) {
			System.out.println("Null in move back");
		}

	}

	@Override
	public void holdUp() {
		try {
			Delay.msDelay(RobotController.STOP_WAIT_TIME);
		}catch(NullPointerException e) {
			System.out.println("Null in hold ups");
		}
		
	}

	/**
	 * Checks if both left and right light sensors are reading black
	 *
	 * @return True if a junction has been detected
	 */
	private boolean junctionReached() {
		return this.lightSensorLeft.getLightValue() <= this.JUNCTION_VALUE
				&& this.lightSensorRight.getLightValue() <= this.JUNCTION_VALUE;
	}
}
