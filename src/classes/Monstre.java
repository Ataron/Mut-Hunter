package classes;

import competences.Competences;
import competences.Shield;

/**
 * 
 * La classe Monstre permet d'instancier un monstre
 * @author Robin Gallifa
 *
 */
public class Monstre extends Personnage {

	/**
	 * Constructeur du Monstre avec une  position donnée
	 * @param p Prend en paramètre la position initiale du monstre
	 */
	public Monstre(Position p) {
		super(p);
		this.setType("monstre");
		this.setTableauCompetences(new Competences[] {new Shield(), null});
	}
	
	/**
	 * Permet de redemander un deplacement tant que ce que donne le joueur n'est pas bon
	 * @param p Prend un plateau en paramètre
	 */
	public void seDeplace(Plateau p) {
		while(!estDeplaceJoueur(p)) {
			System.out.println("Vous ne pouvez pas vous d�placer ici.");
		}
	}
}