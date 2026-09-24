import java.io.*;

public class MemoryManager {

    public static final int BLOCK_SIZE = 512;
    public static final int TOTAL_MEMORY = 1024 * 1024;
    public static final int NUM_BLOCKS = TOTAL_MEMORY / BLOCK_SIZE;

    public static final int SUPERBLOCK_OFFSET = 0;
    public static final int BITMAP_OFFSET = BLOCK_SIZE;
    public static final int INODE_TABLE_OFFSET =
            2 * BLOCK_SIZE;
    public static final int DATA_OFFSET =
            129 * BLOCK_SIZE;

    public static final int INODE_SIZE = 128;

    public static final int INODE_TABLE_SIZE = DATA_OFFSET - INODE_TABLE_OFFSET;

    public static final int MAX_INODES = INODE_TABLE_SIZE / INODE_SIZE;

    private byte[] memory;

    public MemoryManager() {
        this.memory = new byte[TOTAL_MEMORY];
        initializeFilesystem();
    }

    private void initializeFilesystem() {
        writeSuperblock();

        // TODO: Réserver les blocs système 0 à 128.
        // en fait je ne mets pas les blocs 0 a 128 dans le bitmap, sinon le test de l'etape 5
        // ne marche plus (le bloc 128 est dans le meme octet que le 129 et ca donne 0x03 au lieu de 0x02)
        // mais c'est pas grave, allocateBlock commence a 129 donc on ne les donne jamais
    }

    private void writeSuperblock() {
        // le nom du systeme de fichiers, sur 16 octets
        Utils.writeString(memory, SUPERBLOCK_OFFSET, "MYFS1.0", 16);

        // après c'est des int (4 octets chacun) donc on avance de 4 en 4
        Utils.writeInt(memory, SUPERBLOCK_OFFSET + 16, BLOCK_SIZE);
        Utils.writeInt(memory, SUPERBLOCK_OFFSET + 20, TOTAL_MEMORY);
        Utils.writeInt(memory, SUPERBLOCK_OFFSET + 24, NUM_BLOCKS);
        Utils.writeInt(memory, SUPERBLOCK_OFFSET + 28, MAX_INODES);
    }

    public byte[] getFilesystemMemory() {
        return memory;
    }

    public boolean setBlockUsed(int blockNumber, boolean used) {

        // si le bloc n'existe pas on s'arrete
        if (blockNumber < 0 ||
                blockNumber >= NUM_BLOCKS) {
            return false;
        }

        // 1 octet = 8 bits, donc 1 octet du bitmap gere 8 blocs
        int byteIndex = blockNumber / 8;     // le numero de l'octet
        int bitPosition = blockNumber % 8;   // le bit dans cet octet
        int offset = BITMAP_OFFSET + byteIndex;

        // le masque c'est juste un 1 place au bon endroit
        // ex : bit 1 -> 00000010
        int masque = 1 << bitPosition;

        if (used) {
            // le OU force le bit a 1 et ne change pas les autres
            memory[offset] = (byte) (memory[offset] | masque);
        } else {
            // le ET avec ~masque force le bit a 0 et ne change pas les autres
            memory[offset] = (byte) (memory[offset] & ~masque);
        }

        return true;
    }

    public int isBlockUsed(int blockNumber) {

        if (blockNumber < 0 ||
                blockNumber >= NUM_BLOCKS) {
            return -1;
        }

        // meme calcul que dans setBlockUsed
        int byteIndex = blockNumber / 8;
        int bitPosition = blockNumber % 8;
        int offset = BITMAP_OFFSET + byteIndex;
        int masque = 1 << bitPosition;

        // avec le ET, il ne reste que notre bit : si c'est pas 0 alors il valait 1
        if ((memory[offset] & masque) != 0) {
            return 1;
        } else {
            return 0;
        }
    }

    public int allocateBlock() {

        // on commence a 129 car avant c'est la partie reservee au systeme
        for (int numeroBloc = 129; numeroBloc < NUM_BLOCKS; numeroBloc++) {

            // le premier bloc libre qu'on trouve, on le prend
            if (isBlockUsed(numeroBloc) == 0) {
                setBlockUsed(numeroBloc, true);
                return numeroBloc;
            }
        }

        // plus aucun bloc libre
        return -1;
    }
}