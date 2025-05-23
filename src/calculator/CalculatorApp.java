package calculator;

import calculator.history.InMemoryHistoryManager;

import java.util.Scanner;

public class CalculatorApp {

    public static void main(String[] args) {
        System.out.println("Type expressions to evaluate or one of the commands: calculator.history, clear, exit, reset");

        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        Tokenizer tokenizer = new Tokenizer();
        RpnConverter rpnConverter = new RpnConverter();
        Evaluator evaluator = new Evaluator();

        CalculatorService calculator = new CalculatorService(tokenizer, rpnConverter, evaluator, historyManager);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("\n> ");
            String input = scanner.nextLine().trim();

            switch (input.toLowerCase()) {
                case "exit":
                    System.out.println("Goodbye!");
                    return;
                case "history":
                    calculator.printHistory();
                    break;
                case "clear":
                    calculator.clearHistory();
                    System.out.println("History cleared.");
                    break;
                case "":
                    break;
                case "reset":
                    calculator.clearLastResult();
                    System.out.println("Last result cleared.");
                    break;
                default:
                    try {
                        double result = calculator.evaluate(input);
                        System.out.println("= " + result);
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
            }
        }
    }
}
