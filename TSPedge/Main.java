
import java.util.ArrayList;
//import java.util.NoSuchElementException;
//import java.util.Scanner;
import java.io.*;

class Main{
	static int N =20;
	public static Double X = 0.0;
	public static Double Y = 0.0;

	public static ArrayList<Double> coordX = new ArrayList<Double>();
	public static ArrayList<Double> coordY = new ArrayList<Double>();
	public static void main(String[] args) throws IOException{


	try {
 
 /* 
			File fichier = new File("C:/minlocalbis.txt");
			Scanner scanner = new Scanner(fichier);
			int k=1;
			while (true)
			{
				try
				{
					numero = k;
					k+=1;
					*/

					
					
// Ici, deux tests sont possible : un sur des benchmarks ayant pour entr�e les coordonn�es des villes, l'autre sur des graphes d�finis par les matrices d'adjacence. 					
// A partir de maintenant, test pour gros graphes (Exemple pr1002) 
					/*X = scanner.nextDouble();
					Y = scanner.nextDouble();
					coordX.add(X);
					coordY.add(Y);
					System.out.println("Le point numero "+numero+" de coord "+X+","+Y+" a ete charge");
				}
				catch(NoSuchElementException exception)
				{
					break;
				}
			}
			double[][] matrice = new double[58][58];
			for (int i=1;i<=58;i++)
			{
				for (int j=i+1; j<=58;j++)
				{
					matrice[i-1][j-1] = Math.sqrt((coordX.get(i-1)-coordX.get(j-1))*(coordX.get(i-1)-coordX.get(j-1)) + (coordY.get(i-1)-coordY.get(j-1))*(coordY.get(i-1)-coordY.get(j-1)));
					matrice[j-1][i-1] = matrice[i-1][j-1];
				}
			}	*/
	//Fin test gros graphes	avec coordonn�es			
	// D�but Test d'une matrice 5x5 (Exemple de Pierre-Alain ou celui de Pierre)				
					
		
		
		

		
				/*	X = scanner.nextDouble();
					coordX.add(X);
					System.out.println("Le coefficient numero "+numero+" de valeur "+X+" a ete charge");
					}
					catch(NoSuchElementException exception)
					{
						break;
					}
			}
					double[][] matrice = new double[5][5];
					for (int i=1;i<=5;i++)
					{
						for (int j=1; j<=5;j++)
						{
							matrice[i-1][j-1] = coordX.get((5*(i-1))+(j-1));
						}
					}*/
			//Fin test

					
		
		
			String fichier = "/Users/thomasdoutre/Desktop/ALLTSP/Coordonnees/DOUBLE/ch130.tsp";

			Graphe g = new Graphe(TSPParser.donneMatrice(fichier));
			
			
			ArrayList<Integer> tab_min = new ArrayList<Integer>();
			tab_min.add(0);
			tab_min.add(1);
			tab_min.add(2);
			tab_min.add(3);
			tab_min.add(4);
			Routage2 route = new Routage2(g,tab_min);
			ArrayList<Integer> l = route.getRoute();
			try
			{
				for(int i=0; i<N; i++)
				{
					PrintWriter sortie = new PrintWriter(new FileWriter("resultats1."+i+".txt"));
					Recuit.solution(g, l, sortie);
					System.out.println(i);
					sortie.close();
				}
			}
			catch (InterruptedException e){
				e.printStackTrace();
			}
		} catch (IOException e) {
			System.err.println("Error: " + e);
		} catch (NumberFormatException e) {
			System.err.println("nombre invalide...");
		}
	}
}
