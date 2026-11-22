package app;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;

public class AppFrame extends JFrame {
    private final JComboBox<String> labs = new JComboBox<>();
    private final CardLayout layout = new CardLayout();
    private final JPanel cards = new JPanel(layout);

    public AppFrame() {
        super("Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(720, 520));

        JLabel label = new JLabel("Лабораторная работа: ");
        label.setLabelFor(labs);

        JPanel selector = new JPanel(new BorderLayout(12, 0));
        selector.add(label, BorderLayout.WEST);
        selector.add(labs, BorderLayout.CENTER);

        JPanel content = new JPanel(new BorderLayout(0, 12));
        content.setBorder(BorderFactory.createEmptyBorder(12, 16, 16, 16));
        content.add(selector, BorderLayout.NORTH);
        content.add(cards, BorderLayout.CENTER);
        setContentPane(content);

        labs.addActionListener(event -> {
            String selected = (String) labs.getSelectedItem();
            if (selected != null) {
                layout.show(cards, selected);
            }
        });
    }

    public void addLab(String title, JPanel panel) {
        cards.add(panel, title);
        labs.addItem(title);
    }

    public void open() {
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
