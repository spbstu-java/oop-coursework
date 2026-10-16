package gruzdevav.lab1;

public class MoveByPlane extends ACMovement {
	@Override public double getSpeed() {
		return 10;
	}
	@Override public final String getName() {
		return "Самолётом";
	}
}
