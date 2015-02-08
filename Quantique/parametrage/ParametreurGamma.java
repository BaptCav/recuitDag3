package parametrage;

import modele.Graphe;

public class ParametreurGamma {
	
	//Fonction réciproque à tanh
	public static double argth(double x){
		return 0.5*Math.log((1+x)/(1-x)); 
	}

	// Ce parametreur donne les 3 attributs d'un parametre Gamma pour un recuit
	// Il essaie de comparer l'influence d'une amelioration d'Epot et d'Ecin
	// Etant donne un deltaE, le parametreur decrete qu'à la fin du recuit, une amelioration moyenne d'Ecin a la même influence qu'une amelioration deltaE/p.size() de Epot
	// Le poids permet de faie varier linéairement l'influence d'une amelioration sur l'autre
	public static ParametreGamma parametrageGamma(Graphe g, int nombreIterations,int nombreEtat,Temperature temp, double deltaE){
		double t = temp.getValue();
		double finGamma = nombreEtat*t*argth(Math.exp(-deltaE/(2*t*nombreEtat)));
		double facteur = 1.0-Math.pow(0.01,1.0/nombreIterations);
		ParametreGamma gamma = new ParametreGamma(100*finGamma,facteur,finGamma);
		//System.out.println(finGamma);
		return gamma;
	}
}
