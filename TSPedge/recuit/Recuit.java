package recuit;
import modele.*;
import parametrage.*;
import mutation.*;

import java.io.IOException;
import java.util.List;

import javax.swing.*;

// Cette classe definit le probleme du recuit. Il se charge d'effectuer les mutations elementaires, de calculer l'energie et de diminuer T...

public class Recuit extends JFrame
{
	private static final long serialVersionUID = 2L;

	static int nbTours = 100;
	static ParametreK K = new ParametreK(1);
	//static Energie listeEnergie = new ListeEnergie(false, new ArrayList<Double>(),100);
	public static double solutionNumerique = -1;
	static long tempsExecution = -1;


	// Renvoie la possibilité que la nouvelle route soit acceptée. Elle prend en argument la différence d'energie entre les deux routes traitées dans le recuit.
	
	public static double probaAcceptation(NombreEnergie deltaE, Temperature temperature) throws IOException 
	{
	/*	listeEnergie.ajoutListe(Math.abs(deltaE.getEnergie()));
		if(listeEnergie.doitRenvoyerK()){
			K.setK(listeEnergie.donneK());
		}
		*/
		if(deltaE.getEnergie()<0){
			return 1;
		}
		return Math.exp((-deltaE.getEnergie()) / (K.getK()*temperature.getValue()));
	}



	public static Etat solution(Graphe g, int nombreIterations) throws IOException, InterruptedException
	{
	
		
		//On introduit le parametreur qui définit une convergence, une temperature initiale et une temperature finale
		Parametreur param = new ParametreurLocal(g,nombreIterations);
		int compteur =0;
		// parametreT définit les paramètres thermiques du recuit 
		ParametreT parametreT = param.getInitialTemperature();
	
		//Initialisation de la variable mutation
		TwoOptMove mutation = new TwoOptMove(0,0);
		NombreEnergie deltaE = new NombreEnergie(0.0);
		// On definit une route initiale aleatoire et son energie
		// Remplacer Routage(g,int) par Routage(g) si on n'utilise pas ParametreurLocal
		Etat routage = new Routage(g,100);
		NombreEnergie EnergieCourante = routage.energie();
		
		// On définit la meilleure Route et son energie
		Etat meilleureRoute = new Routage(g);
		NombreEnergie meilleureEnergie = meilleureRoute.energie();
		
		int n = g.getdists().length;
		Temperature temperatureRecuit = new Temperature(parametreT.getTemperatureDebut().getValue());

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
				double p = probaAcceptation(deltaE, temperatureRecuit);
				// On decide si on accepte cette nouvelle route. Si c'est le cas, on met à jour la route et l'energie courantes
				if (p >= Math.random()) {
					mutation.faire(routage);
					EnergieCourante.setEnergie(EnergieCourante.getEnergie() + deltaE.getEnergie()); 
				}
				//On modifie la liste des DeltaE
				//Mettre la ligne suivante en commentaire si on n'utilise pas ParametreurLocal
				routage.remplacer(g,10);
				// On regarde si l'energie courante ameliore la meilleure energie rencontree jusque là.
				if (EnergieCourante.getEnergie() < meilleureEnergie.getEnergie()) {
					meilleureRoute=routage.clone();
					meilleureEnergie.setEnergie(EnergieCourante.getEnergie()) ;
				}

				cptTours-=1;
			}
			compteur++;
			// On refroidit la variable temperatureRecuit
			param.refroidir(routage,temperatureRecuit,compteur);
		}
		
		solutionNumerique = meilleureRoute.energie().getEnergie();
		System.out.println(meilleureRoute.energie().getEnergie());
		return meilleureRoute;

	}   

}

