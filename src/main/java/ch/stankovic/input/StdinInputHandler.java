package ch.stankovic.input;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class StdinInputHandler implements InputHandler {

    private final String format;

    public StdinInputHandler(String format) {
        this.format = format;
    }

    @Override
    public List<Double> readNumbers() {
        try (Scanner scanner = new Scanner(System.in)) {
            if (!scanner.hasNextLine()) {
                return List.of(); // Leere Eingabe
            }
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                return List.of();
            }

            if ("csv".equals(format)) {
                return Arrays.stream(input.split(","))
                        .map(String::trim)
                        .map(Double::parseDouble)
                        .toList();
            } else if ("json".equals(format)) {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(input, new TypeReference<>() {
                });
            } else {
                throw new IllegalArgumentException("Unsupported input format: " + format);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Input contains invalid numbers", e);
        } catch (Exception e) {
            throw new IllegalArgumentException("Unexpected error occurred while reading input", e);
        }
    }
}
