package main;
import java.util.*;

import Missions.Mission;
import Missions.Mission1;

public class main {


    public static void main(String[] args) {
    	Mission mission1 = new Mission1("Battlefield");
        System.out.println("Background: " + mission1.getBackground());
        System.out.println("My Units: " + mission1.getMyUnits().size());
        System.out.println("Enemy Units: " + mission1.getEnemyUnits().size());
    }
}
