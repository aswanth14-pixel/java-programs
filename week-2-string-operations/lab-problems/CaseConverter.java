import java.util.Scanner;

public class CaseConverter {

    public static char toUpper(char c) {
        if (c >= 'a' && c <= 'z') {
            return (char) (c - 32);
        }
        return c;
    }

    public static char toLower(char c) {
        if (c >= 'A' && c <= 'Z') {
            return (char) (c + 32);
        }
        return c;
    }

    public static String toUpperCaseManual(String text) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            result.append(toUpper(text.charAt(i)));
        }
        return result.toString();
    }

    public static String toLowerCaseManual(String text) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            result.append(toLower(text.charAt(i)));
        }
        return result.toString();
    }

    public static String toTitleCaseManual(String text) {
        StringBuilder result = new StringBuilder();
        boolean newWord = true;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == ' ') {
                result.append(c);
                newWord = true;
            } else if (newWord) {
                result.append(toUpper(c));
                newWord = false;
            } else {
                result.append(toLower(c));
            }
        }
        return result.toString();
    }

    public static void compareWithBuiltIn(String input) {
        String builtInUpper = input.toUpperCase();
        String builtInLower = input.toLowerCase();
        String builtInTitle = toTitleCaseBuiltIn(input);

        String manualUpper = toUpperCaseManual(input);
        String manualLower = toLowerCaseManual(input);
        String manualTitle = toTitleCaseManual(input);

        System.out.printf("%-15s | %-30s | %-30s | %s\n", "Conversion", "Manual Result", "Built-in Result", "Match?");
        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.printf("%-15s | %-30s | %-30s | %s\n", "Uppercase", manualUpper, builtInUpper, manualUpper.equals(builtInUpper));
        System.out.printf("%-15s | %-30s | %-30s | %s\n", "Lowercase", manualLower, builtInLower, manualLower.equals(builtInLower));
        System.out.printf("%-15s | %-30s | %-30s | %s\n", "Title Case", manualTitle, builtInTitle, manualTitle.equals(builtInTitle));
    }

    public static String toTitleCaseBuiltIn(String input) {
        String[] words = input.toLowerCase().split(" ");
        StringBuilder title = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            if (!words[i].isEmpty()) {
                title.append(Character.toUpperCase(words[i].charAt(0)));
                if (words[i].length() > 1) {
                    title.append(words[i].substring(1));
                }
            }
            if (i != words.length - 1) {
                title.append(" ");
            }
        }
        return title.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter text to convert:");
        String input = scanner.nextLine();

        compareWithBuiltIn(input);
        scanner.close();
    }
}

