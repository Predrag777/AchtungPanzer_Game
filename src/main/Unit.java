package main;

public class Unit {
	private String name;
	private double x, nextX;
	private double y, nextY;
	private int health;
	private int fireRange;
	private int damage;
	private int speed;
	private int fireRate;
	
	private String command;
	private String state;
	private String side;
	
	private Unit target;
	
	public Unit(String name, double x, double y, int health, int fireRange, int fireRate, int damage, int speed) {
		this.name=name;
		this.health=health;
		this.x=x;
		this.y=y;
		this.nextX=x;
		this.nextY=y;	
		this.fireRange=fireRange;
		this.damage=damage;
		this.speed=speed;
		this.target=null;
		this.fireRate=fireRate;
		this.state="Stop";//Stop, Fire, Move
		this.side="Up";//Four sides Up,Down,Left,Right
		this.command="stand";
	}

	
	
	
	public String getCommand() {
		return command;
	}




	public void setCommand(String command) {
		this.command = command;
	}




	public int getFireRate() {
		return fireRate;
	}




	public void setFireRate(int fireRate) {
		this.fireRate = fireRate;
	}




	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getNextX() {
		return nextX;
	}

	public void setNextX(double nextX) {
		this.nextX = nextX;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getNextY() {
		return nextY;
	}

	public void setNextY(double nextY) {
		this.nextY = nextY;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getFireRange() {
		return fireRange;
	}

	public void setFireRange(int fireRange) {
		this.fireRange = fireRange;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public Unit getTarget() {
		return target;
	}

	public void setTarget(Unit unit) {
		this.target = unit;
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

	@Override
	public String toString() {
		return "Unit [target=" + target + ", state=" + state + ", side=" + side + "]";
	}
	
	
	
	
}
