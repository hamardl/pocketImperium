package pocketImperium;

import java.io.Serializable;
import java.util.*;


public abstract class Joueur implements Serializable {
	
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
        
        public abstract void ajouterVaisseau(ArrayList<Hex> listeHex,PlateauDeJeu pdj);
        public abstract void deplacer(ArrayList<Hex> listeHex,ArrayList<Vaisseau> listeVaisseauDeplaces,PlateauDeJeu pdj);
        public abstract void attaquerHex(ArrayList<Hex> listeHex, ArrayList<Vaisseau> listeVaisseauUtilises,PlateauDeJeu pdj);
        public abstract void ordonnerCarte();
        public abstract Secteur choisirSecteur(ArrayList<Secteur> listeSecteurs);
        public abstract Hex choisirHexDepart(ArrayList<Secteur> secteursDejaChoisis, ArrayList<Hex> listeHex);

    public void resoudreConflit(Hex hex) {
        // Map pour compter les vaisseaux par joueur
        Map<Joueur, Integer> vaisseauxParJoueur = new HashMap<>();

        // Parcourir la liste des vaisseaux sur cet hex
        for (Vaisseau vaisseau : hex.getListVaisseaux()) {
            Joueur joueur = vaisseau.getJoueur(); // Obtenir le joueur propriétaire
            vaisseauxParJoueur.put(joueur, vaisseauxParJoueur.getOrDefault(joueur, 0) + 1);
        }

        // Vérifier si au moins 2 joueurs ont des vaisseaux
        if (vaisseauxParJoueur.size() < 2) {
            System.out.println("Aucun conflit : un seul joueur possède des vaisseaux sur cet hex.");
            return;
        }

        // Trouver le nombre minimum de vaisseaux parmi les joueurs présents
        int nombreARetirer = vaisseauxParJoueur.values().stream().min(Integer::compareTo).orElse(0);

        if (nombreARetirer > 0) {
            System.out.println("Chaque joueur perd " + nombreARetirer + " vaisseaux à cause du conflit.");

            // Parcourir les joueurs et retirer les vaisseaux
            for (Map.Entry<Joueur, Integer> entry : vaisseauxParJoueur.entrySet()) {
                Joueur joueur = entry.getKey();
                int vaisseauxRestantsARetirer = nombreARetirer;

                // Parcourir les vaisseaux de l'hex pour ce joueur et les retirer
                Iterator<Vaisseau> iterator = hex.getListVaisseaux().iterator();
                while (iterator.hasNext() && vaisseauxRestantsARetirer > 0) {
                    Vaisseau vaisseau = iterator.next();
                    if (vaisseau.getJoueur().equals(joueur)) {
                        iterator.remove(); // Retirer le vaisseau de l'hex
                        joueur.getListeVaisseaux().remove(vaisseau); // Retirer le vaisseau de la liste du joueur
                        vaisseau.setHex(null);
                        vaisseauxRestantsARetirer--;
                    }
                }
            }
        } else {
            System.out.println("Pas de conflit à résoudre (aucun vaisseau à retirer).");
        }
    }


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
                System.out.println("\n"+this.getNom()+" joue Expand .");
                for (int i = 0; i < 3 - nbJoueurJoueCarte+1; i++) {
                    this.ajouterVaisseau(pdj.getListeHex(),pdj); // Ajouter un vaisseau
                }
            } else if (carte.equals(Carte.Explore)) {
                System.out.println("\n"+this.getNom()+" joue Explore .");
                ArrayList<Vaisseau> vaisseauxDeplaces = new ArrayList<>(); // Liste des vaisseaux déplacés
                for (int i = 0; i < 3 - nbJoueurJoueCarte+1; i++) {
                    this.deplacer(pdj.getListeHex(),vaisseauxDeplaces,pdj); // Déplacer un vaisseau
                }
                vaisseauxDeplaces=null;
            } else if (carte.equals(Carte.Exterminate)) {
                System.out.println("\n"+this.getNom()+" joue Exterminate .");
                ArrayList<Vaisseau> vaisseauxUtilises = new ArrayList<>(); // Liste des vaisseaux déplacés
                for (int i = 0; i < 3 - nbJoueurJoueCarte+1; i++) {
                    this.attaquerHex(pdj.getListeHex(),vaisseauxUtilises,pdj); // Attaquer un Hex
                }
                vaisseauxUtilises=null;
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



	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
