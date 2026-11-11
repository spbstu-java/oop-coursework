package ui;

import javax.swing.*;
import lab1.*;
import lab1.strategies.*;

class Task1Pane extends TaskPane {
    Task1Pane() {
        JComboBox<MovementStrategy> strategyCombo = new JComboBox<>(new MovementStrategy[]{
                new WalkingStrategy(),
                new HorseRidingStrategy(),
                new FlyingStrategy(),
                new SwimmingStrategy()
        });

        Hero hero = new Hero(strategyCombo.getItemAt(0));

        JButton moveButton = new JButton("Переместиться");
        moveButton.addActionListener(e -> {
            hero.setMovementStrategy((MovementStrategy) strategyCombo.getSelectedItem());
            output.append(hero.move() + "\n");
        });

        addRow(row(new JLabel("Стратегия:"), strategyCombo, moveButton));
    }
}
