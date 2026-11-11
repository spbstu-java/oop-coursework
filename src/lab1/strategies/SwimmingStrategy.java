package lab1.strategies;

public class SwimmingStrategy implements MovementStrategy {
    @Override
    public String move() {
        return "Герой плывет";
    }

    @Override
    public String toString() {
        return "Плыть";
    }
}
