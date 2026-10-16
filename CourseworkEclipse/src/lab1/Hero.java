package lab1;


public class Hero {
	public ACMovement wayOfMovement = new MoveByFeet();
	public Point location = new Point(0,0);
	public String move(Point destination) {
		var result = wayOfMovement.move(location, destination);
		location = destination;
		return result;
	}
}
