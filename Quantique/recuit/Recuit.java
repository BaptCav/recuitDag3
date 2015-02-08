package recuit;
import modele.*;
import parametrage.*;
import mutation.*;

import io.Writer;

import java.io.IOException;
import java.io.PrintWriter;
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
		while ((EnergieCinetiqueTotale < (limite - 1)) && k<1000){
			for(int j=0;j<nombreEtat;j++){
				k++;
				ParticuleTSP p2 = p.clone();
				ArrayList<Etat> e1 = p.getEtat();
				ArrayList<Etat> e2 = p2.getEtat();
				Etat r1 = e1.get(j);
				Etat r2 = e2.get(j);
				TwoOptMove m = new TwoOptMove(n);
				
				diff = -p.differenceSpinsTwoOpt(j,m);
				
				if (diff > 0){
					m.faire(p2,r2);
					e.set(j, r2);
					p.setEtat(e);
					EnergieCinetiqueTotale += J.calcul(p.getT(),nombreEtat)*diff;
				}
				
			}
			p.shuffle();
		}
		System.out.println("rapprochement initial en % : " +  ((Routage)p.getEtat().get(0)).pourcentageSimilarite((Routage)p.getEtat().get(1)));
		//System.out.println("dispersion terminée");
		return e;
		
	}

	public  double[] solution(Graphe g,int nombreEtat ,int nombreIterations, int seed, int M,int index, PrintWriter s) throws IOException, InterruptedException
	{	int comptesortie=0;
		int n = g.getdists().length;
		//ArrayList<Etat> r = ecarteEtats(g,nombreEtat,seed);
		ArrayList<Etat> r = new ArrayList<Etat>(nombreEtat);
		for(int i=0; i<nombreEtat; i++){
			r.add(new Routage(g));
		}
		System.out.println("rapprochement initial en % : " +  ((Routage)r.get(0)).pourcentageSimilarite((Routage)r.get(1)));
		
		List<Double> listeDelta = ParametreurT.parametreurRecuit(g, nombreIterations);
		Temperature temperatureDepart = new Temperature(listeDelta.get(50)/nombreEtat);
		ParametreGamma gamma = ParametreurGamma.parametrageGamma(g,nombreIterations,nombreEtat,temperatureDepart,listeDelta.get(index));// Rappel : 1000 echantillons
		
		ParticuleTSP p = new ParticuleTSP(r,seed,gamma);
		ParticuleTSP pBest = p.clone();
		
		
		p.setT(temperatureDepart);
		
		Ponderation J = new Ponderation(p.getGamma());
		double valueJ = J.calcul(p.getT(), nombreEtat);
		double Epot = p.calculerEnergiePotentielle();
		double compteurSpinique = p.calculerCompteurSpinique();
		double diffSpins = 0;
		double E = Epot-valueJ*compteurSpinique;
		double deltapot  = 0;
		double distance = ((Routage)r.get(0)).getDistance();
		double distanceBest = distance;
		for(int i =0; i<nombreIterations;i++){
			valueJ = J.calcul(p.getT(), nombreEtat);
			
			 E = Epot-valueJ*compteurSpinique;
			 
			 ArrayList<Etat> e = p.getEtat();

			 ParticuleTSP p2 = p.clone();
			 
			 ArrayList<Etat> e2 = p2.getEtat();
			 
			for(int j=0;j<nombreEtat;j++){// on effectue M  fois la mutation sur chaque particule avant de descendre gamma
				
				Etat r1 = e.get(j);
				Etat r2 = e2.get(j);
				
				for(int k=0; k<M; k++){
					
					//r1.afficheIsing();
					distance = ((Routage)r2).getDistance();
					
					TwoOptMove m = new TwoOptMove(n);
					deltapot =  m.calculer(p2,r2);
					
					diffSpins = p.differenceSpinsTwoOpt(j,m);
					
					double delta = deltapot/nombreEtat  - valueJ*diffSpins;
					//System.out.println(p.differenceSpinsTwoOpt(j,m));
					
					//VA REGARDER SI L'ON APPLIQUE LA MUTATION OU NON
					double pr=probaAcceptation(delta,deltapot,p.getT());
					if(pr>Math.random()){
						compteurSpinique += diffSpins;
						m.faire(p2,r2);
						e.set(j, r2);
						p.setEtat(e);
						
						Epot += deltapot/nombreEtat;
						//System.out.println("cpt" + compteurSpinique);
						E += delta;// L'energie courante est modifiée
						distance += deltapot;
					}
					if (distance < distanceBest){
						pBest.setEtat(e);
						distanceBest = distance;
					}
					/*if(comptesortie%1000==0){
						Writer.ecriture(comptesortie,distanceBest,s);
					}*/
					//comptesortie++;
				}
				
				
			}
			//UNE FOIS EFFECTUEE SUR tout les etat de la particule on descend gamma
			p.majgamma();
			J.setGamma(p.getGamma());
			p.shuffle();

		}
		Writer.ecriture(comptesortie,distanceBest,s);
		this.solutionNumerique=distanceBest;
		//Renvoie 0 si routes identiques. C'est le nombre de conflits entre les deux premieres routes
		double rapprochement = ((Routage)p.getEtat().get(0)).pourcentageSimilarite((Routage)p.getEtat().get(1));
		System.out.println("rapprochement en % :" + rapprochement);
		double[] t = {distanceBest,rapprochement};
		return t;

	}
	
	



}
