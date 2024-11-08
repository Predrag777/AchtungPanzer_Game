package main;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

class Crtaj extends JPanel implements MouseListener, ActionListener{
	int numberOfEnemyPanzers;
	Panzer myPanzers[];
	Panzer enemyPanzers[];
	Timer t=new Timer(100,this);
	boolean selected=false;
	double x=100;
	double y=100;
	
	public Crtaj() {
		t.start();
		
		this.myPanzers=new Panzer[1];
		this.enemyPanzers=new Panzer[1];
		
		this.myPanzers[0]=new Panzer("Tiger Panzer", x,y,500,100,50,10);
		
		setSize(1000,1000);
		setVisible(true);
		addMouseListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
	}
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		BufferedImage panzer;
		try {
			panzer=ImageIO.read(new File("panzer/base.png"));
			//System.out.println(myPanzers[0].getX()+"    "+myPanzers[0].getY());
			g.drawImage(panzer, (int)x,(int)y,200,100,null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		repaint();
		if(myPanzers[0].getState().equalsIgnoreCase("move")) {
			double targetX=myPanzers[0].getNextX();
			double targetY=myPanzers[0].getNextY();
			
			double deltaX=targetX-x;
			double deltaY=targetY-y;

			
			double distance =Math.sqrt(deltaX * deltaX + deltaY * deltaY);
		
			if(distance<myPanzers[0].getSpeed()) {
				x=targetX;
				y=targetY;
				myPanzers[0].setState("stop");
				System.out.println("Kraj");
				return;
			}
			double directionX=(deltaX/distance);
			double directionY=(deltaY/distance);
			/*myPanzers[0].setX(myPanzers[0].getX()+directionX*myPanzers[0].getSpeed());
			myPanzers[0].setX(myPanzers[0].getY()+directionY*myPanzers[0].getSpeed());*/
			
			x+=directionX*myPanzers[0].getSpeed();
			y+=directionY*myPanzers[0].getSpeed();
			System.out.println(x+"   "+y);

		}
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(!selected &&
				e.getX()<myPanzers[0].getX()+200 && e.getX()>myPanzers[0].getX()-50 && 
				e.getY()<myPanzers[0].getY()+100 && e.getY()>myPanzers[0].getY()) {
			System.out.println("Selected "+myPanzers[0]);
			selected=true;
		}else {
			myPanzers[0].setState("move");
			myPanzers[0].setNextX(e.getX());
			myPanzers[0].setNextY(e.getY());
			System.out.println("Start moving");
		}
		
		
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}


public class Field {
	public static void main(String[] args) {
		Crtaj c=new Crtaj();
		JFrame frame=new JFrame();
		
		frame.setSize(1000,1000);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(c);
	}
}
