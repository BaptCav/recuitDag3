import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class ParametreurLin {
	
	public Graphe g;//Le parametreur est universel et prend en compte les propriétés de chaque graphe.
	public int nombreIterations;
	
	public ParametreurLin(Graphe g, int nombreIterations){
		this.g = g;
		this.nombreIterations = nombreIterations;
	}
	
	
	//On calcule ici les paramètres T. K est fixé à 1 dans Recuit.
	public ParametreT parametrageT(){
		Routage r1;
		Routage r2;
		double deltaE = -1;
		List<Double> l = new LinkedList<Double>();
		for (int i =0; i < 400; i++){
			deltaE = -1;
			while(deltaE<=0){
			r1=new Routage(g);
			r2=new Routage(g);
			r2.clone(r1);
			Mutation.twoOptMove(r1);
			deltaE=Math.abs(r1.getDistance()-r2.getDistance());
			}
			l.add(deltaE);
			//On vient de générer une liste de 400 échantillons deltaE du graphe g.
		}
		Collections.sort(l);
		//Ici, on choisit de se baser autour du dixième du 5e centile des échantillons générés précédemment.
		double debut = 0.2*l.get(20);// 20 car 20/400 = 0.05
		//La temperature de debut sera le 5e centile.
		double fin = 0.0;
		//La temperature de fin sera 0
		double r = (debut-fin)/nombreIterations;
		// On calcule r de façon à ce que le nombre d'itérations voulu soit respecté.
		ParametreT temperature = new ParametreT(debut , r, fin);
		return temperature;
	}
}
