package parametrage;

public class ParametreGamma {


	private double gamma;
	private double facteur;
	private double gammafin;
	
	
	public ParametreGamma(double temp,double froid,double temperaturefin){
		this.setGamma(temp);
		this.facteur = froid;
		this.gammafin = temperaturefin;
	}
	
// Get et Set de la temperature initiale
	public double getGamma() {
		return this.gamma;
	}

	public void setGamma(double temperature) {
		this.gamma = temperature;
	}
//Get et Set de la temperature de fin	
	public double getGammaFin(){
		return this.gammafin;
	}
	
	public void setGammaFin(double gammafin){
		this.gammafin = gammafin;
	}
//Get et Set du refroidissement, refroidissement de la temperature Recuit en exponentiel et lineaire
	public void refroidissementExp(){
		this.gamma *= 1-this.facteur;
	}
	
	public void refroidissementLin(){
		this.gamma -= this.facteur;
	}

	public double getFacteur() {
		return this.facteur;
	}

	public void setFacteur(double facteurDeRefroidissement) {
		this.facteur = facteurDeRefroidissement;
	}
	
	
}
