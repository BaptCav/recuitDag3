import javax.swing.*;
import java.io.IOException;

// Cette classe contient la mÃƒÂ©thode main. Elle initialise la zone de dessin et ses composantes. /*

public class Main extends JFrame {
	
	private static final long serialVersionUID = 1L;
	public static ZoneDessin zoneDessin;

	// Affichage de la fenetre
    public static void main(String[] args) throws InterruptedException, IOException {
    	LecteurImage lectureImage = new LecteurImage("C:/Users/Belaube/Desktop/heightmap2.png");
		int[][] matrice = lectureImage.returnMatrix();
		
		Particule particule;
		int nbreEtats = 10;
		
		zoneDessin = new ZoneDessin(matrice);
		particule = new Particule(nbreEtats);
		
		// initialisation des energies potentielles
		for(int i=0; i<particule.getEtats().size();i++){
			particule.getEtats().get(i).setEnergiePotentielle(matrice);
		}
		
		zoneDessin.setParticule(particule);
		Recuit.solution(matrice, particule, zoneDessin);
		//zoneDessin.repaint();
    }
}
