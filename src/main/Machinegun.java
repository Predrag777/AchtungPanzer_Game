package main;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Machinegun extends Unit{

	public int counter=0;
	boolean logFire=false;
	boolean logFireStop=false;
	int hitX;
	int hitY;
	String image="Machinegun/machinegun1.png";
	Random rand=new Random();
	public Machinegun(String name, double x, double y, int health, int fireRange, int fireRate, int damage, 
			int shootingError) {
		super(name, x, y, health, fireRange, fireRate, damage, 0, shootingError);
		// TODO Auto-generated constructor stub
	}
	
	public void machinegunShotAnimation(Unit target) {
		if(logFire) {
			if(counter<3) {
				image="Machinegun/machinegun2.png";
			}else if(counter<6) {
				hitX=(int) target.getX();
				hitY=(int) target.getY();
				if(hitX<=target.getX()+5) {
					target.setHealth(0);
				}
				image="Machinegun/machinegun3.png";
			}else {
				counter=-1;
			}
			playSound("audio/mgShot.wav",0,0);
		}
		if(logFireStop) {
			logFire=false;
			image="Machinegun/machinegun1.png";
			counter=-1;
		}
		counter++;
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
