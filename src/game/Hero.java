package game;
import game.movement.MovementStrategy;

public class Hero {
    private final String name;
    private Point position;
    private MovementStrategy movement;

    public Hero(String name, Point start, MovementStrategy movement) {
        this.name = name;
        this.position = start;
        setMovement(movement);
    }

    public void setMovement(MovementStrategy movement) {
        if (movement == null) {
            throw new IllegalArgumentException("Способ перемещения не задан");
        }

        this.movement = movement;
    }

    public MovementStrategy getMovement() {
        return movement;
    }

    public String move(Point destination) {
        String message = movement.move(name, position, destination);
        position = destination;
        return message;
    }

    public String getName() {
        return name;
    }

    public Point getPosition() {
        return position;
    }
}
