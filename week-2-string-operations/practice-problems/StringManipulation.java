import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class StringManipulation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a sentence with mixed formatting:");
        String input = scanner.nextLine();

        String trimmed = input.trim();
        String replacedSpaces = trimmed.replace(' ', '_');
        String noDigits = replacedSpaces.replaceAll("\\d", "");
        String[] words = noDigits.split("_");
        String joinedWithPipe = String.join(" | ", words);

        String noPunct = removePunctuation(trimmed);
        String capitalized = capitalizeWords(noPunct);
        String reversed = reverseWordOrder(noPunct);

        System.out.println("\nProcessed results:");
        System.out.println("Trimmed: \"" + trimmed + "\"");
        System.out.println("Spaces replaced with underscores: \"" + replacedSpaces + "\"");
        System.out.println("Digits removed: \"" + noDigits + "\"");
        System.out.print("Words array: ");
        for (String w : words) {
            System.out.print("\"" + w + "\" ");
        }
        System.out.println();
        System.out.println("Joined with \" | \": \"" + joinedWithPipe + "\"");
        System.out.println("Without punctuation: \"" + noPunct + "\"");
        System.out.println("Capitalized words: \"" + capitalized + "\"");
        System.out.println("Reversed word order: \"" + reversed + "\"");
        System.out.println("Word frequencies:");
        countWordFrequency(noPunct);

        scanner.close();
    }

    public static String removePunctuation(String text) {
        return text.replaceAll("\\p{Punct}", "");
    }

    public static String capitalizeWords(String text) {
        String[] words = text.split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String w : words) {
            if (w.length() > 0) {
                sb.append(Character.toUpperCase(w.charAt(0)));
                if (w.length() > 1) {
                    sb.append(w.substring(1).toLowerCase());
                }
                sb.append(" ");
            }
        }
        return sb.toString().trim();
    }

    public static String reverseWordOrder(String text) {
        String[] words = text.split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (int i = words.length - 1; i >= 0; i--) {
            sb.append(words[i]);
            if (i > 0) sb.append(" ");
        }
        return sb.toString();
    }

    public static void countWordFrequency(String text) {
        String[] words = text.toLowerCase().split("\\s+");
        Map<String, Integer> frequencyMap = new HashMap<>();
        for (String w : words) {
            if (w.isEmpty()) continue;
            frequencyMap.put(w, frequencyMap.getOrDefault(w, 0) + 1);
        }
        for (Map.Entry<String, Integer> entry : frequencyMap.entrySet()) {
            System.out.println("  " + entry.getKey() + ": " + entry.getValue());
        }
    }
}