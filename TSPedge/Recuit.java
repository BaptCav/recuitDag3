import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

// Cette classe definit le probleme du recuit. Il se charge d'effectuer les mutations elementaires, de calculer l'energie et de diminuer T...

public class Recuit extends JFrame
{
	private static final long serialVersionUID = 2L;

	static int nbTours = 1;
	static ParametreK K = new ParametreK(1);
	static Convergence convergence = new Convergence(0);
	static Energie listeEnergie = new ListeEnergie(false, new ArrayList<Double>(),100);
	static Energie energieCourante;
	static Energie energieNouvelle;
	static double solutionNumerique = -1;
	static long tempsExecution = -1;




	public static double probaAcceptation(NombreEnergie energieCourante, NombreEnergie energieNouvelle, ParametreT temperature) throws IOException 
	{
		listeEnergie.ajoutListe(Math.abs(energieCourante.getEnergie() - energieNouvelle.getEnergie()));
		if(listeEnergie.doitRenvoyerK()){
			K.setK(listeEnergie.donneK());
		}
		if(energieNouvelle.getEnergie()<energieCourante.getEnergie()){
			return 1;
		}
		return Math.exp((energieCourante.getEnergie() - energieNouvelle.getEnergie()) / (K.getK()*temperature.getTemperature()));
	}



	public static Routage solution(Graphe g,ArrayList<Integer> liste,int nombreIterations) throws IOException, InterruptedException
	{
		long debut = System.currentTimeMillis();

		//On introduit le parametreur suivant la convergence introduite en argument statique
				ParametreurExp param1 = new ParametreurExp(g,nombreIterations);;
				ParametreurLin param2 = new ParametreurLin(g,nombreIterations);
				ParametreT temperature;
				if (convergence.getConvergence() == 1){
					temperature = param1.parametrageT();
				} else {
					temperature = param2.parametrageT();
				}
				
		// On definit une route aleatoire en premier lieu
		Routage solutionCourante = new Routage(g);
		
		//Compteur pour le nombre d'iterations
		int compteur = 0 ;
		
		// On en calcule l'energie
		// et on dit que pour l'instant, c'est la meilleure route !
		Routage meilleureRoute = new Routage(g);
		Routage nvelleSolution = new Routage(g);
		ParametreT temperatureRecuit = new ParametreT(temperature.getTemperature(),temperature.getFacteurDeRefroidissement(),temperature.getTemperatureFin());
		//double temperatureRecuit = temperature;
		int cptTours = nbTours;
		// On repete tant que la temperature est assez haute
		while (compteur < nombreIterations) {
			while(cptTours > 0)
			{
				// On cree une nouvelle route conçue à partir de l'ancienne
				nvelleSolution.clone(solutionCourante);
				// Sur cette nouvelle route, on effectue une mutation elementaire (2optMove)
				Mutation.twoOptMove(nvelleSolution);
				// On recupere l'energie (distance de parcours) des deux routes
				NombreEnergie energieCourante = new NombreEnergie(solutionCourante.getDistance());
				NombreEnergie energieVoisine = new NombreEnergie(nvelleSolution.getDistance());


				double p = probaAcceptation(energieCourante, energieVoisine, temperatureRecuit);
				// On décide si on accepte cette nouvelle route comme vu précédemment
				if (p >= Math.random()) {
					solutionCourante.clone(nvelleSolution);
				}

				if (solutionCourante.getDistance() < meilleureRoute.getDistance()) {
					meilleureRoute.clone(solutionCourante);
				}

				cptTours-=1;
			}
			compteur++ ;
			cptTours = nbTours;
			if (convergence.getConvergence() == 1){
				temperatureRecuit.refroidissementExp();
			} else {
				temperatureRecuit.refroidissementLin();
			}
		}

		// Lorsque l'energie cinetique n'est plus suffisante, on s'arrete et on affiche la solution trouvee
		tempsExecution = System.currentTimeMillis()-debut;
		solutionNumerique = meilleureRoute.getDistance();
		System.out.println("distance meilleure route = " + meilleureRoute.getDistance());

		return meilleureRoute;



	}   

}

