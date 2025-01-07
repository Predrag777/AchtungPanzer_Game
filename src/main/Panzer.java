package main;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Panzer extends Unit{

	int deathCounter=30;
	int killingRange;
	int width;
	int height;
	public Panzer(String name, double x, double y, int width, int height, int health, int fireRange, int fireRate, int damage, int speed, int shootingError) {
		super(name,  x,  y,  health,  fireRange, fireRate,  damage,  speed, shootingError);
		super.setCommand("Base");
		this.width=width;
		this.height=height;
	}
	public int getDeathCounter() {
		return deathCounter;
	}
	public void setDeathCounter(int deathCounter) {
		this.deathCounter = deathCounter;
	}
	public int getKillingRange() {
		return killingRange;
	}
	public void setKillingRange(int killingRange) {
		this.killingRange = killingRange;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
	//////////////////////////////////////////////////////////////////////////////////
	///
	public void panzerAnimationShot() {
    	if(this.getState().equalsIgnoreCase("shot")) {
    		if(this.getFireRate()>10) {
    			this.setCommand("Base");
    		}else if(this.getFireRate()>5) {
    			if(this.getFireRate()==10)
    				playSound("audio/tankShot1.wav",0,3);
    			this.setCommand("Shot");
    		}else {
    			this.setFireRate(40);
    		}
    		this.setFireRate(this.getFireRate()-1);
    	}
    }
	
	
	public void panzerAnimationMove(int compressedMap[][]) {
        this.setTarget(null);
        if(this.stepByStep.size()>0 && (this.path==null || this.path.size()<1)) {
        	this.findShortestPath(compressedMap, this.stepByStep.get(0)[0],this.stepByStep.get(0)[1], this.width, this.height);
            System.out.println(this.stepByStep.size());

        }
	    if (this.path!=null && this.path.size() > 0) {
	    	
	    	this.setX(this.path.get(0)[0]);
	    	this.setY(this.path.get(0)[1]);
	        for (int i = 0; i < 10; i++) {
	            if (this.path.size() > 0) {
	            	this.path.remove(0); 
	                
	            }
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
        	//makeDistance(panzer, myUnits);
        }
        
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