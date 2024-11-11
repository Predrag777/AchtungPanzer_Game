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
import java.awt.event.MouseMotionListener;
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

class Crtaj extends JPanel implements MouseListener, ActionListener, MouseMotionListener {
    int numberOfEnemyPanzers;
    //LinkedList<Panzer> myPanzers=new LinkedList<>();
    //LinkedList<Infantry> infantry=new LinkedList<>();
    
    LinkedList<Unit> myUnits=new LinkedList<>();
    LinkedList<Unit> enemyUnits=new LinkedList<>();
    
    
    LinkedList<Unit> selected=new LinkedList<>();
    //LinkedList<Infantry> infSelected=new LinkedList<>();
    Timer t = new Timer(100, this);
    double x = 100, y = 100;
    double x1 = 400, y1 = 100;
    int counterForFire = 0;
    double angle = 0;
    double a = 500, b = 800;
    int enemySide=1;
    int infMove=0;
    
    int infSide=1;
    
    int viewX=0,viewY=0;
    int mapSpeed=20;
    int borders=40;
    
    String radioSound=null;
    
    Random rand = new Random();
    HashMap<Panzer, Panzer> targets=new HashMap<>();
    Obstacles[] obs=new Obstacles[30];
    BufferedImage background;
    //BufferedImage images[];
    String brokens[]= {"panzer/broken1.png","panzer/broken2.png","panzer/broken3.png","panzer/broken2.png","panzer/broken3.png", "panzer/broken1.png","panzer/broken2.png","panzer/broken3.png","panzer/broken2.png","panzer/broken3.png"};
    public Crtaj() throws IOException {
        t.start();
        this.myUnits.push(new Panzer("tiger", x, y, 500, 650, 40, 50, 10));
        this.myUnits.push(new Panzer("panzerIV", x1, y1, 500, 650, 40, 50, 10));
        //name,  x,  y,  health,  fireRange, fireRate,  damage,  speed
        this.myUnits.push(new Infantry("Rifle", 50,50, 20,25, 30,10, 5));
        this.myUnits.push(new Infantry("Rifle", 50,100, 20,25, 30,10, 5));
        this.myUnits.push(new Infantry("Rifle", 50,150, 20,25, 30,10, 5));
        
        this.myUnits.push(new Infantry("MachinePistol", 50,230, 20,25, 15,10, 5));
       
        
        this.enemyUnits.push(new Panzer("Sherman", a, b, 500, 700, 20, 50, 10));

        this.enemyUnits.push(new Infantry("Rifle", 400, 800, 20,25, 30,10, 5));
        
        
        obs[0]=new Obstacles("panzer/broken1.png",300, 400,100, 200, false);
        obs[1]=new Obstacles("panzer/broken2.png",350, 500,100, 200, false);
        obs[2]=new Obstacles("panzer/broken3.png",600, 50,100, 200, false);
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
        
        try {
            background = ImageIO.read(new File("panzer/background.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        addMouseMotionListener(this);
        setSize(4000, 4000);
        setVisible(true);
        addMouseListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }
    
    int mouseX=0;
    int mouseY=0;

    
    
    public boolean canNotShot() {
    	
    	return true;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D) g;
        g.translate(-viewX, -viewY);
        
        if (background != null) {
        	int imageWidth = background.getWidth();
            int imageHeight = background.getHeight();

            for (int x = 0; x < 10000+ viewX; x += imageWidth) {
                for (int y = 0; y < 10000 + viewY; y += imageHeight) {
                    g2d.drawImage(background, x - viewX, y - viewY, null);
                }
            }
         }

        for(int i=0;i<obs.length;i++) {
        	try {
				g.drawImage(ImageIO.read(new File(obs[i].name)), obs[i].x, obs[i].y, obs[i].width, obs[i].height, null);
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        
        
        BufferedImage panzer, enemy;
        
        String enemyImage="panzer/shermanBase.png";
        
        Random rand=new Random();
        
        
        AffineTransform oldTransform = g2d.getTransform();
        try {
        	for(int i=0;i<myUnits.size();i++) {
        		try {
        			if(myUnits.get(i) instanceof Infantry) {
	        			if(myUnits.get(i).getX()>myUnits.get(i).getNextX() || (myUnits.get(i).getTarget()!=null && myUnits.get(i).getX()>myUnits.get(i).getTarget().getX()) )
	        				infSide=-1;
	        			else
	        				infSide=1;
	    				g.drawImage(ImageIO.read(new File("infantry/Heer/"+myUnits.get(i).getCommand()+""+myUnits.get(i).getName()+".png")), (int)myUnits.get(i).getX(), (int)myUnits.get(i).getY(), 50*infSide, 70, null);
        			}
        		} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
        		
        	}
        	
        	//String command="Base";
        	for(int i=0;i<myUnits.size();i++) {
        		if(myUnits.get(i) instanceof Panzer) {
        		if(myUnits.get(i).getState().equalsIgnoreCase("shot") && myUnits.get(i).getTarget()!=null) {
        			Unit target=(Unit) myUnits.get(i).getTarget();
        			Unit currPanzer=myUnits.get(i);
        			double deltaX=target.getX()-currPanzer.getX();
        			double deltaY=target.getY()-currPanzer.getY();
        			
        			double angle=Math.atan2(deltaY, deltaX);
        			
        			double centerX = currPanzer.getX() + 100;
        	        double centerY = currPanzer.getY() + 50;
        	        g2d.rotate(angle, centerX,centerY);
        	       
        	        
        	        counterForFire++;
        		}
        		if(myUnits.get(i).getState().equalsIgnoreCase("move")) {
        			Unit currPanzer=myUnits.get(i);
        			double deltaX=currPanzer.getNextX()-currPanzer.getX();
        			double deltaY=currPanzer.getNextY()-currPanzer.getY();
        			
        			double angle=Math.atan2(deltaY, deltaX);
        			
        			double centerX = currPanzer.getX() + 100;
        	        double centerY = currPanzer.getY() + 50;
        	        
        	        g2d.rotate(angle, centerX,centerY);
        	        
        		}
        		
        		g2d.drawImage(ImageIO.read(new File("panzer/"+myUnits.get(i).getName()+myUnits.get(i).getCommand()+".png")), (int) myUnits.get(i).getX(), (int) myUnits.get(i).getY(), 200, 100, null);
        		g2d.setTransform(oldTransform);
        		
        	}
        		if(myUnits.get(i).getTarget()!=null && myUnits.get(i) instanceof Infantry) {
        			int newX=(int)myUnits.get(i).getTarget().getX()-rand.nextInt(0,50);
        			int newY=(int)myUnits.get(i).getTarget().getY()-rand.nextInt(0,50);
        			if(myUnits.get(i).getFireRate()<10) {
        				myUnits.get(i).getTarget().setHealth(0);
        				g2d.drawImage(ImageIO.read(new File("specEffects/rifleHit.png")), newX, newY, 45,45,null);
        			}
        		}else if(myUnits.get(i).getTarget()!=null && myUnits.get(i) instanceof Panzer) {
        			int newX=(int)myUnits.get(i).getTarget().getX()-rand.nextInt(0,50);
        			int newY=(int)myUnits.get(i).getTarget().getY()-rand.nextInt(0,50);
        			if(myUnits.get(i).getFireRate()<10) {
        				myUnits.get(i).getTarget().setHealth(0);
        				g2d.drawImage(ImageIO.read(new File("specEffects/panzerHit.png")), newX, newY, 100,100,null);
        			}
        		}
        	}
        	
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
        	
        	for(int i=0;i<enemyUnits.size();i++) {
        		
        		if(enemyUnits.get(i) instanceof Panzer && enemyUnits.get(i).getTarget()!=null) {
        			Unit target=(Unit) enemyUnits.get(i).getTarget();
        			Unit currPanzer=enemyUnits.get(i);
        			double deltaX=target.getX()-currPanzer.getX();
        			double deltaY=target.getY()-currPanzer.getY();
        			
        			double angle=Math.atan2(deltaY, deltaX);
        			
        			double centerX = currPanzer.getX() + 100;
        	        double centerY = currPanzer.getY() + 50;
        	        g2d.rotate(angle, centerX,centerY);
        		}
        		
        		if(enemyUnits.get(i) instanceof Infantry) {
        			
        			if(enemyUnits.get(i).getHealth()<=0)
        				g2d.drawImage(ImageIO.read(new File("infantry/US/"+enemyUnits.get(i).getCommand()+""+enemyUnits.get(i).getName()+".png")), (int)enemyUnits.get(i).getX(), (int)enemyUnits.get(i).getY(), 200, 50, null);
        			else
        				g2d.drawImage(ImageIO.read(new File("infantry/US/"+enemyUnits.get(i).getCommand()+""+enemyUnits.get(i).getName()+".png")), (int)enemyUnits.get(i).getX(), (int)enemyUnits.get(i).getY(), 50, 70, null);
        		}else if(enemyUnits.get(i) instanceof Panzer) {
        			g2d.drawImage(ImageIO.read(new File("panzer/"+enemyUnits.get(i).getCommand()+enemyUnits.get(i).getName()+".png")), (int) a, (int) b, 200, 100*enemySide, null);
        		}

        	}

        } catch (IOException e) {
            e.printStackTrace();
        }
       
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        if (mouseY < borders && viewY-mapSpeed>=0) {
            viewY -= mapSpeed;
        }
        else if (mouseY > getHeight() - borders) {
            viewY += mapSpeed;
        }
        if (mouseX < borders && viewX-mapSpeed>=0) {
            viewX -= mapSpeed;
        }
        else if (mouseX > getWidth() - borders) {
            viewX += mapSpeed;
        }
        
        for(int i=0;i<myUnits.size();i++) {
	        	if(myUnits.get(i) instanceof Infantry) {
		        	if(myUnits.get(i).getState().equalsIgnoreCase("move"))
		        		infantryAnimationMove((Infantry)myUnits.get(i));
		        	if(myUnits.get(i).getState().equalsIgnoreCase("shot")) {
		        		infantryAnimationShot((Infantry)myUnits.get(i));
		        		
		        	}
		        	if(myUnits.get(i).getState().equalsIgnoreCase("stop")) {
		        		myUnits.get(i).setCommand("stand");
		        	}
        	}
        }

        for(int i=0;i<enemyUnits.size();i++) {
        	if(enemyUnits.get(i) instanceof Panzer && enemyUnits.get(i).getState().equalsIgnoreCase("shot")) {
        		panzerAnimationShot((Panzer)enemyUnits.get(i));
        		
        		
        	}
        	
        	if(enemyUnits.get(i) instanceof Infantry && enemyUnits.get(i).getHealth()<=0) {
        		Infantry inf=(Infantry) enemyUnits.get(i);
        		enemyUnits.get(i).setCommand("");
        		if(inf.deathCounter>15)
        			enemyUnits.get(i).setName("death1");
        		else if(inf.deathCounter>10)
        			enemyUnits.get(i).setName("death2");
        		else if(inf.deathCounter>0)
        			enemyUnits.get(i).setName("death3");
        		else {
        			enemyUnits.remove(i);
        			break;
        		}
        		if(enemyUnits.get(i)!=null)
        			inf.deathCounter-=1;
        	}else if(enemyUnits.get(i) instanceof Panzer && enemyUnits.get(i).getHealth()<=0) {
        		Panzer panzer=(Panzer) enemyUnits.get(i);
        		enemyUnits.get(i).setCommand("");
        		if(panzer.deathCounter>5)
        			enemyUnits.get(i).setName("broken4");
        		else {
        			enemyUnits.remove(i);
        			break;
        		}
        		if(enemyUnits.get(i)!=null)
        			panzer.deathCounter-=1;
        	}
        }
        for(int i=0;i<myUnits.size();i++) {
        	if(myUnits.get(i) instanceof Panzer) {
        	Panzer currPanzer=(Panzer)myUnits.get(i);
        	if(myUnits.get(i).getState().equalsIgnoreCase("shot"))
	        	panzerAnimationShot((Panzer)myUnits.get(i));
        	if(currPanzer.getState().equalsIgnoreCase("move")) {
        		double targetX = currPanzer.getNextX();
                double targetY = currPanzer.getNextY();

                double deltaX = targetX - currPanzer.getX();
                double deltaY = targetY - currPanzer.getY();

                double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
                if (distance < myUnits.get(0).getSpeed()) {
                	myUnits.get(i).setX(targetX);
                	myUnits.get(i).setY(targetY);
                	myUnits.get(i).setState("stop");
                    return;
                }

                double directionX = deltaX / distance;
                double directionY = deltaY / distance;
                angle = Math.atan2(directionY, directionX);
                if(myUnits.get(i).getState().equalsIgnoreCase("stop")) {
                	directionX=0;
                	directionY=0;
                	targetX=myUnits.get(i).getX();
                	targetY=myUnits.get(i).getY();
                }
                if(isCrashedOnObstacle()) {
                	myUnits.get(i).setX(currPanzer.getX()-directionX*50);
                	myUnits.get(i).setY(currPanzer.getY()-directionY*50);
                	myUnits.get(i).setState("stop");
                	playSound("audio/panzerCrashOnObst.wav",0);
                }else {
                	myUnits.get(i).setX(currPanzer.getX()+directionX*currPanzer.getSpeed());
                	myUnits.get(i).setY(currPanzer.getY()+directionY*currPanzer.getSpeed());
                	
                }
                
    	        
    	        
    	        
        	}
        	}
        	}
        
        for(int i=0;i<myUnits.size();i++) {
        	Unit newTarget=autoShot(myUnits.get(i), enemyUnits);
        	if(newTarget!=null && myUnits.get(i).getTarget()==null && !myUnits.get(i).getState().equalsIgnoreCase("move")) {
        		myUnits.get(i).setState("shot");
        		playSound("audio/germanAutoShot.wav",0);
        		myUnits.get(i).setTarget(newTarget);
        	}
        }
        for(int i=0;i<enemyUnits.size();i++) {
        	Unit newTarget=autoShot(enemyUnits.get(i), myUnits);
        	
        	if(newTarget!=null && enemyUnits.get(i).getTarget()==null && !enemyUnits.get(i).getState().equalsIgnoreCase("move")) {
        		System.out.println(enemyUnits.get(i));
        		enemyUnits.get(i).setState("shot");
        		
        		enemyUnits.get(i).setTarget(newTarget);
        	}
        }
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    	int x=e.getX()+viewX;
    	int y=e.getY()+viewY;
    	
    	boolean foundTarget=false;
    	
    	if(selected.size()>0 && e.getButton()==MouseEvent.BUTTON1) {//SHOOT
	    	for(int i=0;i<enemyUnits.size();i++) {
	    		//System.out.println((x>enemyUnits.get(i).getX()-20)+"   "+ (x<enemyUnits.get(i).getX()+70) +"  "+(y>enemyUnits.get(i).getY()-30) +"   "+(y<enemyUnits.get(i).getY()+100));
	    		if(((x>enemyUnits.get(i).getX()-20 && x<enemyUnits.get(i).getX()+70 &&
	    				y>enemyUnits.get(i).getY()-30 && y<enemyUnits.get(i).getY()+100))) {
	    			
	    			
	    			for(int j=0;j<selected.size();j++) {
	    				if(selected.get(j) instanceof Panzer)
	    					playSound("audio/panzerFire.wav", 0);
	    				else if(selected.get(j) instanceof Infantry)
	    					playSound("audio/Infantry/heer/shot.wav", 0);
		                selected.get(j).setState("shot");
		                selected.get(j).setTarget(enemyUnits.get(i));
	    			}
	    			foundTarget=true;
	    		}
	    	}
    	}
    	if(selected.size()>0 && !foundTarget && e.getButton()==MouseEvent.BUTTON1) {
    		for(int i=0;i<selected.size();i++) {
    				selected.get(i).setTarget(null);
	                selected.get(i).setState("move");
	                selected.get(i).setCommand("Base");
	                selected.get(i).setNextX(x);
	                selected.get(i).setNextY(y);
	                if(selected.get(i) instanceof Panzer)
	                	playSound("audio/panzerSelect1.wav", 0);
	                else if(selected.get(i) instanceof Infantry)
	                	playSound("audio/Infantry/heer/move.wav", 0);
    		}
    			
	    		
    	}
    	if(e.getButton()==MouseEvent.BUTTON1) {//SELECTING UNITS
	    	for(int i=0;i<myUnits.size();i++) {
	    		//if(myUnits.get(i) instanceof Panzer) {
		    		if(x>myUnits.get(i).getX() && x<myUnits.get(i).getX()+100 &&
		    		   y>myUnits.get(i).getY()-10 && y<myUnits.get(i).getY()+100) {
		    			selected.push(myUnits.get(i));
		    			if(myUnits.get(i) instanceof Panzer)
		    				playSound("audio/panzerSound.wav", 0);
		    			else if(myUnits.get(i) instanceof Infantry)
		    				playSound("audio/Infantry/heer/selected.wav", 0);
		    		}
	    		//}
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
    
    
    
    public boolean isCrashedOnObstacle() {
    	
    	for(int i=0;i<obs.length;i++) {
    		for(int j=0;j<myUnits.size();j++) {
    			Unit curr=myUnits.get(j);
    		if(curr.getX()>=obs[i].x-100 && curr.getX()<=obs[i].x
    				&& curr.getY()>=obs[i].y && curr.getY()<=obs[i].y+100)
    			{
    				System.out.println("CRASH!!!!!!!!!");
    				return true;
    			}
    		}
    	}
    	return false;
    }
    
    public Unit autoShot(Unit unit, LinkedList<Unit> targetUnit) {
    	for(int i=0;i<targetUnit.size();i++) {
    		
    		double deltaX = targetUnit.get(i).getX() - unit.getX();
            double deltaY = targetUnit.get(i).getY() - unit.getY();

            double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    		
    		if(distance<unit.getFireRange()) {
    			return targetUnit.get(i);
    		}
    	}
    	
    	
    	return null;
    }
    
    public boolean checkShotingRange(Unit unit, Unit target) {
    	double deltaX=target.getX()-unit.getX();
    	double deltaY=target.getY()-unit.getY();
    	
    	double distance=Math.sqrt(deltaX*deltaX+deltaY*deltaY);
    	
    	if(unit.getFireRange()>=distance)
    		return true;
    	
    	return false;
    }
    
     public void panzerAnimationShot(Panzer panzer) {
    	if(panzer.getState().equalsIgnoreCase("shot")) {
    		if(panzer.getFireRate()>10) {
    			panzer.setCommand("Base");
    		}else if(panzer.getFireRate()>5) {
    			if(panzer.getFireRate()==10)
    				playSound("audio/tankShot1.wav",0);
    			panzer.setCommand("Shot");
    		}else {
    			panzer.setFireRate(26);
    		}
    		panzer.setFireRate(panzer.getFireRate()-1);
    	}
    }
    
    public void infantryAnimationShot(Infantry inf) {
    	//System.out.println(inf.getFireRate());
    	
    	if(inf.getState().equalsIgnoreCase("shot")) {
    		if(inf.getFireRate()>20) {
    			inf.setCommand("shot1");
    		}else if(inf.getFireRate()>10) {
    			inf.setCommand("shot2");
    		}else if(inf.getFireRate()>5){
    			if(inf.getFireRate()==10)
    				playSound("audio/Infantry/rifleShot1.wav",0);
    			inf.setCommand("shot3");
    		}else {
    			inf.setFireRate(26);
    		}
    		inf.setFireRate(inf.getFireRate()-1);
    	}
    	if(inf.getState().equalsIgnoreCase("move")) {
    		
    	}
    }
    
    public void infantryAnimationMove(Infantry inf) {
    	inf.setTarget(null);
    	double deltaX=inf.getNextX()-inf.getX();
    	double deltaY=inf.getNextY()-inf.getY();
    	
    	double distance=Math.sqrt(deltaX*deltaX+deltaY*deltaY);
    	
    	double directionX = deltaX / distance;
        double directionY = deltaY / distance;
        angle = Math.atan2(directionY, directionX);
        
        if (distance < 10) {
        	inf.setX(inf.getNextX());
        	inf.setY(inf.getNextY());
            inf.setState("stop");
            return;
        }
        inf.setX(inf.getX()+directionX*10);
    	inf.setY(inf.getY()+directionY*10);
        
        
    	if(infMove<5) {
    		inf.setCommand("move1");
    	}else if(infMove<10) {
    		inf.setCommand("move2");
    	}else {
    		infMove=-1;
    	}
    	infMove++;
    }

    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}



	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	

	@Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        

        repaint();
    }
    
    
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
