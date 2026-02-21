package cz.cvut.fel.omo.entity.devices.state;

import cz.cvut.fel.omo.entity.devices.Device;
import cz.cvut.fel.omo.entity.devices.enums.DeviceState;

/**
 * BROKEN state - device is malfunctioning.
 * Can only be repaired, cannot break further.
 */
public class BrokenDeviceState extends AbstractDeviceState {

    public BrokenDeviceState(Device device) {
        super(device);
    }

    @Override
    public boolean canBeRepaired() {
        return true;
    }

    @Override
    public void onStartRepair() {
        changeToState(DeviceState.REPAIRING);
        device.setDeviceStateEnum(DeviceState.REPAIRING);
    }

    /**
     * Device is already broken - do nothing.
     */
    @Override
    public void onBreak() {
        device.setDeviceStateEnum(DeviceState.BROKEN);
        // Already broken, ignore
    }
}
