package modele;
import parametrage.EnergieCinetique;
import parametrage.EnergiePotentielle;
import parametrage.EnergiePotentielleTsp;

public class Probleme extends Particule{

	// On rajoute de plus une seed de génération de nombres aléatoires
	private int seed;

	// Fonctions Ã©lÃ©mentaires de calcul de l'energie de la particule

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
		Routage best = (Routage) this.etat.get(0);
		double min = EnergiePotentielleTsp.calculer(best);
		for(Etat i : this.etat){
			double j = EnergiePotentielleTsp.calculer((Routage)i);
			if(j<min){
				best=(Routage) i;
				min =j;
			}
		}
		return best;
		
	}
	



}

