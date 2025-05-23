package calculator;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {

    public List<String> tokenize(String expression, Double lastResult) {
        List<String> tokens = new ArrayList<>();
        StringBuilder builder = new StringBuilder();

        if (startsWithOperator(expression) && lastResult != null) {
            tokens.add(0, lastResult.toString());
        }

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isWhitespace(c)) {
                continue;
            }

            if (Character.isDigit(c) || c == '.') {
                builder.append(c);
                continue;
            }

            if (builder.length() > 0) {
                tokens.add(builder.toString());
                builder.setLength(0);
            }

            if (c == '-') {
                char prev = lastNonSpace(expression, i);
                if (i == 0 || prev == '(' || isOperator(prev)) {
                    tokens.add("neg");
                    continue;
                }
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

    private boolean startsWithOperator(String expr) {
        char first = expr.trim().charAt(0);
        return "+-*/^".indexOf(first) != -1;
    }
}
