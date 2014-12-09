import java.util.ArrayList;
import java.io.*;


class Main{
	static int N =20;
	public static Double X = 0.0;
	public static Double Y = 0.0;

	public static ArrayList<Double> coordX = new ArrayList<Double>();
	public static ArrayList<Double> coordY = new ArrayList<Double>();
	public static void main(String[] args) throws IOException, InterruptedException{


			String fichier = "C:/Users/belaube_pie/Desktop/pr1002.tsp";
			Graphe g = new Graphe(TSPParser.donneMatrice(fichier));


			ArrayList<Integer> tab_min = new ArrayList<Integer>();
			Routage route = new Routage(g,tab_min);
			ArrayList<Integer> l = route.getRoute();
			g = new Graphe(TSPParser.donneMatrice(fichier));
			double[][] matrice = TSPParser.donneMatrice(fichier);
			int[][] matIndex = DistanceTools.sortIndex(matrice);

			Recuit.solution(matIndex, g, l);
	}
}
