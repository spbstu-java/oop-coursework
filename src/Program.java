import ui.CourseworkFrame;
import javax.swing.SwingUtilities;

public class Program {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CourseworkFrame().setVisible(true));
    }
}
