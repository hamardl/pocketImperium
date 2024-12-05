package pocketImperium;
import javax.swing.*;

import java.util.ArrayList;

public class PlateauDeJeu {

	private ArrayList<Hex> listeHex;         // Liste des Hex sur le plateau
	private ArrayList<Secteur> listeSecteur; // Liste des Vaisseaux sur le plateau

	// Constructeur
	public PlateauDeJeu() {
		ArrayList<Hex> listeHex= new ArrayList<>();
		//on initialise les hex
		for (int j = 1; j <= 9; j++){
			if (j%2!=0){
				int k=6;
				for (int i = 1; i <= k; i++) {
					ArrayList<Integer> list0=new ArrayList<>();
					list0.add(i);
					list0.add(j);
					Hex hex = new Hex(list0);
					listeHex.add(hex);
				}
			}
			else{
				int k=5;
				for (int i = 1; i <= k; i++) {
					ArrayList<Integer> list0=new ArrayList<>();
					list0.add(i);
					list0.add(j);
					Hex hex = new Hex(list0);
				}
			}
		}
		//on initialise leur liste de Hex voisins
		//for (Hex hex : this.listeHex) {
		//	if ()
		//}

		this.listeHex = listeHex;
		this.listeSecteur=listeSecteur;
	}



	// Getter pour la liste des Hex
	public ArrayList<Hex> getListeHex() {
		return listeHex;
	}
	// Getter pour la liste des Secteur
	public ArrayList<Secteur> getListeSecteur() {
		return listeSecteur;
	}


	// Afficher l'état du plateau
	public void afficherPlateau() {

		}
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



	


