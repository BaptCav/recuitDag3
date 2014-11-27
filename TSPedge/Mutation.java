import java.util.Collections;
import java.util.ArrayList;

public class Mutation {
	
	
	// Mutations regroupées dans une classe pour plus de fluidité
	//Echange deux élements d'une route. Les entrées sont les indices correspondants aux noeuds
		public Routage routage;
	
		public static void swap(Routage routage,int i, int j){
			Collections.swap(routage.route,i,j);
		}
		
		//Mutation : on swape seulement 2 noeuds au hasard
		public void swapRandom(Routage routage) {
			int n = routage.tailleRoute();
			int randIndex1=0;
			int randIndex2=0;
			while (randIndex1==randIndex2){
			randIndex1 = (int) (n * Math.random());
			randIndex2 = (int) (n * Math.random());
			}
			swap(routage,randIndex1,randIndex2);
		}
		
	
	
		public static void twoOptMove(Routage routage) {
			int n = routage.tailleRoute();
			int randIndex1 = 0;//Indice du noeud c2
			int randIndex2 = 0;//Indice du noeud c1'
			int i;
			int j;
			//  On recalcule les indices de c2 et c1' jusqu'à ce que c2 soit différent de c1'.Notons que le cas c2=c1' ne change pas la route.
			while (randIndex1 == randIndex2){
				randIndex1 = (int) (n * Math.random()); //c1'
				randIndex2 = (int) (n * Math.random()); //c2
			}
			i = randIndex1;
			j = randIndex2;
			//On itère ensuite pour effectuer tous les échanges du twoOptMove
			while (i!=j && routage.getNextIndex(i)!=j ) {
				swap(routage,i,j);
				i=routage.getNextIndex(i);
				j=routage.getPreviousIndex(j);
			}
		}

	//Fonction nécessaire pour threeOptMove : elle réordonne trois nombres
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
	// Attention ! Ici, le threeOptMove n'est valable que pour des graphes symétriques !
	public void threeOptMove(Routage routage){
		int n = routage.tailleRoute();
		int i = 0;
		int j = 0;
		int k = 0;
		ArrayList<Integer> l = new ArrayList<Integer>();
		while (j==k || i==k || i==j){
			i = (int) (n * Math.random()); 
			j = (int) (n * Math.random()); 
			k = (int) (n * Math.random());
		}
		int[] tab = order(i,j,k);
		int step1=tab[0];
		int step2=tab[1];
		int step3=tab[2];
		int index = step2;
		while(index!=step3){
			l.add(routage.getRoute().get(index));
			index=routage.getNextIndex(index);
		}
		index=step1;
		while(index!=step2){
			l.add(routage.getRoute().get(index));
			index=routage.getNextIndex(index);
		}
		index=step3;
		while(index!=step1){
			l.add(routage.getRoute().get(index));
			index=routage.getNextIndex(index);
		}
		routage.route = l;
	}

	//La mutation consiste à aller aléatoirement vers une autre route
	public void totalRandom(Routage routage){
		routage.route = routage.routeInitiale();
	}
	//Mutation équivalente à la mutation PS3 de l'article "Mutation différentes pour TSP euclidien symétrique"
		/*public void changePlace() {
			int n = routage.tailleRoute();
			int randIndex1=0;
			int randIndex2=0;
			while (randIndex1==randIndex2){
			randIndex1 = (int) (n * Math.random()); //Indice de l'élément à bouger
			randIndex2 = (int) (n * Math.random()); //Indice de l'endroit ou on le dépose
			}
			int i = randIndex1;
			while (i!=randIndex2){
				routage.swap(i,randIndex2);
				i=routage.getNextIndex(i);
			}
		}*/
		
