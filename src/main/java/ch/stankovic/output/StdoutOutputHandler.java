package ch.stankovic.output;

public class StdoutOutputHandler implements OutputHandler{
    private final String format;

    public StdoutOutputHandler(String format) {
        if (!format.equals("csv") && !format.equals("json")) {
            throw new IllegalArgumentException("Unsupported output format: " + format);
        }
        this.format = format;
    }

    @Override
    public void writeResult(String result) {
        if (result == null) {
            throw new NullPointerException("Output result cannot be null");
        }
        System.out.println(result.trim() + "\n");
    }

    public String getFormat() {
        return format;
    }
}
