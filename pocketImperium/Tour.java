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

				// Parcourir la liste triée et appeler choisirSecteur pour chaque joueur
				for (Joueur joueur : joueursTries) {

				/*
				for (Hex hex : pdj.getListHex()) {
					// Vérifier si l'Hex est "TriPrim" et que l'occupant correspond au joueur
					if (hex.estTriPrim() && hex.getOccupant() == joueur)
				*/
					System.out.println("C'est au tour de " + joueur.getNom() + " de choisir un secteur.");
					joueur.choisirSecteurEtScore(); // Appelle la méthode choisirSecteur
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
