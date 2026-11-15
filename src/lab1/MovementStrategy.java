
package lab1;

interface MovementStrategy {
    void move();
}

class Walking implements MovementStrategy {
    public void move() {
        System.out.println("Иду пешком!");
    }
}

class HorseRiding implements MovementStrategy {
    public void move() {
        System.out.println("Еду на лошади!");
    }
}

class Flying implements MovementStrategy {
    public void move() {
        System.out.println("Лечу!");
    }
}
