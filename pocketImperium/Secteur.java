package pocketImperium;

import java.io.Serializable;
import java.util.ArrayList;

public class Secteur implements Serializable {

    // Attribut
    private ArrayList<Hex> listeHex;
    private String nom;

    // Constructeur
    public Secteur(ArrayList<Hex> listeHex, String nom) {
        this.nom= nom;
        this.listeHex = listeHex;
    }

    // Getter
    public ArrayList<Hex> getListeHex() {
        return listeHex;
    }

    public String getNom() {
        return nom;
    }

    // Méthode pour vérifier si un Hex est dans le secteur
    public boolean contientHex(Hex hex) {
        return this.listeHex.contains(hex);
    }

    // Méthode pour afficher les informations des Hex
    public void afficherHexes() {
        for (Hex hex : listeHex) {
            System.out.println(hex);
        }
    }

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
