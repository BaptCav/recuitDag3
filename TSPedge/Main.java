import modele.*;
import mutation.TwoOptMove;
import io.*;

import java.util.ArrayList;
import java.io.*;

import recuit.*;



class Main{
	static int N =208;
	public static void main(String[] args) throws IOException{
		try {

			ExcelManager excelManager = new ExcelManager("C:/Users/berge_pie/Desktop/BilanTSPlin.xls");
			String fichier = "C:/Users/berge_pie/Desktop/benchmark/Brazil58.tsp";
			Graphe g = new Graphe(TSPParser.donneMatrice(fichier));
			Routage route = new Routage(g);
			ArrayList<Integer> l = new ArrayList<Integer>();
			
			try
			{
				for(int i=12; i<111; i++){

					String nomBenchmark = excelManager.lireStringCellule(i, 0).replace(" ", "");
					fichier = "C:/Users/berge_pie/Desktop/benchmark/"+nomBenchmark+".tsp";
					g = new Graphe(TSPParser.donneMatrice(fichier));
					route = new Routage(g);
					l = route.getRoute();
					int n = g.getdists().length;
					
					for(int j=8; j<N; j++)
					{
						
					
						Recuit.solution(g, l,n*n);
						excelManager.modifierCellule(i,j,Recuit.solutionNumerique);
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
