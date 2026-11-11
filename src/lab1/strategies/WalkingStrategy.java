package lab1.strategies;

public class WalkingStrategy implements MovementStrategy {
    @Override
    public String move() {
        return "Герой идет пешком";
    }

    @Override
    public String toString() {
        return "Пешком";
    }
}
