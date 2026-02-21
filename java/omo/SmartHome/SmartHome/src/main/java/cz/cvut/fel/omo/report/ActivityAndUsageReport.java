package cz.cvut.fel.omo.report;

import cz.cvut.fel.omo.entity.devices.Device;
import cz.cvut.fel.omo.entity.people.HouseholdMember;
import cz.cvut.fel.omo.entity.sport.SportsEquipment;

import java.util.List;
import java.util.Map;

/**
 * Report showing device and sports equipment usage by household members.
 * Displays usage statistics for each person.
 */
public class ActivityAndUsageReport extends Report {

    private final List<HouseholdMember> members;

    /**
     * Creates activity and usage report.
     * @param members List of household members to report on
     */
    public ActivityAndUsageReport(List<HouseholdMember> members) {
        this.members = members;
    }

    /**
     * Generates activity and usage report.
     * @return Formatted report showing device and sports usage per person
     */
    @Override
    public String generateReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ACTIVITY AND USAGE REPORT ===\n\n");

        for (HouseholdMember member : members) {
            appendMemberUsage(sb, member);
        }

        return sb.toString();
    }

    /**
     * Appends usage statistics for one household member.
     * @param sb StringBuilder to append to
     * @param member Household member to report on
     */
    private void appendMemberUsage(StringBuilder sb, HouseholdMember member) {
        sb.append("Person: ")
                .append(member.getName())
                .append(" (")
                .append(member.getRole().getRoleName())
                .append(")\n");

        appendDeviceUsage(sb, member);
        appendSportsUsage(sb, member);
        sb.append("\n");
    }

    /**
     * Appends device usage statistics for member.
     * @param sb StringBuilder to append to
     * @param member Household member
     */
    private void appendDeviceUsage(StringBuilder sb, HouseholdMember member) {
        Map<Device, Integer> devices = member.getDeviceUsageCount();
        if (devices.isEmpty()) {
            sb.append("  Devices: none\n");
            return;
        }

        sb.append("  Devices:\n");
        devices.forEach((device, count) -> {
            sb.append("    ")
                    .append(device.getType())
                    .append(" (")
                    .append(device.getName())
                    .append(") → ")
                    .append(count)
                    .append("×\n");
        });
    }

    /**
     * Appends sports equipment usage statistics for member.
     * @param sb StringBuilder to append to
     * @param member Household member
     */
    private void appendSportsUsage(StringBuilder sb, HouseholdMember member) {
        Map<SportsEquipment, Integer> sports = member.getSportsEquipmentUsageCount();
        if (sports.isEmpty()) {
            sb.append("  Sports equipment: none\n");
            return;
        }

        sb.append("  Sports equipment:\n");
        sports.forEach((eq, count) -> {
            sb.append("    ")
                    .append(eq.getType())
                    .append(" → ")
                    .append(count)
                    .append("×\n");
        });
    }
}
