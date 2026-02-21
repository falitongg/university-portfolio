package cz.cvut.fel.omo.entity.devices.util;

import cz.cvut.fel.omo.entity.people.HouseholdMember;

public interface DeviceStateHandler {
    boolean canBeUsed();
    boolean canBeTurnedOn();
    boolean canBeTurnedOff();
    boolean canBeRepaired();

    void onUse(HouseholdMember member);
    void onStopUsing(HouseholdMember member);
    void onTurnOn();
    void onTurnOff();
    void onBreak();
    void onStartRepair();
    void onFinishRepair();
}
