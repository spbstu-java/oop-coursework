package lab1;

import lab1.strategies.IMovable;

public class Hero {
    private final String name;
    private IMovable movable;

    public Hero (String name, IMovable movable)
    {
        this.name = name;
        this.movable = movable;
    }

    public String getName()
    {
        return name;
    }

    public void setMovable(IMovable movable)
    {
        this.movable = movable;
    }

    public String move()
    {
        return movable.move();
    }
}
