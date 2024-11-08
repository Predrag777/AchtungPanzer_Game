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
    Panzer myPanzers[];
    Panzer enemyPanzers[];
    Timer t = new Timer(100, this);
    boolean selected = false;
    double x = 100, y = 100;
    int counterForFire = 0;
    double angle = 0;
    double a = 500, b = 800;

    int obsX[]=new int[5];
    int obsY[]=new int[5];
    Random rand = new Random();

    Obstacles[] obs=new Obstacles[5];
    BufferedImage background;
    BufferedImage images[];
    String brokens[]= {"panzer/broken1.png","panzer/broken2.png","panzer/broken3.png","panzer/broken2.png","panzer/broken3.png"};
    public Crtaj() throws IOException {
        t.start();
        this.myPanzers = new Panzer[1];
        this.enemyPanzers = new Panzer[1];

        this.myPanzers[0] = new Panzer("Tiger Panzer", x, y, 500, 100, 50, 10);
        this.enemyPanzers[0] = new Panzer("Sherman", a, b, 500, 100, 50, 10);
        
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

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
        }
        for(int i=0;i<obsX.length;i++) {
        	System.out.println(i);
        	g.drawImage(images[i], obs[i].x, obs[i].y, 100, 200, null);
        }
        
        
        BufferedImage panzer, enemy;
        
        
        
        
        Graphics2D g2d = (Graphics2D) g;

        AffineTransform oldTransform = g2d.getTransform();

        double centerX = x + 100;
        double centerY = y + 50;

        g2d.rotate(angle, centerX, centerY);

        try {
            if (!myPanzers[0].getState().equalsIgnoreCase("shot"))
                panzer = ImageIO.read(new File("panzer/panzerBase.png"));
            else {
                if (counterForFire < 15) {
                    panzer = ImageIO.read(new File("panzer/panzerBase.png"));
                } else if (counterForFire < 20) {
                    panzer = ImageIO.read(new File("panzer/panzerShot.png"));
                } else {
                    myPanzers[0].setState("move");
                    counterForFire = -1;
                    panzer = ImageIO.read(new File("panzer/panzerBase.png"));
                }
                counterForFire++;
            }

            g2d.drawImage(panzer, (int) x, (int) y, 200, 100, null);
            g2d.setTransform(oldTransform);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            enemy = ImageIO.read(new File("panzer/enemyBase.png"));
            g.drawImage(enemy, (int) a, (int) b, 200, 100, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
       
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();

        if (myPanzers[0].getState().equalsIgnoreCase("shot")) {
            double deltaX = a - x;
            double deltaY = b - y;
            angle = Math.atan2(deltaY, deltaX); 
            return; 
        }

        double targetX = myPanzers[0].getNextX();
        double targetY = myPanzers[0].getNextY();

        double deltaX = targetX - x;
        double deltaY = targetY - y;

        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        if (distance < myPanzers[0].getSpeed()) {
            x = targetX;
            y = targetY;
            myPanzers[0].setState("stop");
            selected = false;
            return;
        }

        double directionX = deltaX / distance;
        double directionY = deltaY / distance;
        angle = Math.atan2(directionY, directionX);

        x += directionX * myPanzers[0].getSpeed();
        y += directionY * myPanzers[0].getSpeed();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getX() >= a && e.getX() <= a + 100) {
            playSound("audio/panzerFire.wav", 0);
            myPanzers[0].setState("shot");
        } else {
            if (!selected && e.getX() < myPanzers[0].getX() + 200 && e.getX() > myPanzers[0].getX() - 50 &&
                e.getY() < myPanzers[0].getY() + 100 && e.getY() > myPanzers[0].getY()) {
                playSound("audio/panzerSound.wav", 0);
                selected = true;
            } else {
                playSound("audio/panzerSelect1.wav", 0);
                selected = false;
                myPanzers[0].setState("move");
                myPanzers[0].setNextX(e.getX());
                myPanzers[0].setNextY(e.getY());
            }
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
