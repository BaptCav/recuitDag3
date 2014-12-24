package modele;
import java.util.ArrayList;
import java.util.Collections;

import parametrage.NombreEnergie;
public class Routage extends Etat {
	ArrayList<Noeud> route ;
	Graphe g;
	
	public Routage(ArrayList<Noeud> l){
		this.route=l;
	}
	
	public ArrayList<Noeud> getRoute(){
		return this.route;
	}
	
	public int tailleRoute() {
		return this.route.size();
	}
	
	public void setRoute(ArrayList<Noeud> l) {
		this.route=l;
		
	}
	
	public Routage (Graphe g){
		this.g = g;
		this.route=routeInitiale();
	
	}

	public  Routage (Graphe g, ArrayList<Noeud> liste){
		this.g=g;
		this.route=liste;
	}
	
	public ArrayList<Noeud> routeInitiale() {
		int n = this.g.nombreDeNoeuds();
		ArrayList<Noeud> liste = new ArrayList<Noeud>();
		for (int index = 0; index < n; index++) {
			liste.add(new Noeud(index));
		}
		// Réorganise aléatoirement l'ordre de visite
		Collections.shuffle(liste);	
		return liste;
		
	}
	
	public Routage clone()
	{
		Routage clone= new Routage(this.g);
		int n = this.g.nombreDeNoeuds();
		for (int index = 0; index < n; index++){
			clone.getRoute().set(index, this.route.get(index));
		}
		return clone;
	}
	
	public Graphe getGraphe(){
		return this.g;
	}
  
	// Les deux methodes suivantes servent � aller chercher le noeud suivant/precedent dans la route que l'on traite. 
	// Elles prennent en compte le rebouclage.
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
				s += this.route.get(index).getIndex() + "->";
			}
			return s;
		}
	
	public double getDistance(){
		double cpt=0.0;
		int j=0;
		int L = this.tailleRoute();
		for(int i=0;i<L;i++){
			j=this.getNextIndex(i);
			cpt+=this.getGraphe().getdists()[this.getRoute().get(i).getIndex()][this.getRoute().get(j).getIndex()];
			
		}
		return cpt;
		
	}
	
	// L'appel de la methode energie engendre necessairement le calcul complet de la distance de la route.
	public NombreEnergie energie(){
		return new NombreEnergie(this.getDistance());
	}


}