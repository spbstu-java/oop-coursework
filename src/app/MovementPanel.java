package app;

import game.Hero;
import game.Point;
import game.movement.Flying;
import game.movement.HorseRiding;
import game.movement.MovementStrategy;
import game.movement.Walking;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;

public class MovementPanel extends LabPanel {
    private static final List<Point> MAP = List.of(
            new Point("Дом"),
            new Point("Лес")
    );

    private static final List<MovementStrategy> STRATEGIES = List.of(
            new Walking(),
            new Flying(),
            new HorseRiding()
    );

    private final Hero hero = new Hero("Void", MAP.get(0), STRATEGIES.get(0));
    private final JComboBox<Point> destination = new JComboBox<>(MAP.toArray(Point[]::new));
    private final JComboBox<String> movement = new JComboBox<>(STRATEGIES.stream().map(MovementStrategy::title).toArray(String[]::new));

    public MovementPanel() {
        JButton move = new JButton("Переместить");
        addRow(labelFor("Куда:", destination), destination, labelFor("Способ:", movement), movement, move);
        appendResult(hero.getName() + " сейчас в точке " + hero.getPosition() + ".");
        move.addActionListener(event -> moveHero());
    }

    private void moveHero() {
        Point target = (Point) destination.getSelectedItem();
        hero.setMovement(STRATEGIES.get(movement.getSelectedIndex()));
        appendResult(hero.move(target));
    }
}
