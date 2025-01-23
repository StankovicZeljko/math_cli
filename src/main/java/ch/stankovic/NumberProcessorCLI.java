package ch.stankovic;

import ch.stankovic.actions.Action;
import ch.stankovic.actions.LessThanFourAction;
import ch.stankovic.actions.MinMaxAction;
import ch.stankovic.actions.SumAction;
import ch.stankovic.input.InputHandler;
import ch.stankovic.input.StdinInputHandler;
import ch.stankovic.output.OutputHandler;
import ch.stankovic.output.StdoutOutputHandler;
import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Command;

import java.util.List;
import java.util.concurrent.Callable;

@Command(
        name = "NumberProcessor",
        description = "Processes a list of floating-point numbers with various actions.",
        mixinStandardHelpOptions = true
)
public class NumberProcessorCLI implements Callable<Integer> {

    @Option(names = {"-a", "--action"}, description = "Action to perform (sum, minMax, lt4)", required = true)
    String action;

    private static final List<String> VALID_ACTIONS = List.of("sum", "minMax", "lt4");

    @Override
    public Integer call() {
        try {
            // Validierung der Aktion
            if (!VALID_ACTIONS.contains(action)) {
                System.err.printf("Invalid action: %s. Valid actions are: %s%n", action, VALID_ACTIONS);
                return 1; // Exit code for invalid action
            }

            // Zahlen aus stdin einlesen
            InputHandler inputHandler = new StdinInputHandler();
            List<Double> numbers = inputHandler.readNumbers();

            if (numbers.isEmpty()) {
                System.err.println("Error: Input is empty");
                return 1; // Exit code for empty input
            }

            // Aktion ausführen
            Action actionProcessor = switch (action) {
                case "sum" -> new SumAction();
                case "minMax" -> new MinMaxAction();
                case "lt4" -> new LessThanFourAction();
                default -> throw new IllegalStateException("Unexpected action: " + action);
            };
            String result = actionProcessor.execute(numbers, "csv");

            // Ergebnis auf stdout ausgeben
            OutputHandler outputHandler = new StdoutOutputHandler();
            outputHandler.writeResult(result);

            return 0; // Exit code for success
        } catch (Exception e) {
            System.err.printf("An error occurred: %s%n", e.getMessage());
            return 2; // Exit code for errors
        }
    }


    public static void main(String[] args) {
        int exitCode = new CommandLine(new NumberProcessorCLI()).execute(args);
        System.exit(exitCode);
    }
}