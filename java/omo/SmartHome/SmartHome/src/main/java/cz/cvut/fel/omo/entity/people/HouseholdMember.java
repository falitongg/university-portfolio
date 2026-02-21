package cz.cvut.fel.omo.entity.people;

import cz.cvut.fel.omo.entity.devices.Device;
import cz.cvut.fel.omo.entity.devices.DeviceAPI;
import cz.cvut.fel.omo.entity.devices.DeviceWithContent;
import cz.cvut.fel.omo.entity.house.Room;
import cz.cvut.fel.omo.entity.people.util.ActivityState;
import cz.cvut.fel.omo.entity.sport.SportsEquipment;
import cz.cvut.fel.omo.event.*;

import java.util.*;

import static cz.cvut.fel.omo.Constants.*;
import static cz.cvut.fel.omo.entity.people.RoleName.*;

/**
 * Represents a person living in the house.
 * Can use devices and sports equipment based on role permissions.
 * Implements EventHandler for reacting to events and EventSource for generating events.
 */
public class HouseholdMember implements EventHandler, EventSource {

    private final String name;
    private final Role role;
    private Room currentRoom;
    private ActivityState state;
    private final DeviceAPI deviceAPI;
    private final Map<Device, Integer> deviceUsageCount;
    private final Map<SportsEquipment, Integer> sportsEquipmentUsageCount;

    /**
     * Creates a new household member.
     * Uses Dependency Injection for DeviceAPI.
     * @param name Person's name
     * @param role Person's role (defines permissions)
     * @param initialRoom Starting room
     * @param deviceAPI API for device interactions
     */
    public HouseholdMember(String name, Role role, Room initialRoom, DeviceAPI deviceAPI){
        this.name = name;
        this.role = role;
        this.currentRoom = initialRoom;
        this.state = ActivityState.IDLE;
        this.deviceUsageCount = new HashMap<>();
        this.sportsEquipmentUsageCount = new HashMap<>();
        this.deviceAPI = deviceAPI;
        initialRoom.addHouseholdMember(this);
    }

    /**
     * Uses device if person is available and has permission.
     * Records usage for statistics.
     * @param device Device to use
     * @return true if successfully started using device
     */
    public boolean useDevice(Device device) {
        if (!isAvailable()){
            return false;
        }

        if (!canPerform(device, Permission.USE)){
            return false;
        }

        boolean success = deviceAPI.use(device, this);
        if (success){
            moveTo(device.getLocation());
            this.state = ActivityState.USING_DEVICE;
            recordDeviceUsage(device);
            stopUsingDevice(device);
        }

        return success;
    }

    /**
     * Stops using device and returns to idle state.
     * @param device Device to stop using
     */
    public void stopUsingDevice(Device device) {
        deviceAPI.stopUsing(device, this);
        this.state = ActivityState.IDLE;

    }

    /**
     * Turns on device if person has permission.
     * @param device Device to turn on
     * @return true if successfully turned on
     */
    public boolean turnOnDevice(Device device) {
        if (!canPerform(device, Permission.TURN_ON_OFF)) {
            return false;
        }
        return deviceAPI.turnOn(device);
    }

    /**
     * Turns off device if person has permission.
     * @param device Device to turn off
     * @return true if successfully turned off
     */
    public boolean turnOffDevice(Device device) {
        if (!canPerform(device, Permission.TURN_ON_OFF)) {
            return false;
        }
        return deviceAPI.turnOff(device);
    }

    /**
     * Repairs broken device if person has permission.
     * @param device Device to repair
     * @return true if successfully started repair
     */
    public boolean repairDevice(Device device) {
        if (!canPerform(device, Permission.REPAIR)) {
            return false;
        }
        return deviceAPI.repair(device, this);
    }

