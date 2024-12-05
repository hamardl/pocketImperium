package pocketImperium;

import java.util.ArrayList;

public class Secteur {

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

    public String toString(){

    }
}
