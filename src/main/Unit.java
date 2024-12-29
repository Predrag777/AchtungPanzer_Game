package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Unit {
	private String name;
	private double x, nextX;
	private double y, nextY;
	private int health;
	private int fireRange;
	private int damage;
	private int speed;
	private int fireRate;
	public LinkedList<int[]> stepByStep=new LinkedList<>();
	private int tempNextX;
	private int tempNextY;
	
	private String command;
	private String state;
	private String side;
	private int shootingError;
	
	int tempX, tempY;
	
	private Unit target;
	private Unit enemy;//Enemy attacks you
	List<int[]> path;
	int myMap[][]=new int[3000][2000];
	public Unit(String name, double x, double y, int health, int fireRange, int fireRate, int damage, int speed, int shootingError) {
		this.name=name;
		this.health=health;
		this.x=x;
		this.y=y;
		this.nextX=x;
		this.nextY=y;	
		this.fireRange=fireRange;
		this.damage=damage;
		this.speed=speed;
		this.fireRate=fireRate;
		this.state="Stop";
		if(this.x!=this.nextX || this.y!=this.nextY)
			this.state="Move";
		this.side="Up";//Four sides Up,Down,Left,Right
		this.shootingError=shootingError;
		this.command="stand";
		this.target=null;
		this.enemy=null;
	}

	
	public void updateMap(int currMap[][], int start[][], int end[][]) {
		this.myMap=currMap;
	}

	public void findShortestPath(int map[][], int height, int width) {
	    if (this.path == null) {
	        this.path = new LinkedList<>();

	        List<int[]> intermediatePoints = makeArrayOfNextteps(500);

	        int[] currentPos = {(int) this.x, (int) this.y};
	        for (int[] nextPos : intermediatePoints) {
	            List<int[]> segment = AStar.astar(currentPos, nextPos, map, width, height);
	            if (segment == null) {
	                System.out.println("No route");
	                return;
	            }System.out.println();
	            /*for(int []a:segment) {
	            	System.out.println(a[0]+" "+a[1]);
	            }*/
	            if (!this.path.isEmpty()) {
	                segment.remove(0);
	            }
	            this.path.addAll(segment);
	            currentPos = nextPos; 
	        }
	    }
	}

	private List<int[]> makeArrayOfNextteps(int stepSize) {
	    List<int[]> points = new LinkedList<>();
	    int startX = (int) this.x;
	    int startY = (int) this.y;
	    int endX = (int) this.nextX;
	    int endY = (int) this.nextY;

	    double distance = Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2));
	    int numSteps = (int) Math.ceil(distance / stepSize);

	    for (int i = 1; i <= numSteps; i++) {
	        double t = (double) i / numSteps;
	        int nextX = (int) (startX + t * (endX - startX));
	        int nextY = (int) (startY + t * (endY - startY));
	        points.add(new int[]{nextX, nextY});
	    }

	    return points;
	}

	
	public Unit getEnemy() {
		return enemy;
	}
	
	 public void updateMove() {
	    	if(this.x!=this.nextX || this.y!=this.nextY) {
	    		
	    	}
	 }




	public void setEnemy(Unit enemy) {
		this.enemy = enemy;
	}




	public int getShootingError() {
		return shootingError;
	}




	public void setShootingError(int shootingError) {
		this.shootingError = shootingError;
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
		this.tempX=(int) this.x;
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
		this.tempY=(int)this.y;
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
		return "Unit [name=" + name + ", nextX=" + nextX + ", nextY=" + nextY + ", state=" + state + "]";
	}
}