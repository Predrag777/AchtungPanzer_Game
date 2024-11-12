package main;

public class Panzer extends Unit{

	int deathCounter=30;
	int killingRange;
	public Panzer(String name, double x, double y, int health, int fireRange, int fireRate, int damage, int speed, int shootingError) {
		super(name,  x,  y,  health,  fireRange, fireRate,  damage,  speed, shootingError);
		super.setCommand("Base");
	}
}