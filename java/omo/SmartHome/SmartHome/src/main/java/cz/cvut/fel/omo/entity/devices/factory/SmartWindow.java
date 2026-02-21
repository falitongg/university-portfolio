package cz.cvut.fel.omo.entity.devices.factory;

import cz.cvut.fel.omo.entity.devices.Device;
import cz.cvut.fel.omo.entity.devices.enums.ConsumptionType;
import cz.cvut.fel.omo.entity.devices.enums.DeviceState;
import cz.cvut.fel.omo.entity.devices.enums.DeviceType;
import cz.cvut.fel.omo.entity.house.Room;

import static cz.cvut.fel.omo.Constants.SMART_WINDOW_CONSUMPTION_ELECTRICITY;

/**
 * Smart window device.
 * Minimal electricity consumption for automation.
 */
public class SmartWindow extends Device {

    public SmartWindow(int id, Room location, String name, DeviceState state) {
        super(id, location, name, state, DeviceType.SMART_WINDOW);
        consumptionPerTick.put(ConsumptionType.ELECTRICITY, SMART_WINDOW_CONSUMPTION_ELECTRICITY);
    }
}
