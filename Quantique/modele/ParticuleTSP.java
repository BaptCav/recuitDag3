package modele;
import parametrage.EnergieCinetiqueTsp;
import parametrage.EnergiePotentielleTsp;
import parametrage.ParametreT;


public class ParticuleTSP extends Probleme {
	Routage[] etat;
	public ParticuleTSP(Routage[] r,int seed){
		this.etat=r;
		this.setSeed(seed);		
	}
	public double calculerEnergiePotentielle(){
		double compteur=0;
		for (Etat i:this.etat){
			compteur +=EnergiePotentielleTsp.calculer(i);	
		}
		return compteur/this.etat.length;
	}
	
	public double calculerEnergie(){
		return EnergieCinetiqueTsp.calculer(this)+this.calculerEnergiePotentielle();
	}
	
	
	public Routage[] getRoutage(){
		return this.etat;
	}
	
	
	public ParametreT getTemperature() {
		// TODO Auto-generated method stub
		return this.getT();
	}
	
	
	public ParticuleTSP clone(){
		int n = this.etat.length;
		Routage[] tableau = new Routage[n];
		for(int i = 0 ; i<n; i++){
			tableau[i]=this.etat[i].clone();
		}
		 ParticuleTSP p = new ParticuleTSP(tableau, this.getSeed()) ;
		return p;
		
	}
	public void setRoutage(Routage[] e) {
		// TODO Auto-generated method stub
		this.etat=e;
		
	}
	public Routage getBest() {
		Routage best = this.etat[0];
		double min = best.getDistance();
		for(Routage i : this.etat){
			double j = i.getDistance();
			if(j<min){
				best=i;
				min =j;
			}
		}
		return best;
		
	}
	
	

}
