package main;

import java.util.LinkedList;

public class Minimax {
	
	int currState[][];
	 LinkedList<Unit> AIunits;
	 LinkedList<Unit> myUnits;
	 
	 LinkedList<Float> evalRes;
	 
	public Minimax(int currState[][], LinkedList<Unit> AIunits, LinkedList<Unit> myUnits) {
		this.currState=currState;
		this.AIunits=AIunits;
		this.myUnits=myUnits;
		this.evalRes=new LinkedList<>();
	}
	
	public void setNextState() {
		
	}
	
	public void evaluate() {
		
	}
}
