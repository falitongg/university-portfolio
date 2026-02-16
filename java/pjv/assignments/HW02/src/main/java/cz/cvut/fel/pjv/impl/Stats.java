package cz.cvut.fel.pjv.impl;

import cz.cvut.fel.pjv.StatsInterface;

import java.util.ArrayList;
import java.util.List;

public class Stats implements StatsInterface {
    // TODO implement all methods from StatsInterface

    private final List<Double> numbers = new ArrayList<Double>();
    @Override
    public void addNumber(double number) {
        numbers.add((number));
        if (numbers.size() == 10){
            System.out.println(getFormattedStatistics());
        }
        if(numbers.size() == 11){
            numbers.clear();
            numbers.add(number);
        }
    }

    @Override
    public double getAverage() {
        if (numbers.isEmpty()) {
            return 0;
        }
        double sum = 0;
        for(double num : numbers){
            sum += num;
        }
        return sum/numbers.size();
    }

    @Override
    public double getStandardDeviation() {
        if(numbers.isEmpty()) return 0;
        double deviation = 0;
        double avg = getAverage();
        double a = 0;
        for (double num : numbers){
            a += Math.pow(num - avg, 2);
        }
        deviation = Math.sqrt(a/numbers.size());
        return deviation;
    }

    @Override
    public int getCount() {
        if (numbers.isEmpty()) return 0;
        return numbers.size();
    }

    @Override
    public String getFormattedStatistics() {
        return String.format("%2d %.3f %.3f", getCount(), getAverage(), getStandardDeviation());
    }
}