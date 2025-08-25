import java.util.Scanner;

public class StringPerformance {

    public static Result testStringConcat(int iterations) {
        long start = System.currentTimeMillis();
        String result = "";
        for (int i = 0; i < iterations; i++) {
            result += "sample";
        }
        long end = System.currentTimeMillis();
        return new Result("String (+)", end - start, result.length());
    }

    public static Result testStringBuilder(int iterations) {
        long start = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < iterations; i++) {
            sb.append("sample");
        }
        long end = System.currentTimeMillis();
        return new Result("StringBuilder", end - start, sb.length());
    }

    public static Result testStringBuffer(int iterations) {
        long start = System.currentTimeMillis();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < iterations; i++) {
            sb.append("sample");
        }
        long end = System.currentTimeMillis();
        return new Result("StringBuffer", end - start, sb.length());
    }

    public static void displayResults(Result... results) {
        System.out.printf("%-15s | %-15s | %-20s\n", "Method", "Time (ms)", "Final String Length");
        System.out.println("---------------------------------------------------------------");
        for (Result r : results) {
            System.out.printf("%-15s | %-15d | %-20d\n", r.method, r.timeTaken, r.length);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of iterations:");
        int iterations = scanner.nextInt();

        Result concatResult = testStringConcat(iterations);
        Result builderResult = testStringBuilder(iterations);
        Result bufferResult = testStringBuffer(iterations);

        displayResults(concatResult, builderResult, bufferResult);
        scanner.close();
    }

    static class Result {
        String method;
        long timeTaken;
        int length;

        Result(String method, long timeTaken, int length) {
            this.method = method;
            this.timeTaken = timeTaken;
            this.length = length;
        }
    }
}

