package recuit;
import modele.*;
import parametrage.*;
import mutation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.*;

// Cette classe definit le probleme du recuit. Il se charge d'effectuer les mutations elementaires, de calculer l'energie et de diminuer T...

public class Recuit extends JFrame
{
	public double solutionNumerique;
	private static final long serialVersionUID = 2L;

	static int nbTours = 1;
	static ParametreK K = new ParametreK(1);



	public static double probaAcceptation(double deltaE, double deltaEpot, Temperature temperature) throws IOException 
	{	if(deltaE<=0 /*|| deltaEpot<0*/){
		return 1;
	}
	return Math.exp( (-deltaE) / (K.getK()*temperature.getValue()));

	}

	//Eloigne les etats les uns des autres avant le début du recuit.
	public ArrayList<Etat> ecarteEtats(Graphe g, int nombreEtat, int seed){
		int n = g.getdists().length;
		ArrayList<Etat> r = new ArrayList<Etat>(nombreEtat);
		for(int i=0; i<nombreEtat; i++){
			r.add(new Routage(g));
		}
		//On a cree une particule quelconque. On va lui appliquer un threshold annealing permettant d'eloigner les etats
		
		ParametreGamma gamma = new ParametreGamma(1.0,0,0);//aucune consequence sur cette methode
		Ponderation J = new Ponderation(gamma);
		ParticuleTSP p = new ParticuleTSP(r,seed,gamma);
		ArrayList<Etat> e = p.getEtat();
		p.setT(1.0);//aucune consequence sur cette methode
		
		double EnergieCinetiqueTotale= -EnergieCinetiqueTsp.calculer(p,J);
		double limite = -J.calcul(p.getT(),nombreEtat)*nombreEtat*((n*(n-9))/2);// correspond à Ecin lorsque les routes sont totalement differentes entre elles
		double diff = 0;
		
		int k = 0;
		
		//Si jamais on atteint pas la limite des routes totalement differentes, on s'arrête à 10000 iterations
		while ((EnergieCinetiqueTotale < (limite - 0.001)) && k<10000){
			for(int j=0;j<nombreEtat;j++){
				k++;
				ParticuleTSP p2 = p.clone();
				ArrayList<Etat> e2 = p2.getEtat();
				
				Etat r2 = e2.get(j);
				TwoOptMove m = new TwoOptMove(n);
				m.faire(p2,r2);
				diff = -p.differenceSpins(j,m);
				
				if (diff > 0){
					e.set(j, r2);
					p.setEtat(e);
					EnergieCinetiqueTotale += diff;
				}
			}
		}
		System.out.println("dispersion terminée");
		return e;
		
	}

	public  double solution(Graphe g,int nombreEtat ,int nombreIterations, int seed, int M) throws IOException, InterruptedException
	{	
		ArrayList<Etat> r = ecarteEtats(g,nombreEtat,seed);
		
		List<Double> listeDelta = ParametreurT.parametreurRecuit(g, nombreIterations);
		Temperature temperatureDepart = new Temperature(listeDelta.get(50)/nombreEtat);
		ParametreGamma gamma = ParametreurGamma.parametrageGamma(g,nombreIterations,nombreEtat,temperatureDepart,listeDelta.get(100),1);
		ParticuleTSP p = new ParticuleTSP(r,seed,gamma);
		ParticuleTSP pBest = p.clone();
		
		
		p.setT(temperatureDepart);
		//System.out.println("temp :" + p.getT().getValue());
		//System.out.println("Gamma :" + listeDelta.get(250));
		
		//double[] tableauDistances = p.tableauDistances();
		
		Ponderation J = new Ponderation(p.getGamma());
		double Epot = p.calculerEnergiePotentielle();
		double compteurSpinique = p.calculerCompteurSpinique();
		double E = Epot-J.calcul(p.getT(), nombreEtat)*compteurSpinique;
		double deltapot  = 0;
		double distance = ((Routage)r.get(0)).getDistance();
		double distanceBest = distance;
		for(int i =0; i<nombreIterations;i++){
			
			 E = Epot-J.calcul(p.getT(), nombreEtat)*compteurSpinique;
			 
			 ArrayList<Etat> e = p.getEtat();

			 ParticuleTSP p2 = p.clone();
			 
			 ArrayList<Etat> e2 = p2.getEtat();
			 
			for(int j=0;j<nombreEtat;j++){// on effectue M  fois la mutation sur chaque particule avant de descendre gamma
				
				Etat r1 = e.get(j);
				Etat r2 = e2.get(j);
				
				for(int k=0; k<M; k++){
					
					distance = ((Routage)r2).getDistance();
					
					int n = ((Routage) r1).tailleRoute();
					TwoOptMove m = new TwoOptMove(n);
					m.faire(p2,r2);
					deltapot =  ((Routage) r2).getDistance() - distance;
					
					double delta = deltapot/nombreEtat  -J.calcul(p.getT(),nombreEtat)*p.differenceSpins(j,m);
					
					//VA REGARDER SI L'ON APPLIQUE LA MUTATION OU NON
					double pr=probaAcceptation(delta,deltapot,p.getT());
					if(pr>Math.random()){
						e.set(j, r2);
						p.setEtat(e);
						
						Epot += deltapot/nombreEtat;
						compteurSpinique += p.differenceSpins(j,m);
						E += delta;// L'energie courante est modifiée
						distance += deltapot;
					}
					if (distance < distanceBest){
						pBest.setEtat(e);
						distanceBest = distance;
					}
				}
				
				
			}
			//UNE FOIS EFFECTUEE SUR tout les etat de la particule on descend gamma
			p.majgamma();
			J.setGamma(p.getGamma());
			Collections.shuffle(p.getEtat());

		}

		this.solutionNumerique=distanceBest;
		System.out.println("result :" + distanceBest);
		return distanceBest;

	}
	
	



}
