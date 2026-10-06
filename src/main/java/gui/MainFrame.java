package gui;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;

// Главное окно: список заданий сверху, панель выбранного задания и общий вывод снизу
class MainFrame extends JFrame {

    private enum Task {
        STRATEGY("1. Стратегия (герой)"),
        ANNOTATIONS("2. Аннотации"),
        TRANSLATOR("3. Переводчик"),
        STREAM("4. Stream API");

        private final String title;

        Task(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return title;
        }
    }

    MainFrame() {
        super("ООП: курсовая работа");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        OutputPane output = new OutputPane();

        // панели заданий лежат в CardLayout, показывается одна
        CardLayout cardLayout = new CardLayout();
        JPanel cards = new JPanel(cardLayout);
        cards.add(new StrategyPanel(output), Task.STRATEGY.name());
        cards.add(new AnnotationsPanel(output), Task.ANNOTATIONS.name());
        cards.add(new TranslatorPanel(output), Task.TRANSLATOR.name());
        cards.add(new StreamPanel(output), Task.STREAM.name());

        JComboBox<Task> taskBox = new JComboBox<>(Task.values());
        taskBox.addActionListener(e -> {
            Task task = (Task) taskBox.getSelectedItem();
            cardLayout.show(cards, task.name());
        });

        JPanel selector = new JPanel(new FlowLayout(FlowLayout.LEFT));
        selector.add(new JLabel("Задание:"));
        selector.add(taskBox);

        JPanel top = new JPanel(new BorderLayout());
        top.add(selector, BorderLayout.NORTH);
        top.add(cards, BorderLayout.CENTER);

        JButton clearButton = new JButton("Очистить вывод");
        clearButton.addActionListener(e -> output.clear());
        JPanel clearPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        clearPanel.add(clearButton);

        setLayout(new BorderLayout());
        add(top, BorderLayout.NORTH);
        add(output, BorderLayout.CENTER);
        add(clearPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }
}