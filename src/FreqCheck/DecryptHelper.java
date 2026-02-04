package FreqCheck;

import java.util.Scanner;

public class DecryptHelper {
    public static void main(String[] args) {
        /*
        Q11:
        GBSXUCGSZQGKGSPKQKGLSKASPCGBGBKGUKGCEUKUZKGGBSQEICACGKGCEUERWKLKUPKQQGCIICUAEUVSHQKGCEUPCGBCGQOEVSHUNSUGKUZCGQSNLSHEHIEEDCUOGEPKHZGBSNKCUGSUKUASERLSKASCUGBSLKACRCACUZSSZEUSBEXHKRGSHWKLKUSQSKCHQTXKZHEUQBKZAENNSUASZFENFCUOCUEKBXGBSWKLKUSQSKNFKQQKZEHGEGBSXUCGSZQGKGSKQUZBCQAEIISKQXSZSICVSHSZGEGBSQSASHSGKHMERQGKGSKREHNKIHSLIMGEKHSASUGKNSHCAKUNSQQKOSPBCISGBCQHSLIMGQKGSZGBKGCGQSSNSZXQSISQQGEAEUGCUXSGBSSJCQGCUOZCLIENKGCAUSOEGCKGCEUQCGAEUGKCUSZUEGBHSKGEHBCUGERPKHEHKHNSZKGGKAD

        Q13:
        QSKDFNSNHRLYWISCLTWNLWISWUWNUHRMWRNYWISCLTSLLSAASYSYTSALHPSLQSACRLHKHFAQSWALLQSRKHFEWRULWALLHYWISCLTSLLSAQSKDFNSNHRLTSWBAWCNKHFOSASYWNSLHMHHFLWRNMSLQSALQSYCRFLSKHFPSLQSAFRNSAKHFAUICRLQSRKHFTSMCRLHYWISCLTSLLSAWRNWRKLCYSKHFBSSPLQSJWCRQSKDFNSASBAWCRNHRLEWAAKLQSOHAPNFJHRKHFAUQHFPNSAUBHAOSPPKHFIRHOLQWLCLUWBHHPOQHJPWKUCLEHHPTKYWICRMQCUOHAPNWPCLLPSEHPNSAQSKDFNSNHRLPSLYSNHORKHFQWXSBHFRNQSARHOMHWRNMSLQSAASYSYTSALHPSLQSACRLHKHFAQSWALLQSRKHFEWRULWALLHYWISCLTSLLSA
         */

        String realFreq = "ETAOINSHRDLCUMWFGYPBVKJXQZ";
        System.out.println("Enter cipher: ");
        Scanner sc = new Scanner(System.in);
        String cipher = sc.next();
        System.out.println("\nStart analyze: ");
        Analyzer ana = new Analyzer(cipher);
        ana.startAnalysing();
        ana.printReport();
        String cipherRank = ana.getFreqRank();
        System.out.println("Compare two ranks: ");
        System.out.println("Real freq rank: " + realFreq);
        System.out.println("Cipher freq rank: " + cipherRank);
        System.out.println("Trying to replace all ...");
        String originGuess = replaceByRank(cipher, cipherRank, realFreq);
        String newGuess = originGuess;

        while (true) {
            System.out.println("New guess: ");
            System.out.println(newGuess);
            System.out.println("Enter chars that you want to swap: (000 to reset, 999 to finish)");
            System.out.println("Hint: ETAOINSHRDLCUMWFGYPBVKJXQZ");
            Scanner scan = new Scanner(System.in);
            String switchPair = scan.next();
            if (switchPair.equals("000")) {
                newGuess = originGuess;
            } else if (switchPair.equals(("999"))) {
                System.out.println("The key is : " + cipherRank);
                break;
            } else {
                newGuess = swapChar(newGuess, switchPair.charAt(0), switchPair.charAt(1));
                cipherRank = swapChar(cipherRank, switchPair.charAt(0), switchPair.charAt(1));
            }
        }
    }

    private static String replaceByRank(String cipher, String cipherRank, String realRank) {
        char[] cA = cipher.toCharArray();
        char[] cR = cipherRank.toCharArray();
        char[] rR = realRank.toCharArray();

        for (int i = 0; i < cA.length; i++) {
            for (int j = 0; j < cR.length; j++) {
                if (cA[i] == cR[j]) {
                    cA[i] = rR[j];
                    break;
                }
            }
        }
        return new String(cA);
    }

    private static String swapChar(String str, Character a, Character b) {
        char[] cA = str.toCharArray();
        for (int i = 0; i < cA.length; i++) {
            if (cA[i] == a) {
                cA[i] = b;
            } else if (cA[i] == b) {
                cA[i] = a;
            }
        }
        return new String(cA);
    }
}