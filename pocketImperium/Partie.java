package pocketImperium;

import java.util.ArrayList;
import java.util.*;

public class Partie {
	// Attributs de la classe
	private PlateauDeJeu plateauDeJeu;  // Plateau de jeu
	private ArrayList<Joueur> listeJoueur;  // Liste des joueurs
	private Tour tour;

	// Constructeur
	public Partie() {
		this.plateauDeJeu = new PlateauDeJeu();
		this.listeJoueur=this.creationJoueur();
	}

	// Getter pour plateauDeJeu
	public PlateauDeJeu getPlateauDeJeu() {
		return plateauDeJeu;

	}

	// Setter pour plateauDeJeu
	public void setPlateauDeJeu(PlateauDeJeu plateauDeJeu) {
		this.plateauDeJeu = plateauDeJeu;
	}

	// Getter pour tour
	public Tour getTour() {
		return tour;
	}

	// Setter pour tour
	public void setPlateauDeJeu(Tour tour) {
		this.tour = tour;
	}

	// Getter pour listeJoueur
	public ArrayList<Joueur> getListeJoueur() {
		return listeJoueur;
	}

	// Setter pour listeJoueur
	public void setListeJoueur(ArrayList<Joueur> listeJoueur) {
		this.listeJoueur = listeJoueur;
	}

	public void commencerPartie() {
		this.initialisation();
		for (int i = 0; i < 9; i++) {
			System.out.println("\n\nDébut du tour numéro "+(i+1)+"\n");
			Tour tour = new Tour(this.getListeJoueur(),(i+1));
			tour.gestionTour(this.plateauDeJeu);
		}
	}
	
	public void quitterPartie() {
		
	}
	
	public void sauvgarderPartie(){
		
	}

