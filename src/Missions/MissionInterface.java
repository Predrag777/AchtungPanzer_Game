package Missions;

import java.util.LinkedList;

import main.Obstacles;
import main.Unit;

public interface MissionInterface {

	public LinkedList<Unit>  getMyUnits();
	public LinkedList<Unit>  getEnemyUnits();
	public  Obstacles[] getObs();
}
