package cz.cvut.fel.omo;

import cz.cvut.fel.omo.event.EventManager;
import cz.cvut.fel.omo.report.*;

public class SimulationRunner {

    private final Simulation simulation;

    public SimulationRunner(String configPath) {
        SimulationConfig config = new SimulationConfig();
        this.simulation = config.createSimulationFromJson(configPath);
    }

    /**
     * Runs the simulation and generates all reports.
     *
     * @param steps number of simulation steps
     */
    public void run(int steps) {
        simulation.run(steps);
        generateReports();
    }
    /**
     * Generates all reports after the simulation.
     *
     */
    private void generateReports() {
        ActivityAndUsageReport reportActivity =
                new ActivityAndUsageReport(simulation.getPeople());

        HouseConfigurationReport reportHouseconf =
                new HouseConfigurationReport(
                        simulation.getHouse(),
                        simulation.getPeople()
                );

        ConsumptionReport reportConsumption =
                new ConsumptionReport(simulation.getDeviceAPI());

        EventReport reportEvent =
                new EventReport(EventManager.getInstance());

        reportEvent.writeToFile("events_report.txt");
        reportConsumption.writeToFile("consumption_report.txt");
        reportActivity.writeToFile("report_activity.txt");
        reportHouseconf.writeToFile("report_houseconf.txt");
    }
}

