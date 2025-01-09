
package pocketImperium;

import java.io.Serializable;

/**
 * L'énumération {@code Carte} représente les différentes actions possibles
 * dans le jeu Pocket Imperium. Chaque carte correspond à une action
 * que les joueurs peuvent effectuer pendant leur tour.
 *
 * <p>Les cartes disponibles sont :</p>
 * <ul>
 *   <li>{@link #Expand} : Permet d'ajouter un vaisseau.</li>
 *   <li>{@link #Explore} : Permet de déplacer des vaisseaux.</li>
 *   <li>{@link #Exterminate} : Permet d'attaquer des hexs et d'éliminer les vaisseaux adversaires.</li>
 * </ul>
 *
 * <p>Cette classe implémente {@link Serializable} pour permettre la sérialisation
 * des cartes lorsqu'une partie est sauvegardée.</p>
 *
 * @author [Loélie Hamard]
 */
public enum Carte implements Serializable {
	/**
	 * Représente l'action d'expansion : ajouter des vaisseaux sur hexagones avec system que l'on controle déjà.
	 */
	Expand,

	/**
	 * Représente l'action d'exploration : déplacer une flotte de vaisseaux à travers 1 ou 2 hexs.
	 */
	Explore,

	/**
	 * Représente l'action d'extermination : attaquer les hexagones des autres joueurs
	 * pour éliminer leurs vaisseaux.
	 */
	Exterminate
}

