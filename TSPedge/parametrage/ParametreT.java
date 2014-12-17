package parametrage;
public class ParametreT {
	
	//ParametreT représente un triplet (debut,refroidissement,fin) permettant de paramétrer le recuit. A ne pas  confondre avec la temperature.

	private Temperature temperaturedebut;
	private double facteurDeRefroidissement;
	private Temperature temperaturefin;
	
	public ParametreT(Temperature temp,double froid,Temperature temperaturefin){
		this.temperaturedebut = temp;
		this.facteurDeRefroidissement = froid;
		this.temperaturefin = temperaturefin;
	}
// Get et Set de la temperature initiale
	public Temperature getTemperatureDebut() {
		return this.temperaturedebut;
	}

	public void setTemperatureDebut(Temperature temperature) {
		this.temperaturedebut = temperature;
	}
//Get et Set de la temperature de fin	
	public Temperature getTemperatureFin(){
		return this.temperaturefin;
	}
	
	public void setTemperatureFin(Temperature temperaturefin){
		this.temperaturefin = temperaturefin;
	}
//Get et Set du refroidissement
	/*public void refroidissementExp(){
		this.temperature *= 1-this.facteurDeRefroidissement;
	}
	
	public void refroidissementLin(){
		this.temperature -= this.facteurDeRefroidissement;
	}*/

	public double getFacteurDeRefroidissement() {
		return this.facteurDeRefroidissement;
	}

	public void setFacteurDeRefroidissement(double facteurDeRefroidissement) {
		this.facteurDeRefroidissement = facteurDeRefroidissement;
	}
	
	
}