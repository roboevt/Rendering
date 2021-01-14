package render;

public class Plane {
	public PointF point;
	public VectorF normal;
	public Material material;

	public Plane(PointF point, VectorF normal, Material material) {
		this.point=point;
		this.normal=normal.normalize();
		this.material=material;
		material.setColor(255, 255, 255);
	}

	public float distanceToRay(Ray ray) {
		float denominator=normal.dot(ray.direction);
		if(Math.abs(denominator)>.0001) {
			VectorF vectorToPoint=ray.origin.subtractToVectorF(point);
			float t=(vectorToPoint.dot(normal))/denominator;
			if(t>0) {
				return t;
				
			}else {
				return Integer.MAX_VALUE;
			}
		}else {
			return Integer.MAX_VALUE;
		}
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
