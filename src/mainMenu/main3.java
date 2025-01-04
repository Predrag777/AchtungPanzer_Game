package mainMenu;

import Missions.Mission;
import Missions.Mission1;

public class main3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Mission mission1 = new Mission1("Battlefield");
        System.out.println("Background: " + mission1.getBackground());
        System.out.println("My Units: " + mission1.getMyUnits().size());
        System.out.println("Enemy Units: " + mission1.getEnemyUnits().size());
	}

}
