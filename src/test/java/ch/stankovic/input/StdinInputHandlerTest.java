package ch.stankovic.input;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StdinInputHandlerTest {

    @Test
    void testReadNumbers_ValidCsvInput() throws IOException {
        System.setIn(new ByteArrayInputStream("1.0,2.5,3.7".getBytes()));
        InputHandler inputHandler = new StdinInputHandler("csv");
        List<Double> numbers = inputHandler.readNumbers();
        assertEquals(List.of(1.0, 2.5, 3.7), numbers);
    }

    @Test
    void testReadNumbers_ValidJsonInput() throws IOException {
        System.setIn(new ByteArrayInputStream("[1.0, 2.5, 3.7]".getBytes()));
        InputHandler inputHandler = new StdinInputHandler("json");
        List<Double> numbers = inputHandler.readNumbers();
        assertEquals(List.of(1.0, 2.5, 3.7), numbers);
    }

    @Test
    void testReadNumbers_EmptyInput() throws IOException {
        System.setIn(new ByteArrayInputStream("".getBytes()));
        InputHandler inputHandler = new StdinInputHandler("csv");
        List<Double> numbers = inputHandler.readNumbers();
        assertTrue(numbers.isEmpty(), "Empty input should return an empty list");
    }

    @Test
    void testReadNumbers_InvalidJsonInput() {
        System.setIn(new ByteArrayInputStream("[1.0, abc, 3.7]".getBytes()));
        InputHandler inputHandler = new StdinInputHandler("json");
        assertThrows(IllegalArgumentException.class, inputHandler::readNumbers, "Invalid JSON should throw an exception");
    }

    @Test
    void testUnsupportedFormat() {
        System.setIn(new ByteArrayInputStream("1.0,2.5,3.7".getBytes()));
        InputHandler inputHandler = new StdinInputHandler("xml");
        assertThrows(IllegalArgumentException.class, inputHandler::readNumbers, "Unsupported format should throw an exception");
    }
}
