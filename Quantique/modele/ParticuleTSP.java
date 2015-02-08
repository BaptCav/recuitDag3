package modele;
import java.util.ArrayList;






import mutation.IMutation;
import mutation.TwoOptMove;
import parametrage.EnergieCinetiqueTsp;
import parametrage.EnergiePotentielleTsp;
import parametrage.ParametreGamma;
import parametrage.Ponderation;
import parametrage.Temperature;


public class ParticuleTSP extends Probleme {
	ParametreGamma gamma;
	
	public ParticuleTSP(int value){
		
	}
	
	public ParticuleTSP(ArrayList<Etat> r,int seed,ParametreGamma gamma){
		this.etat=r;
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
	//Amelioration calculs
	public double[] tableauDistances(){
		int p = this.etat.size();
		double[] tab = new double[p];
		for (int i = 0; i < p; i++){
			tab[i] = EnergiePotentielleTsp.calculer((Routage)this.etat.get(i));	
		}
		return tab;
	}
	 //Universel mais ne marche pas, à corriger (pour l'instant, pas important)
	public double differenceSpins(int index, IMutation m){
		Routage now = (Routage) this.getEtat().get(index);
		Routage now2 = now.clone();
		
		int previous = this.getPreviousIndex(index);
		int next = this.getNextIndex(index);
		
		Routage avant = (Routage) this.getEtat().get(previous);
		Routage apres = (Routage) this.getEtat().get(next);
		
		m.faire(this,now2);
		
		int cptspin = now2.distanceIsing(avant) + now2.distanceIsing(apres) - now.distanceIsing(avant) - now.distanceIsing(apres);
		return cptspin;
	}
	
	//Moins de calcul que la methode precedente
	public double differenceSpinsTwoOpt(int index,TwoOptMove m){
		int cptspin = 0;
		int i = m.getI();
		int j = m.getJ();
		
		Routage now = (Routage) this.getEtat().get(index);
		
		int previous = this.getPreviousIndex(index);
		int next = this.getNextIndex(index);
		
		//Routes concernees, voisines de index dans la particule
		Routage avant = (Routage) this.getEtat().get(previous);
		Routage apres = (Routage) this.getEtat().get(next);
		
		int NodeI  = now.getRoute().get(i);
		int NodeBeforeI = now.getRoute().get(now.getPreviousIndex(i));
		int NodeJ = now.getRoute().get(j);
		int NodeAfterJ = now.getRoute().get(now.getNextIndex(j));
		
		if(now.getNextIndex(j)!=i){
			cptspin -= avant.valueIsing(NodeBeforeI,NodeI) + apres.valueIsing(NodeBeforeI,NodeI);
			cptspin -= avant.valueIsing(NodeAfterJ,NodeJ) + apres.valueIsing(NodeAfterJ,NodeJ);
			cptspin += avant.valueIsing(NodeBeforeI,NodeJ) + apres.valueIsing(NodeBeforeI,NodeJ);
			cptspin += avant.valueIsing(NodeAfterJ,NodeI) + apres.valueIsing(NodeAfterJ,NodeI);
		}
		// Avec un facteur 2 car un passage de 1 à -1 decremente le compteur spinique de 2
		return (2*cptspin);
	}
	
	//Calcul Ecin sans J
	public double calculerCompteurSpinique(){
		return EnergieCinetiqueTsp.calculerCompteurSpinique(this);
	}
	
	//Calcul Ecin
	public double calculerEnergieCinetique(){
		return -EnergieCinetiqueTsp.calculer(this,new Ponderation(this.gamma));
	}
	
	public double calculerEnergie(){
		double E = EnergieCinetiqueTsp.calculer(this,new Ponderation(this.gamma));
		return -E+this.calculerEnergiePotentielle();
	}
	
	
	public ArrayList<Etat> getEtat(){
		return this.etat;
	}
	
	public ParametreGamma getGamma(){
		return this.gamma;
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
