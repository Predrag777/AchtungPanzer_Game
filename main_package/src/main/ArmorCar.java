package main;

public class ArmorCar extends Panzer{
	
	int fireRate;//Armor Car can shot at least tree rafal shots
	
	public ArmorCar(String name,int x, int y, int health, int fireRange, int damage, int speed, int fireRate) {
		super(name, x,y,health, fireRange, speed, damage);
		this.fireRate=fireRate;
		
	}

}
