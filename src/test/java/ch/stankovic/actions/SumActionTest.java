package ch.stankovic.actions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SumActionTest {

    @Test
    void testExecuteCsv() {
        SumAction action = new SumAction();
        List<Double> numbers = List.of(1.0, 2.0, 3.0);
        String result = action.execute(numbers, "csv");
        assertEquals("6.0", result, "SumAction CSV output is incorrect");
    }

    @Test
    void testExecuteJson() {
        SumAction action = new SumAction();
        List<Double> numbers = List.of(1.0, 2.0, 3.0);
        String result = action.execute(numbers, "json");

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode expected = objectMapper.readTree("{\"sum\": 6.0}");
            JsonNode actual = objectMapper.readTree(result);
            assertEquals(expected, actual, "SumAction JSON output is incorrect");
        } catch (Exception e) {
            fail("JSON comparison failed", e);
        }
    }

    @Test
    void testExecuteEmptyList() {
        SumAction action = new SumAction();
        List<Double> numbers = List.of();
        String result = action.execute(numbers, "csv");
        assertEquals("0.0", result, "SumAction should return 0.0 for an empty list");
    }

    @Test
    void testInvalidFormat() {
        SumAction action = new SumAction();
        List<Double> numbers = List.of(1.0, 2.0, 3.0);
        assertThrows(IllegalArgumentException.class, () -> action.execute(numbers, "html"),
                "Expected an exception for unsupported format");
    }
}