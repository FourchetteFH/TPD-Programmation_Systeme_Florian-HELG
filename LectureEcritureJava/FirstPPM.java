import java.io.FileWriter;
import java.io.IOException;

public class FirstPPM {
    public static void main(String[] args) {
        try {
            FileWriter writer = new FileWriter("FirstPPM2.ppm");

            writer.write("P3\n");
            writer.write("3 2\n");
            writer.write("255\n");

            writer.write("255 0 0   ");
            writer.write("0 255 0   ");
            writer.write("0 0 255\n");

            writer.write("255 255 0   ");
            writer.write("255 255 255   ");
            writer.write("0 0 0\n");
                    
			writer.close(); // Fermeture du fichier
            System.out.println("Image PPM cr��e avec succ�s !");
        } catch (IOException e) {
            System.err.println("Erreur lors de l'�criture du fichier : " + e.getMessage());
        }
    }
}