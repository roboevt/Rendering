package render;

public class Camera {
	public PointF location;
	public int renderWidth;
	public int renderHeight;
	public float zoom;
	public float xAngle;
	public float yAngle;
	public float zAngle;
	private float[] sinTable;
	private float[] cosTable;

	public Camera(PointF location,float xAngle, float yAngle, float zAngle, int renderWidth, int renderHeight, float zoom) {
		super();
		this.location = location;
		this.renderWidth=renderWidth;
		this.renderHeight=renderHeight;
		this.zoom=zoom;
		this.xAngle=xAngle;
		this.yAngle=yAngle;
		this.zAngle=zAngle;

		sinTable=new float[361];
		cosTable=new float[361];
		for(int i=0;i<=360;i++) {
			sinTable[i]=(float) Math.sin(Math.toRadians(i)); //maybe switch these back to doubles.
			cosTable[i]=(float) Math.cos(Math.toRadians(i));
		}
	}

	public float getSin(int angle) {
		int angleCircle = angle % 360;
		if (angleCircle<0) {
			angleCircle+=360;
		}
		return sinTable[angleCircle];
	}

	public float getCos(int angle) {
		int angleCircle = angle % 360;
		if (angleCircle<0) {
			angleCircle+=360;
		}
		return cosTable[angleCircle];
	}

	public PointF getLocation() {
		return location;
	}
	public void setLocation(PointF location) {
		this.location = location;
	}
	public float getZoom() {
		return zoom;
	}
	public void setZoom(float zoom) {
		this.zoom = zoom;
	}
	public float getxAngle() {
		return xAngle;
	}
	public void setxAngle(float xAngle) {
		this.xAngle = xAngle;
	}
	public float getyAngle() {
		return yAngle;
	}
	public void setyAngle(float yAngle) {
		this.yAngle = yAngle;
	}
	public float getzAngle() {
		return zAngle;
	}
	public void setzAngle(float zAngle) {
		this.zAngle = zAngle;
	}

	public Ray[][] generateRays(){
		long startTime = System.currentTimeMillis();
		Ray[][] ray=new Ray[renderWidth][renderHeight];
		for(int i=0;i<renderWidth;i++) {
			for(int j=0;j<renderHeight;j++) {
				float x=(i-(renderWidth/2.0f))/(renderWidth/2.0f);
				float y=(j-(renderHeight/2.0f))/(renderHeight/2.0f);	
				float z=zoom;

				float xRot=x;
				float yRot=(float) ((y*Math.cos(Math.toRadians(xAngle)))-(z*Math.sin(Math.toRadians(xAngle))));
				float zRot=(float) ((y*Math.sin(Math.toRadians(xAngle)))+(z*Math.cos(Math.toRadians(xAngle))));

				float xRot1=(float) ((xRot*Math.cos(Math.toRadians(zAngle)))-(yRot*Math.sin(Math.toRadians(zAngle))));
				float yRot1=(float) ((xRot*Math.sin(Math.toRadians(zAngle)))+(yRot*Math.cos(Math.toRadians(zAngle))));
				float zRot1=zRot;

				float xRot2=(float) ((zRot1*Math.cos(Math.toRadians(yAngle)))-(xRot1*Math.sin(Math.toRadians(yAngle))));
				float zRot2=(float) ((zRot1*Math.sin(Math.toRadians(yAngle)))+(xRot1*Math.cos(Math.toRadians(yAngle))));
				float yRot2=yRot1;

				/*double xRot=x;
				double yRot=(y*getCos((int)xAngle))-(z*getSin((int)xAngle));
				double zRot=(y*getSin((int)xAngle))+(z*getCos((int)xAngle));

				double xRot1=(xRot*getCos((int)zAngle))-(yRot*getSin((int)zAngle));
				double yRot1=(xRot*getSin((int)zAngle))+(yRot*getCos((int)zAngle));
				double zRot1=zRot;

				double xRot2=(zRot1*getCos((int)yAngle))-(xRot1*getSin((int)yAngle));
				double zRot2=(zRot1*getSin((int)yAngle))+(xRot1*getCos((int)yAngle));
				double yRot2=yRot1;*/
				ray[i][j]=new Ray(location,new VectorF(xRot2,yRot2,zRot2));
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
				float x=(i-(renderWidth/2.0f))/(renderWidth/2.0f);
				float y=(j-(renderHeight/2.0f))/(renderHeight/2.0f);	
				float z=zoom;

				float xRot=x;
				float yRot=(float) ((y*Math.cos(Math.toRadians(xAngle)))-(z*Math.sin(Math.toRadians(xAngle))));
				float zRot=(float) ((y*Math.sin(Math.toRadians(xAngle)))+(z*Math.cos(Math.toRadians(xAngle))));

				float xRot1=(float) ((xRot*Math.cos(Math.toRadians(zAngle)))-(yRot*Math.sin(Math.toRadians(zAngle))));
				float yRot1=(float) ((xRot*Math.sin(Math.toRadians(zAngle)))+(yRot*Math.cos(Math.toRadians(zAngle))));
				float zRot1=zRot;

				float xRot2=(float) ((zRot1*Math.cos(Math.toRadians(yAngle)))-(xRot1*Math.sin(Math.toRadians(yAngle))));
				float zRot2=(float) ((zRot1*Math.sin(Math.toRadians(yAngle)))+(xRot1*Math.cos(Math.toRadians(yAngle))));
				float yRot2=yRot1;

				ray[i][j]=new Ray(location,new VectorF(xRot2,yRot2,zRot2));
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
			float x=(i-(renderWidth/2.0f))/(renderWidth/2.0f);
			float y=(j-(renderHeight/2.0f))/(renderHeight/2.0f);	
			float z=zoom;

			float xRot=x;
			float yRot=(float) ((y*Math.cos(Math.toRadians(xAngle)))-(z*Math.sin(Math.toRadians(xAngle))));
			float zRot=(float) ((y*Math.sin(Math.toRadians(xAngle)))+(z*Math.cos(Math.toRadians(xAngle))));

			float xRot1=(float) ((xRot*Math.cos(Math.toRadians(zAngle)))-(yRot*Math.sin(Math.toRadians(zAngle))));
			float yRot1=(float) ((xRot*Math.sin(Math.toRadians(zAngle)))+(yRot*Math.cos(Math.toRadians(zAngle))));
			float zRot1=zRot;

			float xRot2=(float) ((zRot1*Math.cos(Math.toRadians(yAngle)))-(xRot1*Math.sin(Math.toRadians(yAngle))));
			float zRot2=(float) ((zRot1*Math.sin(Math.toRadians(yAngle)))+(xRot1*Math.cos(Math.toRadians(yAngle))));
			float yRot2=yRot1;

			ray[j]=new Ray(location,new VectorF(xRot2,yRot2,zRot2));
			ray[j].setDirection(ray[j].getDirection().normalize());
		}
		return ray;
	}
}
