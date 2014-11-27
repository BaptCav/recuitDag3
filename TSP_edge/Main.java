
import java.util.ArrayList;
import java.util.Arrays;
//import java.util.NoSuchElementException;
//import java.util.Scanner;
import java.io.*;
class Main{
	static int N =1;
	public static Double X = 0.0;
	public static Double Y = 0.0;
	public static ArrayList<Double> coordX = new ArrayList<Double>();
	public static ArrayList<Double> coordY = new ArrayList<Double>();
	public static void main(String[] args) throws IOException{
		try {
			//CHANGER LE FICHIER, MARCHE SI LE FICHIER EST UNE MATRICE (exemple brazil58)
			String fichier2 = "/Users/thomasdoutre/Desktop/ALLTSP/Matrices/FULL_MATRIX/swiss42.tsp";
			String fichier3 = "C:/Users/Alain/workspace/TSPedge/pr1002.tsp";
			String fichier4 = "/Users/thomasdoutre/Desktop/ALLTSP/Coordonnees/DOUBLE/ch130.tsp";
			Graphe g = new Graphe(LectureBenchmarks.donneMatrice(fichier3));
			ArrayList<Integer> tab_min = new ArrayList<Integer>();
			Routage2 route = new Routage2(g,tab_min);
			ArrayList<Integer> l = route.getRoute();
			double[][] matrice = LectureBenchmarks.donneMatrice(fichier3);
			int[][] matIndex = DistanceTools.sortIndex(matrice);
			LectureBenchmarks.printMatrix(matIndex);
			try
			{
				for(int i=0; i<N; i++)
				{
					PrintWriter sortie = new PrintWriter(new FileWriter("resultats1."+i+".txt"));
					Recuit.solution(matIndex, g, l, sortie);
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