		//Mutation équivalente à la mutation PS4 de l'article "Mutation différentes pour TSP euclidien symétrique"
		public void moveSequence() {
			int n = routage.tailleRoute();
			int debutSequence = 0;//indice de d�but de la s�quence
			int longueur = 0;//longueur de la s�quence
			int decalage = 0;
			while (longueur==0 || decalage==0 || longueur+decalage>=n){
				debutSequence = (int) (n * Math.random());
				longueur = (int) ((n-1) * Math.random());
				decalage = (int) (n * Math.random());
			}
			//Exemple avec la route : 0->1->2->3->4->5->6 (les entiers representent les emplacements initiaux de chaque noeud dans la route)
			//Ne pas oublier de voir la route comme un ensemble circulaire (il y a rebouclage du dernier �l�ment sur le dernier)
					//n=7
					//debutSequence=5
					//longueurSequence=4 (la s�quence est donc 5->6->0->1)
					//decalage=2
					//On veut donc obtenir l'algorithme passant de 5->6->0->1->2->3->4 � 2->3->5->6->0->1->4
			//On va chercher l'indice de fin de la s�quence
			int j = debutSequence;
			int cpt = longueur-1;//on stocke dans cpt tous les compteurs de l'algorithme
			while(cpt>0){
				j=routage.getNextIndex(j);
				cpt -=1;
			}
			int finSequence = j;
			//Dans l'exemple, on a finSequence=1
			//On rentre les elements decales situes apres sequence. Dans l'exemple, on rentre 2->3
			ArrayList<Integer> l = new ArrayList<Integer>();
			cpt=decalage;
			j=routage.getNextIndex(j);
			while (cpt>0){
				l.add(routage.getRoute().get(j));
				j=routage.getNextIndex(j);
				cpt -=1;
			}
			//On rentre les elements de la sequence. Dans l'exemple, on obtient : 2->3->5->6->0->1
			int i =debutSequence;
			while(i!=routage.getNextIndex(finSequence)){
				l.add(routage.getRoute().get(i));
				i=routage.getNextIndex(i);
			}
			//On ajoute les elements restants, ici 4
			while(j!=debutSequence){
				l.add(routage.getRoute().get(j));
				j=routage.getNextIndex(j);
			}
			routage.route = l;
		}
		//Mutation equivalente sur la mutation PS6 de l'article "Mutation differentes pour TSP euclidien symetrique"
		public void moveReverse(Routage routage) {
			//Le code est principalement le meme que celui de PS4.
			//On cr�e une variable reverse valant 0 ou 1 determinant si on inverse ou non la s�quence
			int n = routage.tailleRoute();
			int debutSequence = 0;//indice de debut de la s�quence
			int longueur = 0;//longueur de la sequence
			int decalage = 0;
			int reverse = (int) (2*Math.random());
			while (longueur==0 || decalage==0 || longueur+decalage>=n){
				debutSequence = (int) (n * Math.random());
				longueur = (int) ((n-1) * Math.random());
				decalage = (int) (n * Math.random());
			}
			int j = debutSequence;
			int cpt = longueur-1;//on stocke dans cpt tous les compteurs de l'algorithme
			while(cpt>0){
				j=routage.getNextIndex(j);
				cpt -=1;
			}
			int finSequence = j;
			ArrayList<Integer> l = new ArrayList<Integer>();
			cpt=decalage;
			j=routage.getNextIndex(j);
			while (cpt>0){
				l.add(routage.getRoute().get(j));
				j=routage.getNextIndex(j);
				cpt -=1;
			}
			//On tient compte ici de reverse
			if (reverse==0){
				int i =debutSequence;
				while(i!=routage.getNextIndex(finSequence)){
					l.add(routage.getRoute().get(i));
					i=routage.getNextIndex(i);
				}
			} else {
				int i = finSequence;
				while(i!=routage.getPreviousIndex(debutSequence)){
					l.add(routage.getRoute().get(i));
					i=routage.getPreviousIndex(i);
				}
			}
			//On ajoute les elements restants, ici 4
			while(j!=debutSequence){
				l.add(routage.getRoute().get(j));
				j=routage.getNextIndex(j);
			}
			routage.route = l;
		}
}
