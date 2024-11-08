package main;

public class Panzer {
	private String name;
	private double x, nextX;
	private double y, nextY;
	private int health;
	private int fireRange;
	private int damage;
	private int speed;
	
	private String state;
	private String side;
	
	public Panzer(String name, double x, double y, int health, int fireRange, int damage, int speed) {
		this.name=name;
		this.health=health;
		this.x=x;
		this.y=y;
		this.nextX=x;
		this.nextY=y;	
		this.fireRange=fireRange;
		this.damage=damage;
		this.speed=speed;
		
		this.state="Stop";//Stop, Fire, Move
		this.side="Up";//Four sides Up,Down,Left,Right
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}
	

	public double getX() {
		return x;
	}

	public void setX(double d) {
		this.x = d;
	}

	public double getY() {
		return y;
	}

	public void setY(double targetY) {
		this.y = targetY;
	}

	
	
	public int getFireRange() {
		return fireRange;
	}

	public void setFireRange(int fireRange) {
		this.fireRange = fireRange;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public double getNextX() {
		return nextX;
	}

	public void setNextX(int nextX) {
		this.nextX = nextX;
	}

	public double getNextY() {
		return nextY;
	}

	public void setNextY(int nextY) {
		this.nextY = nextY;
	}

	@Override
	public String toString() {
		return "Panzer [name=" + name + ", x=" + x + ", y=" + y + ", health=" + health + ", state=" + state + ", side="
				+ side + "]";
	}

	
	
	
}
