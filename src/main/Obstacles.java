package main;

public class Obstacles {
	String name;
	int x,y;
	int width,height;
	boolean destroyable;
	
	public Obstacles(String name, int x, int y,int width,int height, boolean destroyable) {
		this.name=name;
		this.x=x;
		this.y=y;
		this.destroyable=destroyable;
		this.width=width;
		this.height=height;
	}
}
