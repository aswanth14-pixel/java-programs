import java.util.Scanner;

public class SpellChecker {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String[] dictionary = {"this", "is", "a", "simple", "sentence", "with", "some", "correct", "words", "and", "spelling", "errors"};

        System.out.println("Enter a sentence:");
        String sentence = scanner.nextLine();

        String[] words = splitSentence(sentence);

        System.out.printf("%-15s %-20s %-10s %-10s\n", "Original", "Suggestion", "Distance", "Status");

        for (String word : words) {
            String suggestion = findClosestWord(word, dictionary);
            int distance = suggestion.equals(word) ? 0 : stringDistance(word, suggestion);
            String status = distance <= 2 ? (distance == 0 ? "Correct" : "Misspelled") : "Misspelled";

            System.out.printf("%-15s %-20s %-10d %-10s\n", word, suggestion, distance, status);
        }

        scanner.close();
    }

    static String[] splitSentence(String sentence) {
        int len = sentence.length();
        String[] tempWords = new String[len];
        int count = 0;
        int start = 0;

        for (int i = 0; i <= len; i++) {
            if (i == len || !Character.isLetter(sentence.charAt(i))) {
                if (start < i) {
                    tempWords[count++] = sentence.substring(start, i).toLowerCase();
                }
                start = i + 1;
            }
        }

        String[] words = new String[count];
        System.arraycopy(tempWords, 0, words, 0, count);
        return words;
    }

    static int stringDistance(String a, String b) {
        int lenA = a.length();
        int lenB = b.length();
        int minLen = Math.min(lenA, lenB);
        int distance = Math.abs(lenA - lenB);

        for (int i = 0; i < minLen; i++) {
            if (a.charAt(i) != b.charAt(i)) {
                distance++;
            }
        }
        return distance;
    }

    static String findClosestWord(String word, String[] dictionary) {
        int minDistance = Integer.MAX_VALUE;
        String closest = word;

        for (String dictWord : dictionary) {
            int dist = stringDistance(word, dictWord);
            if (dist < minDistance) {
                minDistance = dist;
                closest = dictWord;
            }
        }

        if (minDistance <= 2) {
            return closest;
        } else {
            return word;
        }
    }
}
