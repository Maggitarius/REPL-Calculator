package calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static calculator.Operators.Associativity.LEFT;
import static calculator.Operators.Associativity.RIGHT;

public class RpnConverter {

    public List<String> convert(List<String> tokens) {
        List<String> output = new ArrayList<>();
        Stack<String> operators = new Stack<>();

        for (String token : tokens) {
            if (isNumber(token)) {
                output.add(token);
            } else if (token.equals("(")) {
                operators.push(token);
            } else if (token.equals(")")) {
                while (!operators.isEmpty() && !operators.peek().equals("(")) {
                    output.add(operators.pop());
                }
                if (!operators.isEmpty() && operators.peek().equals("(")) {
                    operators.pop();
                } else {
                    throw new RuntimeException("Mismatched parentheses");
                }
            } else if (token.equals("neg")) {
                operators.push(token);
            } else {
                Operators op1 = Operators.fromToken(token);
                while (!operators.isEmpty() && Operators.fromToken(operators.peek()) != null) {
                    Operators op2 = Operators.fromToken(operators.peek());
                    if ((op1.associativity == LEFT && op1.precedence <= op2.precedence) ||
                            (op1.associativity == RIGHT && op1.precedence < op2.precedence)) {
                        output.add(operators.pop());
                    } else {
                        break;
                    }
                }
                operators.push(token);
            }
        }

        while (!operators.isEmpty()) {
            String op = operators.pop();
            if (op.equals("(") || op.equals(")")) {
                throw new RuntimeException("Mismatched parentheses");
            }
            output.add(op);
        }

        return output;
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
