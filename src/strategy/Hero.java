package strategy;

public class Hero {
    private MoveStrategy moveStrategy;

    public Hero(MoveStrategy moveStrategy) {
        this.moveStrategy = moveStrategy;
    }

    public void setMoveStrategy(MoveStrategy moveStrategy) {
        this.moveStrategy = moveStrategy;
    }

    public String move() {
        return moveStrategy.move();
    }
}
