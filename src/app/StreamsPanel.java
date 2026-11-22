package app;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import streams.StreamOps;

public class StreamsPanel extends LabPanel {
    private enum Operation {
        AVERAGE("Среднее целых чисел", true),
        UPPER("Верхний регистр и префикс _new_", false),
        SQUARES("Квадраты неповторяющихся чисел", true),
        LAST("Последний элемент", false),
        EVEN_SUM("Сумма чётных чисел", true),
        TO_MAP("Строки в Map по первому символу", false);

        private final String title;
        private final boolean numeric;

        Operation(String title, boolean numeric) {
            this.title = title;
            this.numeric = numeric;
        }

        @Override
        public String toString() {
            return title;
        }
    }

    private final JComboBox<Operation> operations = new JComboBox<>(Operation.values());
    private final JLabel hint = new JLabel();
    private final JTextArea input = createTextArea(6, 44);

    public StreamsPanel() {
        JButton run = new JButton("Выполнить");
        addRow(labelFor("Метод:", operations), operations, run);
        addRow(hint);

        JScrollPane inputScroll = new JScrollPane(input);
        inputScroll.setBorder(BorderFactory.createTitledBorder("Входные данные"));
        addInput(inputScroll);

        updateHint();
        operations.addActionListener(event -> updateHint());
        run.addActionListener(event -> runSelected());
    }

    private void updateHint() {
        Operation operation = (Operation) operations.getSelectedItem();
        hint.setText(operation.numeric ? "Разделяйте целые числа пробелами, запятыми или переносами строк" : "Вводите каждый элемент списка с новой строки");
        showResult("");
    }

    private void runSelected() {
        Operation operation = (Operation) operations.getSelectedItem();
        String text = input.getText();

        try {
            Object result = switch (operation) {
                case AVERAGE -> StreamOps.averageOf(parseNumbers(text));
                case UPPER -> StreamOps.upperWithPrefix(parseLines(text));
                case SQUARES -> StreamOps.squaresOfUnique(parseNumbers(text));
                case LAST -> "«" + StreamOps.lastElement(parseLines(text)) + "»";
                case EVEN_SUM -> StreamOps.evenSum(parseNumbers(text).stream().mapToInt(Integer::intValue).toArray());
                case TO_MAP -> StreamOps.indexByFirstChar(parseLines(text));
            };
            showResult(result.toString());
        } catch (NumberFormatException e) {
            showResult("Введите целые числа");
        } catch (IllegalArgumentException | NoSuchElementException e) {
            showResult(e.getMessage());
        }
    }

    private static List<Integer> parseNumbers(String text) {
        if (text.isBlank()) {
            return List.of();
        }
        return Arrays.stream(text.strip().split("[,\\s]+")).map(Integer::parseInt).toList();
    }

    private static List<String> parseLines(String text) {
        return text.isEmpty() ? List.of() : Arrays.asList(text.split("\\R", -1));
    }
}