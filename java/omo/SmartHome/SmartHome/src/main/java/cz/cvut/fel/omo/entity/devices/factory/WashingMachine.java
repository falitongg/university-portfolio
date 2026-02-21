package cz.cvut.fel.omo.entity.devices.factory;

import cz.cvut.fel.omo.entity.devices.Device;
import cz.cvut.fel.omo.entity.devices.enums.ConsumptionType;
import cz.cvut.fel.omo.entity.devices.enums.DeviceState;
import cz.cvut.fel.omo.entity.devices.enums.DeviceType;
import cz.cvut.fel.omo.entity.house.Room;

import static cz.cvut.fel.omo.Constants.WASHING_MACHINE_CONSUMPTION_ELECTRICITY;
import static cz.cvut.fel.omo.Constants.WASHING_MACHINE_CONSUMPTION_WATER;

/**
 * Washing machine device.
 * Consumes electricity and water when WORKING.
 * Processes clothes (content).
 */
public class WashingMachine extends Device {

    public WashingMachine(int id, Room location, String name, DeviceState deviceStateEnum) {
        super(id, location, name, deviceStateEnum, DeviceType.WASHING_MACHINE);
        consumptionPerTick.put(ConsumptionType.ELECTRICITY, WASHING_MACHINE_CONSUMPTION_ELECTRICITY);
        consumptionPerTick.put(ConsumptionType.WATER, WASHING_MACHINE_CONSUMPTION_WATER);

    }


}
