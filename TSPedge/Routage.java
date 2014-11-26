import java.util.ArrayList;
import java.util.Collections;
public class Routage {
	ArrayList<Integer> route ;
	Graphe g;
	
	
	public ArrayList<Integer> getRoute(){
		return route;
	}
	public int tailleRoute() {
		return route.size();
	}
	
	public Routage (Graphe g){
		this.g = g;
		this.route=routeInitiale();
	
	}

	public  Routage (Graphe g, ArrayList<Integer> liste){
		this.g=g;
		this.route=liste;
	}
	
	public ArrayList<Integer> routeInitiale() {
		int n = g.nombreDeNoeuds();
		ArrayList<Integer> liste = new ArrayList<Integer>();
		for (int index = 0; index < n; index++) {
			liste.add(new Integer(index));
		}
		// Réorganise aléatoirement l'ordre de visite
		Collections.shuffle(liste);	
		return liste;
		
	}
	
	public void clone(Routage modele)
	{
		int n = g.nombreDeNoeuds();
		for (int index = 0; index < n; index++){
			this.getRoute().set(index, modele.route.get(index));
		}
	}
	
	public Graphe getGraphe(){
		return this.g;
	}

	public int getNextIndex(int index){
		if (index==(this.tailleRoute()-1)) {
			return 0;
		} else {
			return (index+1);
			}
	}
	
	public int getPreviousIndex(int index){
		if (index==0) {
			return  (this.tailleRoute() - 1);
		} else {
			return (index-1);
		}
	}

		public String toString() {
			int n = this.tailleRoute();
			String s = "";
			for (int index = 0; index < n; index++){
				s += route.get(index).intValue() + "->";
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
			
		}
		return cpt;
		
	}


}