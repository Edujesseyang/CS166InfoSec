package JakobsonBreaker;

import MyBREAKER.Analyzer;

import java.awt.*;
import java.util.*;
import java.util.stream.Collectors;

public class JakobsonAlgorithm {
    static final double[][] BIGRAM = {
/*        A      B      C      D      E      F      G      H      I      J      K      L      M      N      O      P      Q      R      S      T      U      V      W      X      Y      Z */
/*A*/ {-3.17, -3.88, -3.57, -3.40, -2.94, -3.83, -3.66, -3.48, -3.21, -4.50, -4.01, -3.14, -3.62, -3.12, -3.01, -3.75, -5.00, -3.05, -2.90, -3.08, -3.63, -4.08, -3.90, -4.55, -3.74, -4.72},
/*B*/ {-3.88, -4.61, -4.36, -4.49, -3.52, -4.59, -4.42, -4.29, -4.01, -4.94, -4.68, -4.03, -4.26, -4.03, -3.71, -4.56, -5.00, -4.01, -3.91, -3.96, -4.14, -4.68, -4.49, -4.94, -4.14, -5.00},
/*C*/ {-3.57, -4.36, -4.48, -4.22, -3.50, -4.63, -4.49, -4.36, -4.05, -4.97, -4.69, -4.06, -4.30, -4.03, -3.77, -4.53, -5.00, -4.02, -3.97, -3.95, -4.20, -4.71, -4.52, -4.97, -4.16, -5.00},
/*D*/ {-3.40, -4.49, -4.22, -4.09, -3.34, -4.42, -4.26, -4.16, -3.87, -4.86, -4.59, -3.96, -4.17, -3.88, -3.54, -4.33, -5.00, -3.92, -3.85, -3.74, -4.05, -4.57, -4.35, -4.86, -3.99, -5.00},
/*E*/ {-2.94, -3.52, -3.50, -3.34, -2.63, -3.73, -3.53, -3.33, -3.02, -4.40, -3.84, -3.03, -3.42, -2.94, -2.79, -3.69, -5.00, -2.92, -2.80, -2.84, -3.39, -3.93, -3.70, -4.44, -3.50, -4.73},
/*F*/ {-3.83, -4.59, -4.63, -4.42, -3.73, -4.80, -4.59, -4.45, -4.15, -5.00, -4.73, -4.10, -4.45, -4.17, -3.90, -4.66, -5.00, -4.12, -4.02, -4.00, -4.31, -4.73, -4.60, -5.00, -4.26, -5.00},
/*G*/ {-3.66, -4.42, -4.49, -4.26, -3.53, -4.59, -4.45, -4.31, -4.03, -4.97, -4.69, -4.06, -4.29, -4.02, -3.78, -4.54, -5.00, -4.03, -3.97, -3.96, -4.19, -4.71, -4.53, -4.97, -4.16, -5.00},
/*H*/ {-3.48, -4.29, -4.36, -4.16, -3.33, -4.45, -4.31, -4.21, -3.94, -4.91, -4.64, -3.99, -4.24, -3.96, -3.69, -4.43, -5.00, -3.99, -3.91, -3.87, -4.11, -4.63, -4.45, -4.91, -4.08, -5.00},
/*I*/ {-3.21, -4.01, -4.05, -3.87, -3.02, -4.15, -4.03, -3.94, -3.65, -4.79, -4.44, -3.80, -4.05, -3.68, -3.45, -4.12, -5.00, -3.69, -3.60, -3.58, -3.90, -4.44, -4.24, -4.79, -3.92, -5.00},
/*J*/ {-4.50, -4.94, -4.97, -4.86, -4.40, -5.00, -4.97, -4.91, -4.79, -5.00, -5.00, -4.73, -4.97, -4.79, -4.61, -4.94, -5.00, -4.79, -4.74, -4.70, -4.84, -5.00, -4.94, -5.00, -4.84, -5.00},
/*K*/ {-4.01, -4.68, -4.69, -4.59, -3.84, -4.73, -4.69, -4.64, -4.44, -5.00, -4.97, -4.30, -4.61, -4.39, -4.16, -4.71, -5.00, -4.35, -4.26, -4.24, -4.53, -4.97, -4.76, -5.00, -4.49, -5.00},
/*L*/ {-3.14, -4.03, -4.06, -3.96, -3.03, -4.10, -4.06, -3.99, -3.80, -4.73, -4.30, -3.66, -3.96, -3.77, -3.52, -4.07, -5.00, -3.78, -3.70, -3.69, -3.97, -4.30, -4.11, -4.73, -3.98, -5.00},
/*M*/ {-3.62, -4.26, -4.30, -4.17, -3.42, -4.45, -4.29, -4.24, -4.05, -4.97, -4.61, -3.96, -4.22, -3.98, -3.74, -4.43, -5.00, -3.99, -3.91, -3.89, -4.16, -4.61, -4.43, -4.97, -4.12, -5.00},
/*N*/ {-3.12, -4.03, -4.03, -3.88, -2.94, -4.17, -4.02, -3.96, -3.68, -4.79, -4.39, -3.77, -3.98, -3.62, -3.38, -4.12, -5.00, -3.66, -3.57, -3.55, -3.88, -4.39, -4.20, -4.79, -3.92, -5.00},
/*O*/ {-3.01, -3.71, -3.77, -3.54, -2.79, -3.90, -3.78, -3.69, -3.45, -4.61, -4.16, -3.52, -3.74, -3.38, -3.22, -3.85, -5.00, -3.36, -3.28, -3.25, -3.57, -4.16, -3.97, -4.61, -3.75, -5.00},
/*P*/ {-3.75, -4.56, -4.53, -4.33, -3.69, -4.66, -4.54, -4.43, -4.12, -4.94, -4.71, -4.07, -4.43, -4.12, -3.85, -4.66, -5.00, -4.12, -4.03, -4.00, -4.31, -4.71, -4.60, -4.94, -4.26, -5.00},
/*Q*/ {-5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00},
/*R*/ {-3.05, -4.01, -4.02, -3.92, -2.92, -4.12, -4.03, -3.99, -3.69, -4.79, -4.35, -3.78, -3.99, -3.66, -3.36, -4.12, -5.00, -3.56, -3.45, -3.44, -3.76, -4.35, -4.17, -4.79, -3.89, -5.00},
/*S*/ {-2.90, -3.91, -3.97, -3.85, -2.80, -4.02, -3.97, -3.91, -3.60, -4.74, -4.26, -3.70, -3.91, -3.57, -3.28, -4.03, -5.00, -3.45, -3.31, -3.32, -3.61, -4.26, -4.08, -4.74, -3.84, -5.00},
/*T*/ {-3.08, -3.96, -3.95, -3.74, -2.84, -4.00, -3.96, -3.87, -3.58, -4.70, -4.24, -3.69, -3.89, -3.55, -3.25, -4.00, -5.00, -3.44, -3.32, -3.29, -3.58, -4.24, -4.06, -4.70, -3.82, -5.00},
/*U*/ {-3.63, -4.14, -4.20, -4.05, -3.39, -4.31, -4.19, -4.11, -3.90, -4.84, -4.53, -3.97, -4.16, -3.88, -3.57, -4.31, -5.00, -3.76, -3.61, -3.58, -3.91, -4.53, -4.36, -4.84, -3.99, -5.00},
/*V*/ {-4.08, -4.68, -4.71, -4.57, -3.93, -4.73, -4.71, -4.63, -4.44, -5.00, -4.97, -4.30, -4.61, -4.39, -4.16, -4.71, -5.00, -4.35, -4.26, -4.24, -4.53, -4.97, -4.76, -5.00, -4.49, -5.00},
/*W*/ {-3.90, -4.49, -4.52, -4.35, -3.70, -4.60, -4.53, -4.45, -4.24, -4.94, -4.76, -4.11, -4.43, -4.20, -3.97, -4.60, -5.00, -4.17, -4.08, -4.06, -4.36, -4.76, -4.56, -4.94, -4.22, -5.00},
/*X*/ {-4.55, -4.94, -4.97, -4.86, -4.44, -5.00, -4.97, -4.91, -4.79, -5.00, -5.00, -4.73, -4.97, -4.79, -4.61, -4.94, -5.00, -4.79, -4.74, -4.70, -4.84, -5.00, -4.94, -5.00, -4.84, -5.00},
/*Y*/ {-3.74, -4.14, -4.16, -3.99, -3.50, -4.26, -4.16, -4.08, -3.92, -4.84, -4.49, -3.98, -4.12, -3.92, -3.75, -4.26, -5.00, -3.89, -3.84, -3.82, -3.99, -4.49, -4.22, -4.84, -4.05, -5.00},
/*Z*/ {-4.72, -5.00, -5.00, -5.00, -4.73, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00}
    };

