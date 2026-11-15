
package coursework;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class Lab2Panel extends LabPanel {

    private static final Color BACKGROUND = new Color(230, 238, 250);
    private static final Color BLUE = new Color(40, 100, 180);

    public Lab2Panel() {
        setBackground(BACKGROUND);

        JLabel title = new JLabel("Задание 2. Аннотации и Reflection");
        title.setFont(new Font("Arial", Font.BOLD, 21));
        title.setForeground(new Color(30, 55, 90));
        title.setBorder(new EmptyBorder(5, 5, 12, 5));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton runButton = createButton("Запустить");
        runButton.addActionListener(e -> runLab());
        runButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.setBackground(BACKGROUND);
        top.setBorder(new EmptyBorder(10, 10, 10, 10));

        top.add(title);
        top.add(runButton);

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
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        PrintStream oldErr = System.err;

        try (PrintStream newOut = new PrintStream(buffer)) {
            System.setOut(newOut);
            System.setErr(newOut);

            lab2.Main.main(new String[0]);

            newOut.flush();
            print(buffer.toString());
        } catch (Exception e) {
            print("Ошибка выполнения: " + e.getMessage());
        } finally {
            System.setOut(oldOut);
            System.setErr(oldErr);
        }
    }
}
