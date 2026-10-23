public class TestSemaphore {

    public static void main(String[] args) throws InterruptedException {
        
        // Scénario équilibré : 2 producteurs, 3 consommateurs
        ProducteurConsommateur pc1 = new ProducteurConsommateur();

        Thread[] threads1 = new Thread[5];
        threads1[0] = new Thread(pc1.createProducteur("Prod1", 15));
        threads1[1] = new Thread(pc1.createProducteur("Prod2", 15));
        threads1[2] = new Thread(pc1.createConsommateur("Cons1", 10));
        threads1[3] = new Thread(pc1.createConsommateur("Cons2", 10));
        threads1[4] = new Thread(pc1.createConsommateur("Cons3", 10));


        for (Thread t : threads1) {
            t.start();
        }


        for (Thread t : threads1) {
            t.join();
        }


        System.out.println("Équilibré : " + pc1.totalProduits 
                            + " produits, " + pc1.totalConsommes + " consommé");





        // Scénario déséquilibré : 5 producteurs, 2 consommateurs
        ProducteurConsommateur pc2 = new ProducteurConsommateur();

        for (int i = 1; i <= 5; i++) {
            new Thread(pc2.createProducteur("P" + i, 10)).start();
        }

        for (int i = 1; i <= 2; i++) {
            new Thread(pc2.createConsommateur("C" + i, 10)).start();
        }

        Thread.sleep(1000);
        System.out.println("Déséquilibré : " + pc2.totalProduits 
                            + " produits, " + pc2.totalConsommes + " consommés");
        
        
        System.exit(0);
    }
}