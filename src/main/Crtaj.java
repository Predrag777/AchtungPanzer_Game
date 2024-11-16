package main;

import java.awt.CardLayout;
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
import features.selectedUnits;
public class Crtaj extends JPanel implements MouseListener, ActionListener, MouseMotionListener {
    int numberOfEnemyPanzers;
    
    LinkedList<Unit> myUnits=new LinkedList<>();
    LinkedList<Unit> enemyUnits=new LinkedList<>();
    LinkedList<Unit> selected=new LinkedList<>();
    
    private CardLayout cardLayout;
    private JPanel selectionMenu;
    private JPanel callMenu; //Call bombing, parachuter reinforcement
    
    Timer t = new Timer(100, this);
    int counterForFire = 0;
    double angle = 0;
    int enemySide=1;
    int infMove=0;
    
    int infSide=1;
    int enemyInfSide=1;
    int selectedX=0;
    int selectedY=900;
    
    int viewX=0,viewY=0;
    int mapSpeed=20;
    int borders=40;
    selectedUnits selectedImage;
    
    String radioSound=null;
    
    Random rand = new Random();
    HashMap<Panzer, Panzer> targets=new HashMap<>();
    Obstacles[] obs=new Obstacles[30];
    BufferedImage background;
    //BufferedImage images[];
    String brokens[]= {"panzer/broken1.png","panzer/broken2.png","panzer/broken3.png","panzer/broken2.png","panzer/broken3.png", "panzer/broken1.png","panzer/broken2.png","panzer/broken3.png","panzer/broken2.png","panzer/broken3.png"};
    public Crtaj() throws IOException {
        t.start();
        selectedImage=new selectedUnits();
        this.myUnits.push(new Panzer("tiger", 100, 100, 500, 650, 40, 1000, 10, 60));
        this.myUnits.push(new Panzer("panzerIV", 400, 100, 500, 650, 40, 1000, 10, 60));
        
        this.myUnits.push(new Infantry("Rifle", 50,50, 20,500, 30,10, 5, 30, 25));
        this.myUnits.push(new Infantry("Rifle", 50,100, 20,500, 30,10, 5, 30, 25));
        this.myUnits.push(new Infantry("Rifle", 50,150, 20,500, 30,10, 5, 30, 25));
        
        this.myUnits.push(new Infantry("MachinePistol", 50,230, 20,500, 3,15, 5, 80, 3));
        
        this.myUnits.push(new Infantry("Mortar",100,50, 20,800, 25,10, 1, 50, 25));
        
        this.enemyUnits.push(new Panzer("Sherman", 500, 800, 500, 700, 20, 50, 10, 80));
        this.enemyUnits.push(new Infantry("Rifle", 300, 800, 20,450, 30,10, 5, 30, 25));
        this.enemyUnits.push(new Infantry("Rifle", 700, 800, 20,450, 30,10, 5, 30, 25));
        this.enemyUnits.push(new Infantry("Rifle", 750, 850, 20,450, 30,10, 5, 30, 25));
        
        obs[0]=new Obstacles("panzer/broken1.png",300, 400,100, 200, false);
        obs[1]=new Obstacles("panzer/broken2.png",350, 500,100, 200, false);
        obs[2]=new Obstacles("panzer/broken3.png",1600, 50,100, 200, false);
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
        selectedImage.setBounds(selectedX, selectedY, 1000, 100);
        this.setLayout(null);
        this.add(selectedImage);
        
        addMouseMotionListener(this);
        setSize(4000, 4000);
        setVisible(true);
        addMouseListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }
    
    
    public void initialize() {
    	selectedUnits selected=new selectedUnits();
    	
    }
    
    
    
