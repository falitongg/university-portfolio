package cz.cvut.fel.omo.entity.people;

import cz.cvut.fel.omo.entity.devices.enums.DeviceType;

import java.util.*;

/**
 * Represents a household member's role with associated device permissions.
 * Defines which actions (use, turn on/off, repair) each role can perform on specific devices.
 */
public class Role {

    private final Map<DeviceType, Set<Permission>> allowedActions;
    private final RoleName roleName;

    /**
     * Creates a new role with given name.
     * Permissions must be added separately using addPermission().
     * @param roleName Role name (MOM, DAD, SON, DAUGHTER, BABY, etc.)
     */
    public Role(RoleName roleName) {
        this.roleName = roleName;
        this.allowedActions = new HashMap<>();
    }

    /**
     * Adds permission for specific device type.
     * @param deviceType Device type to grant permission for
     * @param permission Permission to add (USE, TURN_ON_OFF, REPAIR)
     */
    public void addPermission(DeviceType deviceType, Permission permission) {
        allowedActions.computeIfAbsent(deviceType, t -> new HashSet<>()).add(permission);
    }

    /**
     * Gets all permissions allowed for specific device type.
     * @param deviceType Device type to check
     * @return Unmodifiable set of permissions, or empty set if none
     */
    public Set<Permission> getAllowedPermissions(DeviceType deviceType) {
        Set<Permission> permissions = allowedActions.get(deviceType);
        return permissions != null ?
                Collections.unmodifiableSet(permissions) : Collections.emptySet();
    }

    /**
     * Removes specific permission for device type.
     * Cleans up empty permission sets automatically.
     * @param deviceType Device type to remove permission from
     * @param permission Permission to remove
     */
    public void removePermission(DeviceType deviceType, Permission permission) {
        Set<Permission> permissions = allowedActions.get(deviceType);
        if (permissions != null) {
            permissions.remove(permission);
            if (permissions.isEmpty()) {
                allowedActions.remove(deviceType);
            }
        }
    }

    /**
     * Gets role name.
     * @return Role name
     */
    public RoleName getRoleName() {
        return roleName;
    }
    /**
     * Checks if role has specific permission for given device type.
     * @param type Device type to check
     * @param permission Permission to check
     * @return true if permission is granted, false otherwise
     */
    public boolean hasPermission(DeviceType type, Permission permission) {
        return getAllowedPermissions(type).contains(permission);
    }

}
