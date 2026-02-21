package cz.cvut.fel.omo.entity.devices.state;

import cz.cvut.fel.omo.entity.devices.Device;
import cz.cvut.fel.omo.entity.devices.enums.DeviceState;
import cz.cvut.fel.omo.entity.people.HouseholdMember;

/**
 * WORKING state - device is actively being used.
 * Only current user can stop using it.
 */
public class WorkingDeviceState extends AbstractDeviceState {

    public WorkingDeviceState(Device device) {
        super(device);
    }

    /**
     * Stops using device - returns to ON state.
     * Validates that only current user can stop.
     * @param member Person stopping usage
     * @throws IllegalStateException if member is not current user
     */
    @Override
    public void onStopUsing(HouseholdMember member) {
        changeToState(DeviceState.ON);
        device.setCurrentUser(null);
        device.setDeviceStateEnum(DeviceState.ON);
    }
}
