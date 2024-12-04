package pocketImperium;

import java.util.ArrayList;
import java.util.List;

public class JoueurVirtuel extends Joueur{
	
	public JoueurVirtuel (String nom,String couleur,int ordreDeJeu) {

        super(nom,couleur,ordreDeJeu);
	}

    public void ajouterVaisseau() {
        System.out.println(this.getNom()+"  a ajouté un vaisseau");
    }
    public void déplacer() {
        System.out.println(this.getNom()+"  a déplacer une flotte");
    }
    public void attaquerHex() {
        System.out.println(this.getNom()+" a attaqué un hex");
    }
    public void choisirSecteurEtScore(){
        System.out.println(this.getNom()+"  a choisi un secteur");
    }

    public void ordonnerCarte(){
        ArrayList<Carte> liste1 = new ArrayList<>();
        liste1.add(Carte.Expand);
        liste1.add(Carte.Explore);
        liste1.add(Carte.Exterminate);
        this.setListeDeCarteOrdonnee(liste1);
    }
    public void choisirSecteur(){

    }
    

}
