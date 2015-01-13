package modele;

import java.util.Collections;
import java.util.List;

import mutation.IMutation;
import mutation.TwoOptMove;

import parametrage.EnergiePotentielle;

public class Etat {
	
	Etat previous;
	Etat next;// nécessaire pour implementer le recuit si l'on considere une chaine fixe
	
	EnergiePotentielle epot= new EnergiePotentielle(0);
	
	public EnergiePotentielle getE(){
		return this.epot;
	}
	
}
