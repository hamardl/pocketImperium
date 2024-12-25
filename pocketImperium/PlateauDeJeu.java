package pocketImperium;
import javax.swing.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static javax.swing.UIManager.get;

public class PlateauDeJeu implements Serializable {

	private ArrayList<Hex> listeHex;         // Liste des Hex sur le plateau
	private ArrayList<Secteur> listeSecteur; // Liste des Vaisseaux sur le plateau

	// Constructeur
	public PlateauDeJeu() {
		ArrayList<Hex> listeHex = new ArrayList<>();
		this.listeHex = listeHex;
		//on initialise les hex
		for (int j = 1; j <= 9; j++) {
			if (j % 2 != 0) {
				int k = 6;
				for (int i = 1; i <= k; i++) {
					ArrayList<Integer> list0 = new ArrayList<>();
					list0.add(j);//numéro ligne
					list0.add(i);//numéro collone
					Hex hex = new Hex(list0);
					this.listeHex.add(hex);
				}
			} else {
				int k = 5;
				for (int i = 1; i <= k; i++) {
					ArrayList<Integer> list0 = new ArrayList<>();
					list0.add(j);
					list0.add(i);
					Hex hex = new Hex(list0);
					this.listeHex.add(hex);
				}
			}
		}

		//on initialise leur liste de Hex voisins
		for (Hex hex : this.listeHex) {
			ArrayList<Hex> voisins = new ArrayList<>();
			int n = hex.getCoordonnees().get(0); // Coordonnée n
			int m = hex.getCoordonnees().get(1); // Coordonnée m

			// Recherche des voisins
			for (Hex autreHex : this.listeHex) {
				int autreN = autreHex.getCoordonnees().get(0);
				int autreM = autreHex.getCoordonnees().get(1);
				if (n % 2 == 0) {
					// Comparer les coordonnées pour voir si ce sont des voisins
					if ((autreN == n - 1 && autreM == m) ||      // n-1, m
							(autreN == n - 1 && autreM == m + 1) ||  // n-1, m-1
							(autreN == n && autreM == m - 1) ||      // n, m-1
							(autreN == n && autreM == m + 1) ||      // n, m+1
							(autreN == n + 1 && autreM == m + 1) ||  // n+1, m-1
							(autreN == n + 1 && autreM == m)) {      // n+1, m
						voisins.add(autreHex);
					}
				} else {
					// Comparer les coordonnées pour voir si ce sont des voisins
					if ((autreN == n - 1 && autreM == m) ||      // n-1, m
							(autreN == n - 1 && autreM == m - 1) ||  // n-1, m-1
							(autreN == n && autreM == m - 1) ||      // n, m-1
							(autreN == n && autreM == m + 1) ||      // n, m+1
							(autreN == n + 1 && autreM == m - 1) ||  // n+1, m-1
							(autreN == n + 1 && autreM == m)) {      // n+1, m
						voisins.add(autreHex);
					}
				}
			}
			// Ajouter les voisins au Hex actuel
			hex.setListeHexesVoisins(voisins);
		}
		//on initialise triPrim, on met en commun les hex voisins et on remplace dans les hex voisins de triPrim pour voir par
		Hex triprim = null;
		Hex hex53 = null;
		Hex hex54 = null;
		Hex hex63 = null;

		// Trouver les hex correspondant
		for (Hex hex : this.listeHex) {
			ArrayList<Integer> coords = hex.getCoordonnees();
			if (coords.get(0) == 4 && coords.get(1) == 3) {
				triprim = hex;
			} else if (coords.get(0) == 5 && coords.get(1) == 3) {
				hex53 = hex;
			} else if (coords.get(0) == 5 && coords.get(1) == 4) {
				hex54 = hex;
			} else if (coords.get(0) == 6 && coords.get(1) == 3) {
				hex63 = hex;
			}
		}

		if (triprim == null || hex53 == null || hex54 == null || hex63 == null) {
			System.out.println("Erreur : Impossible de configurer le Triprim, certains Hex sont introuvables.");
			return;
		}

		// Mettre en commun les voisins
		HashSet<Hex> voisinsCommuns = new HashSet<>();
		voisinsCommuns.addAll(hex53.getListeHexesVoisins());
		voisinsCommuns.addAll(hex54.getListeHexesVoisins());
		voisinsCommuns.addAll(hex63.getListeHexesVoisins());
		voisinsCommuns.addAll(triprim.getListeHexesVoisins());

		// Supprimer les Hex constituants du Triprim des voisins
		voisinsCommuns.remove(hex53);
		voisinsCommuns.remove(hex54);
		voisinsCommuns.remove(hex63);
		voisinsCommuns.remove(triprim);

		// Mettre à jour les voisins de triprim
		triprim.setListeHexesVoisins(new ArrayList<>(voisinsCommuns));

		// Supprimer les Hex constituants du Triprim
		listeHex.remove(hex53);
		listeHex.remove(hex54);
		listeHex.remove(hex63);

		// Parcourir les voisins du Triprim et mettre à jour leurs listes de voisins
		for (Hex voisin : triprim.getListeHexesVoisins()) {
			ArrayList<Hex> voisinsDeVoisin = voisin.getListeHexesVoisins();
			if (voisinsDeVoisin.contains(hex53)) {
				voisinsDeVoisin.remove(hex53);
			}
			if (voisinsDeVoisin.contains(hex54)) {
				voisinsDeVoisin.remove(hex54);
			}
			if (voisinsDeVoisin.contains(hex63)) {
				voisinsDeVoisin.remove(hex63);
			}
			if (!voisinsDeVoisin.contains(triprim)) {
				voisinsDeVoisin.add(triprim);
			}
		}

		// Marquer le Triprim
		triprim.setEstTriprim(true);
		triprim.setCapacite(4);
		//initialise les capacité


		// Hex avec capacité de 3
		ArrayList<ArrayList<Integer>> capacite3Coords = new ArrayList<>();
		capacite3Coords.add(new ArrayList<>(Arrays.asList(1, 1)));
		capacite3Coords.add(new ArrayList<>(Arrays.asList(3, 3)));
		capacite3Coords.add(new ArrayList<>(Arrays.asList(4, 1)));
		capacite3Coords.add(new ArrayList<>(Arrays.asList(3, 6)));
		capacite3Coords.add(new ArrayList<>(Arrays.asList(5, 6)));
		capacite3Coords.add(new ArrayList<>(Arrays.asList(8, 1)));
		capacite3Coords.add(new ArrayList<>(Arrays.asList(8, 3)));
		capacite3Coords.add(new ArrayList<>(Arrays.asList(8, 5)));

		// Hex avec capacité de 2
		ArrayList<ArrayList<Integer>> capacite2Coords = new ArrayList<>();
		capacite2Coords.add(new ArrayList<>(Arrays.asList(1, 3)));
		capacite2Coords.add(new ArrayList<>(Arrays.asList(1, 5)));
		capacite2Coords.add(new ArrayList<>(Arrays.asList(1, 6)));
		capacite2Coords.add(new ArrayList<>(Arrays.asList(2, 1)));
		capacite2Coords.add(new ArrayList<>(Arrays.asList(2, 3)));
		capacite2Coords.add(new ArrayList<>(Arrays.asList(3, 2)));
		capacite2Coords.add(new ArrayList<>(Arrays.asList(4, 5)));
		capacite2Coords.add(new ArrayList<>(Arrays.asList(5, 1)));
		capacite2Coords.add(new ArrayList<>(Arrays.asList(6, 1)));
		capacite2Coords.add(new ArrayList<>(Arrays.asList(6, 5)));
		capacite2Coords.add(new ArrayList<>(Arrays.asList(7, 1)));
		capacite2Coords.add(new ArrayList<>(Arrays.asList(7, 2)));
		capacite2Coords.add(new ArrayList<>(Arrays.asList(7, 4)));
		capacite2Coords.add(new ArrayList<>(Arrays.asList(9, 4)));
		capacite2Coords.add(new ArrayList<>(Arrays.asList(9, 5)));
		capacite2Coords.add(new ArrayList<>(Arrays.asList(9, 6)));

		// Configurer capacité à 3
		for (ArrayList<Integer> coord : capacite3Coords) {
			Hex hex = trouverHexParCoordonnees(coord.get(0), coord.get(1));
			if (hex != null) {
				hex.setCapacite(3);
			}
		}

		// Configurer capacité à 2
		for (ArrayList<Integer> coord : capacite2Coords) {
			Hex hex = trouverHexParCoordonnees(coord.get(0), coord.get(1));
			if (hex != null) {
				hex.setCapacite(2);
			}
		}

		//initialisation des secteurs
		listeSecteur= new ArrayList<>();
		Secteur secteur1 = new Secteur(creerZonesHex().get(0),"Secteur1");
		listeSecteur.add(secteur1);
		Secteur secteur2 = new Secteur(creerZonesHex().get(1),"Secteur2");
		listeSecteur.add(secteur2);
		Secteur secteur3 = new Secteur(creerZonesHex().get(2),"Secteur3");
		listeSecteur.add(secteur3);
		Secteur secteur4 = new Secteur(creerZonesHex().get(3),"Secteur4");
		listeSecteur.add(secteur4);
		Secteur secteur5 = new Secteur(creerZonesHex().get(4),"Secteur5");
		listeSecteur.add(secteur5);
		Secteur secteur6 = new Secteur(creerZonesHex().get(5),"Secteur6");
		listeSecteur.add(secteur6);
		Secteur secteur7 = new Secteur(creerZonesHex().get(6),"Secteur7");
		listeSecteur.add(secteur7);
		Secteur secteur8 = new Secteur(creerZonesHex().get(7),"Secteur8");
		listeSecteur.add(secteur8);
		Secteur secteur9 = new Secteur(creerZonesHex().get(8),"Secteur9");
		listeSecteur.add(secteur9);

	}

/**
 * Méthode utilitaire pour trouver un Hex par ses coordonnées.
 *
 * @param n Coordonnée n de l'Hex.
 * @param m Coordonnée m de l'Hex.
 * @return Hex correspondant ou null si introuvable.
 */
		private Hex trouverHexParCoordonnees(int n, int m) {
			for (Hex hex : listeHex) {
				if (hex.getCoordonnees().get(0) == n && hex.getCoordonnees().get(1) == m) {
					return hex;
				}
			}
			return null;
		}



