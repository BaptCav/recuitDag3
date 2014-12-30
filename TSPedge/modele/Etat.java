package modele;

import java.util.Collections;
import java.util.List;

import mutation.IMutation;
import mutation.TwoOptMove;
import parametrage.NombreEnergie;

public abstract class Etat {
	public List<Double> listeVoisins;//Liste de deltaE produits par mutation

	public abstract NombreEnergie energie();
	
	public abstract Etat clone();
	
	//Cette fonction prend la listeVoisins et remplace un nombre indicesChanges de ses coefficients
	//Pendant le recuit, elle actualise la listeVoisins à chaque route.
	//Dans l'absolu, on devrait prendre indicesChanges = listeVoisins.size() mais cela prend du temps.
	//Pour brg180, modifier seulement un 10e des deltaE suffit pour obtenir des résultats cohérents.
	public void remplacer(Graphe g,int indicesChanges){
		Routage r = (Routage) this;
		int n = r.getGraphe().getdists().length;
		int longueur = this.listeVoisins.size();
		int k;
		for (int i =0; i< indicesChanges; i++){
			k = (int) ((longueur-i)*Math.random());
			this.listeVoisins.remove(k);
		}
		//On a enlevé au hasard un certain nombre de deltaE. Maintenant, il faut en rajouter 10 créés à partir de la nouvelle route.
		
		IMutation mutation = new TwoOptMove(0,0);
		double deltaE = -1;
		for (int i =0; i < indicesChanges; i++){
			deltaE = -1;
			while(deltaE<=0){
		
			mutation = new TwoOptMove(n);
			deltaE=mutation.calculer(this).getEnergie();
			
			}
			this.listeVoisins.add((deltaE));
		}
		Collections.sort(this.listeVoisins);
	}
}
