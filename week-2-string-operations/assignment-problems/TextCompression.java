import java.util.Scanner;

public class TextCompression {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter text to compress:");
        String text = scanner.nextLine();

        Object[] freqResult = countFrequency(text);
        char[] chars = (char[]) freqResult[0];
        int[] freqs = (int[]) freqResult[1];
        int uniqueCount = (int) freqResult[2];

        String[][] mapping = createCompressionCodes(chars, freqs, uniqueCount);

        String compressed = compressText(text, mapping, uniqueCount);
        String decompressed = decompressText(compressed, mapping, uniqueCount);

        displayAnalysis(chars, freqs, uniqueCount, mapping, text, compressed, decompressed);

        scanner.close();
    }

    static Object[] countFrequency(String text) {
        int n = text.length();
        char[] tempChars = new char[n];
        int[] tempFreqs = new int[n];
        int count = 0;

        for (int i = 0; i < n; i++) {
            char c = text.charAt(i);
            int index = -1;
            for (int j = 0; j < count; j++) {
                if (tempChars[j] == c) {
                    index = j;
                    break;
                }
            }
            if (index == -1) {
                tempChars[count] = c;
                tempFreqs[count] = 1;
                count++;
            } else {
                tempFreqs[index]++;
            }
        }

        char[] chars = new char[count];
        int[] freqs = new int[count];
        for (int i = 0; i < count; i++) {
            chars[i] = tempChars[i];
            freqs[i] = tempFreqs[i];
        }
        return new Object[]{chars, freqs, count};
    }

    static String[][] createCompressionCodes(char[] chars, int[] freqs, int size) {
        // Sort characters by frequency descending (simple bubble sort)
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - 1 - i; j++) {
                if (freqs[j] < freqs[j + 1]) {
                    int tempF = freqs[j];
                    freqs[j] = freqs[j + 1];
                    freqs[j + 1] = tempF;
                    char tempC = chars[j];
                    chars[j] = chars[j + 1];
                    chars[j + 1] = tempC;
                }
            }
        }

        String[] codes = {"@", "#", "$", "%", "&", "*", "+", "!", "?", "~", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
        // fallback codes if size > codes.length
        String[] fallback = new String[size];
        for (int i = 0; i < size; i++) {
            if (i < codes.length) {
                fallback[i] = codes[i];
            } else {
                fallback[i] = "x" + (i - codes.length + 1);
            }
        }

        String[][] mapping = new String[size][2];
        for (int i = 0; i < size; i++) {
            mapping[i][0] = "" + chars[i];
            mapping[i][1] = fallback[i];
        }
        return mapping;
    }

    static String compressText(String text, String[][] mapping, int size) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            for (int j = 0; j < size; j++) {
                if (mapping[j][0].charAt(0) == c) {
                    sb.append(mapping[j][1]);
                    break;
                }
            }
        }
        return sb.toString();
    }

    static String decompressText(String compressed, String[][] mapping, int size) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < compressed.length()) {
            boolean found = false;
            // Check for 1 or 2 character codes
            for (int len = 2; len >= 1; len--) {
                if (i + len <= compressed.length()) {
                    String sub = compressed.substring(i, i + len);
                    for (int j = 0; j < size; j++) {
                        if (mapping[j][1].equals(sub)) {
                            sb.append(mapping[j][0]);
                            i += len;
                            found = true;
                            break;
                        }
                    }
                    if (found) break;
                }
            }
            if (!found) {
                // Unknown code (shouldn't happen)
                sb.append('?');
                i++;
            }
        }
        return sb.toString();
    }

    static void displayAnalysis(char[] chars, int[] freqs, int size, String[][] mapping, String original, String compressed, String decompressed) {
        System.out.println("\nCharacter Frequency Table:");
        System.out.printf("%-10s %-10s\n", "Char", "Frequency");
        for (int i = 0; i < size; i++) {
            String ch = chars[i] == ' ' ? "' '" : "" + chars[i];
            System.out.printf("%-10s %-10d\n", ch, freqs[i]);
        }

        System.out.println("\nCompression Mapping:");
        System.out.printf("%-10s %-10s\n", "Char", "Code");
        for (int i = 0; i < size; i++) {
            String ch = chars[i] == ' ' ? "' '" : "" + chars[i];
            System.out.printf("%-10s %-10s\n", ch, mapping[i][1]);
        }

        System.out.println("\nOriginal Text:");
        System.out.println(original);

        System.out.println("\nCompressed Text:");
        System.out.println(compressed);

        System.out.println("\nDecompressed Text:");
        System.out.println(decompressed);

        int originalSize = original.length() * 8; // assuming 8 bits per char
        int compressedSize = compressed.length() * 8; // simplistic, but assuming 8 bits per code char
        double efficiency = ((double)(originalSize - compressedSize) / originalSize) * 100;

        System.out.printf("\nCompression Efficiency: %.2f%%\n", efficiency);

        if (original.equals(decompressed)) {
            System.out.println("Decompression validated: original and decompressed texts match.");
        } else {
            System.out.println("Decompression error: texts do not match.");
        }
    }
}
