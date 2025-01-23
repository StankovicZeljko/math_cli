package ch.stankovic.actions;

import java.util.List;

public final class LessThanFourAction implements Action {

    @Override
    public String execute(List<Double> numbers, String format) {
        if (!format.equals("csv") && !format.equals("json")) {
            throw new IllegalArgumentException("Unsupported format: " + format);
        }
        List<Double> filtered = numbers.stream().filter(n -> n < 4).toList();
        return "csv".equals(format)
                ? String.join(",", filtered.stream().map(String::valueOf).toList())
                : filtered.toString();
    }
}
