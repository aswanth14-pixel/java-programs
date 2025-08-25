import java.util.*;

public class TextCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of expressions to evaluate:");
        int n = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < n; i++) {
            System.out.println("\nEnter expression " + (i + 1) + ":");
            String expr = scanner.nextLine();

            if (!isValidExpression(expr)) {
                System.out.println("Invalid expression format.");
                continue;
            }

            System.out.println("Original Expression: " + expr);
            List<String> steps = new ArrayList<>();
            String result = evaluateWithParentheses(expr, steps);

            for (String step : steps) {
                System.out.println(step);
            }

            System.out.println("Final Result: " + result);
        }

        scanner.close();
    }

    static boolean isValidExpression(String expr) {
        int len = expr.length();
        int parenthesesBalance = 0;
        boolean lastWasOperator = true; // Start expecting number or '('
        for (int i = 0; i < len; i++) {
            char c = expr.charAt(i);
            int ascii = (int) c;

            if (ascii == 32) continue; // space allowed

            if ((ascii >= 48 && ascii <= 57)) {
                lastWasOperator = false;
            } else if (c == '(') {
                parenthesesBalance++;
                lastWasOperator = true;
            } else if (c == ')') {
                parenthesesBalance--;
                if (parenthesesBalance < 0) return false;
                lastWasOperator = false;
            } else if (c == '+' || c == '-' || c == '*' || c == '/') {
                if (lastWasOperator) return false; // no two operators in a row or operator at start
                lastWasOperator = true;
            } else {
                return false; // invalid char
            }
        }
        if (parenthesesBalance != 0) return false;
        if (lastWasOperator) return false; // expression cannot end with operator
        return true;
    }

    static String evaluateWithParentheses(String expr, List<String> steps) {
        String currentExpr = expr;

        while (currentExpr.contains("(")) {
            int lastOpen = currentExpr.lastIndexOf('(');
            int close = currentExpr.indexOf(')', lastOpen);
            String inner = currentExpr.substring(lastOpen + 1, close);
            String innerResult = evaluateSimpleExpression(inner, steps);
            currentExpr = currentExpr.substring(0, lastOpen) + innerResult + currentExpr.substring(close + 1);
            steps.add("After evaluating parentheses: " + currentExpr);
        }

        return evaluateSimpleExpression(currentExpr, steps);
    }

    static String evaluateSimpleExpression(String expr, List<String> steps) {
        List<Integer> numbers = new ArrayList<>();
        List<Character> operators = new ArrayList<>();
        parseExpression(expr, numbers, operators);

        steps.add("Parsed numbers: " + numbers);
        steps.add("Parsed operators: " + operators);

        // Multiplication and division first
        for (int i = 0; i < operators.size();) {
            char op = operators.get(i);
            if (op == '*' || op == '/') {
                int a = numbers.get(i);
                int b = numbers.get(i + 1);
                int res = 0;
                if (op == '*') res = a * b;
                else {
                    if (b == 0) throw new ArithmeticException("Division by zero");
                    res = a / b;
                }
                numbers.set(i, res);
                numbers.remove(i + 1);
                operators.remove(i);
                steps.add("After " + a + " " + op + " " + b + " = " + res + ", numbers: " + numbers + ", operators: " + operators);
            } else {
                i++;
            }
        }

        // Addition and subtraction
        for (int i = 0; i < operators.size();) {
            char op = operators.get(i);
            int a = numbers.get(i);
            int b = numbers.get(i + 1);
            int res = 0;
            if (op == '+') res = a + b;
            else res = a - b;
            numbers.set(i, res);
            numbers.remove(i + 1);
            operators.remove(i);
            steps.add("After " + a + " " + op + " " + b + " = " + res + ", numbers: " + numbers + ", operators: " + operators);
        }

        return numbers.get(0).toString();
    }

    static void parseExpression(String expr, List<Integer> numbers, List<Character> operators) {
        int n = expr.length();
        int i = 0;
        while (i < n) {
            if (expr.charAt(i) == ' ') {
                i++;
                continue;
            }
            int start = i;
            if (expr.charAt(i) == '-' && (i == 0 || !Character.isDigit(expr.charAt(i - 1)))) {
                i++; // handle negative number
            }
            while (i < n && Character.isDigit(expr.charAt(i))) {
                i++;
            }
            if (start != i) {
                int num = Integer.parseInt(expr.substring(start, i));
                numbers.add(num);
            }
            if (i < n) {
                char c = expr.charAt(i);
                if (c == '+' || c == '-' || c == '*' || c == '/') {
                    operators.add(c);
                    i++;
                }
            }
        }
    }
}
