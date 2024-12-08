package pocketImperium;

import java.util.ArrayList;

import java.util.List;
import java.util.Scanner;

public class JoueurPhysique extends Joueur{
	
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
                System.out.println("Carte invalide. Les options disponibles sont : Expander, Explorer, Exterminator.");
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
                if (secteur.getNom().equalsIgnoreCase(nomChoisi)) {
                    secteurChoisi = secteur;
                    break;
                }
            }
            if (secteurChoisi == null) {
                System.out.println("Nom invalide.Les options disponibles sont : Secteur1, Secteur2, etc jusqu'à 9. Essayez à nouveau.");
            }
        } while (secteurChoisi == null);

        System.out.println("Vous avez choisi le secteur : " + secteurChoisi.getNom());
        return secteurChoisi;
    }

    public void ajouterVaisseau(ArrayList<Hex> listeHex) {
        Scanner scanner = new Scanner(System.in);

        // Vérification du nombre de vaisseaux du joueur
        if (this.getListeVaisseaux().size() >= 15) {
            System.out.println("Vous avez déjà 15 vaisseaux. Vous ne pouvez pas en ajouter davantage.");
            return;
        }

        Hex hexChoisi = null;

        while (true) {
            System.out.println("Entrez les coordonnées de l'hexagone où vous souhaitez ajouter un vaisseau (format: x y):");
            int x = scanner.nextInt();
            int y = scanner.nextInt();

            // Recherche de l'hex correspondant aux coordonnées
            for (Hex hex : listeHex) {
                if (hex.getCoordonnees().get(0) == x && hex.getCoordonnees().get(1) == y) {
                    hexChoisi = hex;
                    break;
                }
            }

            if (hexChoisi == null) {
                System.out.println("Aucun hexagone ne correspond à ces coordonnées. Veuillez réessayer.");
            } else if (!this.getNom().equals(hexChoisi.getOccupant())) {
                System.out.println("Cet hexagone n'est pas occupé par vous. Vous ne pouvez pas y ajouter de vaisseau. Veuillez réessayer.");
            } else {
                break; // Coordonnées valides et hex occupé par le joueur
            }
        }

        // Création et ajout du vaisseau
        Vaisseau nouveauVaisseau = new Vaisseau(this);
        hexChoisi.getListVaisseaux().add(nouveauVaisseau);
        this.getListeVaisseaux().add(nouveauVaisseau);

        System.out.println("Un nouveau vaisseau a été ajouté sur l'hexagone " +
                hexChoisi.getCoordonnees().get(0) + ", " +
                hexChoisi.getCoordonnees().get(1) + ".");
    }

	public void déplacer() {
        System.out.println(this.getNom()+"  a déplacer une flotte");
	}	
	
	public void attaquerHex() {
        System.out.println(this.getNom()+"  a attaqué un hex");
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JoueurPhysique joueur1 =new JoueurPhysique("Loélie","rouge",1);
        JoueurVirtuel joueur2 =new JoueurVirtuel("Gab","bleu",2);
        JoueurVirtuel joueur3 =new JoueurVirtuel("Swann","vert",3);

        ArrayList<Joueur> liste0= new ArrayList<>();
        liste0.add(joueur1);
        liste0.add(joueur2);
        liste0.add(joueur3);
        joueur1.setListeJoueur(liste0);
        joueur2.setListeJoueur(liste0);
        joueur3.setListeJoueur(liste0);
		Tour tour1= new Tour(liste0,1);
        PlateauDeJeu pdj = new PlateauDeJeu();

        tour1.gestionTour(pdj);

	}

}
