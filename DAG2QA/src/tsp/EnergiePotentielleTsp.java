package tsp;

import java.util.ArrayList;

import solver.commun.EnergiePotentielle;
import solver.commun.Etat;
import solver.commun.MutationElementaire;

public class EnergiePotentielleTsp extends EnergiePotentielle {
	
	
	public EnergiePotentielleTsp(){
	}
	
	
	public double calculer(Etat etat){
		Routage r = (Routage) etat;
		return r.getEnergie();
	}
	
	public double calculerDeltaE(Etat etat, MutationElementaire mutation){
			TwoOptMoveElementaire m = (TwoOptMoveElementaire) mutation;
			Routage r = (Routage) etat;
			Graphe g = r.getGraphe();
			ArrayList<Integer> l = r.getRoute();
			double cpt =0;
			if (r.getPreviousIndex(m.getI())!=m.getJ()){
				cpt-=g.getdists()[ l.get(m.getI())][ l.get(r.getPreviousIndex(m.getI()))];
				cpt-=g.getdists()[ l.get(m.getJ())][ l.get(r.getNextIndex(m.getJ()))];
				cpt+=g.getdists()[ l.get(m.getI())][ l.get(r.getNextIndex(m.getJ()))];
				cpt+=g.getdists()[ l.get(m.getJ())][ l.get(r.getPreviousIndex(m.getI()))];
				return cpt;
			}
			return cpt;
			
	}
}
