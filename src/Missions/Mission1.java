package Missions;

import java.util.LinkedList;

import Missions.Mission;
import main.Infantry;
import main.Obstacles;
import main.Panzer;
import main.Unit;

public class Mission1 extends Mission {

	LinkedList<Unit> myUnits=new LinkedList<>();
    LinkedList<Unit> enemyUnits=new LinkedList<>();
    int treeHeight=100;
    int treeWidth=150;
    
    int buildingHeight=200;
    int buildingWidth=150;
    
    int brokenWidth=100;
    int brokenHeight=50;
    Obstacles[] obs=new Obstacles[25];

	public Mission1(String background) {
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
		this.myUnits.push(new Panzer("tiger", 100, 100, 100,50, 500, 650, 40, 1000, 10, 60));
        this.myUnits.push(new Panzer("panzerIV", 400, 100, 100, 50, 500, 650, 40, 1000, 10, 60));
        
        this.myUnits.push(new Infantry("Rifle", 50,50, 20,500, 30,10, 5, 30, 25));
        this.myUnits.push(new Infantry("Rifle", 50,100, 20,500, 30,10, 5, 30, 25));
        this.myUnits.push(new Infantry("Rifle", 50,150, 20,500, 30,10, 5, 30, 25));
        
        this.myUnits.push(new Infantry("MachinePistol", 50,230, 20,500, 3,15, 5, 80, 3));
        
        this.myUnits.push(new Infantry("Mortar",100,50, 20,800, 25,100, 1, 50, 25));
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
        
        
        
        this.enemyUnits.push(new Panzer("Sherman", 1500, 100, 100, 50, 500, 700, 20, 50, 10, 80));
        this.enemyUnits.push(new Infantry("Rifle", 1300, 100, 20,450, 30,10, 5, 30, 25));
        this.enemyUnits.push(new Infantry("Rifle", 1700, 100, 20,450, 30,10, 5, 30, 25));
        this.enemyUnits.push(new Infantry("Rifle", 1750, 150, 20,450, 30,10, 5, 30, 25));
        this.enemyUnits.push(new Infantry("Rifle", 1300, 800, 20,450, 30,10, 5, 30, 25));
        this.enemyUnits.push(new Infantry("Rifle", 1700, 800, 20,450, 30,10, 5, 30, 25));
        this.enemyUnits.push(new Infantry("Rifle", 1750, 850, 20,450, 30,10, 5, 30, 25));
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
		obs[0]=new Obstacles("panzer/broken2.png",800, 100,brokenHeight, brokenWidth, false);
        obs[1]=new Obstacles("panzer/broken2.png",350, 500,brokenHeight, brokenWidth, false);
        obs[2]=new Obstacles("panzer/broken3.png",1600, 50,brokenHeight, brokenWidth, false);
        obs[3]=new Obstacles("panzer/buildings/house7.png",50, 600,buildingHeight, buildingWidth, false);
        obs[4]=new Obstacles("panzer/buildings/house7.png",800, 700,buildingHeight, buildingWidth, false);

        
        obs[5]=new Obstacles("trees/tree1.png",1000, 400,treeHeight, treeWidth, false);
        obs[6]=new Obstacles("trees/tree1.png",1100, 500,treeHeight, treeWidth,false);
        obs[7]=new Obstacles("trees/tree1.png",1200, 350,treeHeight, treeWidth, false);
        obs[8]=new Obstacles("trees/tree1.png",1350, 750,treeHeight, treeWidth, false);
        obs[9]=new Obstacles("trees/tree1.png",1450, 900,treeHeight, treeWidth, false);
        
        obs[10]=new Obstacles("panzer/buildings/house2.png",1500, 1400,buildingHeight, buildingWidth, false);
        obs[11]=new Obstacles("panzer/buildings/house3.png",1850, 1500,buildingHeight, buildingWidth, false);
        obs[12]=new Obstacles("panzer/buildings/house4.png",1600, 850,buildingHeight, buildingWidth, false);
        obs[13]=new Obstacles("panzer/buildings/house6.png",1500, 500,buildingHeight, buildingWidth,false);
        obs[14]=new Obstacles("panzer/buildings/house6.png",1500, 1800,buildingHeight, buildingWidth, false);
        
        obs[15]=new Obstacles("trees/tree2.png",1500, 400,treeHeight, treeWidth, false);
        obs[16]=new Obstacles("trees/tree2.png",1500, 1500,treeHeight, treeWidth, false);
        obs[17]=new Obstacles("trees/tree2.png",1600, 150,treeHeight, treeWidth, false);
        obs[18]=new Obstacles("trees/tree2.png",1550, 800,treeHeight, treeWidth, false);
        obs[19]=new Obstacles("trees/tree2.png",1650, 1000,treeHeight, treeWidth, false);
        
        obs[20]=new Obstacles("trees/tree3.png",1800, 400,treeHeight, treeWidth, false);
        obs[21]=new Obstacles("trees/tree3.png",1850, 1500,treeHeight, treeWidth, false);
        obs[22]=new Obstacles("trees/tree3.png",1870, 150,treeHeight, treeWidth,false);
        obs[23]=new Obstacles("trees/tree3.png",1855, 800,treeHeight, treeWidth, false);
        obs[24]=new Obstacles("trees/tree3.png",1888, 1000,treeHeight, treeWidth, false);
        
        /*obs[25]=new Obstacles("trees/tree3.png",2800, 1400,treeHeight, treeWidth, false);
        obs[26]=new Obstacles("trees/tree3.png",2850, 500,treeHeight, treeWidth, false);
        obs[27]=new Obstacles("trees/tree3.png",2870, 1150,treeHeight, treeWidth, false);
        obs[28]=new Obstacles("trees/tree3.png",2855, 1800,treeHeight, treeWidth, false);
        obs[29]=new Obstacles("trees/tree3.png",2888, 1200,treeHeight, treeWidth, false);*/
	}

	
    
    
	
	
}
