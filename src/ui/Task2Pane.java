package ui;

import javax.swing.*;
import lab2.*;

class Task2Pane extends TaskPane {
    Task2Pane() {
        JButton runButton = new JButton("Запустить");
        runButton.addActionListener(e -> output.setText(ReflectionRunner.run(new TestClass())));
        addRow(row(new JLabel("Аннотированные методы TestClass:"), runButton));
    }
}
