package cz.cvut.fel.omo;

import cz.cvut.fel.omo.entity.devices.Device;
import cz.cvut.fel.omo.entity.devices.DeviceAPI;
import cz.cvut.fel.omo.entity.devices.factory.DeviceFactory;
import cz.cvut.fel.omo.entity.devices.sensor.SensorType;
import cz.cvut.fel.omo.entity.devices.enums.DeviceType;
import cz.cvut.fel.omo.entity.house.Floor;
import cz.cvut.fel.omo.entity.house.House;
import cz.cvut.fel.omo.entity.house.Room;
import cz.cvut.fel.omo.entity.people.HouseholdMember;
import cz.cvut.fel.omo.entity.people.Role;
import cz.cvut.fel.omo.entity.people.util.RoleFactory;
import cz.cvut.fel.omo.entity.sport.SportsEquipment;
import cz.cvut.fel.omo.entity.sport.SportsEquipmentType;
import cz.cvut.fel.omo.json.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Configuration builder for simulation.
 * Loads house structure, devices, people, and sports equipment from JSON configuration.
 */
public class SimulationConfig {

    private final DeviceAPI deviceAPI = new DeviceAPI();

    /**
     * Creates simulation from JSON configuration file.
     * @param path Path to JSON configuration file
     * @return Configured simulation instance
     */
    public Simulation createSimulationFromJson(String path) {
        SimulationJsonConfig cfg = JsonConfigLoader.load(path);
        House house = buildHouse(cfg);
        buildSports(cfg, house);
        List<HouseholdMember> people = buildPeople(cfg, house);

        return new Simulation(house, people, deviceAPI);
    }

    /**
     * Builds house structure from configuration.
     * Creates floors, rooms, and devices.
     * @param cfg Configuration data
     * @return Constructed house
     */
    private House buildHouse(SimulationJsonConfig cfg) {
        House house = new House();

        for (FloorConfig f : cfg.floors) {
            Floor floor = new Floor(f.level, f.name, house);
            house.addFloor(floor);

            for (RoomConfig r : f.rooms) {
                Room room = new Room(r.type, floor);
                floor.addRoom(room);

                for (DeviceConfig d : r.devices) {
                    Device device;

                    if (d.type == DeviceType.SENSOR) {
                        SensorType sensorType = SensorType.valueOf(d.sensorType);
                        device = DeviceFactory.createSensor(
                                sensorType,
                                room,
                                d.name
                        );
                    } else {
                        device = DeviceFactory.createDevice(
                                d.type,
                                room,
                                d.name,
                                deviceAPI
                        );
                    }

                    room.addDevice(device);
                    deviceAPI.registerDevice(device);
                }
            }
        }

        return house;
    }

    /**
     * Creates household members from configuration.
     * Places all members in first available room initially.
     * @param cfg Configuration data
     * @param house House where members will live
     * @return List of household members
     */
    private List<HouseholdMember> buildPeople(SimulationJsonConfig cfg, House house) {
        // Choose first room as starting location
        Room startRoom = house.getFloors()
                .get(0)
                .getRooms()
                .get(0);

        List<HouseholdMember> people = new ArrayList<>();

        for (PersonConfig p : cfg.people) {
            Role role = createRole(p.role);
            HouseholdMember member = new HouseholdMember(
                    p.name,
                    role,
                    startRoom,
                    deviceAPI
            );
            people.add(member);
        }

        return people;
    }

    /**
     * Creates role from role name string.
     * @param roleName Role name (case-insensitive)
     * @return Role instance
     * @throws IllegalArgumentException if role name is unknown
     */
    private Role createRole(String roleName) {
        return switch (roleName.toUpperCase()) {
            case "MOM" -> RoleFactory.mom();
            case "DAD" -> RoleFactory.dad();
            case "DAUGHTER" -> RoleFactory.daughter();
            case "SON" -> RoleFactory.son();
            case "BABY" -> RoleFactory.baby();
            case "PET", "DOG" -> RoleFactory.pet();
            default -> throw new IllegalArgumentException(
                    "Unknown role: " + roleName
            );
        };
    }

    /**
     * Creates sports equipment from configuration.
     * Places equipment in specified rooms.
     * @param cfg Configuration data
     * @param house House where equipment will be placed
     */
    private void buildSports(SimulationJsonConfig cfg, House house) {
        if (cfg.sports == null) return;

        for (SportConfig s : cfg.sports) {
            Room room = findRoomByName(house, s.room);
            SportsEquipmentType type =
                    SportsEquipmentType.valueOf(s.type.toUpperCase());

            room.addSportsEquipment(
                    new SportsEquipment(room, type, s.amount)
            );
        }
    }

    /**
     * Finds room by name (case-insensitive).
     * @param house House to search in
     * @param roomName Name of room to find
     * @return Found room
     */
    private Room findRoomByName(House house, String roomName) {
        return house.getFloors().stream()
                .flatMap(f -> f.getRooms().stream())
                .filter(r -> r.getName().equalsIgnoreCase(roomName))
                .findFirst()
                .orElseThrow();
    }
}
