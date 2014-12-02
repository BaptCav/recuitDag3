import java.util.ArrayList;
//import java.util.NoSuchElementException;
//import java.util.Scanner;
import java.io.*;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

class Main{
	static int N =20;
	public static Double X = 0.0;
	public static Double Y = 0.0;

	public static ArrayList<Double> coordX = new ArrayList<Double>();
	public static ArrayList<Double> coordY = new ArrayList<Double>();
	public static void main(String[] args) throws IOException{


	try {
 		
		ExcelManager excelManager = new ExcelManager("/Users/thomasdoutre/Desktop/Test.xls");
			String fichier = "/Users/thomasdoutre/Desktop/ALLTSP/TOUS/Brazil58.tsp";
			Graphe g = new Graphe(TSPParser.donneMatrice(fichier));
			
			/*
public Color getColor(double power)
{
    double H = power * 0.4; // Hue (note 0.4 = Green, see huge chart below)
    double S = 0.9; // Saturation
    double B = 0.9; // Brightness

    return Color.getHSBColor((float)H, (float)S, (float)B);
}
			 */
			
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
				for(int i=90; i<112; i++){
					
					String nomBenchmark = excelManager.lireCellule("/Users/thomasdoutre/Desktop/BilanTSP.xls", i, 0).replace(" ", "");
					fichier = "/Users/thomasdoutre/Desktop/ALLTSP/TOUS/"+nomBenchmark+".tsp";
					g = new Graphe(TSPParser.donneMatrice(fichier));
					
				for(int j=0; j<N; j++)
				{
					Writer.creerFichierEnergie(j);
					Writer.creerFichierProba();
					
					Recuit.solution(g, l);
					excelManager.modifierCellule("/Users/thomasdoutre/Desktop/BilanTSP.xls",i, j+6, Recuit.solutionNumerique);
					Writer.energie.close();
					Writer.proba.close();

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
