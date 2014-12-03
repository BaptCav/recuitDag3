import java.util.ArrayList;
import java.io.*;


class Main{
	static int N =20;
	public static Double X = 0.0;
	public static Double Y = 0.0;

	public static ArrayList<Double> coordX = new ArrayList<Double>();
	public static ArrayList<Double> coordY = new ArrayList<Double>();
	public static void main(String[] args) throws IOException{


		try {

			ExcelManager excelManager = new ExcelManager("/Users/thomasdoutre/Desktop/BilanTSP.xls");
			String fichier = "/Users/thomasdoutre/Desktop/ALLTSP/TOUS/Brazil58.tsp";
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
				for(int i=65; i<87; i++){

					String nomBenchmark = excelManager.lireStringCellule(i, 0).replace(" ", "");
					fichier = "/Users/thomasdoutre/Desktop/ALLTSP/TOUS/"+nomBenchmark+".tsp";
					g = new Graphe(TSPParser.donneMatrice(fichier));

					for(int j=11; j<26; j++)
					{

						Recuit.solution(g, l);
						excelManager.modifierCellule(i, j, Recuit.solutionNumerique);

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
