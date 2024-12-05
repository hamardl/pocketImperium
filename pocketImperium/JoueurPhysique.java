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
                System.out.println("Nom invalide. Essayez à nouveau.");
            }
        } while (secteurChoisi == null);

        System.out.println("Vous avez choisi le secteur : " + secteurChoisi.getNom());
        return secteurChoisi;
    }

	public void ajouterVaisseau() {
        System.out.println(this.getNom()+"  a ajouté un vaisseau");
		
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
