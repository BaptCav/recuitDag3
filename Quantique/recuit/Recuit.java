
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
		for(int i=1; i<r.size()-1; i++){
			r.get(i).setprevious(r.get(i-1));
			r.get(i).setnext(r.get(i+1));
		}
		r.get(r.size()-1).setprevious( r.get(r.size()-2));
		r.get(r.size()-1).setnext( r.get(0));
		r.get(0).setprevious( r.get(r.size()-1));
		r.get(0).setnext(r.get(1));
		//On a cree une particule quelconque. On va lui appliquer un threshold annealing permettant d'eloigner les etats

		ParametreGamma gamma = new ParametreGamma(1.0,0,0);//aucune consequence sur cette methode
		Ponderation J = new Ponderation(gamma);
		ParticuleTSP p = new ParticuleTSP(r,seed,gamma);
		ArrayList<Etat> e = p.getEtat();
		p.setT(1.0);//aucune consequence sur cette methode

		double EnergieCinetiqueTotale= -EnergieCinetiqueTsp.calculer(p,J);
		double limite = -J.calcul(p.getT(),nombreEtat)*nombreEtat*((n*(n-9))/2);// correspond à Ecin lorsque les routes sont totalement differentes entre elles
		//System.out.println("lim :" + limite/J.calcul(p.getT(),nombreEtat));
		double diff = 0;

		int k = 0;

		//Si jamais on atteint pas la limite des routes totalement differentes, on s'arrête à 10000 iterations
		while ((EnergieCinetiqueTotale < (limite - 1)) && k<1000){
			//System.out.println("ecin :" + EnergieCinetiqueTotale);
			for(int j=0;j<nombreEtat;j++){
				k++;
				ParticuleTSP p2 = p.clone();
				ArrayList<Etat> e2 = p2.getEtat();

				Etat r2 = e2.get(j);
				//System.out.println("route init :" + ((Routage)r2).toString());
				TwoOptMove m = new TwoOptMove(n);
				//System.out.println("m(i) :" + m.getI());
				//System.out.println("m(j) :" + m.getJ());
				m.faire(p2,r2);
				//System.out.println("route mutee :" + ((Routage)r2).toString());
				diff = -p.differenceSpinsTwoOpt(j,m);
				//System.out.println("diff2opt :" + (-p.differenceSpinsTwoOpt(j,m)));

				if (diff > 0){
					e.set(j, r2);
					p.setEtat(e);
					//System.out.println("route dans particule :" + ((Routage)p.getEtat().get(j)).toString());
					EnergieCinetiqueTotale += J.calcul(p.getT(),nombreEtat)*diff;
					//System.out.println("ecin :" + EnergieCinetiqueTotale/J.calcul(p.getT(),nombreEtat));
					//System.out.println("ecinTH :" + EnergieCinetiqueTsp.calculerCompteurSpinique(p));
				}
				//System.out.println("rapprochement :" + ((Routage)p.getEtat().get(0)).distanceIsing((Routage)p.getEtat().get(1)));


			}
			//if (k%500 == 0) ((Routage)p.getEtat().get(2)).afficheIsing();
		}
		//System.out.println(k);
		System.out.println("dispersion terminée");
		return e;

	}

	public  double solution(Graphe g,int nombreEtat ,int nombreIterations, int seed, int M,PrintWriter sortie) throws IOException, InterruptedException
	{	
		int compteurpourlasortie=0;
		int n = g.getdists().length;
		ArrayList<Etat> r = ecarteEtats(g,nombreEtat,seed);

		List<Double> listeDelta = ParametreurT.parametreurRecuit(g, nombreIterations);
		Temperature temperatureDepart = new Temperature(listeDelta.get(50)/nombreEtat);
		ParametreGamma gamma = ParametreurGamma.parametrageGamma(g,nombreIterations,nombreEtat,temperatureDepart,listeDelta.get(100));// Rappel : 1000 echantillons
		//System.out.println("elt :" + listeDelta.get(300));
		ParticuleTSP p = new ParticuleTSP(r,seed,gamma);
		ParticuleTSP pBest = p.clone();


		p.setT(temperatureDepart);
		//System.out.println("temp :" + p.getT().getValue());
		//System.out.println("Gamma :" + listeDelta.get(250));

		//double[] tableauDistances = p.tableauDistances();

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
						//Comment for Not MC measures
						


						if (distance < distanceBest){
							pBest.setEtat(e);
							distanceBest = distance;
						}
						Writer.ecriture(compteurpourlasortie,distanceBest, sortie);
					}
					// Uncomment for not MC measures
					//if(compteurpourlasortie%1000==0){
					//Writer.ecriture(compteurpourlasortie,distanceBest, sortie);
					//}
					compteurpourlasortie++;	
				}


			}
			//UNE FOIS EFFECTUEE SUR tout les etat de la particule on descend gamma
			p.majgamma();
			J.setGamma(p.getGamma());
			Collections.shuffle(p.getEtat());

		}

		this.solutionNumerique=distanceBest;

		//Renvoie 0 si routes identiques. C'est le nombre de conflits entre les deux premieres routes
		System.out.println("rapprochement en % :" + ((Routage)p.getEtat().get(0)).pourcentageSimilarite((Routage)p.getEtat().get(1)));

		return distanceBest;

	}





}
