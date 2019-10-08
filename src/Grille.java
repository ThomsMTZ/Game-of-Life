import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Grille extends Observable{


	private int nb_case; 
	private int nb_lignes=20;
	private int nb_colonnes=20;
	public Case[][] tableau ;
	private ArrayList<Observer> tab_observer;
	private ArrayList<Personne> tab_manif;
	private ArrayList<Personne> tab_neutre;
	private ArrayList<Personne> tab_crs;

	
	public Grille(){
		tab_manif = new  ArrayList<Personne>() ;
		tab_neutre= new ArrayList<Personne>() ;
		tab_crs =new ArrayList<Personne>() ;
	
		
		tab_observer = new ArrayList<Observer>();
		this.nb_case=nb_lignes*nb_colonnes; 
		tableau= new Case[nb_lignes][nb_colonnes];
		for(int i = 0 ; i <nb_lignes ; i++){
			for (int j=0;j<nb_colonnes;j++){
				tableau[i][j]= new Case(i,j);
			}
		}
		
	}
	public int getNb_lignes(){//accesseur nb_lignes
		return nb_lignes;
	}
	public int getNb_colonnes(){//accesseur nb_colonnes
		return nb_colonnes;
	}
	
	
	public void addObserver(Observer o){ // ajout de l'observer 
		tab_observer.add(o);
	}
	
	
	public Case get(int i, int j ){ //recuperation de la case i j 
		return tableau[i][j];
	}
	
	
	public void set_case(int i, int j,Personne p){ // modification du statut de chaque case : on transforme la case i j en p 
		Personne p_old=tableau[i][j].get_person();
		
		if(p==null){ // si on supprime une personne 

			switch(tableau[i][j].get_statut()){
			case 1:
				tab_neutre.remove(p_old); // on supprime l'entité de la collection 
				AffichageInfo.nb_neutre--;//on diminue le compteur adequat 
				
				break;
			case 2:
				tab_manif.remove(p_old); // on supprime l'entité de la collection 
				AffichageInfo.nb_manif--;//on diminue le compteur adequat 
				break;
			case 3:
				tab_crs.remove(p_old); // on supprime l'entité de la collection 
				AffichageInfo.nb_crs--;//on diminue le compteur adequat 
				break;
				
			}
			tableau[i][j].set(null);//on modifie le statut de la case en vide 
			
		}
		else{ // si on transforme la case en une entité 
			if(tableau[i][j].get_statut()==0){//si la case est bien vide
				
				switch(p.statut){ 
				case 1:
					tab_neutre.add(p);//on ajoute l'entité a la collection 
					AffichageInfo.nb_neutre++;// on incremente le compteur adequat 
					break;
				case 2:
					tab_manif.add(p);//on ajoute l'entité a la collection 
					AffichageInfo.nb_manif++;// on incremente le compteur adequat 
					break;
				case 3:
					tab_crs.add(p);//on ajoute l'entité a la collection 
					AffichageInfo.nb_crs++;// on incremente le compteur adequat 
					break;
				}
				tableau[i][j].set(p); // modification de la case 
			}
		}
		
		notifier(tableau[i][j]); // notification vers les observers 
		
	}
	
	public void analyse(){ // on analyse toutes les cases du tableaux 
		for(Personne p :tab_neutre){
			p.analyse();
		}
		for(Personne p :tab_manif){
			p.analyse();
		}
		for(Personne p :tab_crs){
			p.analyse();
		}
	}
	/* on transforme  toutes les cases du tableaux qui doivent l'être 
	 ici on doit parcourir le tableau en partant par la fin car on modifie les entités presentent dans le tableau que l'on parcourt on evite ainsi
	 la ConcurrentModificationException  */
	public void transform(){
		for (int i =tab_neutre.size() - 1 ; i >= 0 ; i--) {
			tab_neutre.get(i).transform();
		}
		for (int i =tab_manif.size() - 1 ; i >= 0 ; i--) {
			tab_manif.get(i).transform();
		}
		for (int i =tab_crs.size() - 1 ; i >= 0 ; i--) {
			tab_crs.get(i).transform();
		}
	}
	
	public void deplacement(){ // deplacement des entités. De même que pour la fonction précedente on parcourt le tableau à l'envers 
		for (int i =tab_neutre.size() - 1 ; i >= 0 ; i--) {
			tab_neutre.get(i).deplacement();
		}
	
		for (int i =tab_crs.size() - 1 ; i >= 0 ; i--) {
			tab_crs.get(i).deplacement();
		}
	}
	
	public void notifier(Case c){
		
		 for(int i=0;i<tab_observer.size();i++)
         {
                 Observer o = tab_observer.get(i);
                 o.update(this, c);
         }
	}
	
}