	public ArrayList<Joueur> creationJoueur() {
		Scanner scanner = new Scanner(System.in);
		ArrayList<Joueur> joueurs = new ArrayList<>();
		List<String> couleursDisponibles = new ArrayList<>(List.of("rouge", "bleue", "vert"));
		List<Integer> ordresDisponibles = new ArrayList<>(List.of(1, 2, 3));

		// Demander le nombre de robots
		int nbRobots = -1;
		while (nbRobots < 0 || nbRobots > 3) {
			System.out.println("Combien de robots voulez-vous ? (0, 1, 2 ou 3)");
			String saisieUtilisateur = scanner.nextLine().trim();
			try {
				nbRobots = Integer.parseInt(saisieUtilisateur);
				if (nbRobots < 0 || nbRobots > 3) {
					System.out.println("Nombre invalide. Veuillez entrer un nombre entre 0 et 3.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Entrée invalide. Veuillez entrer un nombre entier.");
			}
		}

		int nbJoueursReels = 3 - nbRobots;

		// Ajouter les joueurs humains
		for (int i = 0; i < nbJoueursReels; i++) {
			String nom = null;
			boolean nomValide = false;

			while (!nomValide) {
				System.out.println("Entrez le nom du joueur physique " + (i + 1) + " (au moins 2 caractères) :");
				nom = scanner.nextLine().trim();
				nomValide = isValidNom(nom) && isNomUnique(nom, joueurs);
				if (!nomValide) {
					System.out.println("Nom invalide ou déjà pris, veuillez réessayer.");
				}
			}

			// Choisir une couleur
			String couleur = null;
			while (couleur == null || !couleursDisponibles.contains(couleur)) {
				System.out.println("Choisissez une couleur parmi les suivantes : " + couleursDisponibles);
				couleur = scanner.nextLine().trim();
				if (!couleursDisponibles.contains(couleur)) {
					System.out.println("Couleur invalide. Veuillez choisir parmi les options disponibles.");
				}
			}

			couleursDisponibles.remove(couleur);

			// Attribuer un ordre de jeu
			int ordreDeJeu = ordresDisponibles.remove(0);

			// Ajouter le joueur humain
			joueurs.add(new JoueurPhysique(nom, couleur, ordreDeJeu));
			System.out.println("Joueur " + nom + " créé avec la couleur " + couleur + " et l'ordre de jeu " + ordreDeJeu + ".");
		}

		// Ajouter les robots
		for (int i = 0; i < nbRobots; i++) {
			String nom = null;
			boolean nomValide = false;

			while (!nomValide) {
				System.out.println("Entrez le nom du robot " + (i + 1) + " (au moins 2 caractères) :");
				nom = scanner.nextLine().trim();
				nomValide = isValidNom(nom) && isNomUnique(nom, joueurs);
				if (!nomValide) {
					System.out.println("Nom invalide ou déjà pris, veuillez réessayer.");
				}
			}

			// Prendre une couleur disponible
			String couleur = couleursDisponibles.remove(0);

			// Attribuer un ordre de jeu
			int ordreDeJeu = ordresDisponibles.remove(0);

			// Ajouter le robot
			joueurs.add(new JoueurVirtuel(nom, couleur, ordreDeJeu));
			System.out.println("Robot " + nom + " créé avec la couleur " + couleur + " et l'ordre de jeu " + ordreDeJeu + ".");
		}

		// Définir la liste des joueurs pour chaque joueur
		for (Joueur joueur : joueurs) {
			joueur.setListeJoueur(joueurs);
		}

		return joueurs;
	}

	private boolean isValidNom(String nom) {
		return nom != null && !nom.isBlank() && nom.length() >= 2;
	}

	private boolean isNomUnique(String nom, List<Joueur> joueurs) {
		for (Joueur joueur : joueurs) {
			if (joueur.getNom().equalsIgnoreCase(nom)) {
				return false; // Le nom existe déjà
			}
		}
		return true; // Le nom est unique
	}




	public void initialisation() {
		// Vérification du nombre de joueurs
		if (this.listeJoueur.size() != 3) {
			System.out.println("Erreur : La partie nécessite exactement 3 joueurs.");
			return;
		}

		// Trier la liste des joueurs par ordre de jeu (ordre croissant)
		ArrayList<Joueur> joueursOrdonnes = new ArrayList<>(this.listeJoueur);
		joueursOrdonnes.sort((joueur1, joueur2) -> Integer.compare(joueur1.getOrdreDeJeu(), joueur2.getOrdreDeJeu()));

		// Liste des secteurs déjà choisis
		ArrayList<Secteur> secteursDejaChoisis = new ArrayList<>();

		// Première boucle : Ordre croissant
		System.out.println("\n\nChoix de placement des vaisseaux de départ");
		System.out.println("\nDans l'ordre croissant : ");
		for (Joueur joueur : joueursOrdonnes) {
			traiterChoixHex(joueur, secteursDejaChoisis);
		}

		// Deuxième boucle : Ordre décroissant
		System.out.println("\nDans l'ordre décroissant : ");
		for (int i = joueursOrdonnes.size() - 1; i >= 0; i--) {
			traiterChoixHex(joueursOrdonnes.get(i), secteursDejaChoisis);
		}
	}

	/**
	 * Méthode utilitaire pour gérer le choix d'un hex pour un joueur donné.
	 */
	private void traiterChoixHex(Joueur joueur, ArrayList<Secteur> secteursDejaChoisis) {

			// Le joueur choisit un hex de départ
			Hex hexChoisi = joueur.choisirHexDepart(secteursDejaChoisis,plateauDeJeu.getListeHex());

			// Créer 2 vaisseau pour le joueur
			Vaisseau vaisseau1 = new Vaisseau(joueur,joueur.getListeVaisseaux().size());
			joueur.getListeVaisseaux().add(vaisseau1);
			vaisseau1.setHex(hexChoisi);
		    Vaisseau vaisseau2 = new Vaisseau(joueur,joueur.getListeVaisseaux().size());
			joueur.getListeVaisseaux().add(vaisseau2);
			vaisseau2.setHex(hexChoisi);

			// Ajouter le vaisseau à l'hex et au joueur
			hexChoisi.getListVaisseaux().add(vaisseau1);
			hexChoisi.getListVaisseaux().add(vaisseau2);


			// Ajouter le secteur contenant l'hex choisi à la liste des secteurs utilisés
			for (Secteur secteur : plateauDeJeu.getListeSecteur()) {
				if (secteur.contientHex(hexChoisi)) {
					secteursDejaChoisis.add(secteur);
					break;
				}
			}

			System.out.println("Le joueur " + joueur.getNom() + " a placé 2 vaisseau sur l'hexagone " +
					hexChoisi.getCoordonnees().get(0) + ", " + hexChoisi.getCoordonnees().get(1) + ".");
	}



	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
