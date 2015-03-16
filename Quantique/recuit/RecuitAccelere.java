
package recuit;
import modele.*;
import parametrage.*;
import mutation.*;
import io.Writer;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.*;

// Cette classe definit le probleme du recuit. Il se charge d'effectuer les mutations elementaires, de calculer l'energie et de diminuer T...

/**
 * 
 * @author Pierre
 *
 */
public class RecuitAccelere extends JFrame
{
	public double solutionNumerique;
	private static final long serialVersionUID = 2L;

	static int nbTours = 1;
	static ParametreK K = new ParametreK(1);


	/**
	 * C'est la méthode qui effectue le recuit quantique. 
	 * @param p
	 * Problème construit par l'utilisateur
	 * @param m
	 * Une mutation aléatoire correspondant au problème traité.
	 * Les générations de mutation aléatoires au fil du recuit s'effectueront avec la méthode maj que l'utilisateur aura implémenté dans sa classe (voir IMutation)
	 * @param nombreIterations
	 * Nombre d'iterations pour chaque réplique. Le nombre de répliques, on le rappelle, est défini dans Problème.
	 * @param seed
	 * Rentrer 1 en argument
	 * @param M
	 * Nombre de fois consécutives que l'on traite une réplique. Rentrer 1 pour une utilisation normale
	 * @param sortie
	 * Fichier .txt dans lequel on stocke le résultat final du recuit.
	 * @return
	 * Solution du recuit : la meilleure énergie rencontrée par la particule
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public  double solution(Probleme p,IMutation m,int nombreIterations, int seed, int M,PrintWriter sortie) throws IOException, InterruptedException
	{	
		int compteurpourlasortie=0;

		int nombreRepliques=p.nombreEtat();
		List<Double> listeDelta = ParametreurT.parametreurRecuit(p,m, nombreIterations);
		Temperature temperatureDepart = new Temperature(listeDelta.get(50)/nombreRepliques);
		ParametreGamma gamma = ParametreurGamma.parametrageGamma(nombreIterations,nombreRepliques,temperatureDepart,listeDelta.get(200));// Rappel : 1000 echantillons
		p.setT(temperatureDepart.getValue());
		p.setGamma(gamma);
		Probleme pBest = p.clone();
		
		double Jr = 0;
		double deltaEp = 0;
		double deltaEcUB = 0;
		double deltaE = 0;
		double EpActuelle = 0;
		double deltaEc = 0;
		
		ArrayList<Etat> e = p.getEtat();
		Etat etat = e.get(0);
		Etat previous = e.get(nombreRepliques-1);
		Etat next = e.get(1);
		double meilleureEnergie = (e.get(0)).getEnergie();
		
		for (int i = 0; i < nombreRepliques; i++){ // initialisation de meilleureEnergie
			double energie = e.get(i).getEnergie();
			if (energie < meilleureEnergie){
				meilleureEnergie = energie ;
			}
		}
		
		double proba = 1;
		
		for(int i =0; i<nombreIterations;i++){ // equivalent du while(Gamma.modifierT()....
			
			Probleme p2 = p.clone();
			ArrayList<Etat> e2 = p2.getEtat();
			 
			Collections.shuffle(p.getEtat());
			Ponderation J = new Ponderation(p.getGamma());
			Jr = J.calcul(p.getT(), nombreRepliques);
			
			for(int j=0;j<nombreRepliques;j++){
				
				Etat r2 = e2.get(j);
				
				for(int k=0; k<M; k++){
					m.maj();
					deltaEp = p.calculerEnergiePotentielle(); // calculer deltaEp si la mutation etait acceptee
					deltaEcUB = 16; // calculer deltaIEc si la mutation etait acceptee
					deltaE = deltaEp/nombreRepliques - deltaEcUB*Jr;
					
					if(deltaEp <= 0){
						m.faire(p2,r2); // faire la mutation
						EpActuelle = etat.getEnergie(); // energie potentielle temporelle
						if( EpActuelle < meilleureEnergie ){ // mettre a jour la meilleur energie
							meilleureEnergie = EpActuelle;	
						}
					}
					else{
						if (deltaE < 0) proba = 1;
						else proba = expf(-deltaE/(K.getK()*p.getT().getValue()));
						if (proba >= Math.random()) {
							deltaEc = p.calculerEnergieCinetique();
							deltaE = deltaEp/nombreRepliques - deltaEc*Jr;
							
							if( deltaE <= 0){
								m.faire(p2,r2); // faire la mutation
								EpActuelle = etat.getEnergie(); // energie potentielle temporelle
							}
							else{
								proba = expf(-deltaE/(K.getK()*p.getT().getValue()));
								if (proba >= Math.random()) {
									m.faire(p.clone(),e.get(j)); // accepter la mutation
								}
							}
						}
					}
				}
			}
			p.majgamma();
			J.setGamma(p.getGamma());
		}
		
		Writer.ecriture(compteurpourlasortie,meilleureEnergie, sortie);

		return meilleureEnergie;

	}

	public static double exp1(double x) {
		x = 1.0 + x / 256.0;
		x *= x;
		x *= x;
		x *= x;
		x *= x;
		x *= x;
		x *= x;
		x *= x;
		x *= x;
		return x;
	}
	public static double exp2(double val) {
		final long tmp = (long) (1512775 * val + 1072632447);
		return Double.longBitsToDouble(tmp << 32);
	}
	public static double expf(double val) {
		if (val >= 0)
			return 1;
		if (val > -2.5)
			return exp1(val);
		if (val > -700)
			return exp2(val);
		else
			return 0;
	}
}
