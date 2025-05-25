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
                if (stack.isEmpty()) {
                    throw new RuntimeException("Unary minus ('neg') without operand");
                }
                stack.push(-stack.pop());
            } else {
                Operators op = Operators.fromToken(token);
                if (op != null) {
                    if (isUnaryOperator(op) && !stack.isEmpty()) {
                        double b = stack.pop();
                        stack.push(op.apply(0, b));
                    }
                    else if (stack.size() >= 2) {
                        double b = stack.pop();
                        double a = stack.pop();
                        stack.push(op.apply(a, b));
                    }
                } else {
                    throw new IllegalArgumentException("Operator '" + token + "' has wrong number of operands");
                }
            }
        }

        if (stack.size() != 1) {
            throw new RuntimeException("Invalid expression. Final stack size: " + stack.size());
        }

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

    private boolean isUnaryOperator(Operators op) {
        return op == Operators.NEG || op == Operators.COS ||
                op == Operators.SIN || op == Operators.TAN || op == Operators.SQRT;
    }
}
