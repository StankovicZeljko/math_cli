package ch.stankovic;

import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class NumberProcessorCLITest {

    @Test
    void cliTestSumActionWithValidStdin() {
        // Simulierte Eingabe über stdin
        String input = "1.0,2.0,3.0";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Ausgabe abfangen
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        // CLI ausführen
        String[] args = {"-a", "sum"};
        NumberProcessorCLI cli = new NumberProcessorCLI();
        CommandLine cmd = new CommandLine(cli);
        int exitCode = cmd.execute(args);

        // Überprüfen
        assertEquals(0, exitCode, "CLI: Exit code should be 0 for successful execution");
        assertEquals("6.0", output.toString().trim(), "CLI: Output should be the sum of the input numbers");
    }

    @Test
    void cliTestMinMaxActionWithValidStdin() {
        // Simulierte Eingabe über stdin
        String input = "1.0,5.0,3.0";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Ausgabe abfangen
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        // CLI ausführen
        String[] args = {"-a", "minMax"};
        NumberProcessorCLI cli = new NumberProcessorCLI();
        CommandLine cmd = new CommandLine(cli);
        int exitCode = cmd.execute(args);

        // Überprüfen
        assertEquals(0, exitCode, "CLI: Exit code should be 0 for successful execution");
        assertEquals("1.0,5.0", output.toString().trim(), "CLI: Output should be the min and max of the input numbers");
    }

    @Test
    void cliTestLt4ActionWithValidStdin() {
        // Simulierte Eingabe über stdin
        String input = "1.0,5.0,3.0,2.0";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Ausgabe abfangen
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        // CLI ausführen
        String[] args = {"-a", "lt4"};
        NumberProcessorCLI cli = new NumberProcessorCLI();
        CommandLine cmd = new CommandLine(cli);
        int exitCode = cmd.execute(args);

        // Überprüfen
        assertEquals(0, exitCode, "CLI: Exit code should be 0 for successful execution");
        assertEquals("1.0,3.0,2.0", output.toString().trim(), "CLI: Output should be the filtered numbers less than 4");
    }

    @Test
    void cliTestEmptyInput() {
        // Simulierte leere Eingabe über stdin
        String input = "";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Fehlerausgabe abfangen
        ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errorStream));

        // CLI ausführen
        String[] args = {"-a", "sum"};
        NumberProcessorCLI cli = new NumberProcessorCLI();
        CommandLine cmd = new CommandLine(cli);
        int exitCode = cmd.execute(args);

        // Assertions
        assertEquals(1, exitCode, "CLI: Exit code should be 1 for empty input");
        String errorOutput = errorStream.toString().trim();
        assertEquals("Error: Input is empty", errorOutput, "CLI: Expected error message for empty input");
    }


    @Test
    void cliTestInvalidAction() {
        // Simulierte Eingabe über stdin
        String input = "1.0,2.0,3.0";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Fehlerausgabe abfangen
        ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errorStream));

        // CLI ausführen
        String[] args = {"-a", "invalidAction"};
        NumberProcessorCLI cli = new NumberProcessorCLI();
        CommandLine cmd = new CommandLine(cli);
        int exitCode = cmd.execute(args);

        // Assertions
        assertEquals(1, exitCode, "CLI: Exit code should be 1 for invalid action");
        String errorOutput = errorStream.toString().trim();
        assertTrue(
                errorOutput.startsWith("Invalid action: invalidAction."),
                "CLI: Expected error message for invalid action"
        );
        assertTrue(
                errorOutput.contains("Valid actions are: [sum, minMax, lt4]"),
                "CLI: Expected list of valid actions in error message"
        );
    }


}
