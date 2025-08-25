import java.util.Scanner;

public class AdvancedStringAnalyzer {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== ADVANCED STRING ANALYZER ===");

        System.out.print("Enter first string: ");
        String str1 = scanner.nextLine();

        System.out.print("Enter second string: ");
        String str2 = scanner.nextLine();

        performAllComparisons(str1, str2);

        double similarity = calculateSimilarity(str1, str2);
        System.out.printf("Similarity percentage: %.2f%%\n", similarity * 100);

        analyzeMemoryUsage(str1, str2);

        String[] inputs = {str1, str2};
        String optimizedResult = optimizedStringProcessing(inputs);
        System.out.println("Optimized concatenation result: " + optimizedResult);

        demonstrateStringIntern();

        scanner.close();
    }

    public static double calculateSimilarity(String str1, String str2) {
        int distance = levenshteinDistance(str1, str2);
        int maxLen = Math.max(str1.length(), str2.length());
        if (maxLen == 0) return 1.0; // both empty strings
        return 1.0 - ((double) distance / maxLen);
    }

    private static int levenshteinDistance(String s1, String s2) {
        int[] costs = new int[s2.length() + 1];
        for (int j = 0; j < costs.length; j++)
            costs[j] = j;
        for (int i = 1; i <= s1.length(); i++) {
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= s2.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]),
                        s1.charAt(i - 1) == s2.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[s2.length()];
    }

    public static void performAllComparisons(String str1, String str2) {
        System.out.println("\nComparison Analysis:");
        System.out.println("Reference equality (==): " + (str1 == str2));
        System.out.println("Content equality (equals): " + str1.equals(str2));
        System.out.println("Case-insensitive equality (equalsIgnoreCase): " + str1.equalsIgnoreCase(str2));
        System.out.println("Lexicographic comparison (compareTo): " + str1.compareTo(str2));
        System.out.println("Case-insensitive lexicographic comparison (compareToIgnoreCase): " + str1.compareToIgnoreCase(str2));
    }

    public static void analyzeMemoryUsage(String... strings) {
        System.out.println("\nApproximate Memory Usage:");
        for (String s : strings) {
            int size = 40 + s.length() * 2; // Rough estimate: object overhead + 2 bytes per char (UTF-16)
            System.out.printf("String \"%s\" ~ %d bytes\n", s, size);
        }
    }

    public static String optimizedStringProcessing(String[] inputs) {
        StringBuilder sb = new StringBuilder();
        for (String s : inputs) {
            sb.append(s).append(" ");
        }
        return sb.toString().trim();
    }

    public static void demonstrateStringIntern() {
        System.out.println("\nDemonstrating String.intern():");
        String a = new String("hello");
        String b = new String("hello");
        System.out.println("Before intern(): a == b ? " + (a == b));
        String aInterned = a.intern();
        String bInterned = b.intern();
        System.out.println("After intern(): aInterned == bInterned ? " + (aInterned == bInterned));
    }
}