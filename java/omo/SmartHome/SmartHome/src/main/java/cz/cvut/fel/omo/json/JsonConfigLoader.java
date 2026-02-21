package cz.cvut.fel.omo.json;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

/**
 * Utility class for loading simulation configuration from JSON file.
 * Uses Jackson ObjectMapper for deserialization.
 */
public class JsonConfigLoader {

    /**
     * Loads simulation configuration from JSON file.
     * @param path Path to JSON configuration file
     * @return Parsed configuration object
     * @throws RuntimeException if file cannot be read or parsed
     */
    public static SimulationJsonConfig load(String path) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(
                    new File(path),
                    SimulationJsonConfig.class
            );
        } catch (IOException e) {
            throw new RuntimeException("Cannot load config", e);
        }
    }
}
