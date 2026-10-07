
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
        //
        //un fichier vide n'a besoin que d'un inode, type 1 (fichier), taille 0, aucun bloc de données,pointeurs à 0, car 0 = aucun bloc.
        // date creation modification identique pour un nouveau fichier 

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

    public boolean writeFile(
        int inodeNum,
        byte[] data) {

        int blocksNeeded =
                (data.length
                + MemoryManager.BLOCK_SIZE - 1)
                / MemoryManager.BLOCK_SIZE;

        if (blocksNeeded > Inode.DIRECT_POINTERS) {
            return false;
        }

        int[] blockPointers =
                new int[Inode.DIRECT_POINTERS];

        // TODO:
        // Allouer blocksNeeded blocs.

        for (int i = 0; i < blocksNeeded; i++) {
            int bloc = memoryManager.allocateBlock();

            if (bloc == -1 ){
                for (int j = 0; j < i; j++) {
                    memoryManager.setBlockUsed(blockPointers[j], false);
                }
                return false;
            }
            blockPointers[i] = bloc;
            
        }

        byte[] memory =
                memoryManager.getFilesystemMemory();

        int bytesRemaining =
                data.length;

        int dataSrcOffset = 0;

        for (int i = 0; i < blocksNeeded; i++) {
            int copies = Math.min(bytesRemaining, MemoryManager.BLOCK_SIZE); //peut etre moins que 512
            int offsetBloc = blockPointers[i] * MemoryManager.BLOCK_SIZE;
            for (int p = 0; p < copies ; p ++ ) {
                memory[offsetBloc + p] = data[dataSrcOffset + p];
            }
        
        dataSrcOffset += copies;
        bytesRemaining -= copies;
        }


        // TODO:
        // Mettre à jour l'inode.

        Inode inode = new Inode(memoryManager, inodeNum);
        long creation = Utils.readLong(memory, inode.getInodeOffset() + 12);
        long modification = System.currentTimeMillis(); //heure actuelle en ms

        inode.writeToMemory(1, data.length, creation, modification, blockPointers, 0, (short)0644, 1);

        return true;
    }


    public byte[] readFile(int inodeNum) {

        Inode inode =
                new Inode(memoryManager, inodeNum);

        int fileSize =
                inode.getFileSize();

        if (fileSize == 0) {
            return new byte[0];
        }

        byte[] fileData =
                new byte[fileSize];

        byte[] memory =
                memoryManager.getFilesystemMemory();

        int[] blockPointers =
                inode.getDirectPointers();

        int bytesRemaining = fileSize;
        int destOffset = 0;

        for (int i = 0; i < Inode.DIRECT_POINTERS && bytesRemaining > 0; i++) {
            int copies = Math.min(bytesRemaining, MemoryManager.BLOCK_SIZE);
            int offsetBloc = blockPointers[i] * MemoryManager.BLOCK_SIZE;

            for (int p = 0; p < copies; p++) {
                fileData[destOffset + p] = memory[offsetBloc + p];
            }

            destOffset = destOffset + copies;
            bytesRemaining = bytesRemaining - copies;

        }
        // TODO:
        // Parcourir les blocs utilisés.
        // Copier chaque fragment vers fileData.

        return fileData;
    }
}
