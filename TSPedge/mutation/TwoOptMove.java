package mutation;

import java.util.ArrayList;

import modele.*;

public class TwoOptMove implements IMutation {
	int i;
	int j;

	public TwoOptMove(int i, int j){
		this.i=i;
		this.j=j;
	}
	
	public TwoOptMove(int n){
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
	
	public double calculer(Etat e){
		Routage r = (Routage) e;
		// Cette méthode va calculer le delta engendré par la mutation
		ArrayList<Integer> l = r.getRoute();
		int k;
		int n;
		if(j==l.size()-1){
			n=0;
		}else{
			n=j+1;
		}
		if(i==0){
			k=l.size()-1;
		}else{
			k=i-1;
		}
		double[][]m = r.getGraphe().getdists();
		double c1 =m[ l.get(i)][ l.get(k)];
		double c2=m[ l.get(j)][ l.get(n)];
		double c3=m[ l.get(i)][ l.get(n)];
		double c4=m[ l.get(j)][ l.get(k)];
		
		// Routage r2 = r.clone();
		 //TwoOptMove.faire(r2,i,j);
		 
		
		 //System.out.println((r.getDistance()-r2.getDistance()));
		return (c4+c3-c2-c1);
	}
	
	
	public void faire (Etat e){
		Routage r = (Routage) e;
		int k = i;
		int l = j;
		Swap s = new Swap(k,l);
		s.faire(e);

		while (k!=l && r.getNextIndex(k)!=l ) {
			k=r.getNextIndex(k);
			l=r.getPreviousIndex(l);
			s = new Swap(k,l);
			s.faire(e);

		}
	}


}
