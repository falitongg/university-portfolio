package cz.cvut.fel.omo.entity.devices.state;

import cz.cvut.fel.omo.entity.devices.Device;
import cz.cvut.fel.omo.entity.devices.enums.DeviceState;

/**
 * OFF state - device is powered off.
 * Can only be turned on.
 */
public class OffDeviceState extends AbstractDeviceState {

    public OffDeviceState(Device device) {
        super(device);
    }

    @Override
    public boolean canBeTurnedOn() {
        return true;
    }

    @Override
    public void onTurnOn() {
        changeToState(DeviceState.ON);
        device.setDeviceStateEnum(DeviceState.ON);
    }
}
