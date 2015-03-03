package modele;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import mutation.IMutation;
import mutation.TwoOptMove;
import parametrage.EnergiePotentielle;

public class Etat {
	
	Etat previous;
	Etat next;
	
	
	
	EnergiePotentielle epot;
	/**
	 * Un etat a par défaut une matrice d'ising et ses voisins definis a NULL, il doit etre modifié par l'utilisateur.
	 * L'utilisateur se devra d'instancier Etat en une classe correspondant à son probleme.
	 * 
	 */
	public Etat(){
		
	}
	public Etat(EnergiePotentielle epot){
		this.epot=epot;
	}
	
	public EnergiePotentielle getE(){
		return this.epot;
	}
	

	public void setprevious(Etat etat) {
		this.previous=etat;
		
	}

	public void setnext(Etat etat) {
		this.next=etat;		
	}
	
	public Etat clone(){
	Etat e = new Etat(this.epot);
	return e;
	}
	public Etat getNext(){
		return this.next;
	}
	public Etat getPrevious(){
		return this.previous;
	}
	public double getEnergie(){
		return this.epot.calculer(this);
	}
	public int distanceIsing(Etat e){
		/**
		 * L'utilisateur devra implementer la methode appropriée a son "type" d'etat
		 * retourne 0 par défaut
		 */
		
		return 0;
	}
	
	public List<Double> buildListeVoisins(Probleme p,IMutation m,int longueurListeVoisins){

		double deltaE = -1;
		List<Double> l = new LinkedList<Double>();
		for (int i =0; i < longueurListeVoisins; i++){
			deltaE = -1;
			while(deltaE<=0){
				m.maj();
				deltaE=m.calculer(p,this);

			}
			l.add(deltaE);
		}
		Collections.sort(l);
		return l;
	}
}
