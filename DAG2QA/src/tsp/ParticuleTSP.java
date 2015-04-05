package tsp;
 

import tsp.Routage;
import solver.commun.EnergieCinetique;
import solver.commun.EnergiePotentielle;
import solver.commun.Etat;
import solver.commun.HighQualityRandom;
import solver.commun.IMutation;
import solver.commun.MutationElementaire;
import solver.commun.Probleme;

public class ParticuleTSP extends Probleme {
	Graphe g;
	int replique;
	
public ParticuleTSP(EnergiePotentielle Ep, IMutation mutation, EnergieCinetique Ec, int k, int replique, Graphe graphe, int seed) {
		this.replique=replique;
		this.Ec = Ec;
		this.g = graphe;
		this.mutation = mutation;

		this.setSeed(seed);
		this.gen = new HighQualityRandom(seed);
		
		this.etats = new Etat[replique];
		for (int i = 0; i < replique; i++){
			this.etats[i] = new Routage(graphe,Ep);
		}
		
	}
	
	public Graphe getGraphe(){
		return this.g;
	}
	
	public Etat[] getEtat(){
		return this.getEtat();
	}
	
	public double calculerDeltaEc(Etat etat,Etat previous,Etat next, MutationElementaire mutation){
		return this.Ec.calculerDeltaE(etat, previous, next, mutation);
	}
	
	public double calculerDeltaEcUB(Etat etat, Etat previous, Etat next, MutationElementaire mutation){
		return this.Ec.calculerDeltaEUB(etat, previous, next, mutation);
	}
	
	public double calculerdeltaEp(Etat etat, MutationElementaire mutation){
		return etat.Ep.calculerDeltaE(etat, mutation);
	}
	
	public double calculerEnergiePotentielle(){
		double cpt = 0;
		for (Etat i: this.etats){
			cpt += i.Ep.calculer(i);
		}
		return cpt;
	}
	
	public double calculerEnergie(){
		return this.calculerEnergiePotentielle() + this.Ec.calculer(this);
	}
	
	public MutationElementaire getMutationElementaire(Etat etat){
		TwoOptMove m = new TwoOptMove();
		return m.getMutationElementaire(this,etat);
	}
	
	public void initialiser(){
		
		this.Ec = new EnergieCinetiqueTsp();
		TwoOptMove m = new TwoOptMove();
		this.mutation = m;
		
	}

	
	public void modifElem(Etat etat, MutationElementaire mutation){
		this.mutation.faire(this,etat,mutation);
	}
	
	@Override
	public void sauvegarderSolution() {
		// TODO Auto-generated method stub
		
	}

}
