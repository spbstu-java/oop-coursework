package lab1.strategies;

public class FlyingStrategy implements MovementStrategy {
    @Override
    public String move() {
        return "Герой летит";
    }

    @Override
    public String toString() {
        return "Лететь";
    }
}
