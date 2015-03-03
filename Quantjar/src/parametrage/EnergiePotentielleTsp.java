package parametrage;

import modele.Routage;

public class EnergiePotentielleTsp extends EnergiePotentielle {
	public static double calculer(Routage r) {
		
		return r.getEnergie();
	}

}
