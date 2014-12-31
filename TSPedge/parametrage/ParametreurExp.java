package parametrage;
import modele.*;
import mutation.*;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class ParametreurExp extends Parametreur {
	
	public ParametreurExp(Graphe g, int nombreIterations){
		Etat r1 = new Routage(g);
		int n = g.getdists().length;
		IMutation mutation = new TwoOptMove(0,0);
		this.nombreIterations = nombreIterations;
		double deltaE = -1;
		List<Double> l = new LinkedList<Double>();
		for (int i =0; i < 1000; i++){
			deltaE = -1;
			while(deltaE<=0){
			
			r1=new Routage(this.g);
			mutation = new TwoOptMove(n);
			deltaE=mutation.calculer(r1).getEnergie();
			}
			l.add((deltaE));
			//On vient de générer une liste de 400 échantillons deltaE du graphe g.
		}
		Collections.sort(l);
		//Ici, on choisit de se baser autour du dixième du 5e centile des échantillons générés précédemment.
		Temperature debut = new Temperature(5*l.get(100));
		Temperature fin = new Temperature(0.5*l.get(50));
		double r = Math.pow(fin.getValue()/debut.getValue(),1.0/nombreIterations);
		this.temperatureInitiale = new ParametreT(debut , r, fin);
	}
	
	public void refroidir(Temperature temp){
		temp.setValue(temp.getValue()*this.temperatureInitiale.getFacteurDeRefroidissement());
	}
	
	public void refroidir(Etat e, Temperature temp, int compteur){
		this.refroidir(temp);
	}
}
