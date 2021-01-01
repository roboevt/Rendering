package render;

public class Sphere {
	public Point center;
	public double radius;
	public Material material;
	
	public Sphere(Point center, double radius, Material material) {
		this.center = center;
		this.radius = radius;
		this.material=material;
	}

	public Point getCenter() {
		return center;
	}

	public void setCenter(Point center) {
		this.center = center;
	}
	public void setX(double x) {
		center.x=x;
	}
	
	public void setY(double y) {
		center.y=y;
	}
	
	public void setZ(double z) {
		center.z=z;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}
	
	public void setColor(int r, int g, int b) {
		this.material.setColor(r, g, b);
	}
}
