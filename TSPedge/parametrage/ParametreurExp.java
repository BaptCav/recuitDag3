package parametrage;

import modele.*;
import mutation.*;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class ParametreurExp {
	
	public Graphe g;//Le parametreur est universel et prend en compte les propriétés de chaque graphe.
	public int nombreIterations;
	
	public ParametreurExp(Graphe g, int nombreIterations){
		this.g = g;
		this.nombreIterations = nombreIterations;
	}
	
	
	//On calcule ici les paramètres T. K est fixé à 1 dans Recuit.
	public ParametreT parametrageT(){
		Routage r1;
		double deltaE = -1;
		List<Double> l = new LinkedList<Double>();
		for (int i =0; i < 400; i++){
			deltaE = -1;
			while(deltaE<=0){
				
			
			r1=new Routage(this.g);
			int n = r1.tailleRoute();
			int randIndex1 = 0;//Indice du noeud c2
			int randIndex2 = 0;//Indice du noeud c1'
			int k;
			int j;
			//  On recalcule les indices de c2 et c1' jusqu'Ã  ce que c2 soit diffÃ©rent de c1'.Notons que le cas c2=c1' ne change pas la route.
			while (randIndex1 == randIndex2){
				randIndex1 = (int) (n * Math.random()); //c1'
				randIndex2 = (int) (n * Math.random()); //c2
			}
			k = randIndex1;
			j = randIndex2;
			deltaE=TwoOptMove.calculer(r1,k,j);
			}
			l.add(deltaE);
			//On vient de générer une liste de 400 échantillons deltaE du graphe g.
		}
		Collections.sort(l);
		//Ici, on choisit de se baser autour du dixième du 5e centile des échantillons générés précédemment.
		double debut = l.get(20);// 20 car 20/400 = 0.05
		//La temperature de debut sera le 5e centile.
		double fin = 0.1*l.get(20);
		//La temperature de fin sera 10 fois plus petite que le 5e centile
		double r = Math.pow(fin/debut,1.0/this.nombreIterations);
		// On calcule r de façon à ce que le nombre d'itérations voulu soit respecté.
		ParametreT temperature = new ParametreT(debut , 1.0-r, fin);
		return temperature;
	}
}