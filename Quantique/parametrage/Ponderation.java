package parametrage;

public class Ponderation {
	ParametreGamma gamma;
	
	public double calcul(ParametreT temperature, int p) {
		double t = temperature.getTemperature();
		return - t/2*Math.log(Math.tanh(this.gamma.getGamma()/(p*t))) ;
	}

}
