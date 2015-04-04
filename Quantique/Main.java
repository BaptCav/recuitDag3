import modele.*;
import mutation.*;
import io.*;
import io.Writer;

import java.util.ArrayList;
import java.io.*;

import parametrage.*;
import recuit.*;



class Main{
	static int N =208;

	public static void main(String[] args) throws IOException{


		try {

			
			String fichier = "C:/Users/Pierre/Desktop/benchmark/a280.tsp";
			Graphe g = new Graphe(TSPParser.donneMatrice(fichier));

			try
			{
				for(int i=2; i<3; i+=3){

					
					fichier = "C:/Users/Pierre/Desktop/benchmark/a280.tsp";
					
					g = new Graphe(TSPParser.donneMatrice(fichier));
			
					Routage route = new Routage(g);
					
					ArrayList<Integer> l = route.getRoute();
					double result = 0;
					int n = g.getdists().length;
					int nombreEtat=10;
					PrintWriter sortie = new PrintWriter("a280DAG3_sanscondition.txt");
					double startTime = System.currentTimeMillis();
					for(int j=0; j<100; j++)
					{
				
						Recuit c= new Recuit();
						ArrayList<Etat> r = new ArrayList<Etat>(nombreEtat);
						for(int indice=0; indice<nombreEtat; indice++){
							r.add(new Routage(g));
						}
						for(int id=1; id<r.size()-1; id++){
							r.get(id).setprevious(r.get(id-1));
							r.get(id).setnext(r.get(id+1));
						}
						r.get(r.size()-1).setprevious( r.get(r.size()-2));
						r.get(r.size()-1).setnext( r.get(0));
						r.get(0).setprevious( r.get(r.size()-1));
						r.get(0).setnext(r.get(1));
						ParticuleTSP p=new ParticuleTSP(g,r);
						p.setEcin(new EnergieCinetiqueTsp());
						p.setEpot(new EnergiePotentielleTsp());
						TwoOptMove m = new TwoOptMove((Routage) r.get(0));
						
						result = c.solution(p,m,10*n*n,1,1);
						System.out.println(result);
						Writer.ecriture(j,result,sortie);
						
				
					} 
					System.out.println("time : "+ (System.currentTimeMillis()-startTime));
					System.out.println("stop");
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
