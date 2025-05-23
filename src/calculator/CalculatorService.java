package calculator;

import calculator.history.HistoryManager;

public class CalculatorService {

    private final Tokenizer tokenizer;
    private final RpnConverter rpnConverter;
    private final Evaluator evaluator;
    private final HistoryManager history;

    public CalculatorService(Tokenizer tokenizer, RpnConverter rpnConverter, Evaluator evaluator, HistoryManager history) {
        this.tokenizer = tokenizer;
        this.rpnConverter = rpnConverter;
        this.evaluator = evaluator;
        this.history = history;
    }

    public double evaluate(String input) {
        var tokens = tokenizer.tokenize(input, history.getLastResult());
        var rpn = rpnConverter.convert(tokens);
        double result = evaluator.evaluate(rpn);
        history.setLastResult(result);
        history.addRecord(input + " = " + result);
        return result;
    }

    public void printHistory() {
        var records = history.getHistory();
        if (records.isEmpty()) {
            System.out.println("No calculator.history yet.");
        } else {
            System.out.println("Calculation history:");
            for (String record : records) {
                System.out.println(record);
            }
        }
    }

    public void clearHistory() {
        history.clearHistory();
    }

    public void clearLastResult() {
        history.clearLastResult();
    }
}
