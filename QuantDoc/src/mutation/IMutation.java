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
	 * Calcule la différence d'énergie apparue lors de la mutation
	 * @param p
	 * Probleme sur lequel on mute
	 * @return
	 * Valeur numérique de la différence d'énergie après mutation (sans pour autant muter !)
	 */
public double calculer(Probleme p);
/**
 * Faire la mutation.
 * @param p
 * Problème sur lequel on mute.
 * @param e
 * Etat de la particule sur lequel la mutation a lieu
 * @return
 * Etat muté
 */
public Etat faire(Probleme p, Etat e);
/**
 * Calcule la différence d'energie suite à la mutation sur l'état e
 * @param p
 * Problème sur lequel on mute
 * @param e
 * Etat de la particule sur lequel la mutation a lieu.
 * @return
 * Différence d'énergie après mutation
 */
public double calculer(Probleme p, Etat e);
/**
 * Transforme la mutation courante en une autre mutation, de même type(même type d'objet) mais différente(arguments différents)
 */
public void maj();
}
