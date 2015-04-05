package tsp;

import solver.commun.EnergieCinetique;
import solver.commun.Etat;
import solver.commun.MutationElementaire;
import solver.commun.Probleme;

public class EnergieCinetiqueTsp extends EnergieCinetique {
	
	public double calculer(Probleme probleme){
		ParticuleTSP p = (ParticuleTSP) probleme;
		Etat[] r = p.getEtat();
		int n = r.length;
		double compteurspinique=0;
		for(int i =0; i<n;i++){
			//// IL reste a coder le calcul particulier
			Etat e = r[i];
			Etat e2 = r[(i+1)%n];
			Routage r1=(Routage) e;
			Routage r2=(Routage) e2;
			
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

	@Override
	public double calculerDeltaE(Etat etat, Etat prev, Etat next,
			MutationElementaire mutation) {
		int cptspin = 0;
		TwoOptMoveElementaire m = (TwoOptMoveElementaire) mutation;
		int i = m.getI();
		int j = m.getJ();
		
		Routage now = (Routage) etat;
		
		
		//Routes concernees, voisines de index dans la particule
		Routage avant = (Routage) prev;
		Routage apres = (Routage) next;
		
		
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

	@Override
	public double calculerDeltaEUB(Etat etat, Etat prev, Etat next,
		MutationElementaire mutation) {
		return 16;
	}

}
