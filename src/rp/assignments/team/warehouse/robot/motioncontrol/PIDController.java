package rp.assignments.team.warehouse.robot.motioncontrol;

import lejos.robotics.navigation.DifferentialPilot;

/**
 * @author Qasim Nawaz
 */
public class PIDController {

    private float leftSpeed;
    private float rightSpeed;

    private LightController lightSensor;

    private final float targetValue;
    private final float baseSpeed;

    private float integral;
    private float lastError;

    public PIDController(float targetValue, LightController lightSensor, float baseSpeed) {
        this.targetValue = targetValue;
        this.lightSensor = lightSensor;
        this.baseSpeed = baseSpeed;
    }

    public void run() {
        float lightValue = this.lightSensor.getLightValue();
        float errorSignal = this.targetValue - lightValue;

        this.integral *= 0.98;
        this.integral += errorSignal;
        float derivative = errorSignal - this.lastError;
        this.lastError = errorSignal;

        float kd = 0.09f;
        float ki = 0.00005f;
        float kp = 8f;

        this.leftSpeed = this.baseSpeed + (kp * errorSignal) + (ki * this.integral) + (kd * derivative);
        this.rightSpeed = this.baseSpeed - (kp * errorSignal) + (ki * this.integral) + (kd * derivative);
    }

    public float getLeftSpeed() {
        return this.leftSpeed;
    }

    public float getRightSpeed() {
        return this.rightSpeed;
    }
}
