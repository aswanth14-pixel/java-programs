import java.util.*;
import java.text.SimpleDateFormat;

public class FileOrganizer {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter file names with extensions (empty line to finish):");

        List<FileInfo> files = new ArrayList<>();
        while (true) {
            String input = scanner.nextLine();
            if (input.trim().isEmpty()) break;
            FileInfo fi = parseFileName(input);
            files.add(fi);
        }

        categorizeFiles(files);
        analyzeContents(files);
        generateNewNames(files);
        displayReport(files);
        generateBatchRenameCommands(files);

        scanner.close();
    }

    static class FileInfo {
        String originalName;
        String name;
        String extension;
        boolean validName;
        String category = "Unknown";
        String subcategory = "";
        int priority = 0;
        String newName = "";
        int duplicateIndex = 0;
        boolean needsAttention = false;
    }

    static FileInfo parseFileName(String fileName) {
        FileInfo file = new FileInfo();
        file.originalName = fileName;

        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex == -1 || dotIndex == 0 || dotIndex == fileName.length() - 1) {
            file.validName = false;
            file.name = fileName;
            file.extension = "";
            file.needsAttention = true;
            return file;
        }

        file.name = fileName.substring(0, dotIndex);
        file.extension = fileName.substring(dotIndex + 1).toLowerCase();

        file.validName = isValidFileName(file.name) && isValidExtension(file.extension);
        if (!file.validName) file.needsAttention = true;
        return file;
    }

    static boolean isValidFileName(String name) {
        // Valid chars: letters, digits, underscores, hyphens, spaces allowed
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (!(Character.isLetterOrDigit(c) || c == '_' || c == '-' || c == ' ')) return false;
        }
        return true;
    }

    static boolean isValidExtension(String ext) {
        // Allow letters and digits only for extension
        for (int i = 0; i < ext.length(); i++) {
            char c = ext.charAt(i);
            if (!Character.isLetterOrDigit(c)) return false;
        }
        return true;
    }

    static void categorizeFiles(List<FileInfo> files) {
        Map<String, String> extCategoryMap = new HashMap<>();
        extCategoryMap.put("txt", "Documents");
        extCategoryMap.put("doc", "Documents");
        extCategoryMap.put("docx", "Documents");
        extCategoryMap.put("pdf", "Documents");

        extCategoryMap.put("jpg", "Images");
        extCategoryMap.put("jpeg", "Images");
        extCategoryMap.put("png", "Images");
        extCategoryMap.put("gif", "Images");

        extCategoryMap.put("mp3", "Audio");
        extCategoryMap.put("wav", "Audio");

        extCategoryMap.put("mp4", "Videos");
        extCategoryMap.put("avi", "Videos");

        for (FileInfo file : files) {
            String cat = extCategoryMap.getOrDefault(file.extension, "Unknown");
            file.category = cat;
            if (cat.equals("Unknown")) file.needsAttention = true;
        }
    }

    static void analyzeContents(List<FileInfo> files) {
        // Simulated content-based analysis for text files
        for (FileInfo file : files) {
            if (file.category.equals("Documents") && file.extension.equals("txt")) {
                String lowerName = file.name.toLowerCase();

                if (lowerName.contains("resume") || lowerName.contains("cv")) {
                    file.subcategory = "Resume";
                    file.priority = 5;
                } else if (lowerName.contains("report")) {
                    file.subcategory = "Report";
                    file.priority = 4;
                } else if (lowerName.contains("code") || lowerName.contains("script")) {
                    file.subcategory = "Code";
                    file.priority = 3;
                } else {
                    file.subcategory = "General";
                    file.priority = 1;
                }
            }
        }
    }

    static void generateNewNames(List<FileInfo> files) {
        Map<String, Integer> nameCounters = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateStr = sdf.format(new Date());

        for (FileInfo file : files) {
            String base = file.category.replaceAll("\\s+", "") + "_" + dateStr;
            if (!file.subcategory.isEmpty()) base += "_" + file.subcategory;
            String newName = base;

            int count = nameCounters.getOrDefault(newName, 0);
            if (count > 0) {
                newName += "_" + count;
            }
            nameCounters.put(base, count + 1);

            newName += "." + file.extension;
            file.newName = newName;

            // Validate new name
            if (!isValidFileName(newName.substring(0, newName.lastIndexOf('.')))) {
                file.needsAttention = true;
            }
        }
    }

    static void displayReport(List<FileInfo> files) {
        System.out.println("\nFile Organization Report:\n");
        System.out.printf("%-25s %-15s %-25s %-12s\n", "Original Filename", "Category", "New Filename", "Attention");
        System.out.println("--------------------------------------------------------------------------------");

        Map<String, Integer> categoryCounts = new HashMap<>();
        int attentionCount = 0;

        for (FileInfo file : files) {
            categoryCounts.put(file.category, categoryCounts.getOrDefault(file.category, 0) + 1);
            String att = file.needsAttention ? "Yes" : "No";
            if (file.needsAttention) attentionCount++;

            System.out.printf("%-25s %-15s %-25s %-12s\n",
                    file.originalName, file.category, file.newName, att);
        }

        System.out.println("\nCategory-wise File Counts:");
        for (var entry : categoryCounts.entrySet()) {
            System.out.printf("%-15s : %d\n", entry.getKey(), entry.getValue());
        }

        if (attentionCount > 0) {
            System.out.println("\nFiles Needing Attention (invalid names or unknown types): " + attentionCount);
        } else {
            System.out.println("\nNo files need attention.");
        }
    }

    static void generateBatchRenameCommands(List<FileInfo> files) {
        System.out.println("\nBatch Rename Commands (for Windows cmd):");
        for (FileInfo file : files) {
            if (!file.originalName.equals(file.newName)) {
                System.out.printf("ren \"%s\" \"%s\"\n", file.originalName, file.newName);
            }
        }
    }
}
