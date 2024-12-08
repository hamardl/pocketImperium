package pocketImperium;

import java.util.ArrayList;
import java.util.List;


public abstract class Joueur {
	
    private String nom;
    protected String couleur;
    protected int score;
    private Carte derniereCarteRevelee;
    private List<Vaisseau> listeVaisseaux;
    private ArrayList<Carte> listeDeCarteOrdonnee;
    private int ordreDeJeu;
    private ArrayList<Joueur> listeJoueur;
	
    // Constructeur avec paramètres
    public Joueur(String nom, String couleur,int ordreDeJeu) {
        this.nom = nom;
        this.couleur = couleur;
        this.score = 0;
        this.derniereCarteRevelee = null;
        this.listeVaisseaux = new ArrayList<>();
        this.listeDeCarteOrdonnee = new ArrayList<>();
        this.ordreDeJeu = ordreDeJeu;

    }

        // Getters et Setters
        public String getNom() {
            return nom;
        }

        public void setNom(String nom) {
            this.nom = nom;
        }

        public String getCouleur() {
            return couleur;
        }

        public void setCouleur(String couleur) {
            this.couleur = couleur;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public Carte getDerniereCarteRevelee() {
            return derniereCarteRevelee;
        }

        public void setDerniereCarteRevelee(Carte derniereCarteRevelee) {
            this.derniereCarteRevelee = derniereCarteRevelee;
        }

        public List<Vaisseau> getListeVaisseaux() {
            return listeVaisseaux;
        }

        public void setListeVaisseaux(List<Vaisseau> listeVaisseaux) {
            this.listeVaisseaux = listeVaisseaux;
        }

        public ArrayList<Carte> getListeDeCarteOrdonnee() {
            return listeDeCarteOrdonnee;
        }

        public void setListeDeCarteOrdonnee(ArrayList<Carte> listeDeCarteOrdonnee) {
            this.listeDeCarteOrdonnee = listeDeCarteOrdonnee;
        }

        public int getOrdreDeJeu() {
            return ordreDeJeu;
        }

        public void setOrdreDeJeu(int ordreDeJeu) {
            this.ordreDeJeu = ordreDeJeu;
        }

    // Getter pour listeJoueur
    public ArrayList<Joueur> getListeJoueur() {
        return listeJoueur;
    }

    // Setter pour listeJoueur
    public void setListeJoueur(ArrayList<Joueur> listeJoueur) {
        this.listeJoueur = listeJoueur;
    }
        
        public abstract void ajouterVaisseau(ArrayList<Hex> listeHex);
        public abstract void déplacer();
        public abstract void attaquerHex();
        public abstract void ordonnerCarte();
        public abstract Secteur choisirSecteur(ArrayList<Secteur> listeSecteurs);
        
    	public void revelerCarte(ArrayList<Carte> listeDeCarteOrdonnee) {

    	    this.setDerniereCarteRevelee(listeDeCarteOrdonnee.get(0));
            listeDeCarteOrdonnee.set(0, listeDeCarteOrdonnee.get(1));
            listeDeCarteOrdonnee.set(1, listeDeCarteOrdonnee.get(2));
            listeDeCarteOrdonnee.set(2, null);
    		
    	}
    	
    	//on compare la carte du joueur avec nos trois carte et après on fais les actions demandé
        public void jouerCarte(PlateauDeJeu pdj) {
            // Calculer le nombre de joueurs ayant joué la même carte
            Carte carte=derniereCarteRevelee;
            int nbJoueurJoueCarte = compterJoueursAvecCarte(carte, listeJoueur);

            // Selon la carte jouée, effectuer les actions
            if (carte.equals(Carte.Expand)) {
                for (int i = 0; i < 3 - nbJoueurJoueCarte+1; i++) {
                    this.ajouterVaisseau(pdj.getListeHex()); // Ajouter un vaisseau
                }
            } else if (carte.equals(Carte.Explore)) {
                for (int i = 0; i < 3 - nbJoueurJoueCarte+1; i++) {
                    this.déplacer(); // Déplacer un vaisseau
                }
            } else if (carte.equals(Carte.Exterminate)) {
                for (int i = 0; i < 3 - nbJoueurJoueCarte+1; i++) {
                    this.attaquerHex(); // Attaquer un Hex
                }
            }
        }

    // Méthode pour compter le nombre de joueurs ayant joué la même carte
    private int compterJoueursAvecCarte(Carte carte, ArrayList<Joueur> listeJoueur) {
        int nbJoueurJoueCarte = 0;
        for (int i = 0; i < listeJoueur.size(); i++) {
            if (listeJoueur.get(i).getDerniereCarteRevelee().equals(carte)) {
                nbJoueurJoueCarte++;
            }
        }
        return nbJoueurJoueCarte;
    }



    //compter le nombre de vaissezau à chaque fin de tour si =0 alors partie finie
    public void compterVaisseaux() {

    }


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
