package mutation;

import modele.Etat;
import modele.Routage;

public interface IMutation {
public void faire(Etat e);
public double calculer(Etat e);
}
