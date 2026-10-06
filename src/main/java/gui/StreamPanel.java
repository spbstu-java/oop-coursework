package gui;

import task4.streams.StreamOperations;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.OptionalDouble;

// Задание 4: методы на Stream API, данные вводятся списком через запятую
class StreamPanel extends JPanel {

    private enum Method {
        AVERAGE("average", "среднее значение", "целые числа через запятую", "2, 4, 6, 8"),
        UPPER_CASE("upperCaseWithPrefix", "верхний регистр и префикс _new_", "строки через запятую", "hello, world"),
        UNIQUE_SQUARES("squaresOfUniqueElements", "квадраты неповторяющихся чисел", "целые числа через запятую", "1, 2, 2, 3, 3, 3, 4"),
        LAST("lastElement", "последний элемент", "строки через запятую", "a, b, c"),
        SUM_EVEN("sumOfEvenNumbers", "сумма чётных чисел", "целые числа через запятую", "1, 2, 3, 4, 5, 6"),
        FIRST_CHAR("firstCharacterIndex", "первый символ строки как ключ", "строки через запятую, первые символы разные", "cat, dog, emu");

        private final String methodName;
        private final String description;
        private final String hint;
        private final String example;

        Method(String methodName, String description, String hint, String example) {
            this.methodName = methodName;
            this.description = description;
            this.hint = hint;
            this.example = example;
        }

        @Override
        public String toString() {
            return methodName + ": " + description;
        }
    }

    private final JLabel hintLabel = new JLabel();
    private final JTextField inputField = new JTextField(30);

    StreamPanel(OutputPane output) {
        JComboBox<Method> methodBox = new JComboBox<>(Method.values());
        JButton runButton = new JButton("Выполнить");

        // при смене метода подставляем подсказку и пример ввода
        methodBox.addActionListener(e -> showMethod((Method) methodBox.getSelectedItem()));
        runButton.addActionListener(e -> run((Method) methodBox.getSelectedItem(), output));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Метод:"));
        top.add(methodBox);

        JPanel middle = new JPanel(new FlowLayout(FlowLayout.LEFT));
        middle.add(hintLabel);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottom.add(new JLabel("Ввод:"));
        bottom.add(inputField);
        bottom.add(runButton);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.add(top);
        content.add(middle);
        content.add(bottom);

        setLayout(new BorderLayout());
        add(content, BorderLayout.NORTH);

        showMethod(Method.AVERAGE);
    }

    private void showMethod(Method method) {
        hintLabel.setText("Формат ввода: " + method.hint);
        inputField.setText(method.example);
    }

    private void run(Method method, OutputPane output) {
        String input = inputField.getText();
        try {
            String result = execute(method, input);
            output.println(method.methodName + "([" + input.trim() + "]) = " + result);
        } catch (IllegalArgumentException e) {
            output.println("Ошибка: " + e.getMessage());
        }
    }

    private static String execute(Method method, String input) {
        return switch (method) {
            case AVERAGE -> average(input);
            case UPPER_CASE -> StreamOperations.upperCaseWithPrefix(InputParser.parseStrings(input)).toString();
            case UNIQUE_SQUARES -> StreamOperations.squaresOfUniqueElements(InputParser.parseIntegers(input)).toString();
            case LAST -> lastElement(input);
            case SUM_EVEN -> sumOfEvens(input);
            case FIRST_CHAR -> firstCharacters(input);
        };
    }

    private static String average(String input) {
        OptionalDouble average = StreamOperations.average(InputParser.parseIntegers(input));
        return average.isPresent() ? String.valueOf(average.getAsDouble()) : "список пуст, среднее не определено";
    }

    private static String lastElement(String input) {
        List<String> items = InputParser.parseStrings(input);
        try {
            return StreamOperations.lastElement(items);
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("коллекция пуста, последнего элемента нет");
        }
    }

    private static String sumOfEvens(String input) {
        int[] numbers = InputParser.parseIntegers(input).stream()
                .mapToInt(Integer::intValue)
                .toArray();
        return String.valueOf(StreamOperations.sumOfEvenNumbers(numbers));
    }

    private static String firstCharacters(String input) {
        try {
            return StreamOperations.firstCharacterIndex(InputParser.parseStrings(input)).toString();
        } catch (IllegalStateException e) {
            throw new IllegalArgumentException("первые символы строк должны быть разными");
        }
    }
}