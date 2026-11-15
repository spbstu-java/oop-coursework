
package coursework;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

public class MainWindow extends JFrame {

    private static final String[] TASKS = {
            "1. Стратегия",
            "2. Аннотации и reflection",
            "3. Переводчик",
            "4. Stream API"
    };

    // Цвета интерфейса
    private static final Color BACKGROUND_COLOR =
            new Color(245, 247, 252);

    private static final Color SIDEBAR_COLOR =
            new Color(42, 55, 85);

    private static final Color SELECTED_COLOR =
            new Color(75, 120, 220);

    private static final Color TEXT_COLOR =
            new Color(255, 255, 255);

    public MainWindow() {
        super("Моя курсовая работа");

        // Основной фон окна
        getContentPane().setBackground(BACKGROUND_COLOR);

        // Создание панелей заданий
        // Создаём менеджер расположения карточек
        CardLayout cards = new CardLayout();

// Создаём общую панель заданий
        JPanel taskPanels = new JPanel(cards);

// Светло-голубой фон правой части
        taskPanels.setBackground(new Color(230, 238, 250));

// Создаём панели лабораторных работ
        Lab1Panel lab1 = new Lab1Panel();
        Lab2Panel lab2 = new Lab2Panel();
        Lab3Panel lab3 = new Lab3Panel();
        Lab4Panel lab4 = new Lab4Panel();

// Добавляем панели в CardLayout
        taskPanels.add(lab1, TASKS[0]);
        taskPanels.add(lab2, TASKS[1]);
        taskPanels.add(lab3, TASKS[2]);
        taskPanels.add(lab4, TASKS[3]);

        // Создание списка заданий
        JList<String> taskList = new JList<>(TASKS);

        taskList.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        taskList.setSelectedIndex(0);

        // Настройка внешнего вида списка
        taskList.setFont(
                new Font("Arial", Font.BOLD, 15)
        );

        taskList.setFixedCellHeight(55);
        taskList.setBackground(SIDEBAR_COLOR);
        taskList.setForeground(TEXT_COLOR);

        taskList.setBorder(
                BorderFactory.createEmptyBorder(10, 8, 10, 8)
        );

        // Настройка выделения элементов списка
        taskList.setSelectionBackground(SELECTED_COLOR);
        taskList.setSelectionForeground(Color.WHITE);

        // Закруглённое выделение выбранного задания
        taskList.setCellRenderer(new DefaultListCellRenderer() {

            @Override
            public Component getListCellRendererComponent(
                    JList<?> list,
                    Object value,
                    int index,
                    boolean isSelected,
                    boolean cellHasFocus) {

                Component component =
                        super.getListCellRendererComponent(
                                list,
                                value,
                                index,
                                isSelected,
                                cellHasFocus
                        );

                setBorder(
                        BorderFactory.createEmptyBorder(
                                10, 15, 10, 10
                        )
                );

                if (isSelected) {
                    setBackground(SELECTED_COLOR);
                    setForeground(Color.WHITE);
                } else {
                    setBackground(SIDEBAR_COLOR);
                    setForeground(TEXT_COLOR);
                }

                setOpaque(true);

                return component;
            }
        });

        // Переключение между заданиями
        taskList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedTask = taskList.getSelectedValue();

                if (selectedTask != null) {
                    cards.show(taskPanels, selectedTask);
                }
            }
        });

        // Панель со списком заданий
        JScrollPane listScroll = new JScrollPane(taskList);

        listScroll.setPreferredSize(
                new Dimension(230, 0)
        );

        listScroll.setBorder(
                BorderFactory.createEmptyBorder()
        );

        listScroll.getViewport().setBackground(SIDEBAR_COLOR);

        // Размещение элементов окна
        add(listScroll, BorderLayout.WEST);
        add(taskPanels, BorderLayout.CENTER);

        // Настройки окна
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(1000, 600);

        // Запрет изменения размера окна
        setResizable(false);

        // Размещение окна по центру экрана
        setLocationRelativeTo(null);
    }
}