    /**
     * Uses sports equipment if person is available.
     * Records usage for statistics.
     * @param sportsEquipment Equipment to use
     * @return true if successfully started using equipment
     */
    public boolean useSportsEquipment(SportsEquipment sportsEquipment) {
        if (!isAvailable()){
            return false;
        }
        if (role.getRoleName() == RoleName.BABY || role.getRoleName() == RoleName.PET){
            return false;
        }
        boolean success = sportsEquipment.useEquipment(this);
        if (success){
            moveTo(sportsEquipment.getLocation());
            this.state = ActivityState.DOING_SPORT;
            recordSportsEquipmentUsage(sportsEquipment);
        }
        stopUsingSportsEquipment(sportsEquipment);
        return success;
    }

    /**
     * Stops using sports equipment and returns to idle state.
     * @param sportsEquipment Equipment to stop using
     */
    public void stopUsingSportsEquipment(SportsEquipment sportsEquipment) {
        sportsEquipment.releaseEquipment(this);
        this.state = ActivityState.IDLE;
    }

    /**
     * Records device usage for activity report.
     * @param device Device that was used
     */
    private void recordDeviceUsage(Device device) {
        deviceUsageCount.merge(device, 1, Integer::sum);
    }

    /**
     * Records sports equipment usage for activity report.
     * @param sportsEquipment Equipment that was used
     */
    private void recordSportsEquipmentUsage(SportsEquipment sportsEquipment) {
        sportsEquipmentUsageCount.merge(sportsEquipment, 1, Integer::sum);
    }

    /**
     * Checks if person can perform action on device.
     * @param device Device to check
     * @param permission Required permission
     * @return true if person has permission
     */
    public boolean canPerform(Device device, Permission permission) {
        return getPermissions(device).contains(permission);
    }

    /**
     * Gets all permissions for specific device based on role.
     * @param device Device to check permissions for
     * @return Set of allowed permissions
     */
    public Set<Permission> getPermissions(Device device) {
        return role.getAllowedPermissions(device.getType());
    }

    /**
     * Moves person to another room.
     * Updates both old and new room's household member lists.
     * @param newRoom Room to move to
     */
    public void moveTo(Room newRoom) {
        if (currentRoom != null) {
            currentRoom.removeHouseholdMember(this);
        }
        this.currentRoom = newRoom;
        newRoom.addHouseholdMember(this);
    }

    /**
     * Gets person's name.
     * @return Name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets sports equipment usage statistics.
     * @return Unmodifiable map of equipment to usage count
     */
    public Map<SportsEquipment, Integer> getSportsEquipmentUsageCount() {
        return Collections.unmodifiableMap(sportsEquipmentUsageCount);
    }

    /**
     * Gets device usage statistics.
     * @return Unmodifiable map of device to usage count
     */
    public Map<Device, Integer> getDeviceUsageCount() {
        return Collections.unmodifiableMap(deviceUsageCount);
    }

    /**
     * Gets current activity state.
     * @return Activity state
     */
    public ActivityState getState() {
        return state;
    }

    /**
     * Gets current room location.
     * @return Current room
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }

    /**
     * Gets person's role.
     * @return Role
     */
    public Role getRole() {
        return role;
    }

    public void finishActivityIfNeeded() {
        if (state == ActivityState.HANDLING_EVENT) {
            state = ActivityState.IDLE;
        }
    }
    /**
     * Checks if person can handle specific event type based on role.
     * @param event Event to check
     * @return true if person can handle this event
     */
    @Override
    public boolean canHandle(Event event) {
        return switch (event.getEventType()) {
            case BABY_CRYING -> role.getRoleName() == MOM || role.getRoleName() == DAD;
            case PET_HUNGRY -> role.getRoleName() != RoleName.BABY && role.getRoleName() != PET;
            case FOOD_EMPTY -> role.getRoleName() != RoleName.BABY && role.getRoleName() != PET;
            case CDS_ENDED -> role.getRoleName() != RoleName.BABY && role.getRoleName() != PET;
            case DEVICE_MALFUNCTION -> role.getRoleName() == MOM || role.getRoleName() == DAD;
            case WATER_LEAK -> role.getRoleName() == MOM || role.getRoleName() == DAD;
            default -> false;
        };
    }

