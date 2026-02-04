package VigenereCipher;

import java.util.Scanner;

public class VigenereBreaker {

    public static void main(String[] args) {
        // cipher: CTMYRDOIBSRESRRRIJYREBYLDIYMLCCYQXSRRMLQFSDXFOWFKTCYJRRIQZSMX
        System.out.println("Enter cipher: ");
        Scanner cipherSc = new Scanner(System.in);
        String cipher = cipherSc.next();

        while (true) {
            char[] cipherChar = cipher.toCharArray();
            System.out.println("Enter a 3 letter word: (999 to exit)");
            Scanner sc = new Scanner(System.in);
            String input = sc.next();
            if(input.equals("999")) break;
            char[] ascii = input.toCharArray();
            int[] nums = new int[3];
            for (int i = 0; i < ascii.length; i++) {
                nums[i] = (int) ascii[i] - 'A';
            }
            int j = 0;
            for (int i = 0; i < cipherChar.length; i++) {
                cipherChar[i] = (char) (cipherChar[i] - nums[j++]);
                if (cipherChar[i] < 'A') {
                    cipherChar[i] = (char) (cipherChar[i] + 26);
                }
                if (j >= 3) {
                    j = 0;
                }
            }
            System.out.println(new String(cipherChar));
        }
    }
}
