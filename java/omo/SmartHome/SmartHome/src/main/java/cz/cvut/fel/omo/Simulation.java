package cz.cvut.fel.omo;

import cz.cvut.fel.omo.entity.devices.Device;
import cz.cvut.fel.omo.entity.devices.DeviceAPI;
import cz.cvut.fel.omo.entity.house.House;
import cz.cvut.fel.omo.entity.people.HouseholdMember;
import cz.cvut.fel.omo.entity.sport.SportsEquipment;
import cz.cvut.fel.omo.event.EventHandler;
import cz.cvut.fel.omo.event.EventManager;
import cz.cvut.fel.omo.report.ActivityAndUsageReport;
import cz.cvut.fel.omo.report.ConsumptionReport;
import cz.cvut.fel.omo.report.EventReport;
import cz.cvut.fel.omo.report.HouseConfigurationReport;

import java.util.*;

import static cz.cvut.fel.omo.Constants.PROBABILITY_USE_DEVICE;

/**
 * Main simulation engine.
 *
 * Represents a discrete-time simulation of a smart household.
 * The simulation controls:
 * <ul>
 *   <li>behavior of household members</li>
 *   <li>usage of devices and sports equipment</li>
 *   <li>event generation and handling</li>
 *   <li>energy consumption updates</li>
 * </ul>
 *
 * One simulation step represents a single time unit in which
 * all entities can act and react to events.
 */
public class Simulation {

    /** Simulated house */
    private final House house;

    /** List of household members */
    private final List<HouseholdMember> people;

    /** All sports equipment available in the house */
    private final List<SportsEquipment> sports;

    /** Central event manager */
    private final EventManager eventManager;

    /** Random generator used for decision making */
    private final Random random = new Random();

    /** API for centralized device management */
    private final DeviceAPI deviceAPI;

    /** All devices registered in the simulation */
    private final List<Device> divices;

    /** All event handlers (people + devices) */
    private final List<EventHandler> handlers = new ArrayList<>();

    /**
     * Creates a new simulation instance.
     *
     * @param house simulated house
     * @param people list of household members
     * @param deviceAPI device API used for device management
     */
    public Simulation(House house, List<HouseholdMember> people, DeviceAPI deviceAPI) {
        this.house = house;
        this.people = people;
        this.eventManager = EventManager.getInstance();
        this.deviceAPI = deviceAPI;

        this.sports = house.getFloors().stream()
                .flatMap(f -> f.getRooms().stream())
                .flatMap(r -> r.getSportsEquipments().stream())
                .toList();

        this.divices = deviceAPI.getDevices();

        // register all event handlers
        this.handlers.addAll(people);
        this.handlers.addAll(divices);
    }

    /**
     * Runs the simulation for a given number of steps.
     *
     * @param steps number of simulation steps
     */
    public void run(int steps) {
        SimulationConfig config = new SimulationConfig();

        Simulation simulation =
                config.createSimulationFromJson("config/simulation.json");

        for (int i = 0; i < steps; i++) {
            simulationStep();
        }
        ActivityAndUsageReport reportActivity =
                new ActivityAndUsageReport(simulation.getPeople());

        HouseConfigurationReport reportHouseconf =
                new HouseConfigurationReport(
                        simulation.getHouse(),
                        simulation.getPeople()
                );
        ConsumptionReport reportConsumption =
                new ConsumptionReport(simulation.getDeviceAPI());
        EventManager em = EventManager.getInstance();
        EventReport reportEvent = new EventReport(em);
        reportEvent.writeToFile("events_report.txt");
        reportConsumption.writeToFile("consumption_report.txt");
        reportActivity.writeToFile("report_activity.txt");
        reportHouseconf.writeToFile("report_houseconf.txt");

    }

