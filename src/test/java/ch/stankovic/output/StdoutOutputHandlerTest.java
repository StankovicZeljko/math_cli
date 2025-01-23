package ch.stankovic.output;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;

import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class StdoutOutputHandlerTest {

    @Test
    void testWriteResultCsvFormatPositive() {
        // Capture the console output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        StdoutOutputHandler handler = new StdoutOutputHandler("csv");
        String result = "1.0,2.0,3.0";

        // Act
        handler.writeResult(result);

        // Assert
        assertEquals("1.0,2.0,3.0\n\n", outputStream.toString(), "Output should match the input result with newline");
    }

    @Test
    void testWriteResultJsonFormatPositive() {
        // Capture the console output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        StdoutOutputHandler handler = new StdoutOutputHandler("json");
        String result = "{\"sum\": 6.0}";

        // Act
        handler.writeResult(result);

        // Assert
        assertEquals("{\"sum\": 6.0}\n\n", outputStream.toString(), "Output should match the JSON result with newline");
    }

    @Test
    void testWriteResultNullResult() {
        StdoutOutputHandler handler = new StdoutOutputHandler("csv");

        // Assert
        NullPointerException exception = assertThrows(NullPointerException.class, () -> handler.writeResult(null));
        assertEquals("Output result cannot be null", exception.getMessage());
    }

    @Test
    void testUnsupportedFormat() {
        // Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new StdoutOutputHandler("xml"));
        assertEquals("Unsupported output format: xml", exception.getMessage());
    }

    @Test
    void testGetFormat() {
        StdoutOutputHandler handler = new StdoutOutputHandler("csv");

        // Assert
        assertEquals("csv", handler.getFormat(), "Format should be 'csv'");
    }

}
