public class Recuit {

	static double energieCourante;
	static double energieVoisine;
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

	public static void solution(int[][] matrice, Particule particule, ZoneDessin zoneDessin) throws InterruptedException
	{			
		while(temperature > 10) {
			
			Etat meilleur = particule.findBest(matrice); //on trouve le meilleur pour ce tour, puis on calcule la distance explicitement
			Etat mutation = new Etat();
			Particule particuleMutee = new Particule(particule.getEtats().size());
			Particule sauvegarde = new Particule(particule.getEtats().size());
		
			particuleMutee.clone(particule);
			int i = 0;
			double ECCourante = 0;
			double ECVoisine = 0;
	
			while(i<particule.getEtats().size())
			{
				double EPCourante = 0;
				double EPVoisine = 0;
				energieCourante = 0;
				energieVoisine = 0;
				
				zoneDessin.setMeilleurEtat(particule.findBest(matrice));
				
				mutation = Mutation.faire(particule.getEtats().get(i),zoneDessin.getHeight(),zoneDessin.getWidth());
				particuleMutee.getEtats().set(i,mutation);
				double termeInteraction = (temperature/2)*Math.log(Math.cosh(gamma/(particule.getEtats().size()*temperature)));
				
				EPCourante = matrice[particule.getEtats().get(i).getCoordonneesX()][particule.getEtats().get(i).getCoordonneesY()];
				EPVoisine = matrice[mutation.getCoordonneesX()][mutation.getCoordonneesY()];
				ECCourante = Math.sqrt(Math.pow(meilleur.getCoordonneesX()-particule.getEtats().get(i).getCoordonneesX(),2)+Math.pow(meilleur.getCoordonneesY()-particule.getEtats().get(i).getCoordonneesY(),2));
				ECVoisine = Math.sqrt(Math.pow(meilleur.getCoordonneesX()-mutation.getCoordonneesX(),2)+Math.pow(meilleur.getCoordonneesY()-mutation.getCoordonneesY(),2));
				
				energieCourante = EPCourante+termeInteraction*ECCourante;
				energieVoisine = EPVoisine+termeInteraction*ECVoisine;
				
				double p = probaAcceptation(energieCourante, energieVoisine, temperature);
				
				if (p >= Math.random()) {
					particule.getEtats().set(i,mutation);
				}
				else
				{
					particule.getEtats().set(i, particule.getEtats().get(i));
				}
				//particule.getEtats().get(i).setEnergiePotentielle(matrice);
				//particule.getEtats().get(i).setEnergieCinetique(matrice, particule);
				
				i=i+1;
				//System.out.println(i);
			}
			
			Thread.sleep(1);
			zoneDessin.repaint();
			
			temperature *= 1 - refroidissement;
			gamma *= 1 + 0.1*refroidissement;
		}
	}
}
