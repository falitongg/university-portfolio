package cz.cvut.fel.omo.entity.devices.sensor;

import cz.cvut.fel.omo.entity.devices.enums.DeviceState;
import cz.cvut.fel.omo.entity.house.Room;

import java.util.Random;

import static cz.cvut.fel.omo.Constants.*;

/**
 * Humidity sensor - measures relative humidity (%).
 * Critical values: < 30% (too dry), > 80% (mold risk/leak).
 */
public class HumiditySensor extends AbstractSensor {


    private static final Random random = new Random();

    public HumiditySensor(int id, Room location, String name, DeviceState deviceStateEnum) {
        super(id, location, name, deviceStateEnum, SensorType.HUMIDITY);

        setMinThreshold(MIN_HUMIDITY_THRESHOLD);
        setMaxThreshold(MAX_HUMIDITY_THRESHOLD);
    }

    /**
     * Generates realistic humidity value using Gaussian (normal) distribution.
     * Mean: 45% (typical indoor humidity)
     * Standard deviation: 10%
     * ~68% of values fall within 35-55%
     * Extreme values trigger events only occasionally
     */
    @Override
    protected double performMeasurement() {
        double baseHumidity = MEAN_HUMIDITY + random.nextGaussian() * HUMIDITY_STD_DEV;

        // Clamp to valid range [0-100%]:
        // - If < 0 → set to 0
        // - If > 100 → set to 100
        // - Otherwise → keep original value
        return Math.max(MIN_HUMIDITY_RANGE, Math.min(MAX_HUMIDITY_RANGE, baseHumidity));
    }
}
