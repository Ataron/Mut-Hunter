package classes;

import competences.Competences;
import competences.Shield;

/**
 * 
 * La classe IAMonstre permet d'instancier un monstre joué par l'ordinateur
 * @author Robin Gallifa
 *
 */
public class IAMonstre extends Personnage {

	String direction = "z";
	
	/**
	 * Constructeur du Chasseur avec une  position donnée
	 * @param p Prend en paramètre la position initiale du chasseur
	 */
	public IAMonstre(Position p) {
		super(p);
		this.setType("monstre");
		this.setTableauCompetences(new Competences[] {new Shield(), null});		
	}
	
	/**
	 * Permet de redemander un deplacement tant que celles données ne sont pas bonnes
	 * @param p Prend un plateau en paramètre
	 */
	public void seDeplace(Plateau p) {
		while(!estDeplace(p, this.directionIADebutant(p)));
	}
	
	/**
	 * Renvoie une direction(zqsd) pour que l'IA traverse le plateau de manière linéaire jusqu'à gagner ou perdre
	 * @param p Prend un plateau en paramètre
	 */
	protected String directionIADebutant(Plateau p) {
		if(direction == "z") {
			if(this.getPosition().getX()-1<0 && !p.getCase(new Position(this.getPosition().getX(), this.getPosition().getY())).getEstPortail()) {
				direction = "s";
				return "d";
			}
			else return direction;
		}
		else {
			if(this.getPosition().getX()+1>=p.getLargeur() && !p.getCase(new Position(this.getPosition().getX(), this.getPosition().getY())).getEstPortail()) {
				direction = "z";
				return "d";
			}
			else return direction;
		}
	}
	
}
