package classes;

public enum TypeTerrain {

	PLAINE(0,0), FORET(0,0), MONTAGNE(0,0), EAU(0,0), DESERT(0,0), VILLE(0,0), PLAINE_ENNEIGEE(0,0),
		MONTAGNE_ENNEIGEE(0,0), LAC_GELE(0,0) ;
	
	private int vision;
	private int deplacement;
	
	private TypeTerrain(int vision, int deplacement) {
		this.vision = vision;
		this.deplacement = deplacement;
	}
	
	public int getVision() {
		return this.vision;
	}
	
}
