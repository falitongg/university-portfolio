package cz.cvut.fel.omo.entity.devices.state;

import cz.cvut.fel.omo.entity.devices.Device;
import cz.cvut.fel.omo.entity.devices.enums.DeviceState;

/**
 * REPAIRING state - device is being repaired.
 * Can only finish repair, which returns device to OFF state.
 */
public class RepairingDeviceState extends AbstractDeviceState {

    public RepairingDeviceState(Device device) {
        super(device);
    }

    /**
     * Finishes repair - returns device to OFF state.
     */
    @Override
    public void onFinishRepair() {
        changeToState(DeviceState.OFF);
        device.setDeviceStateEnum(DeviceState.OFF);
    }
}
