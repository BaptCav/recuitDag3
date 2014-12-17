package parametrage;


import java.util.ArrayList;
import modele.ParticuleTSP;
import modele.Routage;



public class EnergieCinetiqueTsp extends EnergieCinetique {
	
	public static double calculer(ParticuleTSP p, Ponderation J){
		ArrayList<Routage> r = p.getRoutage();
		int n = r.size();
		double compteurspinique=0;
		for(int i =0; i<n-1;i++){
			//// IL reste a coder le calcul particulier
			int[][] Mi = r.get(i).toIsing();
			int[][] Mj = r.get(i+1).toIsing();
			for(int k =0;k<Mi.length;k++){
				for(int l =0;l<Mi[0].length;l++){
					compteurspinique+=Mi[k][l]*Mj[k][l];
				}
			}	
		}
		// on boucle les répliques 
		int[][] Mi = r.get(n-1).toIsing();
		int[][] Mj = r.get(1).toIsing();
		for(int k =0;k<Mi.length;k++){
			for(int l =0;l<Mi[0].length;l++){
				compteurspinique+=Mi[k][l]*Mj[k][l];
			}
		}	
		
		//puis on retourne la somme des produits de spins pondérés pas la fonction de pondération
		return J.calcul(p.getT(),n)*compteurspinique;
	}

}
