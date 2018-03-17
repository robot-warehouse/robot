package rp.assignments.team.warehouse.robot.motioncontrol;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;

public class RobotMotionController implements IRobotMotionController {
	
	private static DifferentialPilot DP;
    LineFollow lf = new LineFollow(DP, SensorPort.S1, SensorPort.S2, SensorPort.S4);

	public RobotMotionController() {
		Calibrate.run();
		DP = new DifferentialPilot(5.5f, 12.4f, Motor.A, Motor.B);
        lf.run();
	}

	@Override
	public void moveForwards() {
		lf.getAction(2);
	}

	@Override
	public void turnLeft() {
		lf.getAction(0);
	}

	@Override
	public void turnRight() {
		lf.getAction(1);

	}

	@Override
	public void turnAround() {
		lf.getAction(3);

	}

	@Override
	public void stop() {
		lf.getAction(4);

	}
}