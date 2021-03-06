package parametrage;


import modele.Etat;
import modele.Graphe;


public abstract class Parametreur {

	public Graphe g;
	public int nombreIterations;
	public ParametreT temperatureInitiale;
	double c;//Pour ParametreurLog uniquement
	double alpha;//Pour ParametreurLog uniquement

	public ParametreT getInitialTemperature(){
		return this.temperatureInitiale;
	}
	public abstract void refroidir(Etat e, Temperature temperature, int compteur);
	
}
