package ch.stankovic.input;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FileInputHandler implements InputHandler {

    private final String filePath;
    private final String format;

    public FileInputHandler(String filePath, String format) {
        this.filePath = filePath;
        this.format = format;
    }

    @Override
    public List<Double> readNumbers() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String content = reader.readLine().trim();

            if (format.equalsIgnoreCase("csv")) {
                return Arrays.stream(content.split(","))
                        .map(String::trim)
                        .map(Double::parseDouble)
                        .toList();
            } else if (format.equalsIgnoreCase("json")) {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(content, new TypeReference<>() {
                });
            } else {
                throw new IllegalArgumentException("Unsupported input format: " + format);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("File contains invalid numbers", e);
        }
    }
}
