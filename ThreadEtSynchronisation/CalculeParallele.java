public class CalculeParallele {
    private static final int[] DONNEES = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private static final int MULTIPLICATEUR = 1000;

    public static void main(String[] args) {
        long debut = System.nanoTime();
        long sommeTotal = 0;

        Calcule[] threads = new Calcule[DONNEES.length];

        for (int i = 0; i < DONNEES.length; i++) {
            threads[i] = new Calcule(DONNEES[i],MULTIPLICATEUR,i);
            threads[i].start();
        }

        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();
            } catch (Exception e) {
            }
            sommeTotal += threads[i].getResultat();
        }

        long duree = System.nanoTime() - debut;
        System.out.println("Durée" + duree);
        System.out.println("Somme Totale" + sommeTotal);
    }
}