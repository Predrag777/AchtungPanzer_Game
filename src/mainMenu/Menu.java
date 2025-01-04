package mainMenu;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.Crtaj;
import Missions.*;
public class Menu {
	private boolean start=false;
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
        
        Mission1SS mission1Panel = new Mission1SS("backgrounds/missionbackg1.jpg");
        mainPanel.add(mission1Panel, "Mission1");
        
        Mission2SS mission2Panel = new Mission2SS("backgrounds/missionbackg1.jpg");
        mainPanel.add(mission2Panel, "Mission2");
        
        
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
           initialize();
        }

        public void initialize() {
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
            mission1.addActionListener(e -> cardLayout.show(mainPanel, "Mission1"));
            add(mission1);
            
            JButton mission2 = new JButton("Mission 2");
            mission2.setBounds(100, 220, 200, 50);
            mission2.addActionListener(e -> cardLayout.show(mainPanel, "Mission2"));
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

    class Mission1SS extends MissionsPanel {
    	Crtaj crtajPanel;
        public Mission1SS(String imagePath) {
            super(imagePath);
        }
        
        protected void drawWrappedText(Graphics g, String text, int x, int y, int wrapWidth) {
    	    FontMetrics metrics = g.getFontMetrics();
    	    int lineHeight = metrics.getHeight();

    	    String[] words = text.split(" ");
    	    StringBuilder line = new StringBuilder();

    	    for (String word : words) {
    	        String testLine = line + word + " ";
    	        int lineWidth = metrics.stringWidth(testLine);

    	        if (lineWidth > wrapWidth) {
    	            g.drawString(line.toString(), x, y);
    	            line = new StringBuilder(word + " ");
    	            y += lineHeight;
    	        } else {
    	            line.append(word).append(" ");
    	        }
    	    }

    	    if (!line.isEmpty()) {
    	        g.drawString(line.toString(), x, y);
    	    }
    	}
        
        @Override
    	protected void paintComponent(Graphics g) {
    	    super.paintComponent(g);
    	    if(crtajPanel!=null) {
    	    	for (int i = 0; i < mainPanel.getComponentCount(); i++) {
    	            if (mainPanel.getComponent(i) instanceof Crtaj) {
    	                System.out.println("Obrisano");
    	                cardLayout.removeLayoutComponent(mainPanel.getComponent(i));
    	                crtajPanel=null;
    	                System.gc();
    	                
    	                break;
    	            }
    	        }
            }
    	    try {
    	        Image backgroundImage = ImageIO.read(new File("backgrounds/JSO.jpg"));
    	        g.drawImage(backgroundImage, 600, 100, 700, 400, null);

    	        g.setFont(new Font("Serif", Font.BOLD, 15));
    	        g.setColor(Color.WHITE);

    	        String text = "In the wake of the tragic assassination of King Alexander I Karađorđević in Marseille, the Kingdom of Yugoslavia is plunged into chaos. "
    	                + "The perpetrators, connected to both the Ustaša movement and elements within the Communist Party of Yugoslavia (KPJ), have unveiled a dangerous alliance fueled by a shared goal: "
    	                + "the destabilization and eventual dismantling of the kingdom.\n"
    	                + "\n"
    	                + "As evidence surfaces of clandestine meetings between communist operatives and Ustaša militants, fears grow that this unlikely coalition could spark widespread insurrection. "
    	                + "Documents recovered from a safehouse in Zagreb reveal a joint operation code-named Crimson Dawn, a plot to incite uprisings across key cities while eliminating high-ranking officials loyal to the crown.\n"
    	                + "\n"
    	                + "The intelligence has led to the formation of a top-secret unit, the Special Operations Detachment (Jedinica za Specijalne Operacije - JSO). Their mission is clear: dismantle the Crimson Dawn network and bring its architects to justice before their plans plunge the nation into anarchy.";

    	        drawWrappedText(g, text, 600, 550, 800); 
    	    } catch (IOException e) {
    	        e.printStackTrace();
    	    }
    	}


        @Override
        public void initialize() {
            JButton contin = new JButton("Continue");
            contin.setBounds(800, 800, 200, 50);
            contin.addActionListener(e -> cardLayout.show(mainPanel, "Crtaj"));
            add(contin);

            JButton backButton = new JButton("Back to Menu");
            backButton.setBounds(100, 800, 200, 50);
            contin.addActionListener(e -> {
                try {
                	Mission m=new Mission1("panzer/background.jpg");
                    crtajPanel = new Crtaj(cardLayout, mainPanel,m);
                    mainPanel.add(crtajPanel, "Crtaj");
                    cardLayout.show(mainPanel, "Crtaj");
                    if(!crtajPanel.isRunning) {
                    	System.out.println("SS");
                    }
                    System.out.println(crtajPanel.isRunning);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            backButton.addActionListener(e -> cardLayout.show(mainPanel, "MainMenu"));
            add(backButton);
        }
        
       
    }
    
    class Mission2SS extends MissionsPanel {
    	Crtaj crtajPanel;

        public Mission2SS(String imagePath) {
            super(imagePath);
        }
        
        protected void drawWrappedText(Graphics g, String text, int x, int y, int wrapWidth) {
    	    FontMetrics metrics = g.getFontMetrics();
    	    int lineHeight = metrics.getHeight();

    	    String[] words = text.split(" ");
    	    StringBuilder line = new StringBuilder();

    	    for (String word : words) {
    	        String testLine = line + word + " ";
    	        int lineWidth = metrics.stringWidth(testLine);

    	        if (lineWidth > wrapWidth) {
    	            g.drawString(line.toString(), x, y);
    	            line = new StringBuilder(word + " ");
    	            y += lineHeight;
    	        } else {
    	            line.append(word).append(" ");
    	        }
    	    }

    	    if (!line.isEmpty()) {
    	        g.drawString(line.toString(), x, y);
    	    }
    	}

        @Override
        public void initialize() {
            JButton contin = new JButton("Continue");
            contin.setBounds(800, 800, 200, 50);
            contin.addActionListener(e -> cardLayout.show(mainPanel, "Crtaj"));
            add(contin);

            JButton backButton = new JButton("Back to Menu");
            backButton.setBounds(100, 800, 200, 50);
            backButton.addActionListener(e -> cardLayout.show(mainPanel, "MainMenu"));
            contin.addActionListener(e -> {
                try {
                	Mission m=new Mission2("panzer/background.jpg");
                	crtajPanel = new Crtaj(cardLayout, mainPanel,m);
                    mainPanel.add(crtajPanel, "Crtaj");
                    cardLayout.show(mainPanel, "Crtaj");
                    if(!crtajPanel.isRunning) {
                    	System.out.println("SS");
                    }
                    System.out.println(crtajPanel.isRunning);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            add(backButton);
            
        }
        
        @Override
    	protected void paintComponent(Graphics g) {
    	    super.paintComponent(g);

    	    try {
    	        Image backgroundImage = ImageIO.read(new File("backgrounds/background6.jpg"));
    	        g.drawImage(backgroundImage, 600, 100, 700, 400, null);

    	        g.setFont(new Font("Serif", Font.BOLD, 15));
    	        g.setColor(Color.WHITE);

    	        String text = "On April 6, 1941, the German war machine roared to life against the Kingdom of Yugoslavia. The skies above Belgrade were filled with fire and smoke as Luftwaffe bombers unleashed devastation in a surprise attack, leaving the \n capital in ruins and the nation reeling. This brutal assault, known as Operation Punishment, marked the beginning of Germany’s campaign to crush Yugoslav resistance and secure its Balkan flank.\n"
    	        		+ "\n"
    	        		+ "Despite the destruction, the Royal Yugoslav Air Force (RYAF) has not been silenced. Fighters and bombers stationed across the kingdom are regrouping for a daring counterattack on German airfields in Austria and Bulgaria. The success \n of this operation could disrupt the Luftwaffe’s ability to sustain its offensive and buy precious time for the kingdom’s defenders.\n"
    	        		+ "\n"
    	        		+ "However, German intelligence is aware of these plans and is preparing a coordinated strike to neutralize key Yugoslav airbases before the bombers can take off. The airfields at Zemun and Kraljevo, critical to the counteroffensive, are now priority targets.";

    	        drawWrappedText(g, text, 600, 550, 800); 
    	    } catch (IOException e) {
    	        e.printStackTrace();
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
