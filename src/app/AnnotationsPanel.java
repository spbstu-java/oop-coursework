package app;

import annotations.AnnotationInvoker;
import annotations.Worker;
import javax.swing.JButton;
import javax.swing.JTextField;

public class AnnotationsPanel extends LabPanel {
    private final JTextField name = new JTextField("Java", 14);
    private final JTextField number = new JTextField("3", 6);

    public AnnotationsPanel() {
        JButton run = new JButton("Вызвать методы");
        addRow(labelFor("Имя:", name), name, labelFor("Целое число:", number), number, run);
        run.addActionListener(event -> runDemo());
    }

    private void runDemo() {
        String enteredName = name.getText().strip();
        if (enteredName.isEmpty()) {
            showResult("Введите имя");
            return;
        }

        int enteredNumber;
        try {
            enteredNumber = Integer.parseInt(number.getText().strip());
        } catch (NumberFormatException e) {
            showResult("Введите целое число");
            return;
        }

        Worker worker = new Worker(this::appendResult);
        AnnotationInvoker invoker = new AnnotationInvoker(enteredName, enteredNumber, this::appendResult);

        showResult("");
        appendResult("Публичные методы:");
        worker.greet("Даниил");
        worker.printSum(5, 6);
        worker.printFirstLetter("Hello");

        appendResult("");
        appendResult("Аннотированные protected и private методы:");
        try {
            invoker.invokeAnnotated(worker);
        } catch (IllegalArgumentException | IllegalStateException e) {
            appendResult("Ошибка вызова: " + e.getMessage());
        }
    }
}
