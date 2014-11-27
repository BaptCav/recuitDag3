import java.util.ArrayList;


public class ListeEnergie extends Energie {

	private boolean movingAverage;
	private ArrayList<Double> deltaEnergie;
	private int fenetre;
	
	public ListeEnergie(boolean movingAverage,ArrayList<Double> deltaEnergie, int fenetre){
		this.movingAverage = movingAverage;
		this.deltaEnergie = deltaEnergie;
		this.fenetre = fenetre;
	}

	public boolean isMovingAverage() {
		return movingAverage;
	}

	public void setMovingAverage(boolean bool) {
		movingAverage = bool;
	}

	public ArrayList<Double> getDeltaE() {
		return deltaEnergie;
	}

	public void setDeltaE(ArrayList<Double> deltaE) {
		deltaEnergie = deltaE;
	}
	
	public void ajoutListe(double e){
		this.deltaEnergie.add(e);
	}
	
	public double donneK(){
	
			double somme = 0;
			int longueurDeltaEnergie = this.tailleDeltaEnergie();
			for(int i = longueurDeltaEnergie; i > longueurDeltaEnergie - this.getFenetre(); i--)
			{
				somme = somme + this.deltaEnergie.get(i-1);

			}
			return somme/(fenetre*fenetre);
		
	}

	public int getFenetre() {
		return fenetre;
	}

	public void setFenetre(int fenetre) {
		this.fenetre = fenetre;
	}
	
	public int tailleDeltaEnergie(){
		return this.deltaEnergie.size();
	}
	
	public boolean doitRenvoyerK(){
		return (this.tailleDeltaEnergie() > this.getFenetre())&&(this.movingAverage);
	}
	
	
	
}
