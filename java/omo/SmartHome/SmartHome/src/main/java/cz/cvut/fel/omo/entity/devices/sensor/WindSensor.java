package cz.cvut.fel.omo.entity.devices.sensor;

import cz.cvut.fel.omo.entity.devices.enums.DeviceState;
import cz.cvut.fel.omo.entity.house.Room;

import java.util.Random;


import static cz.cvut.fel.omo.Constants.*;


/**
 * Wind sensor - measures wind speed (m/s).
 * Installed outside (garden, balcony).
 * Critical value: > 15 m/s (strong wind - close blinds!).
 */
public class WindSensor extends AbstractSensor {


    private static final Random random = new Random();

    public WindSensor(int id, Room location, String name, DeviceState deviceStateEnum) {
        super(id, location, name, deviceStateEnum, SensorType.WIND);

        setMinThreshold(MIN_WIND_THRESHOLD);
        setMaxThreshold(MAX_WIND_THRESHOLD);
    }

    /**
     * Measures wind speed with storm simulation.
     * Normal weather: Gaussian distribution (mean=0m/s, std dev=5m/s)
     * Storm: 5% chance of 15-25 m/s wind speed.
     * @return Wind speed in m/s
     */
    @Override
    protected double performMeasurement() {
        // Gaussian: mean=0m/s, std dev=5m/s (calm weather baseline)
        double windSpeed = Math.abs(random.nextGaussian() * WIND_STD_DEV);

        if (random.nextDouble() < STORM_PROBABILITY) {
            windSpeed = STORM_BASE_SPEED + random.nextDouble() * STORM_SPEED_RANGE;
        }

        return windSpeed;
    }
}
