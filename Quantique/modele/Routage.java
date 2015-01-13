package modele;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import mutation.IMutation;
import mutation.TwoOptMove;

import parametrage.EnergiePotentielleTsp;
public class Routage extends Etat {
	
	EnergiePotentielleTsp epot= new EnergiePotentielleTsp(0);
	Routage previous;
	Routage next;
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
	
	
	//Actualise la listeVoisins et la renvoie
		public List<Double> buildListeVoisins(int longueurListeVoisins){
			int n = this.g.getdists().length;
			IMutation mutation = new TwoOptMove(n);
			Probleme p = new Probleme();
			double deltaE = -1;
			List<Double> l = new LinkedList<Double>();
			for (int i =0; i < longueurListeVoisins; i++){
				deltaE = -1;
				while(deltaE<=0){
			
				mutation = new TwoOptMove(n);
				deltaE=mutation.calculer(p,this);
				
				}
				l.add(deltaE);
			}
			Collections.sort(l);
			return l;
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
		ArrayList<Integer> r= this.getRoute();
		int next;
		int[][] m = new int[n][n];
		for(int i =0; i<n;i++){
			next = r.get(getNextIndex(i));
			if (i<next){
				m[i][next]=1;
			} else {
				m[next][i]=1;
			}
		}
		for (int k=0; k<n-1; k++){
			for (int l=k+1; l<n; l++){
				if (m[k][l] != 1) m[k][l] = -1;
			}
		}
		
		return m;

	}
	public void setNext(Routage r)
	{ this.next = r;
		
	}
	public void setPrevious(Routage r)
	{ this.previous = r;
		
	}

}
