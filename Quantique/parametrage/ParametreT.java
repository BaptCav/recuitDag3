package parametrage;

public class ParametreT {

	private double temperature;
	private double facteurDeRefroidissement;
	
	public ParametreT(double temp,double froid){
		this.setTemperature(temp);
		this.facteurDeRefroidissement = froid;
	}

	public double getTemperature() {
		return this.temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	public void refroidissement(){
		this.temperature *= 1-this.facteurDeRefroidissement;
	}

	public double getFacteurDeRefroidissement() {
		return this.facteurDeRefroidissement;
	}

	public void setFacteurDeRefroidissement(double facteurDeRefroidissement) {
		this.facteurDeRefroidissement = facteurDeRefroidissement;
	}
	
	
}
