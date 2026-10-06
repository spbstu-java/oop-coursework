package gui;

import task1.strategy.FlyingStrategy;
import task1.strategy.Hero;
import task1.strategy.HorseRidingStrategy;
import task1.strategy.MovementStrategy;
import task1.strategy.WalkingStrategy;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.util.function.Supplier;

// Задание 1: герой и смена способа перемещения
class StrategyPanel extends JPanel {

    // способ перемещения: название для списка и создание стратегии
    private enum Mode {
        WALKING("Пешком", WalkingStrategy::new),
        HORSE("На лошади", HorseRidingStrategy::new),
        FLYING("По воздуху", FlyingStrategy::new);

        private final String title;
        private final Supplier<MovementStrategy> factory;

        Mode(String title, Supplier<MovementStrategy> factory) {
            this.title = title;
            this.factory = factory;
        }

        @Override
        public String toString() {
            return title;
        }
    }

    private final Hero hero = new Hero(new WalkingStrategy());

    StrategyPanel(OutputPane output) {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        JComboBox<Mode> modeBox = new JComboBox<>(Mode.values());
        JButton moveButton = new JButton("Переместить героя");

        // при выборе в списке меняем стратегию героя
        modeBox.addActionListener(e -> {
            Mode mode = (Mode) modeBox.getSelectedItem();
            hero.setMovementStrategy(mode.factory.get());
            output.println("Способ перемещения изменён: " + mode);
        });

        moveButton.addActionListener(e -> output.println("Герой: " + hero.move()));

        add(new JLabel("Способ перемещения:"));
        add(modeBox);
        add(moveButton);
    }
}