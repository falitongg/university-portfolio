package cz.cvut.fel.omo.trackingSystem;

/**
 * Class Vehicle represents a single car in company car park.
 */
public class Vehicle {
    private final String manufacturer;
    private int mileage;
    private final String vinCode;

    public Vehicle(String manufacturer, String vinCode, int mileage) {
        this.mileage = mileage;
        this.manufacturer = manufacturer;
        this.vinCode = vinCode;
    }

    public void drive(int distance) {
        this.mileage += distance;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public int getMileage() {
        return mileage;
    }

    public String getVinCode() {
        return vinCode;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "manufacturer='" + manufacturer + '\'' +
                ", mileage=" + mileage +
                ", vinCode='" + vinCode + '\'' +
                '}';
    }
}
