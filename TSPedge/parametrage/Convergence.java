package parametrage;

public class Convergence {

	// L'entier vaut 1 si la convergence est exponentielle, 0 si elle est linéaire.
	// A MODIFIER (pour l'instant, c'est pratique)
	int convergence;
	
	public Convergence(int convergence){
		this.convergence = convergence;
	}
	
	public int getConvergence(){
		return this.convergence;
	}
}