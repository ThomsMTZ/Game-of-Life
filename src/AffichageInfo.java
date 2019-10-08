import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;

public class AffichageInfo extends JLabel implements Observer{ // voir faire un JPan avec 3 Jlabel 
	
	
	public static int  nb_manif;
	public static int nb_neutre;
	public  static int nb_crs;
	public  static int nb_tour;
	public static int nb_manif_placed_max=10;
	
	public AffichageInfo( ){
		reset();
		this.setPreferredSize(new Dimension(50,50));	
	}
	 public void reset(){ // REMISE à 0 des compteurs 
		 nb_manif=0;
		 nb_neutre=0;
		 nb_crs=0;
		 nb_tour=0;
	 }
	public void tour_suivant(){
		nb_tour++;

	}
	
	@Override
	public void update(Observable arg0, Object arg1) {//affichage des infos mis à jour a chaque changement de statut 
		this.setText("nb_manif à placer : "+ nb_manif_placed_max + "   nb_manif :  "+nb_manif +"     nb_neutre: " + nb_neutre + "       nb_CRS : " +nb_crs + "        nb_tour:  " + nb_tour);
		
	}
	
	
}
