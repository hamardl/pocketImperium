package pocketImperium;

import java.util.ArrayList;

public class Partie {
	// Attributs de la classe
	private PlateauDeJeu plateauDeJeu;  // Plateau de jeu
	private ArrayList<Joueur> listeJoueur;  // Liste des joueurs
	private Tour tour;

	// Constructeur
	public Partie(PlateauDeJeu plateauDeJeu, ArrayList<Joueur> listeJoueur,Tour tour) {
		this.plateauDeJeu = plateauDeJeu;
		this.listeJoueur = listeJoueur;
		this.tour=tour;
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
		
	}
	
	public void quitterPartie() {
		
	}
	
	public void sauvgarderPartie(){
		
	}
	
	public void creationJoueurs() {
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
