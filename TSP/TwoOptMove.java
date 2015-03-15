package tsp;

import java.util.ArrayList;

import solver.commun.Etat;
import solver.commun.IMutation;
import solver.commun.MutationElementaire;
import solver.commun.Probleme;

public class TwoOptMove implements IMutation {

	
	public void faire(Probleme probleme, Etat etat, MutationElementaire mutation) {

		Routage routage = (Routage) etat;
		TwoOptMoveElementaire m = (TwoOptMoveElementaire) mutation;
		//Mise à jour d'Ising
		int NodeI  = routage.getRoute().get(m.getI());
		int NodeBeforeI = routage.getRoute().get(routage.getPreviousIndex(m.getI()));
		int NodeJ = routage.getRoute().get(m.getJ());
		int NodeAfterJ = routage.getRoute().get(routage.getNextIndex(m.getJ()));

		//On modifie les spins concernes. La condition elimine un cas où twoOptMove ne modifie rien.
		if(routage.getNextIndex(m.getJ())!=m.getI()){
			routage.disconnect(NodeBeforeI, NodeI);
			routage.disconnect(NodeAfterJ, NodeJ);
			routage.connect(NodeBeforeI, NodeJ);
			routage.connect(NodeAfterJ, NodeI);
		}

		//Mutation sur la liste de noeuds
		int k = m.getI();
		int l = m.getJ();

		Swap.faire(routage,k,l);


		//On itÃ¨re ensuite pour effectuer tous les Ã©changes du twoOptMove
		while (k!=l && routage.getNextIndex(k)!=l ) {

			k=routage.getNextIndex(k);
			l=routage.getPreviousIndex(l);
			Swap.faire(routage,k,l);
		}
	}







	/*public double calculer(Probleme p, Etat e) {
		// Cette méthode va calculer le delta potentiel engendré par la mutation

		Graphe g = ((ParticuleTSP)p).getGraphe();
		Routage r = (Routage) e;
		ArrayList<Integer> l = r.getRoute();
		double cpt =0;
		if (r.getPreviousIndex(this.i)!=this.j){
			cpt-=g.getdists()[ l.get(this.i)][ l.get(r.getPreviousIndex(this.i))];
			cpt-=g.getdists()[ l.get(this.j)][ l.get(r.getNextIndex(this.j))];
			cpt+=g.getdists()[ l.get(this.i)][ l.get(r.getNextIndex(this.j))];
			cpt+=g.getdists()[ l.get(this.j)][ l.get(r.getPreviousIndex(this.i))];
			return cpt;
		}
		return cpt;
	}*/



	public MutationElementaire getMutationElementaire(Probleme probleme, Etat etat){
		Routage r = (Routage) etat;
		int n = r.getRoute().size();
		int randIndex1 = 0;//Indice du noeud c2
		int randIndex2 = 0;//Indice du noeud c1'
		//  On recalcule les indices de c2 et c1' jusqu'Ã  ce que c2 soit diffÃ©rent de c1'.Notons que le cas c2=c1' ne change pas la route.
		while (randIndex1 == randIndex2){
			randIndex1 = (int) (n * Math.random()); //c1'
			randIndex2 = (int) (n * Math.random()); //c2
		}
		return new TwoOptMoveElementaire(n,randIndex1,randIndex2);
	}

}
