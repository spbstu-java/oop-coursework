package ui;

import lab4.MyStreams;

import javax.swing.*;
import java.awt.*;
import java.util.NoSuchElementException;
import java.util.function.Function;

public class Lab4Panel extends TemplatePanel
{
    private static final String numbersHint = "целые числа через пробел или запятую";
    private static final String stringsHint = "строки через запятую";

    private static final String numbersDefaultInput = "1 2 3 4 5";

    private static final String stringsDefaultInput = "один, два, три, четыре";

    public Lab4Panel()
    {
        super("Лаба 4. Stream API");

        init(column(
                method("Метод, возвращающий среднее значение списка целых чисел: ",
                        numbersHint,
                        numbersDefaultInput,
                        in -> String.valueOf(MyStreams.average(UserInputParser.integers(in)))),

                method("Метод, приводящий все строки в списке в верхний регистр и добавляющий к ним префикс «new»: ",
                        stringsHint,
                        stringsDefaultInput,
                        in -> String.valueOf(MyStreams.upperWithPrefix(UserInputParser.strings(in)))),

                method("Метод, возвращающий список квадратов всех встречающихся только один раз элементов списка: ",
                        numbersHint,
                        numbersDefaultInput,
                        in -> String.valueOf(MyStreams.squaresOfUniqueNumbers(UserInputParser.integers(in)))),

                method("Метод, принимающий на вход коллекцию и возвращающий ее последний элемент или кидающий исключение, если коллекция пуста:",
                        stringsHint,
                        stringsDefaultInput,
                        in -> String.valueOf(MyStreams.lastElementOfCollection(UserInputParser.strings(in)))),

                method("Метод, принимающий на вход массив целых чисел, возвращающий сумму чётных чисел или 0, если чётных чисел нет: ",
                        numbersHint,
                        numbersDefaultInput,
                        in -> String.valueOf(MyStreams.sumOfEvenNumbers(
                                UserInputParser.integers(in).stream().mapToInt(Integer::intValue).toArray()))),

                method("Метод, преобразовывающий все строки в списке в Map, где первый символ – ключ, оставшиеся – значение: ",
                        stringsHint,
                        stringsDefaultInput,
                        in -> String.valueOf(MyStreams.toMapByFirstSymbol(UserInputParser.strings(in))))
        ));
    }

    private JPanel method(
            String title,
            String hint,
            String defaultInput,
            Function<String, String> action)
    {
        var textField = new JTextField(defaultInput);
        var button = new JButton("Выполнить");

        Runnable run = () -> {
            var input = textField.getText();
            output.println("> " + title);
            output.println("  Ввод: " + input);
            try
            {
                output.println("  Результат: " + action.apply(input));
            }
            catch (NoSuchElementException ex)
            {
                output.println("  Исключение: " + ex.getMessage());
            }
            catch (IllegalArgumentException ex)
            {
                output.println("  Ошибка ввода: " + ex.getMessage());
            }
            output.println("");
        };
        button.addActionListener(_ -> run.run());
        textField.addActionListener(_ -> run.run());

        var panel = new JPanel(new BorderLayout(8, 0));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(title + " (" + hint + ")"),
                BorderFactory.createEmptyBorder(0, 6, 4, 6)));
        panel.add(textField, BorderLayout.CENTER);
        panel.add(button, BorderLayout.EAST);
        return panel;
    }
}
