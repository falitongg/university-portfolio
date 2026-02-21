package cz.cvut.fel.omo.entity.sport;

import cz.cvut.fel.omo.entity.house.Room;
import cz.cvut.fel.omo.entity.people.HouseholdMember;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents sports equipment available in the house.
 * Supports multiple instances with availability tracking.
 * Examples: skis, bicycles.
 */
public class SportsEquipment {

    private final SportsEquipmentType type;
    private final Room location;
    private final int totalAmount;
    private int amountAvailable;
    private final List<HouseholdMember> currentUsers;

    /**
     * Creates sports equipment with specified quantity.
     * All items start as available.
     * @param location Room where equipment is stored
     * @param type Type of sports equipment
     * @param totalAmount Total number of items
     */
    public SportsEquipment(Room location, SportsEquipmentType type, int totalAmount) {
        this.type = type;
        this.location = location;
        this.totalAmount = totalAmount;
        this.amountAvailable = totalAmount;
        this.currentUsers = new ArrayList<>();
    }

    /**
     * Gets equipment type.
     * @return Equipment type
     */
    public SportsEquipmentType getType() {
        return type;
    }

    /**
     * Checks if at least one item is available.
     * @return true if amountAvailable > 0
     */
    public boolean isAvailable() {
        return amountAvailable > 0;
    }

    /**
     * Releases equipment back to pool.
     * Increases amountAvailable by 1.
     * @param member Person returning the equipment
     * @throws IllegalStateException if member is not using this equipment
     */
    public void releaseEquipment(HouseholdMember member) {
        if (!currentUsers.remove(member)) {
            throw new IllegalStateException(
                    "Bug: " + member.getName() + " is not using " + type
            );
        }
        amountAvailable++;
    }

    /**
     * Takes equipment from pool if available.
     * Decreases amountAvailable by 1.
     * @param member Person taking the equipment
     * @return true if successfully taken, false if none available
     */
    public boolean useEquipment(HouseholdMember member) {
        if (!isAvailable()) {
            return false;
        }
        currentUsers.add(member);
        amountAvailable--;
        return true;
    }

    /**
     * Gets list of current users.
     * Uses defensive copy to protect internal state.
     * @return Unmodifiable list of current users
     */
    public List<HouseholdMember> getCurrentUsers() {
        return Collections.unmodifiableList(currentUsers);
    }

    /**
     * Gets storage location.
     * @return Room where equipment is stored
     */
    public Room getLocation() {
        return location;
    }

    /**
     * Gets total quantity of equipment.
     * @return Total amount (doesn't change)
     */
    public int getTotalAmount() {
        return totalAmount;
    }

    /**
     * Gets number of available items.
     * @return Amount available for use
     */
    public int getAmountAvailable() {
        return amountAvailable;
    }
}
