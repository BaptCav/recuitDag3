package parametrage;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import modele.Etat;
import modele.Graphe;
import modele.Routage;
import mutation.IMutation;
import mutation.TwoOptMove;

public class ParametreurLog extends Parametreur{
	
	public ParametreurLog(Graphe g, int nombreIterations){
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
			deltaE=mutation.calculer(r1).getEnergie();
			}
			l.add((deltaE));
			//On vient de générer une liste de 400 échantillons deltaE du graphe g.
		}
		Collections.sort(l);
		//Ici, on choisit de se baser autour du dixième du 5e centile des échantillons générés précédemment.
		Temperature debut = new Temperature(10.0*l.get(50));// 20 car 20/400 = 0.05
		Temperature fin = new Temperature(l.get(50));
		double r = Math.pow(fin.getValue()/debut.getValue(),1.0/nombreIterations);
		this.temperatureInitiale = new ParametreT(debut , r, fin);
		double x = Math.pow(nombreIterations+2,fin.getValue());
		double y = Math.pow(2,debut.getValue());
		this.alpha = Math.pow((x/y),1.0/(debut.getValue()-fin.getValue()));
		this.c = debut.getValue() * Math.log(2*this.alpha);
		
	}
	
	//On refroidit de manière à ce que la temperature suive la relation : Tn = c / log(alpha*n)
	public void refroidir(Etat e, Temperature temp, int compteur){
		temp.setValue(this.c/Math.log(this.alpha*(compteur+2)));
	}

}
