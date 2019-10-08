import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Panel extends JLabel{ // panel d'affichage d'information 
	
	private Canvas c; 
	
	public Panel ( Canvas c ){
		this.setPreferredSize(new Dimension(50,200));
		this.c=c;
		
		
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		
		
		
	}

}
