package ui;

import javax.swing.*;
import java.awt.*;

public class OutBlock extends JScrollPane
{
    private final JTextArea area = new JTextArea();

    public OutBlock()
    {
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        area.setMargin(new Insets(6, 8, 6, 8));

        setViewportView(area);
        setBorder(BorderFactory.createTitledBorder("Вывод"));
        setPreferredSize(new Dimension(600, 220));
    }

    public void println(String text)
    {
        area.append(text + "\n");
        area.setCaretPosition(area.getDocument().getLength());
    }

    public void clear()
    {
        area.setText("");
    }
}
