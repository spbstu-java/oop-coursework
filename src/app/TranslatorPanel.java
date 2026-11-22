package app;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import translator.DictionaryReader;
import translator.FileReadException;
import translator.InvalidFileFormatException;
import translator.Translator;

public class TranslatorPanel extends LabPanel {
    private static final Pattern LINE_BREAK = Pattern.compile("\\R");

    private Path dictionaryPath = Path.of("dictionary.txt");
    private final JTextField dictionaryLocation = new JTextField(dictionaryPath.toString(), 27);
    private final JTextArea source = createTextArea(6, 44);

    public TranslatorPanel() {
        JButton chooseDictionary = new JButton("Выбрать словарь…");
        JButton openText = new JButton("Открыть текст…");
        JButton translate = new JButton("Перевести");

        dictionaryLocation.setEditable(false);
        addRow(labelFor("Словарь:", dictionaryLocation), dictionaryLocation, chooseDictionary);

        JScrollPane sourceScroll = new JScrollPane(source);
        sourceScroll.setBorder(BorderFactory.createTitledBorder("Текст для перевода"));
        addInput(sourceScroll);
        addRow(openText, translate);

        chooseDictionary.addActionListener(event -> chooseDictionary());
        openText.addActionListener(event -> openText());
        translate.addActionListener(event -> translate());
    }

    private void chooseDictionary() {
        Path selected = chooseFile("Выбор словаря");
        if (selected != null) {
            dictionaryPath = selected;
            dictionaryLocation.setText(selected.getFileName().toString());
            dictionaryLocation.setToolTipText(selected.toString());
        }
    }

    private void openText() {
        Path selected = chooseFile("Выбор текста для перевода");
        if (selected == null) {
            return;
        }

        try {
            String text = Files.readString(selected, StandardCharsets.UTF_8);
            if (text.startsWith("\uFEFF")) {
                text = text.substring(1);
            }
            source.setText(text);
            source.setCaretPosition(0);
            showResult("");
        } catch (IOException e) {
            showResult("Не удалось прочитать текстовый файл в кодировке UTF-8: " + selected);
        }
    }

    private Path chooseFile(String title) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle(title);
        return chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION ? chooser.getSelectedFile().toPath() : null;
    }

    private void translate() {
        try {
            Translator translator = new Translator(DictionaryReader.read(dictionaryPath.toString()));
            showResult(translateLines(translator, source.getText()));
        } catch (FileReadException e) {
            showResult("Ошибка чтения словаря: " + e.getMessage());
        } catch (InvalidFileFormatException e) {
            showResult("Ошибка формата словаря: " + e.getMessage());
        }
    }

    private static String translateLines(Translator translator, String text) {
        StringBuilder result = new StringBuilder();
        Matcher lineBreak = LINE_BREAK.matcher(text);
        int start = 0;

        while (lineBreak.find()) {
            result.append(translator.translate(text.substring(start, lineBreak.start())));
            result.append(lineBreak.group());
            start = lineBreak.end();
        }

        result.append(translator.translate(text.substring(start)));
        return result.toString();
    }
}
