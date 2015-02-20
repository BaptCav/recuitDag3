import java.util.ArrayList;
import java.util.Random;


public class Particule {
	private ArrayList<Etat> etats = new ArrayList<Etat>();

	public Particule(int nbreEtats){
		Random seed = new Random();  
		for( int i =0;i< nbreEtats; i++){ 
			int randomX =  seed.nextInt(ZoneDessin.widthImg);
			int randomY =  seed.nextInt(ZoneDessin.heightImg);
			Etat etat = new Etat(randomX,randomY);
			etats.add(etat);
		} 
	}
	
	public Particule clone(Particule modele){
		Particule clone = new Particule(modele.getEtats().size());
		for(int i=0; i<modele.getEtats().size();i++)
		{
			clone.getEtats().set(i,modele.getEtats().get(i));
		}
		return clone;
	}
	
	public double distanceToNext(int index){
		int coordX1 = etats.get(index).getCoordonneesX();
		int coordY1 = etats.get(index).getCoordonneesY();
		int coordX2=0;
		int coordY2=0;
		if(index == etats.size() - 1){
			coordX2 = etats.get(0).getCoordonneesX();
			coordY2 = etats.get(0).getCoordonneesY();
		}
		else{
			coordX2 = etats.get(index+1).getCoordonneesX();
			coordY2 = etats.get(index+1).getCoordonneesY();
		}
		double distance = Math.sqrt(Math.pow(coordX2-coordX1,2)+Math.pow(coordY2-coordY1,2));
		return distance;
	}
	
	public double distanceToPrevious(int index){
		int coordX1 = etats.get(index).getCoordonneesX();
		int coordY1 = etats.get(index).getCoordonneesY();
		int coordX2=0;
		int coordY2=0;
		if(index == 0){
			coordX2 = etats.get(etats.size()-1).getCoordonneesX();
			coordY2 = etats.get(etats.size()-1).getCoordonneesY();
		}
		else{
			coordX2 = etats.get(index-1).getCoordonneesX();
			coordY2 = etats.get(index-1).getCoordonneesY();
		}
		double distance = Math.sqrt(Math.pow(coordX2-coordX1,2)+Math.pow(coordY2-coordY1,2));
		return distance;
	}
	
	public double sumDistance(int index){
		int coordX1 = etats.get(index).getCoordonneesX();
		int coordY1 = etats.get(index).getCoordonneesY();
		double sum = 0;
		for(int i=0; i<etats.size();i++)
		{
			sum += Math.sqrt(Math.pow(etats.get(i).getCoordonneesX()-coordX1,2)+Math.pow(etats.get(i).getCoordonneesY()-coordY1,2));
		}
		return sum;
	}
	
	public double distanceToBest(int[][] matrice, int index){
		int coordX1 = findBest(matrice).getCoordonneesX();
		int coordY1 = findBest(matrice).getCoordonneesY();
		double distance = Math.sqrt(Math.pow(etats.get(index).getCoordonneesX()-coordX1,2)+Math.pow(etats.get(index).getCoordonneesY()-coordY1,2));
		return distance;
	}
	
	public Etat findBest(int[][] matrice){
		int[] tableau = new int[this.getEtats().size()];
		for(int i=0; i<this.getEtats().size();i++)
		{
			tableau[i] = matrice[this.getEtats().get(i).getCoordonneesX()][this.getEtats().get(i).getCoordonneesY()];
		}
		int indexMin = smallestIndex(tableau);
		//System.out.println("The indexMin is: "+ indexMin);
		return this.getEtats().get(indexMin);
	}
	
	public static int smallestIndex (int[] array) {
		int currentValue = array[0];
		int smallestIndex = 0;
		for (int j=1; j < array.length; j++) {
			if (array[j] < currentValue)
			{
				currentValue = array[j];
				smallestIndex = j;
			}
		}
		return smallestIndex;
	}
	
	public double GetEp()
	{
		double energiePotentielle = 0;
		for(int i=0; i<this.getEtats().size();i++)
		{
			energiePotentielle += this.getEtats().get(i).getEnergiePotentielle();
		}
		energiePotentielle = energiePotentielle/(getEtats().size());
		return energiePotentielle;
	}
	
	public double GetEc()
	{
		double energieCinetique = 0;
		for(int i=0; i<this.getEtats().size();i++)
		{
			energieCinetique += this.getEtats().get(i).getEnergieCinetique();
		}
		energieCinetique = energieCinetique/(getEtats().size());
		return energieCinetique;
	}
	
	public ArrayList<Etat> getEtats()
	{
		return etats;
	}
}
