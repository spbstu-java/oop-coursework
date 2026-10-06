package gui;

import java.util.ArrayList;
import java.util.List;

// Разбор списков, которые пользователь вводит через запятую
final class InputParser {

    private InputParser() {
    }

    static List<Integer> parseIntegers(String input) {
        List<Integer> numbers = new ArrayList<>();
        for (String item : splitItems(input)) {
            try {
                numbers.add(Integer.parseInt(item));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("не удалось разобрать целое число '" + item + "'");
            }
        }
        return numbers;
    }

    static List<String> parseStrings(String input) {
        return splitItems(input);
    }

    // пустая строка даёт пустой список, пустой элемент между запятыми считаем ошибкой
    private static List<String> splitItems(String input) {
        List<String> items = new ArrayList<>();
        if (input.isBlank()) {
            return items;
        }

        for (String part : input.split(",", -1)) {
            String item = part.trim();
            if (item.isEmpty()) {
                throw new IllegalArgumentException("в списке есть пустой элемент");
            }
            items.add(item);
        }
        return items;
    }
}