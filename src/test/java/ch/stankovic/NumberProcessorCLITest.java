package ch.stankovic;

import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class NumberProcessorCLITest {

    @Test
    void testValidArguments() {
        // Capture console output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            // Simulate CLI arguments
            String[] args = {"-i", "stdin", "-o", "stdout", "-a", "sum"};
            NumberProcessorCLI cli = new NumberProcessorCLI();
            CommandLine cmd = new CommandLine(cli);
            int exitCode = cmd.execute(args);

            // Assertions
            assertEquals(0, exitCode, "Expected exit code to be 0");
            assertEquals("stdin", cli.input, "Input argument not set correctly");
            assertEquals("stdout", cli.output, "Output argument not set correctly");
            assertEquals("sum", cli.action, "Action argument not set correctly");

            // Validate console output
            String expectedOutput = "Input: stdin, Output: stdout, Action: sum";
            String actualOutput = outputStream.toString().trim();
            assertTrue(actualOutput.contains(expectedOutput),
                    "Console output does not match expected output");
        } finally {
            // Restore original System.out
            System.setOut(originalOut);
        }
    }

    @Test
    void testMissingRequiredArguments() {
        // Simulate missing arguments
        String[] args = {"-i", "stdin"};
        NumberProcessorCLI cli = new NumberProcessorCLI();
        CommandLine cmd = new CommandLine(cli);

        int exitCode = cmd.execute(args);

        // Assertions
        assertNotEquals(0, exitCode, "Expected non-zero exit code for missing arguments");
    }

    @Test
    void testInvalidActionArgument() {
        // Simulate invalid action
        String[] args = {"-i", "stdin", "-o", "stdout", "-a", "invalidAction"};
        NumberProcessorCLI cli = new NumberProcessorCLI();
        CommandLine cmd = new CommandLine(cli);

        int exitCode = cmd.execute(args);

        // Assertions
        assertNotEquals(0, exitCode, "Expected non-zero exit code for invalid action");
    }

}
