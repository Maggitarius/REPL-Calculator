package calculator;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {

    public List<String> tokenize(String expression, Double lastResult) {
        List<String> tokens = new ArrayList<>();
        StringBuilder builder = new StringBuilder();

        expression = expression.stripLeading();

        if (lastResult != null && expression.startsWith("- ")) {
            tokens.add(lastResult.toString());
            tokens.add("-");
            expression = expression.substring(2).stripLeading();
        }

        if (startsWithBinaryOperatorWithSpace(expression) && lastResult != null) {
            tokens.add(lastResult.toString());
        }

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            // Проверка на пробел
            if (Character.isWhitespace(c)) {
                continue;
            }

            //Проверка на функцию
            String remaining = expression.substring(i);
            String function = checkForFunction(remaining);
            if (function != null) {
                tokens.add(function);

                boolean hasParen = remaining.length() > function.length() &&
                        remaining.charAt(function.length()) == '(';

                if (!hasParen && lastResult != null) {
                    tokens.add(lastResult.toString());
                }

                i += function.length() - 1;
                continue;
            }

            // Проверка на унарный минус
            if (c == '-' && isUnary(expression, i)) {
                tokens.add("neg");
                continue;
            }

            // Проверка на число
            if (Character.isDigit(c) || c == '.') {
                builder.append(c);
                continue;
            }

            // Закрытие накопленного числа
            if (!builder.isEmpty()) {
                tokens.add(builder.toString());
                builder.setLength(0);
            }

            // Проверка на скобки и операторы
            if (isOperator(c) || c == '(' || c == ')') {
                tokens.add(String.valueOf(c));
            } else {
                throw new IllegalArgumentException("Illegal character: " + c);
            }
        }

        if (!builder.isEmpty()) {
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
            if (expr.startsWith(func)) {
                if (expr.length() == func.length() || !Character.isLetterOrDigit(expr.charAt(func.length()))) {
                    return func;
                }
            }
        }
        return null;
    }

}
