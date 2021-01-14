package render;

public class Plane {
	public PointF point;
	public VectorF normal;
	public Material material;
	public float radius;

	public Plane(PointF point, VectorF normal, Material material) {
		this.point=point;
		this.normal=normal.normalize();
		this.material=material;
		material.setColor(255, 255, 255);
		this.radius=0;
	}
	
	public Plane(PointF point, VectorF normal) {
		this.point=point;
		this.normal=normal.normalize();
		this.material=new Material(0);
		material.setColor(255, 255, 255);
		this.radius=0;
	}

	public float distanceToRay(Ray ray) {
		float denominator=normal.dot(ray.direction);
		if(Math.abs(denominator)>.0001) {
			VectorF vectorToPoint=ray.origin.subtractToVectorF(point);
			float t=(vectorToPoint.dot(normal))/denominator;
			if(t>0) {
				VectorF vectorToPlane=ray.getDirection().normalize().multiply(t);
				PointF pointOnPlane=ray.getOrigin().add(vectorToPlane.toPointF());
				VectorF vectorToCenter=point.subtractToVectorF(pointOnPlane);
				if(radius==0) {
					return t;
				}
				if(vectorToCenter.magnitudeSquared()<Math.pow(radius,2)) {
					return t;
				}
			}
		}
		return Integer.MAX_VALUE;
	}


	public PointF getPoint() {
		return point;
	}

	public void setPoint(PointF point) {
		this.point = point;
	}

	public VectorF getNormal() {
		return normal;
	}

	public void setNormal(VectorF normal) {
		this.normal = normal;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}



}
