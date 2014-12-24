package modele;

import parametrage.NombreEnergie;

public abstract class Etat {

	public abstract NombreEnergie energie();
	
	public abstract Etat clone();
	
	
}
