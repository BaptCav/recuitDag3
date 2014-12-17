package mutation;
import modele.Etat;
import modele.Probleme;

public interface IMutation {
public void faire(Probleme p);
public double calculer(Probleme p);
public void faire(Probleme p, Etat e);
public double calculer(Probleme p, Etat e);
}
