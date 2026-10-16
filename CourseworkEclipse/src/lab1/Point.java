package lab1;

import java.lang.Math;

public class Point {
	public double x;
	public double y;
	
	public Point(double x_, double y_) {
		this.x=x_;
		this.y=y_;
	}

	public Point add(Point that) {
		return new Point(this.x+that.x, this.y+that.y);
	}
	public Point sub(Point that) {
		return new Point(this.x-that.x, this.y-that.y);
	}
	public double absDiff(Point that) {
		double x = this.x-that.x;
		double y = this.y-that.y;
		
		x *= x;
		y *= y;
		
		return Math.sqrt(x+y);
	}
}
