package render;

public class Camera {
	public Point location;
	public int renderWidth;
	public int renderHeight;
	public double zoom;
	public double xAngle;
	public double yAngle;
	public double zAngle;

	public Camera(Point location,double xAngle, double yAngle, double zAngle, int renderWidth, int renderHeight, double zoom) {
		super();
		this.location = location;
		this.renderWidth=renderWidth;
		this.renderHeight=renderHeight;
		this.zoom=zoom;
		this.xAngle=xAngle;
		this.yAngle=yAngle;
		this.zAngle=zAngle;
	}

	public Point getLocation() {
		return location;
	}
	public void setLocation(Point location) {
		this.location = location;
	}
	public double getZoom() {
		return zoom;
	}
	public void setZoom(double zoom) {
		this.zoom = zoom;
	}
	public double getxAngle() {
		return xAngle;
	}
	public void setxAngle(double xAngle) {
		this.xAngle = xAngle;
	}
	public double getyAngle() {
		return yAngle;
	}
	public void setyAngle(double yAngle) {
		this.yAngle = yAngle;
	}
	public double getzAngle() {
		return zAngle;
	}
	public void setzAngle(double zAngle) {
		this.zAngle = zAngle;
	}

	public Ray[][] generateRays(){
		Ray[][] ray=new Ray[renderWidth][renderHeight];
		
		for(int i=0;i<renderWidth;i++) {
			for(int j=0;j<renderHeight;j++) {
				double x=(i-(renderWidth/2.0))/(renderWidth/2.0);
				double y=(j-(renderHeight/2.0))/(renderHeight/2.0);	
				double z=zoom;
				
				double xRot=x;
				double yRot=(y*Math.cos(Math.toRadians(xAngle)))-(z*Math.sin(Math.toRadians(xAngle)));
				double zRot=(y*Math.sin(Math.toRadians(xAngle)))+(z*Math.cos(Math.toRadians(xAngle)));
				
				double xRot1=(xRot*Math.cos(Math.toRadians(zAngle)))-(yRot*Math.sin(Math.toRadians(zAngle)));
				double yRot1=(xRot*Math.sin(Math.toRadians(zAngle)))+(yRot*Math.cos(Math.toRadians(zAngle)));
				double zRot1=zRot;
				
				double xRot2=(zRot1*Math.cos(Math.toRadians(yAngle)))-(xRot1*Math.sin(Math.toRadians(yAngle)));
				double zRot2=(zRot1*Math.sin(Math.toRadians(yAngle)))+(zRot1*Math.cos(Math.toRadians(yAngle)));
				double yRot2=yRot1;
				ray[i][j]=new Ray(location,new Vector(xRot2,yRot2,zRot2));
				ray[i][j].setDirection(ray[i][j].getDirection().normalize());
			}
			//System.out.println(ray[i][0].getDirection().toString());
		}
		return ray;
	}
}
