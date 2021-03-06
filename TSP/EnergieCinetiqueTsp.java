package tsp;

import java.util.ArrayList;

import solver.commun.Etat;
import solver.commun.Probleme;

public class EnergieCinetiqueTsp {
	
	public double calculer(Probleme probleme){
		ParticuleTSP p = (ParticuleTSP) probleme;
		ArrayList<Etat> r = p.getEtat();
		int n = r.size();
		double compteurspinique=0;
		for(int i =0; i<n;i++){
			//// IL reste a coder le calcul particulier
			Etat e = r.get(i);
			Routage r1=(Routage) e;
			Routage r2=(Routage) e.getNext();
			
			//System.out.println(r1);
			int[][] Mi = r1.getIsing();
			int[][] Mj = r2.getIsing();
			for(int k =0;k<Mi.length-1;k++){
				for(int l =k+1;l<Mi.length;l++){
					compteurspinique+=Mi[k][l]*Mj[k][l];
				}
			}	
		
		}
		return compteurspinique;
	}

}
