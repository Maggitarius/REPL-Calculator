package calculator;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {

    public List<String> tokenize(String expression, Double lastResult) {
        List<String> tokens = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        boolean insertedLastResult = false;

        expression = expression.stripLeading();

        if (startsWithBinaryOperatorWithSpace(expression) && lastResult != null) {
            tokens.add(lastResult.toString());
        }

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isWhitespace(c)) {
                continue;
            }

            if (c == '-' && isUnary(expression, i)) {
                tokens.add("neg");
                continue;
            }

            if (Character.isDigit(c) || c == '.') {
                builder.append(c);
                continue;
            }

            String remaining = expression.substring(i);
            String function = checkForFunction(remaining);
            if (function != null) {
                if (builder.length() > 0) {
                    tokens.add(builder.toString());
                    builder.setLength(0);
                }
                tokens.add(function);
                i += function.length() - 1; // Move index forward
                continue;
            }

            if (builder.length() > 0) {
                tokens.add(builder.toString());
                builder.setLength(0);
            }

            if (isOperator(c) || c == '(' || c == ')') {
                tokens.add(String.valueOf(c));
            } else {
                throw new IllegalArgumentException("Illegal character: " + c);
            }
        }

        if (builder.length() > 0) {
            tokens.add(builder.toString());
        }

        return tokens;
    }

    private boolean isUnary(String expression, int i) {
        char prev = lastNonSpace(expression, i);
        return i == 0 || prev == '(' || isOperator(prev);
    }


    private boolean isOperator(char ch) {
        return "+-*/^".indexOf(ch) != -1;
    }

    private char lastNonSpace(String expr, int currentIndex) {
        for (int j = currentIndex - 1; j >= 0; j--) {
            if (!Character.isWhitespace(expr.charAt(j))) {
                return expr.charAt(j);
            }
        }
        return '\0';
    }

    private boolean startsWithBinaryOperatorWithSpace(String expr) {
        expr = expr.stripLeading();
        return expr.length() >= 2 &&
                "+-*/^".indexOf(expr.charAt(0)) != -1 &&
                Character.isWhitespace(expr.charAt(1));
    }

    private String checkForFunction(String expr) {
        String[] functions = {"cos", "sin", "tan", "sqrt"};
        for (String func : functions) {
            if (expr.startsWith(func) && !Character.isLetterOrDigit(expr.charAt(func.length()))) {
                return func;
            }
        }
        return null;
    }
}
