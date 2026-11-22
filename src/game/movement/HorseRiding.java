package game.movement;
import game.Point;

public class HorseRiding implements MovementStrategy {
    @Override
    public String title() {
        return "Езда на лошади";
    }

    @Override
    public String move(String heroName, Point from, Point to) {
        return heroName + " скачет на лошади из точки " + from + " в точку " + to;
    }
}