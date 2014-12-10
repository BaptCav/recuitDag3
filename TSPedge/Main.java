import modele.*;
import io.*;

import java.util.ArrayList;
import java.io.*;

import recuit.*;



class Main{
	static int N =100;
	public static Double X = 0.0;
	public static Double Y = 0.0;

	public static ArrayList<Double> coordX = new ArrayList<Double>();
	public static ArrayList<Double> coordY = new ArrayList<Double>();
	public static void main(String[] args) throws IOException{


		try {

			ExcelManager excelManager = new ExcelManager("C:/Users/Baptiste/Desktop/TSP/BilanTSP.xls");
			String fichier = "C:/Users/Baptiste/Desktop/TSP/benchmark/Brazil58.tsp";
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
				for(int i=20; i<50; i++){

					String nomBenchmark = excelManager.lireStringCellule(i, 0).replace(" ", "");
					fichier = "C:/Users/Baptiste/Desktop/TSP/benchmark/"+nomBenchmark+".tsp";
					g = new Graphe(TSPParser.donneMatrice(fichier));
					tab_min = new ArrayList<Integer>();
					tab_min.add(0);
					tab_min.add(1);
					tab_min.add(2);
					tab_min.add(3);
					tab_min.add(4);
					route = new Routage(g,tab_min);
					l = route.getRoute();


					for(int j=11; j<26; j++)
					{

						Recuit.solution(g, l);

					} 

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
