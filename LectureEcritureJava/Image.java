import java.io.FileWriter;
import java.io.IOException;

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
    static public read_txt(String filename) throws IOException {
        try (BufferedReader fr = new FileReader(filename.txt)) {
            StreamTokenizer st = new StreamTokenizer(fr);  

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
}