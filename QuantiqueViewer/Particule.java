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
	
	public ArrayList<Etat> getEtats()
	{
		return etats;
	}
}
