package cz.cvut.fel.omo.entity.devices.factory;

import cz.cvut.fel.omo.entity.devices.DeviceWithContent;
import cz.cvut.fel.omo.entity.devices.enums.ConsumptionType;
import cz.cvut.fel.omo.entity.devices.enums.DeviceState;
import cz.cvut.fel.omo.entity.devices.enums.DeviceType;
import cz.cvut.fel.omo.entity.house.Room;

import static cz.cvut.fel.omo.Constants.FRIDGE_CONSUMPTION_ELECTRICITY;

/**
 * Refrigerator device.
 * Consumes electricity continuously when ON.
 * Stores food (content).
 */
public class Fridge extends DeviceWithContent {

    public Fridge(int id, Room location, String name, DeviceState deviceStateEnum) {
        super(id, location, name, deviceStateEnum, DeviceType.FRIDGE);
        consumptionPerTick.put(ConsumptionType.ELECTRICITY, FRIDGE_CONSUMPTION_ELECTRICITY);
        this.hasContent = true;
    }


}
