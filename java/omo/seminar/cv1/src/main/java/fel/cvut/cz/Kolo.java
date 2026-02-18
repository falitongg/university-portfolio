package fel.cvut.cz;

public class Kolo {
    private final int prumer; //cm

    public Kolo(int prumer) {
        if (prumer <= 0 ) throw new IllegalArgumentException();
        this.prumer = prumer;
    }

    @Override
    public String toString() {
        return "Kolo{" +
                "prumer=" + prumer +
                '}';
    }
}
