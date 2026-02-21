package cz.cvut.fel.omo.json;

import cz.cvut.fel.omo.entity.devices.enums.DeviceType;

/**
 * Configuration data class for device from JSON.
 * Used for deserialization of device configuration.
 */
public class DeviceConfig {
    public DeviceType type;
    public String sensorType;
    public String name;
}

