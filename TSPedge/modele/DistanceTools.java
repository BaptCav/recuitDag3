package modele;

import java.util.Arrays;
import java.util.Comparator;

class IndexValue {
   final int i;
   final double v;

   public IndexValue( int i, double v ) {
       this.i = i;
       this.v = v;
   }
}

public class DistanceTools {

	public static int[][] sortIndex(double[][] matDistance)
	{
		IndexValue[] tabIndex = new IndexValue[matDistance.length];
		int[][] resultats = new int[matDistance.length][matDistance.length]; // Matrice rÃ©sultat

		for( int i = 0 ; i < matDistance.length ; i++) // aller de la ligne 0 Ã  N-1
		{
			double ligneI[] = extractRow(matDistance, i); // extraire de la matrice des distances la ligne i 
			for( int j = 0 ; j < matDistance.length; j++ ) // aller de la colonne 0 Ã  N-1
			{
				tabIndex[j] = new IndexValue( j, ligneI[j] ); // dans le tabIndex stocker [ j, distance j ]
			}

			Arrays.sort( tabIndex, new Comparator<IndexValue>(){public int compare( IndexValue a, IndexValue b ){return (int)(a.v - b.v);}});
			// classer les distances de la ligneI et la stocker dans tabIndex

			for( int l = 0; l < matDistance.length; l++ ) // puis Ã©crire les index dans l'ordre dans rÃ©sultats
			{
				resultats[i][l] = tabIndex[l].i;
			}
		}
		return resultats;
	}

	static public double[] extractRow(double[][] matrice, int i) // extraire la ieme ligne de matrice
	{
		double[] ligne = new double[matrice.length];
		for(int colonne = 0; colonne < matrice.length; colonne++)
		{
			ligne[colonne] = matrice[i][colonne];
		}
		return ligne;
	}
}
