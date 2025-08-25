public class StringBuiltInMethods {
    public static void main(String[] args) {
        String sampleText = " Java Programming is Fun and Challenging! ";

        int originalLength = sampleText.length();
        String trimmedText = sampleText.trim();
        int trimmedLength = trimmedText.length();
        char charAt5 = trimmedText.charAt(5);
        String substringProgramming = trimmedText.substring(5, 16);
        int indexOfFun = trimmedText.indexOf("Fun");
        boolean containsJava = trimmedText.contains("Java");
        boolean startsWithJava = trimmedText.startsWith("Java");
        boolean endsWithExclamation = trimmedText.endsWith("!");
        String upperCaseText = trimmedText.toUpperCase();
        String lowerCaseText = trimmedText.toLowerCase();

        System.out.println("Original text: \"" + sampleText + "\"");
        System.out.println("1. Original length (including spaces): " + originalLength);
        System.out.println("2. Trimmed text: \"" + trimmedText + "\"");
        System.out.println("   Trimmed length: " + trimmedLength);
        System.out.println("3. Character at index 5: '" + charAt5 + "'");
        System.out.println("4. Substring \"Programming\": \"" + substringProgramming + "\"");
        System.out.println("5. Index of the word \"Fun\": " + indexOfFun);
        System.out.println("6. Contains \"Java\"? " + containsJava);
        System.out.println("7. Starts with \"Java\"? " + startsWithJava);
        System.out.println("8. Ends with an exclamation mark? " + endsWithExclamation);
        System.out.println("9. Uppercase: \"" + upperCaseText + "\"");
        System.out.println("10. Lowercase: \"" + lowerCaseText + "\"");

        int vowelCount = countVowels(trimmedText);
        System.out.println("Vowel count in trimmed text: " + vowelCount);
        char targetChar = 'a';
        System.out.print("Positions of character '" + targetChar + "' in trimmed text: ");
        findAllOccurrences(trimmedText, targetChar);
    }

    public static int countVowels(String text) {
        int count = 0;
        String vowels = "AEIOUaeiou";
        for (int i = 0; i < text.length(); i++) {
            if (vowels.indexOf(text.charAt(i)) != -1) {
                count++;
            }
        }
        return count;
    }

    public static void findAllOccurrences(String text, char target) {
        boolean found = false;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == target) {
                System.out.print(i + " ");
                found = true;
            }
        }
        if (!found) {
            System.out.print("None");
        }
        System.out.println();
    }
}