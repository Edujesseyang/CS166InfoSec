package FreqCheck;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Analyzer {
    private String cipherText;
    private char[] cArr;

    private Map<Character, Integer> charStat;
    private List<Character> cipherCharRank;
    private List<Map.Entry<Character, Integer>> listOfReport;


    public Analyzer(String cipher) {
        this.cipherText = cipher;
        this.cArr = cipherText.toCharArray();
        this.charStat = new HashMap<>();
        this.cipherCharRank = new ArrayList<>();
        this.listOfReport = null;
    }

    public void startAnalysing() {
        charCounting();
        listOfReport = new ArrayList<>(charStat.entrySet());
        listOfReport.sort(Map.Entry.comparingByValue());
        listOfReport = listOfReport.reversed();
        for (Map.Entry<Character, Integer> pair : listOfReport) {
            cipherCharRank.add(pair.getKey());
        }
    }

    public void printReport() {
        System.out.println("Ranking word freq: ");
        for (Map.Entry<Character, Integer> pair : listOfReport) {
            System.out.println(pair.getKey() + " : " + pair.getValue());
        }
    }

    public String getFreqRank(){
        StringBuilder sb = new StringBuilder();
        for(Character c : cipherCharRank){
            sb.append(c);
        }
        while(sb.length() < 26){
            sb.append('_');
        }
        return sb.toString();
    }

    private void charCounting() {
        for (Character c : cArr) {
            charStat.put(c, charStat.getOrDefault(c, 0) + 1);
        }
    }


}
