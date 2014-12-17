package parametrage;

public class Temperature {
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
