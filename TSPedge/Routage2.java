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
		// rÃ©organise alÃ©atoirement l'ordre de visite
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
	// En entr�e, un indice correspondant � la place d'un �l�ment sur une route. En sortie, le noeud correspondant � l'indice suivant
	public int getNext(int index){
		if (index==this.tailleRoute()-1) {
			return this.getRoute().get(0);
		} else {
			return this.getRoute().get(index+1);
			}
	}
	//En entr�e, un indice correspondant � la place d'un �l�ment sur une route. En sortie, l'indice correspondant au noeud suivant(dans la plupart des cas, on ajoute simplement 1)
	public int getNextIndex(int index){
		if (index==(this.tailleRoute()-1)) {
			return 0;
		} else {
			return (index+1);
			}
	}
	
	// En entr�e, un indice correspondant � la place d'un �l�ment sur une route. En sortie, le noeud correspondant � l'indice pr�c�dent
	public int getPrevious(int index){
		if (index==0) {
			return  this.getRoute().get(this.tailleRoute() - 1);
		} else {
			return  this.getRoute().get(index-1);
		}
	}
	
	// En entr�e, un indice correspondant � la place d'un �l�ment sur une route. En sortie, l'indice du noeud pr�c�dent(en g�n�ral, on soustrait 1)
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
		
	//Echange deux �lements d'une route. Les entr�es sont les indices correspondants aux noeuds
	public void swap(int i, int j){
		Collections.swap(route,i,j);
	}
	//une fois les getters setters crées on attaque les opérations sur les routes
	//Mutation : on swape seulement 2 noeuds au hasard
	public void swapRandom() {
		int randIndex1=0;
		int randIndex2=0;
		while (randIndex1==randIndex2){
		randIndex1 = (int) (tailleRoute() * Math.random());
		randIndex2 = (int) (tailleRoute() * Math.random());
		}
		this.swap(randIndex1,randIndex2);
	}
	
	
	
// Mutations
	// en theorie une classe mutations devrait etre plus adaptée a notre solution rigide
	
	public void twoOptMove() {
		int randIndex1 = 0;//Indice du noeud c2
		int randIndex2 = 0;//Indice du noeud c1'
		int i;
		int j;
		//  On recalcule les indices de c2 et c1' jusqu'� ce que c2 soit diff�rent de c1'.Notons que le cas c2=c1' ne change pas la route.
		while (randIndex1 == randIndex2){
			randIndex1 = (int) (tailleRoute() * Math.random()); //c1'
			randIndex2 = (int) (tailleRoute() * Math.random()); //c2
		}
		i = randIndex1;
		j = randIndex2;
		//On it�re ensuite pour effectuer tous les �changes du txoOptMove
		while (i!=j && getNextIndex(i)!=j ) {
			swap(i,j);
			i=getNextIndex(i);
			j=getPreviousIndex(j);
		}
	}
	//Fonction n�cessaire pour threeOptMove : elle r�ordonne trois nombres
	public int[] order(int i, int j, int k){
		int[] tab = new int[3];
		if (i<j && i<k){
			tab[0]=i;
			tab[1]=Math.min(j,k);
			tab[2]=Math.max(j,k);
			return tab;
		}
		if (j<i && j<k){
			tab[0]=j;
			tab[1]=Math.min(i,k);
			tab[2]=Math.max(i,k);
			return tab;
		}
		tab[0]=k;
		tab[1]=Math.min(i,j);
		tab[2]=Math.max(i,j);
		return tab;
	}
	// Attention ! Ici, le threeOptMove n'est valable que pour des graphes sym�triques !
	public void threeOptMove(){
		int i = 0;
		int j = 0;
		int k = 0;
		ArrayList<Integer> l = new ArrayList<Integer>();
		while (j==k || i==k || i==j){
			i = (int) (tailleRoute() * Math.random()); 
			j = (int) (tailleRoute() * Math.random()); 
			k = (int) (tailleRoute() * Math.random());
		}
		int[] tab = order(i,j,k);
		int step1=tab[0];
		int step2=tab[1];
		int step3=tab[2];
		int index = step2;
		while(index!=step3){
			l.add(this.getRoute().get(index));
			index=getNextIndex(index);
		}
		index=step1;
		while(index!=step2){
			l.add(this.getRoute().get(index));
			index=getNextIndex(index);
		}
		index=step3;
		while(index!=step1){
			l.add(this.getRoute().get(index));
			index=getNextIndex(index);
		}
		this.route = l;
	}
	//La mutation consiste � aller al�atoirement vers une autre route
	public void totalRandom(){
		this.route = routeInitiale();
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