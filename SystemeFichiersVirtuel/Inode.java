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
            pointers[i] = Utils.readInt(memory, position);
            position = position + 4;
        }

        return pointers;
    }


   //Etape 7
   public void writeToMemory(
        int fileType,
        int fileSize,
        long creationTime,
        long modificationTime,
        int[] directPointers,
        int indirectPointer,
        short permissions,
        int linkCount) {

        byte[] memory =
                memoryManager.getFilesystemMemory();

        int offset = getInodeOffset();

        // TODO:
        // 1. Numéro d'inode
        offset = offset + Utils.writeInt(memory, offset, inodeNumber);
        // 2. Type -> offset de 4
        offset = offset + Utils.writeInt(memory, offset, fileType);
        // 3. Taille -> offset de 8
        offset = offset + Utils.writeInt(memory, offset, fileSize);
        // 4. Création -> offset de 12
        offset = offset + Utils.writeLong(memory, offset, creationTime);
        // 5. Modification -> offset de 20
        offset = offset + Utils.writeLong(memory, offset, modificationTime);
        // 6. 10 pointeurs directs -> offeset de 28
        for (int i = 0; i < DIRECT_POINTERS; i++) {
            offset = offset + Utils.writeInt(memory, offset, directPointers[i]);
        }
        // 7. Pointeur indirect -> offset de 68
        offset = offset + Utils.writeInt(memory, offset, indirectPointer);
        // 8. Permissions -> offset de 72
        offset = offset + Utils.writeShort(memory, offset, permissions);
        // 9. Nombre de liens -> offset de 74
        offset = offset + Utils.writeInt(memory, offset, linkCount);
    }
}