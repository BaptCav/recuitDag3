package mutation;

import java.util.ArrayList;

import modele.*;

public class TwoOptMove implements IMutation {
int i;
int j;

public TwoOptMove(int n ){
	
	int randIndex1 = 0;//Indice du noeud c2
	int randIndex2 = 0;//Indice du noeud c1'
	//  On recalcule les indices de c2 et c1' jusqu'à ce que c2 soit différent de c1'.Notons que le cas c2=c1' ne change pas la route.
	while (randIndex1 == randIndex2){
		randIndex1 = (int) (n * Math.random()); //c1'
		randIndex2 = (int) (n * Math.random()); //c2
	}
	this.i = randIndex1;
	this.j = randIndex2;

}


	public void faire(Probleme p, Etat e) {
		
		Routage routage = (Routage) e;
		int k = this.i;
		int l = this.j;
		
		Swap.faire(routage,k,l);


		//On itère ensuite pour effectuer tous les échanges du twoOptMove
		while (k!=l && routage.getNextIndex(k)!=l ) {
		
			k=routage.getNextIndex(k);
			l=routage.getPreviousIndex(l);
			Swap.faire(routage,k,l);
		}
	}

	

	


	public double calculer(Probleme p, Etat e) {
		// Cette m�thode va calculer le delta engendr� par la mutation
		Routage r = (Routage) e;
		ArrayList<Integer> l = r.getRoute();
		double cpt =0;
		cpt-=r.getGraphe().getdists()[ l.get(this.i)][ l.get(r.getPreviousIndex(this.i))];
		cpt-=r.getGraphe().getdists()[ l.get(this.j)][ l.get(r.getNextIndex(this.j))];
		cpt+=r.getGraphe().getdists()[ l.get(this.i)][ l.get(r.getNextIndex(this.j))];
		cpt+=r.getGraphe().getdists()[ l.get(this.j)][ l.get(r.getPreviousIndex(this.i))];

		return cpt;
		
	}


	///// POUR CETTE MUTATION ON NE S'INTERESSE QU'A UNE MUTATION ETATIQUE
	
	@Override
	public double calculer(Probleme p) {
		// TODO Auto-generated method stub
		return Double.MAX_VALUE;
	}


	@Override
	public void faire(Probleme p) {
		// TODO Auto-generated method stub
		
	}


}
