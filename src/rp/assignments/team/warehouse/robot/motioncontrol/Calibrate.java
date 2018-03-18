package rp.assignments.team.warehouse.robot.motioncontrol;

import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;

/**
 * @author Qasim Nawaz
 */
public class Calibrate {

    public static int average;
    public static Boolean middleLineCal = false;
    public static Boolean junctionLineCal = false;
    public static int middleLine = 0;
    public static int juncLeft = 0;
    public static int juncRight = 0;
    public static int juncAvg = 0;

    public static void run() {
        LightSensor lightSensor1 = new LightSensor(SensorPort.S1);
        LightSensor lightSensor2 = new LightSensor(SensorPort.S2);
        LightSensor lightSensor4 = new LightSensor(SensorPort.S4);

        middleLineCal = true;
        while (middleLineCal) {
            int i = Button.waitForAnyPress();

            if (i == Button.ID_ENTER) {
                System.out.println("Place on middle line and take values");
            }

            int j = Button.waitForAnyPress();

            if (j == Button.ID_ENTER) {
                middleLine = lightSensor2.getLightValue();
                System.out.println("Middle line has been calibrated to: " + middleLine);
                middleLineCal = false;
                break;
            } else if (j == Button.ID_RIGHT) {
                System.out.println("CURRENT READING: " + lightSensor2.getLightValue());
            } else if (j == Button.ID_LEFT) {
                System.out.println("CURRENT READING: " + lightSensor2.getLightValue());
            }

        }

        junctionLineCal = true;
        while (junctionLineCal) {
            int i = Button.waitForAnyPress();

            if (i == Button.ID_ENTER) {
                System.out.println("Place on line junction and take values");
            }

            int j = Button.waitForAnyPress();

            if (j == Button.ID_ENTER) {
                juncLeft = lightSensor1.getLightValue();
                juncRight = lightSensor4.getLightValue();
                juncAvg = (juncLeft + juncRight) / 2;
                System.out.println("Junction has been calibrated to: " + juncAvg);
                junctionLineCal = false;
                break;
            } else if (j == Button.ID_RIGHT) {
                System.out.println("CURRENT READING: " + ((lightSensor1.getLightValue() + lightSensor4.getLightValue
                        ()) / 2));
            } else if (j == Button.ID_LEFT) {
                System.out.println("CURRENT READING: " + ((lightSensor1.getLightValue() + lightSensor4.getLightValue
                        ()) / 2));
            }
        }
    }

    public int getJuncAvg() {
        return juncAvg;
    }

    public int getMiddleLine() {
        return middleLine;
    }

    //    public static void main(String[] args) {
    //    	Calibrate c = new Calibrate();
    //    	Calibrate.run();
    //    }
}
