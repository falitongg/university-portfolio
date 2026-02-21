package cz.cvut.fel.omo.entity.devices.sensor;

import cz.cvut.fel.omo.entity.devices.enums.DeviceState;
import cz.cvut.fel.omo.entity.house.Room;

import java.util.Random;

import static cz.cvut.fel.omo.Constants.*;

/**
 * Temperature sensor - measures air temperature (°C).
 * Critical values: < 10°C (too cold), > 30°C (too hot).
 */
public class TemperatureSensor extends AbstractSensor {

    private static final Random random = new Random();

    public TemperatureSensor(int id, Room location, String name, DeviceState deviceStateEnum) {
        super(id, location, name, deviceStateEnum, SensorType.TEMPERATURE);

        setMinThreshold(MIN_TEMPERATURE_THRESHOLD);
        setMaxThreshold(MAX_TEMPERATURE_THRESHOLD);


    }

    /**
     * Measures room temperature using Gaussian distribution.
     * Mean: 24°C (comfortable room temperature)
     * Standard deviation: 4°C
     * @return Temperature in Celsius
     */
    @Override
    protected double performMeasurement() {
        double baseTemp = MEAN_TEMPERATURE + random.nextGaussian() * TEMPERATURE_STD_DEV;
        return baseTemp;
    }
}
