package modele;

import java.util.Collections;
import java.util.List;

import mutation.IMutation;
import mutation.TwoOptMove;

import parametrage.EnergiePotentielle;

public class Etat {
	
	Etat previous;
	Etat next;
	
	int[][] ising;
	
	EnergiePotentielle epot= new EnergiePotentielle(0);
	
	public EnergiePotentielle getE(){
		return this.epot;
	}
	
	public void setIsing(int[][] ising){
		this.ising =ising ;
	}
	
	public int[][] getIsing(){
		return this.ising;
	}

	public void setprevious(Etat etat) {
		this.previous=etat;
		
	}

	public void setnext(Etat etat) {
		this.next=etat;		
	}
	
	
	public Etat getNext(){
		return this.next;
	}
	public Etat getPrevious(){
		return this.previous;
	}
	
}
