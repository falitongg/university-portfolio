package cz.cvut.fel.pjv.impl;


import cz.cvut.fel.pjv.StatsInterface;
import cz.cvut.fel.pjv.TextIO;

public class Lab02 {
    public static void main(String[] args) {
//        System.out.println("I'm not implemented!");
        StatsInterface stats = new Stats();
        TextIO textIO = new TextIO();

        int lineNumber = 0;

        while (true){
            String input = textIO.getLine();
            lineNumber++;

            if (input.isEmpty()){
                System.err.println("End of input detected!");
                break;
            }
            if (TextIO.isInteger(input) || TextIO.isDouble(input)){
                double number = Double.parseDouble(input);
                stats.addNumber(number);
            }else{
                System.err.println("A number has not been parsed from line " + lineNumber);
            }
        }
        if(stats.getCount() > 1){
            System.out.println(stats.getFormattedStatistics());
        }
    }
}