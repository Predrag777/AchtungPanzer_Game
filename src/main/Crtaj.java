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
import javax.swing.JComponent;
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
    String cursorImage="icons/gauntler.png";
    
    double angle = 0;
    int enemySide=1;
    public boolean isRunning=true;
    int menuX=0,menuY=0;
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
    
    boolean log=false;
    boolean logNotAvailable=false;
    
    
    int pointerX, pointerY;
    
    String radioSound=null;
    AI ai;
    Mission m;
    Random rand = new Random();
    HashMap<Panzer, Panzer> targets=new HashMap<>();
    Obstacles[] obs=new Obstacles[30];
    BufferedImage background;
    List<Node> path;
    //BufferedImage images[];
    String brokens[]= {"panzer/broken1.png","panzer/broken2.png","panzer/broken3.png","panzer/broken2.png","panzer/broken3.png", "panzer/broken1.png","panzer/broken2.png","panzer/broken3.png","panzer/broken2.png","panzer/broken3.png"};
    public Crtaj(CardLayout parentFrame, JPanel mainPanel, Mission m) throws IOException {
        t.start();
        
        this.parentFrame = parentFrame;
        this.mainPanel=mainPanel;
        this.airplane=new Airplane("parachuterAirplane.png");
        selectedImage=new selectedUnits();
        this.m=m;
        this.myUnits=m.getMyUnits();
        this.enemyUnits=m.getEnemyUnits();
        this.obs=m.getObs();
        
        
        bombingList.push(new Explosion("Artilery",  1));
        bombingList.push(new Explosion("Artilery",  1));
        bombingList.push(new Explosion("Artilery",  1));
        bombingList.push(new Explosion("Artilery",  1));
        
        try {
            background = ImageIO.read(new File(m.getBackground()));
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
        
    }
 
    int mouseX=0;
    int mouseY=0;
    

    

    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Hide cursor
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
     	this.setCursor(blankCursor);
        
        
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
        		if((myUnits.get(i) instanceof Machinegun || myUnits.get(i).getName().equalsIgnoreCase("Machinegun")) && myUnits.get(i).getTarget()!=null){
    				Machinegun mg=(Machinegun) myUnits.get(i);
        			
        			g2d.drawImage(ImageIO.read(new File("specEffects/rifleHit.png")), mg.hitX, mg.hitY, 45,45,null);        			
        		}
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
        			int coordinations[]=myUnits.get(i).damagePanzer();
        			if(myUnits.get(i).getFireRate()==10) {
        				g2d.drawImage(ImageIO.read(new File("specEffects/rifleHit.png")), coordinations[0], coordinations[1], 45,45,null);
        			}
        		}else if(myUnits.get(i).getTarget()!=null && myUnits.get(i) instanceof Panzer) {
        			int coordinations[]=myUnits.get(i).damagePanzer();
        			if(myUnits.get(i).getFireRate()==10) {
        				g2d.drawImage(ImageIO.read(new File("specEffects/panzerHit.png")), coordinations[0], coordinations[1], 100,100,null);
        			}
        		} 
        	}
        	
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        for(int i=0;i<myUnits.size();i++) {
        	
        	if((myUnits.get(i) instanceof Machinegun || myUnits.get(i).getName().equalsIgnoreCase("Machinegun"))){
    			
    			Machinegun mg=(Machinegun) myUnits.get(i);
    			try {
					g2d.drawImage(ImageIO.read(new File(mg.image)), (int)mg.getX(), (int)mg.getY(), 50, 50, null);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
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

                    int[] coordinations = currUnit.damagePanzer();
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
                    int[] coordinations = currUnit.damagePanzer();
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
        menu.setBounds(menuX+viewX,menuY+viewY, 2000, 50);
        menu.btn1.setBounds(1700, 0, 100, 50);
        menu.btn2.setBounds(1590, 0, 100, 50);
        menu.btn3.setBounds(1480, 0, 100, 50);
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
        if(!bombing && !parachuter) {
			try {
				g.drawImage(ImageIO.read(new File(cursorImage)), mouseX+viewX, mouseY+viewY, 40, 40,null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        }
        if(perfrormPara) {
        	try {
				g.drawImage(ImageIO.read(new File("airplanes/parachuterAirplane.png")), (int)airplane.x, (int)airplane.y-500, 500, 500, null);
			} catch (IOException e) {
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
        if(m.type.equalsIgnoreCase("defend"))
        	ai.simulation(1000);
        //setNextCoordinates(myUnits);
        for(int i=0;i<myUnits.size();i++) {
        	Unit newTarget=myUnits.get(i).autoShot(enemyUnits);
    		if(newTarget!=null && myUnits.get(i).getTarget()==null && !myUnits.get(i).getState().equalsIgnoreCase("move")) {
    			myUnits.get(i).setTarget(newTarget);
    			myUnits.get(i).getTarget().setEnemy(myUnits.get(i));
    			myUnits.get(i).setState("shot");
    		}

    		
        	if(myUnits.get(i).getTarget()!=null && myUnits.get(i).getState().equalsIgnoreCase("shot")) {
        		
        		
        		if(!myUnits.get(i).checkShotingRange(myUnits.get(i).getTarget())) {
        			//////SOUND FOR MISS THE RANGE
        			myUnits.get(i).setState("stop");
        			myUnits.get(i).setCommand("Base");
        		}else {
	        		if(myUnits.get(i) instanceof Panzer) {
	        			Panzer panzer=(Panzer) myUnits.get(i);
	        			panzer.panzerAnimationShot();
	        		}
	        		else if(myUnits.get(i) instanceof Infantry) {
	        			if(myUnits.get(i).getName().contains("Machine")) {
	        				Infantry inf=(Infantry) myUnits.get(i);
	        				inf.machineAnimationShot();
	        			}else {
	        				Infantry inf=(Infantry) myUnits.get(i);
	        				inf.infantryAnimationShot();
	        			}
	        		}else if(myUnits.get(i) instanceof Machinegun) {
	        			Machinegun mg=(Machinegun) myUnits.get(i);
	        			mg.machinegunShotAnimation(mg.getTarget());
	        			
	        		}
	        		if(!enemyUnits.contains(myUnits.get(i).getTarget())) {
	        			myUnits.get(i).setTarget(null);
	        			myUnits.get(i).setState("stop");
	        			myUnits.get(i).setCommand("Base");
	        		}
        		}
        	}else if(myUnits.get(i).getState().equalsIgnoreCase("move")){
        		if(myUnits.get(i) instanceof Infantry) {
        			Infantry inf=(Infantry) myUnits.get(i);
        			inf.infantryAnimationMove(compressedMap);
        		}
        		else if(myUnits.get(i) instanceof Panzer) {
        			Panzer panzer=(Panzer)myUnits.get(i);
        			panzer.panzerAnimationMove(compressedMap);
        		}
        	}
        	
        	if(myUnits.get(i).getHealth()<=0) {//DEATH
        		myUnits.remove(myUnits.get(i));
        	}
        }
        
        for(int i=0;i<enemyUnits.size();i++) {
        	Unit newTarget=enemyUnits.get(i).autoShot(myUnits);
        	
        	if(newTarget!=null && enemyUnits.get(i).getTarget()==null && !enemyUnits.get(i).getState().equalsIgnoreCase("move")) {
        		enemyUnits.get(i).setTarget(newTarget);
        		enemyUnits.get(i).getTarget().setEnemy(enemyUnits.get(i));
        		enemyUnits.get(i).setState("shot");
    		}
        	if(enemyUnits.get(i).getTarget()!=null && !enemyUnits.get(i).checkShotingRange(enemyUnits.get(i).getTarget())) {
        		enemyUnits.get(i).setTarget(null);
        		enemyUnits.get(i).setState("stop");
        		enemyUnits.get(i).setCommand("Base");
        	}
        	
        	if(enemyUnits.get(i).getState().equalsIgnoreCase("shot") && enemyUnits.get(i).getTarget()!=null) {
        		if(enemyUnits.get(i) instanceof Panzer) {
        			Panzer panzer=(Panzer) enemyUnits.get(i);
        			panzer.panzerAnimationShot();
        		}
        		else if(enemyUnits.get(i) instanceof Infantry) {
        			Infantry inf=(Infantry) enemyUnits.get(i);
    				inf.infantryAnimationShot();
        		}
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
        
        if (enemyUnits.size() <= 0 && isRunning==true) {
            JOptionPane.showMessageDialog(this, "Victory", "InfoBox: Message", JOptionPane.INFORMATION_MESSAGE);
            parentFrame.show(mainPanel, "Mission1");
			isRunning=false;
			System.out.println(isRunning);
			System.gc();
			System.gc();
        } else if (myUnits.size() <= 0 && isRunning==true) {
            JOptionPane.showMessageDialog(this, "Lose", "InfoBox: Message", JOptionPane.INFORMATION_MESSAGE);
            parentFrame.show(mainPanel, "Mission1");
			isRunning=false;
			System.out.println(isRunning);
			System.gc();
			System.gc();
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
	    			width=50;
	    		if(((x>enemyUnits.get(i).getX() && x<enemyUnits.get(i).getX()+width &&
	    				y>enemyUnits.get(i).getY() && y<enemyUnits.get(i).getY()+50))) {
	    			
	    			
	    			for(int j=0;j<selected.size();j++) {
	    				if(selected.get(j) instanceof Panzer && myUnits.contains(selected.get(j)))
	    					playSound("audio/Panzer/Srbija/panzerShotSrbija.wav", 0, 1);
	    				else if(selected.get(j) instanceof Infantry && myUnits.contains(selected.get(j)))
	    					playSound("audio/Infantry/srbija/shot.wav", 0, 1);
	    				selected.get(j).path=null;
		                selected.get(j).setState("shot");
		                selected.get(j).setTarget(enemyUnits.get(i));
		                selected.get(j).getTarget().setEnemy(selected.get(j));
		                if(selected.get(j) instanceof Machinegun && selected.get(j).getTarget()!=null) {
		                    ((Machinegun) selected.get(j)).logFire = true;
		                	
		                }
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
    		if(select && !logNotAvailable) {
    			for(int i=0;i<selected.size();i++) {
    				selected.get(i).setTarget(null);
	                selected.get(i).setState("move");
	                selected.get(i).setCommand("Base");
	                selected.get(i).setNextX(x);
	                selected.get(i).setNextY(y);
	            	selected.get(i).makeArrayOfNextteps2(400);

	                selected.get(i).path=null;
	                if(selected.get(i) instanceof Panzer)
	                	playSound("audio/Panzer/Srbija/panzerMove.wav", 0,1 );
	                else if(selected.get(i) instanceof Infantry)
	                	playSound("audio/Infantry/srbija/move.wav", 0,1);
    			}
    		}else if(logNotAvailable) {
    			playSound("audio/germanAutoShot.wav", 0,1);
    		}
	    		
    	}
    	if(e.getButton()==MouseEvent.BUTTON1 && !bombing && !parachuter) {//SELECTING UNITS
	    	for(int i=0;i<myUnits.size();i++) {
	    		int width=0;
	    		if(myUnits.get(i) instanceof Panzer)
	    			width=150;
	    		else if(myUnits.get(i) instanceof Infantry || myUnits.get(i) instanceof Machinegun)
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
    
     
     
    
    
    public void moveEnemy(Unit enemyUnit) {
    	Unit target=enemyUnit.getEnemy();
    	double x=target.getX();
    	double y=target.getY();
    	
    	if(enemyUnit.checkShotingRange(target) && enemyUnit.getTarget()==null) {
    		enemyUnit.setTarget(target);
    		enemyUnit.setState("shot");
    	}else {
    		enemyUnit.setNextX(target.getX());
    		enemyUnit.setNextY(target.getY());
    		if(enemyUnit instanceof Infantry) {
    			Infantry inf=(Infantry)enemyUnit;
    			inf.infantryAnimationMove(compressedMap);
    		}else if(enemyUnit instanceof Panzer) {
    			Panzer panzer=(Panzer) enemyUnit;
    			panzer.panzerAnimationMove(compressedMap);
    		}		
    	}	
    }



    public void attackEnemy(Unit unit) {
    	if(unit.getTarget()!=null) {
    		Unit saveUnit=unit.getTarget();
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
		
	}
	
	
	@Override
    public void mouseMoved(MouseEvent e) {
		log=false;
		logNotAvailable=false;
        mouseX = e.getX();
        mouseY = e.getY();
        
        for(int i=0;i<enemyUnits.size();i++) {
        	int width=0;
    		if(enemyUnits.get(i) instanceof Panzer)
    			width=100;
    		else if(enemyUnits.get(i) instanceof Infantry)
    			width=30;
    		if(((mouseX+viewX>enemyUnits.get(i).getX() && mouseX+viewX<enemyUnits.get(i).getX()+width &&
    				mouseY+viewY>enemyUnits.get(i).getY() && mouseY+viewY<enemyUnits.get(i).getY()+50))) {
    			log=true;
    		}
        }
        
        for(int i=0;i<obs.length;i++) {
        	int width=0;
    		
    		if(((mouseX+viewX>obs[i].x && mouseX+viewX<obs[i].x+obs[i].width &&
    				mouseY+viewY<obs[i].y+obs[i].height && mouseY+viewY>obs[i].y))) {
    			logNotAvailable=true;
    		}
        }
        if(!logNotAvailable) {
	        if(log) {
				cursorImage="icons/aimPointer.png";
	        }else {
				cursorImage="icons/gauntler.png";
	
	        }
        }else {
        	cursorImage="icons/notAvailable.png";
        }
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
        	
        	if(ai.checkShotingRange(target) && ai.getTarget()==null) {
        		ai.setTarget(target);
        		ai.setState("shot");
        	}else {
            	//ai.findShortestPath(compressedMap, ai.stepByStep.get(0)[0],ai.stepByStep.get(0)[1], 100, 100);

        		ai.setNextX(target.getX());
        		ai.setNextY(target.getY());
        		ai.makeArrayOfNextteps2(300);
        		int width=50;
        		int height=50;
        		if(ai instanceof Infantry) {
        			infantryMove((Infantry)ai);
        		}else if(ai instanceof Panzer) {
        			Panzer ss=(Panzer) ai;
        			panzerMove((Panzer)ai);
        			width=ss.getWidth();
        			height=ss.getHeight();
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
					}else if(selected.get(i) instanceof Machinegun) {
						g.drawImage(ImageIO.read(new File("Machinegun/machinegun1.png")),x+i*200+60, 30, 50, 80, null);
					}
				} catch (IOException e) {
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
		JButton btn1=new JButton("Exit");
		JButton btn2=new JButton("Option");
		JButton btn3=new JButton("Example");
		public MenuBar() {
			this.setVisible(true);
			this.setSize(1000,150);
			this.setBackground(Color.GRAY);
			initialization();
		}
		
		public void initialization() {
			
			
			btn1.addActionListener(e -> {
				parentFrame.show(mainPanel, "Mission1");
				isRunning=false;
				System.out.println(isRunning);
				System.gc();
				
			});
			
			this.add(btn2);
			this.add(btn1);
			this.add(btn3);
		}
	}
}




