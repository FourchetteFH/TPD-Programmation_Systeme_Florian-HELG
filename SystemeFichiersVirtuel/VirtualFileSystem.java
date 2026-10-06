
public class VirtualFileSystem {

    private MemoryManager memoryManager;

    public VirtualFileSystem() {
        this.memoryManager =
                new MemoryManager();
    }

    private int allocateInode() {

        byte[] memory =
                memoryManager.getFilesystemMemory();

        // TODO:
        // Parcourir les inodes de 0 à MAX_INODES - 1.
        // Identifier le premier inode libre.
        // Retourner son numéro.

        //passe tous les inodes pour trouver un inode vide
        for (int i = 0; i < MemoryManager.MAX_INODES; i++) {
            int offset = MemoryManager.INODE_TABLE_OFFSET + i * MemoryManager.INODE_SIZE;
            
            // le type est à offset + 4, donc type 0 est un inode libre
            if (Utils.readInt(memory, offset + 4) == 0) {
                return i;
            }
        }
        return -1;
    }

    public boolean createFile(
            String directory,
            String filename) {

        int inodeNum = allocateInode();

        if (inodeNum == -1) {
            return false;
        }

        // TODO:
        // Construire l'inode.
        // L'initialiser comme fichier vide.

        //Je me suis aidé de l'IA pour cette partie.
        //
        //De ce que j'ai compris, créer un fichier c'est pas forcément écrire son contenu, 
        //il faut d'abord l'inode pour décrire le fichier et ses caractéristiques. 
        //Un fichier vide existe mais sasn bloc de données. 
        //La méthode cherche un inode libre, la boucle les regarde un par un jusqu'à ce que le premier type vale 1. 
        //Ca marche car la mémoire neuve est remplie de 0 donc au départ tous les inodes sont libres, sinon ça échoue.
        // On remplie l'inode avec des valeurs de fichiers vide donc le type à 1, la taille a 0 pour dire que le ficheir est vide
        // la date de creation modification, pointeurs à 0 parce que aucun bloc n'est alloué vu que le bloc 0 est le superbloc
        //les permissions et 1 lien et writeToMemory écrit tous les champs dans la mémoire dans l'ordre du format de l'inode.

        int type = 1;                                       // 1 = fichier
        int taille = 0;                                     // fichier vide
        long creation = System.currentTimeMillis();
        long modification = creation;                       // pareil à la création pour un fichier neuf
        int[] pointeurs = new int[Inode.DIRECT_POINTERS];   // que des 0 = aucun bloc
        int pointeurIndirect = 0;
        short permissions = 0644;
        int liens = 1;

        Inode inode = new Inode(memoryManager, inodeNum);


        inode.writeToMemory(type, taille, creation, modification,
        pointeurs, pointeurIndirect, permissions, liens);
        return true;
    }

    public MemoryManager getMemoryManager() {
        return memoryManager;
    }
}
