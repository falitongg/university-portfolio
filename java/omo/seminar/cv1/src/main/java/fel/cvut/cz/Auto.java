package fel.cvut.cz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Auto {

    private static int pocetInstanci;

    private final List<Kolo> kola;

    private int rok_vyroby;
    private String barva;

    public Auto(int pocet_kol, int prumer_kola, int rok_vyroby, String barva) {
        if (pocet_kol <= 0) throw new IllegalArgumentException("Pocet kol musi byt kladny");
        if (prumer_kola <= 0) throw new IllegalArgumentException("Prumer kola musi byt kladny");

        this.rok_vyroby = rok_vyroby;
        this.barva = barva;

        List<Kolo> kola = new ArrayList<>(pocet_kol);
        for (int i = 0; i < pocet_kol; i++) {
            kola.add(new Kolo(prumer_kola));
        }
        this.kola = Collections.unmodifiableList(kola);
        pocetInstanci++;
    }

    public static Auto vytvorNakladni (int rok_vyroby, String barva){
        return new Auto(6, 50, rok_vyroby, barva);
    }

    public static Auto vytvorOsobni (int rok_vyroby, String barva){
        return new Auto(4, 17, rok_vyroby, barva);
    }

    public static Auto vytvorAuto (int pocet_kol, int prumer_kola, int rok_vyroby, String barva){
        return new Auto(pocet_kol, prumer_kola, rok_vyroby, barva);
    }

    @Override
    public String toString() {
        return "Auto{" +
                "kola=" + kola +
                ", rok_vyroby=" + rok_vyroby +
                ", barva='" + barva + '\'' +
                '}';
    }

    public static int getPocetInstanci() {
        return pocetInstanci;
    }

    public static void setPocetInstanci(int pocetInstanci) {
        Auto.pocetInstanci = pocetInstanci;
    }

    public List<Kolo> getKola() {
        return kola;
    }

    public int getRok_vyroby() {
        return rok_vyroby;
    }

    public void setRok_vyroby(int rok_vyroby) {
        this.rok_vyroby = rok_vyroby;
    }

    public String getBarva() {
        return barva;
    }

    public void setBarva(String barva) {
        this.barva = barva;
    }
}
