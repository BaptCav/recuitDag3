package modele;

import java.util.List;

import parametrage.NombreEnergie;

public abstract class Etat {
	public List<Double> listeVoisins;//Liste de deltaE produits par mutation

	public abstract NombreEnergie energie();
	
	public abstract Etat clone();
	
}
