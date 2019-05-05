package classes;

import java.util.Random;
/**
 * Cette classe a pour but de gérer et manipuler un terrain, représenter par un tableau à deux dimensions.
 * @author Bankaert Benoit
 * @version 1.0
 */
public class Plateau {
	
	/**
	 * Rand permet de générer des nombres aléatoires.
	 */
	private static Random rand = new Random();
	/**
	 * plateau est un tableau à deux dimensions de Case qui représente le terrain de jeu.
	 */
	private Case[][] plateau;
	/**
	 * tours représente le nombre de tours dans la partie en cours.
	 */
	private int tours;
	/**
	 * PORTAIL1 représente la position dans le terrain du premier portail.
	 */
	private final Position PORTAIL1 = new Position(9,0);
	/**
	 * PORTAIL2 représente la position dans le terrain du second portail.
	 */
	private final Position PORTAIL2 = new Position(0,9);
	/**
	 * tourChasseur indique si c'est au chasseur de jouer.
	 */
	private boolean tourChasseur;
	/**
	 * tourMonstre indique si c'est au monstre de jouer.
	 */
	private boolean tourMonstre;
	
	private int dernierLoot;
	
	private final int nbCases;
	
	private int compteurCasesDecouvertes;
	
	/**
	 * On instancie un terrain de 10 par 10 et on règle le nombre de tour sur 1.
	 */
	public Plateau() {
		this.plateau = new Case[10][10];
		this.tours = 1;
		this.dernierLoot = 0;
		this.nbCases = this.getHauteur()*this.getLargeur()-2;
		this.compteurCasesDecouvertes = 1;
	}
	
	/**
	 * Permet d'initialise les cases de tout le terrain et on place les deux joueurs et les deux portails.
	 */
	public void initPlateau() {
		for(int i = 0; i < this.plateau.length; i++) {
			for(int j = 0; j < this.plateau[i].length; j++) {
				if(rand.nextInt(100)>20) {
					this.plateau[i][j] = new Case(TypeTerrain.PLAINE);
				} else {
					this.plateau[i][j] = new Case(TypeTerrain.MONTAGNE);
				}
			}
		}
		this.generePortail();
		
	}
	public void startPersonnage(Personnage chasseur, Personnage monstre) {
		
		this.plateau[chasseur.getPosition().getX()][chasseur.getPosition().getY()].setEstChasseur(true);
		this.plateau[monstre.getPosition().getX()][monstre.getPosition().getY()].setEstMonstre(true);
		this.plateau[monstre.getPosition().getX()][monstre.getPosition().getY()].decouvrirCase();
	}
	
	private void generePortail() {
		this.plateau[this.PORTAIL1.getX()][this.PORTAIL1.getY()].setEstPortail(true);
		this.plateau[this.PORTAIL2.getX()][this.PORTAIL2.getY()].setEstPortail(true);
	}
	
	/**
	 * Permet d'afficher le terrain.
	 * @param p prend un personnage en paramètre
	 */
	public void affichePlateau(Personnage p) {
		clearScreen();
		System.out.print("═════════════════════════════════════════\nTour n°"+this.tours+"\n╔═══╦═══╦═══╦═══╦═══╦═══╦═══╦═══╦═══╦═══╗\n║");
		for(int i = 0; i < this.plateau.length; i++) {
			for(int j = 0; j < this.plateau[i].length; j++) {
				if(p.getType().equals("monstre") && this.plateau[i][j].getIcon()==' ' && this.plateau[i][j].getEstDecouvert()) System.out.print(" X ║");
				else System.out.print(" "+this.plateau[i][j].getIcon()+" ║");
			}
			if(i+1 == this.plateau.length) System.out.print("\n╚═══╩═══╩═══╩═══╩═══╩═══╩═══╩═══╩═══╩═══╝\n");
			else System.out.print("\n╠═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╣\n║");
		}
		System.out.println(p.toString());
		
		if(this.plateau[p.getPosition().getX()][p.getPosition().getY()].getEstChasseur()) {
			if(this.plateau[p.getPosition().getX()][p.getPosition().getY()].getEstDecouvert()) {
				System.out.println("Case découverte au tour "+this.plateau[p.getPosition().getX()][p.getPosition().getY()].getTempsDecouvert());
			}else {
				System.out.println("Case non découverte");
			}
		}
		
		System.out.println("\n═════════════════════════════════════════");
	}
	
	/**
	 * 
	 * @return le terrain.
	 */
	public Case[][] getPlateau(){
		return this.plateau;
	}
	 /**
	  * Remplace le terrain par le terrain passé en parametre.
	  * @param tab est le nouveau terrain.
	  */
	public void setPlateau(Case[][] tab) {
		this.plateau = tab;
	}
	
	private int nbLoot() {
		int nb = 0;
		for(int i = 0; i < this.plateau.length; i++)
			for(int j = 0; j < this.plateau[i].length; j++)
				if(this.plateau[i][j].getLoot())
					nb++;
		return nb;
	}
	
	/**
	 * Ajoute de manière aleatoire un nombre de loot passé en parametre sur le terrain.
	 * @param loot est le nombre de loot que l'on souhaite ajouter.
	 */
	public void ajoutLoot(int loot) {
		if(loot+this.nbLoot() > 2) loot = 2 - this.nbLoot();
		for(int i = 0; i < loot ; i++) {
			if(this.nbLoot() < 2) {
				int x,y;
				do {
					x = rand.nextInt(this.plateau.length);
					y = rand.nextInt(this.plateau[0].length);
				}while(this.plateau[x][y].getLoot() && this.plateau[x][y].getEstPortail() && this.plateau[x][y].getEstMonstre() && this.plateau[x][y].getEstChasseur());
				
				this.plateau[x][y].changeLoot();
			}
		}
	}
	
