package cz.cvut.fel.omo.json;

/**
 * Configuration data class for sports equipment from JSON.
 * Used for deserialization of sports equipment configuration.
 */

public class SportConfig {
    /** Type of sports equipment (e.g. BIKE, BALL, TREADMILL) */
    public String type;

    /** Room name where the equipment is located */
    public String room;

    /** Number of available pieces */
    public int amount;
}
