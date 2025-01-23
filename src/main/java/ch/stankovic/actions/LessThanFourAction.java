package ch.stankovic.actions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.List;

public final class LessThanFourAction implements Action {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(SerializationFeature.INDENT_OUTPUT, false)  // Keine Zeilenumbrüche
            .configure(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED, false)
            .configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);  // Ordnung erzwingen


    @Override
    public String execute(List<Double> numbers, String format) {
        if (!format.equals("csv") && !format.equals("json")) {
            throw new IllegalArgumentException("Unsupported format: " + format);
        }
        List<Double> filtered = numbers.stream().filter(n -> n < 4).toList();

        if ("csv".equals(format)) {
            return String.join(",", filtered.stream().map(String::valueOf).toList());
        } else {
            try {
                return objectMapper.writeValueAsString(filtered);
            } catch (Exception e) {
                throw new RuntimeException("Failed to serialize JSON", e);
            }
        }
    }
}
