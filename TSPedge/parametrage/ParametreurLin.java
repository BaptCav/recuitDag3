package parametrage;
import modele.*;
import mutation.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class ParametreurLin extends Parametreur {
	
	//public Graphe g;//Le parametreur est universel et prend en compte les propri�t�s de chaque graphe.
	//public int nombreIterations;
	//public ParametreT temperatureInitiale;
	
	public ParametreurLin(Graphe g, int nombreIterations){
		this.g = g;
		this.nombreIterations = nombreIterations;
		Routage r1;
		double deltaE = -1;
		List<Double> l = new LinkedList<Double>();
		for (int i =0; i < 1000; i++){
			deltaE = -1;
			while(deltaE<=0){
				
			
			r1=new Routage(this.g);
			int n = r1.tailleRoute();
			int randIndex1 = 0;//Indice du noeud c2
			int randIndex2 = 0;//Indice du noeud c1'
			int k;
			int j;
			//  On recalcule les indices de c2 et c1' jusqu'à ce que c2 soit différent de c1'.Notons que le cas c2=c1' ne change pas la route.
			while (randIndex1 == randIndex2){
				randIndex1 = (int) (n * Math.random()); //c1'
				randIndex2 = (int) (n * Math.random()); //c2'
			}
			k = randIndex1;
			j = randIndex2;
			deltaE=TwoOptMove.calculer(r1,k,j);
			}
			l.add((deltaE));
			//On vient de g�n�rer une liste de 400 �chantillons deltaE du graphe g.
		}
		Collections.sort(l);
		//Ici, on choisit de se baser autour du dixi�me du 5e centile des �chantillons g�n�r�s pr�c�demment.
		double debut = 0.2*l.get(50);// 20 car 20/400 = 0.05
		double fin = 0.05*l.get(50); 
		double r = (debut-fin)/this.nombreIterations;
		this.temperatureInitiale = new ParametreT(debut , r, fin);
	}
	
	public void refroidir(ParametreT temp){
		temp.setTemperature(temp.getTemperature()-this.temperatureInitiale.getFacteurDeRefroidissement());
	}
}