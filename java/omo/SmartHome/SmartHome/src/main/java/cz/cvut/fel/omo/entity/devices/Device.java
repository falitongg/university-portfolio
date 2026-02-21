package cz.cvut.fel.omo.entity.devices;

import cz.cvut.fel.omo.entity.devices.state.*;
import cz.cvut.fel.omo.entity.devices.util.DeviceStateHandler;
import cz.cvut.fel.omo.entity.devices.enums.ConsumptionType;
import cz.cvut.fel.omo.entity.devices.enums.DeviceState;
import cz.cvut.fel.omo.entity.devices.enums.DeviceType;
import cz.cvut.fel.omo.entity.house.Room;
import cz.cvut.fel.omo.entity.people.HouseholdMember;
import cz.cvut.fel.omo.event.*;

import java.util.*;

import static cz.cvut.fel.omo.Constants.*;

/**
 * Base class for all smart home devices.
 * Uses State Pattern for device state transitions (OFF, ON, WORKING, BROKEN, REPAIRING).
 * Uses Flyweight Pattern for state handlers to save memory.
 * Implements EventSource for generating events and EventHandler for reacting to events.
 */
public class Device implements EventSource, EventHandler {

    // Identification
    private final int id;
    private final String name;
    private final DeviceType type;

    // Location
    private final Room location;

    // State Pattern + Flyweight Pattern
    private final DeviceStateHandler offState;
    private final DeviceStateHandler onState;
    private final DeviceStateHandler workingState;
    private final DeviceStateHandler brokenState;
    private final DeviceStateHandler repairingState;
    private DeviceStateHandler currentStateHandler;
    private DeviceState deviceStateEnum;

    // Statistics
    /**
     * Consumption per simulation tick.
     * Initialized in subclass constructors.
     */
    protected final Map<ConsumptionType, Double> consumptionPerTick;
    private final ConsumptionStats consumption;

    private HouseholdMember currentUser;

    /**
     * Creates a new device.
     * Protected constructor - use subclasses (Fridge, WashingMachine, etc.).
     * Initializes State Pattern handlers using Flyweight.
     * @param id Unique device identifier
     * @param location Room where device is located
     * @param name Device name
     * @param deviceStateEnum Initial device state
     * @param type Device type
     */
    protected Device(int id, Room location, String name, DeviceState deviceStateEnum, DeviceType type) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.location = location;
        this.consumptionPerTick = new HashMap<>();
        this.consumption = new ConsumptionStats();

        this.deviceStateEnum = deviceStateEnum;

