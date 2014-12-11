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
	static Convergence convergence = new Convergence(1);
	//static ParametreT temperature=new ParametreT(1000,0.0001,0);
	static Energie listeEnergie = new ListeEnergie(false, new ArrayList<Double>(),100);
	static Energie deltaE;
	public static double solutionNumerique = -1;
	static long tempsExecution = -1;



	public static double probaAcceptation(NombreEnergie deltaE, ParametreT temperature) throws IOException 
	{
		listeEnergie.ajoutListe(Math.abs(deltaE.getEnergie()));
		if(listeEnergie.doitRenvoyerK()){
			K.setK(listeEnergie.donneK());
		}
		if(deltaE.getEnergie()<0){
			return 1;
		}
		return Math.exp((-deltaE.getEnergie()) / (K.getK()*temperature.getTemperature()));
	}



	public static Routage solution(Graphe g,ArrayList<Integer> liste, int nombreIterations) throws IOException, InterruptedException
	{
		long debut = System.currentTimeMillis();
		int nbnoeud=g.getdists().length;
		//On introduit le parametreur suivant la convergence introduite en argument statique
		ParametreurExp param1 = new ParametreurExp(g,nombreIterations);
		ParametreurLin param2 = new ParametreurLin(g,nombreIterations);
		int compteur =0;
		ParametreT temperature;
		if (convergence.getConvergence() == 1){
			temperature = param1.parametrageT();
		} else {
			temperature = param2.parametrageT();
		}
		
		// On definit une route aleatoire en premier lieu
		Routage routage = new Routage(g);
		
		// On en calcule l'energie
		// et on dit que pour l'instant, c'est la meilleure route !
		Routage meilleureRoute = new Routage(g);
		
		
		ParametreT temperatureRecuit = new ParametreT(temperature.getTemperature(),temperature.getFacteurDeRefroidissement(),temperature.getTemperatureFin());
		//double temperatureRecuit = temperature;

		// On repete tant que la temperature est assez haute
		while (compteur < nombreIterations) {
			int cptTours = nbnoeud*nbnoeud;
			while(cptTours > 0)
			{
				int n = routage.tailleRoute();
				int randIndex1 = 0;//Indice du noeud c2
				int randIndex2 = 0;//Indice du noeud c1'
				int i;
				int j;
				//  On recalcule les indices de c2 et c1' jusqu'à ce que c2 soit différent de c1'.Notons que le cas c2=c1' ne change pas la route.
				while ((randIndex1 == randIndex2) || routage.getNextIndex(randIndex1)==randIndex2){
					randIndex1 = (int) (n * Math.random()); //c1'
					randIndex2 = (int) (n * Math.random()); //c2
				}
				i = randIndex1;
				j = randIndex2;
				
				
				deltaE= new NombreEnergie(TwoOptMove.calculer(routage,i,j));

				double p = probaAcceptation((NombreEnergie) deltaE, temperatureRecuit);
				System.out.println("proba: " + p);
				// On décide si on accepte cette nouvelle route comme vu précédemment
				if (p >= Math.random()) {
					TwoOptMove.faire(routage,i,j);
				}
				
				if (routage.getDistance() < meilleureRoute.getDistance()) {
					meilleureRoute=routage.clone();
				}

				cptTours-=1;
			}
			compteur++;
			if (convergence.getConvergence() == 1){
				temperatureRecuit.refroidissementExp();
			} else {
				temperatureRecuit.refroidissementLin();
			}
		}

		// Lorsque l'energie cinetique n'est plus suffisante, on s'arrete et on affiche la solution trouvee
		tempsExecution = System.currentTimeMillis()-debut;
		solutionNumerique = meilleureRoute.getDistance();
		System.out.println("dist="+meilleureRoute.getDistance());

		return meilleureRoute;
		


	}   

}

