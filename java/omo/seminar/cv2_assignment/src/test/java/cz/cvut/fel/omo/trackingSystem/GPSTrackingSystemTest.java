package cz.cvut.fel.omo.trackingSystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by kuki on 22/09/2017.
 */
public class GPSTrackingSystemTest {
    private Tracker tracker;
    private Vehicle vehicle;
    @BeforeEach
    void setUp() {
        tracker = new Tracker(001, 10, null);
        vehicle = new Vehicle("test", "00001", 100);
    }
    @Test
    public void attachTrackingDevices_AddNewVehicleThenAttachNewTracker_ReturnsListOfVehiclesWithAttachedTrackers() {
        List<Vehicle> carParkTest = new ArrayList<>();
        carParkTest.add(vehicle);
        GpsTrackingSystem companySpy = new GpsTrackingSystem();
        companySpy.attachTrackingDevices(carParkTest);

        assertEquals("[Tracker_[0], attached to [Vehicle{manufacturer='test', mileage=100, vinCode='00001'}]]", companySpy.getActiveTrackers().toString());
    }
}