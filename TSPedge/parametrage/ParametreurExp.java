package parametrage;
import modele.*;
import mutation.*;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class ParametreurExp extends Parametreur {
	
	public ParametreurExp(Graphe g, int nombreIterations){
		this.g = g;
		int n = g.getdists().length;
		IMutation mutation = new TwoOptMove(0,0);
		this.nombreIterations = nombreIterations;
		Routage r1;
		double deltaE = -1;
		List<Double> l = new LinkedList<Double>();
		for (int i =0; i < 1000; i++){
			deltaE = -1;
			while(deltaE<=0){
				
			
			r1=new Routage(this.g);
			mutation = new TwoOptMove(n);
			deltaE=mutation.calculer(r1);
			}
			l.add((deltaE));
			//On vient de g�n�rer une liste de 400 �chantillons deltaE du graphe g.
		}
		Collections.sort(l);
		//Ici, on choisit de se baser autour du dixi�me du 5e centile des �chantillons g�n�r�s pr�c�demment.
		Temperature debut = new Temperature(5*l.get(50));// 20 car 20/400 = 0.05
		Temperature fin = new Temperature(0.5*l.get(50));
		double r = Math.pow(fin.getValue()/debut.getValue(),1.0/nombreIterations);
		this.temperatureInitiale = new ParametreT(debut , r, fin);
	}
	
	public void refroidir(Temperature temp){
		temp.setValue(temp.getValue()*this.temperatureInitiale.getFacteurDeRefroidissement());
	}
	
	public void refroidir(Temperature temp, int compteur){
		this.refroidir(temp);
	}
}