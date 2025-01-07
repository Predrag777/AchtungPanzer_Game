package main;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Infantry extends Unit{

	int deathCounter=20;
	int reloading;
	private int infMove=0;
	public Infantry(String name, double x, double y, int health, int fireRange, int fireRate, int damage, int speed, int shootingError, int reloading) {
		super(name,  x,  y,  health,  fireRange, fireRate,  damage,  speed, shootingError);
		super.setCommand("Base");
		this.reloading=reloading;
	}	
	
	public void infantryAnimationShot() {
    	
    	if(this.getState().equalsIgnoreCase("shot")) {
    		
    		if(this.getFireRate()>20) {
    			if(this.getFireRate()==this.reloading-1)
    				if(this.getName().equalsIgnoreCase("Mortar"))
    	    			playSound("audio/mortarShot.wav",0,1);
    			this.setCommand("shot1");
    		}else if(this.getFireRate()>10) {
    			this.setCommand("shot2");
    		}else if(this.getFireRate()>5){
    			if(this.getFireRate()==10 && !this.getName().equalsIgnoreCase("Mortar"))
    				playSound("audio/Infantry/rifleShot1.wav",0,3);
    			this.setCommand("shot3");
    		}else {
    			this.setFireRate(this.reloading);
    		}
    		this.setFireRate(this.getFireRate()-1);
    	}
    }
	
	
	public void infantryAnimationMove(int compressedMap[][]) {
    	this.setTarget(null);
        if(this.stepByStep.size()>0 && (this.path==null || this.path.size()<1)) {
        	this.findShortestPath(compressedMap, this.stepByStep.get(0)[0],this.stepByStep.get(0)[1], 100, 100);
        }
        if(this.path!=null && this.path.size()>0) {
        	this.setX(this.path.get(0)[0]);
        	this.setY(this.path.get(0)[1]);
        	
        	for(int i=0;i<10;i++) {
        		if(this.path.size()>0)
        			this.path.remove(0);
        		
        	}
        }
        if(this.path==null || this.path.size()==0) {
        	if(this.stepByStep.size()>0) {
        		this.stepByStep.remove(0);
        		this.path=null;
			}
        }
        if(this.stepByStep.size()==0 && (this.path==null || this.path.size()==0)){
        	this.setState("stop");
        	//makeDistance(inf, myUnits);
        }
        
        
    	if(infMove<5) {
    		this.setCommand("move1");
    	}else if(infMove<10) {
    		this.setCommand("move2");
    	}else {
    		infMove=-1;
    		
    	}
    	infMove++;
    	 
        
    }
	
	
	
	
	public static void playSound(String soundFile, int loopCount, float volume) {// volume={-80,6}
   	 try {
   	    	//volume=-80;
   	        File soundPath = new File(soundFile);
   	        AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundPath);
   	        Clip clip = AudioSystem.getClip();

   	        clip.open(audioStream);

   	        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
   	        gainControl.setValue(volume); 

   	        clip.loop(loopCount);
   	        clip.start();
   	    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
   	        e.printStackTrace();
   	    } 
   	}
}
