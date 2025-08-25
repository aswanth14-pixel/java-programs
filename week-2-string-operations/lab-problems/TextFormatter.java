import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TextFormatter {

    public static List<String> manualWordSplit(String text) {
        List<String> words = new ArrayList<>();
        int start = 0;
        for (int i = 0; i <= text.length(); i++) {
            if (i == text.length() || text.charAt(i) == ' ') {
                if (start < i) {
                    words.add(text.substring(start, i));
                }
                start = i + 1;
            }
        }
        return words;
    }

    public static List<String> justifyText(List<String> words, int width) {
        List<String> lines = new ArrayList<>();
        StringBuilder line = new StringBuilder();
        int i = 0;

        while (i < words.size()) {
            int lineLen = 0;
            int start = i;
            while (i < words.size() && (lineLen + words.get(i).length() + (i - start)) <= width) {
                lineLen += words.get(i).length();
                i++;
            }

            int wordCount = i - start;
            line.setLength(0);

            if (wordCount == 1 || i == words.size()) {
                for (int j = start; j < i; j++) {
                    line.append(words.get(j));
                    if (j != i - 1) line.append(' ');
                }
                while (line.length() < width) {
                    line.append(' ');
                }
            } else {
                int totalSpaces = width - lineLen;
                int spaceBetween = totalSpaces / (wordCount - 1);
                int extraSpaces = totalSpaces % (wordCount - 1);

                for (int j = start; j < i; j++) {
                    line.append(words.get(j));
                    if (j != i - 1) {
                        int spaces = spaceBetween + (extraSpaces-- > 0 ? 1 : 0);
                        for (int s = 0; s < spaces; s++) line.append(' ');
                    }
                }
            }

            lines.add(line.toString());
        }

        return lines;
    }

    public static List<String> centerAlignText(List<String> words, int width) {
        List<String> lines = new ArrayList<>();
        StringBuilder line = new StringBuilder();
        int i = 0;

        while (i < words.size()) {
            int lineLen = 0;
            int start = i;
            while (i < words.size() && (lineLen + words.get(i).length() + (i - start)) <= width) {
                lineLen += words.get(i).length();
                i++;
            }

            line.setLength(0);
            for (int j = start; j < i; j++) {
                line.append(words.get(j));
                if (j != i - 1) line.append(' ');
            }

            int padding = (width - line.length()) / 2;
            StringBuilder centered = new StringBuilder();
            for (int p = 0; p < padding; p++) centered.append(' ');
            centered.append(line);
            lines.add(centered.toString());
        }

        return lines;
    }

    public static List<String> justifyWithStringConcat(List<String> words, int width) {
        List<String> lines = new ArrayList<>();
        String line = "";
        int i = 0;

        while (i < words.size()) {
            int lineLen = 0;
            int start = i;
            while (i < words.size() && (lineLen + words.get(i).length() + (i - start)) <= width) {
                lineLen += words.get(i).length();
                i++;
            }

            int wordCount = i - start;
            line = "";

            if (wordCount == 1 || i == words.size()) {
                for (int j = start; j < i; j++) {
                    line += words.get(j);
                    if (j != i - 1) line += ' ';
                }
                while (line.length() < width) {
                    line += ' ';
                }
            } else {
                int totalSpaces = width - lineLen;
                int spaceBetween = totalSpaces / (wordCount - 1);
                int extraSpaces = totalSpaces % (wordCount - 1);

                for (int j = start; j < i; j++) {
                    line += words.get(j);
                    if (j != i - 1) {
                        int spaces = spaceBetween + (extraSpaces-- > 0 ? 1 : 0);
                        for (int s = 0; s < spaces; s++) line += ' ';
                    }
                }
            }

            lines.add(line);
        }

        return lines;
    }

    public static void displayFormatted(List<String> lines, String title) {
        System.out.println("\n--- " + title + " ---");
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            System.out.printf("Line %2d [%2d chars]: %s\n", i + 1, line.length(), line);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the text to format:");
        String inputText = scanner.nextLine();
        System.out.println("Enter desired line width:");
        int width = scanner.nextInt();

        List<String> words = manualWordSplit(inputText);

        long startBuilder = System.nanoTime();
        List<String> justified = justifyText(words, width);
        long endBuilder = System.nanoTime();

        long startConcat = System.nanoTime();
        List<String> justifiedConcat = justifyWithStringConcat(words, width);
        long endConcat = System.nanoTime();

        List<String> centered = centerAlignText(words, width);

        displayFormatted(justified, "Justified Text (StringBuilder)");
        displayFormatted(centered, "Center-Aligned Text");
        displayFormatted(justifiedConcat, "Justified Text (String Concatenation)");

        long timeBuilder = endBuilder - startBuilder;
        long timeConcat = endConcat - startConcat;

        System.out.println("\n--- Performance Comparison ---");
        System.out.println("StringBuilder Time (ns):     " + timeBuilder);
        System.out.println("String Concatenation Time (ns): " + timeConcat);
        System.out.println("StringBuilder is " + (timeConcat / (double) timeBuilder) + "x faster (approx.)");
        scanner.close();
    }
}
