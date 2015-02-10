import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.imageio.ImageIO;

public class ZoneDessin extends JFrame implements ActionListener, MouseListener {
	Image img;
	public static int widthImg = 500;
	public static int heightImg = 50;
	int nbreEtats = 20;
	Particule particule;
	Graphics g;

	public ZoneDessin(int[][] matrice) throws InterruptedException, IOException {
		this.setSize(1500, 1000);
		addMouseListener(this);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel picPanel = new PicturePanel();
		this.add(picPanel, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();

		this.add(buttonPanel, BorderLayout.SOUTH);
		img = ImageIO.read(new File("C:/Users/Belaube/Desktop/heightmap3.png"));
		widthImg = img.getWidth(null);
		heightImg = img.getHeight(null);

		this.setVisible(true);
		particule = new Particule(nbreEtats);
		while(true)
		{
			particule = Iteration.iterer(matrice, particule, heightImg-4, widthImg-4);
			Thread.sleep(1);
			this.repaint();
		}
	}
	
	public void actionPerformed(ActionEvent e) {}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
	public void mouseClicked(MouseEvent e) 
	{
		int buttonDown = e.getButton();
		if (buttonDown == MouseEvent.BUTTON1) {}
	}
	
	public int getWidth(){
		return widthImg;
	}
	public int getHeight(){
		return heightImg;
	}
	
	class PicturePanel extends JPanel {
		public void paint(Graphics g) {
			g.drawImage(img, 0, 0, this);
			g.setColor(Color.RED);
			for(int i=0; i<particule.getEtats().size();i++)
			{
				g.fillOval(particule.getEtats().get(i).CoordX-5, particule.getEtats().get(i).CoordY-5, 10, 10);
			}
		}
	}
}
