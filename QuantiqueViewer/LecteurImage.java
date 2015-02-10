import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class LecteurImage {
	
	String string;
	
	LecteurImage(String string){
		this.string = string;
	}

	
	int[][] returnMatrix() throws IOException{
		
		//"/Users/thomasdoutre/Desktop/image.bmp"
		Image image = ImageIO.read(new File(this.string));
		BufferedImage img = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_BYTE_GRAY);
		Graphics g = img.createGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		
		int w = img.getWidth();
		int h = img.getHeight();

		int[][] array = new int[w][h];
		int min = 20;
		int max = 0;
		Raster raster = ((BufferedImage) image).getData();
		for (int j = 0; j < w; j++) {
		    for (int k = 0; k < h; k++) {
		        array[j][k] = raster.getSample(j, k, 0);
		        if (array[j][k]<min){
		        min = array[j][k];
		        }
		        if (array[j][k]>max){
		        	max = array[j][k];
		        }
		    }
		}
	
		printApproxMatrix(array);
		System.out.println("min = "+ min + ",   max = "+ max);
		
		return array;

	}

	
	public static void printApproxMatrix(int[][] array) {
		System.out.println("=================================================================");
		/*for(int[] row : array) {
			for (double i : row) {
				System.out.print((int)i);
				System.out.print("\t");
			}
			System.out.println();
		}*/
		System.out.println("chargement...");
		System.out.println("=================================================================");

	
	}
	
}
