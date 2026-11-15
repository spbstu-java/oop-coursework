
package coursework;

import lab3.Dictionary;
import lab3.FileReadException;
import lab3.InvalidFileFormatException;
import lab3.Translator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class Lab3Panel extends LabPanel {

    private static final Color BACKGROUND = new Color(230, 238, 250);
    private static final Color BLUE = new Color(40, 100, 180);

    private final JLabel dictionaryLabel =
            new JLabel("Словарь не выбран");

    private final JTextArea input =
            new JTextArea("dog look to the window, dog look forward", 4, 40);

    private Translator translator;

    public Lab3Panel() {
        setBackground(BACKGROUND);

        JLabel title = new JLabel("Задание 3. Переводчик");
        title.setFont(new Font("Arial", Font.BOLD, 21));
        title.setForeground(new Color(30, 55, 90));
        title.setBorder(new EmptyBorder(5, 5, 12, 5));

        JButton dictionaryButton = createButton("Выбрать словарь...");
        dictionaryButton.addActionListener(e -> {
            File file = chooseFile("Файл словаря");
            if (file != null) {
                loadDictionary(file.getPath());
            }
        });

        JButton textButton = createButton("Текст из файла...");
        textButton.addActionListener(e -> {
            File file = chooseFile("Файл с текстом");
            if (file != null) {
                loadText(file);
            }
        });

        JButton translateButton = createButton("Перевести");
        translateButton.addActionListener(e -> translate());

        JPanel files = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        files.setBackground(BACKGROUND);
        files.add(dictionaryButton);
        files.add(dictionaryLabel);

        JLabel inputLabel =
                new JLabel("Текст для перевода (можно ввести вручную):");
        inputLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        input.setLineWrap(true);
        input.setWrapStyleWord(true);
        input.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane inputScroll = new JScrollPane(input);
        inputScroll.setPreferredSize(new Dimension(500, 90));

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        buttons.setBackground(BACKGROUND);
        buttons.add(textButton);
        buttons.add(translateButton);

        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.setBackground(BACKGROUND);

        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        files.setAlignmentX(Component.LEFT_ALIGNMENT);
        inputLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        inputScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttons.setAlignmentX(Component.LEFT_ALIGNMENT);

        top.add(title);
        top.add(files);
        top.add(inputLabel);
        top.add(Box.createVerticalStrut(8));
        top.add(inputScroll);
        top.add(buttons);

        add(top, BorderLayout.NORTH);

        // Словарь по умолчанию из папки проекта.
        if (new File("dictionary.txt").exists()) {
            loadDictionary("dictionary.txt");
        }
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setForeground(Color.WHITE);
        button.setBackground(BLUE);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(9, 15, 9, 15));
        button.setOpaque(true);
        return button;
    }

    private File chooseFile(String title) {
        JFileChooser chooser = new JFileChooser(new File("."));
        chooser.setDialogTitle(title);

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        }

        return null;
    }

    private void loadDictionary(String fileName) {
        try {
            translator = new Translator(new Dictionary(fileName));
            dictionaryLabel.setText("Словарь: " + fileName);
            print("Словарь " + fileName + " загружен");

        } catch (FileReadException e) {
            print("Ошибка чтения словаря: " + e.getMessage());

        } catch (InvalidFileFormatException e) {
            print("Неверный формат словаря: " + e.getMessage());
        }
    }

    private void loadText(File file) {
        try {
            input.setText(
                    Files.readString(file.toPath(), StandardCharsets.UTF_8)
            );
            print("Текст загружен из " + file.getName());

        } catch (IOException e) {
            print("Не удалось прочитать файл " + file.getName());
        }
    }

    private void translate() {
        if (translator == null) {
            print("Сначала выберите словарь");
            return;
        }

        print(translator.translate(input.getText()));
    }
}
