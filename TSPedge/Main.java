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
 		
			String fichier = "C:/Users/belaube_pie/Desktop/bayg29.tsp";

			Graphe g = new Graphe(TSPParser.donneMatrice(fichier));
			ArrayList<Integer> tab_min = new ArrayList<Integer>(); 
			Routage route = new Routage(g,tab_min); 
			ArrayList<Integer> l = route.getRoute(); 

			double[][] matriceDistance = TSPParser.donneMatrice(fichier);
			int[][] matriceIndex = DistanceTools.sortIndex(matriceDistance);
			
			try
			{
				for(int i=0; i<N; i++)
				{
					Writer.creerFichierEnergie(i);
					Writer.creerFichierProba();
					
					Recuit.solution(matriceIndex, g, l);
					
					Writer.energie.close();
					Writer.proba.close();

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
