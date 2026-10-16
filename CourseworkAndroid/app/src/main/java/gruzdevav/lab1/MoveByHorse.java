package gruzdevav.lab1;

public class MoveByHorse extends ACMovement {
	@Override public double getSpeed() {
		return 2;
	}
	@Override public final String getName() {
		return "На лошади";
	}
}
