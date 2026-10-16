package lab1;

public abstract class ACMovement {
	public abstract double getSpeed();
	public abstract String getName();
	public String  move(Point start, Point end) {
		double timeInTravel = start.absDiff(end) / getSpeed();
		return "Способ передвижения -  "+ getName() + String.format(". Это заняло %.2f день (дня, дней).", timeInTravel);
	}
}
