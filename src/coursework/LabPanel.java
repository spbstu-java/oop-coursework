
package coursework;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public abstract class LabPanel extends JPanel {

    private static final Color BACKGROUND = new Color(230, 238, 250);
    private static final Color OUTPUT_BACKGROUND = Color.WHITE;

    private final JTextArea output = new JTextArea();

    protected LabPanel() {
        super(new BorderLayout(12, 12));

        setBackground(BACKGROUND);
        setBorder(new EmptyBorder(15, 15, 15, 15));

        output.setEditable(false);
        output.setLineWrap(true);
        output.setWrapStyleWord(true);
        output.setFont(new Font("Arial", Font.PLAIN, 14));
        output.setBackground(OUTPUT_BACKGROUND);
        output.setForeground(new Color(35, 45, 65));
        output.setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(output);
        scrollPane.setBorder(BorderFactory.createLineBorder(
                new Color(190, 205, 225)
        ));
        scrollPane.getViewport().setBackground(OUTPUT_BACKGROUND);

        // Область вывода занимает центр панели.
        add(scrollPane, BorderLayout.CENTER);
    }

    protected void print(String text) {
        output.append(text + "\n");
        output.setCaretPosition(output.getDocument().getLength());
    }
}