    /**
     * Executes a single simulation step.
     *
     * <p>
     * One simulation step represents a discrete time unit in which the state
     * of the household, its members, devices and events is updated.
     * All actions within a step are executed in a fixed order to ensure
     * consistent and predictable simulation behavior.
     * </p>
     *
     * <p>
     * The step consists of the following phases:
     * </p>
     *
     * <ol>
     *   <li>
     *     <b>Event dispatching</b><br>
     *     All previously generated but unhandled events are dispatched.
     *     For each event, the most suitable available handler is selected
     *     based on {@link EventHandler#canHandle},
     *     {@link EventHandler#isAvailable} and
     *     {@link EventHandler#getPriority}.
     *   </li>
     *
     *   <li>
     *     <b>Household member actions</b><br>
     *     Each available household member decides on a random activity:
     *     either attempting to use a device or perform a sport activity.
     *     Members that are currently busy are skipped.
     *   </li>
     *
     *   <li>
     *     <b>Energy consumption update</b><br>
     *     Energy consumption for all devices is recalculated based on
     *     their current operational state.
     *   </li>
     *
     *   <li>
     *     <b>Event generation</b><br>
     *     Devices and household members may generate new events
     *     (e.g. malfunctions, sensor alerts, personal needs).
     *     These events are queued and processed in the next simulation step.
     *   </li>
     *
     *   <li>
     *     <b>Activity finalization</b><br>
     *     All household members finalize their current activities
     *     (e.g. stop using devices or sports equipment) and return
     *     to an idle state if applicable.
     *   </li>
     *
     *   <li>
     *     <b>System-level maintenance</b><br>
     *     All ongoing device repairs are finished and all non-sensor devices
     *     are turned off automatically. This represents system-level
     *     control actions that are not initiated directly by household members.
     *   </li>
     * </ol>
     *
     * <p>
     * This phased approach separates decision-making, physical actions,
     * event handling and system maintenance, making the simulation
     * easier to understand and extend.
     * </p>
     */
    private void simulationStep() {
        eventManager.getHappenedEvents().stream()
                .filter(e -> !e.isHandled())
                .forEach(e -> eventManager.dispatchEvent(e, handlers));

        for (HouseholdMember person : people) {
            if (!person.isAvailable()) continue;

            if (Math.random() < PROBABILITY_USE_DEVICE) {
                tryUseDevice(person);
            } else {
                tryDoSport(person);
            }
        }

        deviceAPI.updateConsumptionForAllDevices();

        divices.forEach(Device::eventOccuired);
        people.forEach(HouseholdMember::eventOccuired);
        people.forEach(HouseholdMember::finishActivityIfNeeded);

        deviceAPI.finishAllRepairs();
        deviceAPI.turnOffAllButSensors();
    }

    /**
     * Attempts to let a household member use one of the available devices.
     *
     * <p>
     * The method follows these steps:
     * </p>
     * <ol>
     *   <li>Retrieves all currently usable devices via {@link DeviceAPI}.</li>
     *   <li>If no usable device is available, the member moves randomly
     *       through the house ({@link #wander(HouseholdMember)}).</li>
     *   <li>The list of usable devices is shuffled to ensure non-deterministic
     *       behavior between simulation steps.</li>
     *   <li>The member iterates over the shuffled devices:
     *       <ul>
     *         <li>Attempts to turn the device on.</li>
     *         <li>Tries to use the device.</li>
     *         <li>If usage succeeds, the method ends immediately.</li>
     *         <li>If usage fails, the device is turned off and the next one is tried.</li>
     *       </ul>
     *   </li>
     *   <li>If none of the devices can be used successfully, the member
     *       moves randomly through the house.</li>
     * </ol>
     *
     * <p>
     * This approach models realistic human behavior where a person may try
     * multiple devices until one becomes usable, or abandons the attempt
     * entirely if none are available.
     * </p>
     *
     * @param person household member attempting to use a device
     */
    private void tryUseDevice(HouseholdMember person) {
        List<Device> devicesToUse = deviceAPI.getUsableDevices();

        if (devicesToUse.isEmpty()) {
            wander(person);
            return;
        }

        Collections.shuffle(devicesToUse, random);

        for (Device device : devicesToUse) {
            person.turnOnDevice(device);

            if (person.useDevice(device)) {
                return;
            }

            person.turnOffDevice(device);
        }

        wander(person);
    }


    /**
     * Attempts to let a household member perform a sport activity
     * using available sports equipment.
     *
     * <p>
     * The method executes the following logic:
     * </p>
     * <ol>
     *   <li>Collects all sports equipment that currently has at least one
     *       available unit.</li>
     *   <li>If no sports equipment is available, the member moves randomly
     *       through the house.</li>
     *   <li>The list of available equipment is shuffled to prevent deterministic
     *       behavior.</li>
     *   <li>The member iterates over the shuffled equipment:
     *       <ul>
     *         <li>Moves to the location of the equipment.</li>
     *         <li>Attempts to use the equipment.</li>
     *         <li>If successful, the method terminates.</li>
     *       </ul>
     *   </li>
     *   <li>If all attempts fail, the member moves randomly through the house.</li>
     * </ol>
     *
     * <p>
     * This method simulates leisure-time behavior of household members
     * when no suitable device interaction is chosen.
     * </p>
     *
     * @param person household member attempting to perform a sport activity
     */
    private void tryDoSport(HouseholdMember person) {
        List<SportsEquipment> available = sports.stream()
                .filter(eq -> eq.getAmountAvailable() > 0)
                .toList();

        if (available.isEmpty()) {
            wander(person);
            return;
        }

        List<SportsEquipment> shuffled = new ArrayList<>(available);
        Collections.shuffle(shuffled, random);

        for (SportsEquipment eq : shuffled) {
            person.moveTo(eq.getLocation());
            if (person.useSportsEquipment(eq)) {
                return;
            }
        }

        wander(person);
    }


    /**
     * Moves a household member to a random room in the house.
     *
     * @param person household member to move
     */
    public void wander(HouseholdMember person) {
        person.moveTo(house.getRandomRoom());
    }

    /**
     * @return simulated house
     */
    public House getHouse() {
        return house;
    }

    /**
     * @return list of household members
     */
    public List<HouseholdMember> getPeople() {
        return people;
    }

    /**
     * @return device API used in simulation
     */
    public DeviceAPI getDeviceAPI() {
        return deviceAPI;
    }
}
