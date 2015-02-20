
public class Etat {
	 int CoordX;
	 int CoordY;
	 int energiePotentielle;
	 double energieCinetique;
	 
	    // Construire un nouveau noeud avec des coordonnÃ©es alÃ©atoires
	   public Etat(){
	       this.CoordX = 0;
	       this.CoordY = 0;
	    }
	   
	   public Etat(int x, int y){
	       this.CoordX = x;
	       this.CoordY = y;
	    }
	   
	   // getters coordonnees
	   public int getCoordonneesX(){
		   return CoordX;
	   }
	   
	   public int getCoordonneesY(){
		   return CoordY;
	   }
	   
	   public int getEnergiePotentielle(){
		   return energiePotentielle;
	   }
	   
	   public double getEnergieCinetique(){
		   return energieCinetique;
	   }
	   
	   // setters coordonnees
	   public void setCoordonneesX(int x){
		   this.CoordX = x;
	   }
	   
	   public void setCoordonneesY(int y){
		   this.CoordY = y;
	   }
	   
	   public void setEnergiePotentielle(int[][] matrice)
	   {
		   this.energiePotentielle = matrice[this.CoordX][this.CoordY];
	   }
	   
	   public void setEnergieCinetique(int[][] matrice, Particule particule)
	   {
		   int coordXBest = particule.findBest(matrice).getCoordonneesX();
		   int coordYBest = particule.findBest(matrice).getCoordonneesY();
		   this.energieCinetique = Math.sqrt(Math.pow(this.CoordX-coordXBest,2)+Math.pow(this.CoordY-coordYBest,2));
	   }
}
