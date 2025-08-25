import java.util.Scanner;

public class ASCIIProcessor {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a string:");
        String input = scanner.nextLine();

        System.out.println("\nCharacter Analysis:");
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            int ascii = (int) ch;
            System.out.println("Character: '" + ch + "' ASCII: " + ascii);
            String type = classifyCharacter(ch);
            System.out.println("  Type: " + type);

            if (type.contains("Letter")) {
                char upper = Character.toUpperCase(ch);
                char lower = Character.toLowerCase(ch);
                int upperAscii = (int) upper;
                int lowerAscii = (int) lower;
                int diff = Math.abs(upperAscii - lowerAscii);
                System.out.println("  Uppercase: '" + upper + "' (" + upperAscii + ")");
                System.out.println("  Lowercase: '" + lower + "' (" + lowerAscii + ")");
                System.out.println("  Difference in ASCII between cases: " + diff);
            }
            System.out.println();
        }

        System.out.println("ASCII Table (32 to 126):");
        displayASCIITable(32, 126);

        System.out.println("\nSimple Caesar Cipher");
        System.out.print("Enter shift amount (integer): ");
        int shift = scanner.nextInt();
        scanner.nextLine(); // consume newline
        String cipherText = caesarCipher(input, shift);
        System.out.println("Ciphered text: " + cipherText);

        scanner.close();
    }

    public static String classifyCharacter(char ch) {
        if (ch >= 'A' && ch <= 'Z') return "Uppercase Letter";
        else if (ch >= 'a' && ch <= 'z') return "Lowercase Letter";
        else if (ch >= '0' && ch <= '9') return "Digit";
        else return "Special Character";
    }

    public static char toggleCase(char ch) {
        if (ch >= 'A' && ch <= 'Z') {
            return (char) (ch + 32);
        } else if (ch >= 'a' && ch <= 'z') {
            return (char) (ch - 32);
        } else {
            return ch;
        }
    }

    public static String caesarCipher(String text, int shift) {
        StringBuilder result = new StringBuilder();
        for (char ch : text.toCharArray()) {
            if (ch >= 'A' && ch <= 'Z') {
                int shifted = ((ch - 'A' + shift) % 26);
                if (shifted < 0) shifted += 26; 
                result.append((char) ('A' + shifted));
            } else if (ch >= 'a' && ch <= 'z') {
                int shifted = ((ch - 'a' + shift) % 26);
                if (shifted < 0) shifted += 26;
                result.append((char) ('a' + shifted));
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    public static void displayASCIITable(int start, int end) {
        for (int i = start; i <= end; i++) {
            System.out.printf("%3d : %c\t", i, (char) i);
            if ((i - start + 1) % 10 == 0) System.out.println();
        }
        System.out.println();
    }

    public static int[] stringToASCII(String text) {
        int[] asciiValues = new int[text.length()];
        for (int i = 0; i < text.length(); i++) {
            asciiValues[i] = (int) text.charAt(i);
        }
        return asciiValues;
    }

    public static String asciiToString(int[] asciiValues) {
        StringBuilder sb = new StringBuilder();
        for (int val : asciiValues) {
            sb.append((char) val);
        }
        return sb.toString();
    }
}