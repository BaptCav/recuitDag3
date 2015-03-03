package parametrage;


import java.util.ArrayList;

import modele.Etat;
import modele.ParticuleTSP;
import modele.Routage;



public class EnergieCinetiqueTsp extends EnergieCinetique {
	
public static double calculerCompteurSpinique(ParticuleTSP p){
		
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
	
	public static double calculer(ParticuleTSP p, Ponderation J){
		int n = p.getEtat().size();
		return J.calcul(p.getT(),n)*calculerCompteurSpinique(p);
	}
	


}
