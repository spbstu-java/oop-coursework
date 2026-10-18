package ui;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame
{
    public MainWindow()
    {
        super("Курсовая работа");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        TemplatePanel[] tasks = {new Lab1Panel(), new Lab2Panel(), new Lab3Panel(), new Lab4Panel()};

        var tabs = new JTabbedPane();
        for (var task : tasks)
        {
            tabs.addTab(task.getTitle(), task);
        }

        var content = new JPanel(new BorderLayout());
        content.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));
        content.add(tabs, BorderLayout.CENTER);
        setContentPane(content);

        setSize(1200, 800);
        setLocationRelativeTo(null);
    }
}