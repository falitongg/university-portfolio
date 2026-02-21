package cz.cvut.fel.omo;

public class Constants {

    public static final double PROBABILITY_USE_DEVICE = 0.5;

    public static final double DEFAULT_EVENT_CHANCE = 0.10;
    public static final double ELECTRICITY_OUTAGE_PROBABILITY = 0.02;
    public static final double CONTENT_EMPTY_PROBABILITY = 0.2;
    public static final int MOM_SKILL_LEVEL = 90;
    public static final int DAD_SKILL_LEVEL = 95;
    public static final int DAUGHTER_SKILL_LEVEL = 50;
    public static final int SON_SKILL_LEVEL = 40;
    public static final int DEFAULT_SKILL_LEVEL = 10;
    public static final double AIR_CONDITIONER_CONSUMPTION_ELECTRICITY = 0.15;
    public static final double BLINDS_CONSUMPTION_ELECTRICITY = 0.002;
    public static final double CD_PLAYER_CONSUMPTION_ELECTRICITY = 0.03;
    public static final double FRIDGE_CONSUMPTION_ELECTRICITY = 0.05;
    public static final double SMART_WINDOW_CONSUMPTION_ELECTRICITY = 0.005;
    public static final double STOVE_CONSUMPTION_GAS = 0.3;
    public static final double STOVE_CONSUMPTION_ELECTRICITY = 0.01;
    public static final double WASHING_MACHINE_CONSUMPTION_ELECTRICITY = 0.2;
    public static final double WASHING_MACHINE_CONSUMPTION_WATER = 5.0;
    public static final double SENSOR_CONSUMPTION_ELECTRICITY = 0.001;
    public static final int DEFAULT_SIMULATIONS_NUMBER = 100;


    public static final double MIN_HUMIDITY_THRESHOLD = 30.0;
    public static final double MAX_HUMIDITY_THRESHOLD = 80.0;
    public static final double MEAN_HUMIDITY = 45.0;
    public static final double HUMIDITY_STD_DEV = 10.0;
    public static final double MIN_HUMIDITY_RANGE = 0.0;
    public static final double MAX_HUMIDITY_RANGE = 100.0;
    public static final double DEFAULT_INITIAL_VALUE = 0.0;


    public static final double MIN_TEMPERATURE_THRESHOLD = 10.0;
    public static final double MAX_TEMPERATURE_THRESHOLD = 30.0;
    public static final double MEAN_TEMPERATURE = 24.0;
    public static final double TEMPERATURE_STD_DEV = 4.0;


    public static final double MIN_WIND_THRESHOLD = 0.0;
    public static final double MAX_WIND_THRESHOLD = 15.0;
    public static final double WIND_STD_DEV = 5.0;
    public static final double STORM_PROBABILITY = 0.10;
    public static final double STORM_BASE_SPEED = 15.0;
    public static final double STORM_SPEED_RANGE = 10.0;


    public static final double MIN_CO2_THRESHOLD = 0.0;
    public static final double MAX_CO2_THRESHOLD = 1000.0;
    public static final double BAD_AIR_PROBABILITY = 0.10;
    public static final double NORMAL_CO2_BASE = 400;
    public static final double CO2_STD_DEV = 100;
    public static final double HIGH_CO2_BASE = 1000;
    public static final double HIGH_CO2_RANGE = 1000;

    private Constants() {
    }
}
