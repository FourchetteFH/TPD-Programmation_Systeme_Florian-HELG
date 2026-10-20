public class TestCompteur {
    public static void main(String[] args) {
        final int NOMBRE_TEST = 50;
        int nbCorrects = 0;
        int min = 0;
        int max = 0;

        for (int test = 0; test < NOMBRE_TEST; test++) {
            CompteurDangereux.resetCompteur();
            Thread[] threads = new Thread[9];
            System.out.println("Test " + test);
            for (int i = 0; i < 9 ;i ++) {
                threads[i] = new Thread(new CompteurDangereux.Incrementeur("test : " + i,11111111));
                threads[i].start();
                
            }
            for (int i = 0; i < 9 ;i ++) {
                try {
                    threads[i].join();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            
            int resultat= CompteurDangereux.getCompteur();
            System.out.println(resultat + " attendu : " + 9*11111111);

            if (resultat == 9*11111111) {
                nbCorrects++;
            }

            if (resultat < min) {
                min = resultat;
            }


            if (resultat > max) {
                max = resultat;
            }

            System.out.println("correct : " + nbCorrects + "/" + NOMBRE_TEST);
            System.out.println("correct : " + (nbCorrects * 100/NOMBRE_TEST));
            System.out.println("min : " + min);
            System.out.println("max : " + max);
        }
    }
}