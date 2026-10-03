public class Inode {

    private MemoryManager memoryManager;
    private int inodeNumber;

    public static final int INODE_SIZE = 128;
    public static final int DIRECT_POINTERS = 10;

    public Inode(
            MemoryManager memoryManager,
            int inodeNumber) {

        this.memoryManager = memoryManager;
        this.inodeNumber = inodeNumber;
    }

    public int getInodeOffset() {
        // TODO:
        // Calculer l'offset exact de l'inode.

        //il faut sauter le début de la table et les inodes précédent
        return MemoryManager.INODE_TABLE_OFFSET + inodeNumber * INODE_SIZE;

        return 0;
    }

    public int getFileType() {
        // TODO:
        // Lire le type à offset + 4.
        
        byte[] memory = memoryManager.getFilesystemMemory();
        int debut = getInodeOffset();

        return Utils.readInt(memory, debut + 4);
    }

    public int getFileSize() {
        // TODO:
        // Lire la taille à offset + 8.

        byte[] memory = memoryManager.getFilesystemMemory();
        int debut = getInodeOffset();

        return Utils.readInt(memory, debut + 8);
    }

    public int[] getDirectPointers() {

        byte[] memory =
                memoryManager.getFilesystemMemory();

        int[] pointers =
                new int[DIRECT_POINTERS];

        // TODO:
        // Lire les 10 pointeurs directs.


        //les 10 pointeurs commencent 28 octets après le début selon votre tableau, les pointeurs sont des int donc ça fait 4 octets
        int position = getInodeOffset() + 28;

        for (int i = 0; i < DIRECT_POINTERS; i++) {
            pointeurs[i] = Utils.readInt(memory, position);
            position = position + 4;
        }

        return pointers;
    }
}