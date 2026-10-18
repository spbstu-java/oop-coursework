package ui;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class FilePickerPanel extends JPanel
{
    private static final String userDir = "user.dir";

    private final JTextField field = new JTextField();
    private final String dialogTitle;

    public FilePickerPanel(String dialogTitle)
    {
        super(new BorderLayout(6, 0));
        this.dialogTitle = dialogTitle;

        var button = new JButton("Выбрать…");
        button.addActionListener(_ -> choose());

        add(field, BorderLayout.CENTER);
        add(button, BorderLayout.EAST);
    }

    private void choose()
    {
        var current = new File(getPath()).getAbsoluteFile();
        var startDir = current.getParentFile() != null && current.getParentFile().isDirectory()
            ? current.getParentFile()
            : new File(System.getProperty(userDir));

        var chooser = new JFileChooser(startDir);
        chooser.setDialogTitle(dialogTitle);

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
        {
            field.setText(chooser.getSelectedFile().getAbsolutePath());
        }
    }

    public String getPath()
    {
        return field.getText().trim();
    }

    @Override
    public void setEnabled(boolean enabled)
    {
        super.setEnabled(enabled);
        for (Component c : getComponents())
        {
            c.setEnabled(enabled);
        }
    }
}
