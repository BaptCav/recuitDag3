
public class Mutation {
	
	
	// Mutations regroupées dans une classe pour plus de fluidité
	//Echange deux élements d'une route. Les entrées sont les indices correspondants aux noeuds
		
	
		public void swap(Routage2 routage,int i, int j){
			Collections.swap(routage.route,i,j);
		}
		//une fois les getters setters crÃ©es on attaque les opÃ©rations sur les routes

		
		//Mutation : on swape seulement 2 noeuds au hasard
		public void swapRandom(Routage2 routage) {
			int randIndex1=0;
			int randIndex2=0;
			while (randIndex1==randIndex2){
			randIndex1 = (int) (tailleRoute() * Math.random());
			randIndex2 = (int) (tailleRoute() * Math.random());
			}
			swap(routage,randIndex1,randIndex2);
		}
		
	
	
		public void twoOptMove(Routage2 routage) {
			int randIndex1 = 0;//Indice du noeud c2
			int randIndex2 = 0;//Indice du noeud c1'
			int i;
			int j;
			//  On recalcule les indices de c2 et c1' jusqu'à ce que c2 soit différent de c1'.Notons que le cas c2=c1' ne change pas la route.
			while (randIndex1 == randIndex2){
				randIndex1 = (int) (tailleRoute() * Math.random()); //c1'
				randIndex2 = (int) (tailleRoute() * Math.random()); //c2
			}
			i = randIndex1;
			j = randIndex2;
			//On itère ensuite pour effectuer tous les échanges du txoOptMove
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
	public void threeOptMove(Routage2 routage){
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
	public void totalRandom(Routage2 routage){
		routage.route = routeInitiale();
	}
}
