
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
		return compteur/etat.length;
	}
	
	public double calculerEnergie(){
		return EnergieCinetiqueTsp.calculer(this)+this.calculerEnergiePotentielle();
	}
	
	public Routage[] getRoutage(){
		return etat;
	}
	public ParametreT getTemperature() {
		// TODO Auto-generated method stub
		return this.getT();
	}

}
