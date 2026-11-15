package lab1;

public class Hero {
    private MovementStrategy strategy;

    public void setStrategy(MovementStrategy strategy) {
        this.strategy = strategy;
    }

    public void Move(){
        strategy.move();
    }
}