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
	
	public ArrayList<Integer> greedyRoute(int[][] matMin) {
		int n = g.nombreDeNoeuds();
		int prochainCandidat =0;
		boolean noeudValide =false;
		ArrayList<Integer> liste = new ArrayList<Integer>();
		int colonneCandidate = 1;
		liste.add(matMin[(int)((n)*Math.random())][1]); //Le premier point visité est le voisin le plus proche du point indexé 0	
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
					l = -1; // car la boucle la réincrémente automatiquement à 0 ! ne pas mettre 0 !
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
				//System.out.println("i = "+i);
			//	System.out.println("prochainCandidat = "+prochainCandidat);
			}
		}
		
		for (int l = 0; l < liste.size(); l++) {
			//System.out.println("greedy route : "+liste.get(l));
		}
		return liste;
	}
	
	public Routage2 (Graphe g, int[][] matMin){
		this.g = g;
		this.route=greedyRoute(matMin);
	}
	
	public Routage2 (Graphe g){
		this.g = g;
		this.route=routeInitiale();
	}
	public Routage2 (Graphe g, ArrayList<Integer> liste){
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
			return this.getRoute().get(this.tailleRoute() - 1);
		} else {
			return this.getRoute().get(index-1);
		}
	}
	// En entrée, un indice correspondant à la place d'un élément sur une route. En sortie, l'indice du noeud précédent(en général, on soustrait 1)
	public int getPreviousIndex(int index){
		if (index==0) {
			return (this.tailleRoute() - 1);
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
