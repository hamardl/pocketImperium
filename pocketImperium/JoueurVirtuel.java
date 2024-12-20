package pocketImperium;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JoueurVirtuel extends Joueur{
	
	public JoueurVirtuel (String nom,String couleur,int ordreDeJeu) {

        super(nom,couleur,ordreDeJeu);
	}


    public void ajouterVaisseau(ArrayList<Hex> listeHex) {
        // Vérification du nombre de vaisseaux du bot
        if (this.getListeVaisseaux().size() >= 15) {
            System.out.println(this.getNom() + " a déjà 15 vaisseaux. Il ne peut pas en ajouter davantage.");
            return;
        }

        // Création de la liste des hex disponibles
        ArrayList<Hex> hexDisponibles = new ArrayList<>();
        for (Hex hex : listeHex) {
            if (hex.getOccupant() != null && hex.getOccupant().equals(this) && hex.getCapacite() > 1) {
                hexDisponibles.add(hex);
            }
        }

        // Vérification si aucun hex n'est disponible
        if (hexDisponibles.isEmpty()) {
            System.out.println(this.getNom() + " n'a aucun hex valide pour ajouter un vaisseau.");
            return;
        }

        // Choix aléatoire d'un hex parmi les hex disponibles
        Random random = new Random();
        Hex hexChoisi = hexDisponibles.get(random.nextInt(hexDisponibles.size()));

        // Création et ajout du vaisseau
        Vaisseau nouveauVaisseau = new Vaisseau(this, this.getListeVaisseaux().size());
        hexChoisi.getListVaisseaux().add(nouveauVaisseau);
        this.getListeVaisseaux().add(nouveauVaisseau);
        nouveauVaisseau.setHex(hexChoisi);

        System.out.println(this.getNom() + " a ajouté un nouveau vaisseau sur l'hexagone (" +
                hexChoisi.getCoordonnees().get(0) + ", " +
                hexChoisi.getCoordonnees().get(1) + ").");
    }


    public void deplacer(ArrayList<Hex> listeHex,ArrayList<Vaisseau> listVaisseauDeplaces) {
        System.out.println(this.getNom()+"  a déplacer une flotte");
    }

    public void attaquerHex(ArrayList<Hex> listeHex, ArrayList<Vaisseau> listeVaisseauUtilises) {
        Random random = new Random();
        List<Hex> hexAttaquables = new ArrayList<>();

        // Parcourir les hex des vaisseaux du joueur et trouver les hex voisins
        for (Vaisseau vaisseau : this.getListeVaisseaux()) {
            Hex hex = vaisseau.getHex();
            for (Hex voisin : hex.getListeHexesVoisins()) {
                if (!hexAttaquables.contains(voisin)) {
                    hexAttaquables.add(voisin);
                }
            }
        }

        // Filtrer les hex attaquables
        hexAttaquables.removeIf(hex -> {
            // Vérifie si l'hex est contrôlé par le joueur ou a une capacité invalide
            boolean estControleParJoueur = this.equals(hex.getOccupant());
            boolean capaciteInvalide = hex.getCapacite() < 2;

            // Vérifie si tous les vaisseaux des hex voisins appartenant au joueur sont déjà utilisés
            boolean tousVaisseauxUtilises = hex.getListeHexesVoisins().stream()
                    .filter(voisin -> this.equals(voisin.getOccupant())) // Hex voisin occupé par le joueur
                    .allMatch(voisin -> !voisin.getListVaisseaux().isEmpty() &&
                            voisin.getListVaisseaux().stream().allMatch(listeVaisseauUtilises::contains)); // Tous les vaisseaux utilisés

            // Retirer l'hex s'il satisfait une des conditions d'exclusion
            return estControleParJoueur || capaciteInvalide || tousVaisseauxUtilises;
        });

        if (hexAttaquables.isEmpty()) {
            System.out.println("Aucun hexagone disponible pour une attaque.");
            return;
        }

        // Sélectionner un hex à attaquer aléatoirement
        Hex hexCible = hexAttaquables.get(random.nextInt(hexAttaquables.size()));
        System.out.println("Hex sélectionné pour l'attaque : (" + hexCible.getCoordonnees().get(0) + ", "
                + hexCible.getCoordonnees().get(1) + ").");

        // Calculer les hex voisins pouvant participer à l'attaque
        ArrayList<Vaisseau> listeVaisseauUtilisesCeTour = new ArrayList<>();
        for (Hex voisin : hexCible.getListeHexesVoisins()) {
            if (this.equals(voisin.getOccupant())) {
                int vaisseauxDisponibles =
                        (int) voisin.getListVaisseaux().stream()
                                .filter(v -> !listeVaisseauUtilises.contains(v))
                                .count();
                if (vaisseauxDisponibles > 0) {
                    System.out.println("Hex voisin (" + voisin.getCoordonnees().get(0) + ", "
                            + voisin.getCoordonnees().get(1) + ") a " + vaisseauxDisponibles + " vaisseaux disponibles.");

                    // Sélectionner aléatoirement combien de vaisseaux utiliser (entre 0 et vaisseauxDisponibles)
                    int vaisseauxAUtiliser = random.nextInt(vaisseauxDisponibles + 1);
                    System.out.println("Nombre de vaisseaux utilisés depuis cet hex : " + vaisseauxAUtiliser);

                    // Ajouter les vaisseaux utilisés à la liste
                    int vaisseauxAjoutes = 0;
                    for (Vaisseau v : voisin.getListVaisseaux()) {
                        if (!listeVaisseauUtilises.contains(v) && vaisseauxAjoutes < vaisseauxAUtiliser) {
                            listeVaisseauUtilisesCeTour.add(v);
                            listeVaisseauUtilises.add(v);
                            vaisseauxAjoutes++;
                        }
                    }
                }
            }
        }

        if (listeVaisseauUtilisesCeTour.isEmpty()) {
            System.out.println("Aucun vaisseau n'a été utilisé pour attaquer.");
            return;
        }

        // Déplacer les vaisseaux sélectionnés vers l'hex cible
        for (Vaisseau v : listeVaisseauUtilisesCeTour) {
            v.setHex(hexCible);
            hexCible.getListVaisseaux().add(v);
        }

        // Résoudre le conflit sur l'hex cible
        this.resoudreConflit(hexCible);
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
        Secteur secteur=null;
        while(secteur==null) {
            Random random = new Random();
            int indexAleatoire = random.nextInt(listeSecteurs.size());// Choisir un indice aléatoire
            if (indexAleatoire != 5) {
                secteur = listeSecteurs.get(indexAleatoire);
            }
        }
        System.out.println(this.getNom() + " a choisi le secteur " + secteur.getNom());
        return secteur;
    }


    public Hex choisirHexDepart(ArrayList<Secteur> secteursDejaChoisis, ArrayList<Hex> listeHex) {
        Random random = new Random();
        Hex hexChoisi = null;
        boolean hexValide = false;

        while (!hexValide) {
            // Sélection aléatoire d'un hex
            hexChoisi = listeHex.get(random.nextInt(listeHex.size()));

            // Vérification : l'hex n'appartient pas à un secteur déjà choisi
            boolean hexDansSecteur = false;
            for (Secteur secteur : secteursDejaChoisis) {
                if (secteur.contientHex(hexChoisi)) {
                    hexDansSecteur = true;
                    break;
                }
            }

            if (hexDansSecteur) {
                continue; // L'hex est déjà dans un secteur choisi, le robot doit recommencer
            }

            // Vérification : l'hex a une capacité de 2
            if (hexChoisi.getCapacite() != 2) {
                continue; // L'hex ne remplit pas les critères de capacité, le robot doit recommencer
            }

            // Si toutes les conditions sont remplies
            hexValide = true;
        }

        System.out.println(this.getNom()+" a choisi l'hexagone " +
                hexChoisi.getCoordonnees().get(0) + ", " +
                hexChoisi.getCoordonnees().get(1) + ".");
        return hexChoisi;
    }



}
