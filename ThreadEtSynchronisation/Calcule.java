public class Calcule extends Thread {
    // TODO: Déclarer les attributs nécessaires
    private int valeur;
    private int multiplicateur;
    private int index;
    private int resultat;

    public Calcule (int valeur, int multiplicateur, int index) {
        this.valeur = valeur;
        this.multiplicateur = multiplicateur;
        this.index = index;
    }

    @Override
    public void run() {

        int resultat = 0;

        for (int j = 0; j < multiplicateur; j++) {
            resultat += valeur * valeur + valeur;
                            // on pourrais aussi mettre un sleep
        }

        // System.out.println("Traitement de " + valeur + " terminé : " + resultat);
        
    }

    public int getResultat() {
        return resultat;
    }


}