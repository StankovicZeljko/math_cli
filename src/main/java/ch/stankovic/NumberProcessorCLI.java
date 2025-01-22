package ch.stankovic;

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
    @Option(names = {"-i", "--input"}, description = "Input source (stdin, file, URL)", required = true)
    String input;

    @Option(names = {"-o", "--output"}, description = "Output destination (stdout, file, URL)")
    String output;

    @Option(names = {"-a", "--action"}, description = "Action to perform (sum, minMax, lt4)", required = true)
    String action;

    private static final List<String> VALID_ACTIONS = List.of("sum", "minMax", "lt4");

    @Override
    public Integer call() {

        if (!VALID_ACTIONS.contains(action)) {
            System.err.printf("Invalid action: %s. Valid actions are: %s%n", action, VALID_ACTIONS);
            return 1; // Exit code for invalid action
        }

        System.out.printf("Input: %s, Output: %s, Action: %s%n", input, output, action);
        return 0;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new NumberProcessorCLI()).execute(args);
        System.exit(exitCode);
    }
}