package cz.cvut.fel.omo.entity.devices;

import cz.cvut.fel.omo.entity.devices.enums.ConsumptionType;
import java.util.HashMap;
import java.util.Map;

/**
 * Stores device consumption statistics for report generation.
 */
public class ConsumptionStats {

    private final Map<ConsumptionType, Double> consumptionStats;

    public ConsumptionStats() {
        this.consumptionStats = new HashMap<>();
        for (ConsumptionType type : ConsumptionType.values()) {
            consumptionStats.put(type, 0.0);
        }
    }

    /**
     * Adds consumption of a specific resource type.
     * @param type Resource type (ELECTRICITY, WATER, GAS)
     * @param amount Amount consumed in appropriate units (kWh, liters, m³)
     */
    public void addConsumption(ConsumptionType type, double amount) {
        consumptionStats.put(type, consumptionStats.get(type) + amount);
    }

    /**
     * Resets all consumption statistics to zero.
     */
    public void reset() {
        consumptionStats.replaceAll((key, value) -> 0.0);
    }

    /**
     * Gets total electricity consumption.
     * @return Electricity consumed in kWh
     */
    public double getElectricity() {
        return consumptionStats.get(ConsumptionType.ELECTRICITY);
    }

    /**
     * Gets total gas consumption.
     * @return Gas consumed in m³
     */
    public double getGas() {
        return consumptionStats.get(ConsumptionType.GAS);
    }

    /**
     * Gets total water consumption.
     * @return Water consumed in liters
     */
    public double getWater() {
        return consumptionStats.get(ConsumptionType.WATER);
    }

    /**
     * Gets consumption by specific type.
     * @param type Resource type to query
     * @return Consumption amount, or 0.0 if not found
     */
    public double getConsumptionByType(ConsumptionType type) {
        return consumptionStats.getOrDefault(type, 0.0);
    }


}
