import java.util.Random;

public abstract class Personne {

	protected Case pos;
	protected int nb_manif;
	protected int nb_crs;
	protected int statut;//  0 vide 1 neutre 2 manif 3 crs
	protected Grille plateau ;

	
	public Personne(Case case_depart, Grille plateau){
		
		this.plateau=plateau;
		nb_manif=0;
		nb_crs=0;
		pos=case_depart;
		
	}
	public void goTo(Case pos){
		this.pos=pos;
	}
	public void transform(){
	}
	
	public void deplacement(){
	}
	
	public int get_statut(){
		return statut;
	}
	public void analyse(){ // methode de comptage des entités situé autour de la personne
	}
}
