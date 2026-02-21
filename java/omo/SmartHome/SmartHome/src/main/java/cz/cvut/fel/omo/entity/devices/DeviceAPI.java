package cz.cvut.fel.omo.entity.devices;

import cz.cvut.fel.omo.entity.devices.enums.DeviceState;
import cz.cvut.fel.omo.entity.devices.enums.DeviceType;
import cz.cvut.fel.omo.entity.people.HouseholdMember;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Facade for device operations.
 * Provides simplified interface for device interactions with validation.
 * Manages device registry and batch operations.
 */
public class DeviceAPI {

    private final List<Device> devices;

    /**
     * Creates new DeviceAPI instance.
     */
    public DeviceAPI() {
        this.devices = new ArrayList<>();
    }

    /**
     * Registers device in the system.
     * @param device Device to register
     */
    public void registerDevice(Device device) {
        devices.add(device);
    }

    /**
     * Gets all registered devices.
     * @return Unmodifiable list of devices
     */
    public List<Device> getDevices() {
        return Collections.unmodifiableList(devices);
    }

    /**
     * Starts using device with validation.
     * Validates that device and member are not null and device can be used.
     * @param device Device to use
     * @param member Person using the device
     * @return true if successfully started using
     */
    public boolean use(Device device, HouseholdMember member) {
        if (device == null || member == null) return false;
        if (!device.canBeUsed()) return false;
        device.performUse(member);
        return true;
    }

    /**
     * Stops using device.
     * @param device Device to stop using
     * @param member Person stopping usage
     */
    public void stopUsing(Device device, HouseholdMember member) {
        if (device == null || member == null) return;
        device.performStopUsing(member);
    }

    /**
     * Turns on device with validation.
     * @param device Device to turn on
     * @return true if successfully turned on
     */
    public boolean turnOn(Device device){
        if (device == null) return false;
        if (!device.canBeTurnedOn()) return false;
        device.performTurnOn();
        return true;
    }

    /**
     * Turns off device with validation.
     * @param device Device to turn off
     * @return true if successfully turned off
     */
    public boolean turnOff(Device device){
        if (device == null) return false;
        if (!device.canBeTurnedOff()) return false;
        device.performTurnOff();
        return true;
    }

    /**
     * Repairs broken device.
     * Validates that device is broken and can be repaired.
     * @param device Device to repair
     * @param member Person performing repair
     * @return true if successfully started repair
     */
    public boolean repair(Device device, HouseholdMember member){
        if (device == null || member == null) return false;
        if (!device.canBeRepaired()) return false;
        device.performStartRepair();
        return true;
    }

    /**
     * Finishes repair of device.
     * Validates that device is currently being repaired.
     * @param device Device to finish repairing
     * @return true if successfully finished repair
     */
    public boolean finishRepair(Device device){
        if (device == null) return false;
        if (device.getDeviceStateEnum() != DeviceState.REPAIRING) return false;
        device.performFinishRepair();
        return true;
    }

    /**
     * Finishes all ongoing repairs.
     * Batch operation that completes repairs for all devices in REPAIRING state.
     */
    public void finishAllRepairs(){
        for (Device device : devices) {
            if (device.getDeviceStateEnum() == DeviceState.REPAIRING){
                finishRepair(device);
            }
        }
    }

    /**
     * Updates consumption statistics for all registered devices.
     * Batch operation called each simulation tick.
     */
    public void updateConsumptionForAllDevices() {
        for (Device device : devices) {
            device.updateConsumption();
        }
    }

    /**
     * Gets devices that can be actively used by household members.
     * Filters devices that people interact with (excludes sensors, windows, etc.).
     * @return List of usable devices (WashingMachine, Stove, Fridge, CDPlayer)
     */
    public List<Device> getUsableDevices(){
        List<Device> usableDevices = new ArrayList<>();
        for (Device device : devices) {
            DeviceType type = device.getType();
            if(type == DeviceType.WASHING_MACHINE || type == DeviceType.STOVE || type == DeviceType.FRIDGE || type == DeviceType.CD_PLAYER){
                usableDevices.add(device);
            }
        }
        return usableDevices;
    }

    /**
     * Gets all devices except sensors.
     * Used for operations that shouldn't affect sensors (e.g., turning off all devices).
     * @return List of non-sensor devices
     */
    public List<Device> getAllButSensors(){
        List<Device> result = new ArrayList<>();
        for (Device device : devices) {
            if (device.getType() != DeviceType.SENSOR){
                result.add(device);
            }
        }
        return result;
    }

    /**
     * Turns off all devices except sensors.
     * Batch operation for shutting down house (sensors stay active for monitoring).
     */
    public void turnOffAllButSensors(){
        for (Device device : getAllButSensors()) {
            if (device.canBeTurnedOff()){
                turnOff(device);
            }
        }
    }
}
