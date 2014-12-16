package parametrage;

import modele.Graphe;

public abstract class Parametreur {

	public Graphe g;
	public int nombreIterations;
	public ParametreT temperatureInitiale;

	public ParametreT getInitialTemperature(){
		return this.temperatureInitiale;
	}
	public abstract void refroidir(ParametreT temperature);
}
