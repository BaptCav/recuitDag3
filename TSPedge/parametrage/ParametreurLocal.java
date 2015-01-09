package parametrage;
import java.util.List;

import modele.Etat;
import modele.Graphe;

public class ParametreurLocal extends Parametreur {
	
	// temperatureinitiale est d�finie nimporte comment. En fait, cette composante n'a aucune importance pour un parametreur local
	public ParametreurLocal(Graphe g, int nombreIterations){
		this.g=g;
		this.nombreIterations = nombreIterations;
		this.temperatureInitiale = new ParametreT(new Temperature(1.0),0.0,new Temperature(0.0));
		
	}
//La temperature au temps t ne d�pend plus seulement des param�tres initiaux.
	//Elle depend aussi de la listeVoisins du routage.
	public void refroidir(Etat r1, Temperature temperature,int compteur) {
		double r = 2.0/nombreIterations;
		List<Double> l = r1.listeVoisins;
		int index = 5*l.size() / 100;
		//On suit un comportement lineaire autour des l.get(index) qui varient selon la route consid�r�e
		temperature = new Temperature((2.0-compteur*r)*l.get(index));
	}
}

