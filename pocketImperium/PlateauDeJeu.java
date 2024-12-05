package pocketImperium;
import javax.swing.*;

import java.util.ArrayList;

public class PlateauDeJeu {

	private ArrayList<Hex> listeHex;         // Liste des Hex sur le plateau
	private ArrayList<Secteur> listeSecteur; // Liste des Vaisseaux sur le plateau

	// Constructeur
	public PlateauDeJeu() {
		ArrayList<Hex> listeHex = new ArrayList<>();
		//on initialise les hex
		for (int j = 1; j <= 9; j++) {
			if (j % 2 != 0) {
				int k = 6;
				for (int i = 1; i <= k; i++) {
					ArrayList<Integer> list0 = new ArrayList<>();
					list0.add(j);//numéro ligne
					list0.add(i);//numéro collone
					Hex hex = new Hex(list0);
					listeHex.add(hex);
				}
			} else {
				int k = 5;
				for (int i = 1; i <= k; i++) {
					ArrayList<Integer> list0 = new ArrayList<>();
					list0.add(j);
					list0.add(i);
					Hex hex = new Hex(list0);
					listeHex.add(hex);
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

			// Ajouter les voisins au Hex actuel
			hex.setListeHexesVoisins(voisins);
		}
		//on initialise triPtim

			this.listeHex = listeHex;
			this.listeSecteur = listeSecteur;
		}
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
				System.out.println("Hex avec dépassement : "
						+ hex.getListVaisseaux().size() + " vaisseaux pour une capacité de " + hex.getCapacite());
				hex.retirerVaisseauxEnTrop();
				System.out.println("Dépassement corrigé. Nombre de vaisseaux actuel : " + hex.getListVaisseaux().size());
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}



	


