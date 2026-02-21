package cz.cvut.fel.omo;


import cz.cvut.fel.omo.event.EventManager;
import cz.cvut.fel.omo.report.ActivityAndUsageReport;
import cz.cvut.fel.omo.report.ConsumptionReport;
import cz.cvut.fel.omo.report.EventReport;
import cz.cvut.fel.omo.report.HouseConfigurationReport;

import static cz.cvut.fel.omo.Constants.DEFAULT_SIMULATIONS_NUMBER;

public class SmartHome {
    public static void main(String[] args) {
        new SimulationRunner("config/simulation.json").run(DEFAULT_SIMULATIONS_NUMBER);
    }
}
