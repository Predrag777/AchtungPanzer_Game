package mainMenu;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.Crtaj;
import main.Field;

public class Menu {

    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel;

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
        frame = new JFrame();
        frame.setBounds(100, 100, 2000, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        frame.getContentPane().add(mainPanel);
        
        BackgroundPanel mainMenuPanel = new BackgroundPanel("backgrounds/background.jpg");
        mainMenuPanel.setLayout(null);
        addMainMenuButtons(mainMenuPanel);
        mainPanel.add(mainMenuPanel, "MainMenu");
        
        MissionsPanel missionsPanel = new MissionsPanel("backgrounds/background1.jpg");
        mainPanel.add(missionsPanel, "Missions");
    }

    private void addMainMenuButtons(JPanel mainMenuPanel) {
        JButton missionsButton = new JButton("Missions");
        missionsButton.setBounds(1500, 20, 275, 50);
        missionsButton.addActionListener(e -> cardLayout.show(mainPanel, "Missions"));
        
        JButton cont = new JButton("Continue");
        cont.setBounds(1500, 80, 275, 50);

        JButton load = new JButton("Load Game");
        load.setBounds(1500, 140, 275, 50);

        JButton campaign = new JButton("Campaign");
        campaign.setBounds(1500, 200, 275, 50);

        JButton exit = new JButton("Exit");
        exit.setBounds(1500, 800, 275, 50);
        exit.addActionListener(e -> System.exit(0));

        mainMenuPanel.add(missionsButton);
        mainMenuPanel.add(cont);
        mainMenuPanel.add(load);
        mainMenuPanel.add(campaign);
        mainMenuPanel.add(exit);
    }

    class MissionsPanel extends JPanel {
        private Image backgroundImage;

        public MissionsPanel(String imagePath) {
            try {
                backgroundImage = new ImageIcon(imagePath).getImage();
            } catch (Exception e) {
                e.printStackTrace();
            }
            setLayout(null);

            JLabel missionsLabel = new JLabel("Welcome to the Missions");
            missionsLabel.setBounds(100, 50, 500, 40);
            missionsLabel.setForeground(Color.WHITE);
            add(missionsLabel);

            JButton backButton = new JButton("Back to Menu");
            backButton.setBounds(100, 800, 200, 50);
            backButton.addActionListener(e -> cardLayout.show(mainPanel, "MainMenu"));
            add(backButton);
            
            JButton mission1 = new JButton("Mission 1");
            mission1.setBounds(100, 150, 200, 50);
            mission1.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					 Crtaj c;
					try {
						c = new Crtaj();
						JFrame frame = new JFrame();

				        frame.setSize(1000, 1000);
				        frame.setVisible(true);
				        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				        frame.add(c);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				        
				}
			});
            add(mission1);
            
            JButton mission2 = new JButton("Mission 2");
            mission2.setBounds(100, 220, 200, 50);
            add(mission2);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String imagePath) {
            try {
                backgroundImage = new ImageIcon(imagePath).getImage();
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
