package ui;

import javax.swing.*;
import java.awt.*;

public abstract class TemplatePanel extends JPanel
{
    private final String title;
    protected final OutBlock output = new OutBlock();

    protected TemplatePanel(String title)
    {
        super(new BorderLayout(0, 8));
        this.title = title;
        setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
    }

    protected void init(JComponent inputs)
    {
        var clearButton = new JButton("Очистить вывод");
        clearButton.addActionListener(_ -> output.clear());

        var footer = rightAligned(clearButton);

        add(inputs, BorderLayout.NORTH);
        add(output, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);
    }

    public String getTitle()
    {
        return title;
    }

    protected static JPanel row(String label, JComponent field, JComponent east)
    {
        var panel = new JPanel(new BorderLayout(8, 0));
        var jLabel = new JLabel(label);
        jLabel.setPreferredSize(new Dimension(130, jLabel.getPreferredSize().height));
        panel.add(jLabel, BorderLayout.WEST);
        panel.add(field, BorderLayout.CENTER);
        if (east != null)
        {
            panel.add(east, BorderLayout.EAST);
        }
        return panel;
    }

    protected static JPanel column(JComponent... items)
    {
        var panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        for (var i = 0; i < items.length; i++)
        {
            if (i > 0)
            {
                panel.add(Box.createVerticalStrut(6));
            }
            items[i].setAlignmentX(LEFT_ALIGNMENT);
            panel.add(items[i]);
        }
        return panel;
    }

    protected static JTextArea description(String text)
    {
        var textArea = new JTextArea(text);
        textArea.setEditable(false);
        textArea.setFocusable(false);
        textArea.setOpaque(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new JLabel().getFont());
        return textArea;
    }

    protected static JPanel rightAligned(JComponent component)
    {
        var panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        panel.add(component);
        return panel;
    }
}