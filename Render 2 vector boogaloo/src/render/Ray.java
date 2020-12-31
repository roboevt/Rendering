package render;

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
		//System.out.println("L: "+L.toString());
		double tToCenter=L.dot(this.direction.normalize());
		//System.out.println("distance to center: "+tToCenter);
		if(tToCenter<0) { //sphere is behind ray
			System.out.println("sphere is behind ray");
			return Integer.MAX_VALUE;
		}
		double d=Math.sqrt(Math.pow(L.magnitude(),2)-Math.pow(tToCenter,2));
		//System.out.println("d: "+d);
		if(d<sphere.radius) {//ray hits sphere!
			double tBackToEdge=Math.sqrt(Math.pow(sphere.radius, 2)-Math.pow(d, 2));
			double t0=tToCenter-tBackToEdge;
			double t1=tToCenter+tBackToEdge;
			if(t0<t1) {
				return t0;
			}else {
				return t1;
			}
		} else {
			//System.out.println("ray does not hit sphere");
			return Integer.MAX_VALUE;
		}
	}
	
	
	
	
	public static void main(String[] args) {
		Point origin=new Point(0,0,-10);
		Vector direction=new Vector(0,0,1);
		Ray ray=new Ray(origin,direction);
		
		Point center=new Point(0,.99,0);
		double radius=1;
		Sphere sphere=new Sphere(center,radius);
		
		System.out.println("distance to sphere: "+ray.distanceToSphere(sphere));
	}
}
