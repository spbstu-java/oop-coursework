package ui;

import lab2.Example;
import lab2.Lab2Starter;

import javax.swing.*;

public class Lab2Panel extends TemplatePanel
{
    public Lab2Panel()
    {
        super("Лаба 2. Аннотации и рефлексия");

        var runButton = new JButton("Выполнить");
        runButton.addActionListener(_ -> run());

        init(column(
                description("Будут вызваны все методы класса Example, помеченные @MyAnnotation и имеющие "
                        + "модификатор private или protected - столько раз, сколько указано в параметре аннотации. "
                        + "Аргументы методов подбираются автоматически по их типам."),
                rightAligned(runButton)
        ));
    }

    private void run()
    {
        output.clear();
        new Lab2Starter(output::println).run(new Example(output::println));
    }
}