	public void ajoutCompetence(Personnage p) {
		Loot.changeCompetence(p);
	}
	
	/**
	 * 
	 * @return le nombre de tour Ã©couler depuis le debut de la partie.
	 */
	public int getTours() {
		return this.tours;
	}
	
	/**
	 * Augement le nombre de tour d'un.
	 */
	public void addTours() {
		this.tours++;
	}
	
	/**
	 * Rend la case dont la position lui est donnée en paramètre normale.
	 * @param p est la position dans le terrain que l'on souhaite éditer
	 */
	public void setCaseNormal(Position p) {
		this.plateau[p.getX()][p.getY()].setEstChasseur(false);
		this.plateau[p.getX()][p.getY()].setEstMonstre(false);
		if(this.plateau[p.getX()][p.getY()].getTypeCase() == TypeCase.LOOT) this.plateau[p.getX()][p.getY()].setLoot(false);
	}
	
	/**
	 * @return la hauteur du terrain
	 */
	public int getHauteur() {
		return this.plateau.length;
	}
	
	/**
	 * @return la largeur du terrain
	 */
	public int getLargeur() {
		return this.plateau[0].length;
	}
	
	/**
	 * Verifie si le chasseur a gagné la partie.
	 * @param c est la position du chasseur
	 * @param m est la position du monstre
	 * @return true si le chasseur se trouve à la même position que le monstre
	 */
	public boolean victoireChasseur(Position c, Position m) {
		if(c.equals(m)) return true;
		return false;
	}
	
	/**
	 * Verifie si le monstre a gagné la partie.
	 * @return true si le monstre a découvert toutes les cases
	 */
	public boolean victoireMonstre() {
		if(this.compteurCasesDecouvertes == this.nbCases) {
			System.out.println("Vous avez découvert toutes las cases. Vous avez GAGNÉ !");
			return true;
		}
		return false;
	}
	
	/**
	 * Verifie si le monstre a perdu la partie.
	 * @param m c'est la position du monstre
	 * @return true si le monstre repasse sur une case
	 */
	
	public boolean defaiteMonstre(Position m) {
		if(this.getCase(m).getEstDecouvert()) {
			System.out.println("Vous êtes déjà passé par cette case. Vous avez PERDU !");
			return true;
		}
		return false;
	}
	
	
	
	/**
	 * @param p est la position de la case voulue
	 * @return la case passé en parametre
	 */
	public Case getCase(Position p) {
		return this.plateau[p.getX()][p.getY()];
	}
	
	/**
	 * Permet au joueur de se téléporter d'un portail à un autre si la position passée en paramètre est la position d'un portail.
	 * @param p est la position du joueur
	 * @return la position d'un portail
	 */
	public Position teleportation(Position p) {
		if(p.equals(this.PORTAIL1)) return this.PORTAIL2;
		return this.PORTAIL1;
	}
	
	/**
	 * @return true si c'est au chasseur de jouer, false sinon
	 */
	public boolean getTourChasseur() {
		return this.tourChasseur;
	}
	
	/**
	 * Change le statut de tourChasseur avec le booléen passé en paramètre.
	 * @param b est le nouveau statut de tourChasseur
	 */
	public void setTourChasseur(boolean b){
		this.tourChasseur = b;
	}
	
	/**
	 * @return true si c'est au monstre de jouer, false sinon
	 */
	public boolean getTourMonstre() {
		return this.tourMonstre;
	}
	
	/**
	 * Change le statut de tourMonstre avec le booléen passé en paramètre. 
	 * @param b est le nouveau statut de tourMonstre
	 */
	public void setTourMonstre(boolean b){
		this.tourMonstre = b;
	}
	
	/**
	 * Découvre une case en conservant le tour de sa découverte 
	 * @param p personnage qui devrait être un monstre
	 */
	public void decouvrirCase(Personnage p) {
		if(this.plateau[p.getPosition().getX()][p.getPosition().getY()].getEstMonstre()) {
			this.plateau[p.getPosition().getX()][p.getPosition().getY()].decouvrirCase();
			this.plateau[p.getPosition().getX()][p.getPosition().getY()].setTempsDecouvert(this.getTours());
		}
	}
	
	/**
	 * @return le tour auquel le dernier loot a été découvert
	 */
	public int getDernierLoot() {
		return this.dernierLoot;
	}
	
	/**
	 * Remplace la valeur de dernierLoot pour le tour actuel
	 */
	public void setDernierLoot() {
		this.dernierLoot = this.getTours();
	}
	
	private static void clearScreen() {
		try {
			if(System.getProperty("os.name" ).startsWith("Windows")) {
				Runtime.getRuntime().exec("cls" );
			} else {
			    Runtime.getRuntime().exec("clear" );
			}
		} catch(Exception e) {
			for(int i = 0; i < 100; i++) {
				System.out.println();
			}
		}
	}
	
	public int getCompteurCasesDecouvertes() {
		return this.compteurCasesDecouvertes;
	}
	
	public void setCompteurCasesDecouvertes() {
		this.compteurCasesDecouvertes++;
	}
}