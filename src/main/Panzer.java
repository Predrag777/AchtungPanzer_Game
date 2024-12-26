package main;

public class Panzer extends Unit{

	int deathCounter=30;
	int killingRange;
	int width;
	int height;
	public Panzer(String name, double x, double y, int width, int height, int health, int fireRange, int fireRate, int damage, int speed, int shootingError) {
		super(name,  x,  y,  health,  fireRange, fireRate,  damage,  speed, shootingError);
		super.setCommand("Base");
		this.width=width;
		this.height=height;
	}
	public int getDeathCounter() {
		return deathCounter;
	}
	public void setDeathCounter(int deathCounter) {
		this.deathCounter = deathCounter;
	}
	public int getKillingRange() {
		return killingRange;
	}
	public void setKillingRange(int killingRange) {
		this.killingRange = killingRange;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
	
}