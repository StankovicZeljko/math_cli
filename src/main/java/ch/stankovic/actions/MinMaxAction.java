package ch.stankovic.actions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.List;
import java.util.Map;

public final class MinMaxAction implements Action {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(SerializationFeature.INDENT_OUTPUT, false)  // Keine Zeilenumbrüche
            .configure(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED, false)
            .configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);  // Ordnung erzwingen


    @Override
    public String execute(List<Double> numbers, String format) {
        if (!format.equals("csv") && !format.equals("json")) {
            throw new IllegalArgumentException("Unsupported format: " + format);
        }
        double min = numbers.stream().mapToDouble(Double::doubleValue).min().orElse(Double.NaN);
        double max = numbers.stream().mapToDouble(Double::doubleValue).max().orElse(Double.NaN);

        if ("csv".equals(format)) {
            return "%s,%s".formatted(min, max);
        } else {
            try {
                Map<String, Double> result = Map.of("min", min, "max", max);
                return objectMapper.writeValueAsString(result);
            } catch (Exception e) {
                throw new RuntimeException("Failed to serialize JSON", e);
            }
        }
    }
}