package render;

import java.awt.Color;
import java.awt.event.KeyEvent;

public class Engine {
	static int renderWidth;
	static int renderHeight;
	static int renderScale;
	static int maxBounces;
	static double speed=.2;
	public static int magnitudeCount=0;
	public double camX;
	public double camY;
	public double camZ;
	public double camRotX;
	public double camRotY;
	public double camRotZ;
	public double camZoom;
	public Camera camera;
	public Sphere[] spheres;
	public Point light;
	Ray[][] cameraRays;

	public Engine(int renderSize, int renderScale, int maxBounces) {
		this.renderWidth=renderSize;
		this.renderHeight=renderSize;
		this.renderScale=renderScale;
		this.maxBounces=maxBounces-1;
		StdDraw.setCanvasSize(renderWidth*renderScale,renderHeight*renderScale);
		StdDraw.setXscale(0,renderWidth);
		StdDraw.setYscale(0,renderHeight);
		StdDraw.setPenColor(new Color(0,0,0));
		StdDraw.filledRectangle(renderWidth/2,renderHeight/2,renderWidth,renderHeight);
		StdDraw.setPenRadius(0);
		StdDraw.enableDoubleBuffering();
		camX=0;
		camY=1;
		camZ=-8;

		camRotX=0;
		camRotY=90;
		camRotZ=0;

		camZoom=1;
		Point camLocation=new Point(camX,camY,camZ);
		camera=new Camera(camLocation,camRotX,camRotY,camRotZ,renderWidth,renderHeight,camZoom);
		spheres=Sphere.generateFloorSpheres(10);
		light=new Point(500,500,0);
		this.cameraRays=new Ray[renderWidth][renderHeight];
		//double i=0;
	}

	public static Color[][] calculateRays(Ray[][] cameraRays,int startX, int endX, int startY, int endY, Sphere[] spheres, Point light){
		Color[][] colorOut=new Color[renderWidth][renderHeight];
		for(int i=startX;i<endX;i++) {
			for(int j=startY;j<endY;j++) {
				colorOut[i][j]=calculateRay(cameraRays[i][j],spheres,light,0);
			}
		}
		return colorOut;
		
	}

	private static Color calculateRay(Ray ray, Sphere[] spheres, Point light, int bounces) { // the main attraction
		Color color=new Color(0,0,0);
		double distanceToSphere=ray.distanceToSpheres(spheres);
		int hitSphere=1;
		if(distanceToSphere<Integer.MAX_VALUE-1) {//ray hits sphere
			Vector vectorToSphere=ray.getDirection().multiply(distanceToSphere);
			Point pointOnSphere=ray.getOrigin().add(vectorToSphere.toPoint());
			for(int i=0;i<spheres.length;i++) {
				Vector centerToPoint=new Vector(pointOnSphere.x-spheres[i].getCenter().x, pointOnSphere.y-spheres[i].getCenter().y, pointOnSphere.z-spheres[i].getCenter().z);
				if(Math.abs(centerToPoint.magnitude()-spheres[i].getRadius())<.01) {//point is on sphere i
					hitSphere=i;
				}
			}

			if(spheres[hitSphere].material.reflective&&bounces<maxBounces) { //if material of hit sphere is reflective, and not at max bounce limit, recurse
				Vector normal=new Vector(pointOnSphere.x-spheres[hitSphere].getCenter().x, pointOnSphere.y-spheres[hitSphere].getCenter().y, pointOnSphere.z-spheres[hitSphere].getCenter().z);
				//pointOnSphere.add(normal.multiply(.001).toPoint()); //the bumps the point away from the surface a bit, seems to not be necessary for some reason
				Ray reflection=ray.calculateReflection(new Ray(spheres[hitSphere].getCenter(),normal), pointOnSphere);
				color=calculateRay(reflection,spheres,light,bounces+1);

			}else {// if not reflective or already at max bounces, check shadows and end.
				if(checkShadow(pointOnSphere,spheres,light)) {
					return Color.black;
				}else {
					double brightness=checkDiffuse(pointOnSphere,spheres[hitSphere],light,ray);
					return spheres[hitSphere].material.scaleColor(brightness);
				}
			}
		}	
		return color;
	}

	private static boolean checkShadow(Point pointOnSphere, Sphere[] spheres, Point light) {
		Vector vectorToLight=new Vector(light.x-pointOnSphere.x,light.y-pointOnSphere.y,light.z-pointOnSphere.z);
		Ray rayToLight=new Ray(pointOnSphere,vectorToLight.normalize());
		if(rayToLight.distanceToSpheres(spheres)<vectorToLight.magnitude()) { //in shadow
			return true; //in shadow
		}else {
			return false; //in light
		}
	}

	private static double checkDiffuse(Point pointOnSphere, Sphere sphere, Point light, Ray ray) {
		Vector lightVector=new Vector(light.x-pointOnSphere.x,light.y-pointOnSphere.y,light.z-pointOnSphere.z);
		Vector normal=new Vector(pointOnSphere.x-sphere.getCenter().x, pointOnSphere.y-sphere.getCenter().y, pointOnSphere.z-sphere.getCenter().z);
		double angle=Math.acos(normal.dot(lightVector)/(lightVector.magnitude()*normal.magnitude()));
		return Math.cos(angle);
	}

	public void draw(Color[][] colorIn) {
		long startTime = System.currentTimeMillis();
		StdDraw.enableDoubleBuffering();
		for(int i=0;i<renderWidth;i++) {
			for(int j=0;j<renderHeight;j++) {
				if(colorIn[i][j]!=null) {
					if(!colorIn[i][j].equals(Color.black)) {
						StdDraw.setPenColor(colorIn[i][j]);
						StdDraw.pixel(i, j); //can be changed to point too
					}
				}
			}
		}
		//StdDraw.show();
		long endTime = System.currentTimeMillis();
		//System.out.println("Draw time: "+((endTime - startTime)) + " milliseconds");
	}

	private static boolean checkFor(int key) {
		if (StdDraw.isKeyPressed(key)) {
			return true;
		}
		else {
			return false;
		}
	}

	public Color[][] calculateFrame(int quadrant, /*Color[][] colorIn,*/ Point camLocation, Vector camRotation) {
		camera.setLocation(camLocation);
		camera.setxAngle(camRotation.getX());
		camera.setyAngle(camRotation.getY());
		camera.setzAngle(camRotation.getZ());
		cameraRays=camera.generateRays();
		Color[][]color=new Color[renderWidth][renderHeight];
		if(quadrant==1) {
			color=calculateRays(cameraRays,renderWidth/2,renderWidth,renderHeight/2,renderHeight,spheres,light);
			//System.out.println(color[0][0].getRed());
		}
		if(quadrant==2) {
			color=calculateRays(cameraRays, 0,renderWidth/2,renderHeight/2,renderHeight, spheres, light);
		}
		if(quadrant==3) {
			color=calculateRays(cameraRays,0,renderWidth/2,0,renderHeight/2,spheres,light);
		}
		if(quadrant==4) {
			color=calculateRays(cameraRays, renderWidth/2,renderWidth,0,renderHeight/2,spheres,light);
		}
		return color;

		//System.out.println(count);

		//StdDraw.setPenColor(Color.white);
		//StdDraw.textLeft(10, renderHeight-10,"fps: "+ 1/((endTime - startTime)/1000.0)+"");
		//StdDraw.textLeft(10, renderHeight-20, "zoom: "+camZoom);
		//StdDraw.textLeft(10, renderHeight-30,"million magnitude calculations: "+magnitudeCount/1000000);
		//StdDraw.show();
	}

	//public static void main(String[] args) {
	//}
}
