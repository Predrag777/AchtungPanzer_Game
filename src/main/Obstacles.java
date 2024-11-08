package main;

public class Obstacles {
	String name;
	int x,y;
	boolean destroyable;
	
	public Obstacles(String name, int x, int y, boolean destroyable) {
		this.name=name;
		this.x=x;
		this.y=y;
		this.destroyable=destroyable;
	}
}
