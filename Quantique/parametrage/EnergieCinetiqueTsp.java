package parametrage;

import modele.ParticuleTSP;
import modele.Routage;



public class EnergieCinetiqueTsp extends EnergieCinetique {
	
	public static double calculer(ParticuleTSP p, Ponderation J){
		Routage[] r = p.getRoutage().clone();
		double compteurspinique=0;
		for(int i =0; i<r.length-1;i++){
			//// IL reste a coder le calcul particulier
			int[][] Mi = r[i].toIsing();
			int[][] Mj = r[i+1].toIsing();
			for(int k =0;k<Mi.length;k++){
				for(int l =0;l<Mi[0].length;l++){
					compteurspinique+=Mi[k][l]*Mj[k][l];
				}
			}	
		}
		// on boucle les répliques 
		int[][] Mi = r[r.length-1].toIsing();
		int[][] Mj = r[1].toIsing();
		for(int k =0;k<Mi.length;k++){
			for(int l =0;l<Mi[0].length;l++){
				compteurspinique+=Mi[k][l]*Mj[k][l];
			}
		}	
		
		//puis on retourne la somme des produits de spins pondérés pas la fonction de pondération
		return J.calcul(p.getT(),r.length)*compteurspinique;
	}

}
