package coursework;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        // окна Swing создаются в потоке обработки событий
        SwingUtilities.invokeLater(() -> new MainWindow().setVisible(true));
    }
}
