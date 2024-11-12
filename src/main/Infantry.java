package main;

public class Infantry extends Unit{

	int deathCounter=20;
	int reloading;
	public Infantry(String name, double x, double y, int health, int fireRange, int fireRate, int damage, int speed, int shootingError, int reloading) {
		super(name,  x,  y,  health,  fireRange, fireRate,  damage,  speed, shootingError);
		super.setCommand("Base");
		this.reloading=reloading;
	}
	


	
	
	
	
	
}
