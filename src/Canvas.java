import java.awt.Color;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
public class Canvas extends JPanel implements Observer{
	private Grille plateau;
	private JButton[][] tab_button;

	
	
	public Canvas( Grille plateau ){

		tab_button=new JButton[plateau.getNb_lignes()][plateau.getNb_colonnes()];
		this.plateau=plateau; 
		this.setLayout(new GridLayout(plateau.getNb_lignes(),plateau.getNb_colonnes()));
		for(int i= 0; i<plateau.getNb_lignes();i++){
			for (int j=0; j<plateau.getNb_colonnes();j++){
				JButton button = new JButton(plateau.get(i, j).toString());
				button.addMouseListener(new MyButtonListener(i,j));
				button.setBackground(Color.WHITE);
				tab_button[i][j]=button;
				this.add(button);
			}
		}
		
		
	}
	
	
	@Override
	public void update(Observable o, Object arg) { // on change la couleur de la case ( arg est une case ) en fonction de son statut 
		
	
		Case c = (Case)arg;
			switch (c.get_statut()){
			case 0:
				tab_button[c.get_x()][c.get_y()].setBackground(Color.WHITE);
				break;
			case 1:
				tab_button[c.get_x()][c.get_y()].setBackground(Color.GRAY);
				break;
			case 2:
				tab_button[c.get_x()][c.get_y()].setBackground(Color.YELLOW);
				break;
			case 3:
				tab_button[c.get_x()][c.get_y()].setBackground(Color.BLUE);
				break;
				
			
			}
	}
	


	public class MyButtonListener extends MouseAdapter {
		private int x;
		private int y; 
		
		public MyButtonListener(int x, int y ){
			this.x=x;
			this.y=y;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if( e.getButton()==MouseEvent.BUTTON1){ // pendant la phase de placement le clik gauche place un manifestant 
				if(AffichageInfo.nb_manif<AffichageInfo.nb_manif_placed_max){
					plateau.set_case(x, y, new Manifestant(plateau.get(x, y),plateau));
				}
				else{//si on s'apprete a depasser le nombre de manifestant maximum un message d'erreur s'affice 
					JOptionPane.showMessageDialog(null, "Vous avez placez tous vos Manifestant \n Clic droite pour supprimer un manifestant", "Information", JOptionPane.ERROR_MESSAGE);
				}
			
			}
			if( e.getButton()==MouseEvent.BUTTON3){//le clique droit le supprime
				
				if(plateau.get(x,y).get_statut()==2){
					plateau.set_case(x, y, null);
				}
			}
		}

		
	}
}
	

