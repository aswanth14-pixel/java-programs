import java.util.Scanner;

public class CaesarCipher {

    public static String encrypt(String text, int shift) {
        StringBuilder encrypted = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                char e = (char) (((c - 'A' + shift) % 26 + 26) % 26 + 'A');
                encrypted.append(e);
            } else if (c >= 'a' && c <= 'z') {
                char e = (char) (((c - 'a' + shift) % 26 + 26) % 26 + 'a');
                encrypted.append(e);
            } else {
                encrypted.append(c);
            }
        }
        return encrypted.toString();
    }

    public static String decrypt(String text, int shift) {
        return encrypt(text, -shift);
    }

    public static void showAsciiValues(String label, String text) {
        System.out.println(label + ": " + text);
        System.out.print("ASCII: ");
        for (int i = 0; i < text.length(); i++) {
            System.out.print((int) text.charAt(i) + " ");
        }
        System.out.println();
    }

    public static boolean validate(String original, String decrypted) {
        return original.equals(decrypted);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter text to encrypt:");
        String originalText = scanner.nextLine();
        System.out.println("Enter shift value:");
        int shift = scanner.nextInt();

        String encryptedText = encrypt(originalText, shift);
        String decryptedText = decrypt(encryptedText, shift);

        showAsciiValues("Original", originalText);
        showAsciiValues("Encrypted", encryptedText);
        showAsciiValues("Decrypted", decryptedText);

        boolean isValid = validate(originalText, decryptedText);
        System.out.println("Decryption successful? " + isValid);
        scanner.close();
    }
}
