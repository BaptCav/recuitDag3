package mutation;

import java.util.ArrayList;

import modele.*;

public class TwoOptMove implements IMutation {


	public void faire(Routage routage) {
		// A NE PAS UTILISER TELLE QU'ELLE il vaut mieux effectuer un calcul sur deux indices pour optimiser les temps de calcul

		int n = routage.tailleRoute();
		int randIndex1 = 0;//Indice du noeud c2
		int randIndex2 = 0;//Indice du noeud c1'
		int i;
		int j;
		//  On recalcule les indices de c2 et c1' jusqu'à ce que c2 soit différent de c1'.Notons que le cas c2=c1' ne change pas la route.
		while (randIndex1 == randIndex2){
			randIndex1 = (int) (n * Math.random()); //c1'
			randIndex2 = (int) (n * Math.random()); //c2
		}
		i = randIndex1;
		j = randIndex2;
		//On itère ensuite pour effectuer tous les échanges du twoOptMove
		while (i!=j && routage.getNextIndex(i)!=j ) {
			Swap.faire(routage,i,j);
			i=routage.getNextIndex(i);
			j=routage.getPreviousIndex(j);
		}
	}



	public double calculer(Routage r) {
		// A NE PAS UTILISER TELLE QU'ELLE
		return Double.MAX_VALUE;
	}

	
	public static double calculer(Routage r , int i , int j){
		// Cette m�thode va calculer le delta engendr� par la mutation
		ArrayList<Integer> l = r.getRoute();
		double cpt =0;
		cpt-=r.getGraphe().getdists()[ l.get(i)][ l.get(r.getPreviousIndex(i))];
		cpt-=r.getGraphe().getdists()[ l.get(j)][ l.get(r.getNextIndex(j))];
		cpt+=r.getGraphe().getdists()[ l.get(i)][ l.get(r.getNextIndex(j))];
		cpt+=r.getGraphe().getdists()[ l.get(j)][ l.get(r.getPreviousIndex(i))];

		return cpt;
	}
	
	
	public static void faire (Routage r , int i , int j){
		Swap.faire(r,i,j);
		while (i!=j && r.getNextIndex(i)!=j ) {
			i=r.getNextIndex(i);
			j=r.getPreviousIndex(j);
			Swap.faire(r,i,j);
		}
	}



	@Override
	public void faire(Probleme p) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void defaire(Probleme p) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void faire(Probleme p, Etat e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void defaire(Probleme p, Etat e) {
		// TODO Auto-generated method stub
		
	}


}
