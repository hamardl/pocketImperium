package pocketImperium;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JoueurVirtuel extends Joueur{
	
	public JoueurVirtuel (String nom,String couleur,int ordreDeJeu) {

        super(nom,couleur,ordreDeJeu);
	}


    public void ajouterVaisseau( ArrayList<Hex> listeHex) {
        // Vérification du nombre de vaisseaux du bot
        if (this.getListeVaisseaux().size() >= 15) {
            System.out.println(this.getNom()+" a déjà 15 vaisseaux. Il ne peut pas en ajouter davantage.");
            return;
        }

        Random random = new Random();
        Hex hexChoisi = null;

        // Recherche d'un hexagone occupé par le bot
        while (true) {
            int indexAleatoire = random.nextInt(listeHex.size());
            Hex candidat = listeHex.get(indexAleatoire);

            if (this.getNom().equals(candidat.getOccupant())) {
                hexChoisi = candidat;
                break;
            }
        }

        // Création et ajout du vaisseau
        Vaisseau nouveauVaisseau = new Vaisseau(this);
        hexChoisi.getListVaisseaux().add(nouveauVaisseau);
        this.getListeVaisseaux().add(nouveauVaisseau);

        System.out.println(this.getNom()+" a ajouté un nouveau vaisseau sur l'hexagone " +
                hexChoisi.getCoordonnees().get(0) + ", " + hexChoisi.getCoordonnees().get(1) + ".");
    }

    public void déplacer() {
        System.out.println(this.getNom()+"  a déplacer une flotte");
    }
    public void attaquerHex() {
        System.out.println(this.getNom()+" a attaqué un hex");
    }

    public void ordonnerCarte(){
        ArrayList<Carte> liste1 = new ArrayList<>();
        liste1.add(Carte.Expand);
        liste1.add(Carte.Explore);
        liste1.add(Carte.Exterminate);
        this.setListeDeCarteOrdonnee(liste1);
    }
    public Secteur choisirSecteur(ArrayList<Secteur> listeSecteurs){
        // Vérification si la liste n'est pas vide
        if (listeSecteurs == null || listeSecteurs.isEmpty()) {
            return null; // Ou vous pouvez choisir de lancer une exception si nécessaire
        }

        Random random = new Random();
        int indexAleatoire = random.nextInt(listeSecteurs.size());// Choisir un indice aléatoire
        Secteur secteur = listeSecteurs.get(indexAleatoire);
        System.out.println(this.getNom()+" a choisi le secteur "+ secteur.getNom());
        return secteur; // Retourner le secteur choisi

    }


}
