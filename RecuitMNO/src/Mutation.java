
public class Mutation {

	public static Coordonnees faire(Coordonnees c, int heightImg, int widthImg)
	{
		double random = Math.random();
		Coordonnees resultat = new Coordonnees(c.CoordX, c.CoordY);
		
		if(random < 0.25)
		{
			if(c.CoordX < widthImg - 4) { resultat.CoordX+=4; }
		}
		if(random >= 0.25 && random < 0.50)
		{
			if(c.CoordY < heightImg - 4) { resultat.CoordY+=4; }
		}
		if(random >= 0.50 && random < 0.75)
		{
			if(c.CoordX > 4) { resultat.CoordX-=4; }
		}
		if(random >= 0.75)
		{
			if(c.CoordY > 4) { resultat.CoordY-=4; }
		}
		return resultat;
	}
}
