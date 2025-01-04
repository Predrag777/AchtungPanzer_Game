package Missions;

import java.util.LinkedList;
import java.util.Random;

import main.Infantry;
import main.Obstacles;
import main.Panzer;
import main.Unit;

public class Mission2 extends Mission {
	LinkedList<Unit> myUnits=new LinkedList<>();
    LinkedList<Unit> enemyUnits=new LinkedList<>();
    int treeHeight=100;
    int treeWidth=150;
    
    int buildingHeight=250;
    int buildingWidth=100;
    
    int brokenWidth=100;
    int brokenHeight=50;
    Obstacles[] obs=new Obstacles[9];
    
	public Mission2(String background) {
		super(background);
		setMyUnits();
		setEnemyUnits();
		setObs();
	}

	@Override
	public LinkedList<Unit> getMyUnits() {
		return myUnits;
	}

	public void setMyUnits() {
		this.myUnits.push(new Panzer("tiger", 100, 1000, 100,50, 500, 650, 40, 1000, 10, 60));
	}
	@Override
	public LinkedList<Unit> getEnemyUnits() {
		return enemyUnits;
	}

	public void setEnemyUnits() {
		/*this.enemyUnits.push(new Panzer("Sherman", 500, 3800, 200, 100, 500, 700, 20, 50, 10, 80));
        this.enemyUnits.push(new Infantry("Rifle", 300,3800, 20,450, 30,10, 5, 30, 25));
        this.enemyUnits.push(new Infantry("Rifle", 700, 3800, 20,450, 30,10, 5, 30, 25));
        this.enemyUnits.push(new Infantry("Rifle", 750, 3850, 20,450, 30,10, 5, 30, 25));
        */
        
        
        
        this.enemyUnits.push(new Panzer("Sherman", 1500, 800, 100, 50, 500, 700, 20, 50, 10, 80));
        /*this.enemyUnits.push(new Infantry("Rifle", 1300, 100, 20,450, 30,10, 5, 30, 25));
        this.enemyUnits.push(new Infantry("Rifle", 1700, 100, 20,450, 30,10, 5, 30, 25));
        this.enemyUnits.push(new Infantry("Rifle", 1750, 150, 20,450, 30,10, 5, 30, 25));
        this.enemyUnits.push(new Infantry("Rifle", 1300, 800, 20,450, 30,10, 5, 30, 25));
        this.enemyUnits.push(new Infantry("Rifle", 1700, 800, 20,450, 30,10, 5, 30, 25));
        this.enemyUnits.push(new Infantry("Rifle", 1750, 850, 20,450, 30,10, 5, 30, 25));
        */
        
        Random rand=new Random();
        for(int i=0;i<enemyUnits.size();i++) {
        	enemyUnits.get(i).setNextX(rand.nextInt()+200);
        	enemyUnits.get(i).setNextY(rand.nextInt()+200);
        }
        
        
        /*
        this.enemyUnits.push(new Infantry("Rifle", 2300, 1000, 20,450, 30,10, 5, 30, 25));
        this.enemyUnits.push(new Infantry("Rifle", 2700, 1000, 20,450, 30,10, 5, 30, 25));
        this.enemyUnits.push(new Infantry("Rifle", 2750, 1050, 20,450, 30,10, 5, 30, 25));*/
	}
	@Override
	public Obstacles[] getObs() {
		return obs;
	}

	public void setObs() {
		obs[0]=new Obstacles("panzer/buildings/house8.png",800, 100,buildingHeight, buildingWidth, false);
        obs[1]=new Obstacles("panzer/buildings/house6.png",350, 500,buildingHeight, buildingWidth, false);
        obs[2]=new Obstacles("panzer/buildings/house7.png",600, 750,buildingHeight, buildingWidth, false);
        obs[3]=new Obstacles("panzer/buildings/house8.png",50, 600,buildingHeight, buildingWidth, false);
        obs[4]=new Obstacles("panzer/buildings/house9.png",500, 300,buildingHeight, buildingWidth, false);

        obs[5]=new Obstacles("airplanes/parachuterAirplane.png", 100,150,100, 100, false);
        obs[6]=new Obstacles("airplanes/parachuterAirplane.png", 100,250,100, 100, false);
        obs[7]=new Obstacles("airplanes/parachuterAirplane.png", 100,350,100, 100, false);
        obs[8]=new Obstacles("airplanes/parachuterAirplane.png", 100,450,100, 100, false);
        
        /*obs[25]=new Obstacles("trees/tree3.png",2800, 1400,treeHeight, treeWidth, false);
        obs[26]=new Obstacles("trees/tree3.png",2850, 500,treeHeight, treeWidth, false);
        obs[27]=new Obstacles("trees/tree3.png",2870, 1150,treeHeight, treeWidth, false);
        obs[28]=new Obstacles("trees/tree3.png",2855, 1800,treeHeight, treeWidth, false);
        obs[29]=new Obstacles("trees/tree3.png",2888, 1200,treeHeight, treeWidth, false);*/
	}

	
    
    
	
}
