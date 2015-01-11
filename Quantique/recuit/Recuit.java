package recuit;
import modele.*;
import parametrage.*;
import mutation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.*;

// Cette classe definit le probleme du recuit. Il se charge d'effectuer les mutations elementaires, de calculer l'energie et de diminuer T...

public class Recuit extends JFrame
{
	public double solutionNumerique;
	private static final long serialVersionUID = 2L;

	static int nbTours = 1;
	static ParametreK K = new ParametreK(1);



	public static double probaAcceptation(double deltaE, Temperature temperature) throws IOException 
	{	if(deltaE<=0){
		return 1;
	}
	return Math.exp( (-deltaE) / (K.getK()*temperature.getValue()));

	}



	public  Etat solution(Graphe g,int nombreEtat ,int nombreIterations, int seed, int M, int tailleListeVoisins) throws IOException, InterruptedException
	{
		ArrayList<Etat> r = new ArrayList<Etat>(nombreEtat);
		for(int i=0; i<nombreEtat; i++){
			
			r.add(new Routage(g));
		}
		Temperature temperatureDepart = ParametreurT.parametreurRecuit(g, nombreIterations);
		ParametreGamma gamma = ParametreurGamma.parametrageGamma(g,nombreIterations,nombreEtat,temperatureDepart,10,1);
		ParticuleTSP p = new ParticuleTSP(r,seed,gamma);
		ParticuleTSP pBest = p.clone();
		
		p.setT(temperatureDepart);
		System.out.println(p.getT().getValue());
		double E = p.calculerEnergie();
		double Ebest = E;
		for(int i =0; i<nombreIterations;i++){
			
			 E = p.calculerEnergie();

			 ArrayList<Etat> e = p.getEtat();

			 ParticuleTSP p2 = p.clone();
			 
			 ArrayList<Etat> e2 = p2.getEtat();
			 
			 double taille = e.size();
			 
			for(int j=0;j<nombreEtat;j++){// on effectue M  fois la mutation sur chaque particule avant de descendre gamma
				
				Etat r1 = e.get(j);
				Etat r2 = e2.get(j);
				//p.setT(((Routage)r1).buildListeVoisins(tailleListeVoisins).get(0)/taille);//On classe 20 deltaE issus de mutations twoOptMove
				
				for(int k=1; k<M; k++){
					
					int n = ((Routage) r1).tailleRoute();
					TwoOptMove m = new TwoOptMove(n);								
					m.faire(p2,r2);
					
					p2.setEtat(e2);
					
					double E2=p2.calculerEnergie();
					//System.out.println("Energie mutée :" + E2);
					//VA REGARDER SI L'ON APPLIQUE LA MUTATION OU NON
					double pr=probaAcceptation(E2-E,p.getT());
					if(pr>Math.random()){
						e.set(j, r2);
						p.setEtat(e);// La particule courante est modifiée
						E = E2;// L'energie courante est modifiée
						if (E < Ebest){
							pBest.setEtat(e);
							Ebest = E;
						}
					}else{
						r2=r1;
					}
				}
				
				
			}
			//UNE FOIS EFFECTUEE SUR tout les etat de la particule on descend gamma
			if (i%200 == 0){
				System.out.println(i);
				System.out.println("tot :" + p.calculerEnergie());
				System.out.println("pot :" + p.calculerEnergiePotentielle());
			}
			p.majgamma();
			Collections.shuffle(p.getEtat());
			//if (i%100 == 0) System.out.println(p.getT().getValue());

		}
		Routage b = (Routage)  pBest.getBest();

		this.solutionNumerique=b.getDistance();
		System.out.println("result :" + b.getDistance());
		return b;

	}
	
	



}
