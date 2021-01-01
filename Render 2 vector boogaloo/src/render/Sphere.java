package render;

import java.awt.Color;

public class Sphere {
	public Point center;
	public double radius;
	public Material material;
	
	public Sphere(Point center, double radius, Material material) {
		this.center = center;
		this.radius = radius;
		this.material=material;
	}
	
	public static Sphere[] generateFloorSpheres(int numSpheres) {
		Sphere[] spheres=new Sphere[numSpheres];
		spheres[0]=new Sphere(new Point(0,-1000,0),1000,new Material(false));
		Color color=new Color((int)(255*Math.random()),(int)(255*Math.random()),(int)(255*Math.random()));
		spheres[0].setColor(color);
		for(int i=1;i<numSpheres;i++) {
			double y=.2+Math.random()*.2;
			double radius=y;
			boolean reflective=true;
			Point center=new Point(2.5-5*Math.random(),y,2.5-5*Math.random());
			spheres[i]=new Sphere(center,radius,new Material(reflective));
			if(Math.random()>0.5) {
				reflective=false;
				color=new Color((int)(255*Math.random()),(int)(255*Math.random()),(int)(255*Math.random()));
				spheres[i].setColor(color);
				spheres[i].material.reflective=reflective;
			}	
		}
		return spheres;
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
	
	public void setColor(Color color) {
		this.material.color=color;
	}
}
