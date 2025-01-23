package ch.stankovic.actions;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LessThanFourActionTest {

    @Test
    void testExecuteCsv() {
        LessThanFourAction action = new LessThanFourAction();
        List<Double> numbers = List.of(1.0, 5.0, 3.0, 2.5);
        String result = action.execute(numbers, "csv");
        assertEquals("1.0,3.0,2.5", result, "LessThanFourAction CSV output is incorrect");
    }

    @Test
    void testExecuteJson() {
        LessThanFourAction action = new LessThanFourAction();
        List<Double> numbers = List.of(1.0, 5.0, 3.0, 2.5);
        String result = action.execute(numbers, "json");
        assertEquals("[1.0, 3.0, 2.5]", result, "LessThanFourAction JSON output is incorrect");
    }

    @Test
    void testExecuteEmptyList() {
        LessThanFourAction action = new LessThanFourAction();
        List<Double> numbers = List.of();
        String result = action.execute(numbers, "csv");
        assertEquals("", result, "LessThanFourAction should return an empty string for an empty list");
    }

    @Test
    void testInvalidFormat() {
        LessThanFourAction action = new LessThanFourAction();
        List<Double> numbers = List.of(1.0, 2.0);
        assertThrows(IllegalArgumentException.class, () -> action.execute(numbers, "yaml"),
                "Expected an exception for unsupported format");
    }
}