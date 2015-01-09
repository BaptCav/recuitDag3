package parametrage;
import java.util.List;

import modele.Etat;
import modele.Graphe;

public class ParametreurLocal{
	Graphe g;
	int nombreIterations;
	
	// temperatureinitiale est définie nimporte comment. En fait, cette composante n'a aucune importance pour un parametreur local
	public ParametreurLocal(Graphe g, int nombreIterations){
		this.g=g;
		this.nombreIterations = nombreIterations;
		
	}
//La temperature au temps t ne dépend plus seulement des paramètres initiaux.
	//Elle depend aussi de la listeVoisins du routage.
	public void refroidir(Etat r1, Temperature temperature,int compteur) {
		double r = 2.0/this.nombreIterations;
		List<Double> l = r1.getListeVoisins();
		int index = 5*l.size() / 100;
		//On suit un comportement lineaire autour des l.get(index) qui varient selon la route considérée
		temperature = new Temperature((2.0-compteur*r)*l.get(index));
	}
}

