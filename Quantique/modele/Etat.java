package modele;

import java.util.Collections;
import java.util.List;

import mutation.IMutation;
import mutation.TwoOptMove;

import parametrage.EnergiePotentielle;

public class Etat {
	
	int[][] ising;
	
	EnergiePotentielle epot= new EnergiePotentielle(0);
	
	public EnergiePotentielle getE(){
		return this.epot;
	}
	
	public void setIsing(int[][] ising){
		this.ising =ising ;
	}
	
	public int[][] getIsing(){
		return this.ising;
	}
	
	//Affiche la matrice d'Ising de la route. Utile pour vérifications
		public void afficheIsing(){
			int[][] M = this.getIsing();
			for(int k =0;k<M.length;k++){
				for(int l =0;l<M.length;l++){
					System.out.print(M[k][l] + " , ");
				}
				System.out.println("");
			}
		}
		
	public int nombredeUn(){
		int cpt = 0 ;
		int n = this.ising.length;
		for (int j = 1; j < n; j++){
			if (this.ising[0][j] == 1) cpt++;
		}
		return cpt;
	}
	
}
