package app;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AppFrame window = new AppFrame();

            window.addLab("1. Перемещение героя", new MovementPanel());
            window.addLab("2. Аннотации", new AnnotationsPanel());
            window.addLab("3. Переводчик", new TranslatorPanel());
            window.addLab("4. Stream API", new StreamsPanel());

            window.open();
        });
    }
}