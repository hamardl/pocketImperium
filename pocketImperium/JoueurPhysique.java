package pocketImperium;

import java.io.Serializable;
import java.util.*;

public class JoueurPhysique extends Joueur implements Serializable {
	
	public JoueurPhysique (String nom, String couleur, int ordreDeJeu) {
		super(nom,couleur,ordreDeJeu);
	}

    // Méthode pour ordonner les cartes en les demandant à l'utilisateur
    public void ordonnerCarte() {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Carte> list1 = new ArrayList<>();
        ArrayList<Carte> cartesChoisies = new ArrayList<>(); // Liste des cartes déjà choisies

        // Demander explicitement la première, deuxième, puis troisième carte
        for (int i = 1; i <= 3; i++) {
            Carte carte = lireCarteDepuisUtilisateur(scanner, cartesChoisies, i); // Inclut la validation
            list1.add(carte);
        }

        this.setListeDeCarteOrdonnee(list1); // Met à jour la liste des cartes ordonnées
        System.out.println("Les cartes ont été ordonnées.");
    }

    // Méthode pour lire une carte, en vérifiant si elle est valide et non déjà choisie
    private Carte lireCarteDepuisUtilisateur(Scanner scanner, ArrayList<Carte> cartesChoisies, int numeroCarte) {
        Carte carte = null;
        boolean carteValide = false;

        while (!carteValide) {
            System.out.println("Entrez la " + ordinal(numeroCarte) + " carte : (Expand, Explore, Exterminate)");
            String saisie = scanner.nextLine().trim();

            // Vérifier si la saisie correspond à une carte valide
            try {
                carte = Carte.valueOf(saisie); // Convertit la saisie en enum Carte
                if (cartesChoisies.contains(carte)) {
                    System.out.println("Cette carte a déjà été choisie. Veuillez en choisir une autre.");
                } else {
                    carteValide = true; // La carte est valide et pas encore choisie
                    cartesChoisies.add(carte); // Ajouter à la liste des cartes choisies
                }
            } catch (IllegalArgumentException e) {
                // La carte entrée n'est pas valide
                System.out.println("Carte invalide. Les options disponibles sont : Expand, Explore, Exterminate.");
            }
        }
        return carte;
    }

    // Méthode pour convertir un numéro en ordinal (1 -> "première", 2 -> "deuxième", etc.)
    private String ordinal(int numero) {
        switch (numero) {
            case 1:
                return "première";
            case 2:
                return "deuxième";
            case 3:
                return "troisième";
            default:
                return numero + "ème";
        }
    }

	public Secteur choisirSecteur(ArrayList<Secteur> listeSecteurs) {
        System.out.print("Entrez le nom du secteur : ");
        String nomChoisi;
        Secteur secteurChoisi = null;
        Scanner scanner = new Scanner(System.in); // Declare and initialize Scanner

        do {
            nomChoisi = scanner.nextLine();
            for (Secteur secteur : listeSecteurs) {
                if (secteur.getNom().equalsIgnoreCase(nomChoisi)&&!"secteur5".equalsIgnoreCase(nomChoisi)) {
                    secteurChoisi = secteur;
                    break;
                }
            }
            if (secteurChoisi == null) {
                System.out.println("Nom invalide.Les options disponibles sont : Secteur1, Secteur2, etc jusqu'à 9 sauf le secteur de TriPrim. Essayez à nouveau.");
            }
        } while (secteurChoisi == null);

        System.out.println("Vous avez choisi le secteur : " + secteurChoisi.getNom());
        return secteurChoisi;
    }

    public void ajouterVaisseau(ArrayList<Hex> listeHex,PlateauDeJeu pdj) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Souhaitez-vous ajouter un Vaisseau ? (oui/non)");
        String reponse = scanner.nextLine().trim().toLowerCase();
        if (!reponse.equals("oui")) {
            System.out.println("Aucun Vaisseau ajouté.");
            return;
        }

