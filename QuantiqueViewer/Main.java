import javax.swing.*;

import java.awt.event.*;
import java.awt.BorderLayout;
import java.io.IOException;

// Cette classe contient la mÃƒÂ©thode main. Elle initialise la zone de dessin et ses composantes. /*

public class Main extends JFrame {
	
	private static final long serialVersionUID = 1L;
	public static ZoneDessin zoneDessin;

	// Affichage de la fenetre
    public static void main(String[] args) throws InterruptedException, IOException {
    	LecteurImage lectureImage = new LecteurImage("C:/Users/Belaube/Desktop/heightmap3.png");
		int[][] matrice = lectureImage.returnMatrix();
		zoneDessin = new ZoneDessin(matrice);
    }
}
