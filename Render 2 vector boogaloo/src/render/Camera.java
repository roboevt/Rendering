package render;

public class Camera {
	public Point location;
	public int renderWidth;
	public int renderHeight;
	public double zoom;
	public double xAngle;
	public double yAngle;
	public double zAngle;
	private double[] sinTable;
	private double[] cosTable;

	public Camera(Point location,double xAngle, double yAngle, double zAngle, int renderWidth, int renderHeight, double zoom) {
		super();
		this.location = location;
		this.renderWidth=renderWidth;
		this.renderHeight=renderHeight;
		this.zoom=zoom;
		this.xAngle=xAngle;
		this.yAngle=yAngle;
		this.zAngle=zAngle;

		sinTable=new double[361];
		cosTable=new double[361];
		for(int i=0;i<=360;i++) {
			sinTable[i]=Math.sin(Math.toRadians(i));
			cosTable[i]=Math.cos(Math.toRadians(i));
		}
	}

	public double getSin(int angle) {
		int angleCircle = angle % 360;
		if (angleCircle<0) {
			angleCircle+=360;
		}
		return sinTable[angleCircle];
	}

	public double getCos(int angle) {
		int angleCircle = angle % 360;
		if (angleCircle<0) {
			angleCircle+=360;
		}
		return cosTable[angleCircle];
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
		long startTime = System.currentTimeMillis();
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
				double zRot2=(zRot1*Math.sin(Math.toRadians(yAngle)))+(xRot1*Math.cos(Math.toRadians(yAngle)));
				double yRot2=yRot1;

				/*double xRot=x;
				double yRot=(y*getCos((int)xAngle))-(z*getSin((int)xAngle));
				double zRot=(y*getSin((int)xAngle))+(z*getCos((int)xAngle));

				double xRot1=(xRot*getCos((int)zAngle))-(yRot*getSin((int)zAngle));
				double yRot1=(xRot*getSin((int)zAngle))+(yRot*getCos((int)zAngle));
				double zRot1=zRot;

				double xRot2=(zRot1*getCos((int)yAngle))-(xRot1*getSin((int)yAngle));
				double zRot2=(zRot1*getSin((int)yAngle))+(xRot1*getCos((int)yAngle));
				double yRot2=yRot1;*/
				ray[i][j]=new Ray(location,new Vector(xRot2,yRot2,zRot2));
				ray[i][j].setDirection(ray[i][j].getDirection().normalize());
			}
			//System.out.println(ray[i][0].getDirection().toString());
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Generate ray time: "+((endTime - startTime)) + " milliseconds");
		return ray;
	}

	public Ray[][] generateRaysQuadrant(int quadrant){
		if(quadrant==1) {
			return generateRaysPortion(0,renderWidth/3,renderHeight/2,renderHeight,1);
		}
		if(quadrant==2) {
			return generateRaysPortion(renderWidth/3,2*renderWidth/3,renderHeight/2,renderHeight,2);
		}
		if(quadrant==3) {
			return generateRaysPortion(2*renderWidth/3,renderWidth,renderHeight/2,renderHeight,3);
		}
		if(quadrant==4) {
			return generateRaysPortion(0,renderWidth/3,0,renderHeight/2,4);
		}
		if(quadrant==5) {
			return generateRaysPortion(renderWidth/3,2*renderWidth/3,0,renderHeight/2,5);
		}
		if(quadrant==6) {
			return generateRaysPortion(2*renderWidth/3,renderWidth,0,renderHeight/2,6);
		}
		return null;
	}

	private Ray[][] generateRaysPortion(int startX, int endX, int startY, int endY, int quadrant){
		long startTime = System.currentTimeMillis();
		Ray[][] ray=new Ray[renderWidth][renderHeight];
		for(int i=startX;i<endX;i++) {
			for(int j=startY;j<endY;j++) {
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
				double zRot2=(zRot1*Math.sin(Math.toRadians(yAngle)))+(xRot1*Math.cos(Math.toRadians(yAngle)));
				double yRot2=yRot1;

				ray[i][j]=new Ray(location,new Vector(xRot2,yRot2,zRot2));
				ray[i][j].setDirection(ray[i][j].getDirection().normalize());
			}
			//System.out.println(ray[i][0].getDirection().toString());
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Generate quadrant "+quadrant+" rays time: "+((endTime - startTime)) + " milliseconds");
		return ray;
	}

	public Ray[] generateRaysLine(int line){
		Ray[] ray=new Ray[renderWidth];
		int i=line;
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
			double zRot2=(zRot1*Math.sin(Math.toRadians(yAngle)))+(xRot1*Math.cos(Math.toRadians(yAngle)));
			double yRot2=yRot1;

			ray[j]=new Ray(location,new Vector(xRot2,yRot2,zRot2));
			ray[j].setDirection(ray[j].getDirection().normalize());
		}
		return ray;
	}
}
