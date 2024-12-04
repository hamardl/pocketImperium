package pocketImperium;

import java.util.ArrayList;

public class Hex {

    // Attributs
    private ArrayList<Hex> listeHexesVoisins; // Liste des hexagones voisins
    private boolean estTriprim;              // Indique si l'hexagone est "Triprim"
    private int capacite;                    // Capacité de l'hexagone
    private String couleur;                   // Statut de l'hexagone peut être une énuméraition
    private ArrayList<Vaisseau> listVaisseaux; // Liste des vaisseaux présents sur l'hexagone

    // Constructeur
    public Hex(ArrayList<Hex> listeHexesVosins, int capacite) {
        this.listeHexesVoisins = listeHexesVosins;
        this.estTriprim = false;
        this.capacite = capacite;
        this.listVaisseaux = new ArrayList<>();
    }

    // Getters et Setters
    public ArrayList<Hex> getListeHexesVoisins() {
        return listeHexesVoisins;
    }

    public void setListeHexesVoisins(ArrayList<Hex> listeHexesVoisins) {
        this.listeHexesVoisins = listeHexesVoisins;
    }

    public boolean isEstTriprim() {
        return estTriprim;
    }

    public void setEstTriprim(boolean estTriprim) {
        this.estTriprim = estTriprim;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public ArrayList<Vaisseau> getListVaisseaux() {
        return listVaisseaux;
    }

    public void setListVaisseaux(ArrayList<Vaisseau> listVaisseaux) {
        this.listVaisseaux = listVaisseaux;
    }

    // Méthode pour ajouter un voisin
    public void ajouterVoisin(Hex voisin) {
        this.listeHexesVoisins.add(voisin);
    }

    // Méthode pour ajouter un vaisseau
    public void ajouterVaisseau(Vaisseau vaisseau) {
        this.listVaisseaux.add(vaisseau);
    }

    // Méthode pour retirer un vaisseau
    public void retirerVaisseau(Vaisseau vaisseau) {
        this.listVaisseaux.remove(vaisseau);
    }

    public void retirerVaisseauxEnTrop() {
        while (listVaisseaux.size() > capacite) {
            listVaisseaux.remove(listVaisseaux.size() - 1); // Supprime le dernier vaisseau
        }
    }

    //méthode qui renvoie l'accupant du hex
    public void getOccupant() {
    	
    }
}
