package modele;

import java.util.Collections;
import java.util.List;

import mutation.IMutation;
import mutation.TwoOptMove;

import parametrage.EnergiePotentielle;

public class Etat {
	
<<<<<<< HEAD
	int[][] ising;
=======
	Etat previous;
	Etat next;// nécessaire pour implementer le recuit si l'on considere une chaine fixe
>>>>>>> origin/master
	
	EnergiePotentielle epot= new EnergiePotentielle(0);
	
	public EnergiePotentielle getE(){
		return this.epot;
	}
	
<<<<<<< HEAD
	public void setIsing(int[][] ising){
		this.ising =ising ;
	}
	
	public int[][] getIsing(){
		return this.ising;
	}
	
=======
	public void setNext(Etat e){
		this.next=e;
	}
	public void setPrevious(Etat e){
		this.previous=e;
	}
	
	
>>>>>>> origin/master
}
