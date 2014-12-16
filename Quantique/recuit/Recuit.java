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

	public static double solutionNumerique = -1;
	static long tempsExecution = -1;



	public static double probaAcceptation(double deltaE, ParametreT temperature) throws IOException 
	{

		if(deltaE<0){
			return 1;
		}
		return Math.exp((-deltaE) / (K.getK()*temperature.getTemperature()));
	}



	public static Routage solution(Graphe g,ArrayList<Integer> liste,int nombreEtat ,int nombreIterations, int seed, int M) throws IOException, InterruptedException
	{
		Routage[] r = new Routage[nombreEtat];
		for(int i=0; i<nombreEtat; i++){
			r[i]= new Routage(g,liste);
		}

		ParticuleTSP p = new ParticuleTSP(r,seed);
		ParametreGamma gamma = new ParametreGamma(1000,1,0.1);
		double E = p.calculerEnergie();
		for(int i =0; i<nombreIterations;i++){

			Routage[] e = p.getRoutage();
			ParticuleTSP p2 = p.clone();
			Routage[] e2 = p2.getRoutage();

			for(int j=0;j<nombreEtat;j++){// on effectue M  fois la mutation sur chaque particule avant de descendre gamma
				Routage r1 = e[j];
				Routage r2 = e2[j];

				for(int k=1; k<M; k++){
					int n = r2.tailleRoute();
					int randIndex1 = 0;//Indice du noeud c2
					int randIndex2 = 0;//Indice du noeud c1'
					int i1;
					int j1;
					//  On recalcule les indices de c2 et c1' jusqu'à ce que c2 soit différent de c1'.Notons que le cas c2=c1' ne change pas la route.
					while ((randIndex1 == randIndex2) || r2.getNextIndex(randIndex1)==randIndex2){
						randIndex1 = (int) (n * Math.random()); //c1'
						randIndex2 = (int) (n * Math.random()); //c2
					}
					i1 = randIndex1;
					j1 = randIndex2;				
					TwoOptMove.faire(r2, i1, j1);
					double E2=p2.calculerEnergie();

					//VA REGARDER SI L'ON APPLIQUE LA MUTATION OU NON

					if(probaAcceptation(E2-E,p.getT())>Math.random()){
						e[j]=r2;
						p.setRoutage(e);
					}else{
						r2=r1;
					}
				}	


			}
			//UNE FOIS EFFECTUEE SUR tout les etat de la ^particule on descend gamma
			gamma.refroidissementExp();



		}



		return p.getBest();

	}
}
