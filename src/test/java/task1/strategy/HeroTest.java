package task1.strategy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HeroTest {

    @Test
    void constructorRejectsNullStrategy() {
        assertThrows(NullPointerException.class, () -> new Hero(null));
    }

    @Test
    void setMovementStrategyRejectsNull() {
        Hero hero = new Hero(new WalkingStrategy());
        assertThrows(NullPointerException.class, () -> hero.setMovementStrategy(null));
    }

    @Test
    void moveDelegatesToCurrentStrategy() {
        Hero hero = new Hero(new WalkingStrategy());
        assertEquals("Walking", hero.move());

        hero.setMovementStrategy(new FlyingStrategy());
        assertEquals("Flying", hero.move());

        hero.setMovementStrategy(new HorseRidingStrategy());
        assertEquals("Riding a horse", hero.move());
    }
}