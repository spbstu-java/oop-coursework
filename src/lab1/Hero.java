package lab1;

import lab1.strategies.MovementStrategy;

public class Hero {
    private MovementStrategy movementStrategy;

    public Hero(MovementStrategy movementStrategy) {
        this.movementStrategy = movementStrategy;
    }

    public void setMovementStrategy(MovementStrategy movementStrategy) {
        this.movementStrategy = movementStrategy;
    }

    public String move() {
        return movementStrategy.move();
    }
}
