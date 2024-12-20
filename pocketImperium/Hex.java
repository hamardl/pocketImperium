package pocketImperium;

import java.util.ArrayList;

public class Hex {

    // Attributs
    private ArrayList<Hex> listeHexesVoisins; // Liste des hexagones voisins
    private boolean estTriprim;              // Indique si l'hexagone est "Triprim"
    private int capacite;                    // Capacité de l'hexagone
    private ArrayList<Vaisseau> listVaisseaux; // Liste des vaisseaux présents sur l'hexagone
    private ArrayList<Integer> coordonnees;

    // Constructeur
    public Hex(ArrayList<Integer> coordonnees) {
        this.coordonnees=coordonnees;
        this.capacite=1;
        this.estTriprim=false;
        listVaisseaux=new ArrayList<>();
        listeHexesVoisins=new ArrayList<>();

    }

    // Getters et Setters
    public ArrayList<Hex> getListeHexesVoisins() {
        return listeHexesVoisins;
    }

    public void setListeHexesVoisins(ArrayList<Hex> listeHexesVosins) {
        this.listeHexesVoisins = listeHexesVosins;
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

    public ArrayList<Vaisseau> getListVaisseaux() {
        return listVaisseaux;
    }

    // Méthode pour ajouter un vaisseau
    public void ajouterVaisseau(Vaisseau vaisseau) {
        this.listVaisseaux.add(vaisseau);
    }

    // Méthode pour retirer un vaisseau
    public void retirerVaisseau(Vaisseau vaisseau) {
        this.listVaisseaux.remove(vaisseau);
    }


    //get coordonnes
    public ArrayList<Integer> getCoordonnees(){
        return this.coordonnees;
    }

    //méthode qui renvoie l'occupant du hex
    public Joueur getOccupant() {
        for (Vaisseau vaisseau : listVaisseaux) {
            if (vaisseau.getJoueur() != null) {
                return vaisseau.getJoueur(); // Retourne le joueur du premier vaisseau trouvé
            }
        }
        return null; // Si aucun joueur n'est trouvé
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        // Ajouter les coordonnées de l'Hex
        sb.append("Hex (")
                .append("Coordonnées: ")
                .append(coordonnees != null ? coordonnees : "[Non définies]")
                .append(")\n");

        // Ajouter l'information sur Triprim
        sb.append("Est Triprim: ")
                .append(estTriprim ? "Oui" : "Non")
                .append("\n");

        // Ajouter la capacité
        sb.append("Capacité: ")
                .append(capacite)
                .append("\n");

        // Ajouter les vaisseaux présents
        sb.append("Vaisseaux présents (" + listVaisseaux.size() + "): ");
        if (!listVaisseaux.isEmpty()) {
            for (Vaisseau vaisseau : listVaisseaux) {
                sb.append(vaisseau.getJoueur().getNom()).append(", ");
            }
            sb.setLength(sb.length() - 2); // Retirer la virgule et l'espace finaux
        } else {
            sb.append("Aucun");
        }
        sb.append("\n");

        // Ajouter les coordonnées des voisins
        sb.append("Voisins: ");
        if (listeHexesVoisins != null && !listeHexesVoisins.isEmpty()) {
            for (Hex voisin : listeHexesVoisins) {
                sb.append(voisin.getCoordonnees()).append(", ");
            }
            sb.setLength(sb.length() - 2); // Retirer la virgule et l'espace finaux
        } else {
            sb.append("Aucun");
        }
        sb.append("\n");

        return sb.toString();
    }


}
