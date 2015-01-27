package mutation;
import modele.Etat;
import modele.Probleme;

public interface IMutation {
public void faire(Probleme p);
public double calculer(Probleme p);
public Etat faire(Probleme p, Etat e);
public double calculer(Probleme p, Etat e);
}
