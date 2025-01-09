package pocketImperium;

import javax.swing.*;
import java.awt.*;

public class Vue {

    /**
     * Affiche une fenêtre contenant une image spécifiée par son chemin.
     *
     * @param cheminImage Chemin vers l'image à afficher.
     */
    public static void afficherImage(String cheminImage) {
        // Créer une fenêtre (JFrame)
        JFrame frame = new JFrame("Plateau De Jeu Initial");

        // Configurer l'action de fermeture de la fenêtre
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Charger l'image à partir du chemin spécifié
        ImageIcon imageIcon = new ImageIcon(cheminImage);

        // Vérifier si l'image a été correctement chargée
        if (imageIcon.getIconWidth() == -1 || imageIcon.getIconHeight() == -1) {
            JOptionPane.showMessageDialog(frame, "Image introuvable : " + cheminImage, "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Ajouter l'image à un composant JLabel
        JLabel label = new JLabel(imageIcon);

        // Ajouter le JLabel au contenu de la fenêtre
        frame.getContentPane().add(label, BorderLayout.CENTER);

        // Ajuster la taille de la fenêtre à celle de l'image
        frame.pack();

        // Positionner la fenêtre au centre de l'écran
        frame.setLocationRelativeTo(null);

        // Rendre la fenêtre visible
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Exemple d'utilisation : afficher une image

    }
}

