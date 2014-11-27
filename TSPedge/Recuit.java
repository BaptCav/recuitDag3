import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

// Cette classe definit le probleme du recuit. Il se charge d'effectuer les mutations elementaires, de calculer l'energie et de diminuer T...

public class Recuit extends JFrame
{
	private static final long serialVersionUID = 2L;
	static double temperature = 1000;
	static double refroidissement = 0.0001;
	static double K = 1; // 0.1 ideal ? Hypothese : T0*K ~ nbre de points = ideal ?
	static int nbTours = 1;
	static int Npruning = 20; // on effectue des mutations sur les Npruning plus proches voisins
	static int fenetre = 100; // de l'ordre de grandeur du nombre de villes
	static boolean movingAverage = true;
	public static ArrayList<Double> DeltaE = new ArrayList<Double>();

	// Probabilite d'accepter une solution pire que l'actuelle
	public static double probaAcceptation(double energieCourante, double energieNouvelle, double temperature/*, PrintWriter sortie*/) throws IOException 
	{
		DeltaE.add(Math.abs(energieCourante - energieNouvelle)); // On conserve les valeurs prises du delta Ã©nergÃ©tique
		// Si la nouvelle solution est meilleure, alors on accepte !

		// le K peut etre definie a l'aide d'une moyenne glissante.
		// Constat : prendre la moyenne des deltaE n'a pas l'air idÃ©al... D'oÃ¹ la division par fenetre^2 
		if(movingAverage)
		{
			if(DeltaE.size() > fenetre)
			{
				double somme = 0;
				for(int i = DeltaE.size(); i>DeltaE.size() - fenetre; i--)
				{
					somme = somme + DeltaE.get(i-1);
				}
				K = somme/(fenetre*fenetre);
			}

		}
		// si elle est pire, on definit une proba pour accepter eventuellement cette solution...
		return Math.exp((energieCourante - energieNouvelle) / (K*temperature));
	}
	public static Routage solution(int[][] matIndex, Graphe g,ArrayList<Integer> liste) throws IOException, InterruptedException
	{
		// On definit une route aleatoire en premier lieu
		Routage solutionCourante = new Routage(g,matIndex);
		System.out.println(" distance Greedy = "+solutionCourante.getDistance());
		int compteur=0;
		// On en calcule l'energie
		// et on dit que pour l'instant, c'est la meilleure route !
		Routage meilleureRoute = new Routage(g);
		Routage nvelleSolution = new Routage(g);
		double temperatureRecuit = temperature;
		int cptTours = nbTours;
		// On repete tant que la temperature est assez haute
		while (temperatureRecuit > 1) {
			while(cptTours > 0)
			{
			// On cree une nouvelle route conÃ§ue Ã  partir de l'ancienne
			nvelleSolution.clone(solutionCourante);
			// Sur cette nouvelle route, on effectue une mutation elementaire (2optMove)
			Mutation.pruningTwoOptMove(matIndex, nvelleSolution, 20);
			compteur++;
			
			// On recupere l'energie (distance de parcours) des deux routes
			double energieCourante = solutionCourante.getDistance();
			double energieVoisine = nvelleSolution.getDistance();

			double p = probaAcceptation(energieCourante, energieVoisine, temperatureRecuit);
			// On dÃ©cide si on accepte cette nouvelle route comme vu prÃ©cÃ©demment
			if (p >= Math.random()) {
				solutionCourante.clone(nvelleSolution);
			}

			if (solutionCourante.getDistance() < meilleureRoute.getDistance()) {
				meilleureRoute.clone(solutionCourante);
			}
			
			Writer.ecriture(compteur, meilleureRoute.getDistance(), Writer.energie);
			Writer.ecriture(compteur, p, Writer.proba);
			
			cptTours-=1;
			}
			cptTours = nbTours;
			temperatureRecuit *= 1-refroidissement;
		}

		// Lorsque l'energie cinetique n'est plus suffisante, on s'arrete et on affiche la solution trouvee
		System.out.println("distance meilleure route = " + meilleureRoute.getDistance());
		
		return meilleureRoute;
	}   

}
