package cz.cvut.fel.omo.entity.devices.factory;

import cz.cvut.fel.omo.entity.devices.DeviceWithContent;
import cz.cvut.fel.omo.entity.devices.enums.ConsumptionType;
import cz.cvut.fel.omo.entity.devices.enums.DeviceState;
import cz.cvut.fel.omo.entity.devices.enums.DeviceType;
import cz.cvut.fel.omo.entity.house.Room;

import static cz.cvut.fel.omo.Constants.CD_PLAYER_CONSUMPTION_ELECTRICITY;

/**
 * CD Player device.
 * Plays CDs (content).
 */
public class CDplayer extends DeviceWithContent {

    public CDplayer(int id, Room location, String name, DeviceState state) {
        super(id, location, name, state, DeviceType.CD_PLAYER);
        consumptionPerTick.put(ConsumptionType.ELECTRICITY, CD_PLAYER_CONSUMPTION_ELECTRICITY);
        this.hasContent = true;
    }


}
