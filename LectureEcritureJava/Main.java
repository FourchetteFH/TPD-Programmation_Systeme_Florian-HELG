public class Main {
    public static void main(String[] args) throws Exception {

        Image image = new Image(4, 2);
        image.setPixel(0, 0, 255, 0, 0);
        image.setPixel(1, 0, 0, 255, 0);
        image.setPixel(2, 0, 0, 0, 255);
        image.setPixel(3, 0, 255, 255, 0);
        image.setPixel(0, 1, 128, 128, 128);
        image.setPixel(1, 1, 255, 255, 255);
        image.setPixel(2, 1, 0, 0, 0);
        image.setPixel(3, 1, 100, 50, 200);

        image.write_bin("test_bin.ppm");
        Image image2 = Image.read_bin("test_bin.ppm");

        System.out.println("Largeur: " + image2.getLargeur());
        System.out.println("Hauteur: " + image2.getHauteur());
    }
}