public class Probleme extends Particule{
	private int seed;

	// Fonctions élémentaires de calcul de l'energie de la particule
	
	public double calculerEnergie(){
	return EnergieCinetique.calculer(this)+this.calculerEnergiePotentielle();
	}
	public double calculerEnergiePotentielle(){
		double compteur=0;
		for (Etat i:this.etat){
			compteur +=EnergiePotentielle.calculer(i);	
		}
		return compteur;
	}
	public void modifElem(){
	}
	public void annulerModif(){
	}
	public void modifEtat(Etat e){
	}
	public void annulerModifEtat(Etat e){
	}
	
	public int getSeed(){
		return this.seed;
	}
	public void setSeed(int seed){
		this.seed=seed;
	}
	
	
	
	
}