        pdj.decrirePlateau();
        // Vérification du nombre de vaisseaux du joueur
        if (this.getListeVaisseaux().size() >= 15) {
            System.out.println("Vous avez déjà 15 vaisseaux. Vous ne pouvez pas en ajouter davantage.");
            return;
        }

        // Filtrer les hex valides
        ArrayList<Hex> hexDisponibles = new ArrayList<>();
        for (Hex hex : listeHex) {
            if (hex.getOccupant() != null && hex.getOccupant().equals(this) && hex.getCapacite() > 1) {
                hexDisponibles.add(hex);
            }
        }

        // Si aucun hex valide n'est disponible
        if (hexDisponibles.isEmpty()) {
            System.out.println("Aucun hex valide pour ajouter un vaisseau. Vous ne pouvez pas en ajouter.");
            return;
        }

        // Affichage des hex disponibles
        System.out.println("Hex disponibles pour ajouter un vaisseau :");
        for (Hex hex : hexDisponibles) {
            System.out.println(" - Hex (" + hex.getCoordonnees().get(0) + ", " + hex.getCoordonnees().get(1) + ")");
        }

        Hex hexChoisi = null;
        while (hexChoisi == null) {
            try {
            System.out.println("Entrez les coordonnées de l'hexagone où vous souhaitez ajouter un vaisseau (format: x y) :");
            int x = scanner.nextInt();
            int y = scanner.nextInt();

            for (Hex hex : hexDisponibles) {
                if (hex.getCoordonnees().get(0) == x && hex.getCoordonnees().get(1) == y) {
                    hexChoisi = hex;
                    break;
                }
            }

            if (hexChoisi == null) {
                System.out.println("Les coordonnées ne correspondent à aucun hex valide. Veuillez réessayer.");
            }
            }
            catch (InputMismatchException e) {
                System.out.println("Entrée invalide. Veuillez entrer des coordonnées sous la forme de deux nombres (ex: 3 5).");
                scanner.nextLine();
            }
        }

        // Création et ajout du vaisseau
        Vaisseau nouveauVaisseau = new Vaisseau(this, this.getListeVaisseaux().size());
        hexChoisi.getListVaisseaux().add(nouveauVaisseau);
        this.getListeVaisseaux().add(nouveauVaisseau);
        nouveauVaisseau.setHex(hexChoisi);

        System.out.println("Un nouveau vaisseau a été ajouté sur l'hexagone (" +
                hexChoisi.getCoordonnees().get(0) + ", " +
                hexChoisi.getCoordonnees().get(1) + ").");
    }

