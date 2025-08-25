import java.util.*;

public class CSVAnalyzer {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter CSV data (end input with an empty line):");

        StringBuilder inputData = new StringBuilder();
        while (true) {
            String line = scanner.nextLine();
            if (line.trim().isEmpty()) break;
            inputData.append(line).append("\n");
        }

        String[][] data = parseCSV(inputData.toString());
        cleanAndValidateData(data);

        Map<Integer, ColumnStats> stats = analyzeData(data);

        displayTable(data, stats);

        displaySummary(data, stats);

        scanner.close();
    }

    static String[][] parseCSV(String input) {
        List<String[]> rows = new ArrayList<>();

        int i = 0, n = input.length();
        List<String> currentRow = new ArrayList<>();
        while (i < n) {
            StringBuilder field = new StringBuilder();
            boolean inQuotes = false;

            // Handle quoted fields and commas/newlines inside quotes
            while (i < n) {
                char c = input.charAt(i);

                if (c == '"') {
                    if (inQuotes && i + 1 < n && input.charAt(i + 1) == '"') {
                        // Escaped quote inside quoted field
                        field.append('"');
                        i += 2;
                        continue;
                    }
                    inQuotes = !inQuotes;
                    i++;
                } else if (c == ',' && !inQuotes) {
                    i++;
                    break; // end of field
                } else if (c == '\n' && !inQuotes) {
                    i++;
                    break; // end of row
                } else {
                    field.append(c);
                    i++;
                }
            }
            currentRow.add(field.toString());

            // If end of line or end of input, push current row
            if (i == n || (!inQuotes && (i == n || input.charAt(i - 1) == '\n'))) {
                rows.add(currentRow.toArray(new String[0]));
                currentRow = new ArrayList<>();
            }
        }

        // Convert to 2D array, fix rows with missing fields by padding with empty strings
        int maxCols = 0;
        for (String[] row : rows) {
            if (row.length > maxCols) maxCols = row.length;
        }

        String[][] result = new String[rows.size()][maxCols];
        for (int r = 0; r < rows.size(); r++) {
            String[] row = rows.get(r);
            for (int c = 0; c < maxCols; c++) {
                result[r][c] = c < row.length ? row[c] : "";
            }
        }

        return result;
    }

    static void cleanAndValidateData(String[][] data) {
        for (int r = 0; r < data.length; r++) {
            for (int c = 0; c < data[r].length; c++) {
                String val = data[r][c].trim();
                // Remove enclosing quotes if any
                if (val.startsWith("\"") && val.endsWith("\"") && val.length() > 1) {
                    val = val.substring(1, val.length() - 1).replace("\"\"", "\"");
                }
                data[r][c] = val;
            }
        }
    }

    static Map<Integer, ColumnStats> analyzeData(String[][] data) {
        Map<Integer, ColumnStats> statsMap = new HashMap<>();
        int rows = data.length;

        // Assume first row is header, analyze from second row
        int cols = data[0].length;

        for (int c = 0; c < cols; c++) {
            ColumnStats stats = new ColumnStats();
            stats.columnIndex = c;
            stats.isNumeric = true;

            for (int r = 1; r < rows; r++) {
                String val = data[r][c];
                if (val.isEmpty()) {
                    stats.missing++;
                    continue;
                }

                if (isNumeric(val)) {
                    double d = Double.parseDouble(val);
                    if (stats.count == 0) {
                        stats.min = d;
                        stats.max = d;
                    } else {
                        if (d < stats.min) stats.min = d;
                        if (d > stats.max) stats.max = d;
                    }
                    stats.sum += d;
                    stats.count++;
                } else {
                    stats.isNumeric = false;
                    stats.uniqueValues.add(val);
                }
            }
            statsMap.put(c, stats);
        }
        return statsMap;
    }

    static boolean isNumeric(String s) {
        int len = s.length();
        if (len == 0) return false;
        int dotCount = 0;
        for (int i = 0; i < len; i++) {
            char ch = s.charAt(i);
            if (ch == '.') {
                dotCount++;
                if (dotCount > 1) return false;
            } else if (ch < '0' || ch > '9') return false;
        }
        return true;
    }

    static void displayTable(String[][] data, Map<Integer, ColumnStats> stats) {
        int rows = data.length;
        int cols = data[0].length;
        int[] colWidths = new int[cols];

        // Calculate column widths by header & data length
        for (int c = 0; c < cols; c++) {
            int max = data[0][c].length();
            for (int r = 1; r < rows; r++) {
                int len = data[r][c].length();
                if (len > max) max = len;
            }
            colWidths[c] = Math.min(Math.max(max, 5), 30); // limit max width
        }

        StringBuilder sb = new StringBuilder();

        // Build border line
        String border = "+";
        for (int w : colWidths) {
            border += "-".repeat(w + 2) + "+";
        }

        sb.append(border).append("\n");

        // Header row
        sb.append("|");
        for (int c = 0; c < cols; c++) {
            sb.append(" ").append(padCenter(data[0][c], colWidths[c])).append(" |");
        }
        sb.append("\n").append(border).append("\n");

        // Data rows
        for (int r = 1; r < rows; r++) {
            sb.append("|");
            for (int c = 0; c < cols; c++) {
                String val = data[r][c];
                ColumnStats stat = stats.get(c);

                String dispVal = val;
                boolean highlight = false;

                if (val.isEmpty()) {
                    dispVal = "(missing)";
                    highlight = true;
                } else if (stat.isNumeric && !isNumeric(val)) {
                    dispVal = "(invalid)";
                    highlight = true;
                }

                if (stat.isNumeric && !val.isEmpty() && isNumeric(val)) {
                    // Format numeric values to 2 decimal places
                    double d = Double.parseDouble(val);
                    dispVal = String.format("%.2f", d);
                }

                if (highlight) {
                    dispVal = "*" + dispVal + "*";
                }

                sb.append(" ").append(padRight(dispVal, colWidths[c])).append(" |");
            }
            sb.append("\n");
        }

        sb.append(border);
        System.out.println("\nFormatted Table:\n" + sb);
    }

    static void displaySummary(String[][] data, Map<Integer, ColumnStats> stats) {
        int totalRows = data.length - 1;
        int totalMissing = 0;
        int totalCells = totalRows * data[0].length;

        for (ColumnStats cs : stats.values()) {
            totalMissing += cs.missing;
        }
        double completeness = 100.0 * (totalCells - totalMissing) / totalCells;

        System.out.println("\nData Summary Report:");
        System.out.println("Total Records Processed: " + totalRows);
        System.out.printf("Data Completeness: %.2f%%\n", completeness);

        System.out.println("\nColumn-wise Statistics:");
        for (int c = 0; c < data[0].length; c++) {
            ColumnStats cs = stats.get(c);
            System.out.println("Column: " + data[0][c]);
            if (cs.isNumeric) {
                System.out.printf("  Numeric column - Min: %.2f, Max: %.2f, Average: %.2f\n",
                        cs.min, cs.max, cs.count > 0 ? cs.sum / cs.count : 0);
            } else {
                System.out.println("  Categorical column - Unique values count: " + cs.uniqueValues.size());
            }
            System.out.println("  Missing entries: " + cs.missing);
            System.out.println();
        }
    }

    static String padRight(String s, int width) {
        return String.format("%-" + width + "s", s);
    }

    static String padCenter(String s, int width) {
        int padding = width - s.length();
        int padStart = padding / 2;
        int padEnd = padding - padStart;
        return " ".repeat(padStart) + s + " ".repeat(padEnd);
    }

    static class ColumnStats {
        int columnIndex;
        boolean isNumeric;
        int missing = 0;
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        double sum = 0;
        int count = 0;
        Set<String> uniqueValues = new HashSet<>();
    }
}
