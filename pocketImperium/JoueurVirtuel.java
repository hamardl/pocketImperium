package pocketImperium;

import java.io.Serializable;
import java.util.*;

public class JoueurVirtuel extends Joueur implements Serializable {
	
	public JoueurVirtuel (String nom,String couleur,int ordreDeJeu) {

        super(nom,couleur,ordreDeJeu);
	}


    public void ajouterVaisseau(ArrayList<Hex> listeHex, PlateauDeJeu pdj) {
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


    public void attaquerHex(ArrayList<Hex> listeHex, ArrayList<Vaisseau> listeVaisseauUtilises,PlateauDeJeu pdj) {
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

    public void ordonnerCarte() {
        ArrayList<Carte> liste1 = new ArrayList<>();
        liste1.add(Carte.Expand);
        liste1.add(Carte.Explore);
        liste1.add(Carte.Exterminate);

        // Mélange la liste pour obtenir un ordre aléatoire
        Collections.shuffle(liste1);

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

    public void deplacer(ArrayList<Hex> listeHex, ArrayList<Vaisseau> listVaisseauDeplaces,PlateauDeJeu pdj) {
        Random random = new Random(); // Initialisation du Random localement
        ArrayList<Vaisseau> listeVaisseauxDeplacesCeTour = new ArrayList<>();
        int nbDeplacement = 1;

        // Choisir automatiquement si déplacer une flotte
        System.out.println("Déplacement d'une flotte choisi.");

        // Création de la liste des hex de départ
        ArrayList<Hex> listeHexDepart = new ArrayList<>();
        for (Hex hex : listeHex) {
            if (hex.getListVaisseaux().stream().anyMatch(v -> this.equals(v.getJoueur()))) {
                listeHexDepart.add(hex);
            }
        }

        // Filtrage des hex de départ
        listeHexDepart.removeIf(hex -> {
            boolean tousVoisinsOccupesParEnnemis = hex.getListeHexesVoisins().stream()
                    .allMatch(voisin -> voisin.getOccupant() != null && !this.equals(voisin.getOccupant()));
            boolean tousVaisseauxDejaDeplaces = !listVaisseauDeplaces.isEmpty() && hex.getListVaisseaux().stream()
                    .allMatch(listVaisseauDeplaces::contains);
            return tousVoisinsOccupesParEnnemis || tousVaisseauxDejaDeplaces;
        });

        if (listeHexDepart.isEmpty()) {
            System.out.println("Aucun hex disponible pour commencer un déplacement.");
            return;
        }

        // Sélection aléatoire d'un hex de départ
        Hex hexCourant = listeHexDepart.get(random.nextInt(listeHexDepart.size()));
        System.out.println("Hex de départ choisi : (" + hexCourant.getCoordonnees().get(0) + ", " + hexCourant.getCoordonnees().get(1) + ")");

        while (nbDeplacement <= 2) {
            switch (nbDeplacement) {
                case 1 -> {
                    Hex hexDestination = choisirDestinationAleatoire(hexCourant, listVaisseauDeplaces, random);
                    if (hexDestination == null) return; // Aucun déplacement possible
                    int nbVaisseaux=deplacerVaisseaux(hexCourant, hexDestination, listeVaisseauxDeplacesCeTour, listVaisseauDeplaces, random);
                    if(nbVaisseaux==0){
                        return;
                    }
                    if (hexDestination.isEstTriprim()) {
                        System.out.println("L'hex destination est TriPrim. Vous ne pouvez pas continuer le déplacement.");
                        return;
                    }
                    hexCourant = hexDestination; // Mise à jour pour le prochain tour
                    nbDeplacement++;
                }
                case 2 -> {
                    Hex hexDestination = choisirDestinationAleatoire(hexCourant, listVaisseauDeplaces, random);
                    if (hexDestination == null) return; // Aucun déplacement possible
                    int nbVaisseaux2=deplacerVaisseaux(hexCourant, hexDestination, listeVaisseauxDeplacesCeTour, listVaisseauDeplaces, random);
                    System.out.println("Deuxième déplacement effectué. Fin de l'action.");
                    return;
                }
            }
        }
    }

    private Hex choisirDestinationAleatoire(Hex hexCourant, ArrayList<Vaisseau> listVaisseauDeplaces, Random random) {
        ArrayList<Hex> listeHexDestination = new ArrayList<>(hexCourant.getListeHexesVoisins());
        listeHexDestination.removeIf(hex -> !this.equals(hex.getOccupant()) && hex.getOccupant() != null);

        if (listeHexDestination.isEmpty()) {
            System.out.println("Aucun hex de destination disponible depuis cet hex.");
            return null;
        }

        // Choisir une destination aléatoire parmi les options disponibles
        Hex hexDestination = listeHexDestination.get(random.nextInt(listeHexDestination.size()));
        System.out.println("Hex de destination choisi : (" + hexDestination.getCoordonnees().get(0) + ", " + hexDestination.getCoordonnees().get(1) + ")");
        return hexDestination;
    }

    private int deplacerVaisseaux(Hex hexCourant, Hex hexDestination, ArrayList<Vaisseau> listeVaisseauxDeplacesCeTour, ArrayList<Vaisseau> listeVaisseauxDeplaces, Random random) {
        long nbVaisseauxDeplacables = hexCourant.getListVaisseaux().stream()
                .filter(v -> !listeVaisseauxDeplaces.contains(v))
                .count();

        int nbVaisseaux = random.nextInt((int) nbVaisseauxDeplacables + 1); // Choisir un nombre aléatoire de vaisseaux à déplacer

        int vaisseauxAjoutes = 0;
        Iterator<Vaisseau> iterator = hexCourant.getListVaisseaux().iterator();
        while (iterator.hasNext() && vaisseauxAjoutes < nbVaisseaux) {
            Vaisseau v = iterator.next();
            if (!listeVaisseauxDeplaces.contains(v)) {
                listeVaisseauxDeplacesCeTour.add(v);
                hexDestination.getListVaisseaux().add(v);
                iterator.remove(); // Supprime de hexCourant
                v.setHex(hexDestination);
                vaisseauxAjoutes++;
            }
        }

        System.out.println(nbVaisseaux + " vaisseaux déplacés vers l'hex (" + hexDestination.getCoordonnees().get(0)
                + ", " + hexDestination.getCoordonnees().get(1) + ").");
        return nbVaisseaux;
    }


}