	// Getter pour la liste des Hex
	public ArrayList<Hex> getListeHex() {
		return listeHex;
	}
	// Getter pour la liste des Secteur
	public ArrayList<Secteur> getListeSecteur() {
		return listeSecteur;
	}


	public void entretenirVaisseaux() {
		for (Hex hex : this.listeHex) {
			if (hex.getListVaisseaux().size() > hex.getCapacite()) {
				System.out.println("l'hex " + hex.getCoordonnees().get(0) + " " + hex.getCoordonnees().get(1) + " avec dépassement : "
						+ hex.getListVaisseaux().size() + " vaisseaux pour une capacité de " + hex.getCapacite());
				ArrayList<Vaisseau> listeVaisseauSupprimes =new ArrayList<>();
				while (hex.getListVaisseaux().size() > hex.getCapacite()) {
					listeVaisseauSupprimes.add(hex.getListVaisseaux().get(hex.getListVaisseaux().size() - 1));
					hex.getListVaisseaux().remove(hex.getListVaisseaux().size() - 1); // Supprime le dernier vaisseau
				}
				for (Vaisseau vaisseau : listeVaisseauSupprimes){
					hex.getOccupant().getListeVaisseaux().remove(vaisseau);
					vaisseau.setHex(null);
				}
				System.out.println("Dépassement corrigé. Nombre de vaisseaux actuel : " + hex.getListVaisseaux().size());
			}
		}
	}

