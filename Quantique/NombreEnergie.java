
public class NombreEnergie extends Energie{

	private double nombreEnergie;

	public NombreEnergie(double e){
		this.nombreEnergie = e;
	}
	public double getEnergie() {
		return nombreEnergie;
	}

	public void setEnergie(double nombreEnergie) {
		this.nombreEnergie = nombreEnergie;
	}
	
	public void ajoutListe(double abs){
		System.out.println("erreur : ajoutListe sur nombreEnergie");
	}
	
	public boolean doitRenvoyerK(){
		System.out.println("erreur : doitRenvoyerK sur nombreEnergie");
		return false;
	}

	public double donneK(){
		System.out.println("erreur : donneK sur nombreEnergie");
		return 0;
	}
	
}
