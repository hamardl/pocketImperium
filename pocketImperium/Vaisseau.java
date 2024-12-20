package pocketImperium;

public class Vaisseau {

	private Joueur joueur;
	private int numero;
	private Hex hex;

	public Joueur getJoueur() {
		return joueur;
	}
	public int getNumero() {
		return numero;
	}

	public Hex getHex() {
		return hex;
	}
	public void setHex(Hex hex){
		this.hex=hex;
	}

	public Vaisseau(Joueur joueur, int numero){
		this.joueur=joueur;
		this.numero=numero;
		this.hex=null;
	}

	public String toString() {
		String coordonneesHex = (hex != null) ? "(" + hex.getCoordonnees().get(0) + ", " + hex.getCoordonnees().get(1) + ")" : "Non assigné";
		return "Vaisseau{" +
				"Joueur='" + (joueur != null ? joueur.getNom() : "Aucun") + '\'' +
				", Numéro=" + numero +
				", Position=" + coordonneesHex +
				'}';
	}



}
