package cz.cvut.fel.omo.entity.devices;

import cz.cvut.fel.omo.entity.devices.factory.Fridge;
import cz.cvut.fel.omo.entity.devices.enums.DeviceState;
import cz.cvut.fel.omo.entity.devices.enums.DeviceType;
import cz.cvut.fel.omo.entity.house.Room;
import cz.cvut.fel.omo.event.EventManager;
import cz.cvut.fel.omo.event.EventType;

import static cz.cvut.fel.omo.Constants.CONTENT_EMPTY_PROBABILITY;
import static cz.cvut.fel.omo.Constants.DEFAULT_EVENT_CHANCE;

/**
 * Abstract base class for devices that can contain items.
 * Examples: Fridge (food), WashingMachine (clothes), CdPlayer (CDs).
 * Provides common functionality for managing content state.
 */
public abstract class DeviceWithContent extends Device {

    /**
     * Indicates whether device currently has content.
     */
    protected boolean hasContent;
    /**
     * Creates new DeviceWithContent instance.
     * Content is initially empty (hasContent = false).
     * @param id Unique device identifier
     * @param location Room where device is located
     * @param name Device name
     * @param state Initial device state
     * @param type Device type
     */
    public DeviceWithContent(int id, Room location, String name, DeviceState state, DeviceType type) {
        super(id, location, name, state, type);
        this.hasContent = true;
    }

    /**
     * Adds content to device.
     * Sets hasContent flag to true.
     * Example: Fill fridge with food, load washing machine with clothes.
     */
    public void addContent() {
        this.hasContent = true;
    }

    /**
     * Removes content from device.
     * Sets hasContent flag to false.
     * Example: Take food from fridge, unload washing machine.
     */
    public void removeContent() {
        this.hasContent = false;
    }

    /**
     * Checks if device currently has content.
     * @return true if device has content, false otherwise
     */
    public boolean hasContent() {
        return hasContent;
    }

    /**
     * Sets content state directly.
     * @param hasContent New content state (true = has content, false = empty)
     */
    public void setHasContent(boolean hasContent) {
        this.hasContent = hasContent;
    }

    /**
     * Simulates content-related and failure events for devices that contain
     * consumable or usable content (e.g. fridge, CD player).
     * <p>
     * Events are generated only if the device is in an active state
     * ({@link DeviceState#WORKING} or {@link DeviceState#ON}).
     * <p>
     * Possible events:
     * <ul>
     *     <li><b>DEVICE_MALFUNCTION</b> – 10% probability
     *         Device randomly breaks during operation.</li>
     *     <li><b>FOOD_EMPTY</b> – 20% probability (for {@link Fridge})
     *         Content is consumed and the fridge becomes empty.</li>
     *     <li><b>CDS_ENDED</b> – 20% probability (for other content devices)
     *         All CDs are consumed or finished.</li>
     * </ul>
     *
     * When a content-related event occurs, the device content is removed
     * before the event is generated.
     * <p>
     * Generated events are registered in the {@link EventManager} and later
     * dispatched to appropriate handlers.
     */
    @Override
    public void eventOccuired() {
        EventManager em = EventManager.getInstance();

        if (getDeviceStateEnum() == DeviceState.WORKING
                || getDeviceStateEnum() == DeviceState.ON) {

            if (Math.random() < DEFAULT_EVENT_CHANCE) {
                em.generateEvent(this, this, EventType.DEVICE_MALFUNCTION);
                return;
            }

            if (Math.random() < CONTENT_EMPTY_PROBABILITY) {
                removeContent();

                if (this instanceof Fridge) {
                    em.generateEvent(this, this, EventType.FOOD_EMPTY);
                } else {
                    em.generateEvent(this, this, EventType.CDS_ENDED);
                }
            }
        }
    }

}
