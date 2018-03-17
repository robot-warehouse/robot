package rp.assignments.team.warehouse.robot.motioncontrol;

import lejos.robotics.navigation.DifferentialPilot;

/**
 * @author Qasim Nawaz
 */
public class PIDController {

    final float targetValue;
    final float baseSpeed;
    final float Kp = (float) 8;
    final float Ki = (float) 0.00005;
    final float Kd = (float) 0.09;
    DifferentialPilot DP;
    LightController lightSensor;
    float leftSpeed;
    float rightSpeed;
    float lightValue;
    float errorSignal;
    float integral = 0;
    float lastError = 0;
    float derivative = 0;

    public PIDController(float targetValue, LightController lightSensor, float baseSpeed, DifferentialPilot DP) {
        this.targetValue = targetValue;
        this.lightSensor = lightSensor;
        this.baseSpeed = baseSpeed;
        this.DP = DP;
    }

    public void run() {
        this.lightValue = this.lightSensor.getLightValue();
        this.errorSignal = this.targetValue - this.lightValue;

        this.integral *= 0.98;
        this.integral += this.errorSignal;
        this.derivative = this.errorSignal - this.lastError;
        this.lastError = this.errorSignal;

        this.leftSpeed = this.baseSpeed + (this.Kp * this.errorSignal) + (this.Ki * this.integral) + (this.Kd * this
                .derivative);
        this.rightSpeed = this.baseSpeed - (this.Kp * this.errorSignal) + (this.Ki * this.integral) + (this.Kd * this
                .derivative);
    }
}
