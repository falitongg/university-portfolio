package cz.cvut.fel.omo.json;

/**
 * Configuration data class for household member from JSON.
 * Used for deserialization of person configuration.
 */
public class PersonConfig {
    public String name;

    /** Person's role (MOM, DAD, SON, DAUGHTER, BABY, PET) */
    public String role;
}