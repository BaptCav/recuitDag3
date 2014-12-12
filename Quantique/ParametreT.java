

public class ParametreT {

	private double temperature;
	private double facteurDeRefroidissement;
	private double temperaturefin;
	
	public ParametreT(double temp,double froid,double temperaturefin){
		this.setTemperature(temp);
		this.facteurDeRefroidissement = froid;
		this.temperaturefin = temperaturefin;
	}
// Get et Set de la temperature initiale
	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
//Get et Set de la temperature de fin	
	public double getTemperatureFin(){
		return temperaturefin;
	}
	
	public void setTemperatureFin(double temperaturefin){
		this.temperaturefin = temperaturefin;
	}
//Get et Set du refroidissement, refroidissement de la temperature Recuit en exponentiel et lineaire
	public void refroidissementExp(){
		this.temperature *= 1-this.facteurDeRefroidissement;
	}
	
	public void refroidissementLin(){
		this.temperature -= this.facteurDeRefroidissement;
	}

	public double getFacteurDeRefroidissement() {
		return facteurDeRefroidissement;
	}

	public void setFacteurDeRefroidissement(double facteurDeRefroidissement) {
		this.facteurDeRefroidissement = facteurDeRefroidissement;
	}
	
	
}

