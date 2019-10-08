import java.util.Observer;

public class Case {

	private Personne person; 
	private int x;
	private int y; 
	private int statut; // 0 si vide 1 si neutre et 2 si manif 
	
	public Case( int x, int y ){
		person= null;
		statut=0;
		this.x=x;
		this.y=y; 
		
	}
	public Personne get_person(){// renvoie la personne presente sur la case 
		return person;
	}
	public void set(Personne p){ // modifie le statut de la case en fonction de la personne presente dessus 
		
		if(p!=null){
			p.goTo(this);
			this.statut=p.get_statut();
		}
		else{
			this.statut=0;
		}
		person=p;
		
	}
	public int get_statut(){
		return this.statut;
	}
	public void set_x(int x){
		this.x=x;
	}
	public void set_y(int y){
		this.y=y;
	}
	
	public int get_x(){
		return x;
	}
	
	public int get_y(){
		return y; 
	}
}
