import modele.*;
import io.*;

import java.io.*;

import recuit.*;



class Main{
	static int N =208;
	public static void main(String[] args) throws IOException{
		try {

			ExcelManager excelManager = new ExcelManager("C:/Users/berge_pie/Desktop/BilanTSPLog.xls");
			String fichier = "C:/Users/Pierre/Desktop/benchmark/brazil58.tsp";
			Graphe g = new Graphe(TSPParser.donneMatrice(fichier));
			
			try
			{
				for(int i=44; i<45; i++){

					String nomBenchmark = excelManager.lireStringCellule(i, 0).replace(" ", "");
					fichier = "C:/Users/Pierre/Desktop/benchmark/"+nomBenchmark+".tsp";
					g = new Graphe(TSPParser.donneMatrice(fichier));
					int n = g.getdists().length;
					
					for(int j=8; j<13; j++)
					{
						Recuit.solution(g,n*n);
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
