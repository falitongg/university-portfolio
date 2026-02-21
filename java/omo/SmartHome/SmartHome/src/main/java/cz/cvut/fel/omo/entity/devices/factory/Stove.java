package cz.cvut.fel.omo.entity.devices.factory;

import cz.cvut.fel.omo.entity.devices.Device;
import cz.cvut.fel.omo.entity.devices.enums.ConsumptionType;
import cz.cvut.fel.omo.entity.devices.enums.DeviceState;
import cz.cvut.fel.omo.entity.devices.enums.DeviceType;
import cz.cvut.fel.omo.entity.house.Room;

import static cz.cvut.fel.omo.Constants.STOVE_CONSUMPTION_ELECTRICITY;
import static cz.cvut.fel.omo.Constants.STOVE_CONSUMPTION_GAS;

/**
 * Gas stove device.
 * Consumes primarily gas, minimal electricity for ignition.
 */
public class Stove extends Device {

    public Stove(int id, Room location, String name, DeviceState deviceStateEnum) {
        super(id, location, name, deviceStateEnum, DeviceType.STOVE);
        consumptionPerTick.put(ConsumptionType.GAS, STOVE_CONSUMPTION_GAS);
        consumptionPerTick.put(ConsumptionType.ELECTRICITY, STOVE_CONSUMPTION_ELECTRICITY);
    }
}
