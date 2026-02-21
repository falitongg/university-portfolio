package cz.cvut.fel.omo.entity.devices.factory;

import cz.cvut.fel.omo.entity.devices.Device;
import cz.cvut.fel.omo.entity.devices.DeviceAPI;
import cz.cvut.fel.omo.entity.devices.sensor.*;
import cz.cvut.fel.omo.entity.devices.sensor.SensorType;
import cz.cvut.fel.omo.entity.devices.enums.DeviceState;
import cz.cvut.fel.omo.entity.devices.enums.DeviceType;
import cz.cvut.fel.omo.entity.house.Room;

/**
 * Factory Method Pattern for creating devices.
 * Centralizes device creation logic with auto-incremented IDs.
 */
public class DeviceFactory {

    private static int devicerIdCounter = 0;

    /**
     * Creates device based on DeviceType.
     * Assigns auto-incremented ID and initial OFF state.
     * @param type Device type
     * @param location Room where device is located
     * @param name Device name
     * @return Device instance
     * @throws IllegalArgumentException if device type is unknown
     */
    public static Device createDevice(DeviceType type, Room location, String name, DeviceAPI deviceAPI) {
        int id = devicerIdCounter++;
        DeviceState initialState = DeviceState.OFF;

        return switch (type) {
            case FRIDGE -> new Fridge(id, location, name, initialState);
            case SMART_WINDOW -> new SmartWindow(id, location, name, initialState);
            case AIR_CONDITIONER -> new AirConditioner(id, location, name, initialState);
            case BLINDS -> new Blinds(id, location, name, initialState);
            case STOVE -> new Stove(id, location, name, initialState);
            case CIRCUIT_BREAKER -> new CircuitBreaker(id, location, name, initialState, deviceAPI);
            case CD_PLAYER -> new CDplayer(id, location, name, initialState);
            case WASHING_MACHINE -> new WashingMachine(id, location, name, initialState);
            default -> throw new IllegalArgumentException("Unknown device type: " + type);
        };
    }

    /**
     * Creates sensor based on SensorType.
     * Assigns auto-incremented ID and initial ON state.
     * @param sensorType Type of sensor
     * @param location Room where sensor is located
     * @param name Sensor name
     * @return AbstractSensor instance
     */
    public static AbstractSensor createSensor(SensorType sensorType, Room location, String name) {
        int id = devicerIdCounter++;
        DeviceState initialState = DeviceState.ON; // Sensors start ON

        return switch (sensorType){
            case HUMIDITY -> new HumiditySensor(id, location, name, initialState);
            case CO2 -> new СO2Sensor(id, location, name, initialState);
            case WIND -> new WindSensor(id, location, name, initialState);
            case TEMPERATURE -> new TemperatureSensor(id, location, name, initialState);
        };
    }
}
