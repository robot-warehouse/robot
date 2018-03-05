package motioncontrol;

import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;

public class Calibrate {

	public static int average;
	
	public Calibrate() {
		
	}
	
	public static void calibrate() {
		LightSensor lightSensor1 = new LightSensor(SensorPort.S1);
		LightSensor lightSensor2 = new LightSensor(SensorPort.S4);
		LightSensor lightSensor3 = new LightSensor(SensorPort.S2);
		int white, black, white2, black2, white3, black3 = 0;
		Button.waitForAnyPress();
		System.out.println("Light colour");
		Button.waitForAnyPress();
		white = lightSensor1.getNormalizedLightValue();
		white2 = lightSensor2.getNormalizedLightValue();
		white3 = lightSensor3.getNormalizedLightValue();
		int averageWhite = (white + white2 + white3) / 3;
		System.out.println("White colour: " + averageWhite);
		System.out.println("Dark colour");
		Button.waitForAnyPress();
		lightSensor1.calibrateLow();
		lightSensor2.calibrateLow();
		black = lightSensor1.getNormalizedLightValue();
		black2 = lightSensor2.getNormalizedLightValue();
		black3 = lightSensor3.getNormalizedLightValue();
		int averageBlack = (black + black2+ black3) / 3;
		System.out.println("Black colour: " + averageBlack);

		average = (averageBlack + averageWhite) / 2;
		Button.waitForAnyPress();
	}
}