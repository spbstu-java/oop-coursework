package ui;

import javax.swing.*;
import java.awt.*;

public class CourseworkFrame extends JFrame {
    private final DefaultListModel<String> taskNames = new DefaultListModel<>();
    private final JList<String> taskList = new JList<>(taskNames);
    private final CardLayout cards = new CardLayout();
    private final JPanel cardPanel = new JPanel(cards);

    public CourseworkFrame() {
        super("Курсовая работа по ООП");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        addTask("Задание 1", new Task1Pane());
        addTask("Задание 2", new Task2Pane());
        addTask("Задание 3", new Task3Pane());
        addTask("Задание 4", new Task4Pane());

        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taskList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cards.show(cardPanel, taskList.getSelectedValue());
            }
        });

        JScrollPane listScroll = new JScrollPane(taskList);
        listScroll.setPreferredSize(new Dimension(170, 0));

        JPanel root = new JPanel(new BorderLayout(10, 0));
        root.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        root.add(listScroll, BorderLayout.WEST);
        root.add(cardPanel, BorderLayout.CENTER);
        setContentPane(root);

        taskList.setSelectedIndex(0);
        setSize(740, 480);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private void addTask(String title, TaskPane pane) {
        taskNames.addElement(title);
        cardPanel.add(pane, title);
    }
}
