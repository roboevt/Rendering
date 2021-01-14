package render;

import java.awt.Color;

public class Sphere {
	public PointF center;
	public float radius;
	public Material material;
	
	public Sphere(PointF center, float radius, Material material) {
		this.center = center;
		this.radius = radius;
		this.material=material;
	}
	
	public static Sphere[] generateRandomSpheres(int numSpheres) {
		Sphere[] spheres=new Sphere[numSpheres+1];
		Color color=new Color((int)(255*Math.random()),(int)(255*Math.random()),(int)(255*Math.random()));
		spheres[0]=new Sphere(new PointF(0,-1000,0), 1000, new Material(false));
		spheres[0].setColor(color);
		for(int i=1;i<numSpheres+1;i++) {
			float y=(float) (.2+Math.random()*.2);
			float radius=y;
			boolean reflective=true;
			PointF center=new PointF((float)(2.5-5*Math.random()),y,(float)(2.5-5*Math.random()));
			spheres[i]=new Sphere(center,radius,new Material(reflective));
			if(Math.random()>.5) {//number represents probability of reflective sphere
				reflective=false;
				color=new Color((int)(255*Math.random()),(int)(255*Math.random()),(int)(255*Math.random()));
				spheres[i].setColor(color);
				spheres[i].material.reflective=reflective;
			}	
		}
		return spheres;
	}
	
	public float distanceToRay(Ray ray) { //this method takes the most time of them all. How do I optimize it? Is it just called a lot?
		VectorF L = ray.origin.subtractToVectorF(this.getCenter()); 
		float tToCenter=L.dot(ray.direction.normalize());
		float d=(float) Math.sqrt(Math.pow(L.magnitude(),2)-Math.pow(tToCenter,2));
		if(tToCenter>0) {//if the sphere is in front of the ray
			if(d<this.radius) {//if the ray hits the sphere
				float tBackToEdge=(float) Math.sqrt(Math.pow(this.radius, 2)-Math.pow(d, 2));
				float t0=tToCenter-tBackToEdge;
				float t1=tToCenter+tBackToEdge;
				if(t0<t1) {
					return t0;
				}else{
					return t1;
				}
			}else {
				return Integer.MAX_VALUE;
			}
		}else {
			return Integer.MAX_VALUE;
		}
	}

	public PointF getCenter() {
		return center;
	}

	public void setCenter(PointF center) {
		this.center = center;
	}
	public void setX(float x) {
		center.x=x;
	}
	
	public void setY(float y) {
		center.y=y;
	}
	
	public void setZ(float z) {
		center.z=z;
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}
	
	public void setColor(int r, int g, int b) {
		this.material.setColor(r, g, b);
	}
	
	public void setColor(Color color) {
		this.material.color=color;
	}
}
