package gui;

import task3.dictionary.DictionaryLoader;
import task3.exception.FileReadException;
import task3.exception.InvalidFileFormatException;
import task3.translator.TextFileReader;
import task3.translator.Translator;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.nio.file.Path;
import java.util.Optional;

// Задание 3: переводчик, словарь и текст берутся из файлов, текст можно ввести вручную
class TranslatorPanel extends JPanel {

    private final OutputPane output;

    private final JLabel dictionaryLabel = new JLabel("не выбран");
    private final JLabel textFileLabel = new JLabel("не выбран");
    private final JTextArea inputArea = new JTextArea(4, 40);
    private final JButton textFileButton = new JButton("Выбрать файл с текстом...");
    private final JRadioButton manualRadio = new JRadioButton("Ввести текст вручную", true);
    private final JRadioButton fileRadio = new JRadioButton("Взять текст из файла");

    // появляется после успешной загрузки словаря
    private Translator translator;
    private Path textFile;

    TranslatorPanel(OutputPane output) {
        this.output = output;

        JButton dictionaryButton = new JButton("Выбрать словарь...");
        dictionaryButton.addActionListener(e -> chooseDictionary());

        textFileButton.addActionListener(e -> chooseTextFile());

        ButtonGroup group = new ButtonGroup();
        group.add(manualRadio);
        group.add(fileRadio);
        manualRadio.addActionListener(e -> updateInputMode());
        fileRadio.addActionListener(e -> updateInputMode());

        JButton translateButton = new JButton("Перевести");
        translateButton.addActionListener(e -> translate());

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.add(row(dictionaryButton, new JLabel("Словарь:"), dictionaryLabel));
        content.add(row(manualRadio, fileRadio));
        content.add(row(new JScrollPane(inputArea)));
        content.add(row(textFileButton, new JLabel("Файл:"), textFileLabel));
        content.add(row(translateButton));

        setLayout(new BorderLayout());
        add(content, BorderLayout.NORTH);

        updateInputMode();
    }

    private static JPanel row(Component... components) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        for (Component component : components) {
            panel.add(component);
        }
        return panel;
    }

    // в зависимости от выбора доступно либо поле ввода, либо выбор файла
    private void updateInputMode() {
        boolean manual = manualRadio.isSelected();
        inputArea.setEnabled(manual);
        textFileButton.setEnabled(!manual);
    }

    private Optional<Path> chooseFile() {
        JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            return Optional.of(chooser.getSelectedFile().toPath());
        }
        return Optional.empty();
    }

    private void chooseDictionary() {
        Optional<Path> chosen = chooseFile();
        if (chosen.isEmpty()) {
            return;
        }

        Path path = chosen.get();
        try {
            translator = new Translator(DictionaryLoader.load(path));
            dictionaryLabel.setText(path.getFileName().toString());
            output.println("Словарь загружен: " + path);
        } catch (FileReadException | InvalidFileFormatException e) {
            output.println("Ошибка: " + e.getMessage());
        }
    }

    private void chooseTextFile() {
        chooseFile().ifPresent(path -> {
            textFile = path;
            textFileLabel.setText(path.getFileName().toString());
        });
    }

    private void translate() {
        if (translator == null) {
            output.println("Ошибка: сначала выберите словарь");
            return;
        }

        String text;
        if (manualRadio.isSelected()) {
            text = inputArea.getText();
        } else {
            if (textFile == null) {
                output.println("Ошибка: не выбран файл с текстом");
                return;
            }
            try {
                text = TextFileReader.read(textFile);
            } catch (FileReadException e) {
                output.println("Ошибка: " + e.getMessage());
                return;
            }
        }

        if (text.isBlank()) {
            output.println("Ошибка: нет текста для перевода");
            return;
        }

        output.println("Перевод:");
        output.println(translator.translate(text));
    }
}