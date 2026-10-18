package ui;

import lab3.FileManager;
import lab3.Glossary;
import lab3.Translator;
import lab3.exceptions.FileReadException;
import lab3.exceptions.InvalidFileFormatException;

import javax.swing.*;
import java.awt.*;

public class Lab3Panel extends TemplatePanel {
    private final FilePickerPanel dictionaryFile = new FilePickerPanel("Выберите файл словаря");
    private final FilePickerPanel textFile = new FilePickerPanel("Выберите файл с текстом для перевода");
    private final JRadioButton manualMode = new JRadioButton("Ввести текст вручную", true);
    private final JRadioButton fileMode = new JRadioButton("Взять текст из файла");
    private final JTextArea manualInput = new JTextArea(5, 40);

    public Lab3Panel() {
        super("Лаба 3. Переводчик");

        manualInput.setLineWrap(true);
        manualInput.setWrapStyleWord(true);

        var manualScroll = new JScrollPane(manualInput);
        manualScroll.setBorder(BorderFactory.createTitledBorder("Текст для перевода (ручной ввод)"));

        var buttonGroup = new ButtonGroup();
        buttonGroup.add(manualMode);
        buttonGroup.add(fileMode);
        manualMode.addActionListener(_ -> updateMode());
        fileMode.addActionListener(_ -> updateMode());

        var modePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        modePanel.add(manualMode);
        modePanel.add(fileMode);

        var translateButton = new JButton("Перевести");
        translateButton.addActionListener(_ -> translate());

        init(column(
                row("Файл словаря:", dictionaryFile, null),
                modePanel,
                row("Файл с текстом:", textFile, null),
                manualScroll,
                rightAligned(translateButton)
        ));
        updateMode();
    }

    private void updateMode()
    {
        var manual = manualMode.isSelected();
        manualInput.setEnabled(manual);
        textFile.setEnabled(!manual);
    }

    private void translate()
    {
        output.clear();

        if (dictionaryFile.getPath().isBlank())
        {
            output.println("Ошибка: не выбран файл словаря");
            return;
        }

        try
        {
            var glossary = new Glossary(dictionaryFile.getPath());
            glossary.loadVocabulary();

            String text;
            if (fileMode.isSelected())
            {
                if (textFile.getPath().isBlank())
                {
                    output.println("Ошибка: не выбран файл с текстом");
                    return;
                }
                text = FileManager.read(textFile.getPath());
            } else
            {
                text = manualInput.getText();
            }

            if (text.isBlank())
            {
                output.println("Ошибка: нет текста для перевода");
                return;
            }

            output.println(new Translator(glossary).multilineTranslate(text));
        }
        catch (InvalidFileFormatException | FileReadException ex)
        {
            output.println("Ошибка: " + ex.getMessage());
        }
    }
}
