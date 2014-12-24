package mutation;

import parametrage.NombreEnergie;
import modele.Etat;

public interface IMutation {
public void faire(Etat e);
public NombreEnergie calculer(Etat e);
}
