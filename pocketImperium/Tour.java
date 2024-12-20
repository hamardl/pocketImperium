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
		System.out.println("\nLes joueurs vont ordonner leurs cartes.");
		for (Joueur joueur : listeJoueur) {
			System.out.println("\n C'est à "+joueur.getNom()+" d'ordonner ses cartes");
			joueur.ordonnerCarte();  // Appel de la méthode ordonnerCarte() pour chaque joueur
		}

		// Etape 2: Chaque joueur révèle sa carte
		for (int i = 0; i < 3; i++) {
			System.out.println("\n\nLes joueurs révèle leur carte.");
			for (Joueur joueur : listeJoueur) {
				joueur.revelerCarte(joueur.getListeDeCarteOrdonnee());// Chaque joueur révèle sa carte à chaque tour
				System.out.println("\n"+joueur.getNom() + " a choisit la carte " + joueur.getDerniereCarteRevelee());
			}
			// Etape 3: Déterminer l'ordre de jeu et obtenir la nouvelle liste d'ordre
			ArrayList<Joueur> ordreDeJeu = trierJoueursParCarteEtOrdre(listeJoueur);
			// Etape 4: Parcourir la liste d'ordre et chaque joueur joue sa carte
			for (Joueur joueur : ordreDeJeu) {
				System.out.println("\nC'est à "+joueur.getNom()+" de jouer.");
				joueur.jouerCarte(pdj);  // Chaque joueur joue sa carte selon l'ordre déterminé
			}
		}
			//etape 5 : entrenir vaisseaux
			System.out.println("\n\n Entretiens des Vaisseaux");
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
						System.out.println("\n\nC'est au tour de " + joueur.getNom() + " de choisir deux secteurs.");

						// Premier choix
						Secteur secteurA;
						do {
							secteurA = joueur.choisirSecteur(pdj.getListeSecteur()); // Appelle la méthode choisirSecteur
							if (secteursDejaChoisis.contains(secteurA)) {
								System.out.println("Ce secteur a déjà été choisi. Veuillez en choisir un autre.");
							}
						} while (secteursDejaChoisis.contains(secteurA));
						secteursDejaChoisis.add(secteurA); // Ajoute le secteur choisi à la liste
						this.calculScore(secteurA);

						// Deuxième choix
						Secteur secteurB;
						do {
							secteurB = joueur.choisirSecteur(pdj.getListeSecteur()); // Appelle la méthode choisirSecteur
							if (secteursDejaChoisis.contains(secteurB)) {
								System.out.println("Ce secteur a déjà été choisi. Veuillez en choisir un autre.");
							}
						} while (secteursDejaChoisis.contains(secteurB));
						secteursDejaChoisis.add(secteurB); // Ajoute le secteur choisi à la liste
						this.calculScore(secteurB);

					} else {
						System.out.println("\n\nC'est au tour de " + joueur.getNom() + " de choisir un secteur.");

						// Unique choix pour les autres joueurs
						Secteur secteur;
						do {
							secteur = joueur.choisirSecteur(pdj.getListeSecteur()); // Appelle la méthode choisirSecteur
							if (secteursDejaChoisis.contains(secteur)) {
								System.out.println("Ce secteur a déjà été choisi. Veuillez en choisir un autre.");
							}
						} while (secteursDejaChoisis.contains(secteur));
						secteursDejaChoisis.add(secteur);// Ajoute le secteur choisi à la liste
						this.calculScore(secteur);
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

	// Calcul du score en vérifiant l'occupant
	public void calculScore(Secteur secteur) {
		for (Hex hex : secteur.getListeHex()) {
			// Vérifie si l'hex a un occupant
			Joueur occupant = hex.getOccupant();
			if (occupant == null) {
				continue; // Passe au prochain hex si aucun occupant
			}

			for (Joueur joueur : listeJoueur) {
				// Vérifie si le nom de l'occupant correspond au joueur
				if (occupant.equals(joueur)) {
					int capacite = hex.getCapacite();
					if (capacite == 2) {
						joueur.score += 1;
					} else if (capacite == 3) {
						joueur.score += 2;
					}
				}
			}
		}
		for (Joueur joueur : listeJoueur) {
		System.out.println("Score actuel pour " + joueur.getNom() + ": " + joueur.score);
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
