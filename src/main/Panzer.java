package main;

public class Panzer extends Unit{

	int deathCounter=30;
	public Panzer(String name, double x, double y, int health, int fireRange, int fireRate, int damage, int speed) {
		super(name,  x,  y,  health,  fireRange, fireRate,  damage,  speed);
		super.setCommand("Base");
	}

	

	
	
	
}
