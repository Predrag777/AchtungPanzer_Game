package Missions;

import java.util.LinkedList;

import main.Obstacles;
import main.Unit;

public class Mission {

	LinkedList<Unit> myUnits=new LinkedList<>();
    LinkedList<Unit> enemyUnits=new LinkedList<>();
    
    Obstacles[] obs=new Obstacles[30];
    
    String background;

	public Mission(String background) {
		super();
		this.background = background;
	}
    
	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}
    
}
