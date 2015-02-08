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
			String fichier = "C:/Users/Pierre/Desktop/benchmark/swiss42.tsp";
			Graphe g = new Graphe(TSPParser.donneMatrice(fichier));

			try
			{
				for(int i=1; i<2; i++){

					//String nomBenchmark = excelManager.lireStringCellule(i, 0).replace(" ", "");
					fichier = "C:/Users/Pierre/Desktop/benchmark/"+"swiss42"+".tsp";
					//Je mets ici un exemple de petit graphe. 
					//C'est utile pour tester le comportement du recuit sur peu d'itérations avec de petites routes.
					double[] tab1 = {0,1,4,3,4,1,1};
					double[] tab2 = {1,0,3,1,3,1,1};
					double[] tab3 = {4,3,0,2,4,1,1};
					double[] tab4 = {3,1,2,0,1,1,1};
					double[] tab5 = {4,3,4,1,0,1,2};
					double[] tab6 = {1,1,1,1,1,0,1};
					double[] tab7 = {1,1,1,1,2,1,0};
					double[][] tab = {tab1,tab2,tab3,tab4,tab5,tab6,tab7};
					//g = new Graphe(tab);
					g = new Graphe(TSPParser.donneMatrice(fichier));
					/*ParticuleTSP p = new ParticuleTSP(1);
					
					ArrayList<Integer> liste = new ArrayList<Integer>();
					liste.add(0);
					liste.add(1);
					liste.add(2);
					liste.add(3);
					liste.add(4);
					liste.add(5);
					liste.add(6);
					
					Routage routeinit = new Routage(g,liste);
					Routage routetemp = new Routage(g,liste);
					double var = 0;
					
					for (int cpt=0; cpt<50 ; cpt++){
						routeinit = routetemp.clone();
						//System.out.println(routeinit.toString());
						TwoOptMove m = new TwoOptMove(7);
						//System.out.println("i :" + m.getI());
						//System.out.println("j :" + m.getJ());
						var = m.calculer(p,routetemp);
						m.faire(p,routetemp);
						//System.out.println(routetemp.toString());
						System.out.println("VAR :" + var);
						System.out.println("DISTANCES :" + (routetemp.getDistance() - routeinit.getDistance()));
					}
			*/
					Routage route = new Routage(g);
					
					ArrayList<Integer> l = route.getRoute();
					double result = 0;
					double rapprochement=0;
					int n = g.getdists().length;
					
					
					for(int j=0; j<30; j++)
					{	PrintWriter s= new PrintWriter("gr96qa_"+i+"."+j+".txt");
						Recuit c= new Recuit();
						double[] t = c.solution(g,20,5*n*n,1,1,200,s);
						result += t[0];
						rapprochement += t[1];
						s.close();
						//excelManager.modifierCellule(i,j,c.solutionNumerique);
						//System.out.println(j);
					} 
					System.out.println("indice : " + i);
					System.out.println("ecart :" + ((result-1273*30)/(1273*30))*100); 
					System.out.println("rapprochement :" + rapprochement/30);
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
