package render;

import java.awt.Color;
import java.awt.event.KeyEvent;

public class Engine {
	public int renderWidth;
	public int renderHeight;
	public int renderScale;
	public int maxBounces;
	public double speed=.2;
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
	Color skyColor;

	public Engine(int renderSize, int renderScale, int maxBounces, Sphere[] spheres) {
		this.renderWidth=renderSize;
		this.renderHeight=renderSize;
		this.renderScale=renderScale;
		this.maxBounces=maxBounces-1;
		this.spheres=spheres;
		camX=0;
		camY=1;
		camZ=-8;
		camRotX=0;
		camRotY=90;
		camRotZ=0;
		camZoom=1;
		Point camLocation=new Point(camX,camY,camZ);
		camera=new Camera(camLocation,camRotX,camRotY,camRotZ,renderWidth,renderHeight,camZoom);
		light=new Point(500,500,0);
		this.cameraRays=new Ray[renderWidth][renderHeight];
		skyColor=new Color(0,0,0);
	}
	
	public void setSkyColor(Color color) {
		this.skyColor=color;
	}

	public  Color[][] calculateRays(Ray[][] cameraRays,int startX, int endX, int startY, int endY, Sphere[] spheres, Point light){
		Color[][] colorOut=new Color[renderWidth][renderHeight];
		for(int i=startX;i<endX;i++) {
			for(int j=startY;j<endY;j++) {
				colorOut[i][j]=calculateRay(cameraRays[i][j],spheres,light,0);
			}
		}
		return colorOut;

	}

	private  Color calculateRay(Ray ray, Sphere[] spheres, Point light, int bounces) { // the main attraction
		Color color=skyColor;
		double distanceToSphere=ray.distanceToSpheres(spheres);
		int hitSphere=1;
		if(distanceToSphere<Integer.MAX_VALUE-1) {//ray hits sphere
			Vector vectorToSphere=ray.getDirection().multiply(distanceToSphere);
			Point pointOnSphere=ray.getOrigin().add(vectorToSphere.toPoint());
			for(int i=0;i<spheres.length;i++) {
				Vector centerToPoint=new Vector(pointOnSphere.x-spheres[i].getCenter().x, pointOnSphere.y-spheres[i].getCenter().y, pointOnSphere.z-spheres[i].getCenter().z);
				if(Math.abs(centerToPoint.magnitude()-spheres[i].getRadius())<.001) {//point is on sphere i
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

	public static void draw(Color[][] colorIn, int renderWidth, int renderHeight) {
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
	}

	private static boolean checkFor(int key) {
		if (StdDraw.isKeyPressed(key)) {
			return true;
		}
		else {
			return false;
		}
	}

	public Color[][] calculateFrame(int quadrant, Ray[][] cameraRays) {
		if(quadrant==1) {
			return calculateRays(cameraRays,renderWidth/2,renderWidth,renderHeight/2,renderHeight,spheres,light);
		}
		if(quadrant==2) {
			return calculateRays(cameraRays, 0,renderWidth/2,renderHeight/2,renderHeight, spheres, light);
		}
		if(quadrant==3) {
			return calculateRays(cameraRays,0,renderWidth/2,0,renderHeight/2,spheres,light);
		}
		if(quadrant==4) {
			return calculateRays(cameraRays, renderWidth/2,renderWidth,0,renderHeight/2,spheres,light);
		}
		return null;
	}

	public static void main(String[] args) {
	}
}
