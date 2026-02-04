package MyBREAKER;

import java.util.*;
import java.util.stream.Collectors;

public class MyBreaker {

    private static class ScorePair {
        String key;
        Long score;

        public ScorePair(String key, Long score) {
            this.key = key;
            this.score = score;
        }
    }

    public static void main(String[] args) {
        Set<String> keywords = initKeywordList();
        System.out.println("Enter the cipher: ");
        Scanner sc = new Scanner(System.in);
        String cipher = sc.next();
        System.out.println("Analyzing, it takes up to 30 seconds ......");
        Map<String, Long> report = analyze(cipher, keywords);
        int count = 0;
        System.out.println("Report: ");
        for (Map.Entry<String, Long> e : report.entrySet()) {
            count++;
            System.out.println("No." + count);
            System.out.println("Score: " + e.getValue() + "    Key: " + e.getKey());
            System.out.println("Plaintext: ");
            System.out.println(decrypt(cipher, e.getKey()));
            System.out.println("-----------------------");
            if (count >= 10) break;
        }

    }

    private static Map<String, Long> analyze(String cipher, Set<String> keywords) {
        String key = getInitialKey(cipher);
        Map<String, Long> report = new HashMap<>();
        int count = 100;
        long bestScore = 0;
        while (count >= 0) {
            ScorePair pair = mixNext(cipher, key, keywords, report);
            if (pair.score >= bestScore) {
                key = pair.key;
                bestScore = pair.score;
            }
            pair = mixPairs(cipher, key, keywords, report);
            if (pair.score >= bestScore) {
                key = pair.key;
                bestScore = pair.score;
            }
            count--;
        }
        report = report.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(20)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));

        return report;
    }

    private static ScorePair mixPairs(String cipher, String key, Set<String> keywords, Map<String, Long> report) {
        long bestScore = 0;
        String bestKey = key;
        char[] keyArr = key.toCharArray();
        for (int i = 0; i < keyArr.length; i++) {
            for (int j = 0; j < keyArr.length; j++) {
                swap(keyArr, i, j); // swap 2 chars of key
                String newKey = new String(keyArr);
                String newMessage = decrypt(cipher, newKey);
                long score = getScore(newMessage, keywords);
                if (score > bestScore) {
                    bestScore = score;
                    bestKey = newKey;
                }
                report.put(newKey, score);
                swap(keyArr, i, j); // swap back
            }
        }
        return new ScorePair(bestKey, bestScore);
    }


    private static ScorePair mixNext(String cipher, String key, Set<String> keywords, Map<String, Long> report) {
        long bestScore = 0;
        String bestKey = key;
        char[] keyArr = key.toCharArray();

        for (int i = 1; i < keyArr.length; i++) {
            int j = i - 1;
            swap(keyArr, i, j); // swap 2 chars of key
            String newKey = new String(keyArr);
            String newMessage = decrypt(cipher, newKey);
            long score = getScore(newMessage, keywords);
            if (score > bestScore) {
                bestScore = score;
                bestKey = newKey;
            }
            report.put(newKey, score);
            swap(keyArr, i, j); // swap back
        }
        return new ScorePair(bestKey, bestScore);
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

    private static long getScore(String guess, Set<String> keywords) {
        long score = 0;
        for (String word : keywords) {
            int count = countOccurrences(guess, word);
            long length = word.length();
            score += length * count;

        }
        return score;
    }

    private static int countOccurrences(String text, String keyword) {
        int count = 0;
        int i = 0;
        int keyLen = keyword.length();
        while (i <= text.length() - keyLen) {
            int startPoint = i;
            for (int j = 0; j < keyLen; j++) {
                if (text.charAt(startPoint) == keyword.charAt(j)) {
                    startPoint++;
                } else {
                    break;
                }
                if (j == keyLen - 1) count++;
            }
            i++;
        }
        return count;
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

    private static Set<String> initKeywordList() {
        return new HashSet<>(Arrays.asList(
                "THE", "OF", "AND", "TO", "IN", "AM", "IS", "THAT", "FOR", "IT", "AS", "WITH", "WAS", "ON", "BE",
                "AT", "BY", "THIS", "HAVE", "FROM", "OR", "ONE", "HAD", "NOT", "BUT", "WHAT", "ALL", "WERE",
                "WHEN", "WE", "THERE", "CAN", "AN", "YOUR", "WHICH", "THEIR", "SAID", "IF", "DO", "WILL",
                "EACH", "ABOUT", "HOW", "UP", "OUT", "THEM", "THEN", "SHE", "MANY", "SOME", "SO", "THESE",
                "WOULD", "OTHER", "INTO", "HAS", "MORE", "HER", "TWO", "LIKE", "HIM", "SEE", "TIME", "COULD",
                "NO", "MAKE", "THAN", "FIRST", "ITS", "WHO", "NOW", "PEOPLE", "MY", "OVER", "DID", "DOWN",
                "ONLY", "WAY", "FIND", "USE", "MAY", "WATER", "LONG", "LITTLE", "VERY", "AFTER", "WORDS",
                "CALLED", "JUST", "WHERE", "MOST", "KNOW", "GET", "THROUGH", "BACK", "MUCH", "GO", "GOOD",
                "NEW", "WRITE", "OUR", "USED", "ME", "MAN", "TOO", "ANY", "DAY", "SAME", "RIGHT", "LOOK",
                "THINK", "ALSO", "AROUND", "ANOTHER", "CAME", "COME", "WORK", "THREE", "MUST", "BECAUSE",
                "DOES", "PART", "EVEN", "PLACE", "WELL", "SUCH", "HERE", "TAKE", "WHY", "ASK", "WENT",
                "MEN", "READ", "NEED", "LAND", "DIFFERENT", "HOME", "US", "MOVE", "TRY", "KIND", "HAND",
                "PICTURE", "AGAIN", "CHANGE", "OFF", "PLAY", "SPELL", "AIR", "AWAY", "ANIMAL", "HOUSE",
                "POINT", "PAGE", "LETTER", "MOTHER", "ANSWER", "FOUND", "STUDY", "STILL", "LEARN",
                "SHOULD", "AMERICA", "WORLD", "HIGH", "EVERY", "NEAR", "ADD", "FOOD", "BETWEEN", "OWN",
                "BELOW", "COUNTRY", "PLANT", "LAST", "SCHOOL", "FATHER", "KEEP", "TREE", "NEVER",
                "START", "CITY", "EARTH", "EYES", "LIGHT", "THOUGHT", "HEAD", "UNDER", "STORY", "SAW",
                "LEFT", "DON’T", "FEW", "WHILE", "ALONG", "MIGHT", "CLOSE", "SOMETHING", "SEEM",
                "NEXT", "HARD", "OPEN", "EXAMPLE", "BEGIN", "LIFE", "ALWAYS", "THOSE", "BOTH", "PAPER",
                "TOGETHER", "GOT", "GROUP", "OFTEN", "RUN", "IMPORTANT", "UNTIL", "CHILDREN", "SIDE",
                "FEET", "CAR", "MILE", "NIGHT", "WALK", "WHITE", "SEA", "BEGAN", "GROW", "TOOK",
                "RIVER", "FOUR", "CARRY", "STATE", "ONCE", "BOOK", "HEAR", "STOP", "WITHOUT",
                "SECOND", "LATE", "MISS", "IDEA", "ENOUGH", "EAT", "FACE", "WATCH", "FAR", "INDIAN",
                "REAL", "ALMOST", "LET", "ABOVE", "GIRL", "SOMETIMES", "MOUNTAIN", "CUT", "YOUNG",
                "TALK", "SOON", "LIST", "SONG", "BEING", "LEAVE", "FAMILY", "IT’S", "BODY", "MUSIC",
                "COLOR", "STAND", "SUN", "QUESTIONS", "FISH", "AREA", "MARK", "DOG", "HORSE", "BIRDS",
                "PROBLEM", "COMPLETE", "ROOM", "KNEW", "SINCE", "EVER", "PIECE", "TOLD", "USUALLY",
                "DIDN’T", "FRIENDS", "EASY", "HEARD", "ORDER", "RED", "DOOR", "SURE", "BECOME", "TOP",
                "SHIP", "ACROSS", "TODAY", "DURING", "SHORT", "BETTER", "BEST", "HOWEVER", "LOW",
                "HOURS", "BLACK", "PRODUCT", "HAPPENED", "WHOLE", "MEASURE", "REMEMBER", "EARLY",
                "WAVES", "REACHED", "LISTEN", "WIND", "ROCK", "SPACE", "COVER", "FAST", "SEVERAL",
                "HOLD", "HIMSELF", "TOWARD", "FIVE", "STEP", "MORNING", "PASSED", "VOCAL", "TRUE",
                "HUNDRED", "AGAINST", "PATTERN", "NUMERAL", "TABLE", "NORTH", "SLOWLY", "MONEY",
                "MAP", "FARM", "PULLED", "DRAW", "VOICE", "SEEN", "COLD", "CRIED", "PLAN", "NOTICE",
                "SOUTH", "SING", "WAR", "GROUND", "FALL", "KING", "TOWN", "I’LL", "UNIT", "FIGURE",
                "CERTAIN", "FIELD", "TRAVEL", "WOOD", "FIRE", "UPON", "DONE", "ENGLISH", "ROAD",
                "HALF", "TEN", "FLY", "GAVE", "BOX", "FINALLY", "WAIT", "CORRECT", "OH", "QUICKLY",
                "PERSON", "BECAME", "SHOW", "MINUTES", "STRONG", "VERB", "STARS", "FRONT", "FEEL",
                "FACT", "INCHES", "STREET", "DECIDED", "CONTAIN", "COURSE", "SURFACE", "PRODUCE", "PHY", "CHO",
                "BUILD", "OCEAN", "CLASS", "NOTE", "NOTHING", "REST", "CAREFULLY", "SCIENTISTS", "ESE", "CES",
                "INSIDE", "WHEELS", "STAY", "GREEN", "KNOWN", "ISLAND", "WEEK", "LESS", "MACHINE", "JAPANESE",
                "BASE", "AGO", "STOOD", "PLAIN", "GAS", "WEIGHT", "COPY", "SHOE", "HILL", "EXPERIMENT",
                "BEAUTY", "DRIVE", "STOOD", "CONTAINS", "FRONT", "TEACH", "WEEK", "FINAL", "GAVE", "BE",
                "IF", "IS", "AM", "TO", "SO", "IT", "AS", "AN", "IN", "ON", "BY", "US", "MY", "HE", "ME",
                "DO", "NO", "AT", "WE", "UP", "THE", "AND", "FOR", "NOT", "YOU", "ALL", "ANY", "FEW", "WEEK",
                "CAN", "HAD", "HER", "WAS", "ONE", "OUR", "OUT", "DAY", "HAS", "HIM", "ENGLISH", "AMERICA", "SCORE",
                "HIS", "HOW", "MAN", "NEW", "NOW", "OLD", "SEE", "TWO", "WAY", "WHO", "USED", "PLAIN", "TEXT", "TAXI",
                "DID", "ITS", "SET", "WITH", "THIS", "FROM", "THEY", "WERE", "HAVE", "FOOD", "DONT", "MISS", "HIT",
                "WILL", "YOUR", "WHAT", "WHEN", "WHERE", "WHOM", "WHICH", "MAKE", "TIME", "BEAT", "AFRAID", "BETTER",
                "YEAR", "WANT", "KNOW", "TAKE", "INTO", "ONLY", "OVER", "BACK", "EVEN", "MEAN", "TRUE", "PLEASE",
                "WORK", "LIFE", "THERE", "WHICH", "ABOUT", "WOULD", "THEIR", "OTHER", "COULD", "THESE", "REMEMBER",
                "FIRST", "AFTER", "WHERE", "THINK", "THOSE", "NEVER", "WORLD", "RIGHT", "MEMORY", "TOP", "BOTTOM",
                "PLACE", "UNDER", "AGAIN", "EVERY", "THING", "HOUSE", "POINT", "WATER", "PEOPLE", "PUBLIC",
                "SHOULD", "ALWAYS", "LITTLE", "SYSTEM", "NUMBER", "LEFT", "RIGHT", "MIDDLE", "EAR", "HAND", "FOOT", "HEAD",
                "FOLLOW", "AROUND", "BECOME", "DURING", "BEFORE", "WITHIN", "LISTEN", "OCCUR", "BOTH", "COMPARE", "BET",
                "MOTHER", "FATHER", "FRIEND", "GROUND", "COURSE", "ENOUGH", "MATCH", "WHERE", "LETTER", "SAME", "SIMPLE",
                "BETWEEN", "WITHOUT", "ANOTHER", "COUNTRY", "PROBLEM", "COMPANY", "SUPPOSE", "CONTAINS", "APPROACH",
                "GENERAL", "THROUGH", "DURING", "BECAUSE", "NOTHING", "SOMETHING", "REPEAT", "WRITE", "READ", "FOLLOW",
                "SEVERAL", "SERVICE", "HISTORY", "PERSONAL", "PROCESS", "EXAMPLE", "FIRST", "SECOND", "GIVE", "PUT",
                "RESULTS", "AGAINST", "PROGRAM", "FACTORS", "OFFICIAL", "MESSAGE", "MADE", "MUCH", "PASS", "ERROR",
                "INTEREST", "PROVIDED", "GOVERNMENT", "EFFECT", "AFFECT", "RECOVER", "COVER", "CORRECT", "MESS", "GUESS",
                "CONTINUE", "LANGUAGE", "INCREASE", "EDUCATION", "DEVELOPMENT", "ACCEPT", "TRY", "DEPTH", "DEEP", "THREE",
                "SITUATION", "KNOWLEDGE", "CONSIDER", "EVERYBODY", "POSSIBLE", "CLASS", "DIFF", "ONLY", "WORD", "LOW",
                "NATIONAL", "AVAILABLE", "INFORMATION", "EXPERIENCE", "COMMUNITY", "HIGH", "WEID", "LONG", "TREAT", "SING",
                "IMPORTANT", "DIFFERENT", "GOVERNMENTS", "INFORMATIVE", "DEVELOPMENT", "SONG", "APPLE", "BEER", "BRO",
                "EDUCATIONAL", "COMMUNICATION", "SITUATION", "AVAILABLE", "CONTINUING", "SAMPLE", "WHICH", "SAN", "SON",
                "HISTORICAL", "RELATIONSHIP", "DEPARTMENT", "MANAGEMENT", "POPULATION", "FATHER", "MOTHER", "BOY", "GIRL",
                "TECHNICAL", "ENVIRONMENT", "INTERNATIONAL", "ORGANIZATION", "CAR", "BOAT", "FLY", "CHIP", "PRO", "PRE",
                "GOVERNANCE", "POPULARITY", "MANAGEMENT", "TECHNOLOGY", "SCR", "SCH", "STU", "STA", "STR", "PLA", "PLE",
                "DEPARTURE", "CONDITIONS", "EXPERIENCE", "PROCESSING", "CHA", "CHI", "QUA", "SQ", "CRA", "OO", "EE",
                "UNDERSTAND", "EVERYTHING", "PRODUCTION", "ADMINISTER", "THEREFORE", "HOWEVER", "ALTHOUGH", "BECAUSE",
                "WHILE", "BETWEEN", "WITHOUT", "WITHIN", "ACROSS", "AROUND", "STATEMENT", "ER", "AR", "OU", "EST", "SED",
                "AGREEMENT", "NEGOTIATION", "RESPONSE", "MESSAGE", "COLD", "HOT", "WARM", "UNITED", "STATE", "JAPAN",
                "ATTACK", "SPE", "SPO", "NNY", "MMY", "SEN", "TEN", "EVER", "BAT", "OND", "VEN", "WEN", "ACE", "ICE", "RITY",
                "LITY", "TION", "URN", "ACH", "RAL", "NAL", "CRY", "KEN", "AGE", "BOBM", "SECRETARY", "REPLY", "FORMAL"
        ));
    }
}
