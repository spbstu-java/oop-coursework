import java.util.function.Consumer;

public class Hero {
    private Movement movement = new Walk();

    public void setMovement(Movement movement) {
        this.movement = movement;
    }

    public void move(Consumer<String> output) {
        output.accept(movement.move());
    }
}

interface Movement {
    String move();
}

class Walk implements Movement {
    public String move() { return "Герой идёт пешком"; }
}

class Run implements Movement {
    public String move() { return "Герой бежит"; }
}

class Ride implements Movement {
    public String move() { return "Герой едет верхом"; }
}

class Fly implements Movement {
    public String move() { return "Герой летит"; }
}
