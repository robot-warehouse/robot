package rp.assignments.team.warehouse.robot;

import lejos.nxt.Motor;

import rp.config.WheeledRobotConfiguration;

public class RobotConfiguration {

    /** Diameter of the wheel, measure in cm */
    private static final float WHEEL_DIAMETER = 5.5f;

    /** Distance between middle of each wheel, measured in cm */
    private static final float TRACK_WIDTH = 12.4f;

    /** Length of the robot measured in cm */
    private static final float ROBOT_LENGTH = 16.0f; // this is a guess right now

    public static final WheeledRobotConfiguration ROBOT_CONFIG = new WheeledRobotConfiguration(WHEEL_DIAMETER,
            TRACK_WIDTH, ROBOT_LENGTH, Motor.A, Motor.B);
}

