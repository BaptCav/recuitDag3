package modele;
import java.util.ArrayList;



import parametrage.EnergieCinetiqueTsp;
import parametrage.EnergiePotentielleTsp;
import parametrage.ParametreGamma;
import parametrage.Ponderation;
import parametrage.Temperature;


public class ParticuleTSP extends Probleme {
	ParametreGamma gamma;
	public ParticuleTSP(ArrayList<Etat> r,int seed,ParametreGamma gamma){
		this.etat=r;
		for(int i =1, i<etat.size()-1,i++){
			etat[i].setPrevious(etat[i-1]);
			etat[i].setPrevious(etat[i+1]);
		}
		this.setSeed(seed);	
		this.gamma = gamma;
	}
	public double calculerEnergiePotentielle(){
		double compteur=0;
		for (Etat i:this.etat){
			compteur +=EnergiePotentielleTsp.calculer((Routage)i);	
		}
		return compteur/this.etat.size();
	}
	
	public double calculerEnergie(){
		double E = EnergieCinetiqueTsp.calculer(this,new Ponderation(this.gamma));
		return -E+this.calculerEnergiePotentielle();
	}
	
	
	public ArrayList<Etat> getEtat(){
		return this.etat;
	}
	
	
	public Temperature getTemperature() {
		// TODO Auto-generated method stub
		return this.getT();
	}
	
	
	public ParticuleTSP clone(){
		int n = this.etat.size();
		ArrayList<Etat> r = new ArrayList<Etat>(n);
		for(int i=0; i<n; i++){
			r.add(((Routage) this.etat.get(i)).clone());
		}
		 ParticuleTSP p = new ParticuleTSP(r, this.getSeed(),this.gamma) ;
		 p.setT(this.getT());
		return p;
		
	}
	public void setRoutage(ArrayList<Etat> e) {
		// TODO Auto-generated method stub
		this.etat=e;
		
	}
	public void majgamma(){
		this.gamma.refroidissementExp();
	}
	

}
