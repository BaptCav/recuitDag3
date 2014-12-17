package recuit;
import modele.*;
import parametrage.*;
import mutation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.swing.*;

// Cette classe definit le probleme du recuit. Il se charge d'effectuer les mutations elementaires, de calculer l'energie et de diminuer T...

public class Recuit extends JFrame
{
	private static final long serialVersionUID = 2L;

	static int nbTours = 1;
	static ParametreK K = new ParametreK(1);



	public static double probaAcceptation(double deltaE, ParametreT temperature) throws IOException 
	{	if(deltaE<0){
		return 1;
	}
	return Math.exp((-deltaE) / (K.getK()*temperature.getTemperature()));
	}



	public static Etat solution(Graphe g,ArrayList<Integer> liste,int nombreEtat ,int nombreIterations, int seed, int M) throws IOException, InterruptedException
	{
		ArrayList<Routage> r = new ArrayList<Routage>(nombreEtat);
		for(int i=0; i<nombreEtat; i++){
			r.set(i,new Routage(g,liste));
		}

		ParticuleTSP p = new ParticuleTSP(r,seed);
		ParametreGamma gamma = new ParametreGamma(1000,1,0.1);
		double E = p.calculerEnergie();
		for(int i =0; i<nombreIterations;i++){

			 ArrayList<Etat> e = p.getEtat();
			ParticuleTSP p2 = p.clone();
			 ArrayList<Etat> e2 = p2.getEtat();

			for(int j=0;j<nombreEtat;j++){// on effectue M  fois la mutation sur chaque particule avant de descendre gamma
				Etat r1 = e.get(j);
				Etat r2 = e2.get(j);

				for(int k=1; k<M; k++){
					
					int n = ((Routage) r1).tailleRoute();
					TwoOptMove m = new TwoOptMove(n);				
					m.faire(p2,r2);
					double E2=p2.calculerEnergie();

					//VA REGARDER SI L'ON APPLIQUE LA MUTATION OU NON

					if(probaAcceptation(E2-E,p.getT())>Math.random()){
						e.set(j, r2);
						p.setEtat(e);
					}else{
						r2=r1;
					}
				}	


			}
			//UNE FOIS EFFECTUEE SUR tout les etat de la ^particule on descend gamma
			gamma.refroidissementExp();
			Collections.shuffle(p.getEtat());


		}



		return p.getBest();

	}
}
