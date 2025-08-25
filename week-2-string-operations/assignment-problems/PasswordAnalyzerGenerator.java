import java.util.*;

public class PasswordAnalyzerGenerator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of passwords to analyze:");
        int n = Integer.parseInt(scanner.nextLine());

        String[] passwords = new String[n];
        for (int i = 0; i < n; i++) {
            System.out.println("Enter password " + (i + 1) + ":");
            passwords[i] = scanner.nextLine();
        }

        System.out.printf("%-15s %-8s %-10s %-10s %-8s %-15s %-6s %-10s\n",
                "Password", "Length", "Uppercase", "Lowercase", "Digits", "Special Chars", "Score", "Strength");

        for (String password : passwords) {
            int upper = 0, lower = 0, digits = 0, special = 0;
            for (int i = 0; i < password.length(); i++) {
                char c = password.charAt(i);
                int ascii = (int) c;
                if (ascii >= 65 && ascii <= 90) upper++;
                else if (ascii >= 97 && ascii <= 122) lower++;
                else if (ascii >= 48 && ascii <= 57) digits++;
                else if (ascii >= 33 && ascii <= 126) special++;
            }

            int score = calculateScore(password.length(), upper, lower, digits, special, password);
            String strength = score <= 20 ? "Weak" : (score <= 50 ? "Medium" : "Strong");

            System.out.printf("%-15s %-8d %-10d %-10d %-8d %-15d %-6d %-10s\n",
                    password, password.length(), upper, lower, digits, special, score, strength);
        }

        System.out.println("\nDo you want to generate a strong password? (yes/no)");
        String choice = scanner.nextLine();
        if (choice.equalsIgnoreCase("yes")) {
            System.out.println("Enter desired password length (min 10):");
            int len = Integer.parseInt(scanner.nextLine());
            if (len < 10) len = 10;
            String generated = generateStrongPassword(len);
            System.out.println("Generated Strong Password: " + generated);
        }

        scanner.close();
    }

    static int calculateScore(int length, int upper, int lower, int digits, int special, String password) {
        int score = 0;
        if (length > 8) score += (length - 8) * 2;
        if (upper > 0) score += 10;
        if (lower > 0) score += 10;
        if (digits > 0) score += 10;
        if (special > 0) score += 10;

        String lowerPass = password.toLowerCase();
        String[] common = {"123", "abc", "qwerty", "password", "111", "000"};
        for (String pattern : common) {
            if (lowerPass.contains(pattern)) score -= 10;
        }

        return score;
    }

    static String generateStrongPassword(int length) {
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String special = "!@#$%^&*()-_=+[]{};:,.<>?";

        String all = upper + lower + digits + special;
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();

        sb.append(upper.charAt(rand.nextInt(upper.length())));
        sb.append(lower.charAt(rand.nextInt(lower.length())));
        sb.append(digits.charAt(rand.nextInt(digits.length())));
        sb.append(special.charAt(rand.nextInt(special.length())));

        for (int i = 4; i < length; i++) {
            sb.append(all.charAt(rand.nextInt(all.length())));
        }

        List<Character> chars = new ArrayList<>();
        for (int i = 0; i < sb.length(); i++) {
            chars.add(sb.charAt(i));
        }
        Collections.shuffle(chars);

        StringBuilder finalPassword = new StringBuilder();
        for (char c : chars) {
            finalPassword.append(c);
        }

        return finalPassword.toString();
    }
}
