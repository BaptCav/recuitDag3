package mutation;

import java.util.ArrayList;

import parametrage.NombreEnergie;
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
	
	public NombreEnergie calculer(Etat e){
		Routage r = (Routage) e;
		// Cette méthode va calculer le delta engendré par la mutation
		ArrayList<Noeud> l = r.getRoute();
		
		int k = r.getPreviousIndex(this.i);
		int n = r.getNextIndex(this.j);

		double[][]m = r.getGraphe().getdists();
		// Calcul des delta engendre par la creation/disparition de chaque arête
		double c1 =m[ l.get(this.i).getIndex()][ l.get(k).getIndex()];
		double c2=m[ l.get(this.j).getIndex()][ l.get(n).getIndex()];
		double c3=m[ l.get(this.i).getIndex()][ l.get(n).getIndex()];
		double c4=m[ l.get(this.j).getIndex()][ l.get(k).getIndex()];
		
		return new NombreEnergie(c4+c3-c2-c1);
	}
	
	
	public void faire (Etat e){
		Routage r = (Routage) e;
		int k = this.i;
		int l = this.j;
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
