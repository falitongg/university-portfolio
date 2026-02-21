package cz.cvut.fel.omo.entity.devices.sensor;

import cz.cvut.fel.omo.entity.devices.Device;
import cz.cvut.fel.omo.entity.devices.enums.ConsumptionType;
import cz.cvut.fel.omo.entity.devices.enums.DeviceState;
import cz.cvut.fel.omo.entity.devices.enums.DeviceType;
import cz.cvut.fel.omo.entity.house.Room;
import cz.cvut.fel.omo.event.EventManager;
import cz.cvut.fel.omo.event.EventType;

import static cz.cvut.fel.omo.Constants.*;
import static cz.cvut.fel.omo.Constants.SENSOR_CONSUMPTION_ELECTRICITY;

/**
 * Abstract base class for all sensors.
 * Uses Template Method Pattern for measurement logic.
 * Subclasses implement performMeasurement() to provide specific sensor logic.
 */
public abstract class AbstractSensor extends Device {




    private final SensorType sensorType;

    /**
     * Current measured value.
     */
    protected double currentValue;

    /**
     * Threshold values for generating events.
     */
    protected double minThreshold;
    protected double maxThreshold;

    /**
     * Creates sensor with specified type.
     * All sensors consume minimal electricity (0.001 per tick).
     * @param id Sensor ID
     * @param location Room where sensor is installed
     * @param name Sensor name
     * @param deviceStateEnum Initial state
     * @param sensorType Type of sensor
     */
    public AbstractSensor(int id, Room location, String name,
                          DeviceState deviceStateEnum, SensorType sensorType) {
        super(id, location, name, deviceStateEnum, DeviceType.SENSOR);
        this.sensorType = sensorType;
        this.currentValue = DEFAULT_INITIAL_VALUE;
        consumptionPerTick.put(ConsumptionType.ELECTRICITY, SENSOR_CONSUMPTION_ELECTRICITY);
    }

    /**
     * Template Method: performs measurement if sensor is ON or WORKING.
     * Calls abstract performMeasurement() implemented by subclasses.
     */
    public final void measure(){
        if (getDeviceStateEnum() != DeviceState.ON){
            return;
        }

        currentValue = performMeasurement();
    }

    /**
     * Abstract method for specific sensor measurement logic.
     * Implemented by subclasses (HumiditySensor, TemperatureSensor, etc.).
     * @return Measured value
     */
    protected abstract double performMeasurement();

    /**
     * Checks measured value and generates events if critical.
     */
    protected void checkThresholds(){
        if (currentValue < minThreshold || currentValue > maxThreshold) {
            EventType type = switch (sensorType) {
                case TEMPERATURE -> EventType.TEMPERATURE_ALERT;
                case HUMIDITY -> EventType.WATER_LEAK;
                case WIND -> EventType.WIND_ALERT;
                case CO2 -> EventType.CO2_ALERT;
            };

            EventManager.getInstance().generateEvent(this, this, type);
        }
    }
    @Override
    public void eventOccuired() {
        measure();        // ⬅️ CHYBÍ
        checkThresholds();
    }

    /**
     * Gets sensor type.
     * @return Sensor type
     */
    public SensorType getSensorType() {
        return sensorType;
    }

    /**
     * Gets current measured value.
     * @return Current value
     */
    public double getCurrentValue() {
        return currentValue;
    }

    /**
     * Gets minimum threshold.
     * @return Min threshold
     */
    public double getMinThreshold() {
        return minThreshold;
    }

    /**
     * Gets maximum threshold.
     * @return Max threshold
     */
    public double getMaxThreshold() {
        return maxThreshold;
    }

    /**
     * Sets minimum threshold for event generation.
     * @param minThreshold Min threshold value
     */
    public void setMinThreshold(double minThreshold) {
        this.minThreshold = minThreshold;
    }

    /**
     * Sets maximum threshold for event generation.
     * @param maxThreshold Max threshold value
     */
    public void setMaxThreshold(double maxThreshold) {
        this.maxThreshold = maxThreshold;
    }
}
