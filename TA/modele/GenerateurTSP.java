package modele;

import java.util.ArrayList;
import java.util.Collections;

//Constructeur de graphes aléatoires si on veut faire des experiences en fonction du nombre de noeuds
public class GenerateurTSP {
	
	
	//Construit un graphe aléatoire avec comme distance minimale AreteMin*nombreNoeud.
	//La distribution des énergies a un ecart-type ecartE
	public static Graphe genereGraphe(int nombreNoeuds,double AreteMin, double ecartE){
		double[][] dists = new double[nombreNoeuds][nombreNoeuds];
		
		ArrayList<Integer> best = new ArrayList<Integer>();
		for (int index = 0; index < nombreNoeuds; index++) {
			best.add(new Integer(index));
		}
		Collections.shuffle(best);
		
		int current = 0;
		int next = 1;
		for (int i = 0;i<nombreNoeuds-1;i++){
			dists[best.get(current)][best.get(next)] = AreteMin;
			dists[best.get(next)][best.get(current)] = AreteMin;
			current++;
			next++;
		}
		dists[best.get(current)][best.get(0)] = AreteMin;
		dists[best.get(0)][best.get(current)] = AreteMin;
		
		double value = 0;
		for (int i = 0; i < nombreNoeuds; i++){
			for(int j = i+1; j < nombreNoeuds;j++){
				
				value = AreteMin + 3.4641016*ecartE*Math.random()/nombreNoeuds;
				if (dists[j][i] != AreteMin){
				dists[j][i] = value;
				dists[i][j] = value;
				//3.4641016 correspond à racine de 12 qui est l'inverse de l'ecart-type de Math.random()
				}
			}	
		}
		return new Graphe(dists);
	}

}
