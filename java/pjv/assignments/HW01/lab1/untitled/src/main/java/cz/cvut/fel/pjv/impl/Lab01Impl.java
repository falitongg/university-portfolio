package cz.cvut.fel.pjv.impl;

import cz.cvut.fel.pjv.Lab01;

import java.util.Scanner;

public class Lab01Impl implements Lab01 {
    @Override
    public void homework() {
        // TODO: Implement the homework here
        Scanner sc = new Scanner(System.in);
        System.out.println("Vyber operaci (1-soucet, 2-rozdil, 3-soucin, 4-podil):");
        int operation = sc.nextInt();
        if (operation == 1) {
            System.out.println("Zadej scitanec: ");
            double a = sc.nextDouble();
            System.out.println("Zadej scitanec: ");
            double b = sc.nextDouble();
            System.out.println("Zadej pocet desetinnych mist: ");
            int dot = sc.nextInt();
            if (dot < 0){
                System.out.println("Chyba - musi byt zadane kladne cislo!");
            }else{
                double result = a + b;
                String format = "%." + dot + "f";
                System.out.printf(format + " + " + format + " = " + format + "%n", a,b, result);
            }

        } else if (operation == 2) {
            System.out.println("Zadej mensenec: ");
            double a = sc.nextDouble();
            System.out.println("Zadej mensitel: ");
            double b = sc.nextDouble();
            System.out.println("Zadej pocet desetinnych mist: ");
            int dot = sc.nextInt();
            if (dot < 0) {
                System.out.println("Chyba - musi byt zadane kladne cislo!");
            } else {
                double result = a - b;
                String format = "%." + dot + "f";
                System.out.printf(format + " - " + format + " = " + format + "%n", a, b, result);

            }
        } else if (operation == 3) {
            System.out.println("Zadej cinitel: ");
            double a = sc.nextDouble();
            System.out.println("Zadej cinitel: ");
            double b = sc.nextDouble();
            System.out.println("Zadej pocet desetinnych mist: ");
            int dot = sc.nextInt();
            if (dot < 0) {
                System.out.println("Chyba - musi byt zadane kladne cislo!");
            } else {
                double result = a * b;
                String format = "%." + dot + "f";
                System.out.printf(format + " * " + format + " = " + format + "%n", a, b, result);

            }
        } else if (operation == 4) {
            System.out.println("Zadej delenec: ");
            double a = sc.nextDouble();
            System.out.println("Zadej delitel: ");
            double b = sc.nextDouble();
            if (b == 0){
                System.out.println("Pokus o deleni nulou!");
                sc.close();
                return;
            }
            System.out.println("Zadej pocet desetinnych mist: ");
            int dot = sc.nextInt();
            if (dot < 0) {
                System.out.println("Chyba - musi byt zadane kladne cislo!");
                sc.close();
                return;
            } else {
                double result = a / b;
                String format = "%." + dot + "f";
                System.out.printf(format + " / " + format + " = " + format + "%n", a, b, result);

            }
        } else{
                System.out.println("Chybna volba!");
            }
    }
}
