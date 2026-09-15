public class Gradient {
    public static void main(String[] args) {
        int largeur = 200;
        int hauteur = 100;
        Image img = new Image(largeur, hauteur);

        // Génération du dégradé de bleu
        for (int y = 0; y < hauteur; y++) {
            for (int x = 0; x < largeur; x++) {
                int bleu = 255 * x / (largeur - 1);
                //normalisation = x/largeur-1
                //255.0 * nomralisation = nombre entre 0 et 255 pour le rgb
                img.setPixel(x, y, 0, 0, bleu);
            }
        }

        try {
            img.save_txt("gradient.ppm");
            System.out.println("Dégradé créé avec succès !");
        } catch (Exception e) {
            System.err.println("Erreur lors de la création du dégradé : " + e.getMessage());
        }
    }
}
                