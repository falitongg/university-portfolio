package cz.cvut.fel.omo.trackingSystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by kuki on 22/09/2017.
 */
public class TrackerTest {

    private Tracker tracker;
    private Vehicle vehicle;

    @BeforeEach
    public void setUp() {
        tracker = new Tracker(001, 10, null);
        vehicle = new Vehicle("test", "00001", 100);
    }

    @Test
    public void attachTracker_NewVehicle_ReturnNewVehicle() {
        tracker.attachTracker(vehicle);
        assertEquals(tracker.getCurrentVehicle(), vehicle);
    }

    @Test
    public void getTackerMileage_VehicleInnerMemoryMileageByDefaultIs100ThenDrives100_ReturnsTrackerMileage100(){
        tracker.attachTracker(vehicle);
        vehicle.drive(100);
        tracker.getTrackerMileage();
        assertEquals(100, tracker.getTrackerMileage());
    }

    @Test
    public void resetTrackerMileage_VehicleInnerMemoryMileageByDefaultIs100ThenDrives100_ReturnsTrackerMileage0() {
        tracker.attachTracker(vehicle);
        vehicle.drive(100);
        tracker.resetTrackerMileage();
        assertEquals(0, tracker.getTrackerMileage());
    }
}