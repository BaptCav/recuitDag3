import java.io.IOException;


public class Iteration {

	static int energieCourante = 100;
	static int energieNouvelle;
	static double temperature = 500;
	static double refroidissement = 0.00001;
	
	public static double probaAcceptation(int energieCourante, int energieNouvelle, double temperature, int threshold)
	{
		if(energieNouvelle<=energieCourante)
		{
			return 1;
		}
		if(threshold == 1)
		{
			return 0;
		} 
		else
		{
			return Math.exp((energieCourante - energieNouvelle) / (0.1*temperature));
		}
	}
	
	public static Coordonnees iterer(int[][] matrice, Coordonnees current, int heightImg, int widthImg, int threshold)
	{	
		Coordonnees resultat = new Coordonnees();
		resultat = current;
		
		if (temperature > 10) {
				
				Coordonnees mutation = new Coordonnees();
				mutation = Mutation.faire(current,heightImg,widthImg);
			
				int energieCourante = matrice[current.CoordX][current.CoordY];
				int energieVoisine = matrice[mutation.CoordX][mutation.CoordY];
				
				double p = probaAcceptation(energieCourante, energieVoisine, temperature, threshold);
				System.out.println("temperature = "+temperature);

				if (p >= Math.random()) {
					resultat = mutation;
				}

			temperature *=1 - refroidissement;
		}
		
		return resultat;
	}
}
