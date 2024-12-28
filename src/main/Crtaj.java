package main;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.List; 

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import Missions.Mission1;
import Missions.Mission;
public class Crtaj extends JPanel implements MouseListener, ActionListener, MouseMotionListener {
    int numberOfEnemyPanzers;
    
    
    int startX = 0, startY = 0, targetX = 4, targetY = 4;
    
    LinkedList<Unit> myUnits=new LinkedList<>();
    LinkedList<Unit> enemyUnits=new LinkedList<>();
    LinkedList<Unit> selected=new LinkedList<>();
    private CardLayout parentFrame;
    Timer t = new Timer(100, this);
    int counterForFire = 0;
    double angle = 0;
    int enemySide=1;
    int infMove=0;
    public boolean isRunning=true;
    int menuX=10,menuY=0;
    int newSize=500;
    int infSide=1;
    int enemyInfSide=1;
    int selectedX=0;
    int selectedY=850;
    int specX=0, specY=700;
    int viewX=0,viewY=0;
    int mapSpeed=20;
    int borders=40;
    int[][] compressedMap;
    int map[][]=new int[2000][2000];
    MenuBar menu;
    LinkedList<Explosion> bombingList=new LinkedList<>();
    Airplane airplane;
    //////Buttons
    boolean bombing=false, artillery=false, parachuter=false;
    boolean performBomb=false, performArti=false, perfrormPara=false;
    int perfSpecX=0, perfSpecY=0;
    
    int parachuterX=0;
    int parachuterY=0;
    private JPanel mainPanel;
    selectedUnits selectedImage;
    
