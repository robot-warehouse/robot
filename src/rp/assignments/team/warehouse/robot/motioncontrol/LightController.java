package rp.assignments.team.warehouse.robot.motioncontrol;

import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;

/**
 * @author Qasim Nawaz
 */
public class LightController {

    /** maximum and minimum sensor distance readings */
    private static final int MAX_LIGHT = 100;
    private static final int MIN_LIGHT = 0;

    /** the amount of times the filter needs to see a max reading before the reading can be trusted */
    private static final int FILTER_AMOUNT = 5;

    /** */
    private LightSensor lightSensor;

    /** whether a max reading is actually a max, or really a min */
    private boolean filterToMax = true;

    /** saved distance in case the distance from the sensor is filtered */
    private int light;

    /** */
    private int filterCount = 0;

    public LightController(SensorPort port) {
        this.lightSensor = new LightSensor(port);
    }

    public int getLightValue() {
        int realDistance = filter(this.lightSensor.getLightValue());
        saveLight(realDistance);
        return this.light;
    }

    private int filter(int sensorLight) {
        if (sensorLight >= MAX_LIGHT) {
            // sensor reading is at an extreme, and may need to be filtered
            if (this.filterCount > FILTER_AMOUNT) {
                // sensor really is at the min / max distance
                this.filterCount = 0;
                return getExtremeLight();
            } else {
                // extreme sensor reading cannot be trusted
                this.filterCount++;
                return this.light;
            }
        }
        this.filterCount = 0; // resets counter
        return sensorLight;
    }

    private void saveLight(int light) {
        this.filterToMax = light > 12;
        this.light = light;
    }

    private int getExtremeLight() {
        return this.filterToMax ? MAX_LIGHT : MIN_LIGHT;
    }
}
