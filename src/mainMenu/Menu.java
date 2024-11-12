package mainMenu;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Menu {

	private JFrame frame;
    private JLabel txt;
    
    private JLabel txts[];

    private int count;
    private int level;

    private boolean log;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Menu window = new Menu();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    public Menu() {
    	 initialize();
    }
	
    
    private void initialize() {
    	JButton missions=new JButton("Missions");
    	missions.setBounds(1500,20,275,50);
    	
        JButton cont=new JButton("Continue");
        cont.setBounds(1500,80,275,50);
        
        JButton load=new JButton("Load Game");
        load.setBounds(1500,140,275,50);
        
        JButton campaine=new JButton("Campaign");
        campaine.setBounds(1500,200,275,50);
        
        JButton exit=new JButton("Campaign");
        exit.setBounds(1500,800,275,50);
        
    	
        frame = new JFrame();
        frame.setBounds(100, 100, 2000, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        BackgroundPanel backgroundPanel = new BackgroundPanel("backgrounds/background.jpg"); // Proverite da li je putanja ispravna
        backgroundPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        frame.getContentPane().add(backgroundPanel);
        backgroundPanel.setLayout(null);
        
        
        backgroundPanel.add(missions);
        backgroundPanel.add(cont);
        backgroundPanel.add(load);
        backgroundPanel.add(campaine);
        backgroundPanel.add(exit);
    }
    
    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String imagePath) {
            try {
                backgroundImage = new ImageIcon(imagePath).getImage();
                if (backgroundImage == null) {
                    System.err.println("Slika nije pronađena: " + imagePath);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
            
            
            
            
            
        }
    }


}
