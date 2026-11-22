package game.movement;
import game.Point;

public interface MovementStrategy {
    String title();

    String move(String heroName, Point from, Point to);
}