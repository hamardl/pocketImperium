package pocketImperium;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * La classe {@code Secteur} représente un ensemble d'hexagones regroupés sous un nom commun.
 * Un secteur est une partie du plateau de jeu, qui contient une liste d'hexagones
 * et un nom qui l'identifie.
 *
 * <p>Cette classe implémente {@link Serializable} pour permettre la sérialisation
 * des secteurs lorsqu'une partie est sauvegardée.</p>
 *
 * @author [Loélie Hamard]
 */
public class Secteur implements Serializable {

    /** La liste des hexagones qui composent le secteur. */
    private ArrayList<Hex> listeHex;

    /** Le nom unique du secteur. */
    private String nom;

    /**
     * Constructeur de la classe {@code Secteur}.
     *
     * @param listeHex la liste des hexagones qui composent le secteur.
     * @param nom le nom du secteur.
     */
    public Secteur(ArrayList<Hex> listeHex, String nom) {
        this.nom = nom;
        this.listeHex = listeHex;
    }

    /**
     * Retourne la liste des hexagones appartenant au secteur.
     *
     * @return une liste d'objets {@link Hex} représentant les hexagones du secteur.
     */
    public ArrayList<Hex> getListeHex() {
        return listeHex;
    }

    /**
     * Retourne le nom du secteur.
     *
     * @return le nom du secteur sous forme de chaîne de caractères.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Vérifie si un hexagone donné appartient au secteur.
     *
     * @param hex l'hexagone a verifier.
     * @return {@code true} si l'hexagone appartient au secteur, {@code false} sinon.
     */
    public boolean contientHex(Hex hex) {
        return this.listeHex.contains(hex);
    }

    /**
     * Retourne une représentation textuelle du secteur, incluant son nom
     * et la liste de ses hexagones.
     *
     * @return une chaîne de caractères décrivant le secteur et ses hexagones.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Secteur Nom: ").append(nom).append("\n");
        sb.append("Hexagones:\n");
        for (Hex hex : listeHex) {
            sb.append("  - ").append(hex.toString()).append("\n");
        }
        return sb.toString();
    }
}

