package cz.cvut.fel.omo.entity.house;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a single floor in the house.
 * Contains multiple rooms and has a level number.
 */
public class Floor {

    private final int level;
    private final String name;
    private final List<Room> rooms;
    private final House house;

    /**
     * Creates a new floor.
     * @param level Floor level number (0 = ground floor, 1 = first floor, etc.)
     * @param name Floor name
     */
    public Floor(int level, String name, House house) {
        this.level = level;
        this.name = name;
        rooms = new ArrayList<>();
        this.house = house;
    }
public House getHouse() {
        return house;
}
    /**
     * Gets floor name.
     * @return Floor name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets floor level number.
     * @return Floor level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Adds room to this floor.
     * @param room Room to add
     */
    public void addRoom(Room room) {
        rooms.add(room);
    }

    /**
     * Gets all rooms on this floor.
     * @return Unmodifiable list of rooms
     */
    public List<Room> getRooms() {
        return Collections.unmodifiableList(rooms);
    }
}
