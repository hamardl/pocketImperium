package pocketImperium;

import java.io.Serializable;

/**
 * La classe {@code Vaisseau} représente un pion de jeu contrôlé par un joueur
 * dans le jeu Pocket Imperium. Chaque vaisseau est associé à un joueur,
 * a un numéro, et une position sur le plateau de jeu (un hexagone).
 *
 * <p>Cette classe implémente {@link Serializable} pour permettre la sérialisation
 * des vaisseaux lorsqu'une partie est sauvegardée.</p>
 *
 * @author [Loélie Hamard]
 */
public class Vaisseau implements Serializable {

	/** Le joueur propriétaire du vaisseau. */
	private Joueur joueur;

	/** Le numéro unique du vaisseau. */
	private int numero;

	/** L'hexagone où le vaisseau est actuellement situé. */
	private Hex hex;

	/**
	 * Retourne le joueur propriétaire du vaisseau.
	 *
	 * @return le joueur propriétaire.
	 */
	public Joueur getJoueur() {
		return joueur;
	}

	/**
	 * Retourne l'hexagone ou le vaisseau est situé.
	 *
	 * @return l'hexagone actuel du vaisseau, ou {@code null} si le vaisseau
	 *         n'est pas encore positionné.
	 */
	public Hex getHex() {
		return hex;
	}

	/**
	 * Définit l'hexagone ou le vaisseau doit être positionné.
	 *
	 * @param hex l'hexagone où positionner le vaisseau.
	 */
	public void setHex(Hex hex) {
		this.hex = hex;
	}

	/**
	 * Constructeur de la classe {@code Vaisseau}.
	 *
	 * @param joueur le joueur propriétaire du vaisseau.
	 * @param numero le numéro unique du vaisseau.
	 *
	 * Par défaut, le vaisseau n'a pas de position donc hex est initialisé à null.
	 */
	public Vaisseau(Joueur joueur, int numero) {
		this.joueur = joueur;
		this.numero = numero;
		this.hex = null; // Par défaut, le vaisseau n'a pas de position.
	}

	/**
	 * Retourne une représentation textuelle du vaisseau, incluant son propriétaire,
	 * son numéro, et sa position actuelle sur le plateau.
	 *
	 * @return une chaîne de caractères avec les attributs du vaisseau.
	 */
	@Override
	public String toString() {
		String coordonneesHex = (hex != null) ? "(" + hex.getCoordonnees().get(0) + ", " + hex.getCoordonnees().get(1) + ")" : "Non assigné";
		return "Vaisseau{" +
				"Joueur='" + (joueur != null ? joueur.getNom() : "Aucun") + '\'' +
				", Numéro=" + numero +
				", Position=" + coordonneesHex +
				'}';
	}
}

