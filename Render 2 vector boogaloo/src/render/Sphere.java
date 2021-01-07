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
	
	public static Sphere[] generateRandomSpheres(int numSpheres) {
		Sphere[] spheres=new Sphere[numSpheres+1];
		Color color=new Color((int)(255*Math.random()),(int)(255*Math.random()),(int)(255*Math.random()));
		spheres[0]=new Sphere(new Point(0,-1000,0), 1000, new Material(false));
		spheres[0].setColor(color);
		for(int i=1;i<numSpheres+1;i++) {
			double y=.2+Math.random()*.2;
			double radius=y;
			boolean reflective=true;
			Point center=new Point(2.5-5*Math.random(),y,2.5-5*Math.random());
			spheres[i]=new Sphere(center,radius,new Material(reflective));
			if(Math.random()>.5) {//number represents probability of reflective sphere
				reflective=false;
				color=new Color((int)(255*Math.random()),(int)(255*Math.random()),(int)(255*Math.random()));
				spheres[i].setColor(color);
				spheres[i].material.reflective=reflective;
			}	
		}
		/*Sphere[] spheres=new Sphere[3];
		spheres[0]=new Sphere(new Point(0,-1000,0),999,new Material(false));
		spheres[1]=new Sphere(new Point(0,0,0),2,new Material(false));
		spheres[2]=new Sphere(new Point(3,0,0),2,new Material(true));*/
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
