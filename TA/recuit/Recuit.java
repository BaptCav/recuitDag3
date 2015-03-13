package recuit;
import modele.*;
import parametrage.*;
import mutation.*;
import io.Writer;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.swing.*;

// Cette classe definit le probleme du recuit. Il se charge d'effectuer les mutations elementaires, de calculer l'energie et de diminuer T...

public class Recuit extends JFrame
{
	private static final long serialVersionUID = 2L;

	static int nbTours = 100;
	//static Energie listeEnergie = new ListeEnergie(false, new ArrayList<Double>(),100);
	public static double solutionNumerique = -1;
	static long tempsExecution = -1;


	// Renvoie la possibilit� que la nouvelle route soit accept�e. Elle prend en argument la diff�rence d'energie entre les deux routes trait�es dans le recuit.
	
	public static double probaAcceptation(NombreEnergie deltaE) throws IOException 
	{
	/*	listeEnergie.ajoutListe(Math.abs(deltaE.getEnergie()));
		if(listeEnergie.doitRenvoyerK()){
			K.setK(listeEnergie.donneK());
		}
		*/
		if(deltaE.getEnergie()<0){
			return 1;
		}
		return 0;
	}



	public static double solution(Graphe g, int nombreIterations,PrintWriter sortie) throws IOException, InterruptedException
	{
	
		int compteur =0;
	
		//Initialisation de la variable mutation
		TwoOptMove mutation = new TwoOptMove(0,0);
		NombreEnergie deltaE = new NombreEnergie(0.0);
		// On definit une route initiale aleatoire et son energie
		Etat routage = new Routage(g);
		NombreEnergie EnergieCourante = routage.energie();
		
		// On d�finit la meilleure Route et son energie
		Etat meilleureRoute = new Routage(g);
		NombreEnergie meilleureEnergie = meilleureRoute.energie();
		
		int n = g.getdists().length;

		// On repete nombreIterations fois
		while (compteur < nombreIterations) {
			// Pour chaque temperature, on fixe un nombre d'iterations nbTours
			int cptTours = nbTours;
			while(cptTours > 0)
			{	
				
				//On definit une mutation pour ce tour-ci.
				mutation = new TwoOptMove(n);
				// deltaE nous donne la difference d'energie entre la route mutee et la route courante
				deltaE=mutation.calculer(routage);
				// On definit la probabilite d'accepter la route mutee
				double p = probaAcceptation(deltaE);
				// On decide si on accepte cette nouvelle route. Si c'est le cas, on met � jour la route et l'energie courantes
				if (p > 0.5) {
					mutation.faire(routage);
					EnergieCourante.setEnergie(EnergieCourante.getEnergie() + deltaE.getEnergie()); 
				}
				// On regarde si l'energie courante ameliore la meilleure energie rencontree jusque l�.
				if (EnergieCourante.getEnergie() < meilleureEnergie.getEnergie()) {
					meilleureRoute=routage.clone();
					meilleureEnergie.setEnergie(EnergieCourante.getEnergie()) ;
				}

				cptTours-=1;
			}
			compteur++;
		}
		Writer.ecriture(0,meilleureRoute.energie().getEnergie(), sortie);
		solutionNumerique = meilleureRoute.energie().getEnergie();
		System.out.println(meilleureRoute.energie().getEnergie());
		return meilleureRoute.energie().getEnergie();

	}   

}

