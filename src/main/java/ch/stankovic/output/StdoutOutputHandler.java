package ch.stankovic.output;

public class StdoutOutputHandler implements OutputHandler{
    @Override
    public void writeResult(String result) {
        if (result == null) {
            throw new NullPointerException("Output result cannot be null");
        }
        System.out.println(result);
    }
}
