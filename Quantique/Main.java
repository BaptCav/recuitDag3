import modele.*;
import io.*;

import java.util.ArrayList;
import java.io.*;

import recuit.*;



class Main{
	static int N =208;

	public static void main(String[] args) throws IOException{


		try {

			//ExcelManager excelManager = new ExcelManager("C:/Users/cavarec_bap/Desktop/BilanTSP.xls");
			String fichier = "C:/Users/Pierre/Desktop/benchmark/brazil58.tsp";
			Graphe g = new Graphe(TSPParser.donneMatrice(fichier));

			try
			{
				for(int i=0; i<300; i+=300){

					//String nomBenchmark = excelManager.lireStringCellule(i, 0).replace(" ", "");
					//fichier = "C:/Users/Pierre/Desktop/benchmark/"+"brazil58"+".tsp";
					//Je mets ici un exemple de petit graphe. 
					//C'est utile pour tester le comportement du recuit sur peu d'itérations avec de petites routes.
					double[] tab1 = {0,1,4,3,4};
					double[] tab2 = {1,0,3,1,3};
					double[] tab3 = {4,3,0,2,4};
					double[] tab4 = {3,1,2,0,1};
					double[] tab5 = {4,3,4,1,0};
					double[][] tab = {tab1,tab2,tab3,tab4,tab5};
					//g = new Graphe(tab);
					//g = new Graphe(TSPParser.donneMatrice(fichier));
			
					Routage route = new Routage(g);
					ArrayList<Integer> l = route.getRoute();
					double result = 0;

					for(int j=0; j<1; j++)
					{
						Recuit c= new Recuit();
						result += c.solution(g,5,20*58*58,1,1);
						//excelManager.modifierCellule(i,j,c.solutionNumerique);
						//System.out.println(j);
					} 
					System.out.println("indice : " + i);
					System.out.println("ecart :" + ((result-118282*30)/(118282*30))*100);
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
