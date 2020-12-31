package render;

public class Point {
	public double x;
	public double y;
	public double z;
	
	public Point(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}
	
	public String toString() {
		return this.x+" "+this.y+" "+this.z;
	}
	
	public Vector toVector() {
		return new Vector(this.x,this.y,this.z);
	}
	
	public Point add(Point point) {
		return new Point(this.x+point.x,this.y+point.y,this.z+point.z);
	}
	
	public Vector subtractToVector(Point point) {
		return new Vector(point.x-this.x,point.y-this.y,point.z-this.z);
	}
}
