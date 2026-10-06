package gui;

import task2.domain.Robot;
import task2.invoker.InvocationResult;
import task2.invoker.MethodInvoker;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.util.List;

// Задание 2: вызов аннотированных методов через рефлексию
class AnnotationsPanel extends JPanel {

    private final JTextField nameField = new JTextField("R2D2", 15);

    AnnotationsPanel(OutputPane output) {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton runButton = new JButton("Вызвать аннотированные методы");
        runButton.addActionListener(e -> run(output));

        add(new JLabel("Имя робота:"));
        add(nameField);
        add(runButton);
    }

    private void run(OutputPane output) {
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            output.println("Ошибка: имя робота не должно быть пустым");
            return;
        }

        output.println("Вызов методов объекта Robot(" + name + "):");
        try {
            List<InvocationResult> results = MethodInvoker.invokeAnnotated(new Robot(name));
            for (InvocationResult result : results) {
                output.println(result.methodName() + " (вызов " + result.callNumber() + "): "
                        + result.returnValue());
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            output.println("Ошибка: " + e.getMessage());
        }
    }
}