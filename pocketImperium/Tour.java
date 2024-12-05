package pocketImperium;
import java.util.ArrayList;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Tour {
	private ArrayList<Joueur> listeJoueur;
	private int numero;

	public Tour(ArrayList<Joueur> listeJoueur,int numero){
		this.listeJoueur=listeJoueur;
		this.numero=numero;
	}

	// Méthode principale de gestion du jeu
	public void gestionTour(PlateauDeJeu pdj) {
		// Etape 1: Chaque joueur utilise ordonnerCarte()
		for (Joueur joueur : listeJoueur) {
			System.out.println("c'est à"+joueur.getNom()+"d'ordonner ses cartes");
			joueur.ordonnerCarte();  // Appel de la méthode ordonnerCarte() pour chaque joueur
		}

		// Etape 2: Chaque joueur révèle sa carte
		for (int i = 0; i < 3; i++) {
			for (Joueur joueur : listeJoueur) {
				joueur.revelerCarte(joueur.getListeDeCarteOrdonnee());// Chaque joueur révèle sa carte à chaque tour
				System.out.println(joueur.getNom() + "a choisit la carte" + joueur.getDerniereCarteRevelee());
			}

			// Etape 3: Déterminer l'ordre de jeu et obtenir la nouvelle liste d'ordre
			ArrayList<Joueur> ordreDeJeu = trierJoueursParCarteEtOrdre(listeJoueur);

			// Etape 4: Parcourir la liste d'ordre et chaque joueur joue sa carte
			for (Joueur joueur : ordreDeJeu) {
				joueur.jouerCarte();  // Chaque joueur joue sa carte selon l'ordre déterminé
			}
		}
			//etape 5 : entrenir vaisseaux
			pdj.entretenirVaisseaux();

			if(this.numero!=9) {
				//etape 6 :choisir secetur et update score
				// Créer une nouvelle liste triée en fonction de l'attribut ordreDeJeu
				ArrayList<Joueur> joueursTries = new ArrayList<>(listeJoueur);
				joueursTries.sort(Comparator.comparingInt(Joueur::getOrdreDeJeu)); // Tri croissant

				Joueur joueurOccupantTriprim=null;
				for (Hex hex : pdj.getListeHex()) {
					// Vérifier si l'Hex est "TriPrim" et que l'occupant correspond au joueur
					if (hex.isEstTriprim()) {
						joueurOccupantTriprim=hex.getOccupant();
					}
				}
				// Initialisation d'une liste pour suivre les secteurs déjà choisis
				ArrayList<Secteur> secteursDejaChoisis = new ArrayList<>();

				// Parcourir la liste triée et appeler choisirSecteur pour chaque joueur
				for (Joueur joueur : joueursTries) {
					if (joueur == joueurOccupantTriprim) {
						System.out.println("C'est au tour de " + joueur.getNom() + " de choisir deux secteurs.");

						// Premier choix
						Secteur secteur1;
						do {
							secteur1 = joueur.choisirSecteur(pdj.getListeSecteur()); // Appelle la méthode choisirSecteur
							if (secteursDejaChoisis.contains(secteur1)) {
								System.out.println("Ce secteur a déjà été choisi. Veuillez en choisir un autre.");
							}
						} while (secteursDejaChoisis.contains(secteur1));
						secteursDejaChoisis.add(secteur1); // Ajoute le secteur choisi à la liste
						joueur.calculScore(secteur1);

						// Deuxième choix
						Secteur secteur2;
						do {
							secteur2 = joueur.choisirSecteur(pdj.getListeSecteur()); // Appelle la méthode choisirSecteur
							if (secteursDejaChoisis.contains(secteur2)) {
								System.out.println("Ce secteur a déjà été choisi. Veuillez en choisir un autre.");
							}
						} while (secteursDejaChoisis.contains(secteur2));
						secteursDejaChoisis.add(secteur2); // Ajoute le secteur choisi à la liste
						joueur.calculScore(secteur2);

					} else {
						System.out.println("C'est au tour de " + joueur.getNom() + " de choisir un secteur.");

						// Unique choix pour les autres joueurs
						Secteur secteur;
						do {
							secteur = joueur.choisirSecteur(pdj.getListeSecteur()); // Appelle la méthode choisirSecteur
							if (secteursDejaChoisis.contains(secteur)) {
								System.out.println("Ce secteur a déjà été choisi. Veuillez en choisir un autre.");
							}
						} while (secteursDejaChoisis.contains(secteur));
						secteursDejaChoisis.add(secteur); // Ajoute le secteur choisi à la liste
						joueur.calculScore(secteur);
					}
				}
				//switch l'ordre de Jeu
				int odj = 0;
				for (Joueur joueur : joueursTries) {
					odj += 1;
					joueur.setOrdreDeJeu(odj); // Appelle la méthode choisirSecteur
				}
			}
			else{

			}

	}


	public ArrayList<Joueur> trierJoueursParCarteEtOrdre(ArrayList<Joueur> joueurs) {

		if (joueurs == null || joueurs.isEmpty()) {
			System.out.println("La liste des joueurs est vide ou non initialisée.");
			return new ArrayList<>(); // Retourne une liste vide si l'argument est invalide
		}

		ArrayList<Joueur> nouvelleListe = new ArrayList<>();

		// Ajouter les joueurs avec la carte Expand, triés par ordre de jeu croissant
		joueurs.stream()
				.filter(joueur -> joueur.getDerniereCarteRevelee() == Carte.Expand)
				.sorted(Comparator.comparingInt(Joueur::getOrdreDeJeu))
				.forEach(nouvelleListe::add);

		// Ajouter les joueurs avec la carte Explore, triés par ordre de jeu croissant
		joueurs.stream()
				.filter(joueur -> joueur.getDerniereCarteRevelee() == Carte.Explore)
				.sorted(Comparator.comparingInt(Joueur::getOrdreDeJeu))
				.forEach(nouvelleListe::add);

		// Ajouter les joueurs avec la carte Exterminate, triés par ordre de jeu croissant
		joueurs.stream()
				.filter(joueur -> joueur.getDerniereCarteRevelee() == Carte.Exterminate)
				.sorted(Comparator.comparingInt(Joueur::getOrdreDeJeu))
				.forEach(nouvelleListe::add);

		return nouvelleListe;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
