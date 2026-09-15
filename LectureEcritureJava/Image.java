import java.io.FileWriter;
import java.io.FileReader;
import java.io.StreamTokenizer;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Image {
    private int largeur;
    private int hauteur;
    private int[][][] pixels; // pixels y & x & 0=R,1=G,2=B

    public int getLargeur() { return largeur; }
    public int getHauteur() { return hauteur; }

    public Image(int largeur, int hauteur) {
        this.largeur = largeur;
        this.hauteur = hauteur;
        pixels = new int[hauteur][largeur][3];
    }

    public void setPixel(int x, int y, int r, int g, int b) {
        if (x >= 0 && x < largeur && y >= 0 && y < hauteur) {
            pixels[y][x][0] = r;
            pixels[y][x][1] = g;
            pixels[y][x][2] = b;
        }
    }

    public void save_txt(String nomFichier) throws IOException {
        FileWriter writer = new FileWriter(nomFichier);
        writer.write("P3\n");
        writer.write(largeur + " " + hauteur + "\n");
        writer.write("255\n");

        for (int y = 0; y < hauteur; y++) {
            for (int x = 0; x < largeur; x++) {
                writer.write(pixels[y][x][0] + " " +
                              pixels[y][x][1] + " " +
                              pixels[y][x][2] + "   ");
            }
            writer.write("\n");
        }
        writer.close();
    }


    // file.reader lis les caractères
    // Stream tokenizer découpe le flux en token
    // next token avance d'un token
    
    public static Image read_txt(String filename) throws IOException {
        try (FileReader fr = new FileReader(filename)) {
            StreamTokenizer st = new StreamTokenizer(fr);  

            st.wordChars('0','9'); //Pour que P3 reste en un seul mot 
            st.nextToken();

            String magic = st.sval;
            if(!"P3".equals(magic)){
                throw new IOException("Format pas bon on vuet du P3");
            }

            st.nextToken();
            int width = (int) st.nval;
            st.nextToken();
            int height = (int) st.nval;
            st.nextToken();
            int maxVal = (int) st.nval;

            Image img = new Image(width,height);
            for (int y=0; y < height ; y++) {
                for(int x=0; x < width ; x++){
                    st.nextToken();
                    int r = (int) st.nval;
                    st.nextToken();
                    int g = (int) st.nval;
                    st.nextToken();
                    int b = (int) st.nval;
                    img.setPixel(x,y,r,g,b);
                }
            }
            return img;
        }
    }

    // pareil que save_txt mais en binaire, du coup pas d'espaces ni de retours à la ligne pour les pixels
    // on ecrit direct les octets avec FileOutputStream
    public void write_bin(String nomFichier) throws IOException {
        FileOutputStream writer = new FileOutputStream(nomFichier);
        String entete = "P6\n" + largeur + " " + hauteur + "\n255\n";
        writer.write(entete.getBytes()); // l'entete reste en texte seuls les pixels changent

        byte[] donnees = new byte[largeur * hauteur * 3];
        int i = 0;
        for (int y = 0; y < hauteur; y++) {
            for (int x = 0; x < largeur; x++) {
                donnees[i++] = (byte) pixels[y][x][0];
                donnees[i++] = (byte) pixels[y][x][1];
                donnees[i++] = (byte) pixels[y][x][2];
            }
        }
        writer.write(donnees);
        writer.close();
    }

    // meme principe que read_txt mais on peut pas utiliser StreamTokenizer
    // parce qu'il bufferise (stock temporairement les données dans une zone mémoire)
    // et du coup on sait plus ou on s'arrete pile avant les pixels
    // du coup on lit a la main caractere par caractere jusqu'au \n
    public static Image read_bin(String filename) throws IOException {
        try (FileInputStream fr = new FileInputStream(filename)) {

            // magic number P6
            StringBuilder sb = new StringBuilder();
            int c = fr.read();
            while (c != '\n') {
                sb.append((char) c);
                c = fr.read();
            }
            String magic = sb.toString();
            if (!"P6".equals(magic)) {
                throw new IOException("Format pas bon on veut du P6");
            }

            // largeur et hauteur sur la meme ligne separees par un espace
            sb = new StringBuilder();
            c = fr.read();
            while (c != '\n') {
                sb.append((char) c);
                c = fr.read();
            }
            String[] dims = sb.toString().split(" ");
            int width = Integer.parseInt(dims[0]);
            int height = Integer.parseInt(dims[1]);

            // valeur max, on s'en sert pas vraiment mais faut quand meme la lire
            sb = new StringBuilder();
            c = fr.read();
            while (c != '\n') {
                sb.append((char) c);
                c = fr.read();
            }
            int maxVal = Integer.parseInt(sb.toString());

            Image img = new Image(width, height);

            // la on est pile au debut des pixels binaires, on lit tout d'un coup
            byte[] donnees = new byte[width * height * 3];
            int lus = 0;
            while (lus < donnees.length) {
                int n = fr.read(donnees, lus, donnees.length - lus);
                if (n == -1) throw new IOException("Fichier trop court");
                lus += n;
            }

            int i = 0;
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    // & 0xFF sinon ca devient negatif (byte signe en java...)
                    int r = donnees[i++] & 0xFF;
                    int g = donnees[i++] & 0xFF;
                    int b = donnees[i++] & 0xFF;
                    img.setPixel(x, y, r, g, b);
                }
            }
            return img;
        }
    }
}