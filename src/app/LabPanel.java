package app;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

public abstract class LabPanel extends JPanel {
    private final JPanel inputs = new JPanel(new GridBagLayout());
    private final JTextArea output = createTextArea(11, 44);

    protected LabPanel() {
        super(new BorderLayout(0, 8));
        output.setEditable(false);

        JScrollPane result = new JScrollPane(output);
        result.setBorder(BorderFactory.createTitledBorder("Результат"));
        add(inputs, BorderLayout.NORTH);
        add(result, BorderLayout.CENTER);
    }

    protected static JTextArea createTextArea(int rows, int columns) {
        JTextArea area = new JTextArea(rows, columns);
        area.setFont(UIManager.getFont("Label.font").deriveFont(14f));
        area.setMargin(new Insets(5, 6, 5, 6));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        return area;
    }

    protected void addInput(JComponent component) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = inputs.getComponentCount();
        constraints.weightx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(0, 0, 6, 0);
        inputs.add(component, constraints);
    }

    protected void addRow(JComponent... components) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEADING, 6, 2));
        for (JComponent component : components) {
            row.add(component);
        }
        addInput(row);
    }

    protected static JLabel labelFor(String text, JComponent field) {
        JLabel label = new JLabel(text);
        label.setLabelFor(field);
        return label;
    }

    protected void showResult(String text) {
        output.setText(text);
        output.setCaretPosition(0);
    }

    protected void appendResult(String text) {
        output.append(text + "\n");
        output.setCaretPosition(output.getDocument().getLength());
    }
}
