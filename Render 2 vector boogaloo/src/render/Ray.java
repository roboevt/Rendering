package render;

import java.util.Arrays;

public class Ray {
	public PointF origin;
	public VectorF direction;

	public Ray(PointF origin, VectorF direction) {
		this.origin = origin;
		this.direction = direction;
	}

	public PointF getOrigin() {
		return origin;
	}

	public void setOrigin(PointF origin) {
		this.origin = origin;
	}

	public VectorF getDirection() {
		return direction;
	}

	public void setDirection(VectorF direction) {
		this.direction = direction;
	}

	public float distanceToSphere(Sphere sphere) {
		VectorF L = this.origin.subtractToVectorF(sphere.getCenter());
		float tToCenter=L.dot(this.direction.normalize());
		if(tToCenter<0) { //sphere is behind ray
			System.out.println("sphere is behind ray");
			return Integer.MAX_VALUE;
		}
		float d=(float) Math.sqrt(Math.pow(L.magnitude(),2)-Math.pow(tToCenter,2));
		if(d<sphere.radius) {//ray hits sphere!
			float tBackToEdge=(float) Math.sqrt(Math.pow(sphere.radius, 2)-Math.pow(d, 2));
			float t0=tToCenter-tBackToEdge;
			float t1=tToCenter+tBackToEdge;
			if(t0<t1) {
				return t0;
			}else {
				return t1;
			}
		} else {//ray does not hit sphere
			return Integer.MAX_VALUE;
		}
	}

	public float distanceToSpheres(Sphere[] spheres) {
		float minDistance=Integer.MAX_VALUE;
		for(Sphere sphere:spheres) {		
			VectorF L = this.origin.subtractToVectorF(sphere.getCenter()); 
			float tToCenter=L.dot(this.direction.normalize());
			float d=(float) Math.sqrt(Math.pow(L.magnitude(),2)-Math.pow(tToCenter,2));
			if(tToCenter>0) {//if the sphere is in front of the ray
				if(d<sphere.radius) {//if the ray hits the sphere
					float tBackToEdge=(float) Math.sqrt(Math.pow(sphere.radius, 2)-Math.pow(d, 2));
					float t0=tToCenter-tBackToEdge;
					float t1=tToCenter+tBackToEdge;
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

	public Ray calculateReflection(Ray normal,PointF pointOnSphere) {
		normal.setDirection(normal.getDirection().normalize()); //normalize
		float dot=normal.getDirection().dot(this.getDirection()); //(normal dot incoming)
		VectorF step2=normal.getDirection().multiply(2*dot); //(2*normal)*
		VectorF reflection=this.getDirection().subtract(step2);
		return new Ray(pointOnSphere,reflection);
	}

	public static void main(String[] args) {
	}
}
