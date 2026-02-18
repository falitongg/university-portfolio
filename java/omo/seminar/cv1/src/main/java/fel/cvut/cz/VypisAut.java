package fel.cvut.cz;

import java.util.ArrayList;
import java.util.List;

public class VypisAut {
    public static void main(String[] args) {

        Auto car1 = Auto.vytvorAuto(1, 2, 2025, "zelena");
        Auto car2 = Auto.vytvorNakladni(1999, "zelena");
        Auto car3 = Auto.vytvorOsobni(1999, "zluta");
        Auto car4 = Auto.vytvorNakladni(2009, "zelena");

        List<Auto> autoList = new ArrayList<>();
        autoList.add(car1);
        autoList.add(car2);
        autoList.add(car3);
        autoList.add(car4);
        for (Auto auto : autoList) {
            System.out.println(auto);
            System.out.println(auto.getPocetInstanci());
        }
    }
}
