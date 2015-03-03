package modele;
import java.util.ArrayList;

import mutation.IMutation;
import parametrage.EnergieCinetique;
import parametrage.EnergiePotentielle;
import parametrage.ParametreGamma;
import parametrage.Temperature;

public class Probleme extends Particule{

	// On rajoute de plus une seed de g�n�ration de nombres al�atoires
	private int seed;
	private EnergieCinetique energiecin;
	private EnergiePotentielle energiepot;
	// Fonctions élémentaires de calcul de l'energie de la particule
	private ParametreGamma gamma;

	
	public Probleme(){
		
	}
	public Probleme(ArrayList<Etat> etat,Temperature T,int seed,EnergieCinetique energiecin,EnergiePotentielle energiepot,ParametreGamma gamma){
		super(etat, T);
		this.seed=seed;
		this.energiecin=energiecin;
		this.energiepot=energiepot;
		this.gamma=gamma;
	}


	public Probleme clone(){
		ArrayList<Etat> e= this.getEtat();
		int n = this.etat.size();
		ArrayList<Etat> r = new ArrayList<Etat>(n);
		for(int i=0; i<n; i++){
			r.add(( (this.etat.get(i)).clone()));
		}
		Probleme p = new Probleme(r,this.getT(),this.seed,this.energiecin,this.energiepot,this.gamma);
		return p;
	}
	public double calculerEnergie(){
		return this.calculerEnergieCinetique()+this.calculerEnergiePotentielle();
	}
	public double calculerEnergieCinetique(){
		return 	this.energiecin.calculer(this);
	}

	public double calculerEnergiePotentielle(){
		double compteur=0;
		for (Etat i:this.etat){
			compteur +=this.energiepot.calculer(i);	
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
		double min = energiepot.calculer(best);
		for(Etat i : this.etat){
			double j = energiepot.calculer((Routage)i);
			if(j<min){
				best= i;
				min =j;
			}
		}
		return best;

	}

	public int differenceSpins(Etat e,IMutation m){
		
			Etat now = e;
			Etat now2 = e.clone();
			
			Etat avant = now.getPrevious();
			Etat apres = now.getNext();
			
			m.faire(this,now2);
			
			int cptspin = now2.distanceIsing(avant) + now2.distanceIsing(apres) - now.distanceIsing(avant) - now.distanceIsing(apres);
			return cptspin;
		}


	public ParametreGamma getGamma(){
		return this.gamma;
	}
	public void majgamma(){
		this.gamma.refroidissementExp();
	}
	public void setGamma(ParametreGamma gamma){
		this.gamma=gamma;
	}
	
	public EnergieCinetique getEcin(){
		return energiecin;
	}
	public EnergiePotentielle getEpot(){
		return energiepot;
	}
	public void setEcin(EnergieCinetique ecin){
		this.energiecin=ecin;
	}
	
	public void setEpot(EnergiePotentielle epot){
		this.energiepot=epot;
	}
	
	public Etat creeEtatAleatoire(){
		/**
		 * Par defaut cree un Etat a NULL
		 * L'utilisateur DOIT IMPLEMENTER cett methode pour creer un Etat adapte a son probleme
		 */
		
		return new Etat();
	}
	
}

