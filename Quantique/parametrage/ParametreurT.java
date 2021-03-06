package parametrage;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import modele.*;
import mutation.IMutation;


public class ParametreurT {
	
	/**
	 * Renvoie la liste de 1000 deltaEpot tri�e. Permet le param�trage de T (au 5e centile) et de Gamma (voir param�treurGamma)
	 * @param p
	 * Probl�me en entr�e du recuit
	 * @param m
	 * Type de mutation que l'on traite
	 * @param nombreIterations
	 * Nombre d'it�rations du recuit
	 * @return
	 * Liste tri�e de 1000 deltaEpot
	 */
	public static List<Double> parametreurRecuit(Probleme p,IMutation m,int nombreIterations){
		
		Etat r1;
		double deltaE = -1;
		List<Double> l = new LinkedList<Double>();
		for (int i =0; i < 1000; i++){
			deltaE = -1;
			while(deltaE<=0){
		
			r1=p.creeEtatAleatoire();
			
			deltaE=m.calculer(p,r1);
			m.maj();
			}
			l.add((deltaE));
			//On vient de générer une liste de 400 échantillons deltaE du graphe g.
		}
		Collections.sort(l);
		//Ici, on choisit de se baser autour du 5e centile des échantillons générés précédemment.
		//Temperature temp = new Temperature(l.get(30));
		return l;
	}
}
