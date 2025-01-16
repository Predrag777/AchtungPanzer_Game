package Missions;

import java.util.LinkedList;
import main.Obstacles;
import main.Unit;

public abstract class Mission {
    LinkedList<Unit> myUnits = new LinkedList<>();
    LinkedList<Unit> enemyUnits = new LinkedList<>();
    Obstacles[] obs = new Obstacles[30];
    String background;
    public String type="offansive";//offansive, defensive ==>offansive->you attack deffensive->you deffend

    public Mission(String background) {
        this.background = background;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public abstract LinkedList<Unit> getMyUnits();
    public abstract LinkedList<Unit> getEnemyUnits();
    public abstract Obstacles[] getObs();
}
