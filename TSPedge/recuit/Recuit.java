package recuit;
import modele.*;
import parametrage.*;
import mutation.*;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

// Cette classe definit le probleme du recuit. Il se charge d'effectuer les mutations elementaires, de calculer l'energie et de diminuer T...

public class Recuit extends JFrame
{
	private static final long serialVersionUID = 2L;

	static int nbTours = 1;
	static ParametreK K = new ParametreK(1);
	//static ParametreT temperature=new ParametreT(1000,0.0001,0);
	//static Energie listeEnergie = new ListeEnergie(false, new ArrayList<Double>(),100);
	static NombreEnergie deltaE;
	public static double solutionNumerique = -1;
	static long tempsExecution = -1;



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



	public static Routage solution(Graphe g,ArrayList<Integer> liste, int nombreIterations) throws IOException, InterruptedException
	{
	
		//On introduit le parametreur suivant la convergence introduite en argument statique
		Parametreur param = new ParametreurLog(g,nombreIterations);
		int compteur =0;
		ParametreT parametreT = param.getInitialTemperature();
	
		//Initialisation de la variable mutation
		TwoOptMove mutation = new TwoOptMove(0,0);
		// On definit une route aleatoire en premier lieu
		Routage routage = new Routage(g);
		
		// On en calcule l'energie
		// et on dit que pour l'instant, c'est la meilleure route !
		Routage meilleureRoute = new Routage(g);
		
		int n = g.getdists().length;
		Temperature temperatureRecuit = new Temperature(parametreT.getTemperatureDebut().getValue());
		//double temperatureRecuit = temperature;

		// On repete tant que la temperature est assez haute
		while (compteur < nombreIterations) {
			int cptTours = 100;
			while(cptTours > 0)
			{	
				mutation = new TwoOptMove(n);
				deltaE= new NombreEnergie(mutation.calculer(routage));
				

				double p = probaAcceptation((NombreEnergie) deltaE, temperatureRecuit);
				// On décide si on accepte cette nouvelle route comme vu précédemment
				if (p >= Math.random()) {
					mutation.faire(routage);
				}
				
				if (routage.getDistance() < meilleureRoute.getDistance()) {
					meilleureRoute=routage.clone();
				}

				cptTours-=1;
			}
			compteur++;
			param.refroidir(temperatureRecuit,compteur);
		}

		// Lorsque l'energie cinetique n'est plus suffisante, on s'arrete et on affiche la solution trouvee
		solutionNumerique = meilleureRoute.getDistance();
		return meilleureRoute;
		


	}   

}

