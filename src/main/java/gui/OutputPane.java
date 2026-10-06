package gui;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

// Область вывода, текст в ней нельзя редактировать
class OutputPane extends JScrollPane {

    private final JTextArea textArea = new JTextArea(12, 60);

    OutputPane() {
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        setViewportView(textArea);
        setBorder(BorderFactory.createTitledBorder("Вывод"));
    }

    void println(String line) {
        textArea.append(line + "\n");
        // прокручиваем вниз, чтобы видеть последнюю строку
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    void clear() {
        textArea.setText("");
    }
}