package gui;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

// Точка входа графического приложения
public final class App {

    private App() {
    }

    public static void main(String[] args) {
        useNimbus();
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }

    private static void useNimbus() {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (Exception e) {
                    // не вышло, останется стандартный вид
                }
                return;
            }
        }
    }
}