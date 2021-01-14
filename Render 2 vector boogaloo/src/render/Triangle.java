package render;

public class Triangle {
	public PointF point1;
	public PointF point2;
	public PointF point3;
	public VectorF normal;
	public Plane plane;
	public Material material;
	
	public Triangle(PointF point1, PointF point2, PointF point3) {
		this.point1=point1;
		this.point2=point2;
		this.point3=point3;
		VectorF oneTwo = point1.subtractToVectorF(point2);
		VectorF oneThree=point1.subtractToVectorF(point3);
		this.normal=oneTwo.cross(oneThree).normalize();
		this.plane=new Plane(point1, normal);
		this.material=new Material(0);
	}
	
	private boolean checkPoint(PointF checkPoint) {
		VectorF edge1=point1.subtractToVectorF(point2);
		VectorF edge2=point2.subtractToVectorF(point3);
		VectorF edge3=point3.subtractToVectorF(point1);
		
		VectorF C1=point1.subtractToVectorF(checkPoint);
		VectorF C2=point2.subtractToVectorF(checkPoint);
		VectorF C3=point3.subtractToVectorF(checkPoint);
		
		if(normal.dot(edge1.cross(C1))>0&&normal.dot(edge2.cross(C2))>0&&normal.dot(edge3.cross(C3))>0) {
			return true;
		}
				
		return false;
	}
	
	public float distanceToRay(Ray ray) {
		float distanceToPlane=plane.distanceToRay(ray);
		if(distanceToPlane<Integer.MAX_VALUE-1) {//ray hits triangles plane
			VectorF vectorToPlane=ray.getDirection().normalize().multiply(distanceToPlane);
			PointF pointOnPlane=ray.getOrigin().add(vectorToPlane.toPointF());
			if(checkPoint(pointOnPlane)) {//if the point is within the triangle
				return distanceToPlane;
			}
		}
		return Integer.MAX_VALUE;
	}
	
}
