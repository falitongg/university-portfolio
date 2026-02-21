package cz.cvut.fel.omo.report;

import cz.cvut.fel.omo.entity.devices.Device;
import cz.cvut.fel.omo.entity.devices.sensor.AbstractSensor;
import cz.cvut.fel.omo.entity.house.Floor;
import cz.cvut.fel.omo.entity.house.House;
import cz.cvut.fel.omo.entity.house.Room;
import cz.cvut.fel.omo.entity.people.HouseholdMember;
import cz.cvut.fel.omo.entity.sport.SportsEquipment;

import java.util.List;

/**
 * Report showing house structure and configuration.
 * Displays floors, rooms, devices, sensors, equipment, and residents.
 */
public class HouseConfigurationReport extends Report {

    private final House house;
    private final List<HouseholdMember> members;

    /**
     * Creates house configuration report.
     * @param house House to report on
     * @param members List of household members
     */
    public HouseConfigurationReport(House house, List<HouseholdMember> members) {
        this.house = house;
        this.members = members;
    }

    /**
     * Generates house configuration report.
     * @return Formatted report showing house structure and contents
     */
    @Override
    public String generateReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== HOUSE CONFIGURATION REPORT ===\n\n");

        appendHouse(sb);
        appendMembers(sb);

        return sb.toString();
    }

    /**
     * Appends house structure (floors and rooms).
     * @param sb StringBuilder to append to
     */
    private void appendHouse(StringBuilder sb) {
        sb.append("House\n");
        for (Floor floor : house.getFloors()) {
            appendFloor(sb, floor);
        }
    }

    /**
     * Appends floor information and its rooms.
     * @param sb StringBuilder to append to
     * @param floor Floor to report on
     */
    private void appendFloor(StringBuilder sb, Floor floor) {
        sb.append("  Floor ")
                .append(floor.getLevel())
                .append(" - ")
                .append(floor.getName())
                .append("\n");

        for (Room room : floor.getRooms()) {
            appendRoom(sb, room);
        }
    }

    /**
     * Appends room information including devices, sensors, equipment, and occupants.
     * @param sb StringBuilder to append to
     * @param room Room to report on
     */
    private void appendRoom(StringBuilder sb, Room room) {
        sb.append("    Room: ")
                .append(room.getType())
                .append("\n");

        appendDevices(sb, room);
        appendSensors(sb, room);
        appendSports(sb, room);
        appendRoomMembers(sb, room);
    }

    /**
     * Appends devices in room (excluding sensors).
     * @param sb StringBuilder to append to
     * @param room Room containing devices
     */
    private void appendDevices(StringBuilder sb, Room room) {
        for (Device device : room.getDevices()) {
            if (!(device instanceof AbstractSensor)) {
                sb.append("      Device: ")
                        .append(device.getType())
                        .append(" (")
                        .append(device.getName())
                        .append(")\n");
            }
        }
    }

    /**
     * Appends sensors in room.
     * @param sb StringBuilder to append to
     * @param room Room containing sensors
     */
    private void appendSensors(StringBuilder sb, Room room) {
        for (Device device : room.getDevices()) {
            if (device instanceof AbstractSensor sensor) {
                sb.append("      Sensor: ")
                        .append(sensor.getSensorType())
                        .append("\n");
            }
        }
    }

    /**
     * Appends sports equipment in room.
     * @param sb StringBuilder to append to
     * @param room Room containing equipment
     */
    private void appendSports(StringBuilder sb, Room room) {
        for (SportsEquipment eq : room.getSportsEquipments()) {
            sb.append("      Sports equipment: ")
                    .append(eq.getType())
                    .append(" (")
                    .append(eq.getTotalAmount())
                    .append(")\n");
        }
    }

    /**
     * Appends people currently in room.
     * @param sb StringBuilder to append to
     * @param room Room containing people
     */
    private void appendRoomMembers(StringBuilder sb, Room room) {
        for (HouseholdMember member : room.getHouseholdMembers()) {
            sb.append("      Person in room: ")
                    .append(member.getName())
                    .append(" (")
                    .append(member.getRole().getRoleName())
                    .append(")\n");
        }
    }

    /**
     * Appends household members summary.
     * @param sb StringBuilder to append to
     */
    private void appendMembers(StringBuilder sb) {
        sb.append("\nHousehold Members:\n");
        for (HouseholdMember member : members) {
            sb.append("  ")
                    .append(member.getName())
                    .append(" - ")
                    .append(member.getRole().getRoleName())
                    .append(", room: ")
                    .append(member.getCurrentRoom().getType())
                    .append("\n");
        }
    }
}
