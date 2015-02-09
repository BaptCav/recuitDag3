import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;

import javax.swing.*;
import javax.imageio.ImageIO;

import java.net.URL;

public class ZoneDessin extends JFrame implements ActionListener, MouseListener {
  Image img;
  int widthImg = 0;
  int heightImg = 0;
  Graphics g;
  JButton getPictureButton  = new JButton("Charger Monde");
  Coordonnees coordonnees = new Coordonnees();
  Coordonnees threshold = new Coordonnees();
 
  public ZoneDessin(int[][] matrice) throws InterruptedException, IOException {
    this.setSize(400, 400);
    addMouseListener(this);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JPanel picPanel = new PicturePanel();
    this.add(picPanel, BorderLayout.CENTER);

    JPanel buttonPanel = new JPanel();
    getPictureButton.addActionListener(this);
    buttonPanel.add(getPictureButton);
    this.add(buttonPanel, BorderLayout.SOUTH);
    img = ImageIO.read(new File("C:/Users/Baptiste/Desktop/MNO/lake2.png"));;
    widthImg = img.getWidth(null);
    heightImg = img.getHeight(null);
   // coordonnees.CoordX = (int)(widthImg * Math.random());
   // coordonnees.CoordY = (int)(heightImg * Math.random());
   // threshold.CoordX = (int)(widthImg * Math.random());
   // threshold.CoordY = (int)(heightImg * Math.random());
    coordonnees.CoordX = 10;
    coordonnees.CoordY = 10;
    threshold.CoordX = 10;
    threshold.CoordY = 10;
    this.setVisible(true);
    while(true)
    {
    coordonnees = Iteration.iterer(matrice, coordonnees, heightImg-4, widthImg-4, 0);
    threshold = Iteration.iterer(matrice, threshold, heightImg-4, widthImg-4, 1);
    Thread.sleep(1);
    this.repaint();
    }
  }

  public void actionPerformed(ActionEvent e) {
  }
  
  public void mouseEntered(MouseEvent arg0) {}
  public void mouseExited(MouseEvent arg0) {}
  public void mousePressed(MouseEvent arg0) {}
  public void mouseReleased(MouseEvent arg0) {}
  public void mouseClicked(MouseEvent e) 
  {
		int buttonDown = e.getButton();
		if (buttonDown == MouseEvent.BUTTON1) {
			coordonnees.CoordX = (int)(widthImg * Math.random());
		    coordonnees.CoordY = (int)(heightImg * Math.random());
		    threshold.CoordX = (int)(widthImg * Math.random());
		    threshold.CoordY = (int)(heightImg * Math.random());
		    Iteration.temperature = 500;
	    }
  }
 
  class PicturePanel extends JPanel {
    public void paint(Graphics g) {
      g.drawImage(img, 0, 0, this);
      g.setColor(Color.RED);
      g.fillOval(coordonnees.CoordX-5, coordonnees.CoordY-5, 10, 10);
      g.setColor(Color.BLUE);
      g.fillOval(threshold.CoordX-5, threshold.CoordY-5, 10, 10);
    }
  }

}
