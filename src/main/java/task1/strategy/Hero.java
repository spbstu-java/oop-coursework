package task1.strategy;

import java.util.Objects;

/**
 * A game character whose movement behaviour can be swapped at runtime
 * (classic Strategy pattern). Hero delegates entirely to the current
 * {@link MovementStrategy} and never performs I/O itself.
 */
public final class Hero {

    private MovementStrategy movementStrategy;

    /**
     * @throws NullPointerException if {@code movementStrategy} is null
     */
    public Hero(MovementStrategy movementStrategy) {
        this.movementStrategy = requireStrategy(movementStrategy);
    }

    /**
     * @throws NullPointerException if {@code movementStrategy} is null
     */
    public void setMovementStrategy(MovementStrategy movementStrategy) {
        this.movementStrategy = requireStrategy(movementStrategy);
    }

    public String move() {
        return movementStrategy.move();
    }

    private static MovementStrategy requireStrategy(MovementStrategy strategy) {
        return Objects.requireNonNull(strategy, "Movement strategy cannot be null");
    }
}