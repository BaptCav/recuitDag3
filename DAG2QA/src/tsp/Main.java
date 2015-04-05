package tsp;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import solver.commun.IRecuit;
import solver.parametres.ConstanteK;
import solver.parametres.ConstanteKConstant;
import solver.parametres.FonctionLineaire;
import solver.quantique.RecuitQuantique;
import solver.quantique.RecuitQuantiqueAccelere;

public class Main {

	public static void main(String[] args) throws IOException{


		
			
			String fichier = "C:/Users/Pierre/Desktop/benchmark/swiss42.tsp";
			Graphe g = new Graphe(TSPParser.donneMatrice(fichier));

			
				for(int i=5; i<6; i+=5){
					for(int j = 0; j<2; j+=50){

					
					fichier = "C:/Users/Pierre/Desktop/benchmark/a280.tsp";
					
					g = new Graphe(TSPParser.donneMatrice(fichier));
			
					Routage route = new Routage(g);
					PrintWriter sortie = new PrintWriter("a280DAG2_sanscondition_accelere.txt");
					ArrayList<Integer> l = route.getRoute();
					int n = g.getdists().length;
					double startTime = System.currentTimeMillis();
					for(int k=0; k<100; k++)
					{
						ParticuleTSP p = new ParticuleTSP(new EnergiePotentielleTsp(), new TwoOptMove(), new EnergieCinetiqueTsp(), 1, 10, g, 1);
						p.initialiser();
						FonctionLineaire gamma = new FonctionLineaire(10.0,0,10*n*n);
						ConstanteKConstant K = new ConstanteKConstant(1);
						RecuitQuantiqueAccelere r = new RecuitQuantiqueAccelere(gamma,K,1,0.1);
						double result = r.lancer(p,0);
						Writer.ecriture(k,result,sortie);
						
					} 
					System.out.println("time : "+ (System.currentTimeMillis()-startTime));
					sortie.close();
					}
				}
			}
		
}
