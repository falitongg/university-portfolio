package cz.cvut.fel.omo.entity.devices.state;

import cz.cvut.fel.omo.entity.devices.Device;
import cz.cvut.fel.omo.entity.devices.enums.DeviceState;
import cz.cvut.fel.omo.entity.people.HouseholdMember;

/**
 * ON state - device is powered on but idle.
 * Can be used or turned off.
 */
public class OnDeviceState extends AbstractDeviceState {

    public OnDeviceState(Device device) {
        super(device);
    }

    @Override
    public boolean canBeUsed(){
        return true;
    }

    @Override
    public boolean canBeTurnedOff(){
        return true;
    }

    /**
     * Starts using device - transitions to WORKING state.
     * @param member Person using the device
     */
    @Override
    public void onUse(HouseholdMember member){
        device.setCurrentUser(member);
        changeToState(DeviceState.WORKING);
        device.setDeviceStateEnum(DeviceState.WORKING);
    }

    @Override
    public void onTurnOff() {
        device.setCurrentUser(null);
        changeToState(DeviceState.OFF);
        device.setDeviceStateEnum(DeviceState.OFF);
    }
}
