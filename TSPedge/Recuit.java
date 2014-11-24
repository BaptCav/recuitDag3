import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
	static int fenetre = 100; // de l'ordre de grandeur du nombre de villes
	static boolean movingAverage = true;
	public static ArrayList<Double> DeltaE = new ArrayList<Double>();

	// Probabilite d'accepter une solution pire que l'actuelle
	public static double probaAcceptation(double energieCourante, double energieNouvelle, double temperature, PrintWriter sortie) throws IOException 
	{
		DeltaE.add(Math.abs(energieCourante - energieNouvelle)); // On conserve les valeurs prises du delta énergétique
		// Si la nouvelle solution est meilleure, alors on accepte !
		if (energieNouvelle < energieCourante) {
			saveProba(1,sortie);
			return 1.0;
		}
		// le K peut etre definie a l'aide d'une moyenne glissante.
		// Constat : prendre la moyenne des deltaE n'a pas l'air idéal... D'où la division par fenetre^2 
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
			//System.out.println(K);
		}
		// si elle est pire, on definit une proba pour accepter eventuellement cette solution...
		saveProba((double)Math.round((Math.exp((energieCourante - energieNouvelle) / (K*temperature))) * 10000) / 10000,sortie);
		return Math.exp((energieCourante - energieNouvelle) / (K*temperature));
	}
	public static Routage2 solution(Graphe g,ArrayList<Integer> liste,PrintWriter sortie) throws IOException, InterruptedException
	{
		// On definit une route aleatoire en premier lieu
		Routage2 solutionCourante = new Routage2(g);
		int nbit=0;
		PrintWriter printProba = new PrintWriter(new FileWriter("Proba.txt"));
		// On en calcule l'energie
		// et on dit que pour l'instant, c'est la meilleure route !
		Routage2 meilleureRoute = new Routage2(g);
		Routage2 nvelleSolution = new Routage2(g);
		double temperatureRecuit = temperature;
		int cptTours = nbTours;
		// On repete tant que la temperature est assez haute
		while (temperatureRecuit > 1) {
			while(cptTours > 0)
			{
			// On cree une nouvelle route conçue à partir de l'ancienne
			nvelleSolution.clone(solutionCourante);
			// Sur cette nouvelle route, on effectue une mutation elementaire (2optMove)
			Mutation.twoOptMove(nvelleSolution);
			nbit++;
			
			// On recupere l'energie (distance de parcours) des deux routes
			double energieCourante = solutionCourante.getDistance();
			double energieVoisine = nvelleSolution.getDistance();

			// On décide si on accepte cette nouvelle route comme vu précédemment
			if (probaAcceptation(energieCourante, energieVoisine, temperatureRecuit,printProba) >= Math.random()) {
				solutionCourante.clone(nvelleSolution);
			}

			if (solutionCourante.getDistance() < meilleureRoute.getDistance()) {
				meilleureRoute.clone(solutionCourante);
			}
			Enregistrer(meilleureRoute.getDistance(),nbit,sortie);
			cptTours-=1;
			}
			cptTours = nbTours;
			temperatureRecuit *= 1-refroidissement;
		}
		printProba.close();
		// Lorsque l'energie cinetique n'est plus suffisante, on s'arrete et on affiche la solution trouvee
		System.out.println("distance meilleure route = " + meilleureRoute.getDistance());
		
		return meilleureRoute;
		



	}   
	
	   public static void Enregistrer(double E,int nbit,PrintWriter sortie) throws IOException
	    {    	
	    	sortie.println(E+","+nbit);
	    }
	   public static void saveProba(double proba,PrintWriter sortie) throws IOException
	    {    	
	    	sortie.println(proba+",");
	    }
}

