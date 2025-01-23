package ch.stankovic.actions;

import java.util.List;

public sealed interface Action permits SumAction, MinMaxAction, LessThanFourAction {
    String execute(List<Double> numbers, String format);
}
