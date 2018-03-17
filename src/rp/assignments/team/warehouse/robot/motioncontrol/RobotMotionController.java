package rp.assignments.team.warehouse.robot.motioncontrol;

import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;

import rp.assignments.team.warehouse.robot.communications.RobotCommunicationsManager;

public class RobotMotionController implements IRobotMotionController {

    private static DifferentialPilot DP;
    LineFollow lf;

    public RobotMotionController(RobotCommunicationsManager manager) {

        DP = new DifferentialPilot(5.5f, 12.4f, Motor.A, Motor.B);
        this.lf = new LineFollow(DP, SensorPort.S1, SensorPort.S2, SensorPort.S4, manager);
        this.lf.run();
    }

    @Override
    public void moveForwards() {
        this.lf.getAction(2);
    }

    @Override
    public void turnLeft() {
        this.lf.getAction(0);
    }

    @Override
    public void turnRight() {
        this.lf.getAction(1);
    }

    @Override
    public void turnAround() {
        this.lf.getAction(3);
    }

    @Override
    public void stop() {
        this.lf.getAction(4);
    }
}