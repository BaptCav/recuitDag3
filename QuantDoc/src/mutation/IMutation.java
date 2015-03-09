package mutation;
import modele.Etat;
import modele.Probleme;

public interface IMutation {
	/**
	 * Faire la mutation.
	 * @param p
	 * Probleme sur lequel on mute (exemple : ParticuleTSP)
	 */
public void faire(Probleme p);
	/**
	 * Calcule la diff�rence d'�nergie apparue lors de la mutation
	 * @param p
	 * Probleme sur lequel on mute
	 * @return
	 * Valeur num�rique de la diff�rence d'�nergie apr�s mutation (sans pour autant muter !)
	 */
public double calculer(Probleme p);
/**
 * Faire la mutation.
 * @param p
 * Probl�me sur lequel on mute.
 * @param e
 * Etat de la particule sur lequel la mutation a lieu
 * @return
 * Etat mut�
 */
public Etat faire(Probleme p, Etat e);
/**
 * Calcule la diff�rence d'energie suite � la mutation sur l'�tat e
 * @param p
 * Probl�me sur lequel on mute
 * @param e
 * Etat de la particule sur lequel la mutation a lieu.
 * @return
 * Diff�rence d'�nergie apr�s mutation
 */
public double calculer(Probleme p, Etat e);
/**
 * Transforme la mutation courante en une autre mutation, de m�me type(m�me type d'objet) mais diff�rente(arguments diff�rents)
 */
public void maj();
}
