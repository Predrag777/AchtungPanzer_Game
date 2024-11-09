package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

class Crtaj extends JPanel implements MouseListener, ActionListener {
    int numberOfEnemyPanzers;
    LinkedList<Panzer> myPanzers=new LinkedList<>();
    LinkedList<Panzer> enemyPanzers=new LinkedList<>();
    LinkedList<Panzer> selected=new LinkedList<>();
    Timer t = new Timer(100, this);
    double x = 100, y = 100;
    double x1 = 400, y1 = 100;
    int counterForFire = 0;
    double angle = 0;
    double a = 500, b = 800;
    int enemySide=1;
    

    int obsX[]=new int[5];
    int obsY[]=new int[5];
    Random rand = new Random();
    HashMap<Panzer, Panzer> targets=new HashMap<>();
    Obstacles[] obs=new Obstacles[5];
    BufferedImage background;
    BufferedImage images[];
    String brokens[]= {"panzer/broken1.png","panzer/broken2.png","panzer/broken3.png","panzer/broken2.png","panzer/broken3.png"};
    public Crtaj() throws IOException {
        t.start();
        this.myPanzers.push(new Panzer("panzerBase", x, y, 500, 650, 50, 10));
        this.myPanzers.push(new Panzer("panzerIV", x1, y1, 500, 650, 50, 10));
        
        this.enemyPanzers.push(new Panzer("Sherman", a, b, 500, 700, 50, 10));

        /*this.myPanzers[0] = new Panzer("Tiger Panzer", x, y, 500, 650, 50, 10);
        this.enemyPanzers[0] = new Panzer("Sherman", a, b, 500, 700, 50, 10);*/
        
        
        obs[0]=new Obstacles("SS",300, 400, false);
        obs[1]=new Obstacles("SS",350, 500, false);
        obs[2]=new Obstacles("SS",600, 50, false);
        obs[3]=new Obstacles("SS",50, 600, false);
        obs[4]=new Obstacles("SS",800, 700, false);
        BufferedImage[] obstacles= {ImageIO.read(new File("panzer/broken2.png")),
					        		ImageIO.read(new File("panzer/broken1.png")),
					        		ImageIO.read(new File("panzer/broken3.png")),
					        		ImageIO.read(new File("panzer/broken1.png")),
					        		ImageIO.read(new File("panzer/broken3.png"))};
        images=obstacles;
        /*for (int i = 0; i < obsX.length; i++) {
            obsX[i] = rand.nextInt(901) + 100; 
            obsY[i] = rand.nextInt(800)+100;
            
            obs[i]=new Obstacles("SS",(int)obsX[i], (int)obsY[i], false);
        }*/
        
        try {
            background = ImageIO.read(new File("panzer/background.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        setSize(1000, 1000);
        setVisible(true);
        addMouseListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

    public boolean isCrashedOnObstacle() {
    	
    	for(int i=0;i<obs.length;i++) {
    		//System.out.println(this.x+" "+obs[i].x+"       "+this.y+" "+obs[i].y);
    		if(this.x>=obs[i].x-100 && this.x<=obs[i].x
    				&& this.y>=obs[i].y && this.y<=obs[i].y+100)//this.y<=obs[i].y && this.y>=obs[i].y+50)
    			{
    				System.out.println("CRASH!!!!!!!!!");
    				return true;
    			}
    	}
    	return false;
    }
    
    public boolean canNotShot() {
    	
    	return true;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
        }
        for(int i=0;i<obs.length;i++) {
        	g.drawImage(images[i], obs[i].x, obs[i].y, 100, 200, null);
        }
        
        
        BufferedImage panzer, panzer2, enemy;
        String enemyImage="panzer/shermanBase.png";
        
        
        
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform oldTransform = g2d.getTransform();

        try {
        	for(int i=0;i<myPanzers.size();i++) {
        		if(myPanzers.get(i).getState().equalsIgnoreCase("shot") && myPanzers.get(i).getTarget()!=null) {
        			Panzer target=myPanzers.get(i).getTarget();
        			Panzer currPanzer=myPanzers.get(i);
        			double deltaX=target.getX()-currPanzer.getX();
        			double deltaY=target.getY()-currPanzer.getY();
        			
        			double angle=Math.atan2(deltaY, deltaX);
        			
        			double centerX = currPanzer.getX() + 100;
        	        double centerY = currPanzer.getY() + 50;
        	        g2d.rotate(angle, centerX,centerY);
        		}
        		if(myPanzers.get(i).getState().equalsIgnoreCase("move")) {
        			Panzer currPanzer=myPanzers.get(i);
        			double deltaX=currPanzer.getNextX()-currPanzer.getX();
        			double deltaY=currPanzer.getNextY()-currPanzer.getY();
        			
        			double angle=Math.atan2(deltaY, deltaX);
        			
        			double centerX = currPanzer.getX() + 100;
        	        double centerY = currPanzer.getY() + 50;
        	        
        	        g2d.rotate(angle, centerX,centerY);
        	        
        	        
        		}
        		g2d.drawImage(ImageIO.read(new File("panzer/"+myPanzers.get(i).getName()+".png")), (int) myPanzers.get(i).getX(), (int) myPanzers.get(i).getY(), 200, 100, null);
        		g2d.setTransform(oldTransform);
        	}
        	
        	
            /*g2d.drawImage(ImageIO.read(new File("panzer/"+myPanzers.get(0).getName()+".png")), (int) myPanzers.get(0).getX(), (int) myPanzers.get(0).getY(), 200, 100, null);
            g2d.drawImage(ImageIO.read(new File("panzer/"+myPanzers.get(1).getName()+".png")), (int) myPanzers.get(1).getX(), (int) myPanzers.get(1).getY(), 200, 100, null);
        	*/
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
        	for(int i=0;i<enemyPanzers.size();i++) {
        		if(enemyPanzers.get(i)!=null) {
		        	if(enemyPanzers.get(0).getHealth()<=0) {
		        		enemyImage="panzer/broken3.png";
		        		enemy = ImageIO.read(new File(enemyImage));
			            g.drawImage(enemy, (int) a, (int) b, 100, 200*enemySide, null);
		        		enemySide=-1;
		        		enemyPanzers.remove(0);
		        	}else {
			            enemy = ImageIO.read(new File(enemyImage));
			            g.drawImage(enemy, (int) a, (int) b, 200, 100*enemySide, null);
		        	}
        		}
        	}
        } catch (IOException e) {
            e.printStackTrace();
        }
       
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();

        
        for(int i=0;i<myPanzers.size();i++) {
        	Panzer currPanzer=myPanzers.get(i);
        	if(currPanzer.getState().equalsIgnoreCase("move")) {
        		double targetX = currPanzer.getNextX();
                double targetY = currPanzer.getNextY();

                double deltaX = targetX - currPanzer.getX();
                double deltaY = targetY - currPanzer.getY();

                double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
                if (distance < myPanzers.get(0).getSpeed()) {
                	myPanzers.get(i).setX(targetX);
                	myPanzers.get(i).setY(targetY);
                    myPanzers.get(i).setState("stop");
                    return;
                }

                double directionX = deltaX / distance;
                double directionY = deltaY / distance;
                angle = Math.atan2(directionY, directionX);
                if(myPanzers.get(i).getState().equalsIgnoreCase("stop")) {
                	directionX=0;
                	directionY=0;
                	targetX=myPanzers.get(i).getX();
                	targetY=myPanzers.get(i).getY();
                }
                if(isCrashedOnObstacle()) {
                	myPanzers.get(i).setX(currPanzer.getX()-directionX*50);
                	myPanzers.get(i).setY(currPanzer.getY()-directionY*50);
                	myPanzers.get(i).setState("stop");
                	/*x-=directionX*50;
                	y-=directionY*50;
                	myPanzers.get(0).setState("stop");*/
                	playSound("audio/panzerCrashOnObst.wav",0);
                }else {
                	myPanzers.get(i).setX(currPanzer.getX()+directionX*currPanzer.getSpeed());
                	myPanzers.get(i).setY(currPanzer.getY()+directionY*currPanzer.getSpeed());
                	
        	        /*x += directionX * myPanzers.get(0).getSpeed();
        	        y += directionY * myPanzers.get(0).getSpeed();*/
                }
    	        
    	        
    	        
        	}
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    	System.out.println(e.getX()+"    "+e.getY());
    	
    	if(selected.size()>0 && e.getButton()==MouseEvent.BUTTON1) {//SHOOT
	    	for(int i=0;i<enemyPanzers.size();i++) {
	    		if(e.getX()>enemyPanzers.get(i).getX() && e.getX()<enemyPanzers.get(i).getX()+100 &&
	    				e.getY()>enemyPanzers.get(i).getY()-10 && e.getY()<enemyPanzers.get(i).getY()+100) {
	    			playSound("audio/panzerFire.wav", 0);
	    			for(int j=0;j<selected.size();j++) {
		                selected.get(j).setState("shot");
		                selected.get(j).setTarget(enemyPanzers.get(i));
	    			}
	    		}else {
	    			for(int j=0;j<selected.size();j++) {
		                selected.get(j).setState("move");
		                selected.get(j).setNextX(e.getX());
		                selected.get(j).setNextY(e.getY());
	    			}
	    			playSound("audio/panzerSelect1.wav", 0);
	    		}
	    	}
    	}
    	if(e.getButton()==MouseEvent.BUTTON1) {//SELECTING UNITS
	    	for(int i=0;i<myPanzers.size();i++) {
	    		if(e.getX()>myPanzers.get(i).getX() && e.getX()<myPanzers.get(i).getX()+100 &&
	    		   e.getY()>myPanzers.get(i).getY()-10 && e.getY()<myPanzers.get(i).getY()+100) {
	    			selected.push(myPanzers.get(i));
	    			playSound("audio/panzerSound.wav", 0);
	    			System.out.println(selected);
	    		}
	    	}
    	}
    	if(e.getButton()==MouseEvent.BUTTON3) {//Unselect
    		selected=new LinkedList<>();
    	}
        
    }


    public static void playSound(String soundFile, int loopCount) {
        try {
            File soundPath = new File(soundFile);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundPath);
            Clip clip = AudioSystem.getClip();

            clip.open(audioStream);
            clip.loop(loopCount);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}

public class Field {
    public static void main(String[] args) throws IOException {
        Crtaj c = new Crtaj();
        JFrame frame = new JFrame();

        frame.setSize(1000, 1000);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(c);
    }
}
