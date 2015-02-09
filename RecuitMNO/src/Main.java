import javax.swing.*;

import java.awt.event.*;
import java.awt.BorderLayout;
import java.io.IOException;

// Cette classe contient la mÃ©thode main. Elle initialise la zone de dessin et ses composantes. /*

public class Main extends JFrame {
	
	private static final long serialVersionUID = 1L; // bidouille obscurantiste tant qu'Ã©sotÃ©rique pour se dÃ©barasser d'un warning...
	private ZoneDessin zoneDessin;
    
	public Main(String title, int[][] matrice) throws InterruptedException, IOException 
	{
		initZoneDessin(matrice);
	}
	

	
	// initialise la zone de dessin...
	protected void initZoneDessin(int[][] matrice) throws InterruptedException, IOException {
		this.zoneDessin = new ZoneDessin(matrice);
	}

	// Affichage de la fenÃªtre
    public static void main(String[] args) throws InterruptedException, IOException {
    	LecteurImage lectureImage = new LecteurImage("C:/Users/Baptiste/Desktop/MNO/lake2.png");
		int[][] matrice = lectureImage.returnMatrix();
    	Main app = new Main("Illustration Recuit Simule", matrice);
    }
}
