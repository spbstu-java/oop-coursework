package ui;

import lab1.Hero;
import lab1.strategies.*;

import javax.swing.*;
import java.util.function.Supplier;

public class Lab1Panel extends TemplatePanel
{
    private record Option(String title, Supplier<IMovable> factory)
    {
        @Override
        public String toString()
        {
            return title;
        }
    }

    private final String heroNameDefault = "Hero";
    private final JTextField nameField = new JTextField(heroNameDefault);
    private final JComboBox<Option> strategyBox = new JComboBox<>(new Option[]
    {
        new Option("Идти пешком", FootMoving::new),
        new Option("Ехать на лошади", HorseMoving::new),
        new Option("Ехать на мотоцикле", BikeMoving::new),
        new Option("Ехать на автомобиле", CarMoving::new)
    });

    private Hero hero;

    public Lab1Panel()
    {
        super("Лаба 1. Герой и способы перемещения");

        var moveButton = new JButton("Переместить героя");
        moveButton.addActionListener(_ -> move());

        init(column(
                description("Введите имя героя, выберите способ перемещения и нажмите кнопку. "
                        + "Способ перемещения можно менять в любой момент - герой сохраняется."),
                row("Имя героя:", nameField, null),
                row("Способ перемещения:", strategyBox, moveButton)
        ));
    }

    private void move()
    {
        var heroName = nameField.getText().trim();
        if (heroName.isBlank())
        {
            heroName = heroNameDefault;
        }

        var movableSelected = (Option) strategyBox.getSelectedItem();
        if (movableSelected == null) {
            output.println("Способ перемещения не выбран");
            return;
        }
        var strategy = movableSelected.factory().get();

        if (hero == null || !heroName.equals(hero.getName()))
        {
            hero = new Hero(heroName, strategy);
        } else
        {
            hero.setMovable(strategy);
        }

        output.println("Герой " + hero.getName() + " " + hero.move());
    }
}

