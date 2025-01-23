package ch.stankovic.actions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class SumAction implements Action {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(SerializationFeature.INDENT_OUTPUT, false)  // Keine Zeilenumbrüche
            .configure(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED, false)
            .configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);  // Ordnung erzwingen


    @Override
    public String execute(List<Double> numbers, String format) {
        if (!"csv".equals(format) && !"json".equals(format)) {
            throw new IllegalArgumentException("Unsupported format: " + format);
        }

        double sum = numbers.stream().mapToDouble(Double::doubleValue).sum();

        if ("csv".equals(format)) {
            return String.valueOf(sum);
        } else {
            Map<String, Double> result = new HashMap<>();
            result.put("sum", sum);
            try {
                return objectMapper.writeValueAsString(result);
            } catch (Exception e) {
                throw new RuntimeException("Failed to generate JSON output", e);
            }
        }
    }
}
