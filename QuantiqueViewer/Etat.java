
public class Etat {
	 int CoordX;
	 int CoordY;
	    
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
	   
	   // setters coordonnees
	   public void setCoordonneesX(int x){
		   this.CoordX = x;
	   }
	   
	   public void setCoordonneesY(int y){
		   this.CoordY = y;
	   }
}
