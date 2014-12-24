package parametrage;

public class Temperature {
	
	//Une temperature est directement definie par le double qu'on lui assigne
	double temperature;
	
	public Temperature(double temperature){
		this.temperature=temperature;
	}
	
	public double getValue(){
		return this.temperature;
	}
	
	public void setValue(double value){
		this.temperature = value;
	}

}
