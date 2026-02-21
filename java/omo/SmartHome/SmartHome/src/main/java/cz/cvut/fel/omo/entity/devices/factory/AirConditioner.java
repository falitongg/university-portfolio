package cz.cvut.fel.omo.entity.devices.factory;

import cz.cvut.fel.omo.entity.devices.Device;
import cz.cvut.fel.omo.entity.devices.enums.ConsumptionType;
import cz.cvut.fel.omo.entity.devices.enums.DeviceState;
import cz.cvut.fel.omo.entity.devices.enums.DeviceType;
import cz.cvut.fel.omo.entity.house.Room;

import static cz.cvut.fel.omo.Constants.AIR_CONDITIONER_CONSUMPTION_ELECTRICITY;

/**
 * Air conditioner device.
 * Consumes electricity when running.
 */
public class AirConditioner extends Device {

    public AirConditioner(int id, Room location, String name, DeviceState state) {
        super(id, location, name, state, DeviceType.AIR_CONDITIONER);
        consumptionPerTick.put(ConsumptionType.ELECTRICITY, AIR_CONDITIONER_CONSUMPTION_ELECTRICITY);
    }
}
