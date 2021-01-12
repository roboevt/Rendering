package render;

import java.util.Arrays;

public class Ray {
	public Point origin;
	public Vector direction;

	public Ray(Point origin, Vector direction) {
		this.origin = origin;
		this.direction = direction;
	}

	public Point getOrigin() {
		return origin;
	}

	public void setOrigin(Point origin) {
		this.origin = origin;
	}

	public Vector getDirection() {
		return direction;
	}

	public void setDirection(Vector direction) {
		this.direction = direction;
	}

	public double distanceToSphere(Sphere sphere) {
		Vector L = this.origin.subtractToVector(sphere.getCenter());
		double tToCenter=L.dot(this.direction.normalize());
		if(tToCenter<0) { //sphere is behind ray
			System.out.println("sphere is behind ray");
			return Integer.MAX_VALUE;
		}
		double d=Math.sqrt(Math.pow(L.magnitude(),2)-Math.pow(tToCenter,2));
		if(d<sphere.radius) {//ray hits sphere!
			double tBackToEdge=Math.sqrt(Math.pow(sphere.radius, 2)-Math.pow(d, 2));
			double t0=tToCenter-tBackToEdge;
			double t1=tToCenter+tBackToEdge;
			if(t0<t1) {
				return t0;
			}else {
				return t1;
			}
		} else {//ray does not hit sphere
			return Integer.MAX_VALUE;
		}
	}

	public double distanceToSpheres(Sphere[] spheres) {
		double minDistance=Integer.MAX_VALUE;
		for(Sphere sphere:spheres) {		
			Vector L = this.origin.subtractToVector(sphere.getCenter()); 
			double tToCenter=L.dot(this.direction.normalize());
			double d=Math.sqrt(Math.pow(L.magnitude(),2)-Math.pow(tToCenter,2));
			if(tToCenter>0) {//if the sphere is in front of the ray
				if(d<sphere.radius) {//if the ray hits the sphere
					double tBackToEdge=Math.sqrt(Math.pow(sphere.radius, 2)-Math.pow(d, 2));
					double t0=tToCenter-tBackToEdge;
					double t1=tToCenter+tBackToEdge;
					if(t0<t1&&t0<minDistance) {//which intersection point is closer and is this closer than any other point?
						minDistance=t0;
					}else if(t1<minDistance){
						minDistance=t1;
					}
				}
			}
		}
		return minDistance;
	}

	public Ray calculateReflection(Ray normal,Point pointOnSphere) {
		normal.setDirection(normal.getDirection().normalize()); //normalize
		double dot=normal.getDirection().dot(this.getDirection()); //(normal dot incoming)
		Vector step2=normal.getDirection().multiply(2*dot); //(2*normal)*
		Vector reflection=this.getDirection().subtract(step2);
		return new Ray(pointOnSphere,reflection);
	}

	public static void main(String[] args) {
	}
}
