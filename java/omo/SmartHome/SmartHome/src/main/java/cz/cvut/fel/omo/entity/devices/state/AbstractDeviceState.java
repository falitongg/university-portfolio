package cz.cvut.fel.omo.entity.devices.state;

import cz.cvut.fel.omo.entity.devices.Device;
import cz.cvut.fel.omo.entity.devices.util.DeviceStateHandler;
import cz.cvut.fel.omo.entity.devices.enums.DeviceState;
import cz.cvut.fel.omo.entity.people.HouseholdMember;

/**
 * Abstract base class for device states using State Pattern.
 * Implements Flyweight Pattern for state reuse.
 * Provides default implementations that throw exceptions - subclasses override allowed operations.
 */
public abstract class AbstractDeviceState implements DeviceStateHandler {

    protected final Device device;

    /**
     * Creates state handler for device.
     * @param device Device to handle state for
     */
    protected AbstractDeviceState(Device device) {
        this.device = device;
    }

    /**
     * Changes device to target state using Flyweight pattern.
     * Reuses existing state handlers instead of creating new ones.
     * @param targetState State to transition to
     */
    protected void changeToState(DeviceState targetState) {
        device.setCurrentStateHandler(getStateHandler(targetState));
        device.setDeviceStateEnum(targetState);
    }

    /**
     * Gets reusable state handler for target state (Flyweight pattern).
     * @param targetState Target state
     * @return State handler instance
     */
    private DeviceStateHandler getStateHandler(DeviceState targetState) {
        return switch (targetState) {
            case OFF -> device.getOffState();
            case ON -> device.getOnState();
            case WORKING -> device.getWorkingState();
            case BROKEN -> device.getBrokenState();
            case REPAIRING -> device.getRepairingState();
        };
    }

    @Override
    public boolean canBeTurnedOff() {
        return false;
    }

    @Override
    public boolean canBeUsed() {
        return false;
    }

    @Override
    public boolean canBeTurnedOn() {
        return false;
    }

    @Override
    public boolean canBeRepaired() {
        return false;
    }

    @Override
    public void onUse(HouseholdMember member) {
        throw new IllegalStateException("Cannot be called in current state");
    }

    @Override
    public void onStopUsing(HouseholdMember member) {
        throw new IllegalStateException("Cannot be called in current state");
    }

    @Override
    public void onTurnOn() {
        throw new IllegalStateException("Cannot be called in current state");
    }

    @Override
    public void onTurnOff() {
        throw new IllegalStateException("Cannot be called in current state");
    }

    /**
     * Breaks device - allowed from any state.
     */
    @Override
    public void onBreak() {
        changeToState(DeviceState.BROKEN);
    }

    @Override
    public void onStartRepair() {
        throw new IllegalStateException("Cannot be called in current state");
    }

    @Override
    public void onFinishRepair() {
        throw new IllegalStateException("Cannot be called in current state");
    }
}
