package ch.stankovic.output;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileOutputHandlerTest {

    private final String testFilePath = "test_output.txt";

    @AfterEach
    void cleanup() {
        // Delete the test file after each test
        File testFile = new File(testFilePath);
        if (testFile.exists()) {
            assertTrue(testFile.delete(), "Failed to delete test file");
        }
    }

    @Test
    void testWriteResultCsvFormatPositive() throws IOException {
        FileOutputHandler handler = new FileOutputHandler(testFilePath, "csv");
        String result = "1.0,2.0,3.0";

        // Act
        handler.writeResult(result);

        // Assert
        try (BufferedReader reader = new BufferedReader(new FileReader(testFilePath))) {
            String line = reader.readLine();
            assertEquals("1.0,2.0,3.0", line, "File content should match the written result");
        }
    }

    @Test
    void testWriteResultJsonFormatPositive() throws IOException {
        FileOutputHandler handler = new FileOutputHandler(testFilePath, "json");
        String result = "{\"sum\": 6.0}";

        // Act
        handler.writeResult(result);

        // Assert
        try (BufferedReader reader = new BufferedReader(new FileReader(testFilePath))) {
            String line = reader.readLine();
            assertEquals("{\"sum\": 6.0}", line, "File content should match the written JSON result");
        }
    }

    @Test
    void testWriteResultNullResult() {
        FileOutputHandler handler = new FileOutputHandler(testFilePath, "csv");

        // Assert
        NullPointerException exception = assertThrows(NullPointerException.class, () -> handler.writeResult(null));
        assertEquals("Output result cannot be null", exception.getMessage());
    }

    @Test
    void testUnsupportedFormat() {
        // Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new FileOutputHandler(testFilePath, "xml"));
        assertEquals("Unsupported output format: xml", exception.getMessage());
    }

    @Test
    void testGetFormat() {
        FileOutputHandler handler = new FileOutputHandler(testFilePath, "csv");

        // Assert
        assertEquals("csv", handler.getFormat(), "Format should be 'csv'");
    }

    @Test
    void testFileCreation() throws IOException {
        FileOutputHandler handler = new FileOutputHandler(testFilePath, "csv");
        String result = "test data";

        // Act
        handler.writeResult(result);

        // Assert
        File testFile = new File(testFilePath);
        assertTrue(testFile.exists(), "File should be created");
        assertTrue(testFile.length() > 0, "File should not be empty");
    }
}
