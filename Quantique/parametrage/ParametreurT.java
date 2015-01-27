package parametrage;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import modele.Graphe;
import modele.Probleme;
import modele.Routage;
import mutation.IMutation;
import mutation.TwoOptMove;

public class ParametreurT {
	
	public static List<Double> parametreurRecuit(Graphe g, int nombreIterations){
		int n = g.getdists().length;
		IMutation mutation = new TwoOptMove(n);
		Probleme p = new Probleme();
		Routage r1;
		double deltaE = -1;
		List<Double> l = new LinkedList<Double>();
		for (int i =0; i < 1000; i++){
			deltaE = -1;
			while(deltaE<=0){
		
			r1=new Routage(g);
			mutation = new TwoOptMove(n);
			deltaE=mutation.calculer(p,r1);
			
			}
			l.add((deltaE));
			//On vient de générer une liste de 400 échantillons deltaE du graphe g.
		}
		Collections.sort(l);
		//Ici, on choisit de se baser autour du 5e centile des échantillons générés précédemment.
		//Temperature temp = new Temperature(l.get(30));
		return l;
	}
}
