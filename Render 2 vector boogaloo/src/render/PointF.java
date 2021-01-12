package render;

public class PointF {
	public float x;
	public float y;
	public float z;
	
	public PointF(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public double getX() {
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
		return this.x+" "+this.y+" "+this.z;
	}
	
	public VectorF toVectorF() {
		return new VectorF(this.x,this.y,this.z);
	}
	
	public PointF add(PointF pointf) {
		return new PointF(this.x+pointf.x,this.y+pointf.y,this.z+pointf.z);
	}
	
	public VectorF subtractToVectorF(PointF pointf) {
		return new VectorF(pointf.x-this.x,pointf.y-this.y,pointf.z-this.z);
	}
}
