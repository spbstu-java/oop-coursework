package ui;

import javax.swing.*;
import java.awt.BorderLayout;
import lab4.*;
import java.util.*;

class Task4Pane extends TaskPane {
    private static final String[] METHODS = {
            "Среднее значение списка целых чисел",
            "Верхний регистр + префикс \"new\"",
            "Квадраты элементов, встречающихся один раз",
            "Последний элемент коллекции",
            "Сумма чётных чисел массива",
            "Map: первый символ - ключ, остаток - значение"
    };

    Task4Pane() {
        JComboBox<String> methodCombo = new JComboBox<>(METHODS);

        JTextField inputField = new JTextField();

        JButton runButton = new JButton("Выполнить");
        runButton.addActionListener(e -> run(methodCombo.getSelectedIndex(), inputField.getText()));

        addRow(row(new JLabel("Метод:"), methodCombo));

        // растягивает поле ввода на всю ширину строки.
        JPanel dataRow = new JPanel(new BorderLayout(10, 0));
        dataRow.add(new JLabel("Данные:"), BorderLayout.WEST);
        dataRow.add(inputField, BorderLayout.CENTER);
        dataRow.add(runButton, BorderLayout.EAST);
        addRow(dataRow);
    }

    private void run(int method, String input) {
        try {
            Object result = switch (method) {
                case 0 -> StreamTasks.average(parseInts(input));
                case 1 -> StreamTasks.upperCaseWithPrefix(parseStrings(input));
                case 2 -> StreamTasks.squaresOfUnique(parseInts(input));
                case 3 -> StreamTasks.getLastElement(parseStrings(input));
                case 4 -> StreamTasks.sumOfEven(parseIntArray(input));
                default -> StreamTasks.toMapByFirstChar(parseStrings(input));
            };
            output.setText("Результат: " + result);
        } catch (NoSuchElementException ex) {
            output.setText("Исключение " + ex.getClass().getSimpleName() + ": " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            output.setText("Ошибка ввода: " + ex.getMessage());
        }
    }

    private static List<Integer> parseInts(String input) {
        input = input.trim();
        if (input.isEmpty()) {
            throw new IllegalArgumentException("введите числа через запятую или пробел");
        }
        try {
            return Arrays.stream(input.split("[,\\s]+"))
                    .map(Integer::parseInt)
                    .toList();
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("ожидались целые числа, получено: " + input);
        }
    }

    private static int[] parseIntArray(String input) {
        return parseInts(input).stream().mapToInt(Integer::intValue).toArray();
    }

    private static List<String> parseStrings(String input) {
        input = input.trim();
        if (input.isEmpty()) {
            return List.of();
        }
        // Если есть запятая, делим по запятым, иначе по пробелам.
        String[] parts = input.contains(",")
                ? input.split("\\s*,\\s*")
                : input.split("\\s+");
        return Arrays.asList(parts);
    }
}
