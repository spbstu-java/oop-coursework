import javax.swing.*;
import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class CourseworkFrame extends JFrame
{
    public CourseworkFrame()
    {
        super("Лабораторные работы 1–4");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(980, 740);
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);

        String[] names = {"1 — Перемещение героя", "2 — Аннотации и рефлексия",
                "3 — Переводчик", "4 — Stream API"};
        JComboBox<String> selector = new JComboBox<>(names);
        JPanel selectionPanel = new JPanel(new BorderLayout(0, 8));
        selectionPanel.setBorder(BorderFactory.createEmptyBorder(40, 12, 8, 12));
        JLabel selectionLabel = new JLabel("Выберите приложение");
        selectionLabel.setLabelFor(selector);
        selectionPanel.add(selectionLabel, BorderLayout.NORTH);
        selectionPanel.add(selector, BorderLayout.CENTER);
        CardLayout layout = new CardLayout();
        JPanel cards = new JPanel(layout);
        cards.add(heroPanel(), names[0]);
        cards.add(carPanel(), names[1]);
        cards.add(translatorPanel(), names[2]);
        cards.add(streamPanel(), names[3]);
        selector.addActionListener(e -> layout.show(cards, (String) selector.getSelectedItem()));
        add(selectionPanel, BorderLayout.NORTH);
        add(cards, BorderLayout.CENTER);
    }

    private JPanel heroPanel()
    {
        JTextArea output = outputArea();
        Hero hero = new Hero();
        JComboBox<String> movement = new JComboBox<>(new String[]{"Пешком", "Бег", "Верхом", "Полёт"});
        JButton execute = new JButton("Переместить героя");
        execute.addActionListener(e -> {
            Movement strategy = switch (movement.getSelectedIndex())
            {
                case 1 -> new Run();
                case 2 -> new Ride();
                case 3 -> new Fly();
                default -> new Walk();
            };
            hero.setMovement(strategy);
            hero.move(line -> output.append(line + "\n"));
        });
        JPanel controls = new JPanel();
        controls.add(new JLabel("Способ перемещения:"));
        controls.add(movement);
        controls.add(execute);
        return resultPanel(controls, output);
    }

    private JPanel carPanel()
    {
        JTextArea output = outputArea();
        JTextField speed = new JTextField("200");
        JTextField fuel = new JTextField("АИ-95");
        JTextField liters = new JTextField("50");
        JTextField price = new JTextField("10");
        JCheckBox smoothly = new JCheckBox("Плавное торможение", true);
        JPanel controls = new JPanel(new GridLayout(0, 2, 8, 8));
        controls.add(new JLabel("Максимальная скорость, км/ч:")); controls.add(speed);
        controls.add(new JLabel("Топливо:")); controls.add(fuel);
        controls.add(new JLabel("Объём топлива, л:")); controls.add(liters);
        controls.add(new JLabel("Цена колеса, т.р.:")); controls.add(price);
        controls.add(smoothly);
        JButton execute = new JButton("Вызвать аннотированные методы");
        controls.add(execute);
        execute.addActionListener(e -> {
            output.setText("");
            try
            {
                int maxSpeed;
                try
                {
                    maxSpeed = Integer.parseInt(speed.getText().strip());
                }
                catch (NumberFormatException ex)
                {
                    throw new IllegalArgumentException("Скорость: введите целое число");
                }
                if (maxSpeed < 0) throw new IllegalArgumentException("Скорость не может быть отрицательной");
                if (fuel.getText().isBlank()) throw new IllegalArgumentException("Укажите топливо");
                double volume = InputParser.nonNegativeDecimal(liters.getText(), "Объём топлива");
                double cost = InputParser.nonNegativeDecimal(price.getText(), "Цена колеса");
                AnnotatedRunner runner = new AnnotatedRunner(fuel.getText(), maxSpeed,
                        volume, smoothly.isSelected(), Map.of(Car.Wheel.class, () -> new Car.Wheel(cost)));
                runner.invokeAnnotatedMethods(new Car(line -> output.append(line + "\n")));
            } catch (RuntimeException ex)
            {
                showError(output, ex);
            }
        });
        return resultPanel(controls, output);
    }

    private JPanel translatorPanel()
    {
        JTextArea output = outputArea();
        JTextArea input = new JTextArea(8, 40);
        input.setLineWrap(true);
        input.setWrapStyleWord(true);
        input.setText("dog look to the window, dog look forward");
        JTextField dictionary = new JTextField("dictionary.txt", 30);
        dictionary.setEditable(false);
        JButton chooseDictionary = new JButton("Выбрать словарь…");
        JButton chooseText = new JButton("Загрузить текст из файла…");
        JButton translate = new JButton("Перевести");
        JLabel status = new JLabel("Введите текст вручную или загрузите файл UTF-8");
        chooseDictionary.addActionListener(e -> {
            Path path = chooseFile("Выберите словарь UTF-8");
            if (path != null) dictionary.setText(path.toString());
        });
        chooseText.addActionListener(e -> {
            Path path = chooseFile("Выберите текст UTF-8");
            if (path == null) return;
            input.setEditable(false);
            chooseText.setEnabled(false);
            translate.setEnabled(false);
            new SwingWorker<String, Void>() {
                protected String doInBackground() throws Exception
                {
                    try
                    {
                        return Files.readString(path, StandardCharsets.UTF_8);
                    }
                    catch (java.io.IOException | SecurityException ex)
                    {
                        throw new FileReadException("Не удалось прочитать текст: " + path, ex);
                    }
                }
                protected void done()
                {
                    try
                    {
                        input.setText(get());
                        status.setText("Загружен: " + path.getFileName() + " — текст можно редактировать");
                    } catch (InterruptedException ex)
                    {
                        Thread.currentThread().interrupt(); showError(output, ex);
                    } catch (ExecutionException ex)
                    {
                        showError(output, ex.getCause());
                    }
                    finally
                    {
                        input.setEditable(true); chooseText.setEnabled(true); translate.setEnabled(true);
                    }
                }
            }.execute();
        });
        translate.addActionListener(e -> {
            String source = input.getText();
            String dictionaryPath = dictionary.getText();
            runInBackground(translate, output,
                    () -> new Translator(Path.of(dictionaryPath)).translate(source));
        });
        JPanel fileRow = new JPanel(new BorderLayout(8, 8));
        fileRow.add(dictionary, BorderLayout.CENTER);
        fileRow.add(chooseDictionary, BorderLayout.EAST);
        fileRow.setBorder(BorderFactory.createTitledBorder("Словарь"));
        JPanel buttons = new JPanel(); buttons.add(chooseText); buttons.add(translate);
        JPanel controls = new JPanel(new BorderLayout(8, 8));
        controls.add(fileRow, BorderLayout.NORTH);
        JPanel source = new JPanel(new BorderLayout());
        source.add(status, BorderLayout.NORTH);
        source.add(new JScrollPane(input), BorderLayout.CENTER);
        controls.add(source, BorderLayout.CENTER);
        controls.add(buttons, BorderLayout.SOUTH);
        return resultPanel(controls, output);
    }

    private JPanel streamPanel()
    {
        JTextArea output = outputArea();
        String[] names = {"Среднее значение", "Верхний регистр и *new*", "Квадраты уникальных элементов",
                "Последний элемент", "Сумма чётных чисел", "Map по первому символу"};
        JComboBox<String> method = new JComboBox<>(names);
        JTextArea input = new JTextArea("2 4 6 8", 7, 40);
        JLabel hint = new JLabel();
        Runnable updateHint = () -> hint.setText(switch (method.getSelectedIndex()) {
            case 0, 2, 4 -> "Целые числа через пробел, запятую или ;. Пустое поле — пустой список.";
            default -> "Один элемент на строку. Пустое поле — пустой список. Последний элемент — строка.";
        });
        updateHint.run();
        method.addActionListener(e -> updateHint.run());
        JButton execute = new JButton("Выполнить");
        execute.addActionListener(e -> {
            try
            {
                String text = input.getText();
                Object result = switch (method.getSelectedIndex()) {
                    case 0 -> StreamTasks.average(InputParser.integers(text));
                    case 1 -> StreamTasks.uppercaseWithPrefix(InputParser.strings(text));
                    case 2 -> StreamTasks.uniqueSquares(InputParser.integers(text));
                    case 3 -> StreamTasks.lastElement(InputParser.strings(text));
                    case 4 -> StreamTasks.sumEven(InputParser.integers(text).stream().mapToInt(Integer::intValue).toArray());
                    default -> StreamTasks.stringsToMap(InputParser.strings(text));
                };
                output.setText("Результат: " + result);
            } catch (RuntimeException ex)
            {
                output.setText(""); showError(output, ex);
            }
        });
        JPanel controls = new JPanel(new BorderLayout(8, 8));
        JPanel top = new JPanel(new GridLayout(0, 1)); top.add(method); top.add(hint);
        controls.add(top, BorderLayout.NORTH);
        controls.add(new JScrollPane(input), BorderLayout.CENTER);
        controls.add(execute, BorderLayout.SOUTH);
        return resultPanel(controls, output);
    }

    private static JTextArea outputArea()
    {
        JTextArea area = new JTextArea(12, 40);
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        return area;
    }

    private static JPanel resultPanel(JPanel controls, JTextArea output)
    {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        panel.add(controls, BorderLayout.NORTH);
        JScrollPane scroll = new JScrollPane(output);
        scroll.setBorder(BorderFactory.createTitledBorder("Результат"));
        panel.add(scroll, BorderLayout.CENTER);
        JButton clear = new JButton("Очистить результат");
        clear.addActionListener(e -> output.setText(""));
        panel.add(clear, BorderLayout.SOUTH);
        return panel;
    }

    private Path chooseFile(String title)
    {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle(title);
        return chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION
                ? chooser.getSelectedFile().toPath() : null;
    }

    private static void showError(JTextArea output, Throwable error)
    {
        output.append("Ошибка: " + error.getClass().getSimpleName() + ": " + error.getMessage() + "\n");
    }

    private static void runInBackground(JButton button, JTextArea output, Callable<String> task)
    {
        button.setEnabled(false);
        output.setText("Выполняется…");
        new SwingWorker<String, Void>() {
            protected String doInBackground() throws Exception { return task.call(); }
            protected void done()
            {
                try
                {
                    output.setText(get());
                } catch (InterruptedException ex)
                {
                    Thread.currentThread().interrupt(); output.setText(""); showError(output, ex);
                } catch (ExecutionException ex)
                {
                    output.setText(""); showError(output, ex.getCause());
                } finally
                {
                    button.setEnabled(true);
                }
            }
        }.execute();
    }
}
