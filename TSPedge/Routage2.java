import java.util.ArrayList;
import java.util.Collections;
public class Routage2 {
	ArrayList<Integer> route ;
	Graphe g;
	
	public ArrayList<Integer> routeInitiale() {
		int n = g.nombreDeNoeuds();
		ArrayList<Integer> liste = new ArrayList<Integer>();
		for (int index = 0; index < n; index++) {
			liste.add(new Integer(index));
		}
		// rÃƒÂ©organise alÃƒÂ©atoirement l'ordre de visite
		Collections.shuffle(liste);	
		return liste;
		
	}
	public Routage2 (Graphe g){
		this.g = g;
		this.route=routeInitiale();
	
	}
	
	
	public  Routage2 (Graphe g, ArrayList<Integer> liste){
		this.g=g;
		this.route=liste;
	}
	
	public void clone(Routage2 modele)
	{
		int n = g.nombreDeNoeuds();
		for (int index = 0; index < n; index++){
			this.getRoute().set(index, modele.route.get(index));
		}
	}
	public void clear()
	{
		this.route.clear();
	}
	public Graphe getGraphe(){
		return this.g;
	}
	// En entrée, un indice correspondant à la place d'un élément sur une route. En sortie, le noeud correspondant à l'indice suivant
	public int getNext(int index){
		if (index==this.tailleRoute()-1) {
			return this.getRoute().get(0);
		} else {
			return this.getRoute().get(index+1);
			}
	}
	//En entrée, un indice correspondant à la place d'un élément sur une route. En sortie, l'indice correspondant au noeud suivant(dans la plupart des cas, on ajoute simplement 1)
	public int getNextIndex(int index){
		if (index==(this.tailleRoute()-1)) {
			return 0;
		} else {
			return (index+1);
			}
	}
	
	// En entrée, un indice correspondant à la place d'un élément sur une route. En sortie, le noeud correspondant à l'indice précédent
	public int getPrevious(int index){
		if (index==0) {
			return  this.getRoute().get(this.tailleRoute() - 1);
		} else {
			return  this.getRoute().get(index-1);
		}
	}
	
	// En entrée, un indice correspondant à la place d'un élément sur une route. En sortie, l'indice du noeud précédent(en général, on soustrait 1)
	public int getPreviousIndex(int index){
		if (index==0) {
			return  (this.tailleRoute() - 1);
		} else {
			return (index-1);
		}
	}
		public ArrayList<Integer> getRoute(){
			return route;
		}
		public int tailleRoute() {
			return route.size();
		}

	
		public String toString() {
			int n = this.tailleRoute();
			String s = "";
			for (int index = 0; index < n; index++){
				s += route.get(index).intValue() + /*"("+ g.longueurEntre(route.get(index),getPreviousIndex(index)) + ")"+ */"->";
			}
			return s;
		}
	
	public double getDistance(){
		double cpt=0.0;
		int j=0;
		int L = this.tailleRoute();
		for(int i=0;i<L;i++){
			j=this.getNextIndex(i);
			cpt+=this.getGraphe().getdists()[this.getRoute().get(i)][this.getRoute().get(j)];
			
		}//on prend quand meme en consideration la distance dernier -> premier

		return cpt;
		
	}


}