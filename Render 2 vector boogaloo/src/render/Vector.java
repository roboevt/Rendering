package render;

public class Vector {
	public double x;
	public double y;
	public double z;
	
	public Vector(double x, double y, double z) {
		super();
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
		return x+" "+y+" "+z+" ";
	}
	
	public Point toPoint() {
		return new Point(this.x,this.y,this.z);
	}
	
	public Vector subtract(Vector vector) {
		return new Vector(this.x-vector.x,this.y-vector.y,this.z-vector.z);
	}
	
	public Vector add(Vector vector) {
		return new Vector(this.x+vector.x,this.y+vector.y,this.z+vector.z);
	}
	
	public Vector multiply(Double scalar) {
		return new Vector(this.x*scalar,this.y*scalar,this.z*scalar);
	}
	
	public double magnitude() {
		return Math.sqrt(Math.pow(this.x, 2)+Math.pow(this.y, 2)+Math.pow(this.z, 2));
	}
	
	public Vector normalize() {
		return new Vector(this.x/this.magnitude(),this.y/this.magnitude(),this.z/this.magnitude());
	}
	
	public double dot(Vector vector) {
		return (this.x*vector.x)+(this.y*vector.y)+(this.z*vector.z);
	}
	
	public Vector cross(Vector vector) {
		double xCross=this.y*vector.z-this.z*vector.y;
		double yCross=this.z*vector.x-this.x*vector.z;
		double zCross=this.x*vector.y-this.y*vector.x;
		
		return new Vector(xCross,yCross,zCross);
	}
	
	public static void main(String[] args) {
		Vector vector=new Vector(2,3,4);
		System.out.println(vector.toString());
		System.out.println(vector.normalize().toString());
	}
}
