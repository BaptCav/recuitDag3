package mutation;

import java.util.ArrayList;

import parametrage.NombreEnergie;

import modele.*;

public class TwoOptMove implements IMutation {
int i;
int j;

public TwoOptMove(int n ){
	
	int randIndex1 = 0;//Indice du noeud c2
	int randIndex2 = 0;//Indice du noeud c1'
	//  On recalcule les indices de c2 et c1' jusqu'Ã  ce que c2 soit diffÃ©rent de c1'.Notons que le cas c2=c1' ne change pas la route.
	while (randIndex1 == randIndex2){
		randIndex1 = (int) (n * Math.random()); //c1'
		randIndex2 = (int) (n * Math.random()); //c2
	}
	this.i = randIndex1;
	this.j = randIndex2;

}

public int getI(){
	return this.i;
}

public int getJ(){
	return this.j;
}

	public Etat faire(Probleme p, Etat e) {
		
		Routage routage = (Routage) e;
		//Mise à jour d'Ising
		int NodeI  = routage.getRoute().get(this.i);
		int NodeBeforeI = routage.getRoute().get(routage.getPreviousIndex(this.i));
		int NodeJ = routage.getRoute().get(this.j);
		int NodeAfterJ = routage.getRoute().get(routage.getNextIndex(this.j));
		
		//On modifie les spins concernes. La condition elimine un cas où twoOptMove ne modifie rien.
		if(routage.getNextIndex(this.j)!=this.i){
				routage.disconnect(NodeBeforeI, NodeI);
				routage.disconnect(NodeAfterJ, NodeJ);
				routage.connect(NodeBeforeI, NodeJ);
				routage.connect(NodeAfterJ, NodeI);
			}
		
		//Mutation sur la liste de noeuds
		int k = this.i;
		int l = this.j;
		
		Swap.faire(routage,k,l);


		//On itÃ¨re ensuite pour effectuer tous les Ã©changes du twoOptMove
		while (k!=l && routage.getNextIndex(k)!=l ) {
		
			k=routage.getNextIndex(k);
			l=routage.getPreviousIndex(l);
			Swap.faire(routage,k,l);
		}
		//routage.updateIsing();
		return routage;
	}

	

	


	public double calculer(Probleme p, Etat e) {
		// Cette méthode va calculer le delta potentiel engendré par la mutation
		
		
		Routage r = (Routage) e;
		ArrayList<Integer> l = r.getRoute();
		double cpt =0;
		if (r.getPreviousIndex(this.i)!=this.j){
		cpt-=r.getGraphe().getdists()[ l.get(this.i)][ l.get(r.getPreviousIndex(this.i))];
		//System.out.println("avanti - i: " + r.getGraphe().getdists()[ l.get(this.i)][ l.get(r.getPreviousIndex(this.i))]);
		cpt-=r.getGraphe().getdists()[ l.get(this.j)][ l.get(r.getNextIndex(this.j))];
		//System.out.println("j - apresj: " + r.getGraphe().getdists()[ l.get(this.j)][ l.get(r.getNextIndex(this.j))]);
		cpt+=r.getGraphe().getdists()[ l.get(this.i)][ l.get(r.getNextIndex(this.j))];
		//System.out.println("i - apresj: " + r.getGraphe().getdists()[ l.get(this.i)][ l.get(r.getNextIndex(this.j))]);
		cpt+=r.getGraphe().getdists()[ l.get(this.j)][ l.get(r.getPreviousIndex(this.i))];
		//System.out.println("avanti - j: " +r.getGraphe().getdists()[ l.get(this.j)][ l.get(r.getPreviousIndex(this.i))]);
		return cpt;
		}
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