//    public void deplacer(ArrayList<Hex> listeHex,ArrayList<Vaisseau> listVaisseauDeplaces) {
//        System.out.println(this.getNom()+"  a déplacer une flotte");
//    }

    public void deplacer(ArrayList<Hex> listeHex, ArrayList<Vaisseau> listVaisseauDeplaces,PlateauDeJeu pdj) {

        Scanner scanner = new Scanner(System.in);
        ArrayList<Vaisseau> listeVaisseauxDeplacesCeTour = new ArrayList<>(); // Nouvelle liste temporaire
        int nbDeplacement = 1;

        System.out.println("Souhaitez-vous déplacer une flotte ? (oui/non)");
        String reponse = scanner.nextLine().trim().toLowerCase();
        if (!reponse.equals("oui")) {
            System.out.println("Aucune flotte déplacée.");
            return;
        }
        pdj.decrirePlateau();
        // Création de la liste des hex de départ
        ArrayList<Hex> listeHexDepart = new ArrayList<>();
        for (Hex hex : listeHex) {
            if (hex.getListVaisseaux().stream().anyMatch(v -> this.equals(v.getJoueur()))) {
                listeHexDepart.add(hex);
            }
        }

        // Filtrer les hex de départ
        listeHexDepart.removeIf(hex -> {
            // Vérifie si tous les voisins sont occupés par des ennemis
            boolean tousVoisinsOccupesParEnnemis = hex.getListeHexesVoisins().stream()
                    .allMatch(voisin -> voisin.getOccupant() != null && !this.equals(voisin.getOccupant()));

            // Vérifie si tous les vaisseaux de cet hex ont déjà été déplacés
            boolean tousVaisseauxDejaDeplaces = !listVaisseauDeplaces.isEmpty() && hex.getListVaisseaux().stream()
                    .allMatch(listVaisseauDeplaces::contains);

            // Si une des deux conditions est vraie, l'hex est retiré
            return tousVoisinsOccupesParEnnemis || tousVaisseauxDejaDeplaces;
        });



        if (listeHexDepart.isEmpty()) {
            System.out.println("Aucun hex disponible pour commencer un déplacement.");
            return;
        }

        Hex hexCourant = null;

        // Sélection de l'hex de départ
        System.out.println("Options d'hex de départ :");
        for (int i = 0; i < listeHexDepart.size(); i++) {
            Hex hex = listeHexDepart.get(i);
            long nbVaisseauxDeplacables = hex.getListVaisseaux().stream()
                    .filter(v -> !listVaisseauDeplaces.contains(v))
                    .count();
            System.out.println(i + 1 + ". Hex (" + hex.getCoordonnees().get(0) + ", " + hex.getCoordonnees().get(1)
                    + ") - " + nbVaisseauxDeplacables + " vaisseaux disponibles");
        }

        while (hexCourant == null) {
            try {
                System.out.println("Choisissez un hex de départ (numéro) :");
                int choix = Integer.parseInt(scanner.nextLine().trim()) - 1;
                if (choix >= 0 && choix < listeHexDepart.size()) {
                    hexCourant = listeHexDepart.get(choix);
                } else {
                    System.out.println("Choix invalide. Réessayez.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrée invalide. Veuillez entrer un nombre.");
            }
        }
        int nbVaisseaux=0;
        while (nbDeplacement <= 2) {
            switch (nbDeplacement) {
                case 1 -> {
                    Hex hexDestination = choisirDestination(hexCourant, scanner, listVaisseauDeplaces, listeVaisseauxDeplacesCeTour);
                    if (hexDestination == null) {
                        return; // Aucun déplacement possible
                    }
                    nbVaisseaux=deplacerVaisseaux(hexCourant, hexDestination, listeVaisseauxDeplacesCeTour,listVaisseauDeplaces, scanner,0);
                    if (nbVaisseaux==0){
                        return;
                    }
                    if (hexDestination.isEstTriprim()) {
                        System.out.println("L'hex destination est TriPrim. Vous ne pouvez pas continuer le déplacement.");
                        return;
                    }

                    System.out.println("Voulez-vous continuer à déplacer jusqu'à un deuxième hex ? (oui/non)");
                    String reponse2 = scanner.nextLine().trim().toLowerCase();
                    if (!reponse2.equals("oui")) {

                        // Ajouter les vaisseaux déplacés ce tour à la liste principale
                        listVaisseauDeplaces.addAll(listeVaisseauxDeplacesCeTour);
                        return;
                    }

                    hexCourant = hexDestination; // Mise à jour pour le prochain tour
                    nbDeplacement++;
                }
                case 2 -> {
                    Hex hexDestination = choisirDestination(hexCourant, scanner, listVaisseauDeplaces, listeVaisseauxDeplacesCeTour);
                    if (hexDestination == null) {
                        return; // Aucun déplacement possible
                    }

                    int nbVaisseaux2=deplacerVaisseaux(hexCourant, hexDestination, listeVaisseauxDeplacesCeTour, listVaisseauDeplaces,scanner,nbVaisseaux);
                    System.out.println("Deuxième déplacement effectué. Fin de l'action.");
                    // Ajouter les vaisseaux déplacés ce tour à la liste principale
                    listVaisseauDeplaces.addAll(listeVaisseauxDeplacesCeTour);
                    return;
                }
            }
        }

    }
    private Hex choisirDestination(Hex hexCourant, Scanner scanner, ArrayList<Vaisseau> listVaisseauDeplaces, ArrayList<Vaisseau> listeVaisseauxDeplacesCeTour) {
        ArrayList<Hex> listeHexDestination = new ArrayList<>(hexCourant.getListeHexesVoisins());
        listeHexDestination.removeIf(hex -> !this.equals(hex.getOccupant()) && hex.getOccupant() != null);

        if (listeHexDestination.isEmpty()) {
            System.out.println("Aucun hex de destination disponible depuis cet hex.");
            return null;
        }

        System.out.println("Options d'hex de destination :");
        for (int i = 0; i < listeHexDestination.size(); i++) {
            Hex hex = listeHexDestination.get(i);
            System.out.println(i + 1 + ". Hex (" + hex.getCoordonnees().get(0) + ", " + hex.getCoordonnees().get(1) + ")");
        }

        Hex hexDestination = null;
        while (hexDestination == null) {
            try {
                System.out.println("Choisissez un hex de destination (numéro) :");
                int choix = Integer.parseInt(scanner.nextLine().trim()) - 1;
                if (choix >= 0 && choix < listeHexDestination.size()) {
                    hexDestination = listeHexDestination.get(choix);
                } else {
                    System.out.println("Choix invalide. Réessayez.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrée invalide. Veuillez entrer un nombre.");
            }
        }

        return hexDestination;
    }
    private int deplacerVaisseaux(Hex hexCourant, Hex hexDestination, ArrayList<Vaisseau> listeVaisseauxDeplacesCeTour, ArrayList<Vaisseau> listeVaisseauxDeplaces, Scanner scanner,int nbVaisseauDeplacesAvant) {
        long nbVaisseauxDeplacables = hexCourant.getListVaisseaux().stream()
                .filter(v -> !listeVaisseauxDeplaces.contains(v))
                .count();

        int nbVaisseaux = -1;
        while (nbVaisseaux < 0 || nbVaisseaux > nbVaisseauxDeplacables) {
            try {
                System.out.println("Combien de vaisseaux voulez-vous déplacer ? (max : " + nbVaisseauxDeplacables + ")");
                nbVaisseaux = Integer.parseInt(scanner.nextLine().trim());
                if (nbVaisseaux < 0 || nbVaisseaux > nbVaisseauxDeplacables) {
                    System.out.println("Nombre invalide. Veuillez entrer un nombre entre 0 et " + nbVaisseauxDeplacables + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrée invalide. Veuillez entrer un nombre.");
            }
        }

        int vaisseauxAjoutes = 0;
        Iterator<Vaisseau> iterator = hexCourant.getListVaisseaux().iterator();
        while (iterator.hasNext()) {
            Vaisseau v = iterator.next();
            if (!listeVaisseauxDeplaces.contains(v) && vaisseauxAjoutes < nbVaisseaux) {
                if(nbVaisseaux>nbVaisseauDeplacesAvant) {
                    listeVaisseauxDeplacesCeTour.add(v);
                }
                hexDestination.getListVaisseaux().add(v);
                iterator.remove(); // Supprime de hexCourant de manière sûre
                v.setHex(hexDestination);
                vaisseauxAjoutes++;
            }
        }

        System.out.println(nbVaisseaux + " vaisseaux déplacés vers l'hex (" + hexDestination.getCoordonnees().get(0)
                + ", " + hexDestination.getCoordonnees().get(1) + ").");
        return nbVaisseaux;
    }




    public void attaquerHex(ArrayList<Hex> listeHex, ArrayList<Vaisseau> listeVaisseauUtilises,PlateauDeJeu pdj) {
        Scanner scanner = new Scanner(System.in);
        List<Hex> hexAttaquables = new ArrayList<>();

        System.out.println("Souhaitez-vous attaquer un Hex ? (oui/non)");
        String reponse = scanner.nextLine().trim().toLowerCase();
        if (!reponse.equals("oui")) {
            System.out.println("Aucun Hex attaqué.");
            return;
        }
        pdj.decrirePlateau();
        // Parcourir les hex des vaisseaux du joueur et trouver les hex voisins
        for (Vaisseau vaisseau : this.getListeVaisseaux()) {
            Hex hex = vaisseau.getHex();
            for (Hex voisin : hex.getListeHexesVoisins()) {
                if (!hexAttaquables.contains(voisin)) {
                    hexAttaquables.add(voisin);
                }
            }
        }
        for (Vaisseau vaisseau : listeVaisseauUtilises){
            System.out.println(vaisseau);
        }

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

        // Afficher les hex attaquables avec le nombre de vaisseaux disponibles
        System.out.println("Hexagones possibles pour une attaque :");
        Map<Hex, Integer> hexAvecVaisseaux = new HashMap<>();
        for (Hex hex : hexAttaquables) {
            int vaisseauxDisponibles = 0;

            // Compter les vaisseaux disponibles des hex voisins
            for (Hex voisin : hex.getListeHexesVoisins()) {
                if (this.equals(voisin.getOccupant())) {
                    int vaisseauxNonUtilises =
                            (int) voisin.getListVaisseaux().stream()
                                    .filter(v -> !listeVaisseauUtilises.contains(v))
                                    .count();
                    vaisseauxDisponibles += vaisseauxNonUtilises;
                }
            }

            if (vaisseauxDisponibles > 0) {
                hexAvecVaisseaux.put(hex, vaisseauxDisponibles);
                System.out.println("Hex : (" + hex.getCoordonnees().get(0) + ", " + hex.getCoordonnees().get(1)
                        + ") - Vaisseaux disponibles : " + vaisseauxDisponibles);
            }
        }

        if (hexAvecVaisseaux.isEmpty()) {
            System.out.println("Aucun vaisseau disponible pour attaquer les hexagones potentiels.");
            return;
        }

        // Demander à l'utilisateur de choisir l'hex à attaquer
        Hex hexCible = null;
        while (hexCible == null) {
            try {
                System.out.println("Veuillez choisir un hexagone à attaquer (format: x y) :");
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                scanner.nextLine(); // Consommer la ligne restante

                for (Hex hex : hexAvecVaisseaux.keySet()) {
                    if (hex.getCoordonnees().get(0) == x && hex.getCoordonnees().get(1) == y) {
                        hexCible = hex;
                        break;
                    }
                }

                if (hexCible == null) {
                    System.out.println("Hex invalide. Veuillez choisir parmi les hexagones affichés.");
                }
            }
            catch (InputMismatchException e) {
                    System.out.println("Entrée invalide. Veuillez entrer des coordonnées sous la forme de deux nombres (ex: 3 5).");
                    scanner.nextLine();
            }
        }

        // Demander pour chaque voisin le nombre de vaisseaux à utiliser
        System.out.println("Vous avez choisi d'attaquer l'hex (" + hexCible.getCoordonnees().get(0) + ", "
                + hexCible.getCoordonnees().get(1) + ").");
        ArrayList<Vaisseau> listeVaisseauUtilisesCeTour = null;

        for (Hex voisin : hexCible.getListeHexesVoisins()) {
            if (this.equals(voisin.getOccupant())) {
                int vaisseauxDisponibles = (int) voisin.getListVaisseaux().stream()
                        .filter(v -> !listeVaisseauUtilises.contains(v))
                        .count();

                if (vaisseauxDisponibles > 0) {
                    System.out.println("Depuis l'hex voisin (" + voisin.getCoordonnees().get(0) + ", "
                            + voisin.getCoordonnees().get(1) + "), vous avez " + vaisseauxDisponibles + " vaisseaux disponibles.");

                    int vaisseauxAUtiliser = -1;

                    // Validation robuste de l'entrée utilisateur
                    while (true) {
                        System.out.println("Combien de vaisseaux voulez-vous utiliser depuis cet hex ? (max : " + vaisseauxDisponibles + ")");
                        String saisieUtilisateur = scanner.nextLine().trim();

                        try {
                            vaisseauxAUtiliser = Integer.parseInt(saisieUtilisateur);

                            if (vaisseauxAUtiliser >= 0 && vaisseauxAUtiliser <= vaisseauxDisponibles) {
                                // Ajouter les vaisseaux utilisés à la liste
                                listeVaisseauUtilisesCeTour = new ArrayList<>();
                                int vaisseauxAjoutes = 0;
                                for (Vaisseau v : voisin.getListVaisseaux()) {
                                    if (!listeVaisseauUtilises.contains(v) && vaisseauxAjoutes < vaisseauxAUtiliser) {
                                        listeVaisseauUtilisesCeTour.add(v);
                                        listeVaisseauUtilises.add(v);
                                        vaisseauxAjoutes++;
                                        System.out.println("Un vaisseau a été déplacé");
                                    }
                                }
                                break; // Sortir de la boucle si l'entrée est valide
                            } else {
                                System.out.println("Nombre invalide. Veuillez entrer un nombre entre 0 et " + vaisseauxDisponibles + ".");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Entrée invalide. Veuillez entrer un nombre entier.");
                        }
                    }


                    if (listeVaisseauUtilisesCeTour.isEmpty()) {
                        System.out.println("Aucun vaisseau n'a été utilisé pour attaquer.");
                    }
                }
            }
        }

        for (Vaisseau v : listeVaisseauUtilisesCeTour){
            v.setHex(hexCible);
            hexCible.getListVaisseaux().add(v);
        }
        this.resoudreConflit(hexCible);
    }


    public Hex choisirHexDepart(ArrayList<Secteur> secteursDejaChoisis, ArrayList<Hex> listeHex) {
        Scanner scanner = new Scanner(System.in);
        Hex hexChoisi = null;
        boolean hexValide = false;

        while (!hexValide) {
            try {
                System.out.println("C'est à " + this.getNom() + " d'entrer les coordonnées de l'hexagone de départ (format: x y) :");
                int x = scanner.nextInt();
                int y = scanner.nextInt();

                // Recherche de l'hex avec les coordonnées données
                for (Hex hex : listeHex) {
                    if (hex.getCoordonnees().get(0) == x && hex.getCoordonnees().get(1) == y) {
                        hexChoisi = hex;
                        break;
                    }
                }

                // Vérification : l'hex existe
                if (hexChoisi == null) {
                    System.out.println("Aucun hexagone ne correspond à ces coordonnées. Veuillez réessayer.");
                    continue;
                }

                // Vérification : l'hex n'appartient pas à un secteur déjà choisi
                boolean hexDansSecteur = false;
                for (Secteur secteur : secteursDejaChoisis) {
                    if (secteur.contientHex(hexChoisi)) {
                        hexDansSecteur = true;
                        System.out.println("L'hexagone appartient déjà au secteur : " + secteur.getNom());
                    }
                }

                if (hexDansSecteur) {
                    System.out.println("Voici les secteurs déjà choisis :");
                    for (Secteur secteur : secteursDejaChoisis) {
                        System.out.println("- " + secteur.getNom());
                    }
                    continue;
                }

                // Vérification : l'hex a une capacité de 2
                if (hexChoisi.getCapacite() != 2) {
                    System.out.println("L'hexagone choisi doit avoir un systeme I. Veuillez réessayer.");
                    continue;
                }

                // Si toutes les conditions sont remplies
                hexValide = true;
            }catch (InputMismatchException e) {
                    System.out.println("Entrée invalide. Veuillez entrer des coordonnées sous la forme de deux nombres (ex: 3 5).");
                    scanner.nextLine();
            }
        }

        System.out.println("\n"+this.getNom()+" a choisi l'hexagone " +
                hexChoisi.getCoordonnees().get(0) + ", " +
                hexChoisi.getCoordonnees().get(1) + ".");
        return hexChoisi;
    }

    public static void main(String[] args) {

	}

}
