package parametrage;

public class Ponderation {
	ParametreGamma gamma;
	public Ponderation(ParametreGamma gamma){
		this.gamma=gamma;
	}
	public double calcul(Temperature temperature, int p) {
		double t = temperature.getValue();
		return - t/2*Math.log(Math.tanh(this.gamma.getGamma()/(p*t))) ;
	}

}
