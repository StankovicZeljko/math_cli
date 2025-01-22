package ch.stankovic.actions;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MinMaxActionTest {

    @Test
    void testExecuteCsv() {
        MinMaxAction action = new MinMaxAction();
        List<Double> numbers = List.of(1.0, 5.0, 3.0);
        String result = action.execute(numbers, "csv");
        assertEquals("1.0,5.0", result, "MinMaxAction CSV output is incorrect");
    }

    @Test
    void testExecuteJson() {
        MinMaxAction action = new MinMaxAction();
        List<Double> numbers = List.of(1.0, 5.0, 3.0);
        String result = action.execute(numbers, "json");
        assertEquals("{\"min\": 1.0, \"max\": 5.0}", result, "MinMaxAction JSON output is incorrect");
    }

    @Test
    void testExecuteEmptyList() {
        MinMaxAction action = new MinMaxAction();
        List<Double> numbers = List.of();
        String result = action.execute(numbers, "csv");
        assertEquals("NaN,NaN", result, "MinMaxAction should return NaN for an empty list");
    }

    @Test
    void testInvalidFormat() {
        MinMaxAction action = new MinMaxAction();
        List<Double> numbers = List.of(1.0, 5.0, 3.0);
        assertThrows(IllegalArgumentException.class, () -> action.execute(numbers, "xml"),
                "Expected an exception for unsupported format");
    }
}
