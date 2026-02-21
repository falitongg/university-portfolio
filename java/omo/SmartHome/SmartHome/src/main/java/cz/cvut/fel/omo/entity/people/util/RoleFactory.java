package cz.cvut.fel.omo.entity.people.util;

import cz.cvut.fel.omo.entity.devices.enums.DeviceType;
import cz.cvut.fel.omo.entity.people.Permission;
import cz.cvut.fel.omo.entity.people.Role;
import cz.cvut.fel.omo.entity.people.RoleName;

public final class RoleFactory {

    private RoleFactory() {
    }

    public static Role mom() {
        Role mom = new Role(RoleName.MOM);

        allowAll(mom);
        allowRepairAll(mom);
        allowTurnOnOff(mom);

        return mom;
    }

    public static Role dad() {
        Role dad = new Role(RoleName.DAD);

        allowAll(dad);

        allowRepairAll(dad);
        allowTurnOnOff(dad);
        return dad;
    }

    public static Role daughter() {
        Role daughter = new Role(RoleName.DAUGHTER);
        daughter.addPermission(DeviceType.CD_PLAYER, Permission.USE);
        daughter.addPermission(DeviceType.CD_PLAYER, Permission.TURN_ON_OFF);
        daughter.addPermission(DeviceType.FRIDGE, Permission.USE);
        daughter.addPermission(DeviceType.FRIDGE, Permission.TURN_ON_OFF);

        return daughter;
    }

    public static Role son() {
        Role son = new Role(RoleName.SON);

        son.addPermission(DeviceType.CD_PLAYER, Permission.USE);
        son.addPermission(DeviceType.CD_PLAYER, Permission.TURN_ON_OFF);
        son.addPermission(DeviceType.FRIDGE, Permission.USE);
        son.addPermission(DeviceType.FRIDGE, Permission.TURN_ON_OFF);
        return son;
    }

    public static Role baby() {
        return new Role(RoleName.BABY);
    }

    public static Role pet() {
        return new Role(RoleName.PET);
    }

    private static void allowAll(Role role) {
        for (DeviceType type : DeviceType.values()) {
            if(type == DeviceType.WASHING_MACHINE || type == DeviceType.STOVE || type == DeviceType.FRIDGE || type == DeviceType.CD_PLAYER) {
                role.addPermission(type, Permission.USE);
                role.addPermission(type, Permission.TURN_ON_OFF);
            }

        }
    }
    private static void allowRepairAll(Role role) {
        for (DeviceType type : DeviceType.values()) {
          role.addPermission(type, Permission.REPAIR);

        }
    }
    private static void allowTurnOnOff(Role role) {
        for (DeviceType type : DeviceType.values()) {
            role.addPermission(type, Permission.TURN_ON_OFF);

        }
    }


}
