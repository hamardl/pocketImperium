package pocketImperium;
import javax.swing.*;

import java.util.ArrayList;

public class PlateauDeJeu {
	/*
	private ArrayList<Hex> listeHex;         // Liste des Hex sur le plateau
	private ArrayList<Secteur> listeSecteur; // Liste des Vaisseaux sur le plateau

	// Constructeur
	public PlateauDeJeu(ArrayList<Hex> listeHex,ArrayList<Secteur> listeSecetur) {
		this.listeHex = listeHex;
		this.listeSecteur=listeSecetur;

		// Initialisation des 50 Hex

	}
	*/
	public PlateauDeJeu() {

	}
/*
	// Getter pour la liste des Hex
	public ArrayList<Hex> getListeHex() {
		return listeHex;
	}


	// Afficher l'état du plateau
	public void afficherPlateau() {
		System.out.println("=== État du Plateau ===");
		System.out.println("Hexagones :");
		for (Hex hex : listeHex) {
			System.out.println("- " + hex);
		}

		}

*/
	/*
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
*/
public void entretenirVaisseaux(){
	System.out.println("entretien des vaisseaux");
}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}



	


