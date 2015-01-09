package modele;

import java.util.Collections;
import java.util.List;

import mutation.IMutation;
import mutation.TwoOptMove;

import parametrage.EnergiePotentielle;

public class Etat {
	EnergiePotentielle epot= new EnergiePotentielle(0);
	
	public EnergiePotentielle getE(){
		return this.epot;
	}
	
}