    String radioSound=null;
    AI ai;
    Random rand = new Random();
    HashMap<Panzer, Panzer> targets=new HashMap<>();
    Obstacles[] obs=new Obstacles[30];
    BufferedImage background;
    List<Node> path;
    //BufferedImage images[];
    String brokens[]= {"panzer/broken1.png","panzer/broken2.png","panzer/broken3.png","panzer/broken2.png","panzer/broken3.png", "panzer/broken1.png","panzer/broken2.png","panzer/broken3.png","panzer/broken2.png","panzer/broken3.png"};
    public Crtaj(CardLayout parentFrame, JPanel mainPanel) throws IOException {
        t.start();
        
        this.parentFrame = parentFrame;
        this.mainPanel=mainPanel;
        this.airplane=new Airplane("parachuterAirplane.png");
        selectedImage=new selectedUnits();
        Mission1 m=new Mission1("SS");
        this.myUnits=m.getMyUnits();
        this.enemyUnits=m.getEnemyUnits();
        this.obs=m.getObs();
        
        
        bombingList.push(new Explosion("Artilery",  1));
        bombingList.push(new Explosion("Artilery",  1));
        bombingList.push(new Explosion("Artilery",  1));
        bombingList.push(new Explosion("Artilery",  1));
        
        try {
            background = ImageIO.read(new File("panzer/background.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }        
        
        selectedImage.setBounds(selectedX, selectedY, 1000, 250);
        
        this.setLayout(null);
        this.add(selectedImage);
        
        addMouseMotionListener(this);
        setSize(2000, 2000);
        setVisible(true);
        addMouseListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        ai=new AI(enemyUnits, myUnits);
        for(int i=0;i<this.obs.length;i++) {
        	int c=obs[i].x;
        	while(c<obs[i].x+this.obs[i].width && c<2000) {
        		map[obs[i].y][c]=1;
        		c+=1;
        	}
        	c=obs[i].y;
        	while(c>=obs[i].y-this.obs[i].height && c>0) {
        		map[c][obs[i].x]=1;
        		c-=1;
        	}
        }
        
        compressedMap = map;
        //compressedMap=compressMap(map, newSize);
        menu=new MenuBar();
        menu.setBounds(menuX, menuY, 1700, 50);
        this.add(menu);
        
        playSound("audio/covers/backgroundSound1.wav",50, -20);
    }
 
    int mouseX=0;
    int mouseY=0;
    

    

    
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
	    				g.drawImage(ImageIO.read(new File("infantry/Heer/"+myUnits.get(i).getCommand()+""+myUnits.get(i).getName()+".png")), (int)myUnits.get(i).getX(), (int)myUnits.get(i).getY(), 30*infSide, 50, null);
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
        			Panzer ss=(Panzer) currPanzer;

        			double centerX = currPanzer.getX() + 10;
        	        double centerY = currPanzer.getY() -10;
        	        g2d.rotate(angle, centerX,centerY);
        	       
        	        
        	        counterForFire++;
        		}
        		if(myUnits.get(i).getState().equalsIgnoreCase("move")) {
        			Unit currPanzer=myUnits.get(i);
        			if(currPanzer.path!=null && currPanzer.path.size()>0) {
	        			double deltaX=currPanzer.path.get(0)[0]-currPanzer.getX();
	        			double deltaY=currPanzer.path.get(0)[1]-currPanzer.getY();
	        			
	        			double angle=Math.atan2(deltaY, deltaX);
	        			Panzer ss=(Panzer) currPanzer;
	        			double centerX = currPanzer.getX() + 10;
	        	        double centerY = currPanzer.getY() - 10;
	        	        
	        	        g2d.rotate(angle, centerX,centerY);
        			}
        		}
        		Panzer curr1=(Panzer) myUnits.get(i);
        		g2d.drawImage(ImageIO.read(new File("panzer/"+myUnits.get(i).getName()+myUnits.get(i).getCommand()+".png")), (int) myUnits.get(i).getX(), (int) myUnits.get(i).getY(), curr1.getWidth(), curr1.getHeight(), null);
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
                        30 * enemyInfSide, 50,
                        null
                    );
                } else if (currUnit instanceof Panzer) {
                    Panzer currPanzer=(Panzer) currUnit;
                	g2d.drawImage(
                        ImageIO.read(new File("panzer/" + currUnit.getCommand() + currUnit.getName() + ".png")),
                        (int) currUnit.getX(),
                        (int) currUnit.getY(),
                        currPanzer.getWidth(), currPanzer.getHeight() * enemySide,
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
        g2d.setTransform(oldTransform); 
        selectedImage.setBounds(selectedX+viewX, selectedY+viewY, 1000, 150);
        menu.setBounds(menuX+viewX,menuY+viewY, 1700, 50);
        try {
			g.drawImage(ImageIO.read(new File("icons/bomb.png")), specX+viewX, specY+viewY, 50, 50,null);
			g.drawImage(ImageIO.read(new File("icons/parachute.png")), specX+viewX, specY+100+viewY, 50, 50,null);

		} catch (IOException e) {
			e.printStackTrace();
		}
        
        
        if(bombing) {
        	g.setColor(Color.RED);
        	g.fillOval(mouseX+viewX, mouseY+viewY, 20, 20);
        }
        if(parachuter) {
        	g.setColor(Color.GREEN);
        	g.fillOval(mouseX+viewX, mouseY+viewY, 20, 20);
        }
        if(perfrormPara) {
        	try {
				g.drawImage(ImageIO.read(new File("airplanes/parachuterAirplane.png")), (int)airplane.x, (int)airplane.y-500, 500, 500, null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	airplane.x+=50;
        }
        
        if(perfrormPara && airplane.x>=perfSpecX && airplane.y<=perfSpecY) {
        	parachuter=false;
        	if(parachuterY<=perfSpecY) {
	        	try {
					g.drawImage(ImageIO.read(new File("infantry/Heer/parachuter.png")), (int)parachuterX, (int)parachuterY, 50, 50, null);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	parachuterY+=10;
        	}else {
        		myUnits.push(new Infantry("Rifle", parachuterX,parachuterY, 20,500, 30,10, 5, 30, 25));
        		parachuterX=parachuterY=0;
        		perfrormPara=parachuter=false;
        	}
        }
        if(performBomb) {
        	
        	if(bombingList.size()>0) {
        		try {
        			int newX=(int)perfSpecX+rand.nextInt(300)-200;
        			int newY=(int)perfSpecY+rand.nextInt(300)-200;
					g.drawImage(ImageIO.read(new File("specEffects/bombing/explosion"+bombingList.get(0).counter+".png")), newX, newY, 300, 300, null
					);
					for(int i=0;i<enemyUnits.size();i++) {
						if(newX>=enemyUnits.get(i).getX() && newX<=enemyUnits.get(i).getX()+350 &&
								newY<=enemyUnits.get(i).getY() && newY>=enemyUnits.get(i).getY()-350	) {
							enemyUnits.get(i).setHealth(enemyUnits.get(i).getHealth()-200);
						}
					}
					playSound("audio/explosionSound.wav",0, 6);
					bombingList.get(0).counter+=1;
					if(bombingList.get(0).counter>=6) {
						bombingList.pop();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        	bombing=false;
        }else {
        	performBomb=false;
        }
        
    }
    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        
        



        
        selectedImage.setSelected(this.selected);
        isCrashedOnObstacle();
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
        //ai.simulation(1000);
        setNextCoordinates(myUnits);
        //setNextCoordinates(enemyUnits);
        for(int i=0;i<myUnits.size();i++) {
        	//avoidObst(myUnits.get(i));
        	//myUnits.get(i).updateMap(this.map);
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
        
        if (enemyUnits.size() <= 0) {
            JOptionPane.showMessageDialog(this, "Victory", "InfoBox: Message", JOptionPane.INFORMATION_MESSAGE);
            isRunning=false;
        } else if (myUnits.size() <= 0) {
            JOptionPane.showMessageDialog(this, "Lose", "InfoBox: Message", JOptionPane.INFORMATION_MESSAGE);
            isRunning=false;
        }
      
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    	int x=e.getX()+viewX;
    	int y=e.getY()+viewY;
    	
    	
    	
    	boolean foundTarget=false;
    	
    	if(selected.size()>0 && e.getButton()==MouseEvent.BUTTON1) {//SHOOT
	    	for(int i=0;i<enemyUnits.size();i++) {
	    		int width=0;
	    		if(enemyUnits.get(i) instanceof Panzer)
	    			width=150;
	    		else if(enemyUnits.get(i) instanceof Infantry)
	    			width=45;
	    		if(((x>enemyUnits.get(i).getX()-20 && x<enemyUnits.get(i).getX()+width &&
	    				y>enemyUnits.get(i).getY()-100 && y<enemyUnits.get(i).getY()+100))) {
	    			
	    			
	    			for(int j=0;j<selected.size();j++) {
	    				if(selected.get(j) instanceof Panzer && myUnits.contains(selected.get(j)))
	    					playSound("audio/Panzer/Srbija/panzerShotSrbija.wav", 0, 1);
	    				else if(selected.get(j) instanceof Infantry && myUnits.contains(selected.get(j)))
	    					playSound("audio/Infantry/srbija/shot.wav", 0, 1);
	    				selected.get(j).path=null;
		                selected.get(j).setState("shot");
		                selected.get(j).setTarget(enemyUnits.get(i));
		                selected.get(j).getTarget().setEnemy(selected.get(j));
	    			}
	    			foundTarget=true;
	    		}
	    	}
    	}
    	if(selected.size()>0 && !foundTarget && e.getButton()==MouseEvent.BUTTON1 && !bombing && !parachuter) {
    		boolean select=true;
    		for(int i=0;i<myUnits.size();i++) {
    			if((x>myUnits.get(i).getX()-20 && x<myUnits.get(i).getX()+100 &&
	    				y>myUnits.get(i).getY()-100 && y<myUnits.get(i).getY()+100)) {
    				select=false;
    			}
    		}
    		if(select) {
    			for(int i=0;i<selected.size();i++) {
    				selected.get(i).setTarget(null);
	                selected.get(i).setState("move");
	                selected.get(i).setCommand("Base");
	                selected.get(i).setNextX(x);
	                selected.get(i).setNextY(y);
	                selected.get(i).path=null;
	                if(selected.get(i) instanceof Panzer)
	                	playSound("audio/Panzer/Srbija/panzerMove.wav", 0,1 );
	                else if(selected.get(i) instanceof Infantry)
	                	playSound("audio/Infantry/srbija/move.wav", 0,1);
    			}
    		}
	    		
    	}
    	if(e.getButton()==MouseEvent.BUTTON1 && !bombing && !parachuter) {//SELECTING UNITS
	    	for(int i=0;i<myUnits.size();i++) {
	    		int width=0;
	    		if(myUnits.get(i) instanceof Panzer)
	    			width=150;
	    		else if(myUnits.get(i) instanceof Infantry)
	    			width=45;
	    		if(x>myUnits.get(i).getX() && x<myUnits.get(i).getX()+width &&
	    		   y>myUnits.get(i).getY()-10 && y<myUnits.get(i).getY()+100 && !selected.contains(myUnits.get(i))) {
	    			selected.add(myUnits.get(i));
	    			if(myUnits.get(i) instanceof Panzer)
	    				playSound("audio/Panzer/Srbija/panzerSelect1.wav", 0,1);
	    			else if(myUnits.get(i) instanceof Infantry)
	    				playSound("audio/Infantry/srbija/selected.wav", 0,1);
		    		}
	    	}
    	}
    	
    	if(e.getButton()==MouseEvent.BUTTON1) {
    		if(mouseX>=specX && mouseX<=specX+50 && mouseY>=specY && mouseY<=specY+50) {
    			System.out.println("Bombing selected");
    			bombing=true;
    		}
    		if(mouseX>=specX && mouseX<=specX+50 && mouseY>=specY+100 && mouseY<=specY+100+50) {
    			System.out.println("Parachuter selected");
    			parachuter=true;
    		}
    	}
    	
    	if(e.getButton()==MouseEvent.BUTTON1 && bombing && !(mouseX>=specX && mouseX<=specX+50 && mouseY>=specY && mouseY<=specY+50)) {
    		performBomb=true;
    		
        	perfSpecX=mouseX+viewX;
        	perfSpecY=mouseY+viewY;
    	}
    	
    	if(e.getButton()==MouseEvent.BUTTON1 && parachuter && !(mouseX>=specX && mouseX<=specX+50 && mouseY>=specY+100 && mouseY<=specY+100+50)) {
    		perfrormPara=true;
    		
        	perfSpecX=mouseX+viewX;
        	perfSpecY=mouseY+viewY;
        	airplane.y=perfSpecY;
        	parachuterX=perfSpecX;
        	parachuterY=perfSpecY-500;
        	playSound("specEffects/reinforcement.wav",0,2);
        	playSound("audio/airplaneSound.wav",0,2);
    	}
    	
    	if(e.getButton()==MouseEvent.BUTTON3) {//Unselect
    		selected=new LinkedList<>();
    		bombing=false;
    		parachuter=false;
    	}
    	
    }

    public void setNextCoordinates(LinkedList<Unit> units) {
    	for(int i=0;i<units.size()-1;i++) {
    		for(int j=i+1;j<units.size();j++) {
    			if(Math.abs(units.get(i).getNextX()-units.get(j).getNextX())<20) {
    				units.get(j).setNextX(units.get(j).getNextX()+110-Math.abs(units.get(i).getNextX()-units.get(j).getNextX()));
    			}
    			if(Math.abs(units.get(i).getNextY()-units.get(j).getNextY())<20) {
    				units.get(j).setNextY(units.get(j).getNextY()+110-Math.abs(units.get(i).getNextY()-units.get(j).getNextY()));
    			}
    		}
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
    
    public void makeDistance(Unit unit, LinkedList<Unit> yourUnit, int pointer) {
    	for(int i=0;i<yourUnit.size()-1;i++) {
    		if(i!=pointer && Math.abs(unit.getX()-yourUnit.get(i).getX())<100) {
    			unit.setX(yourUnit.get(i).getX()+100);
    		}
    	}
    }
    
    public void neightbourUnits(Unit unit) {
    	int range=100;
    	
    	
    }
    
    public int[][] importantMap(int map[][], int x, int y){
    	int [][] newMap=new int[y+1][x+1];
    	for(int i=0;i<newMap.length;i++) {
    		for(int j=0;j<newMap[0].length;j++) {
    			newMap[i][j]=map[i][j];
    		}
    	}
    	return newMap;
    }
    
    public void PanzerMove(Panzer panzer, List<Integer> moves) {
    	
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
    				playSound("audio/tankShot1.wav",0,3);
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
     				playSound("audio/Infantry/rifleShot1.wav",0,3);
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
    	    			playSound("audio/mortarShot.wav",0,1);
    			inf.setCommand("shot1");
    		}else if(inf.getFireRate()>10) {
    			inf.setCommand("shot2");
    		}else if(inf.getFireRate()>5){
    			if(inf.getFireRate()==10 && !inf.getName().equalsIgnoreCase("Mortar"))
    				playSound("audio/Infantry/rifleShot1.wav",0,3);
    			inf.setCommand("shot3");
    		}else {
    			inf.setFireRate(inf.reloading);
    		}
    		inf.setFireRate(inf.getFireRate()-1);
    	}
    }
    
    public void panzerAnimationMove(Panzer panzer) {
        panzer.setTarget(null);

        CompletableFuture.runAsync(() -> {
        	//panzer.makeArrayOfNextteps();
    		panzer.findShortestPath(compressedMap, panzer.width, panzer.height);

        }).thenRun(() -> {
            if (panzer.path.size() > 0) {
                int offsetX = 0;
                int offsetY = 0;
                
                panzer.setX(panzer.path.get(0)[0] + offsetX);
                panzer.setY(panzer.path.get(0)[1] + offsetY);

                for (int i = 0; i < 10; i++) {
                    if (panzer.path.size() > 0) {
                        panzer.path.remove(0);   
                    }
                }
                
            } else {
                panzer.setX(panzer.getNextX());
                panzer.setY(panzer.getNextY());
                panzer.setState("stop");
            }
        });
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
        inf.findShortestPath(compressedMap, 100, 100);
        if(inf.path!=null && inf.path.size()>0) {
        	inf.setX(inf.path.get(0)[0]);
        	inf.setY(inf.path.get(0)[1]);
	        //followMap((Unit)panzer, panzer.path.get(0));
        	for(int i=0;i<10;i++)
        		if(inf.path.size()>0)
        			inf.path.remove(0);
	        
        }else {
        	inf.setX(inf.getNextX());
        	inf.setY(inf.getNextY());
        	inf.setState("stop");
        }
        
        
    	if(infMove<5) {
    		inf.setCommand("move1");
    	}else if(infMove<10) {
    		inf.setCommand("move2");
    	}else {
    		infMove=-1;
    	}
    	infMove++;
    }


    public void avoidObst(Unit unit) {
    	for(int i=0;i<obs.length;i++) {
    		if(unit.getX()+200>=obs[i].x && unit.getX()+200<=obs[i].x+obs[i].width &&
    				unit.getY()-100>=obs[i].y-obs[i].height && unit.getY()-100<=obs[i].y) {
    			if(unit.getNextX()<unit.getX()) {
    				if(unit.getNextY()>unit.getY()) {
    					unit.setY(unit.getX()-100);System.out.println("WAFFEN");
    				}else {
    					unit.setY(unit.getX()+100);System.out.println("WAFFEN SS");
    				}
    			}else {
					if(unit.getNextY()>unit.getY()) {
						unit.setY(unit.getY()-100);System.out.println("SS");
    				}else {
    					unit.setY(unit.getY()+100);System.out.println("SAS");
    				}
    			}
    		}
    			
    	}
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
     public static int[][] compressMap(int[][] largeMap, int newSize) {
    	    int originalSize = largeMap.length;
    	    int blockSize = originalSize / newSize;
    	    
    	    int[][] compressedMap = new int[newSize][newSize];
    	    
    	    for (int i = 0; i < newSize; i++) {
    	        for (int j = 0; j < newSize; j++) {
    	            compressedMap[i][j] = aggregateBlock(largeMap, i * blockSize, j * blockSize, blockSize);
    	        }
    	    }
    	    return compressedMap;
    	}

    	private static int aggregateBlock(int[][] map, int startX, int startY, int blockSize) {
    	    int endX = startX + blockSize;
    	    int endY = startY + blockSize;
    	    boolean hasObstacle = false;

    	    for (int x = startX; x < endX; x++) {
    	        for (int y = startY; y < endY; y++) {
    	            if (map[x][y] == 1) {
    	                hasObstacle = true;
    	                break;
    	            }
    	        }
    	        if (hasObstacle) break;
    	    }

    	    return hasObstacle ? 1 : 0; // Ako postoji prepreka, cijeli blok je prepreka
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
		
	}
	
	
	@Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        

        repaint();
    }
	
	
    class AI{
    	LinkedList<Unit> aiUnit;
    	LinkedList<Unit> enemyUnit;
    	Random rand=new Random();
    	LinkedList<Double> reward;
    	int infMove=0;
    	public AI(LinkedList<Unit> aiUnit, LinkedList<Unit> enemyUnit) {
    		this.aiUnit=aiUnit;
    		this.enemyUnit=enemyUnit;
    		this.reward=new LinkedList<>();
    		for(int i=0;i<aiUnit.size();i++) {
    			reward.add(0.0);
    		}
    	}//Decission based on the health, distance of an enemy and of the their player
    	
    	public void evaluateMove(Unit unit, int pos) {
    		for(int i=0;i<this.enemyUnit.size();i++) {
    			double deltaX = enemyUnit.get(i).getX() - unit.getX();
                double deltaY = enemyUnit.get(i).getY() - unit.getY();
                int distance = (int)Math.sqrt(deltaX * deltaX + deltaY * deltaY);
                if(distance<10) {
                	if((unit.getHealth()<50 && unit instanceof Infantry) || (unit.getHealth()<100 && unit instanceof Panzer)) {
                        reward.set(pos, reward.get(pos)+rand.nextDouble(100));
                	}else {
                		reward.set(pos, reward.get(pos)-rand.nextDouble(101));
                	}
                }else if(distance<50) {
                	if((unit.getHealth()<10 && unit instanceof Infantry) || (unit.getHealth()<100 && unit instanceof Panzer)) {
                        reward.set(pos, reward.get(pos)+rand.nextDouble(60));
                	}else {
                		reward.set(pos, reward.get(pos)-rand.nextDouble(61));
                	}
                }else if(distance<300){
                	if((unit.getHealth()<10 && unit instanceof Infantry) || (unit.getHealth()<100 && unit instanceof Panzer)) {
                        reward.set(pos, reward.get(pos)+rand.nextDouble(20));
                	}else {
                		reward.set(pos, reward.get(pos)-rand.nextDouble(60));
                	}
                }else {
                	if((unit.getHealth()<10 && unit instanceof Infantry) || (unit.getHealth()<100 && unit instanceof Panzer)) {
                		reward.set(pos, 0.0);
                	}else {
                		reward.set(pos, reward.get(pos)+rand.nextDouble(100));
                	}
                }
    		}
    	}
    	
    	
    	
    	public void simulation(int numOfSimulations) {
    		for(int i=0;i<numOfSimulations;i++) {
    			for(int j=0;j<aiUnit.size();j++) {
    				evaluateMove(aiUnit.get(j), j);
    			}
    		}
    		for(int i=0;i<aiUnit.size();i++) {
    			if(reward.get(i)>60000) {//go forward
    				if(!aiUnit.get(i).getState().equalsIgnoreCase("shot")) {
    					for(int j=0;j<enemyUnit.size();j++)
    						moveEnemy(aiUnit.get(i), enemyUnit.get(j));
    				}
    			}else {
    				
    			}
    		}	
    	}
    	
    	
    	public void moveEnemy(Unit ai, Unit target) {
        	double x=target.getX();
        	double y=target.getY();
        	
        	if(checkShotingRange(ai, target) && ai.getTarget()==null) {
        		ai.setTarget(target);
        		ai.setState("shot");
        	}else {
        		ai.setNextX(target.getX());
        		ai.setNextY(target.getY());
        		if(ai instanceof Infantry) {
        			infantryMove((Infantry)ai);
        		}else if(ai instanceof Panzer) {
        			panzerMove((Panzer)ai);
        		}
        	}	
        }
    	
    	public void infantryMove(Infantry inf) {
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
            inf.setX(inf.getX()+directionX*5);
        	inf.setY(inf.getY()+directionY*5);
            
            
        	if(infMove<5) {
        		inf.setCommand("move1");
        	}else if(infMove<10) {
        		inf.setCommand("move2");
        	}else {
        		infMove=-1;
        	}
        	infMove++;
        }
    	
    	public void panzerMove(Panzer panzer) {
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
            panzer.setX(panzer.getX()+directionX*5);
            panzer.setY(panzer.getY()+directionY*5);
        }
    	
    }
	class selectedUnits extends JPanel implements ActionListener{
		public LinkedList<Unit> selected;
		int x=100;
		public selectedUnits() {
	        setLayout(null);
	        selected=new LinkedList<>();
	        this.setBackground(Color.GRAY);
	        this.setPreferredSize(new Dimension(1000, 100));       
	    }
		
		public void setSelected(LinkedList<Unit> selected) {
			this.selected=selected;
		}
		
		@Override
	    public void paintComponent(Graphics g) {
			super.paintComponent(g);
			for(int i=0;i<4;i++) {
				try {
					g.drawImage(ImageIO.read(new File("icons/shield.png")),x+i*200, 20, 180, 130,null);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			for(int i=0;i<this.selected.size();i++) {
				try {
					if(selected.get(i) instanceof Panzer) {
						g.drawImage(ImageIO.read(new File("panzer/"+selected.get(i).getName()+"Base.png")),x+i*200+50, 40, 100, 80, null);
					}else if(selected.get(i) instanceof Infantry) {
						g.drawImage(ImageIO.read(new File("infantry/Heer/Base"+selected.get(i).getName()+".png")),x+i*200+60, 30, 50, 80, null);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


			}
			
		}
		
		@Override
	    public void actionPerformed(ActionEvent e) {
	        repaint();
	        
		}
	
		
	}
	class MenuBar extends JPanel{
		public MenuBar() {
			this.setVisible(true);
			this.setSize(1000,150);
			this.setBackground(Color.GRAY);
			initialization();
		}
		
		public void initialization() {
			JButton btn1=new JButton("Exit");
			
			btn1.addActionListener(e -> parentFrame.show(mainPanel, "MainMenu"));
			this.add(btn1);
		}
	}
}




