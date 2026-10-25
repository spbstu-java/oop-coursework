import annotations.Runner;
import annotations.Sample;
import strategy.FlyStrategy;
import strategy.Hero;
import strategy.HorseStrategy;
import strategy.WalkStrategy;
import stream.StreamMethods;
import translator.FileReadException;
import translator.InvalidFileFormatException;
import translator.Translator;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class App extends JFrame {
    private final CardLayout cards = new CardLayout();
    private final JPanel cardPanel = new JPanel(cards);

    private Hero hero;
    private JTextArea strategyOut;
    private JTextArea annotationsOut;
    private JTextArea translatorOut;
    private JTextArea streamOut;

    private JTextField dictPathField;
    private JTextField textPathField;
    private JTextArea translatorInput;
    private final Translator translator = new Translator();

    private JTextField intsField;
    private JTextField stringsField;
    private JTextField arrayField;

    public App() {
        super("Курсовая работа ООП");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(860, 580);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(root);

        DefaultListModel<String> model = new DefaultListModel<>();
        model.addElement("1. Стратегия");
        model.addElement("2. Аннотации");
        model.addElement("3. Переводчик");
        model.addElement("4. Stream API");

        JList<String> list = new JList<>(model);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.setFixedCellWidth(160);
        list.setBorder(BorderFactory.createTitledBorder("Задания"));
        list.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                switch (list.getSelectedIndex()) {
                    case 0 -> cards.show(cardPanel, "strategy");
                    case 1 -> cards.show(cardPanel, "annotations");
                    case 2 -> cards.show(cardPanel, "translator");
                    case 3 -> cards.show(cardPanel, "stream");
                }
            }
        });

        cardPanel.add(buildStrategyPanel(), "strategy");
        cardPanel.add(buildAnnotationsPanel(), "annotations");
        cardPanel.add(buildTranslatorPanel(), "translator");
        cardPanel.add(buildStreamPanel(), "stream");

        root.add(list, BorderLayout.WEST);
        root.add(cardPanel, BorderLayout.CENTER);
    }

    private JPanel buildStrategyPanel() {
        hero = new Hero(new WalkStrategy());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder("Стратегия"));

        JComboBox<String> modes = new JComboBox<>(new String[]{
                "Идти пешком", "Ехать на лошади", "Лететь"
        });
        modes.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        modes.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttons.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton moveBtn = new JButton("Переместиться");
        moveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idx = modes.getSelectedIndex();
                if (idx == 0) {
                    hero.setMoveStrategy(new WalkStrategy());
                } else if (idx == 1) {
                    hero.setMoveStrategy(new HorseStrategy());
                } else {
                    hero.setMoveStrategy(new FlyStrategy());
                }
                strategyOut.append(hero.move() + "\n");
            }
        });

        JButton clearBtn = new JButton("Очистить");
        clearBtn.addActionListener(e -> strategyOut.setText(""));

        buttons.add(moveBtn);
        buttons.add(clearBtn);

        strategyOut = readOnlyArea();
        JScrollPane scroll = new JScrollPane(strategyOut);
        scroll.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(new JLabel("Способ перемещения:"));
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(modes);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(buttons);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(scroll);
        return panel;
    }

    private JPanel buildAnnotationsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder("Аннотации"));

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttons.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton runBtn = new JButton("Запустить");
        runBtn.addActionListener(e ->
                annotationsOut.setText(captureOut(() -> Runner.run(new Sample()))));

        JButton clearBtn = new JButton("Очистить");
        clearBtn.addActionListener(e -> annotationsOut.setText(""));

        buttons.add(runBtn);
        buttons.add(clearBtn);

        annotationsOut = readOnlyArea();
        JScrollPane scroll = new JScrollPane(annotationsOut);
        scroll.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(buttons);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(scroll);
        return panel;
    }

    private JPanel buildTranslatorPanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBorder(BorderFactory.createTitledBorder("Переводчик"));

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(3, 4, 3, 4);
        c.fill = GridBagConstraints.HORIZONTAL;

        dictPathField = new JTextField("dictionary.txt");
        textPathField = new JTextField();

        JButton pickDict = new JButton("Словарь...");
        pickDict.addActionListener(e -> chooseFile(dictPathField));

        JButton loadDict = new JButton("Загрузить");
        loadDict.addActionListener(e -> loadDictionary());

        JButton pickText = new JButton("Файл текста...");
        pickText.addActionListener(e -> {
            chooseFile(textPathField);
            loadTextFile();
        });

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0;
        form.add(new JLabel("Словарь:"), c);
        c.gridx = 1;
        c.weightx = 1;
        form.add(dictPathField, c);
        c.gridx = 2;
        c.weightx = 0;
        form.add(pickDict, c);
        c.gridx = 3;
        form.add(loadDict, c);

        c.gridx = 0;
        c.gridy = 1;
        form.add(new JLabel("Текст из файла:"), c);
        c.gridx = 1;
        c.weightx = 1;
        form.add(textPathField, c);
        c.gridx = 2;
        c.weightx = 0;
        form.add(pickText, c);

        translatorInput = new JTextArea(6, 40);
        translatorInput.setLineWrap(true);
        translatorInput.setWrapStyleWord(true);
        translatorInput.setBorder(BorderFactory.createTitledBorder("Текст для перевода"));

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton translateBtn = new JButton("Перевести");
        translateBtn.addActionListener(e -> doTranslate());
        JButton clearBtn = new JButton("Очистить вывод");
        clearBtn.addActionListener(e -> translatorOut.setText(""));
        buttons.add(translateBtn);
        buttons.add(clearBtn);

        translatorOut = readOnlyArea();
        translatorOut.setRows(8);
        translatorOut.setBorder(BorderFactory.createTitledBorder("Результат"));

        JPanel center = new JPanel(new BorderLayout(6, 6));
        center.add(new JScrollPane(translatorInput), BorderLayout.CENTER);
        center.add(buttons, BorderLayout.SOUTH);

        panel.add(form, BorderLayout.NORTH);
        panel.add(center, BorderLayout.CENTER);
        panel.add(new JScrollPane(translatorOut), BorderLayout.SOUTH);
        return panel;
    }

    private JPanel buildStreamPanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBorder(BorderFactory.createTitledBorder("Stream API"));

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        c.fill = GridBagConstraints.HORIZONTAL;

        intsField = new JTextField("1, 2, 3, 4, 5, 2, 3");
        stringsField = new JTextField("apple, banana, avocado, cat");
        arrayField = new JTextField("1, 2, 3, 4, 5, 6");

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0;
        form.add(new JLabel("Список целых:"), c);
        c.gridx = 1;
        c.weightx = 1;
        form.add(intsField, c);

        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0;
        form.add(new JLabel("Список строк:"), c);
        c.gridx = 1;
        c.weightx = 1;
        form.add(stringsField, c);

        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 0;
        form.add(new JLabel("Массив целых:"), c);
        c.gridx = 1;
        c.weightx = 1;
        form.add(arrayField, c);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton runBtn = new JButton("Выполнить");
        runBtn.addActionListener(e -> runStreamMethods());
        JButton clearBtn = new JButton("Очистить");
        clearBtn.addActionListener(e -> streamOut.setText(""));
        buttons.add(runBtn);
        buttons.add(clearBtn);

        JPanel north = new JPanel(new BorderLayout());
        north.add(form, BorderLayout.CENTER);
        north.add(buttons, BorderLayout.SOUTH);

        streamOut = readOnlyArea();
        panel.add(north, BorderLayout.NORTH);
        panel.add(new JScrollPane(streamOut), BorderLayout.CENTER);
        return panel;
    }

    private void chooseFile(JTextField target) {
        JFileChooser chooser = new JFileChooser(".");
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            target.setText(chooser.getSelectedFile().getAbsolutePath());
        }
    }

    private void loadDictionary() {
        try {
            translator.load(Path.of(dictPathField.getText().trim()));
            translatorOut.append("Словарь загружен: " + translator.size() + " записей\n");
        } catch (InvalidFileFormatException e) {
            showError("Формат файла: " + e.getMessage());
        } catch (FileReadException e) {
            showError("Чтение файла: " + e.getMessage());
        }
    }

    private void loadTextFile() {
        String path = textPathField.getText().trim();
        if (path.isEmpty()) {
            return;
        }
        try {
            translatorInput.setText(Files.readString(Path.of(path), StandardCharsets.UTF_8));
        } catch (IOException e) {
            showError("Не удалось прочитать текст: " + e.getMessage());
        }
    }

    private void doTranslate() {
        if (translator.size() == 0) {
            loadDictionary();
            if (translator.size() == 0) {
                return;
            }
        }
        translatorOut.append(translator.translate(translatorInput.getText()) + "\n");
    }

    private void runStreamMethods() {
        try {
            List<Integer> numbers = parseInts(intsField.getText());
            List<String> strings = parseStrings(stringsField.getText());
            int[] array = parseIntArray(arrayField.getText());

            StringBuilder sb = new StringBuilder();
            sb.append("average: ").append(StreamMethods.average(numbers)).append('\n');
            sb.append("upper: ").append(StreamMethods.upperWithPrefix(strings)).append('\n');
            sb.append("uniqueSquares: ").append(StreamMethods.uniqueSquares(numbers)).append('\n');
            sb.append("last: ").append(StreamMethods.lastElement(numbers)).append('\n');
            sb.append("sumEven: ").append(StreamMethods.sumEven(array)).append('\n');
            sb.append("toMap: ").append(StreamMethods.toMap(strings)).append('\n');

            try {
                StreamMethods.lastElement(new ArrayList<String>());
            } catch (NoSuchElementException e) {
                sb.append("empty collection: ").append(e).append('\n');
            }

            streamOut.setText(sb.toString());
        } catch (RuntimeException e) {
            showError("Ошибка ввода: " + e.getMessage());
        }
    }

    private List<Integer> parseInts(String text) {
        List<Integer> list = new ArrayList<>();
        for (String part : text.split("[,;\\s]+")) {
            if (!part.isBlank()) {
                list.add(Integer.parseInt(part.trim()));
            }
        }
        return list;
    }

    private List<String> parseStrings(String text) {
        List<String> list = new ArrayList<>();
        for (String part : text.split("[,;]+")) {
            String s = part.trim();
            if (!s.isEmpty()) {
                list.add(s);
            }
        }
        return list;
    }

    private int[] parseIntArray(String text) {
        List<Integer> list = parseInts(text);
        int[] array = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    private JTextArea readOnlyArea() {
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        return area;
    }

    private String captureOut(Runnable action) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream capture = new PrintStream(buffer, true, StandardCharsets.UTF_8);
        PrintStream oldOut = System.out;
        PrintStream oldErr = System.err;
        System.setOut(capture);
        System.setErr(capture);
        try {
            action.run();
        } finally {
            System.setOut(oldOut);
            System.setErr(oldErr);
            capture.close();
        }
        return buffer.toString(StandardCharsets.UTF_8);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Ошибка", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App().setVisible(true));
    }
}
