
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

		int nombreEtat=p.nombreEtat();
		List<Double> listeDelta = ParametreurT.parametreurRecuit(p,m, nombreIterations);
		Temperature temperatureDepart = new Temperature(listeDelta.get(50)/nombreEtat);
		ParametreGamma gamma = ParametreurGamma.parametrageGamma(nombreIterations,nombreEtat,temperatureDepart,listeDelta.get(200));// Rappel : 1000 echantillons
		p.setT(temperatureDepart.getValue());
		p.setGamma(gamma);
		Probleme pBest = p.clone();


		p.setT(temperatureDepart);
		ArrayList<Etat> e = p.getEtat();
		Ponderation J = new Ponderation(p.getGamma());
		double Epot = p.calculerEnergiePotentielle();
		double compteurSpinique = p.calculerEnergieCinetique();
		double E = Epot-J.calcul(p.getT(), nombreEtat)*compteurSpinique;
		double deltapot  = 0;
		double energie = (e.get(0)).getEnergie();
		double energieBest = energie;
		double EpActuelle = 0;
		double proba = 0;

		for(int i =0; i<nombreIterations;i++){

			E = Epot-J.calcul(p.getT(), nombreEtat)*compteurSpinique;


			Probleme p2 = p.clone();

			ArrayList<Etat> e2 = p2.getEtat();

			for(int j=0;j<nombreEtat;j++){// on effectue M  fois la mutation sur chaque particule avant de descendre gamma

				Etat r1 = e.get(j);
				Etat r2 = e2.get(j);

				for(int k=0; k<M; k++){

					m.maj();
					energie = r2.getEnergie();

					deltapot =  m.calculer(p2,r2);

					double delta = deltapot/nombreEtat;

					if(deltapot <= 0){
						m.faire(p2,r2);
						EpActuelle = r2.getEnergie();

						if(EpActuelle < energieBest)
						{
							energieBest = EpActuelle;
						}
					}
					else {
						if(delta < 0){
							proba = 1;
						}
						else{
							proba = expf(-delta/(K.getK()*p.getT().getValue()));
						}
						
						if(proba >= Math.random())
						{
							double deltaEc = p.differenceSpins(r2,m);
							delta = deltapot/nombreEtat - J.calcul(p.getT(),nombreEtat)*deltaEc;
							
							if(delta <= 0){
								m.faire(p2,r2);
								EpActuelle = r2.getEnergie();
							}
							else{
								proba = expf(-delta/(K.getK()*p.getT().getValue()));
								if(proba >= Math.random()){
									m.faire(p2,r2);
								}
							}
						}
					}
				}


			}
			//UNE FOIS EFFECTUEE SUR tout les etat de la particule on descend gamma
			p.majgamma();
			J.setGamma(p.getGamma());
			Collections.shuffle(p.getEtat());

		}
		Writer.ecriture(compteurpourlasortie,energieBest, sortie);

		return energieBest;

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
