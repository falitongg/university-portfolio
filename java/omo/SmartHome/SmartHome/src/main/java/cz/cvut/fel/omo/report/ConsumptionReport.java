package cz.cvut.fel.omo.report;

import cz.cvut.fel.omo.entity.devices.ConsumptionStats;
import cz.cvut.fel.omo.entity.devices.Device;
import cz.cvut.fel.omo.entity.devices.DeviceAPI;
import cz.cvut.fel.omo.entity.devices.enums.ConsumptionType;

/**
 * Report showing resource consumption by devices.
 * Displays electricity, gas, and water usage per device and totals.
 */
public class ConsumptionReport extends Report {

    private final DeviceAPI deviceAPI;

    /**
     * Creates consumption report.
     * @param deviceAPI Device API containing all registered devices
     */
    public ConsumptionReport(DeviceAPI deviceAPI) {
        this.deviceAPI = deviceAPI;
    }

    /**
     * Generates consumption report.
     * @return Formatted report showing consumption per device and house totals
     */
    @Override
    public String generateReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== CONSUMPTION REPORT ===\n\n");

        deviceAPI.getDevices().forEach(device ->
                appendDeviceConsumption(sb, device)
        );

        appendTotalConsumption(sb);
        return sb.toString();
    }

    /**
     * Appends consumption statistics for one device.
     * @param sb StringBuilder to append to
     * @param device Device to report consumption for
     */
    private void appendDeviceConsumption(StringBuilder sb, Device device) {
        sb.append("Device: ")
                .append(device.getName())
                .append(" (")
                .append(device.getType())
                .append(", ")
                .append(device.getLocation().getType())
                .append(")\n");

        ConsumptionStats stats = device.getConsumption();
        boolean hasConsumption = false;

        for (ConsumptionType type : ConsumptionType.values()) {
            double amount = stats.getConsumptionByType(type);
            if (amount > 0) {
                sb.append("  ")
                        .append(type)
                        .append(": ")
                        .append(String.format("%.2f", amount))
                        .append("\n");
                hasConsumption = true;
            }
        }

        if (!hasConsumption) {
            sb.append("  No consumption recorded\n");
        }

        sb.append("\n");
    }

    /**
     * Appends total house consumption summary.
     * Sums up consumption across all devices.
     * @param sb StringBuilder to append to
     */
    private void appendTotalConsumption(StringBuilder sb) {
        double totalElectricity = 0;
        double totalGas = 0;
        double totalWater = 0;

        for (Device device : deviceAPI.getDevices()) {
            ConsumptionStats stats = device.getConsumption();
            totalElectricity += stats.getElectricity();
            totalGas += stats.getGas();
            totalWater += stats.getWater();
        }

        sb.append("--- TOTAL HOUSE CONSUMPTION ---\n");
        sb.append("Electricity: ")
                .append(String.format("%.2f", totalElectricity))
                .append("\n");
        sb.append("Gas: ")
                .append(String.format("%.2f", totalGas))
                .append("\n");
        sb.append("Water: ")
                .append(String.format("%.2f", totalWater))
                .append("\n");
    }
}
