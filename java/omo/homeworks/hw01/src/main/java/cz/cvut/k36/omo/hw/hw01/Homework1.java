package cz.cvut.k36.omo.hw.hw01;

public class Homework1 {
    private int instanceH;
    public static int instanceI;

    public boolean f(){
        return true;
    }

    public static boolean g(){
        return false;
    }

    public int h(){
        this.instanceH++;
        return instanceH;
    }

    public int i(){
        instanceI++;
        return instanceI;
    }
}
