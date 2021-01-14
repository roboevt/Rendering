package render;

public class VectorF {
	public float x;
	public float y;
	public float z;
	
	public VectorF(float x, float y, float z) {
		//Engine.count++;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}
	
	public String toString() {
		return x+" "+y+" "+z+" ";
	}
	
	public PointF toPointF() {
		return new PointF(this.x,this.y,this.z);
	}
	
	public VectorF subtract(VectorF vectorf) {
		return new VectorF(this.x-vectorf.x,this.y-vectorf.y,this.z-vectorf.z);
	}
	
	public VectorF add(VectorF vectorf) {
		return new VectorF(this.x+vectorf.x,this.y+vectorf.y,this.z+vectorf.z);
	}
	
	public VectorF multiply(float scalar) {
		return new VectorF(this.x*scalar,this.y*scalar,this.z*scalar);
	}
	
	public float magnitude() {
		return (float) Math.sqrt(Math.pow(this.x, 2)+Math.pow(this.y, 2)+Math.pow(this.z, 2));
	}
	
	public float magnitudeSquared() {
		return (float) (Math.pow(this.x, 2)+Math.pow(this.y, 2)+Math.pow(this.z, 2));
	}
	
	public VectorF normalize() {
		float magnitude=this.magnitude();
		return new VectorF(this.x/magnitude,this.y/magnitude,this.z/magnitude);	
	}
	
	public float dot(VectorF vectorf) {
		return (this.x*vectorf.x)+(this.y*vectorf.y)+(this.z*vectorf.z);
	}
	
	public VectorF cross(VectorF vectorf) {
		float xCross=this.y*vectorf.z-this.z*vectorf.y;
		float yCross=this.z*vectorf.x-this.x*vectorf.z;
		float zCross=this.x*vectorf.y-this.y*vectorf.x;
		
		return new VectorF(xCross,yCross,zCross);
	}
	
	public static void main(String[] args) {
	}
}
