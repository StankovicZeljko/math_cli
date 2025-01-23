package ch.stankovic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class NumberProcessorCLITest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private String executeCLI(String input, String[] args, int expectedExitCode) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        System.setErr(new PrintStream(out));

        NumberProcessorCLI cli = new NumberProcessorCLI();
        CommandLine cmd = new CommandLine(cli);
        int actualExitCode = cmd.execute(args);

        assertEquals(expectedExitCode, actualExitCode, "CLI: Exit code should match the expected value");

        String result = out.toString().trim();
        return result.replaceAll("\\[.*?] INFO .*", "").trim(); // Entferne Logs
    }


    @Test
    void testSumWithCsvInputAndCsvOutput() {
        String input = "1.0,2.0,3.0";
        String[] args = {"-i", "-", "-a", "sum"};
        String output = executeCLI(input, args, 0);
        assertEquals("6.0", output.trim(), "Expected sum in CSV format");
    }

    @Test
    void testSumWithCsvInputAndJsonOutput() {
        String input = "1.0,2.0,3.0";
        String[] args = {"-i", "-", "-a", "sum", "-f", "csv", "-F", "json"};
        String output = executeCLI(input, args, 0);

        try {
            JsonNode json = objectMapper.readTree(output);
            assertEquals(6.0, json.get("sum").asDouble(), "Output JSON should contain the correct sum");
        } catch (Exception e) {
            fail("Output is not valid JSON");
        }
    }

    @Test
    void testInvalidJsonInput() {
        String input = "[1.0, 2.0, abc]";
        String[] args = {"-i", "-", "-a", "sum", "-f", "json"};
        String output = executeCLI(input, args, 4);
        assertTrue(output.contains("Unexpected error"), "Expected error message for invalid JSON input");
    }

    @Test
    void testMissingActionArgument() {
        String input = "1.0,2.0,3.0";
        String[] args = {"-i", "-"};
        String output = executeCLI(input, args, 2);
        assertTrue(output.contains("Missing required option"), "Expected error message for missing action argument");
    }

    @Test
    void testInvalidOutputFormat() {
        String input = "1.0,2.0,3.0";
        String[] args = {"-i", "-", "-a", "sum", "-F", "xml"};
        String output = executeCLI(input, args, 4);
        assertTrue(output.contains("Invalid output format"), "Expected error message for invalid output format");
    }
}
