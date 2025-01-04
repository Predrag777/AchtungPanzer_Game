package Missions;

public class main2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Mission mission1 = new Mission1("Battlefield");
        System.out.println("Background: " + mission1.getBackground());
        System.out.println("My Units: " + mission1.getMyUnits().size());
        System.out.println("Enemy Units: " + mission1.getEnemyUnits().size());
	}

}
