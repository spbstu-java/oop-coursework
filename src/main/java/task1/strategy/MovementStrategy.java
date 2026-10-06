package task1.strategy;

/**
 * Strategy for how a {@link Hero} moves between two points.
 * Implementations must stay free of any I/O — they only compute
 * the result, the caller decides how/where to display it.
 */
public sealed interface MovementStrategy
        permits WalkingStrategy, HorseRidingStrategy, FlyingStrategy {

    /**
     * Performs the movement and returns a human-readable description of it.
     */
    String move();
}