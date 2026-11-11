package lab1.strategies;

public class HorseRidingStrategy implements MovementStrategy {
    @Override
    public String move() {
        return "Герой скачет на лошади";
    }

    @Override
    public String toString() {
        return "На лошади";
    }
}
