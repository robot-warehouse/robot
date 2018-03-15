package rp.assignments.team.warehouse.robot.motioncontrol;

import lejos.robotics.navigation.DifferentialPilot;

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
        lightValue = lightSensor.getLightValue();
        errorSignal = targetValue - lightValue;

        integral *= 0.98;
        integral += errorSignal;
        derivative = errorSignal - lastError;
        lastError = errorSignal;

        leftSpeed = baseSpeed + (Kp * errorSignal) + (Ki * integral) + (Kd * derivative);
        rightSpeed = baseSpeed - (Kp * errorSignal) + (Ki * integral) + (Kd * derivative);
    }
}
