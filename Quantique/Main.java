import modele.*;
import mutation.TwoOptMove;
import io.*;

import java.util.ArrayList;
import java.io.*;

import parametrage.ParametreGamma;
import recuit.*;



class Main{
	static int N =208;

	public static void main(String[] args) throws IOException{


		try {

			//ExcelManager excelManager = new ExcelManager("C:/Users/cavarec_bap/Desktop/BilanTSP.xls");
			String fichier = "C:/Users/cavarec_bap/Desktop/benchmark/swiss42.tsp";
			Graphe g = new Graphe(TSPParser.donneMatrice(fichier));

			try
			{
				for(int i=0; i<300; i+=300){

					//String nomBenchmark = excelManager.lireStringCellule(i, 0).replace(" ", "");
					fichier = "C:/Users/cavarec_bap/Desktop/benchmark/"+"brazil58"+".tsp";
					//Je mets ici un exemple de petit graphe. 
					//C'est utile pour tester le comportement du recuit sur peu d'itérations avec de petites routes.
					double[] tab1 = {0,1,4,3,4};
					double[] tab2 = {1,0,3,1,3};
					double[] tab3 = {4,3,0,2,4};
					double[] tab4 = {3,1,2,0,1};
					double[] tab5 = {4,3,4,1,0};
					double[][] tab = {tab1,tab2,tab3,tab4,tab5};
					//g = new Graphe(tab);
					g = new Graphe(TSPParser.donneMatrice(fichier));
			
					Routage route = new Routage(g);/*
					System.out.println("route init :" + route.toString());
					route.afficheIsing();
					ArrayList<Etat> r = new ArrayList<Etat>();
					r.add(route);
					r.add(route);
					r.add(route);
					ParticuleTSP p = new ParticuleTSP(r,1,new ParametreGamma(1.0,0,0));
					TwoOptMove m = new TwoOptMove(5);
					System.out.println("m(i) :" + m.getI());
					System.out.println("m(j) :" + m.getJ());
					System.out.println("diffspin " + p.differenceSpinsTwoOpt(1,m));
					Routage route2 = (Routage) m.faire(new ParticuleTSP(0),route);
					System.out.println("route2 mutee :" + route2.toString());
					route2.afficheIsing();*/
					ArrayList<Integer> l = route.getRoute();
					double result = 0;

					for(int j=0; j<100; j++)
					{
				
						PrintWriter sortie = new PrintWriter("brazil58MC_"+(j)+".txt");
					
						Recuit c= new Recuit();
						result += c.solution(g,10,10*58*58,1,1,sortie);
						sortie.close();
						
						//excelManager.modifierCellule(i,j,c.solutionNumerique);
						//System.out.println(j);
					} 
					//System.out.println("indice : " + i);
					//System.out.println("ecart :" + ((result-118282*30)/(118282*30))*100);
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
