

public class Manifestant extends Personne {

	
	//taille du groupe de manifestant
	public Manifestant(Case pos_depart, Grille plateau) {
		super(pos_depart, plateau);
		statut=2;
		
	}
	@Override
	public void transform(){
		if(nb_crs>=2){
			plateau.set_case(pos.get_x(), pos.get_y(),null);
		}
	}
	@Override
	public void deplacement(){//pas de déplacement de manifestant 
		
	}
	
	public void analyse(){ // methode de comptage des entités situé autour de la personne ici on s'interesse au CRS 
		
		nb_crs=0;

		int X = pos.get_x();
		int Y = pos.get_y();
		
		for(int i=X-1;i<X+2;i++){
			for(int j=Y-1; j <Y+2;j++){
				
				try{
					if((plateau.get(i, j).get_statut()==3)&&(plateau.get(i, j) != pos)){
						this.nb_crs++;	
					}
						
				}
				catch(IndexOutOfBoundsException e){}
			}
		}
	}
}
