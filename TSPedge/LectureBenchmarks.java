import java.util.Locale;
import java.util.Scanner;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;


public class LectureBenchmarks {

	public static double[][] donneMatrice (String stringFichier) {


		BufferedReader br;
		Scanner scanner;


		String thisLine;
		int dimension = 0;
		boolean fullMatrix = false;
		boolean upper = false;
		boolean lower = false;
		try {


			br = new BufferedReader(new InputStreamReader(new FileInputStream(stringFichier)));

			
			String chaine ="";

			
			boolean doitSArreter = false;
			while (((thisLine = br.readLine()) != null)&&!(doitSArreter)) {

				
				
				boolean ligneDeLaMatrice = (thisLine.startsWith(" "));
				for(int j=0; j < 10 ; j++){
					ligneDeLaMatrice = ligneDeLaMatrice || thisLine.startsWith(""+j);
				}

				if(thisLine.startsWith("DISPLAY_DATA_SECTION")||thisLine.startsWith("EOF")){
					doitSArreter = true;
				}

				if (ligneDeLaMatrice){

					chaine = chaine + " " +thisLine;
				} 
				if(thisLine.startsWith("DIMENSION :")){
					dimension = Integer.parseInt(thisLine.replace("DIMENSION : ", ""));
					System.out.println("dimension = " +dimension);
				}
				
				if(thisLine.startsWith("DIMENSION:")){
					dimension = Integer.parseInt(thisLine.replace("DIMENSION: ", ""));
					System.out.println("dimension = " +dimension);
				}
				
				if((thisLine.startsWith("EDGE_WEIGHT_FORMAT")) && (thisLine.replace("EDGE_WEIGHT_FORMAT: ", "").startsWith("LOWER_DIAG_ROW"))){
					lower = true;
				}

				if((thisLine.startsWith("EDGE_WEIGHT_FORMAT")) && (thisLine.replace("EDGE_WEIGHT_FORMAT: ", "").startsWith("FULL_MATRIX"))){
					fullMatrix = true;
				}
				
				if((thisLine.startsWith("EDGE_WEIGHT_FORMAT")) && (thisLine.replace("EDGE_WEIGHT_FORMAT: ", "").startsWith("UPPER_ROW"))){
					upper = true;
				}

			}

			
			
			br.close();
			
			scanner = new Scanner(chaine);

			
			System.out.println("chaine : "+chaine);

			double[][] matrice = new double[dimension][dimension];



			if(lower){

			
				for(int j=0; j < dimension ; j++){
					for (int i=0; i <= j ; i++){
						if (scanner.hasNextInt()) {
							matrice[j][i] = (double)scanner.nextInt();
						}
					}

				}

				double[][] mat = MatrixAddDouble(matrice,transposeMatrixDouble(matrice));


				scanner.close();
				return mat;


			}

			else if(fullMatrix)  {

				
				for(int j=0; j < dimension ; j++){
					for (int i=0; i < dimension ; i++){
						if (scanner.hasNextInt()) {
							matrice[j][i] = (double)scanner.nextInt();
						}
					}
				}



				scanner.close();
				return matrice;

			}

			else if(upper){

				
				for(int j=0; j < dimension ; j++){
					for (int i=j+1; i <dimension ; i++){
						if (scanner.hasNextInt()) {
							matrice[j][i] = (double)scanner.nextInt();
						}
					}

				}



				double[][] mat = MatrixAddDouble(matrice,transposeMatrixDouble(matrice));



				scanner.close();
				return mat;	

			}
			
			else {
				
				double[][] matriceDesCoordonnees = new double[dimension][3];
				
				System.out.println("dimension = " +dimension);
				
				scanner.useLocale(Locale.US);
				
				
				
				
				for(int j=0; j < dimension ; j++){
					for (int i=0; i <3 ; i++){
						
						if (scanner.hasNextDouble()) {
							matriceDesCoordonnees[j][i] = (double)scanner.nextDouble();
							//System.out.println("matriceDesCoordonnees["+j+"]"+"["+i+"] =" + matriceDesCoordonnees[j][i]);
						}

					}

				}
				

				printApproxMatrix(matriceDesCoordonnees);

				
				for (int i=0; i<dimension;i++){
					for (int j=0; j<dimension;j++){
						matrice[i][j] = Math.sqrt((matriceDesCoordonnees[i][1]-matriceDesCoordonnees[j][1])*(matriceDesCoordonnees[i][1]-matriceDesCoordonnees[j][1]) + (matriceDesCoordonnees[i][2]-matriceDesCoordonnees[j][2])*(matriceDesCoordonnees[i][2]-matriceDesCoordonnees[j][2]));
					}
				}

				printApproxMatrix(matrice);
				
			}
			
			scanner.close();
			return matrice;


		} catch (IOException e) {
			System.err.println("Error: " + e);
		} catch (NumberFormatException e) {
			System.err.println("Invalid number");
		}
		
		return null;

	}




	public static int[][] transposeMatrixInt(int [][] m){
		int[][] temp = new int[m[0].length][m.length];
		for (int i = 0; i < m.length; i++)
			for (int j = 0; j < m[0].length; j++)
				temp[j][i] = m[i][j];
		return temp;
	}

	public static double[][] transposeMatrixDouble(double [][] m){
		double[][] temp = new double[m[0].length][m.length];
		for (int i = 0; i < m.length; i++)
			for (int j = 0; j < m[0].length; j++)
				temp[j][i] = m[i][j];
		return temp;
	}

	public static int[][] MatrixAddInt(int[][] A, int[][] B)
	{
		int[][]C = new int[A.length][A[0].length];

		for(int i =0; i < A.length; i++)
		{
			for(int j=0; j < A[i].length;j++)
			{
				C[i][j] = A[i][j] + B[i][j]; 
			}
		}
		return C;
	}

	public static double[][] MatrixAddDouble(double[][] A, double[][] B)
	{
		double[][]C = new double[A.length][A[0].length];

		for(int i =0; i < A.length; i++)
		{
			for(int j=0; j < A[i].length;j++)
			{
				C[i][j] = A[i][j] + B[i][j]; 
			}
		}
		return C;
	}

	public static void printApproxMatrix(double[][] twoDm) {
		System.out.println("=================================================================");
		  for(double[] row : twoDm) {
			  for (double i : row) {
		            System.out.print((int)i);
		            System.out.print("\t");
		        }
		        System.out.println();
	        }
		  System.out.println("=================================================================");
       
    }
}
