import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.imageio.ImageIO;

public class ZoneDessin extends JFrame {
	private static final long serialVersionUID = 1L;
	Image img;
	public static int widthImg = 500;
	public static int heightImg = 50;
	int nbreEtats = 5;
	Particule particule;
	Etat meilleurEtat;
	Graphics g;
	JLabel texteEnergie;
	
	public ZoneDessin(int[][] matrice) throws InterruptedException, IOException {
		this.setSize(600, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		img = ImageIO.read(new File("C:/Users/Belaube/Desktop/heightmap2.png"));
		widthImg = img.getWidth(null);
		heightImg = img.getHeight(null);
		JPanel picPanel = new PicturePanel();
		texteEnergie = new JLabel("Energie potentielle :");
		this.add(picPanel, BorderLayout.CENTER);
		this.add(texteEnergie, BorderLayout.NORTH);
		this.setVisible(true);
	}

	public void setParticule(Particule particule)
	{
		this.particule = particule;
	}
	
	public void setMeilleurEtat(Etat meilleurEtat)
	{
		this.meilleurEtat = meilleurEtat;
	}
	
	public int getWidth(){
		return widthImg;
	}
	public int getHeight(){
		return heightImg;
	}
	public JLabel getTexteEnergie(){
		return texteEnergie;
	}
	class PicturePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public void paint(Graphics g) {
		g.drawImage(img, 0, 0, this);
		g.setColor(Color.RED);
		for(int i=0; i<particule.getEtats().size();i++)
		{
			g.fillOval(particule.getEtats().get(i).CoordX-5, particule.getEtats().get(i).CoordY-5, 10, 10);
		}
		g.setColor(Color.BLUE);
		g.fillOval(meilleurEtat.CoordX-5, meilleurEtat.CoordY-5, 10, 10);
	}
	}

}
