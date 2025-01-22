package ch.stankovic.actions;

import java.util.List;

public final class SumAction implements Action {

    @Override
    public String execute(List<Double> numbers, String format) {
        if (!format.equals("csv") && !format.equals("json")) {
            throw new IllegalArgumentException("Unsupported format: " + format);
        }
        double sum = numbers.stream().mapToDouble(Double::doubleValue).sum();
        return "csv".equals(format) ? String.valueOf(sum) : "{\"sum\": %s}".formatted(sum);
    }
}
