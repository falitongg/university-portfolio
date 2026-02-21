package cz.cvut.fel.omo.json;

import cz.cvut.fel.omo.entity.house.RoomType;

import java.util.List;

/**
 * Configuration data class for room from JSON.
 * Used for deserialization of room configuration.
 */
public class RoomConfig {
    public RoomType type;
    /** List of devices in this room */
    public List<DeviceConfig> devices;
}

