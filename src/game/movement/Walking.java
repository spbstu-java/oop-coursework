package game.movement;
import game.Point;

public class Walking implements MovementStrategy {
    @Override
    public String title() {
        return "Пешком";
    }

    @Override
    public String move(String heroName, Point from, Point to) {
        return heroName + " идёт пешком из точки " + from + " в точку " + to;
    }
}