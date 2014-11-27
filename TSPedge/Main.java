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
 		
			String fichier = "/Users/thomasdoutre/Desktop/ALLTSP/TOUS/Brazil58.tsp";

			Graphe g = new Graphe(TSPParser.donneMatrice(fichier));
			
			
			ArrayList<Integer> tab_min = new ArrayList<Integer>();
			tab_min.add(0);
			tab_min.add(1);
			tab_min.add(2);
			tab_min.add(3);
			tab_min.add(4);
			Routage route = new Routage(g,tab_min);
			ArrayList<Integer> l = route.getRoute();
			
			try
			{
				for(int i=0; i<N; i++)
				{
					Writer.creerFichierEnergie(i);
					Writer.creerFichierProba();
					
					Recuit.solution(g, l);
					
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
