package game;

// Точка на карте
public class Point {
    private final String name;

    public Point(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override 
    public String toString() {
        return name;
    }
}
