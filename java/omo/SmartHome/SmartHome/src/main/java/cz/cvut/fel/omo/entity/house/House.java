package cz.cvut.fel.omo.entity.house;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class House {


    private final List<Floor> floors;
    private final Random random = new Random();

    public House() {
        this.floors = new ArrayList<>();
    }



    /**
     * Adds floor to the house.
     * @param floor Floor to add
     */
    public void addFloor(Floor floor) {
        floors.add(floor);
    }

    /**
     * Gets all floors in the house.
     * @return Unmodifiable list of floors
     */
    public List<Floor> getFloors() {
        return Collections.unmodifiableList(floors);
    }
    public Room getRandomRoom() {
        List<Room> allRooms = floors.stream()
                .flatMap(f -> f.getRooms().stream())
                .toList();

        if (allRooms.isEmpty()) {
            throw new IllegalStateException("House has no rooms");
        }

        return allRooms.get(random.nextInt(allRooms.size()));
    }
}