    int mouseX=0;
    int mouseY=0;

    
    
    
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        selectedImage.setBounds(selectedX+viewX, selectedY+viewY, 1000, 100);
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
    				e.printStackTrace();
    			}
        		
        	}
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
        			int coordinations[]=damagePanzer(myUnits.get(i));
        			if(myUnits.get(i).getFireRate()==10) {
        				g2d.drawImage(ImageIO.read(new File("specEffects/rifleHit.png")), coordinations[0], coordinations[1], 45,45,null);
        			}
        		}else if(myUnits.get(i).getTarget()!=null && myUnits.get(i) instanceof Panzer) {
        			int coordinations[]=damagePanzer(myUnits.get(i));
        			if(myUnits.get(i).getFireRate()==10) {
        				g2d.drawImage(ImageIO.read(new File("specEffects/panzerHit.png")), coordinations[0], coordinations[1], 100,100,null);
        			}
        		}
        	}
        	
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            for (int i = 0; i < enemyUnits.size(); i++) {
                Unit currUnit = enemyUnits.get(i);

                if (currUnit.getState().equalsIgnoreCase("stop") || currUnit.getTarget() == null) {
                    currUnit.setState("stop");
                    currUnit.setTarget(null);
                    currUnit.setCommand("Base");
                }

                boolean rotated = false;

                if (currUnit instanceof Panzer && currUnit.getTarget() != null) {
                    Unit target = (Unit) currUnit.getTarget();
                    double deltaX = target.getX() - currUnit.getX();
                    double deltaY = target.getY() - currUnit.getY();
                    double angle = Math.atan2(deltaY, deltaX);

                    double centerX = currUnit.getX() + 100;
                    double centerY = currUnit.getY() + 50;

                    g2d.rotate(angle, centerX, centerY);
                    rotated = true; 

                    int[] coordinations = damagePanzer(currUnit);
                    if (currUnit.getFireRate() == 10) {
                        g2d.drawImage(ImageIO.read(new File("specEffects/panzerHit.png")), coordinations[0], coordinations[1], 100, 100, null);
                    }
                }

                if (!rotated && currUnit instanceof Panzer &&
                    (currUnit.getX() != currUnit.getNextX() || currUnit.getY() != currUnit.getNextY())) {
                    
                    double deltaX = currUnit.getNextX() - currUnit.getX();
                    double deltaY = currUnit.getNextY() - currUnit.getY();
                    double angle = Math.atan2(deltaY, deltaX);

                    double centerX = currUnit.getX() + 100;
                    double centerY = currUnit.getY() + 50;

                    g2d.rotate(angle, centerX, centerY);
                }

                if (currUnit instanceof Infantry) {
                    if (currUnit.getTarget() != null) {
                        Unit target = (Unit) currUnit.getTarget();
                        enemyInfSide = (currUnit.getX() >= target.getX()) ? -1 : 1;
                    }
                    g2d.drawImage(
                        ImageIO.read(new File("infantry/US/" + currUnit.getCommand() + currUnit.getName() + ".png")),
                        (int) currUnit.getX(),
                        (int) currUnit.getY(),
                        50 * enemyInfSide, 70,
                        null
                    );
                } else if (currUnit instanceof Panzer) {
                    g2d.drawImage(
                        ImageIO.read(new File("panzer/" + currUnit.getCommand() + currUnit.getName() + ".png")),
                        (int) currUnit.getX(),
                        (int) currUnit.getY(),
                        200, 100 * enemySide,
                        null
                    );
                }

                if (currUnit.getTarget() != null && currUnit instanceof Infantry) {
                    int[] coordinations = damagePanzer(currUnit);
                    if (currUnit.getFireRate() == 10) {
                        g2d.drawImage(ImageIO.read(new File("specEffects/rifleHit.png")), coordinations[0], coordinations[1], 45, 45, null);
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
        	
        	Unit newTarget=autoShot(myUnits.get(i), enemyUnits);
        	
    		if(newTarget!=null && myUnits.get(i).getTarget()==null && !myUnits.get(i).getState().equalsIgnoreCase("move")) {
    			myUnits.get(i).setTarget(newTarget);
    			myUnits.get(i).getTarget().setEnemy(myUnits.get(i));
    			myUnits.get(i).setState("shot");
    		}
        	if(myUnits.get(i).getTarget()!=null && myUnits.get(i).getState().equalsIgnoreCase("shot")) {
        		
        		
        		if(!checkShotingRange(myUnits.get(i), myUnits.get(i).getTarget())) {
        			//////SOUND FOR MISS THE RANGE
        			myUnits.get(i).setState("stop");
        			myUnits.get(i).setCommand("Base");
        		}else {
	        		if(myUnits.get(i) instanceof Panzer)
	        			panzerAnimationShot((Panzer) myUnits.get(i));
	        		else if(myUnits.get(i) instanceof Infantry) {
	        			if(myUnits.get(i).getName().contains("Machine"))
	        				machineAnimationShot((Infantry) myUnits.get(i));
	        			else
	        				infantryAnimationShot((Infantry) myUnits.get(i));
	        		}
	        		if(!enemyUnits.contains(myUnits.get(i).getTarget())) {
	        			myUnits.get(i).setTarget(null);
	        			myUnits.get(i).setState("stop");
	        			myUnits.get(i).setCommand("Base");
	        		}
        		}
        	}else if(myUnits.get(i).getState().equalsIgnoreCase("move")){
        		if(myUnits.get(i) instanceof Infantry)
        			infantryAnimationMove((Infantry) myUnits.get(i));
        		else if(myUnits.get(i) instanceof Panzer)
        			panzerAnimationMove((Panzer)myUnits.get(i));
        	}
        	
        	if(myUnits.get(i).getHealth()<=0) {//DEATH
        		myUnits.remove(myUnits.get(i));
        	}
        }
        
        for(int i=0;i<enemyUnits.size();i++) {
        	
        	
        	if(enemyUnits.get(i).getEnemy()!=null && enemyUnits.get(i).getTarget()==null) {
        		moveEnemy(enemyUnits.get(i));
        	}
        	Unit newTarget=autoShot(enemyUnits.get(i), myUnits);
        	
        	if(newTarget!=null && enemyUnits.get(i).getTarget()==null && !enemyUnits.get(i).getState().equalsIgnoreCase("move")) {
        		enemyUnits.get(i).setTarget(newTarget);
        		enemyUnits.get(i).getTarget().setEnemy(enemyUnits.get(i));
        		enemyUnits.get(i).setState("shot");
    		}
        	if(enemyUnits.get(i).getTarget()!=null && !checkShotingRange(enemyUnits.get(i), enemyUnits.get(i).getTarget())) {
        		enemyUnits.get(i).setTarget(null);
        		enemyUnits.get(i).setState("stop");
        		enemyUnits.get(i).setCommand("Base");
        	}
        	
        	if(enemyUnits.get(i).getState().equalsIgnoreCase("shot") && enemyUnits.get(i).getTarget()!=null) {
        		if(enemyUnits.get(i) instanceof Panzer)
        			panzerAnimationShot((Panzer) enemyUnits.get(i));
        		else if(enemyUnits.get(i) instanceof Infantry)
        			infantryAnimationShot((Infantry) enemyUnits.get(i));
        		if(!myUnits.contains(enemyUnits.get(i).getTarget())) {
        			enemyUnits.get(i).setTarget(null);
            		enemyUnits.get(i).setState("stop");
            		enemyUnits.get(i).setCommand("Base");
        		}
        	}
        	
        	if(enemyUnits.get(i).getHealth()<=0) {//DEATH
        		enemyUnits.remove(enemyUnits.get(i));
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
	    		if(((x>enemyUnits.get(i).getX()-20 && x<enemyUnits.get(i).getX()+100 &&
	    				y>enemyUnits.get(i).getY()-100 && y<enemyUnits.get(i).getY()+100))) {
	    			
	    			
	    			for(int j=0;j<selected.size();j++) {
	    				if(selected.get(j) instanceof Panzer)
	    					playSound("audio/panzerFire.wav", 0);
	    				else if(selected.get(j) instanceof Infantry)
	    					playSound("audio/Infantry/heer/shot.wav", 0);
		                selected.get(j).setState("shot");
		                selected.get(j).setTarget(enemyUnits.get(i));
		                selected.get(j).getTarget().setEnemy(selected.get(j));
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
		    		if(x>myUnits.get(i).getX() && x<myUnits.get(i).getX()+100 &&
		    		   y>myUnits.get(i).getY()-10 && y<myUnits.get(i).getY()+100) {
		    			selected.push(myUnits.get(i));
		    			if(myUnits.get(i) instanceof Panzer)
		    				playSound("audio/panzerSound.wav", 0);
		    			else if(myUnits.get(i) instanceof Infantry)
		    				playSound("audio/Infantry/heer/selected.wav", 0);
		    		}
	    	}
    	}
    	
    	if(e.getButton()==MouseEvent.BUTTON3) {//Unselect
    		selected=new LinkedList<>();
    	}
        
    }

    public int[] mortarShot(Unit unit) {
    	int coordinations[]=new int[2];
    	Infantry myMortar=(Infantry)unit;
    	Unit target=unit.getTarget();
    	
    	
    	return coordinations;
    	
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
    
    public void makeDistance(Unit unit, LinkedList<Unit> yourUnit, int pointer) {
    	for(int i=0;i<yourUnit.size()-1;i++) {
    		if(i!=pointer && Math.abs(unit.getX()-yourUnit.get(i).getX())<100) {
    			unit.setX(yourUnit.get(i).getX()+100);
    		}
    	}
    }
    
    public boolean isCrashedOnObstacle() {
    	for(int i=0;i<obs.length;i++) {
    		for(int j=0;j<myUnits.size();j++) {
    			Unit curr=myUnits.get(j);
    		if(curr.getX()>=obs[i].x-100 && curr.getX()<=obs[i].x
    				&& curr.getY()>=obs[i].y && curr.getY()<=obs[i].y+100)
    			{
    				return true;
    			}
    		}
    	}
    	return false;
    }
    
    public Unit autoShot(Unit unit, LinkedList<Unit> targetUnit) {
    	double shortest=999999;
    	Unit target=null;
    	for(int i=0;i<targetUnit.size();i++) {
    		
    		double deltaX = targetUnit.get(i).getX() - unit.getX();
            double deltaY = targetUnit.get(i).getY() - unit.getY();
            double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    		if(distance<unit.getFireRange() && distance<shortest) {
    			shortest=distance;
    			target=targetUnit.get(i);
    		}
    	}
    	
    	
    	return target;
    }
    
    public boolean checkShotingRange(Unit unit, Unit target) {
    	double deltaX=target.getX()-unit.getX();
    	double deltaY=target.getY()-unit.getY();
    	
    	double distance=Math.sqrt(deltaX*deltaX+deltaY*deltaY);
    	
    	if(unit.getFireRange()>=distance)
    		return true;
    	
    	return false;
    }
    
    public int[] damagePanzer(Unit unit) {
    	Random rand=new Random();
    	int newX=(int)(unit.getTarget().getX()-rand.nextInt(unit.getShootingError()));
		int newY=(int)(unit.getTarget().getY()-rand.nextInt(unit.getShootingError()));
		
		int []coordinations= {newX,newY};
		if(unit.getFireRate()==10 || (unit.getFireRate()==1 && unit.getName().contains("Machine"))) {
			//System.out.println((int)unit.getTarget().getX()-newX);
			if((int)unit.getTarget().getX()-newX<=10) {//full damage
				unit.getTarget().setHealth(unit.getTarget().getHealth()-unit.getDamage());
			}
			else if((int)unit.getTarget().getX()-newX<50) {
				unit.getTarget().setHealth((int)(unit.getTarget().getHealth()-unit.getDamage()*0.3));
			}
			else if((int)unit.getTarget().getX()-newX<70) {
				unit.getTarget().setHealth((int)(unit.getTarget().getHealth()-unit.getDamage()*0.5));
			}
		}
    	
    	return coordinations;
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
    			panzer.setFireRate(40);
    		}
    		panzer.setFireRate(panzer.getFireRate()-1);
    	}
    }
    
     public void machineAnimationShot(Infantry inf) {
    	 if(inf.getState().equalsIgnoreCase("shot")) {
     		//System.out.println(inf.getFireRate());
     		if(inf.getFireRate()>2) {
     			inf.setCommand("shot1");
     		}else if(inf.getFireRate()>1) {
     			inf.setCommand("shot2");
     		}else if(inf.getFireRate()>0){
     			if(inf.getFireRate()==1)
     				playSound("audio/Infantry/rifleShot1.wav",0);
     			inf.setCommand("shot3");
     		}else {
     			inf.setFireRate(inf.reloading);
     		}
     		inf.setFireRate(inf.getFireRate()-1);
     	}
     }
     
    public void infantryAnimationShot(Infantry inf) {
    	
    	if(inf.getState().equalsIgnoreCase("shot")) {
    		
    		if(inf.getFireRate()>20) {
    			if(inf.getFireRate()==inf.reloading-1)
    				if(inf.getName().equalsIgnoreCase("Mortar"))
    	    			playSound("audio/mortarShot.wav",0);
    			inf.setCommand("shot1");
    		}else if(inf.getFireRate()>10) {
    			inf.setCommand("shot2");
    		}else if(inf.getFireRate()>5){
    			if(inf.getFireRate()==10 && !inf.getName().equalsIgnoreCase("Mortar"))
    				playSound("audio/Infantry/rifleShot1.wav",0);
    			inf.setCommand("shot3");
    		}else {
    			inf.setFireRate(inf.reloading);
    		}
    		inf.setFireRate(inf.getFireRate()-1);
    	}
    }
    
    public void panzerAnimationMove(Panzer panzer) {
    	panzer.setTarget(null);
    	double deltaX=panzer.getNextX()-panzer.getX();
    	double deltaY=panzer.getNextY()-panzer.getY();
    	
    	double distance=Math.sqrt(deltaX*deltaX+deltaY*deltaY);
    	
    	double directionX = deltaX / distance;
        double directionY = deltaY / distance;
        angle = Math.atan2(directionY, directionX);
        
        if (distance < 10) {
        	panzer.setX(panzer.getNextX());
        	panzer.setY(panzer.getNextY());
        	panzer.setState("stop");
            return;
        }
        panzer.setX(panzer.getX()+directionX*panzer.getSpeed());
        panzer.setY(panzer.getY()+directionY*panzer.getSpeed());
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

    public void moveEnemy(Unit enemyUnit) {
    	Unit target=enemyUnit.getEnemy();
    	double x=target.getX();
    	double y=target.getY();
    	
    	if(checkShotingRange(enemyUnit, target) && enemyUnit.getTarget()==null) {
    		enemyUnit.setTarget(target);
    		enemyUnit.setState("shot");
    	}else {
    		enemyUnit.setNextX(target.getX());
    		enemyUnit.setNextY(target.getY());
    		if(enemyUnit instanceof Infantry) {
    			infantryAnimationMove((Infantry)enemyUnit);
    		}else if(enemyUnit instanceof Panzer) {
    			panzerAnimationMove((Panzer)enemyUnit);
    		}
    		
    		
    		
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




