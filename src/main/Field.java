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
    LinkedList<Panzer> myPanzers=new LinkedList<>();
    LinkedList<Infantry> infantry=new LinkedList<>();
    
    
    LinkedList<Panzer> enemyPanzers=new LinkedList<>();
    
    LinkedList<Panzer> selected=new LinkedList<>();
    LinkedList<Infantry> infSelected=new LinkedList<>();
    Timer t = new Timer(100, this);
    double x = 100, y = 100;
    double x1 = 400, y1 = 100;
    int counterForFire = 0;
    double angle = 0;
    double a = 500, b = 800;
    int enemySide=1;
    int infMove=0;
    
    int viewX=0,viewY=0;
    int mapSpeed=20;
    int borders=40;
    
    Random rand = new Random();
    HashMap<Panzer, Panzer> targets=new HashMap<>();
    Obstacles[] obs=new Obstacles[15];
    BufferedImage background;
    BufferedImage images[];
    String brokens[]= {"panzer/broken1.png","panzer/broken2.png","panzer/broken3.png","panzer/broken2.png","panzer/broken3.png", "panzer/broken1.png","panzer/broken2.png","panzer/broken3.png","panzer/broken2.png","panzer/broken3.png"};
    public Crtaj() throws IOException {
        t.start();
        this.myPanzers.push(new Panzer("tiger", x, y, 500, 650, 50, 10));
        this.infantry.push(new Infantry("infantry/Heer/stand.png", 50,50, 20,25));
        this.myPanzers.push(new Panzer("panzerIV", x1, y1, 500, 650, 50, 10));
        
        this.enemyPanzers.push(new Panzer("Sherman", a, b, 500, 700, 50, 10));

        obs[0]=new Obstacles("SS",300, 400, false);
        obs[1]=new Obstacles("SS",350, 500, false);
        obs[2]=new Obstacles("SS",600, 50, false);
        obs[3]=new Obstacles("SS",50, 600, false);
        obs[4]=new Obstacles("SS",800, 700, false);
        
        obs[5]=new Obstacles("SS",1000, 400, false);
        obs[6]=new Obstacles("SS",1500, 1500, false);
        obs[7]=new Obstacles("SS",1600, 150, false);
        obs[8]=new Obstacles("SS",1550, 1500, false);
        obs[9]=new Obstacles("SS",1650, 1000, false);
        
        obs[10]=new Obstacles("SS",2500, 1400, false);
        obs[11]=new Obstacles("SS",2850, 2500, false);
        obs[12]=new Obstacles("SS",1600, 850, false);
        obs[13]=new Obstacles("SS",2500, 500, false);
        obs[14]=new Obstacles("SS",2500, 2000, false);
        BufferedImage[] obstacles= {ImageIO.read(new File("panzer/broken2.png")),
					        		ImageIO.read(new File("panzer/broken1.png")),
					        		ImageIO.read(new File("panzer/broken3.png")),
					        		ImageIO.read(new File("panzer/broken1.png")),
					        		ImageIO.read(new File("panzer/broken3.png")),
					        		ImageIO.read(new File("panzer/broken2.png")),
					        		ImageIO.read(new File("panzer/broken1.png")),
					        		ImageIO.read(new File("panzer/broken3.png")),
					        		ImageIO.read(new File("panzer/broken1.png")),
					        		ImageIO.read(new File("panzer/broken3.png")),
					        		
					        		ImageIO.read(new File("panzer/buildings/house1.png")),
					        		ImageIO.read(new File("panzer/buildings/house2.png")),
					        		ImageIO.read(new File("panzer/buildings/house3.png")),
					        		ImageIO.read(new File("panzer/buildings/house4.png")),
					        		ImageIO.read(new File("panzer/buildings/house5.png"))};
        images=obstacles;
        
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
        	g.drawImage(images[i], obs[i].x, obs[i].y, 100, 200, null);
        }
        
        
        BufferedImage panzer, enemy;
        
        String enemyImage="panzer/shermanBase.png";
        
        
        
        
        AffineTransform oldTransform = g2d.getTransform();
        //infantry.get(0).setState("shot");
        try {
        	for(int i=0;i<infantry.size();i++) {
        		try {
        			System.out.println(infantry.get(i).getState());
        			if(infantry.get(i).getState().equalsIgnoreCase("shot"))
        				infantryAnimationShot(infantry.get(i));
        			/*if(infantry.get(i).getState().equalsIgnoreCase("move"))
        				infantryAnimationMove(infantry.get(i));*/
    				g.drawImage(ImageIO.read(new File(infantry.get(i).getName())), (int)infantry.get(i).getX(), (int)infantry.get(i).getY(), 50, 70, null);
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
        		
        	}
        	
        	String command="Base";
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
        	        
        	        if(counterForFire<35) {
        	        	command="Base";
        	        }else if(counterForFire<40) {
        	        	if(counterForFire==35) {
        	        		playSound("audio/tankShot1.wav", 0);
        	        	}
        	        	command="Shot";
        	        }else {
        	        	command="Base";
        	        	counterForFire=-1;
        	        }
        	        
        	        counterForFire++;
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
        		if(!myPanzers.get(i).getState().equalsIgnoreCase("shot"))
        			command="Base";
        		g2d.drawImage(ImageIO.read(new File("panzer/"+myPanzers.get(i).getName()+command+".png")), (int) myPanzers.get(i).getX(), (int) myPanzers.get(i).getY(), 200, 100, null);
        		g2d.setTransform(oldTransform);
        	}
        	
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
        for(int i=0;i<infantry.size();i++) {
        	infantryAnimationMove(infantry.get(i));
        }

        
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
                	playSound("audio/panzerCrashOnObst.wav",0);
                }else {
                	myPanzers.get(i).setX(currPanzer.getX()+directionX*currPanzer.getSpeed());
                	myPanzers.get(i).setY(currPanzer.getY()+directionY*currPanzer.getSpeed());
                	
                }
    	        
    	        
    	        
        	}
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    	int x=e.getX()+viewX;
    	int y=e.getY()+viewY;
    	//Infantry commands
    	/*if(e.getButton()==MouseEvent.BUTTON1) {
    		if(infSelected.size()>0) {
    			for(int i=0;i<infSelected.size();i++) {
    				
    				infSelected.get(i).setNextX(x);
    				infSelected.get(i).setNextY(y);
    				playSound("audio/Infantry/heer/move.wav",0);
    			}
    		}*/
    		for(int i=0;i<infantry.size();i++) {
    			if(x>infantry.get(i).getX() && x<infantry.get(i).getX()+100 &&
	    		   y>infantry.get(i).getY()-10 && y<infantry.get(i).getY()+100) {
    				playSound("audio/Infantry/heer/selected.wav",0);
    				
    				infSelected.push(infantry.get(i));
    			}else if(infSelected.size()>0) {
    				infSelected.get(i).setNextX(x);
    				infSelected.get(i).setNextY(y);
    				infantry.get(i).setState("move");
    				playSound("audio/Infantry/heer/move.wav",0);
    		}
    		
    		
    	}
    	
    	
    	//Panzer commands
    	if(selected.size()>0 && e.getButton()==MouseEvent.BUTTON1) {//SHOOT
	    	for(int i=0;i<enemyPanzers.size();i++) {
	    		if(x>enemyPanzers.get(i).getX() && x<enemyPanzers.get(i).getX()+40 &&
	    				y>enemyPanzers.get(i).getY()-10 && y<enemyPanzers.get(i).getY()+100) {
	    			playSound("audio/panzerFire.wav", 0);
	    			for(int j=0;j<selected.size();j++) {
		                selected.get(j).setState("shot");
		                selected.get(j).setTarget(enemyPanzers.get(i));
	    			}
	    		}else {
	    			for(int j=0;j<selected.size();j++) {
		                selected.get(j).setState("move");
		                selected.get(j).setNextX(x);
		                selected.get(j).setNextY(y);
	    			}
	    			playSound("audio/panzerSelect1.wav", 0);
	    		}
	    	}
    	}
    	if(e.getButton()==MouseEvent.BUTTON1) {//SELECTING UNITS
	    	for(int i=0;i<myPanzers.size();i++) {
	    		if(x>myPanzers.get(i).getX() && x<myPanzers.get(i).getX()+100 &&
	    		   y>myPanzers.get(i).getY()-10 && y<myPanzers.get(i).getY()+100) {
	    			selected.push(myPanzers.get(i));
	    			playSound("audio/panzerSound.wav", 0);
	    		}
	    	}
    	}
    	
    	if(e.getButton()==MouseEvent.BUTTON3) {//Unselect
    		selected=new LinkedList<>();
    		infSelected=new LinkedList<>();
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
    		for(int j=0;j<myPanzers.size();j++) {
    			Panzer curr=myPanzers.get(j);
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
    
    public void infantryAnimationShot(Infantry inf) {
    	
    	if(inf.getState().equalsIgnoreCase("shot")) {
    		if(inf.getRiflemanFireRate()>20) {
    			inf.setName("infantry/Heer/shot1.png");
    		}else if(inf.getRiflemanFireRate()>10) {
    			inf.setName("infantry/Heer/shot2.png");
    		}else if(inf.getRiflemanFireRate()>5){
    			if(inf.getRiflemanFireRate()==10)
    				playSound("audio/Infantry/rifleShot1.wav",0);
    			inf.setName("infantry/Heer/shot3.png");
    		}else {
    			inf.setRiflemanFireRate(26);
    		}
    		inf.setRiflemanFireRate(inf.getRiflemanFireRate()-1);
    	}
    	if(inf.getState().equalsIgnoreCase("move")) {
    		
    	}
    }
    
    public void infantryAnimationMove(Infantry inf) {
    	double deltaX=inf.getNextX()-inf.getX();
    	double deltaY=inf.getNextY()-inf.getY();
    	
    	double distance=Math.sqrt(deltaX*deltaX+deltaY*deltaY);
    	
    	double directionX = deltaX / distance;
        double directionY = deltaY / distance;
        angle = Math.atan2(directionY, directionX);
        
        if (distance < myPanzers.get(0).getSpeed()) {
        	inf.setX(inf.getNextX());
        	inf.setY(inf.getNextY());
            inf.setState("stop");
            return;
        }
        inf.setX(inf.getX()+directionX*2);
    	inf.setY(inf.getY()+directionY*2);
        
        
    	if(infMove<5) {
    		inf.setName("infantry/Heer/move1.png");
    	}else if(infMove<10) {
    		inf.setName("infantry/Heer/move2.png");
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
        int mouseX = e.getX();
        int mouseY = e.getY();
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
