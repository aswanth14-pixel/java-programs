import java.util.*;

public class EmailAnalyzer {

    static class EmailInfo {
        String email;
        String username;
        String domain;
        String domainName;
        String extension;
        boolean isValid;

        EmailInfo(String email, String username, String domain, String domainName, String extension, boolean isValid) {
            this.email = email;
            this.username = username;
            this.domain = domain;
            this.domainName = domainName;
            this.extension = extension;
            this.isValid = isValid;
        }
    }

    public static boolean validateEmail(String email) {
        int atIndex = email.indexOf('@');
        int lastAt = email.lastIndexOf('@');
        if (atIndex <= 0 || atIndex != lastAt) return false;

        String afterAt = email.substring(atIndex + 1);
        int dotIndex = afterAt.indexOf('.');
        if (dotIndex <= 0) return false;

        String username = email.substring(0, atIndex);
        String domain = email.substring(atIndex + 1);
        if (username.isEmpty() || domain.isEmpty()) return false;

        return true;
    }

    public static EmailInfo extractEmailInfo(String email) {
        if (!validateEmail(email)) {
            return new EmailInfo(email, "-", "-", "-", "-", false);
        }

        int atIndex = email.indexOf('@');
        String username = email.substring(0, atIndex);
        String domain = email.substring(atIndex + 1);

        int dotIndex = domain.lastIndexOf('.');
        String domainName = dotIndex > 0 ? domain.substring(0, dotIndex) : "-";
        String extension = dotIndex > 0 ? domain.substring(dotIndex + 1) : "-";

        return new EmailInfo(email, username, domain, domainName, extension, true);
    }

    public static void analyzeAndDisplay(List<String> emails) {
        List<EmailInfo> results = new ArrayList<>();
        Map<String, Integer> domainCount = new HashMap<>();
        int validCount = 0;
        int totalUsernameLength = 0;

        for (String email : emails) {
            EmailInfo info = extractEmailInfo(email);
            results.add(info);

            if (info.isValid) {
                validCount++;
                totalUsernameLength += info.username.length();
                domainCount.put(info.domain, domainCount.getOrDefault(info.domain, 0) + 1);
            }
        }

        int invalidCount = emails.size() - validCount;
        String mostCommonDomain = domainCount.entrySet()
                .stream()
                .max(Comparator.comparingInt(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse("N/A");

        double averageUsernameLength = validCount > 0 ? (double) totalUsernameLength / validCount : 0;

        System.out.printf("%-30s %-15s %-25s %-20s %-10s %-10s\n", "Email", "Username", "Domain", "Domain Name", "Ext", "Valid?");
        System.out.println("----------------------------------------------------------------------------------------------");
        for (EmailInfo info : results) {
            System.out.printf("%-30s %-15s %-25s %-20s %-10s %-10s\n",
                    info.email, info.username, info.domain, info.domainName, info.extension, info.isValid ? "Yes" : "No");
        }

        System.out.println("\n--- Analysis ---");
        System.out.println("Total Emails: " + emails.size());
        System.out.println("Valid Emails: " + validCount);
        System.out.println("Invalid Emails: " + invalidCount);
        System.out.println("Most Common Domain: " + mostCommonDomain);
        System.out.printf("Average Username Length: %.2f\n", averageUsernameLength);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> emails = new ArrayList<>();

        System.out.println("Enter email addresses (type 'done' to finish):");
        while (true) {
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("done")) break;
            if (!input.isEmpty()) emails.add(input);
        }

        analyzeAndDisplay(emails);
        scanner.close();
    }
}
        