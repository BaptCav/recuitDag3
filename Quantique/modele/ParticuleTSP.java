package modele;
import java.util.ArrayList;

import parametrage.EnergieCinetiqueTsp;
import parametrage.EnergiePotentielleTsp;
import parametrage.ParametreT;


public class ParticuleTSP extends Probleme {
	 ArrayList<Routage> etat;
	public ParticuleTSP(ArrayList<Routage> r,int seed){
		this.etat=r;
		this.setSeed(seed);		
	}
	public double calculerEnergiePotentielle(){
		double compteur=0;
		for (Etat i:this.etat){
			compteur +=EnergiePotentielleTsp.calculer(i);	
		}
		return compteur/this.etat.size();
	}
	
	public double calculerEnergie(){
		return EnergieCinetiqueTsp.calculer(this)+this.calculerEnergiePotentielle();
	}
	
	
	public ArrayList<Routage> getRoutage(){
		return this.etat;
	}
	
	
	public ParametreT getTemperature() {
		// TODO Auto-generated method stub
		return this.getT();
	}
	
	
	public ParticuleTSP clone(){
		int n = this.etat.size();
		ArrayList<Routage> tableau = new ArrayList<Routage>(n);
		for(int i = 0 ; i<n; i++){
			this.etat.set(i, this.etat.get(i).clone());
		}
		 ParticuleTSP p = new ParticuleTSP(tableau, this.getSeed()) ;
		return p;
		
	}
	public void setRoutage(ArrayList<Routage> e) {
		// TODO Auto-generated method stub
		this.etat=e;
		
	}
	
	

}