    /**
     * Checks if person is available to handle events.
     * @return true if idle or waiting
     */
    @Override
    public boolean isAvailable() {
        return getState() == ActivityState.IDLE;
    }

    /**
     * Gets priority for event handling based on role.
     * Higher priority handlers are chosen first.
     * @param event Event to handle
     * @return Priority value (higher = more priority)
     */
    @Override
    public int getPriority(Event event) {
        return switch (role.getRoleName()) {
            case MOM -> MOM_SKILL_LEVEL;
            case DAD -> DAD_SKILL_LEVEL;
            case DAUGHTER -> DAUGHTER_SKILL_LEVEL;
            case SON -> SON_SKILL_LEVEL;
            default -> DEFAULT_SKILL_LEVEL;
        };
    }

    /**
     * Handles an incoming event by performing an appropriate action
     * as a household member.
     * <p>
     * The behavior depends on the type of event target:
     * <ul>
     *     <li><b>Device</b>:
     *         <ul>
     *             <li>{@code DEVICE_MALFUNCTION} → device is repaired</li>
     *             <li>{@code FOOD_EMPTY} or {@code CDS_ENDED} → content is refilled</li>
     *         </ul>
     *     </li>
     *     <li><b>HouseholdMember</b>:
     *         <ul>
     *             <li>The handler moves to the member's current room</li>
     *         </ul>
     *     </li>
     * </ul>
     *
     * If the event target is located in a different room,
     * the household member moves to that room before handling the event.
     * <p>
     * After processing the event, the member's activity state is set to
     * {@link ActivityState#HANDLING_EVENT}.
     *
     * @param event event to handle
     */
    @Override
    public void handle(Event event) {
        Object target = event.getTarget();
        Room targetRoom = null;

        if (target instanceof Device device) {
            targetRoom = device.getLocation();

            if (event.getEventType() == EventType.DEVICE_MALFUNCTION) {
                repairDevice(device);
            }

            if (device instanceof DeviceWithContent deviceWithContent) {
                if (event.getEventType() == EventType.FOOD_EMPTY
                        || event.getEventType() == EventType.CDS_ENDED) {
                    deviceWithContent.addContent();
                }
            }

        } else if (target instanceof HouseholdMember member) {
            targetRoom = member.getCurrentRoom();
        }

        if (targetRoom != null && !getCurrentRoom().equals(targetRoom)) {
            moveTo(targetRoom);
        }

        state = ActivityState.HANDLING_EVENT;
    }

    /**
     * Simulates spontaneous events generated by a household member.
     * <p>
     * Certain roles can randomly generate specific events:
     * <ul>
     *     <li>{@code BABY} → {@code BABY_CRYING} (10% probability)</li>
     *     <li>{@code PET} → {@code PET_HUNGRY} (10% probability)</li>
     * </ul>
     *
     * Generated events are registered in the {@link EventManager}
     * and later dispatched to available handlers.
     * <p>
     * This method is typically called during simulation ticks.
     */
    @Override
    public void eventOccuired() {
        EventManager em = EventManager.getInstance();

        if (role.getRoleName() == RoleName.BABY) {
            if (Math.random() < DEFAULT_EVENT_CHANCE) {
                em.generateEvent(this, this, EventType.BABY_CRYING);
                return;
            }
        }

        if (role.getRoleName() == PET) {
            if (Math.random() < DEFAULT_EVENT_CHANCE) {
                em.generateEvent(this, this, EventType.PET_HUNGRY);
            }
        }
    }

    /**
     * Gets source name for event generation.
     * @return Person's name
     */
    @Override
    public String getSourceName() {
        return getName();
    }
}
