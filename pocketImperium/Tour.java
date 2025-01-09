package pocketImperium;
import java.io.Serializable;
import java.util.ArrayList;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * La classe {@code Tour} représente un tour de jeu dans le cadre du jeu Pocket Imperium.
 * Un tour inclut une liste de joueurs, un numéro de tour, et une référence à la partie en cours.
 *
 * <p>Elle contient la logique principale pour gérer les différentes étapes du tour,
 * telles que l'ordre des cartes, les actions des joueurs, et la mise à jour des scores.</p>
 *
 * @author [Loélie Hamard]
 */


public class Tour implements Serializable {

	/** Liste des joueurs participant au tour. */

	private ArrayList<Joueur> listeJoueur;

	/** Numéro du tour actuel. */

	private int numero;

	/** Référence à la partie associée au tour. */

	private Partie partie;

	/**
	 * Constructeur de la classe {@code Tour}.
	 *
	 * @param listeJoueur la liste des joueurs participant au tour.
	 * @param numero le numéro du tour.
	 * @param partie la partie associée au tour.
	 */


	public Tour(ArrayList<Joueur> listeJoueur,int numero,Partie partie){
		this.listeJoueur=listeJoueur;
		this.numero=numero;
		this.partie=partie;
	}

	/**
	 * Définit le numéro du tour.
	 *
	 * @param num le nouveau numéro du tour.
	 */

	public void setNumero(int num){
		this.numero=num;
	}

	/**
	 * Retourne la partie associée au tour.
	 *
	 * @return la partie en cours.
	 */

	public Partie getPartie(){
		return this.partie;
	}

	/**
	 * Gère les différentes étapes du tour de jeu, incluant :
	 * <ul>
	 *   <li>Etape 1: Chaque joueur ordonne ses 3 cartes.</li>
	 *   <li>Etape 2: Chaque joueur révèle sa carte.</li>
	 *   <li>Etape 3: Déterminer l'ordre de jeu, et obtenir une liste de joueur ordonnes dans l'odre ou ils doivent jouer.</li>
	 *   <li>Etape 4: Chaque joueur joue sa carte.</li>
	 *   <li>Etape 5: Entrenir les vaisseaux sur le plateau</li>
	 *   </li>Etape 6: Vérifier que les joueurs ont encore des vaisseaux sinon le numéro du tour devient 9 et c'est la fin de partie</li>
	 *   <li>Etape 7: Chaque Joueur choisit un secteur et leur score est mis à jour.</li>
	 * </ul>
	 *
	 * @param pdj le plateau de jeu utilisé pour ce tour.
	 */


	// Méthode principale de gestion du jeu
	public void gestionTour(PlateauDeJeu pdj) {
		// Etape 1: Chaque joueur utilise ordonnerCarte()
		ArrayList<Joueur> joueursTries2 = new ArrayList<>(listeJoueur);
		joueursTries2.sort(Comparator.comparingInt(Joueur::getOrdreDeJeu));// Tri croissant
		System.out.println("\n--Les joueurs vont ordonner leurs cartes.");
		for (Joueur joueur : joueursTries2) {
			System.out.println("\n C'est à "+joueur.getNom()+" d'ordonner ses cartes");
			joueur.ordonnerCarte();  // Appel de la méthode ordonnerCarte() pour chaque joueur
		}

		// Etape 2: Chaque joueur révèle sa carte
		for (int i = 0; i < 3; i++) {
			System.out.println("\n\n--Les joueurs révèle leur carte.");
			for (Joueur joueur : joueursTries2) {
				joueur.revelerCarte(joueur.getListeDeCarteOrdonnee());// Chaque joueur révèle sa carte à chaque tour
				System.out.println("\n"+joueur.getNom() + " a choisit la carte " + joueur.getDerniereCarteRevelee());
			}
			// Etape 3: Déterminer l'ordre de jeu et obtenir la nouvelle liste d'ordre
			ArrayList<Joueur> ordreDeJeu = trierJoueursParCarteEtOrdre(listeJoueur);
			// Etape 4: Parcourir la liste d'ordre et chaque joueur joue sa carte
			for (Joueur joueur : ordreDeJeu) {
				System.out.println("\n\n--C'est à "+joueur.getNom()+" de jouer.");
				joueur.jouerCarte(pdj);  // Chaque joueur joue sa carte selon l'ordre déterminé
			}
		}
			//etape 5 : entrenir vaisseaux
			System.out.println("\n\n--Entretiens des Vaisseaux");
			pdj.entretenirVaisseaux();

			//etape 6 vérifier que les joueurs ont bien des vaisseaux sinon tour=9 et fin de partie
		for (Joueur joueur :listeJoueur){
			if(joueur.getListeVaisseaux().isEmpty()){
				this.setNumero(9);
				System.out.println(joueur.getNom()+" n'a plus de vaisseaux.\nFin de la partie.\n Calcul final des scores");
			}
		}

			System.out.println("\n\n--Choix de Secteurs pour marquer des points");
			if(this.numero<9) {
				//etape 7 :choisir secetur et update score
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
				for (Joueur joueur : joueursTries) {
					joueur.setOrdreDeJeu(joueur.getOrdreDeJeu()-1);
					if(joueur.getOrdreDeJeu()==0){
						joueur.setOrdreDeJeu(3);
					}
				}
			}
			else{
				System.out.println("\n\n--Calcul des scores final");
				for (Secteur secteur : pdj.getListeSecteur()){
					calculScore(secteur);
					calculScore(secteur);
				}
				Joueur gagnant= listeJoueur.getFirst();;
				for (Joueur joueur :listeJoueur){
					if (joueur.getScore()> gagnant.getScore()){
						gagnant=joueur;
					}
					System.out.println("\n le score de "+ joueur.getNom()+ " est : "+joueur.getScore());
				}
				System.out.println("\n\nLe gagnant est : "+gagnant.getNom());
			}

	}
	/**
	 * Trie les joueurs en fonction de la carte révélée et de leur ordre de jeu.
	 *
	 * <p>Les joueurs sont triés par catégories de cartes jouées :
	 * <ul>
	 *   <li>Les joueurs ayant joué la carte Expand apparaissent en premier.</li>
	 *   <li>Les joueurs ayant joué la carte Explore apparaissent ensuite.</li>
	 *   <li>Enfin, les joueurs ayant joué la carte Exterminate.</li>
	 * </ul>
	 * Les joueurs d'une même catégorie sont triés par ordre de jeu croissant.</p>
	 *
	 * @param joueurs la liste des joueurs à trier.
	 * @return une liste triée des joueurs.
	 */



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

	/**
	 * Calcule les scores pour un secteur donné.
	 *
	 * <p>Les scores sont mis à jour pour chaque joueur occupant un hexagone du secteur.
	 * La capacité des hexagones est prise en compte :
	 * <ul>
	 *   <li>Un hexagone avec une capacité de 2 ajoute 1 point au joueur occupant.</li>
	 *   <li>Un hexagone avec une capacité de 3 ajoute 2 points au joueur occupant.</li>
	 * </ul></p>
	 *
	 * @param secteur le secteur choisit par un joueur pour lequel les scores sont modifié.
	 */


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

	/**
	 * Retourne le numéro du tour.
	 *
	 * @return le numéro actuel du tour.
	 */

	public int getNumero(){
		return numero;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
