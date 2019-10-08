import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Menu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;


public class Fenetre extends JFrame {
	
	private JMenuBar menu;
	private JMenu menu_go;
	private JMenu menu_aide;
	private JMenuItem kefa;
	private JMenuItem contact;
	private JMenu auto;
	private Grille grille;
	private Canvas c;
	private AffichageInfo infos;
	private JMenu next_turn;
	private boolean jeu ;
	private Boolean crs;
	private Boolean auto_test;
	
	public Fenetre(){

		/*Initialisation des données de jeu*/
		this.crs= false ;
		this.jeu=false;
		this.auto_test=false; 
		
		/*Initialisation des entités de la fênetre*/		
		this.grille= new Grille();
		this.c=new Canvas(grille);
		menu= new JMenuBar();
		menu_go= new JMenu("Lancer");
		menu_aide= new JMenu("aide");
		next_turn= new JMenu("Tour suivant");
		auto = new JMenu ("Jeu Automatique ");
		kefa = new JMenuItem("Que faire ?");
		contact = new JMenuItem("Nous contacter");
		infos = new AffichageInfo();
		/*Ajout des Observers : Canvas et Affichage Infos observent grille*/
		grille.addObserver(c);
		grille.addObserver(infos);
		
		
		
		
		
		/*COnfiguration des boutons du menu */
		menu_go.setPreferredSize(new Dimension(100,70));
		menu.setBackground(Color.GRAY);
		menu_go.setBackground(Color.GREEN);
		menu_go.setOpaque(true);
		menu.setPreferredSize(new Dimension(5000, 50)); 
		/*Ajout des boutons au menu*/
		menu_aide.add(kefa);
		menu_aide.add(contact);
		menu.add(menu_go);
		menu.add(next_turn);
		menu.add(auto);
		menu.add(menu_aide);
		
		this.setJMenuBar(menu);
		/*Désactivation des boutons utilisable uniquement en phase 2 */
		next_turn.setEnabled(false);
		auto.setEnabled(false);
		/*Ecoute des boutons du menu */
		auto.addMouseListener(new MyListenerAuto());
		next_turn.addMouseListener(new MyListenerTurn());
		menu_go.addMouseListener(new MyListenerGo());
		kefa.addActionListener(new MyListenerAide());
		contact.addActionListener(new MyListenerAide());
		
		/*Configuration de la fênetre*/
		this.setSize(500,700);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.add(c, BorderLayout.CENTER);
		this.add(infos,BorderLayout.SOUTH);
		this.setVisible(true);
		/*Initialisation du plateau*/
		placement_depart_neutre();
		/*Message d'information à propos des actions à éffectuer par le joueur*/
		JOptionPane.showMessageDialog(this, "Placez vos manifestant avec le clique droit(clique gauche pour effacer) \nCliquez sur Lancer pour lancer la similation", "Information", JOptionPane.INFORMATION_MESSAGE);
		
	}
	public void boucleDeJeu(){
		/*Tirage du dés pour l'apparition de neutre ou de CRS */
		Random rand= new Random(); 
		int dice = rand.nextInt(100);
		if(dice < 40)apparition_person(true); 
		if(crs &&( dice> 60)) apparition_person(false);
			
		/*Analyse de chaque case pour compter les diffèrentes entités présente autour (crs et manifestants)*/
		grille.analyse();
		/*Transformation des differentes entités */
		grille.transform();
		/*Deplacement des CRS et des neutres  */
		grille.deplacement();
		/*incrementation de la variable tour  */
		infos.tour_suivant();					
		/*A partir d'un certain nombre de manifestant des crs apparaissent */		
		if((AffichageInfo.nb_manif>20) || (AffichageInfo.nb_tour>50)){
			crs=true; 
			lancer_crs();
		}
		
		/*Test des conditions de fin de jeu */
		if(AffichageInfo.nb_manif>(grille.getNb_colonnes()*grille.getNb_colonnes()/10)){
			jeu=false;
			JOptionPane.showMessageDialog(null, "Le gouvernement cède à toute les revandications, le peuple a vaincu \n VICTOIRE", "Information", JOptionPane.ERROR_MESSAGE);
			System.exit(0);	
		}
			
		if(AffichageInfo.nb_manif<AffichageInfo.nb_manif_placed_max){
			jeu=false;
			JOptionPane.showMessageDialog(null, "Les CRS on contenu la manifestation, aucunes mesures ne sera prise par le gouvernement \n DEFAITE", "Information", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
	/*Methode de d'apparition àleatoire d'une entité*/
	public void apparition_person(Boolean statut){ 
		Random rand = new Random();
		int dice_ligne = rand.nextInt(grille.getNb_lignes());
		int dice_colonnes= rand.nextInt(grille.getNb_colonnes());
		Case c = grille.get(dice_ligne, dice_colonnes);
		if(c.get_statut()==0){
			if(statut){
				grille.set_case(dice_ligne, dice_colonnes, null);
				grille.set_case(dice_ligne, dice_colonnes, new Neutre(c, grille));
			}
			else{
				grille.set_case(dice_ligne, dice_colonnes, null);
				grille.set_case(dice_ligne, dice_colonnes, new CRS(c, grille));	
			}
		}
	}
	/*Arrivée des CRS*/
	public void lancer_crs () { 
		while(AffichageInfo.nb_crs<grille.getNb_colonnes()*grille.getNb_lignes()/30){
			apparition_person(false);
		}
	}
	
	/*Placement des neutres présent au debut du jeu */
	public void placement_depart_neutre(){
		for(int i=0; i < grille.getNb_lignes(); i++){
			for(int j=0; j< grille.getNb_colonnes(); j++){
				Random rand = new Random();
				int dice = rand.nextInt(100); 
				if(dice<20)grille.set_case(i, j, new Neutre(grille.get(i, j),grille));// probabilité d'avoir un neutre de 10 %
			}
			
		}
	}
	/*Ecouteur du bouton de lancement : le jeu est lancé on change la couleur et la disponibilité des boutons du menus */
	class MyListenerGo extends MouseAdapter
	{
		@Override
		public void mouseClicked(MouseEvent arg0) {	
				jeu=true;
				menu_go.setBackground(Color.gray); 
				menu_go.setEnabled(false);
				next_turn.setBackground(Color.GREEN);
				next_turn.setEnabled(true);	
				auto.setEnabled(true);
				auto.setBackground(Color.GREEN);
				
					
			}
			
	}
	/*Ecouteur du bouton de lancement Automatique du jeu */
	class MyListenerAuto extends MouseAdapter
	{
		@Override
		public void mouseClicked(MouseEvent arg0) {	
			auto_test=!(auto_test);
			if(!(auto_test)){ // on repasse en mode manuel 
				next_turn.setBackground(Color.GREEN); 
				next_turn.setEnabled(true);
			}
			
			if(jeu && auto_test){//le jeu est automatique 
				next_turn.setBackground(Color.gray); 
				next_turn.setEnabled(false);
				final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
				executorService.scheduleAtFixedRate(new Runnable(){	// on execute la boucle tant que le jeu n'est pas fini puis on attend x secondes
					@Override
					public void run(){
						if(!(jeu) || !(auto_test)) return;
						boucleDeJeu();
								
					}
				}, 500, 500, TimeUnit.MILLISECONDS);
			}
					
		}
			
	}
	class MyListenerAide implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0) {	 // affichage des differente fenetre d'aide 
			if(jeu){
				JOptionPane.showMessageDialog(null, "Tour suivant pour le mode pas à pas \n Automatique pour le jeu Automatique", "Information", JOptionPane.INFORMATION_MESSAGE);
			}
			else{
				JOptionPane.showMessageDialog(null, "Placez vos manifestant avec le clique droit(clique gauche pour effacer) \nCliquez sur Lancer pour lancer la similation", "Information", JOptionPane.INFORMATION_MESSAGE);
			}	
		}
	}
	class MyListenerTurn extends MouseAdapter // ecouteur du bouton de lancement du jeu 
	{
		@Override
		public void mouseClicked(MouseEvent arg0) {
			if(jeu){
				boucleDeJeu();
			}
			
			
		}
	
	}
}
