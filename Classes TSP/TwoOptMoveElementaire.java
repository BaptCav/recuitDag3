package tsp;

import solver.commun.MutationElementaire;

public class TwoOptMoveElementaire extends MutationElementaire {
	
	int taille;
	int i;
	int j;
	
	public TwoOptMoveElementaire(int taille, int i, int j){
		this.taille = taille;
		this.i = i;
		this.j = j;
		
	}
	
	public int getI(){
		return this.i;
	}
	
	public int getJ(){
		return this.j;
	}
	
	public int getTaille(){
		return this.taille;
	}
	
	public void setI(int i){
		this.i = i;
	}
	
	public void setJ(int j){
		this.j = j;
	}
	
	public void setTaille(int taille){
		this.taille = taille;
	}
	

}
