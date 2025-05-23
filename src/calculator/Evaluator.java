package calculator;

import java.util.List;
import java.util.Stack;

public class Evaluator {

    public double evaluate(List<String> rpnTokens) {
        Stack<Double> stack = new Stack<>();

        for (String token : rpnTokens) {
            if (isNumber(token)) {
                stack.push(Double.parseDouble(token));
            } else if (token.equals("neg")) {
                if (stack.isEmpty()) throw new RuntimeException("No operand for unary minus");
                stack.push(-stack.pop());
            } else {
                Operators op = Operators.fromToken(token);
                if (op != null && stack.size() >= 2) {
                    double b = stack.pop();
                    double a = stack.pop();
                    stack.push(op.apply(a, b));
                } else {
                    throw new IllegalArgumentException("Invalid operator or insufficient operands: " + token);
                }
            }
        }

        if (stack.size() != 1) throw new RuntimeException("Invalid expression");

        return stack.pop();
    }

    private boolean isNumber(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
