package ch.stankovic.output;

import java.io.IOException;

public interface OutputHandler {
    void writeResult(String result) throws IOException;
}
