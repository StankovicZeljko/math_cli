package ch.stankovic.output;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileOutputHandler implements OutputHandler{
    private final String filePath;
    private final String format;

    public FileOutputHandler(String filePath, String format) {
        if (!format.equals("csv") && !format.equals("json")) {
            throw new IllegalArgumentException("Unsupported output format: " + format);
        }
        this.filePath = filePath;
        this.format = format;
    }

    @Override
    public void writeResult(String result) throws IOException {
        if (result == null) {
            throw new NullPointerException("Output result cannot be null");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(result);
            writer.write("\n");
        }
    }

    public String getFormat() {
        return format;
    }
}
