package Missions;

import java.util.LinkedList;

import main.Infantry;
import main.Obstacles;
import main.Panzer;
import main.Unit;

public class Mission2 extends Mission{
	LinkedList<Unit> myUnits=new LinkedList<>();
    LinkedList<Unit> enemyUnits=new LinkedList<>();
    
    Obstacles[] obs=new Obstacles[30];

	public Mission2(String background) {
		super(background);
		setMyUnits();
		setEnemyUnits();
		setObs();
	}

	public LinkedList<Unit> getMyUnits() {
		return myUnits;
	}

	public void setMyUnits() {
		this.myUnits.push(new Panzer("tiger", 100, 100, 500, 650, 40, 1000, 10, 60));
        this.myUnits.push(new Panzer("panzerIV", 400, 100, 500, 650, 40, 1000, 10, 60));
        
        this.myUnits.push(new Infantry("Rifle", 50,50, 20,500, 30,10, 5, 30, 25));
        this.myUnits.push(new Infantry("Rifle", 50,100, 20,500, 30,10, 5, 30, 25));
        this.myUnits.push(new Infantry("Rifle", 50,150, 20,500, 30,10, 5, 30, 25));
        
        this.myUnits.push(new Infantry("MachinePistol", 50,230, 20,500, 3,15, 5, 80, 3));
        
        this.myUnits.push(new Infantry("Mortar",100,50, 20,400, 25,10, 1, 50, 25));
	}

	public LinkedList<Unit> getEnemyUnits() {
		return enemyUnits;
	}

	public void setEnemyUnits() {
		this.enemyUnits.push(new Panzer("Sherman", 500, 800, 500, 700, 20, 50, 10, 80));
        this.enemyUnits.push(new Infantry("Rifle", 300, 800, 20,450, 30,10, 5, 30, 25));
        this.enemyUnits.push(new Infantry("Rifle", 700, 800, 20,450, 30,10, 5, 30, 25));
        this.enemyUnits.push(new Infantry("Rifle", 750, 850, 20,450, 30,10, 5, 30, 25));
	}

	public Obstacles[] getObs() {
		return obs;
	}

	public void setObs() {
		obs[0]=new Obstacles("panzer/broken1.png",300, 400,100, 200, false);
        obs[1]=new Obstacles("panzer/broken2.png",350, 500,100, 200, false);
        obs[2]=new Obstacles("panzer/broken3.png",1600, 50,100, 200, false);
        obs[3]=new Obstacles("panzer/buildings/house1.png",50, 600,300, 400, false);
        obs[4]=new Obstacles("panzer/buildings/house1.png",800, 700,300, 400, false);

        
        obs[5]=new Obstacles("trees/tree1.png",1000, 400,300, 250, false);
        obs[6]=new Obstacles("trees/tree1.png",1500, 1500,300, 250, false);
        obs[7]=new Obstacles("trees/tree1.png",1600, 150,300, 250, false);
        obs[8]=new Obstacles("trees/tree1.png",1550, 1500,300, 250, false);
        obs[9]=new Obstacles("trees/tree1.png",1650, 1000,300, 250, false);
        
        obs[10]=new Obstacles("panzer/buildings/house2.png",2500, 1400,500, 200, false);
        obs[11]=new Obstacles("panzer/buildings/house3.png",2850, 2500,500, 200, false);
        obs[12]=new Obstacles("panzer/buildings/house4.png",1600, 850,500, 200, false);
        obs[13]=new Obstacles("panzer/buildings/house1.png",2500, 500,300, 400, false);
        obs[14]=new Obstacles("panzer/buildings/house1.png",2500, 2000,300, 400, false);
        
        obs[15]=new Obstacles("trees/tree2.png",1500, 400,300, 250, false);
        obs[16]=new Obstacles("trees/tree2.png",1500, 1500,300, 250, false);
        obs[17]=new Obstacles("trees/tree2.png",1600, 150,300, 250, false);
        obs[18]=new Obstacles("trees/tree2.png",1550, 800,300, 250, false);
        obs[19]=new Obstacles("trees/tree2.png",1650, 1000,300, 250, false);
        
        obs[20]=new Obstacles("trees/tree3.png",1800, 400,300, 250, false);
        obs[21]=new Obstacles("trees/tree3.png",1850, 1500,300, 250, false);
        obs[22]=new Obstacles("trees/tree3.png",1870, 150,300, 250, false);
        obs[23]=new Obstacles("trees/tree3.png",1855, 800,300, 250, false);
        obs[24]=new Obstacles("trees/tree3.png",1888, 1000,300, 250, false);
        
        obs[25]=new Obstacles("trees/tree3.png",2800, 1400,300, 250, false);
        obs[26]=new Obstacles("trees/tree3.png",2850, 500,300, 250, false);
        obs[27]=new Obstacles("trees/tree3.png",2870, 1150,300, 250, false);
        obs[28]=new Obstacles("trees/tree3.png",2855, 1800,300, 250, false);
        obs[29]=new Obstacles("trees/tree3.png",2888, 1200,300, 250, false);
	}
}
