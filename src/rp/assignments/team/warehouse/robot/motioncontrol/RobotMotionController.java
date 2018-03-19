package rp.assignments.team.warehouse.robot.motioncontrol;

import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;

import rp.robotics.DifferentialDriveRobot;
import rp.assignments.team.warehouse.robot.RobotConfiguration;
import rp.assignments.team.warehouse.robot.RobotController;

public class RobotMotionController implements IRobotMotionController {

    private final int TARGET_VALUE = 34; //NAMELESS 35; //TRIHARD 35; //JOHNCENA 34;
    private final int JUNCTION_VALUE = 37; //NAMELESS 34; //TRIHARD 34; //JOHNCENA 45;
    private final int DISTANCE_TO_JUNCTION = 8;


    private DifferentialPilot differentialPilot;
    private LightController lightSensorLeft;
    private LightController lightSensorRight;
    private PIDController pidController;

    public RobotMotionController() {
        DifferentialDriveRobot robot = new DifferentialDriveRobot(RobotConfiguration.ROBOT_CONFIG);
        this.differentialPilot = robot.getDifferentialPilot();
        this.lightSensorLeft = new LightController(SensorPort.S1);
        this.lightSensorRight = new LightController(SensorPort.S4);

        LightController lightSensorMiddle = new LightController(SensorPort.S2);
        this.pidController = new PIDController(
            this.TARGET_VALUE, lightSensorMiddle,
            (float) this.differentialPilot.getTravelSpeed());
    }

    @Override
    public void moveForwards() {
        while (!junctionReached()) {
            this.pidController.run();
            this.differentialPilot.forward();
            Motor.A.setSpeed(this.pidController.getRightSpeed());
            Motor.B.setSpeed(this.pidController.getLeftSpeed());
        }

        this.differentialPilot.stop();
    }

    @Override
    public void takeLeftExit() {
        this.differentialPilot.travel(this.DISTANCE_TO_JUNCTION);
        this.differentialPilot.rotate(-90);
        moveForwards();
    }

    @Override
    public void takeRightExit() {
        this.differentialPilot.travel(this.DISTANCE_TO_JUNCTION);
        this.differentialPilot.rotate(90);
        moveForwards();
    }

    @Override
    public void takeRearExit() {
        this.differentialPilot.travel(this.DISTANCE_TO_JUNCTION);
        this.differentialPilot.rotate(180);
        moveForwards();
    }

    @Override
    public void holdUp() {
        Delay.msDelay(RobotController.STOP_WAIT_TIME);
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
