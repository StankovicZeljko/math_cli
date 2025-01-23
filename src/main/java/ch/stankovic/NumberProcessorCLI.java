package ch.stankovic;

import ch.stankovic.actions.Action;
import ch.stankovic.actions.LessThanFourAction;
import ch.stankovic.actions.MinMaxAction;
import ch.stankovic.actions.SumAction;
import ch.stankovic.input.FileInputHandler;
import ch.stankovic.input.InputHandler;
import ch.stankovic.input.StdinInputHandler;
import ch.stankovic.output.FileOutputHandler;
import ch.stankovic.output.OutputHandler;
import ch.stankovic.output.StdoutOutputHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(NumberProcessorCLI.class);

    @Option(names = {"-i", "--input"}, description = "Input source (stdin, file)", required = true)
    String input;

    @Option(names = {"-o", "--output"}, description = "Output destination (stdout, file)")
    String output;

    @Option(names = {"-f", "--input-format"}, description = "Input format (csv, json)", defaultValue = "csv")
    String inputFormat;

    @Option(names = {"-F", "--output-format"}, description = "Output format (csv, json)", defaultValue = "csv")
    String outputFormat;

    @Option(names = {"-a", "--action"}, description = "Action to perform (sum, minMax, lt4)", required = true)
    String action;

    private static final List<String> VALID_ACTIONS = List.of("sum", "minMax", "lt4");
    private static final List<String> VALID_FORMATS = List.of("csv", "json");

    @Override
    public Integer call() {
        logger.info("Starting NumberProcessorCLI with arguments: input={}, output={}, inputFormat={}, outputFormat={}, action={}",
                input, output, inputFormat, outputFormat, action);

        try {
            validateInputs();

            InputHandler inputHandler = createInputHandler();
            List<Double> numbers = inputHandler.readNumbers();

            if (numbers.isEmpty()) {
                System.err.println("Input is empty");
                return 4; // Validation error
            }

            Action actionProcessor = createActionProcessor();
            String result = actionProcessor.execute(numbers, outputFormat);

            OutputHandler outputHandler = createOutputHandler();
            outputHandler.writeResult(result);

            return 0; // Success
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return 4; // Validation error
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            return 2; // General error
        }
    }

    private void validateInputs() {
        if (!VALID_ACTIONS.contains(action)) {
            throw new IllegalArgumentException("Invalid action: " + action + ". Valid actions are: " + VALID_ACTIONS);
        }
        if (!VALID_FORMATS.contains(inputFormat)) {
            throw new IllegalArgumentException("Invalid input format: " + inputFormat + ". Valid formats are: " + VALID_FORMATS);
        }
        if (!VALID_FORMATS.contains(outputFormat)) {
            throw new IllegalArgumentException("Invalid output format: " + outputFormat + ". Valid formats are: " + VALID_FORMATS);
        }
    }

    private InputHandler createInputHandler() {
        if (input.equals("-")) {
            logger.info("Using stdin for input with format '{}'", inputFormat);
            return new StdinInputHandler(inputFormat);
        } else {
            logger.info("Using file '{}' for input in format '{}'", input, inputFormat);
            return new FileInputHandler(input, inputFormat);
        }
    }

    private OutputHandler createOutputHandler() {
        if (output == null || output.equals("-")) {
            logger.info("Using stdout for output in format '{}'", outputFormat);
            return new StdoutOutputHandler(outputFormat);
        } else {
            logger.info("Using file '{}' for output in format '{}'", output, outputFormat);
            return new FileOutputHandler(output, outputFormat);
        }
    }

    private Action createActionProcessor() {
        return switch (action) {
            case "sum" -> new SumAction();
            case "minMax" -> new MinMaxAction();
            case "lt4" -> new LessThanFourAction();
            default -> throw new IllegalArgumentException("Unexpected action: " + action);
        };
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new NumberProcessorCLI()).execute(args);
        System.exit(exitCode);
    }
}
