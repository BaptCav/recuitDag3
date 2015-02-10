
public class Mutation {

	public static Etat faire(Etat c, int heightImg, int widthImg)
	{
		double random = Math.random();
		Etat resultat = new Etat(c.CoordX, c.CoordY);
		
		if(random < 0.25)
		{
			if(c.CoordX < widthImg - 4) { resultat.CoordX+=1; }
		}
		if(random >= 0.25 && random < 0.50)
		{
			if(c.CoordY < heightImg - 4) { resultat.CoordY+=1; }
		}
		if(random >= 0.50 && random < 0.75)
		{
			if(c.CoordX > 4) { resultat.CoordX-=1; }
		}
		if(random >= 0.75)
		{
			if(c.CoordY > 4) { resultat.CoordY-=1; }
		}
		return resultat;
	}
}
