package calculator;

import java.util.function.BiFunction;

public enum Operators {

    NEG("neg", 4, Associativity.RIGHT, (a, b) -> -b),
    SUM("+", 1, Associativity.LEFT, (a, b) -> a + b),
    SUB("-", 1, Associativity.LEFT, (a, b) -> a - b),
    MUL("*", 2, Associativity.LEFT, (a, b) -> a * b),
    DIV("/", 2, Associativity.LEFT, (a, b) -> {
        if (b == 0) throw new ArithmeticException("Division by zero");
        return a / b;
    }),
    POW("^", 3, Associativity.RIGHT, (a, b) -> Math.pow(a, b)),
    COS("cos", 3, Associativity.LEFT, (a,b) -> Math.cos(b)),
    SIN("sin", 3, Associativity.LEFT, (a,b) -> Math.sin(b)),
    TAN("tan", 3, Associativity.LEFT, (a, b) -> Math.tan(b)),
    SQRT("sqrt", 3, Associativity.RIGHT, (a, b) -> Math.sqrt(b));


    public final String symbol;
    public final int precedence;
    public final Associativity associativity;
    private final BiFunction<Double, Double, Double> operation;

    Operators(String symbol, int precedence, Associativity associativity, BiFunction<Double, Double, Double> operation) {
        this.symbol = symbol;
        this.precedence = precedence;
        this.associativity = associativity;
        this.operation = operation;
    }

    public double apply(double a, double b) {
        return operation.apply(a, b);
    }

    public static Operators fromToken(String token) {
        for (Operators op : values()) {
            if (op.symbol.equals(token)) {
                return op;
            }
        }
        return null;
    }

    public enum Associativity {
        LEFT, RIGHT
    }
}