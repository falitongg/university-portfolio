package cz.cvut.fel.omo.entity.devices.factory;
import cz.cvut.fel.omo.entity.devices.Device;
import cz.cvut.fel.omo.entity.devices.DeviceAPI;
import cz.cvut.fel.omo.entity.devices.enums.DeviceState;
import cz.cvut.fel.omo.entity.devices.enums.DeviceType;
import cz.cvut.fel.omo.entity.house.Room;
import cz.cvut.fel.omo.event.Event;
import cz.cvut.fel.omo.event.EventType;

import java.util.List;

/**
 * Circuit breaker device.
 * Does not consume resources.
 */
public class CircuitBreaker extends Device {

    private final DeviceAPI deviceAPI;

    public CircuitBreaker(int id, Room location, String name, DeviceState state, DeviceAPI deviceAPI) {
        super(id, location, name, state, DeviceType.CIRCUIT_BREAKER);
        this.deviceAPI = deviceAPI;
    }

    /**
     * Handles an {@link EventType#ELECTRICITY_OUTAGE} event.
     * <p>
     * When a power outage occurs, the circuit breaker simulates
     * an emergency power cut by turning off all active electrical
     * devices except sensors.
     * <p>
     * Behavior:
     * <ul>
     *     <li>Ignores all events except {@link EventType#ELECTRICITY_OUTAGE}</li>
     *     <li>Retrieves all non-sensor devices via {@link DeviceAPI}</li>
     *     <li>Turns off only devices that are currently in {@link DeviceState#ON}</li>
     * </ul>
     *
     * This method represents system-level control of devices,
     * independent of household members, as required by the assignment
     * (e.g. automatic shutdown during critical situations).
     *
     * @param event Event to handle
     */
    @Override
    public void handle(Event event) {
        if (event.getEventType() != EventType.ELECTRICITY_OUTAGE) {
            return;
        }

        List<Device> devices = deviceAPI.getAllButSensors();

        for (Device device : devices) {
            if (device.getDeviceStateEnum() != DeviceState.ON) {
                continue;
            }
            deviceAPI.turnOff(device);
        }
    }

}
