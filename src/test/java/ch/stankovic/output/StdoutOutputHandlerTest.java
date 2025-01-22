package ch.stankovic.output;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class StdoutOutputHandlerTest {

    @Test
    void testWriteResult_ValidOutput() throws IOException {
        // Simulierte Ausgabe
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Teste StdoutOutputHandler
        OutputHandler outputHandler = new StdoutOutputHandler();
        String simulatedResult = "1.0,2.5,3.7";
        outputHandler.writeResult(simulatedResult);

        // Erwartete Ausgabe
        String expectedOutput = "1.0,2.5,3.7";

        // Assertions
        assertEquals(expectedOutput.trim(), outputStream.toString().trim(), "The output written to stdout does not match the expected output.");
    }

    @Test
    void testWriteResult_EmptyOutput() throws IOException {
        // Simulierte leere Ausgabe
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Teste StdoutOutputHandler
        OutputHandler outputHandler = new StdoutOutputHandler();
        outputHandler.writeResult("");

        // Erwartete Ausgabe
        String expectedOutput = "";

        // Assertions
        assertEquals(expectedOutput.trim(), outputStream.toString().trim(), "The output should be empty.");
    }

    @Test
    void testWriteResult_NullOutput() {
        // Teste StdoutOutputHandler
        OutputHandler outputHandler = new StdoutOutputHandler();

        // Erwartung: NullPointerException wird geworfen
        assertThrows(NullPointerException.class, () -> outputHandler.writeResult(null), "Expected a NullPointerException for null output");
    }

}
