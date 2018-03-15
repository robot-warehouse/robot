package rp.assignments.team.warehouse.robot.motioncontrol;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.SensorPortListener;
import lejos.robotics.navigation.DifferentialPilot;

import rp.systems.RobotProgrammingDemo;

public class MotionControl extends RobotProgrammingDemo implements SensorPortListener {

    private static DifferentialPilot DP;

    public static void main(String[] args) {
        Button.waitForAnyPress();
        DP = new DifferentialPilot(5.5f, 12.4f, Motor.A, Motor.B);
        RobotProgrammingDemo lf = new LineFollow(DP, SensorPort.S1, SensorPort.S2, SensorPort.S4);
        lf.run();
    }

    @Override
    public void run() {}

    @Override
    public void stateChanged(SensorPort aSource, int aOldValue, int aNewValue) {}
}
