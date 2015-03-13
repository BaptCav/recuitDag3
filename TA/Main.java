import modele.*;
import io.*;

import java.io.*;

import recuit.*;



class Main{
	static int N =208;
	public static void main(String[] args) throws IOException{
		// Pour lancer le recuit, la méthode est Recuit.solution(g,nombreIterations,sortie)
		// g est le graphe
		// nombreIterations est le nombre total d'iterations
		// sortie est le PrintWriter pour récupérer le résultat en .txt
		try {

			//ExcelManager excelManager = new ExcelManager("C:/Users/berge_pie/Desktop/BilanTSPLog.xls");
			String fichier = "C:/Users/Pierre/Desktop/benchmark/brazil58.tsp";
			Graphe g = new Graphe(TSPParser.donneMatrice(fichier));
			
			try
			{
				for(int i=0; i<1; i++){

					//String nomBenchmark = excelManager.lireStringCellule(i, 0).replace(" ", "");
					fichier = "C:/Users/Pierre/Desktop/benchmark/"+"swiss42"+".tsp";
					g = new Graphe(TSPParser.donneMatrice(fichier));
					int n = g.getdists().length;
					
					for(int j=0; j<100; j++)
					{
						
						
						PrintWriter sortie = new PrintWriter("swiss42_"+i+"_"+0+".txt");
					
						Recuit.solution(g,n*n,sortie);
						sortie.close();
						//excelManager.modifierCellule(i,j,Recuit.solutionNumerique);
						
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
