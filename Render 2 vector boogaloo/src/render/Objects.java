package render;

public class Objects {
	public Sphere[] spheres;
	public Plane[] planes;
	public Triangle[] triangles;
	
	public Objects(Sphere[] spheres, Plane[] planes, Triangle[] triangles) {
		this.spheres = spheres;
		this.planes = planes;
		this.triangles = triangles;
	}

	public Sphere[] getSpheres() {
		return spheres;
	}

	public void setSpheres(Sphere[] spheres) {
		this.spheres = spheres;
	}

	public Plane[] getPlanes() {
		return planes;
	}

	public void setPlanes(Plane[] planes) {
		this.planes = planes;
	}

	public Triangle[] getTriangles() {
		return triangles;
	}

	public void setTriangles(Triangle[] triangles) {
		this.triangles = triangles;
	}
	
	
}