    private static class KeyScorePair {
        String key;
        Double score;

        KeyScorePair(String key, Double score) {
            this.key = key;
            this.score = score;
        }
    }

    public static void main(String[] args) {
        System.out.println("Enter the cipher: ");
        Scanner sc = new Scanner(System.in);
        String cipher = sc.next();
        System.out.println("Analyzing, it takes up to 30 seconds ......");
        Map<String, Double> report = analyze(cipher);
        int count = 0;
        System.out.println("Report: ");
        for (Map.Entry<String, Double> e : report.entrySet()) {
            count++;
            System.out.println("No." + count);
            System.out.println("Score: " + e.getValue() + "    Key: " + e.getKey());
            System.out.println("Plaintext: ");
            System.out.println(decrypt(cipher, e.getKey()));
            System.out.println("-----------------------");
            if (count >= 50) break;
        }
    }

    private static Map<String, Double> analyze(String cipher) {
        String key = getInitialKey(cipher);
        Map<String, Double> report = new HashMap<>();
        int count = 100;
        double bestScore = 0;
        while (count >= 0) {
            KeyScorePair pair = getBestScore(cipher, key, report);
            if (bestScore < pair.score) {
                bestScore = pair.score;
                key = pair.key;
            }
            count--;
        }
        report = report.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .limit(20)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));

        return report;

    }

    private static KeyScorePair getBestScore(String cipher, String key, Map<String, Double> report) {
        double bestScore = 0.0;
        String bestKey = key;
        char[] keyArr = key.toCharArray();

        for (int i = 0; i < keyArr.length; i++) {
            for (int j = 0; j < keyArr.length; j++) {
                swap(keyArr, i, j); // swap 2 chars of key
                String newKey = new String(keyArr);
                String newMessage = decrypt(cipher, newKey);
                double score = getScore(newMessage);
                if (score > bestScore) {
                    bestScore = score;
                    bestKey = newKey;
                }
                report.put(bestKey, score);
                swap(keyArr, i, j); // swap back
            }
        }
        return new KeyScorePair(bestKey, bestScore);
    }

    private static double getScore(String guess) {
        double score = 0;
        char[] arr = guess.toCharArray();
        for (int i = 1; i < arr.length; i++) {
            int j = i - 1;
            int next = arr[i] - 'A';
            int prev = arr[j] - 'A';
            double bigramScore = BIGRAM[prev][next];
            score += (bigramScore - -5.0);
        }
        return score;
    }

    private static void swap(char[] arr, int i, int j) {
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private static String getInitialKey(String cipher) {
        Analyzer ana = new Analyzer(cipher);
        ana.startAnalysing();
        return ana.getFreqRank();
    }

    private static String decrypt(String cipher, String key) {
        String defaultKey = "ETAOINSHRDLCUMWFGYPBVKJXQZ";
        char[] d = defaultKey.toCharArray();
        char[] c = cipher.toCharArray();
        char[] k = key.toCharArray();
        for (int i = 0; i < c.length; i++) {
            for (int j = 0; j < k.length; j++) {
                if (c[i] == k[j]) {
                    c[i] = d[j];
                    break;
                }
            }
        }
        return new String(c);
    }
}
