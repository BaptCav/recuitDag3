package modele;

import java.util.ArrayList;
import java.util.Collections;

import parametrage.EnergiePotentielleTsp;
public class Routage extends Etat {
	
	EnergiePotentielleTsp epot= new EnergiePotentielleTsp(0);
	
	ArrayList<Integer> route ;
	Graphe g;
	public ArrayList<Integer> getRoute(){
		return this.route;
	}
	public int tailleRoute() {
		return this.route.size();
	}
	public Routage (Graphe g){
		this.g = g;
		this.route=routeInitiale();
	}
	public Routage (Graphe g, ArrayList<Integer> liste){
		this.g=g;
		this.route=liste;
	}
	public ArrayList<Integer> routeInitiale() {
		int n = this.g.nombreDeNoeuds();
		ArrayList<Integer> liste = new ArrayList<Integer>();
		for (int index = 0; index < n; index++) {
			liste.add(new Integer(index));
		}
		// Réorganise aléatoirement l'ordre de visite
		Collections.shuffle(liste);
		return liste;
	}
	public Routage clone()
	{
		Routage clone= new Routage(this.g);
		int n = this.g.nombreDeNoeuds();
		ArrayList<Integer> l =clone.getRoute();
		for (int index = 0; index < n; index++){
			l.set(index, this.route.get(index));
		}
		return clone;
	}
	
	public EnergiePotentielleTsp getE(){
		return this.epot;
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
			return (this.tailleRoute() - 1);
		} else {
			return (index-1);
		}
	}
	public String toString() {
		int n = this.tailleRoute();
		String s = "";
		for (int index = 0; index < n; index++){
			s += this.route.get(index).intValue() + "->";
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


	//Rend la representation d'Ising du routage
	
	public int[][] toIsing(){
		int n = this.tailleRoute();
		int[][] m = new int[n][n];
		for(int i =0; i<n;i++){
			for(int j=i; j<n; j++){
				ArrayList<Integer> r= this.getRoute();
				//met des 1 si ils sont voisins
				if(r.get(this.getNextIndex(this.getRoute().lastIndexOf(i)))==j){
					m[i][j]=1;
				}else{
					m[i][j]=-1;
				}

			}
		}
		return m;

	}

}
