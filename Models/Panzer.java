package AchtungPanzer_Game.Models;

public class Panzer {
	private String name;
	private int health;
	private int fireRange;
	private int damage;
	
	private String state;
	private String side;
	
	public Panzer(String name, int health, int fireRange, int damage) {
		this.name=name;
		this.health=health;
		this.fireRange=fireRange;
		this.damage=damage;
		
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

	@Override
	public String toString() {
		return "Panzer [name=" + name + ", state=" + state + ", side=" + side + "]";
	}
	
	
}
