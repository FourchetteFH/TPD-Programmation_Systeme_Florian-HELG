/*
 * ProducteurConsommateur.java
 * IUT de Rodez, pas de copyright (ni de "copyleft")
 */

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class ProducteurConsommateur {

    private static final int TAILLE_BUFFER = 5;
    private final Queue<Integer> buffer = new LinkedList<>();

    // Étape 1 Déclaration des sémaphores
    private final Semaphore placesLibres = new Semaphore(TAILLE_BUFFER);  // 5 au début
    private final Semaphore elementsDisponibles = new Semaphore(0);       // 0 au début
    private final Semaphore mutexBuffer = new Semaphore(1);               // 1 (mutex binaire)

    int totalProduits = 0;
    int totalConsommes = 0;

    private int genererProduit() {
        return (int) (Math.random() * 100) + 1;
    }



    class Producteur implements Runnable {

        private final String nom;
        private final int nbProduits;

        public Producteur(String nom, int nbProduits) {
            this.nom = nom;
            this.nbProduits = nbProduits;
        }


        public void run() {
            for (int i = 0; i < nbProduits; i++) {

                try {
                    int produit = genererProduit();

                    // Attendre une place libre
                    placesLibres.acquire();

                    // Acquérir l'accès exclusif au buffer
                    mutexBuffer.acquire();

                    // Ajouter l'élément au buffer
                    buffer.add(produit);
                    totalProduits++;
                    System.out.println("[" + nom + "] Produit : " + produit + " | Taille buffer : " + buffer.size());

                    // Libérer l'accès au buffer
                    mutexBuffer.release();

                    // Signaler qu'un élément est disponible
                    elementsDisponibles.release();

                    Thread.sleep(100);


                } catch (InterruptedException e) {
                    System.err.println(nom + " interrompu : " + e.getMessage());
                }
            }
        }
    }


    class Consommateur implements Runnable {
        private final String nom;
        private final int nbConsommations;

        public Consommateur(String nom, int nbConsommations) {
            this.nom = nom;
            this.nbConsommations = nbConsommations;
        }

        public void run() {

            // nombre de conso passé en paramètre pour que chaque conso en prenne autant qu'on lui dise
            for (int i = 0; i < nbConsommations; i++) {
                
                try {
                    // Attendre qu'un élément soit disponible
                    elementsDisponibles.acquire();

                    // Acquérir l'accès exclusif au buffer
                    mutexBuffer.acquire();

                    // Retirer un élément du buffer
                    Integer produit = buffer.poll();
                    totalConsommes++;
                    System.out.println("  [" + nom + "] Consommé : " + produit + " | Taille buffer : " + buffer.size());

                    // Libérer l'accès au buffer
                    mutexBuffer.release();

                    // Signaler qu'une place est libre
                    placesLibres.release();

                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    System.err.println(nom + " interrompu : " + e.getMessage());
                }
            }
        }
    }

    public Producteur createProducteur(String nom, int nbProduits) {
        return new Producteur(nom, nbProduits);
    }

    public Consommateur createConsommateur(String nom, int nbConsommations) {
        return new Consommateur(nom, nbConsommations);
    }
}