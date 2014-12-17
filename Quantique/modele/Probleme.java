package modele;
import parametrage.EnergieCinetique;
import parametrage.EnergiePotentielle;

public class Probleme extends Particule{

	// On rajoute de plus une seed de g�n�ration de nombres al�atoires
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
		return compteur/this.etat.size();
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
	
	public Etat getBest() {
		Etat best = this.etat.get(0);
		EnergiePotentielle e = best.getE();
		double min = e.calculer(best);
		for(Etat i : this.etat){
			double j = e.calculer(i);
			if(j<min){
				best=i;
				min =j;
			}
		}
		return best;
		
	}



}

