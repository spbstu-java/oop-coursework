package ui;

import javax.swing.*;
import lab3.*;
import java.awt.*;
import java.io.*;
import java.nio.file.*;

class Task3Pane extends TaskPane {
    private final Translator translator = new Translator();
    private String dictionaryPath;

    Task3Pane() {
        JLabel dictLabel = new JLabel("Словарь не выбран");

        JButton dictButton = new JButton("Выбрать словарь...");
        dictButton.addActionListener(e -> {
            File file = chooseFile("Выберите файл словаря");
            if (file != null) {
                dictionaryPath = file.getAbsolutePath();
                dictLabel.setText(dictionaryPath);
            }
        });

        JTextArea input = new JTextArea(4, 30);
        input.setLineWrap(true);
        input.setWrapStyleWord(true);

        JButton loadButton = new JButton("Загрузить текст из файла...");
        loadButton.addActionListener(e -> {
            File file = chooseFile("Выберите файл с текстом");
            if (file == null) {
                return;
            }
            try {
                input.setText(Files.readString(file.toPath()));
            } catch (IOException ex) {
                showError("Не удалось прочитать файл: " + ex.getMessage());
            }
        });

        JButton translateButton = new JButton("Перевести");
        translateButton.addActionListener(e -> {
            if (dictionaryPath == null) {
                showError("Сначала выберите файл словаря.");
                return;
            }
            try {
                translator.loadDictionaryFromFile(dictionaryPath);
                output.setText(translator.translate(input.getText()));
            } catch (FileReadException | InvalidFileFormatException ex) {
                showError(ex.getMessage());
            }
        });

        JPanel dictRow = new JPanel(new BorderLayout(10, 0));
        dictRow.add(dictButton, BorderLayout.WEST);
        dictRow.add(dictLabel, BorderLayout.CENTER);

        addRow(dictRow);
        addRow(new JScrollPane(input));
        addRow(row(loadButton, translateButton));
    }

    private File chooseFile(String title) {
        JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
        chooser.setDialogTitle(title);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        }
        return null;
    }
}
