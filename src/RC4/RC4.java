package RC4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RC4 {
    public static void main(String[] args) {
        List<Integer> key = new ArrayList<>(Arrays.asList(0x1A, 0x2B, 0x3C, 0x4D, 0x5E, 0x6F, 0x77));
        // parse key to bit array
        int[] binaryKey = parseToBitArray(key);

        // A) init generator and KSA
        System.out.println("================== (A) ======================");
        System.out.println("Status after KSA:");
        KeystreamGenerator G = new KeystreamGenerator(binaryKey);
        printOut_S(G.s); // showing s
        System.out.println("i = " + G.i_idx); // i = 0
        System.out.println("j = " + G.j_idx); // j = 0

        // B) generate 100 bytes of the keystream
        System.out.println("================== (B) ======================");
        System.out.println("Status after generating 100 bytes: ");
        List<Integer> keystreamSeg1 = new ArrayList<>();
        for(int i = 0; i < 100; i++){
            keystreamSeg1.add(G.next());
        }
        printOut_S(G.s);
        System.out.println("i = " + G.i_idx);
        System.out.println("j = " + G.j_idx);

        // C) generate another 900 bytes

        System.out.println("================== (C) ======================");
        System.out.println("Status after generating another 900 bytes: ");
        List<Integer> keystreamSeg2 = new ArrayList<>();
        for(int i = 0; i < 900; i++){
            keystreamSeg1.add(G.next());
        }
        printOut_S(G.s);
        System.out.println("i = " + G.i_idx);
        System.out.println("j = " + G.j_idx);

    }

    private static int[] parseToBitArray(List<Integer> intList) {
        int[] bits = new int[intList.size() * 8];
        int idx = 0;

        for (int v : intList) {
            v &= 0xFF;
            for (int i = 7; i >= 0; i--) {
                bits[idx++] = (v >> i) & 1;
            }
        }
        return bits;
    }

    private static void printOut_S(int[] s) {
        for (int i = 0; i < s.length; i++) {
            if (i != 0 && i % 16 == 0) {
                System.out.println();
            }
            System.out.print(s[i] + " ");
        }
        System.out.println();
    }

    static class KeystreamGenerator {
        int[] s;
        int[] key;
        int i_idx;
        int j_idx;

        public KeystreamGenerator(int[] key) {
            s = new int[256];
            for (int i = 0; i < 256; i++) {
                s[i] = i;
            }
            this.key = key;
            this.i_idx = 0;
            this.j_idx = 0;
            KSA();
        }

        private void KSA() {
            int kLen = key.length;
            int j = 0;
            for (int i = 0; i < s.length; i++) {
                j = (j + s[i] + key[i % kLen]) % 0xff;
                swap(s, i, j);
            }
        }


        private int next() {
            i_idx = (i_idx + 1) % 0xff;
            j_idx = (j_idx + s[i_idx]) % 0xff;
            swap(s, i_idx, j_idx);
            int k = (s[i_idx] + s[j_idx]) % 0xff;
            return s[k];
        }

        private void swap(int[] s, int i, int j) {
            int temp = s[i];
            s[i] = s[j];
            s[j] = temp;
        }
    }
}
