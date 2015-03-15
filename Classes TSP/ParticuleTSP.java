package tsp;

import java.util.ArrayList;

import solver.commun.Etat;
import solver.commun.MutationElementaire;
import solver.commun.Probleme;

public class ParticuleTSP extends Probleme {
	Graphe g;
	ArrayList<Routage> etat ;
	
	public Graphe getGraphe(){
		return this.g;
	}
	
	public ArrayList<Routage> getEtat(){
		return this.etat;
	}
	
	public double calculerDeltaEc(Etat etat,Etat previous,Etat next, MutationElementaire mutation ){
		
	}

}
