
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
	
	
	public  double solution(Probleme p,IMutation m,int nombreIterations, int seed, int M,PrintWriter sortie) throws IOException, InterruptedException
	{	
		int compteurpourlasortie=0;
		
		int nombreEtat=p.nombreEtat();
		List<Double> listeDelta = ParametreurT.parametreurRecuit(p,m, nombreIterations);
		Temperature temperatureDepart = new Temperature(listeDelta.get(50)/nombreEtat);
		ParametreGamma gamma = ParametreurGamma.parametrageGamma(nombreIterations,nombreEtat,temperatureDepart,listeDelta.get(200));// Rappel : 1000 echantillons
		p.setT(temperatureDepart.getValue());
		p.setGamma(gamma);
		Probleme pBest = p.clone();
		
		
		p.setT(temperatureDepart);
		ArrayList<Etat> e = p.getEtat();
		Ponderation J = new Ponderation(p.getGamma());
		double Epot = p.calculerEnergiePotentielle();
		double compteurSpinique = p.calculerEnergieCinetique();
		double E = Epot-J.calcul(p.getT(), nombreEtat)*compteurSpinique;
		double deltapot  = 0;
		double distance = (e.get(0)).getEnergie();
		double distanceBest = distance;
		
		for(int i =0; i<nombreIterations;i++){
			
			 E = Epot-J.calcul(p.getT(), nombreEtat)*compteurSpinique;
			 

			 Probleme p2 = p.clone();
			 
			 ArrayList<Etat> e2 = p2.getEtat();
			 
			for(int j=0;j<nombreEtat;j++){// on effectue M  fois la mutation sur chaque particule avant de descendre gamma
				
				Etat r1 = e.get(j);
				Etat r2 = e2.get(j);
				
				for(int k=0; k<M; k++){
					
					m.maj();
					distance = r2.getEnergie();
					
					deltapot =  m.calculer(p2,r2);
					
					double delta = deltapot/nombreEtat  -J.calcul(p.getT(),nombreEtat)*p.differenceSpins(r2,m);
					
					//VA REGARDER SI L'ON APPLIQUE LA MUTATION OU NON
					double pr=probaAcceptation(delta,deltapot,p.getT());
					if(pr>Math.random()){
						m.faire(p2,r2);
						compteurSpinique += p.differenceSpins(r2,m);
						
						e.set(j, r2);
						p.setEtat(e);
						
						Epot += deltapot/nombreEtat;
						E += delta;// L'energie courante est modifiée
						distance += deltapot;
						
					}
					if (distance < distanceBest){
						pBest.setEtat(e);
						distanceBest = distance;
					}
					/*if(compteurpourlasortie%1000==0){
					Writer.ecriture(compteurpourlasortie,distanceBest, sortie);
					}
					compteurpourlasortie++;
					*/	
				}
				
				
			}
			//UNE FOIS EFFECTUEE SUR tout les etat de la particule on descend gamma
			p.majgamma();
			J.setGamma(p.getGamma());
			Collections.shuffle(p.getEtat());
			
		}
		Writer.ecriture(compteurpourlasortie,distanceBest, sortie);
		
		return distanceBest;

	}
	
	



}
