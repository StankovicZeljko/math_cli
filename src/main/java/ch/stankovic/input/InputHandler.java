package ch.stankovic.input;

import java.io.IOException;
import java.util.List;

public interface InputHandler {
    List<Double> readNumbers() throws IOException;
}
