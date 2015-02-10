import java.io.IOException;
import java.util.Collections;


public class Iteration {

	static int energieCourante = 100;
	static int energieNouvelle;
	static double temperature = 500;
	static double refroidissement = 0.0001;
	static double gamma = 500;

	public static double probaAcceptation(double energieCourante, double energieNouvelle, double temperature)
	{
		if(energieNouvelle<=energieCourante)
		{
			return 1;
		}
		else
		{
			return Math.exp((energieCourante - energieNouvelle) / (0.1*temperature));
		}
	}

	public static Particule iterer(int[][] matrice, Particule particule, int heightImg, int widthImg)
	{	
		Particule resultat = new Particule(particule.getEtats().size());
		resultat = particule;

		if (temperature > 10) {
			
			Etat mutation = new Etat();
			Particule particuleMutee = new Particule(particule.getEtats().size());
			particuleMutee.clone(particule);
			Collections.shuffle(particule.getEtats());
			
			for(int i=0; i<particule.getEtats().size();i++)
			{
			
				mutation = Mutation.faire(particule.getEtats().get(i),heightImg,widthImg);
				particuleMutee.getEtats().set(i,mutation);
				
				double termeInteraction = (temperature/2)*Math.log(Math.tanh(gamma/(particule.getEtats().size()*temperature)));
				
				double EPCourante = matrice[particule.getEtats().get(i).getCoordonneesX()][particule.getEtats().get(i).getCoordonneesY()];
				double EPVoisine = matrice[mutation.getCoordonneesX()][mutation.getCoordonneesY()];
				
				double ECCourante = termeInteraction*(particule.sumDistance(i));
				double ECVoisine = termeInteraction*(particuleMutee.sumDistance(i));
				
				double energieCourante = EPCourante-ECCourante;
				double energieVoisine = EPVoisine-ECVoisine;

				double p = probaAcceptation(energieCourante, energieVoisine, temperature);
				
				//System.out.println("(ECCourante-ECVoisine) = "+(ECCourante-ECVoisine));
				//System.out.println("(EPCourante-EPVoisine) = "+(EPCourante-EPVoisine));
				
				if (p >= Math.random()) {
					resultat.getEtats().set(i,mutation);
				}
				else
				{
					resultat.getEtats().set(i, particule.getEtats().get(i));
				}
			}
			temperature *=1 - refroidissement;
			gamma *= 1- refroidissement;
		}

		return resultat;
	}
}
