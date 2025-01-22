package ch.stankovic.input;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StdinInputHandlerTest {

    @Test
    void testReadNumbers_ValidInput() throws IOException {
        // Simulierte Eingabe
        String simulatedInput = "1.0,2.5,3.7";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Teste StdinInputHandler
        InputHandler inputHandler = new StdinInputHandler();
        List<Double> numbers = inputHandler.readNumbers();

        // Erwartete Ausgabe
        List<Double> expectedNumbers = List.of(1.0, 2.5, 3.7);

        // Assertions
        assertEquals(expectedNumbers, numbers, "The numbers read from stdin do not match the expected output.");
    }

    @Test
    void testReadNumbers_EmptyInput() throws IOException {
        // Simulierte leere Eingabe
        String simulatedInput = "";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Teste StdinInputHandler
        InputHandler inputHandler = new StdinInputHandler();
        List<Double> numbers = inputHandler.readNumbers();

        // Assertions
        assertTrue(numbers.isEmpty(), "The result should be an empty list for empty input.");
    }

    @Test
    void testReadNumbers_InvalidNumbers() {
        // Simulierte ungültige Eingabe
        String simulatedInput = "1.0,abc,3.7";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Teste StdinInputHandler
        InputHandler inputHandler = new StdinInputHandler();

        // Assertions: IOException wird erwartet
        assertThrows(NumberFormatException.class, inputHandler::readNumbers, "Expected a NumberFormatException for invalid input.");
    }

}
