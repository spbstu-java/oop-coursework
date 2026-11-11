package ui;

import javax.swing.*;
import java.awt.*;

// сверху форма ввода, снизу поле вывода
abstract class TaskPane extends JPanel {
    protected final JTextArea output = new JTextArea();
    private final JPanel form = new JPanel();

    TaskPane() {
        output.setEditable(false);

        JButton clearButton = new JButton("Очистить");
        clearButton.addActionListener(e -> output.setText(""));

        JPanel outputPanel = new JPanel(new BorderLayout());
        outputPanel.setBorder(BorderFactory.createTitledBorder("Вывод"));
        outputPanel.add(new JScrollPane(output), BorderLayout.CENTER);
        outputPanel.add(row(clearButton), BorderLayout.SOUTH);

        // BoxLayout ставит строки формы друг под другом.
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));

        setLayout(new BorderLayout(0, 10));
        add(form, BorderLayout.NORTH);
        add(outputPanel, BorderLayout.CENTER);
    }

    protected void addRow(JComponent row) {
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.add(row);
    }

    protected static JPanel row(Component... components) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        for (Component component : components) {
            panel.add(component);
        }
        return panel;
    }

    protected void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Ошибка", JOptionPane.ERROR_MESSAGE);
    }
}
