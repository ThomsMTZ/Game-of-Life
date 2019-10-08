
import java.util.Random;

public class Neutre extends Personne {

	int nombre_personnes; 
	public Neutre(Case pos_depart, Grille plateau) {
		super(pos_depart, plateau);
		Random rand = new Random();
		nombre_personnes=rand.nextInt(20);
		statut=1;
	}
	
	
	@Override
	public void transform(){ //transformation si il y a 2 manifestant ou plus autour de la personne neutre
	 
		if(nb_manif>=2){
			plateau.set_case(pos.get_x(),pos.get_y(),null);
			plateau.set_case(pos.get_x(),pos.get_y(),new Manifestant(pos,plateau));

		}
		
	}
	
	@Override
	public void deplacement(){// on lance un dès entre 1 et 4 (pour les 4 directions) si un deplacement est impossible on ne bouge pas 
		Random r = new Random();
		switch (r.nextInt(4)){
		case 0:
			if(pos.get_x()+1< plateau.getNb_colonnes() && (plateau.get(pos.get_x()+1, pos.get_y()).get_statut()== 0)){
				plateau.set_case(pos.get_x(), pos.get_y(), null);
				plateau.set_case(pos.get_x()+1, pos.get_y(), new Neutre(plateau.get(pos.get_x()+1, pos.get_y()),plateau));				
			}
			break;
		case 1:
			if((pos.get_x()-1>= 0)&&(plateau.get(pos.get_x()-1, pos.get_y()).get_statut()== 0)){
				plateau.set_case(pos.get_x(), pos.get_y(), null);
				plateau.set_case(pos.get_x()-1, pos.get_y(), new Neutre(plateau.get(pos.get_x()-1, pos.get_y()),plateau));				
			}			
			break;
		case 2:
			if((pos.get_y()+1< plateau.getNb_lignes()) && (plateau.get(pos.get_x(), pos.get_y()+1).get_statut()== 0)){
				plateau.set_case(pos.get_x(), pos.get_y(), null);
				plateau.set_case(pos.get_x(), pos.get_y()+1, new Neutre(plateau.get(pos.get_x(), pos.get_y()+1),plateau));				
			}
			break;
		case 3:
			if((pos.get_y()-1>= 0)&&(plateau.get(pos.get_x(), pos.get_y()-1).get_statut()== 0)){
				plateau.set_case(pos.get_x(), pos.get_y(), null);
				plateau.set_case(pos.get_x(), pos.get_y()-1, new Neutre(plateau.get(pos.get_x(), pos.get_y()-1),plateau));
			}
			break;		
		}
		
	}
public void analyse(){ // methode de comptage des entités situé autour de la personne ici on s'interesse au manifestant 
		
		nb_manif=0;
		int X = pos.get_x();
		int Y = pos.get_y();
		
		for(int i=X-1;i<X+2;i++){
			for(int j=Y-1; j <Y+2;j++){
				
				try{
					if((plateau.get(i, j).get_statut()==2)&&(plateau.get(i, j) != pos)){							
						this.nb_manif++;	
					}
						
				}
				catch(IndexOutOfBoundsException e){
					
				}
			}
		}
}
	

}
