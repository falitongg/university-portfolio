package cz.cvut.fel.omo.json;

import java.util.List;

/**
 * Configuration data class for floor from JSON.
 * Used for deserialization of floor configuration.
 */
public class FloorConfig {
    public int level;
    public String name;
    public List<RoomConfig> rooms;
}