package cz.cvut.fel.omo.entity.devices.sensor;

import cz.cvut.fel.omo.entity.devices.enums.DeviceState;
import cz.cvut.fel.omo.entity.house.Room;

import java.util.Random;

import static cz.cvut.fel.omo.Constants.*;

/**
 * CO2 sensor - measures CO2 concentration (ppm).
 * Normal range: 400–800 ppm
 * Critical value: > 1000 ppm (open window).
 */
public class СO2Sensor extends AbstractSensor {


    private static final Random random = new Random();

    public СO2Sensor(int id, Room location, String name, DeviceState deviceStateEnum) {
        super(id, location, name, deviceStateEnum, SensorType.CO2);
        setMinThreshold(MIN_CO2_THRESHOLD);
        setMaxThreshold(MAX_CO2_THRESHOLD);
    }

    /**
     * Measures CO2 concentration.
     * - 90% chance: normal room (400–800 ppm)
     * - 10% chance: bad air quality spike (1000–2000 ppm)
     */
    @Override
    protected double performMeasurement() {
        // 10 % chance of bad air
        if (random.nextDouble() < BAD_AIR_PROBABILITY) {
            return HIGH_CO2_BASE + random.nextDouble() * HIGH_CO2_RANGE; // 1000–2000 ppm
        }

        // normal conditions
        return NORMAL_CO2_BASE + random.nextGaussian() * CO2_STD_DEV; // cca 300–600 ppm
    }
}
