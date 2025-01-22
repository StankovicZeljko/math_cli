package ch.stankovic.actions;

import java.util.List;

public final class MinMaxAction implements Action{

    @Override
    public String execute(List<Double> numbers, String format) {
        if (!format.equals("csv") && !format.equals("json")) {
            throw new IllegalArgumentException("Unsupported format: " + format);
        }
        double min = numbers.stream().mapToDouble(Double::doubleValue).min().orElse(Double.NaN);
        double max = numbers.stream().mapToDouble(Double::doubleValue).max().orElse(Double.NaN);
        return "csv".equals(format) ? "%s,%s".formatted(min, max) : "{\"min\": %s, \"max\": %s}".formatted(min, max);
    }
}
