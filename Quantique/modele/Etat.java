package modele;

import parametrage.EnergiePotentielle;

public class Etat {
	
	EnergiePotentielle epot= new EnergiePotentielle(0);
	
	public EnergiePotentielle getE(){
		return this.epot;
	}
	

}
