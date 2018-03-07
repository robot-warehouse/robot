package motioncontrol;

import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;

public class LightController {
	
	private LightSensor lightSensor;
	
	// maximum and minimum sensor distance readings
	private static final int MIN_LIGHT = 0;
	private static final int MAX_LIGHT = 100;
	// the amount of times the filter needs to see a max reading
	// before the reading can be trusted
	private static final int FILTER_AMOUNT = 5;
	private int filterCount = 0;
	// whether a max reading is actually a max, or really a min
	public boolean filterToMax = true;
	// saved distance in case the distance from the sensor is filtered
	public int light;

	public LightController(SensorPort port){
		this.lightSensor = new LightSensor(port);
	}
	
	private void saveLight(int light) {
		this.filterToMax = light > 12;
		this.light = light;
	}

	private int filter(int sensorLight) {
		if (sensorLight >= MAX_LIGHT) {
			// sensor reading is at an extreme, and may need to be filtered
			if (filterCount > FILTER_AMOUNT) {
				// sensor really is at the min / max distance
				filterCount = 0;
				return getExtremeLight();
			} else {

				// extreme sensor reading cannot be trusted
				filterCount++;
				return light;
			}
		}
		filterCount = 0; // resets counter
		return sensorLight;
	}

	private int getExtremeLight() {
		return filterToMax ? MAX_LIGHT : MIN_LIGHT;
	}

	public int getLightValue() {
		int realDistance = filter(lightSensor.getLightValue());
		saveLight(realDistance);
		return light;
	}
}