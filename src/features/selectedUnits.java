package features;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import main.Infantry;
import main.Panzer;
import main.Unit;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

class selectedUnits extends JPanel{
	public LinkedList<Unit> selected;
	int x=300;
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
		
		for(int i=0;i<this.selected.size();i++) {
			if(this.selected.get(i) instanceof Panzer)
				g.setColor(Color.RED);
			else if(this.selected.get(i) instanceof Infantry)
				g.setColor(Color.YELLOW);

			//g.fillRect(x+i*100, 20, 50, 50);
			try {
				g.drawImage(ImageIO.read(new File("panzer/panzerIVBase.png")),x+i*100, 20, 50, 50, null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}
	}
}
