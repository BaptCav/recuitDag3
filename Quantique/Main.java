import modele.*;
import io.*;

import java.util.ArrayList;
import java.io.*;

import recuit.*;



class Main{
	static int N =208;

	public static void main(String[] args) throws IOException{


		try {

			ExcelManager excelManager = new ExcelManager("C:/Users/cavarec_bap/Desktop/BilanTSP.xls");
			String fichier = "C:/Users/cavarec_bap/Desktop/benchmark/Brazil58.tsp";
			Graphe g = new Graphe(TSPParser.donneMatrice(fichier));

			try
			{
				for(int i=11; i<12; i++){

					String nomBenchmark = excelManager.lireStringCellule(i, 0).replace(" ", "");
					fichier = "C:/Users/cavarec_bap/Desktop/benchmark/"+nomBenchmark+".tsp";
					g = new Graphe(TSPParser.donneMatrice(fichier));
			
					Routage route = new Routage(g);
					ArrayList<Integer> l = route.getRoute();


					for(int j=8; j<N; j++)
					{
						Recuit c= new Recuit();
						c.solution(g,20,100000,1,10);
						excelManager.modifierCellule(i,j,c.solutionNumerique);
						System.out.println(j);
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