        // Flyweight Pattern: reuse state handlers
        this.offState = new OffDeviceState(this);
        this.onState = new OnDeviceState(this);
        this.workingState = new WorkingDeviceState(this);
        this.brokenState = new BrokenDeviceState(this);
        this.repairingState = new RepairingDeviceState(this);
        this.currentStateHandler = offState;
    }

    /**
     * Checks if device can be used in current state.
     * @return true if device can be used
     */
    boolean canBeUsed() {
        return currentStateHandler.canBeUsed();
    }

    /**
     * Checks if device can be turned on in current state.
     * @return true if device can be turned on
     */
    boolean canBeTurnedOn() {
        return currentStateHandler.canBeTurnedOn();
    }

    /**
     * Checks if device can be turned off in current state.
     * @return true if device can be turned off
     */
    boolean canBeTurnedOff() {
        return currentStateHandler.canBeTurnedOff();
    }

    /**
     * Checks if device can be repaired in current state.
     * @return true if device is broken and can be repaired
     */
    boolean canBeRepaired() {
        return currentStateHandler.canBeRepaired();
    }

    /**
     * Performs use action (delegates to State Pattern handler).
     * @param member Person using the device
     */
    void performUse(HouseholdMember member) {
        currentStateHandler.onUse(member);
    }

    /**
     * Stops using device (delegates to State Pattern handler).
     * @param member Person stopping usage
     */
    void performStopUsing(HouseholdMember member) {
        currentStateHandler.onStopUsing(member);
    }

    /**
     * Turns on device (delegates to State Pattern handler).
     */
    void performTurnOn() {
        currentStateHandler.onTurnOn();

    }

    /**
     * Turns off device (delegates to State Pattern handler).
     */
    void performTurnOff() {
        currentStateHandler.onTurnOff();
    }

    /**
     * Breaks device (delegates to State Pattern handler).
     */
    void performBreak() {
        currentStateHandler.onBreak();
    }

    /**
     * Starts device repair (delegates to State Pattern handler).
     */
    void performStartRepair() {
        currentStateHandler.onStartRepair();
    }

    /**
     * Finishes device repair (delegates to State Pattern handler).
     */
    void performFinishRepair() {
        currentStateHandler.onFinishRepair();
    }

    /**
     * Gets device ID.
     * @return Device ID
     */
    public int getId() {
        return id;
    }

    /**
     * Gets device name.
     * @return Device name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets device type.
     * @return Device type
     */
    public DeviceType getType() {
        return type;
    }

    /**
     * Gets device location.
     * @return Room where device is located
     */
    public Room getLocation() {
        return location;
    }

    /**
     * Gets OFF state handler.
     * @return OFF state handler
     */
    public DeviceStateHandler getOffState() {
        return offState;
    }

    /**
     * Gets ON state handler.
     * @return ON state handler
     */
    public DeviceStateHandler getOnState() {
        return onState;
    }

    /**
     * Gets WORKING state handler.
     * @return WORKING state handler
     */
    public DeviceStateHandler getWorkingState() {
        return workingState;
    }

    /**
     * Gets BROKEN state handler.
     * @return BROKEN state handler
     */
    public DeviceStateHandler getBrokenState() {
        return brokenState;
    }

    /**
     * Gets REPAIRING state handler.
     * @return REPAIRING state handler
     */
    public DeviceStateHandler getRepairingState() {
        return repairingState;
    }

    /**
     * Gets current state handler.
     * @return Current state handler
     */
    public DeviceStateHandler getCurrentStateHandler() {
        return currentStateHandler;
    }

    /**
     * Gets current device state enum.
     * @return Current state
     */
    public DeviceState getDeviceStateEnum() {
        return deviceStateEnum;
    }

    /**
     * Gets consumption statistics.
     * @return Consumption stats
     */
    public ConsumptionStats getConsumption() {
        return consumption;
    }

    /**
     * Gets current user of the device.
     * @return Current user, or null if not in use
     */
    public HouseholdMember getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets current state handler (used by State Pattern transitions).
     * @param currentStateHandler New state handler
     */
    public void setCurrentStateHandler(DeviceStateHandler currentStateHandler) {
        this.currentStateHandler = currentStateHandler;
    }

    /**
     * Sets device state enum (used by State Pattern transitions).
     * @param deviceStateEnum New state
     */
    public void setDeviceStateEnum(DeviceState deviceStateEnum) {
        this.deviceStateEnum = deviceStateEnum;
    }

    /**
     * Sets current user of the device.
     * @param currentUser Person using the device, or null
     */
    public void setCurrentUser(HouseholdMember currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * Checks if device can handle specific event type.
     * @param event Event to check
     * @return true if device can handle this event
     */
    @Override
    public boolean canHandle(Event event) {
        return switch (event.getEventType()) {
            case WIND_ALERT -> type == DeviceType.BLINDS;
            case ELECTRICITY_OUTAGE -> type == DeviceType.CIRCUIT_BREAKER;
            case TEMPERATURE_ALERT -> type == DeviceType.AIR_CONDITIONER;
            case CO2_ALERT -> type == DeviceType.SMART_WINDOW;
            default -> false;
        };
    }

    /**
     * Checks if device is available to handle events.
     * @return true if device is in ON state
     */
    @Override
    public boolean isAvailable() {
        return currentStateHandler == offState;
    }

    /**
     * Gets priority for event handling.
     * @param event Event to handle
     * @return Priority value (higher = more priority)
     */
    @Override
    public int getPriority(Event event) {
        return switch (type) {
            default -> 10;
        };
    }

    /**
     * Handles an incoming event by performing a device-specific action.
     * <p>
     * The reaction depends on both the {@link EventType} and the concrete
     * {@link DeviceType} of this device:
     * <ul>
     *     <li>{@code TEMPERATURE_ALERT} → Air conditioner is turned on</li>
     *     <li>{@code WIND_ALERT} → Blinds are turned on (closed)</li>
     *     <li>{@code CO2_ALERT} → Smart window is turned on (opened)</li>
     * </ul>
     *
     * Other event types are intentionally ignored.
     * <p>
     * This method represents the device-side reaction in the event-driven
     * architecture and is typically invoked by {@link EventManager#dispatchEvent}.
     *
     * @param event event to handle
     */
    @Override
    public void handle(Event event) {
        switch (event.getEventType()) {
            case TEMPERATURE_ALERT -> {
                if (type == DeviceType.AIR_CONDITIONER) {
                    performTurnOn();
                }
            }
            case WIND_ALERT -> {
                if (type == DeviceType.BLINDS) {
                    performTurnOn();
                }
            }
            case CO2_ALERT -> {
                if (type == DeviceType.SMART_WINDOW) {
                    performTurnOn();
                }
            }
            default -> {
                // Event type not relevant for this device
            }
        }
    }

    /**
     * Simulates internal device events occurring during runtime.
     * <p>
     * If the device is currently in {@code WORKING} or {@code ON} state,
     * random failures may occur:
     * <ul>
     *     <li>Device malfunction with 10% probability</li>
     *     <li>Electricity outage with 2% probability</li>
     * </ul>
     *
     * When a failure occurs, the device is switched to a broken state
     * and a corresponding event is generated via {@link EventManager}.
     * <p>
     * If a household member is currently using the device, the event source
     * is set to that user; otherwise, the device itself is used as the source.
     *
     * This method is typically called during simulation ticks.
     */
    @Override
    public void eventOccuired() {
        EventManager em = EventManager.getInstance();

        if (currentStateHandler == workingState || currentStateHandler == onState) {

            if (Math.random() < DEFAULT_EVENT_CHANCE) {
                performBreak();

                if (currentUser == null) {
                    em.generateEvent(this, this, EventType.DEVICE_MALFUNCTION);
                } else {
                    em.generateEvent(currentUser, this, EventType.DEVICE_MALFUNCTION);
                }
                return;
            }

            if (Math.random() < ELECTRICITY_OUTAGE_PROBABILITY) {
                performBreak();
                em.generateEvent(this, this, EventType.ELECTRICITY_OUTAGE);
            }
        }
    }

    /**
     * Returns the name of this device as an event source identifier.
     * <p>
     * Used in event reporting to group events by their source.
     *
     * @return device name
     */
    @Override
    public String getSourceName() {
        return getName();
    }
    /**
     * Updates consumption statistics based on current state.
     * Only consumes resources when ON or WORKING.
     */
    public void updateConsumption(){
        if (!isConsuming()){
            return;
        }

        consumptionPerTick.forEach((key, amount) -> {
            if (amount > 0){
                consumption.addConsumption(key, amount);
            }
        });
    }

    /**
     * Checks if device is currently consuming resources.
     * @return true if device is ON or WORKING
     */
    private boolean isConsuming() {
        return deviceStateEnum == DeviceState.WORKING ||
                deviceStateEnum == DeviceState.ON;
    }



}