package render;

import java.awt.Color;

public class Engine {
	static int renderWidth=500;
	static int renderHeight=500;
	static int renderScale=2;

	public static double[][] calculateRays(Ray[][] cameraRays, Sphere[] spheres, Point light){
		double[][] brightness=new double[renderWidth][renderHeight];
		int x=-1;
		int y=0;
		for(Ray[] array : cameraRays) {
			y=-1;
			x++;
			for(Ray ray : array) {
				y++;
				brightness[x][y]=calculateRay(ray,spheres,light,0);
			}
		}
		return brightness;
	}

	public static double calculateRay(Ray ray, Sphere[] spheres, Point light, int bounces) {
		if(bounces>3) {//end condition - last ray shadow check
			//int hitSphere=-1;
			double distanceToSphere=ray.distanceToSpheres(spheres);
			if(distanceToSphere<Integer.MAX_VALUE-1) { //hit sphere
				Vector vectorToSphere=ray.getDirection().multiply(distanceToSphere);
				Point pointOnSphere=ray.getOrigin().add(vectorToSphere.toPoint());

				if(checkShadow(pointOnSphere,spheres,light)) {
					return 40;
				}else {
					return 255;
				}
			}else {//did not hit sphere
				return 0;
			}
		}
		//not end condition

		double brightness=0;
		double distanceToSphere=ray.distanceToSpheres(spheres);
		int hitSphere=-1;
		if(distanceToSphere<Integer.MAX_VALUE-1) {//ray hits sphere
			Vector vectorToSphere=ray.getDirection().multiply(distanceToSphere);
			Point pointOnSphere=ray.getOrigin().add(vectorToSphere.toPoint());
			for(int i=0;i<spheres.length;i++) {
				Vector centerToPoint=new Vector(pointOnSphere.x-spheres[i].getCenter().x, pointOnSphere.y-spheres[i].getCenter().y, pointOnSphere.z-spheres[i].getCenter().z);
				if(Math.abs(centerToPoint.magnitude()-spheres[i].getRadius())<.01) {//point is on sphere i
					hitSphere=i;
				}
			}
			if(spheres[hitSphere].material.reflective) {
				Vector normal=new Vector(pointOnSphere.x-spheres[hitSphere].getCenter().x, pointOnSphere.y-spheres[hitSphere].getCenter().y, pointOnSphere.z-spheres[hitSphere].getCenter().z);
				//pointOnSphere.add(normal.multiply(.01).toPoint());
				Ray reflection=ray.calculateReflection(new Ray(spheres[hitSphere].getCenter(),normal), pointOnSphere);
				brightness=calculateRay(reflection,spheres,light,bounces+1);
			}else {
				if(checkShadow(pointOnSphere,spheres,light)) {
					return 40;
				}else {
					return 255;
				}
			}
		}	
		return brightness;
	}

	public static boolean checkShadow(Point pointOnSphere, Sphere[] spheres, Point light) {
		Vector vectorToLight=new Vector(light.x-pointOnSphere.x,light.y-pointOnSphere.y,light.z-pointOnSphere.z);
		Ray rayToLight=new Ray(pointOnSphere,vectorToLight.normalize());
		if(rayToLight.distanceToSpheres(spheres)<vectorToLight.magnitude()) { //in shadow
			return true; //in shadow
		}else {
			return false; //in light
		}
	}

	public static void draw(double[][] brightness) {
		for(int i=0;i<renderWidth;i++) {
			for(int j=0;j<renderHeight;j++) {
				int brightnessInt=(int)brightness[i][j];
				StdDraw.setPenColor(new Color(brightnessInt,brightnessInt,brightnessInt));
				StdDraw.point(i, j);
			}
		}
		StdDraw.show();
	}

	public static void main(String[] args) {
		StdDraw.setCanvasSize(renderWidth*renderScale,renderHeight*renderScale);
		StdDraw.setXscale(0,renderWidth);
		StdDraw.setYscale(0,renderHeight);
		StdDraw.setPenColor(new Color(0,0,0));
		StdDraw.filledRectangle(renderWidth/2,renderHeight/2,renderWidth,renderHeight);
		StdDraw.setPenRadius(.005);
		StdDraw.enableDoubleBuffering();

		Point camLocation=new Point(0,0,-10);
		Camera camera=new Camera(camLocation,renderWidth,renderHeight,6);

		Point sphere1Center=new Point(0,0,0);
		Material sphere1Material=new Material(true);
		Sphere sphere1=new Sphere(sphere1Center,.15,sphere1Material);
		Point sphere2Center=new Point(0,0,0);
		Material sphere2Material=new Material(true);
		Sphere sphere2=new Sphere(sphere2Center,.3,sphere2Material);
		Point floorSphereCenter=new Point(0,-1001,0);
		Material floorMaterial=new Material(false);
		Sphere floorSphere=new Sphere(floorSphereCenter,1000.2,floorMaterial);
		Sphere[] spheres= {sphere1,sphere2,floorSphere};


		Point light=new Point(0,5,0);
		double i=0;
		while(true) {
			System.out.println("Frame done");
			StdDraw.setPenColor(new Color(0,0,0));
			StdDraw.filledRectangle(renderWidth/2,renderHeight/2,renderWidth,renderHeight);
			i+=.05;
			//light.setX(Math.cos(i));
			//light.setZ(Math.sin(i+Math.PI)*2);
			spheres[0].setX(Math.sin(i)/4);
			spheres[0].setZ(Math.cos(i)/2);
			spheres[0].setY(Math.sin(i)/2);
			spheres[1].setY(Math.sin(2*i)/10);
			Ray[][] cameraRays=camera.generateRays();
			double[][]brightness=calculateRays(cameraRays,spheres,light);
			draw(brightness);
		}
	}
}
