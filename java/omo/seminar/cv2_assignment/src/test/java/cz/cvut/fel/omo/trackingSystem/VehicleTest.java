package cz.cvut.fel.omo.trackingSystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import static org.junit.Assert.*;

/**
 * Created by kuki on 22/09/2017.
 */
public class VehicleTest {

    private Vehicle vehicle;

    @BeforeEach
    public void setUp() {
        vehicle = new Vehicle("test", "00001", 100);
    }

    @Test
    public void drive_100Distance_Returns200Mileage() {
        vehicle.drive(100);
        assertEquals(200, vehicle.getMileage(), "Shoud be unchanged");
    }
}