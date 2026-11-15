
package coursework;

import lab4.StreamMethods;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;

public class Lab4Panel extends LabPanel {

    private static final Color BACKGROUND = new Color(230, 238, 250);
    private static final Color BLUE = new Color(40, 100, 180);

    public Lab4Panel() {
        setBackground(BACKGROUND);

        JLabel title = new JLabel("Задание 4. Stream API");
        title.setFont(new Font("Arial", Font.BOLD, 21));
        title.setForeground(new Color(30, 55, 90));
        title.setBorder(new EmptyBorder(5, 5, 12, 5));

        JPanel rows = new JPanel(new GridLayout(0, 1, 0, 7));
        rows.setBackground(BACKGROUND);

        addRow(rows, "Среднее значение", "10, 20, 30, 40, 50",
                text -> String.valueOf(
                        StreamMethods.average(parseInts(text))
                ));

        addRow(rows, "Верхний регистр и префикс _new_",
                "tiger, dolphin, elephant, giraffe, kangaroo",
                text -> String.valueOf(
                        StreamMethods.addPrefix(parseStrings(text))
                ));

        addRow(rows, "Квадраты встречающихся один раз",
                "2, 3, 4, 2, 5, 6, 4, 7",
                text -> String.valueOf(
                        StreamMethods.uniqueSquares(parseInts(text))
                ));

        addRow(rows, "Последний элемент коллекции",
                "Индия, Саудовская Аравия, Австралия, Япония",
                text -> StreamMethods.lastElement(parseStrings(text)));

        addRow(rows, "Сумма чётных чисел массива",
                "1, 2, 3, 4, 5, 6, 7, 8",
                text -> String.valueOf(
                        StreamMethods.sumEven(
                                parseInts(text).stream()
                                        .mapToInt(Integer::intValue)
                                        .toArray()
                        )
                ));

        addRow(rows, "Строки в Map",
                "Apple, Banana, Cherry, Dragonfruit, Elderberry",
                text -> String.valueOf(
                        StreamMethods.toMap(parseStrings(text))
                ));

        JPanel top = new JPanel(new BorderLayout(0, 10));
        top.setBackground(BACKGROUND);
        top.add(title, BorderLayout.NORTH);
        top.add(rows, BorderLayout.CENTER);

        add(top, BorderLayout.NORTH);
    }

    private void addRow(
            JPanel rows,
            String title,
            String example,
            Function<String, String> method
    ) {
        JLabel label = new JLabel(title);
        label.setPreferredSize(new Dimension(230, 25));
        label.setFont(new Font("Arial", Font.PLAIN, 13));

        JTextField field = new JTextField(example);
        field.setFont(new Font("Arial", Font.PLAIN, 13));

        JButton button = new JButton("Выполнить");
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(BLUE);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorder(new EmptyBorder(8, 12, 8, 12));

        button.addActionListener(e -> {
            String text = field.getText();

            try {
                print(title + " [" + text + "]: " + method.apply(text));

            } catch (NumberFormatException ex) {
                print(title + ": нужно ввести целые числа через запятую");

            } catch (NoSuchElementException ex) {
                print(title + ": " + ex.getMessage());
            }
        });

        JPanel row = new JPanel(new BorderLayout(8, 0));
        row.setBackground(BACKGROUND);
        row.setBorder(new EmptyBorder(3, 5, 3, 5));

        row.add(label, BorderLayout.WEST);
        row.add(field, BorderLayout.CENTER);
        row.add(button, BorderLayout.EAST);

        rows.add(row);
    }

    private static List<String> parseStrings(String text) {
        if (text.isBlank()) {
            return List.of();
        }

        return Arrays.stream(text.split(","))
                .map(String::trim)
                .toList();
    }

    private static List<Integer> parseInts(String text) {
        return parseStrings(text).stream()
                .map(Integer::parseInt)
                .toList();
    }
}