	/**
	 * Méthode pour imprimer tous les Hex du plateau.
	 */
	public void afficherTousLesHex() {
		for (Hex hex : listeHex) {
			System.out.println(hex);
		}
	}

	/**
	 * Méthode pour imprimer tous les secteurs du plateau.
	 */
	public void afficherTousLesSecteurs() {
		for (Secteur secteur : listeSecteur) {
			System.out.println(secteur);
		}
	}

	public ArrayList<ArrayList<Hex>> creerZonesHex() {
		ArrayList<ArrayList<Hex>> zones = new ArrayList<>();

		// Définition des zones avec leurs coordonnées
		int[][][] zonesCoords = {
				{{1, 1}, {2, 1}, {3, 2}},
				{{1, 3}, {2, 3}, {3, 3}},
				{{1, 5}, {1, 6}, {3, 6}},
				{{4, 1}, {5, 1}, {6, 1}},
				{{4, 3}},
				{{4, 5}, {5, 6}, {6, 5}},
				{{7, 1}, {7, 2}, {8, 1}},
				{{7, 4}, {8, 3}, {9, 4}},
				{{8, 5}, {9, 5}, {9, 6}}
		};

		// Création des ArrayList de Hex pour chaque zone
		for (int[][] zoneCoords : zonesCoords) {
			ArrayList<Hex> zone = new ArrayList<>();
			for (int[] coord : zoneCoords) {
				Hex hex = trouverHexParCoordonnees(coord[0], coord[1]);
				if (hex != null) {
					zone.add(hex);
				}
			}
			zones.add(zone);
		}

		return zones;
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}


}



	


