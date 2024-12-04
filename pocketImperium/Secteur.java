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

    // Setter
    public void setListeHex(ArrayList<Hex> listeHex) {
        this.listeHex = listeHex;
    }

    // Méthode pour ajouter un Hex au secteur
    public void ajouterHex(Hex hex) {
        this.listeHex.add(hex);
    }

    // Méthode pour supprimer un Hex du secteur
    public void retirerHex(Hex hex) {
        this.listeHex.remove(hex);
    }

    // Méthode pour récupérer un Hex spécifique
    public Hex obtenirHex(int index) {
        if (index >= 0 && index < listeHex.size()) {
            return listeHex.get(index);
        } else {
            throw new IndexOutOfBoundsException("Index invalide : " + index);
        }
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
}
