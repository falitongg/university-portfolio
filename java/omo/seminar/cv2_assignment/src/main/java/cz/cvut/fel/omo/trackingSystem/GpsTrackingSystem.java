package cz.cvut.fel.omo.trackingSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kuki on 22/09/2017.
 * GpsTrackingSystem class represents the newly introduced tool for gaining control over company car park.
 */
public class GpsTrackingSystem {
    private static int counter;
    private List<Tracker> activeTrackers = new ArrayList<>();

    public void attachTrackingDevices(List<Vehicle> vehicles) {
        for (Vehicle vehicle : vehicles) {
            activeTrackers.add(new Tracker(counter++, 0, vehicle));
        }
    }

    public void generateMonthlyReport() {
        int monthMileage = 0;
        System.out.println("—– GPS Tracking system: Monthly report —– ");
        System.out.println("Currently active devices: ");
        for (Tracker tracker : activeTrackers) {
            System.out.println(tracker.toString());
            monthMileage += tracker.getTrackerMileage();
        }
        System.out.println("This month traveled distance: " + monthMileage);
    }

    public List<Tracker> getActiveTrackers() {
        return activeTrackers;
    }
}
