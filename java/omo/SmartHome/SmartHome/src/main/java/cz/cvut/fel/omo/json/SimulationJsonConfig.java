package cz.cvut.fel.omo.json;

import java.util.List;

/**
 * Root configuration data class for simulation from JSON.
 * Contains all configuration data for house, people, and sports equipment.
 * Used for deserialization by Jackson ObjectMapper.
 */
public class SimulationJsonConfig {

    /** List of floors in the house */
    public List<FloorConfig> floors;

    /** List of household members */
    public List<PersonConfig> people;

    /** List of sports equipment (optional) */
    public List<SportConfig> sports;
}