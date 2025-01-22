package ch.stankovic.input;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class StdinInputHandler implements InputHandler{
    @Override
    public List<Double> readNumbers() throws IOException{
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String content = reader.lines().collect(Collectors.joining(",")).trim();
            if (content.isEmpty()) {
                return Collections.emptyList();
            }
            return Arrays.stream(content.split(","))
                    .map(String::trim)
                    .map(Double::parseDouble)
                    .collect(Collectors.toList());
        }
    }
}
