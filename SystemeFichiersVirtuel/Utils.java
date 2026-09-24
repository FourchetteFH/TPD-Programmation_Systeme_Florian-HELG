public class Utils {
    // TODO: Écrire les 4 octets de 'value' dans 'memory'
    // à partir de 'offset', en big-endian.
    public static int writeInt(byte[] memory, int offset, int value) {
        memory[offset]     = (byte) (value >> 24);
        memory[offset + 1] = (byte) (value >> 16);
        memory[offset + 2] = (byte) (value >> 8);
        memory[offset + 3] = (byte) value;
        return 4;
    }



    // TODO: Reconstituer le int sur 4 octets.
    public static int readInt(byte[] memory, int offset) {
        return ((memory[offset]     & 0xFF) << 24)
                | ((memory[offset + 1] & 0xFF) << 16)
                | ((memory[offset + 2] & 0xFF) << 8)
                |  (memory[offset + 3] & 0xFF);
    }


    // TODO: Écrire les 2 octets de 'value'.
    public static int writeShort(byte[] memory, int offset, short value) {
        memory[offset]     = (byte) (value >> 8);
        memory[offset + 1] = (byte) value;
        return 2;
    }



    // TODO: Lire le short sur 2 octets.
    public static short readShort(byte[] memory, int offset) {
        return (short) (((memory[offset] & 0xFF) << 8)
                |  (memory[offset + 1] & 0xFF));
    }


    // TODO: Écrire les 8 octets du long en big-endian.
    public static int writeLong(byte[] memory, int offset, long value) {
        for (int i = 0; i < 8; i++) {
            memory[offset + i] = (byte) (value >> (56 - 8 * i));
        }
        return 8;
    }


    // TODO: Reconstituer le long.
    public static long readLong(byte[] memory, int offset) {
        long resultat = 0;
        for (int i = 0; i < 8; i++) {
            resultat = (resultat << 8) | (memory[offset + i] & 0xFFL);
        }
        return resultat;
    }




    // TODO:
    // 1. Convertir la chaîne en octets.
    // 2. Copier les octets sans dépasser maxLength.
    // 3. Nettoyer le reste de la zone avec des zéros.
    public static int writeString(byte[] memory, int offset, String str, int maxLength) {
        byte[] octets = str.getBytes();
        for (int i = 0; i < maxLength; i++) {
            if (i < octets.length) {
                memory[offset + i] = octets[i];
            } else {
                memory[offset + i] = 0;
            }
        }
        return maxLength;
    }

    // TODO:
    // Lire jusqu'au premier octet nul
    // ou jusqu'à maxLength.
    public static String readString(byte[] memory, int offset, int maxLength) {
        int longueur = 0;
        while (longueur < maxLength && memory[offset + longueur] != 0) {
            longueur++;
        }
        return new String(memory, offset, longueur);
    }
}