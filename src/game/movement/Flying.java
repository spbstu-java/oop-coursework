package game.movement;
import game.Point;

public class Flying implements MovementStrategy {
    @Override
    public String title() {
        return "Летит";
    }

    @Override
    public String move(String heroName, Point from, Point to) {
        return heroName + " летит из точки " + from + " в точку " + to;
    }
}
