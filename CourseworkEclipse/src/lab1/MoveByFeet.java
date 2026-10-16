package lab1;

public class MoveByFeet extends ACMovement {
	@Override public double getSpeed() {
		return 0.5;
	}
	@Override public final String getName() {
		return "Пешком";
	}
}
