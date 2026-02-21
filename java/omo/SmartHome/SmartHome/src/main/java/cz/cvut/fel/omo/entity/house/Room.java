package cz.cvut.fel.omo.entity.house;

import cz.cvut.fel.omo.entity.sport.SportsEquipment;
import cz.cvut.fel.omo.entity.devices.Device;
import cz.cvut.fel.omo.entity.people.HouseholdMember;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a room in the house.
 * Contains devices, household members, and sports equipment.
 */
public class Room {

    private final List<Device> devices;
    private final List<HouseholdMember> householdMembers;
    private final List<SportsEquipment> sportsEquipments;
    private final RoomType type;
    private final Floor floor;

    /**
     * Creates a new room of specified type.
     * @param type Room type (KITCHEN, BEDROOM, BATHROOM, etc.)
     */
    public Room(RoomType type, Floor floor) {
        this.devices = new ArrayList<>();
        this.householdMembers = new ArrayList<>();
        this.sportsEquipments = new ArrayList<>();
        this.type = type;
        this.floor = floor;
    }

    public Floor getFloor() {
        return floor;
    }
    /**
     * Adds device to this room.
     * @param device Device to add
     */
    public void addDevice(Device device) {
        devices.add(device);
    }

    /**
     * Adds household member to this room.
     * @param householdMember Person to add
     */
    public void addHouseholdMember(HouseholdMember householdMember) {
        householdMembers.add(householdMember);
    }

    /**
     * Adds sports equipment to this room.
     * @param sportsEquipment Equipment to add
     */
    public void addSportsEquipment(SportsEquipment sportsEquipment) {
        sportsEquipments.add(sportsEquipment);
    }

    /**
     * Gets all devices in this room.
     * @return Unmodifiable list of devices
     */
    public List<Device> getDevices() {
        return Collections.unmodifiableList(devices);
    }

    /**
     * Gets all household members currently in this room.
     * @return Unmodifiable list of people
     */
    public List<HouseholdMember> getHouseholdMembers() {
        return Collections.unmodifiableList(householdMembers);
    }

    /**
     * Gets all sports equipment in this room.
     * @return Unmodifiable list of equipment
     */
    public List<SportsEquipment> getSportsEquipments() {
        return Collections.unmodifiableList(sportsEquipments);
    }

    /**
     * Gets room type.
     * @return Room type
     */
    public RoomType getType() {
        return type;
    }

    /**
     * Gets room name based on type.
     * @return Room name as string
     */
    public String getName() {
        return type.toString();
    }

    /**
     * Removes device from this room.
     * @param device Device to remove
     */
    public void removeDevice(Device device) {
        devices.remove(device);
    }

    /**
     * Removes household member from this room.
     * @param householdMember Person to remove
     */
    public void removeHouseholdMember(HouseholdMember householdMember) {
        householdMembers.remove(householdMember);
    }

    /**
     * Removes sports equipment from this room.
     * @param sportsEquipment Equipment to remove
     */
    public void removeSportsEquipment(SportsEquipment sportsEquipment) {
        sportsEquipments.remove(sportsEquipment);
    }



}
