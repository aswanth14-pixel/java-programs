import java.util.ArrayList;
import java.util.Scanner;

public class SubstringReplace {

    public static ArrayList<Integer> findOccurrences(String text, String find) {
        ArrayList<Integer> positions = new ArrayList<>();
        int index = 0;
        while ((index = text.indexOf(find, index)) != -1) {
            positions.add(index);
            index += find.length();
        }
        return positions;
    }

    public static String manualReplace(String text, String find, String replace) {
        StringBuilder result = new StringBuilder();
        int i = 0;
        while (i < text.length()) {
            if (i <= text.length() - find.length() && text.substring(i, i + find.length()).equals(find)) {
                result.append(replace);
                i += find.length();
            } else {
                result.append(text.charAt(i));
                i++;
            }
        }
        return result.toString();
    }

    public static boolean compareWithBuiltIn(String original, String find, String replace, String manual) {
        String builtIn = original.replace(find, replace);
        return builtIn.equals(manual);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the main text:");
        String text = scanner.nextLine();
        System.out.println("Enter the substring to find:");
        String find = scanner.nextLine();
        System.out.println("Enter the substring to replace with:");
        String replace = scanner.nextLine();

        ArrayList<Integer> positions = findOccurrences(text, find);
        System.out.println("Found at positions: " + positions);

        String manualResult = manualReplace(text, find, replace);
        System.out.println("Manual replace result: " + manualResult);

        String builtInResult = text.replace(find, replace);
        System.out.println("Built-in replace result: " + builtInResult);

        boolean isEqual = compareWithBuiltIn(text, find, replace, manualResult);
        System.out.println("Do both results match? " + isEqual);
        scanner.close();
    }
}
