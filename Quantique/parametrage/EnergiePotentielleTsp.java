package parametrage;

import modele.Routage;

public class EnergiePotentielleTsp extends EnergiePotentielle {
	public static double calculer(Routage r) {
		
		return r.getDistance();
	}
	public EnergiePotentielleTsp(int i){
	super(i);
	}

}
