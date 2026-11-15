
package coursework;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;


public class Lab1Panel extends LabPanel {

    private static final Color BACKGROUND = new Color(230, 238, 250);
    private static final Color BLUE = new Color(40, 100, 180);

    private final JTextField choiceField = new JTextField("1", 5);

    public Lab1Panel() {
        setBackground(BACKGROUND);

        JLabel title = new JLabel("Задание 1. Стратегия");
        title.setFont(new Font("Arial", Font.BOLD, 21));
        title.setForeground(new Color(30, 55, 90));
        title.setBorder(new EmptyBorder(5, 5, 12, 5));

        JLabel choiceLabel = new JLabel(
                "Способ перемещения: 1 — пешком, 2 — на лошади, 3 — лететь"
        );
        choiceLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton runButton = createButton("Запустить");

        runButton.addActionListener(e -> runLab());

        JPanel controls = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 10, 8)
        );
        controls.setBackground(BACKGROUND);
        controls.add(choiceLabel);
        controls.add(choiceField);
        controls.add(runButton);

        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.setBackground(BACKGROUND);

        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        controls.setAlignmentX(Component.LEFT_ALIGNMENT);

        top.add(title);
        top.add(controls);

        add(top, BorderLayout.NORTH);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setForeground(Color.WHITE);
        button.setBackground(BLUE);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(9, 15, 9, 15));
        button.setOpaque(true);
        return button;
    }



    private void runLab() {
        String choice = choiceField.getText().trim();

        if (!choice.matches("[1-3]")) {
            print("Введите число от 1 до 3.");
            return;
        }

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        PrintStream oldOut = System.out;
        PrintStream oldErr = System.err;
        java.io.InputStream oldIn = System.in;

        String input = choice + System.lineSeparator()
                + "0" + System.lineSeparator();

        try (PrintStream newOut = new PrintStream(buffer)) {

            System.setOut(newOut);
            System.setErr(newOut);

            System.setIn(new java.io.ByteArrayInputStream(
                    input.getBytes(java.nio.charset.StandardCharsets.UTF_8)
            ));

            Class<?> mainClass = Class.forName("lab1.Main");

            Method mainMethod = mainClass.getMethod(
                    "main", String[].class
            );

            // Выполняем исходную лабораторную
            mainMethod.invoke(null, (Object) new String[0]);

        } catch (Exception e) {
            Throwable cause = e;

            while (cause.getCause() != null) {
                cause = cause.getCause();
            }

            System.setOut(oldOut);
            System.setErr(oldErr);
            System.setIn(oldIn);

            print("Ошибка лабораторной: "
                    + cause.getClass().getSimpleName()
                    + (cause.getMessage() != null
                    ? " — " + cause.getMessage()
                    : ""));
            return;

        } finally {
            System.setOut(oldOut);
            System.setErr(oldErr);
            System.setIn(oldIn);
        }

        switch (choice) {
            case "1":
                print("Герой двигается пешком!");
                break;
            case "2":
                print("Герой едет на лошади!");
                break;
            case "3":
                print("Герой летит!");
                break;
        }
    }
}
