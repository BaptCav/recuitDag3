import java.util.ArrayList;
import java.util.Collections;
public class Routage extends Etat {
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
	
	public ArrayList<Integer> greedyRoute(int[][] matMin) {
		int n = g.nombreDeNoeuds();
		int prochainCandidat =0;
		boolean noeudValide =false;
		ArrayList<Integer> liste = new ArrayList<Integer>();
		int colonneCandidate = 1;
		liste.add(matMin[(int)((n)*Math.random())][1]); //Le premier point visite est le voisin le plus proche du point indexÃ© 0	
		for (int i = 1; i < n; i++) 
		{
			prochainCandidat = matMin[liste.get(i-1)][colonneCandidate];
			for(int l = 0; l<liste.size() ;l++)
			{
				if(prochainCandidat == liste.get(l)) // si la prochaine ville est malheureusement deja dans la liste...
				{
					colonneCandidate+=1;
					noeudValide = false;
					prochainCandidat = matMin[liste.get(i-1)][colonneCandidate];
					l = -1; // car la boucle la rÃ©incrÃ©mente automatiquement Ã  0 ! ne pas mettre 0 !
				}
				else
				{
					noeudValide = true;
				}
			}
			if(noeudValide == true)
			{
				liste.add(prochainCandidat);
				noeudValide = false;
				colonneCandidate = 1;
			}
		}
		return liste;
	}
	
	//Rend la representation d'Ising du routage
	 public int[][] toIsing(){
		 int n = this.tailleRoute();
		 int[][] m = new int[n][n];
		 for(int i =0; i<n;i++){
			 for(int j=i; j<n; j++){
			//met des 1 si ils sont voisins
				 if(this.getRoute().get(this.getRoute().lastIndexOf(i)+1)==j){
					 m[i][j]=1;
				 }else{
					 m[i][j]=-1;
				 }
				 
			 }
		 }
		 
		 
		 return m;
		 
	 }

}
