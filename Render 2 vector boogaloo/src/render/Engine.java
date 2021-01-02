package render;

import java.awt.Color;
import java.awt.event.KeyEvent;

public class Engine {
	static int renderWidth=200;
	static int renderHeight=200;
	static int renderScale=4;
	static int maxBounces=10;
	static double speed=.1;
	public static int count=0;

	public static Color[][] calculateRays(Ray[][] cameraRays, Sphere[] spheres, Point light){
		Color[][] color=new Color[renderWidth][renderHeight];
		int x=-1;
		int y=0;
		for(Ray[] array : cameraRays) {
			y=-1;
			x++;
			for(Ray ray : array) {
				y++;
				color[x][y]=calculateRay(ray,spheres,light,0);
			}
		}
		return color;
	}
	public static Color calculateRay(Ray ray, Sphere[] spheres, Point light, int bounces) {
		if(bounces>maxBounces) {//end condition - last ray shadow check
			int hitSphere=-1;
			double distanceToSphere=ray.distanceToSpheres(spheres);
			if(distanceToSphere<Integer.MAX_VALUE-1) { //hit sphere
				Vector vectorToSphere=ray.getDirection().multiply(distanceToSphere);
				Point pointOnSphere=ray.getOrigin().add(vectorToSphere.toPoint());
				for(int i=0;i<spheres.length;i++) {
					Vector centerToPoint=new Vector(pointOnSphere.x-spheres[i].getCenter().x, pointOnSphere.y-spheres[i].getCenter().y, pointOnSphere.z-spheres[i].getCenter().z);
					if(Math.abs(centerToPoint.magnitude()-spheres[i].getRadius())<.01) {//point is on sphere i
						hitSphere=i;
					}
				}
				if(checkShadow(pointOnSphere,spheres,light)) {//check shadows
					return spheres[hitSphere].material.darken(6);
				}else {
					return spheres[hitSphere].material.color.brighter();
				}
			}else {//did not hit sphere
				return Color.black;
			}
		}

		//not end condition
		Color color=new Color(0,0,0);
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
			if(spheres[hitSphere].material.reflective) { //if material of hit sphere is reflective, recurse
				Vector normal=new Vector(pointOnSphere.x-spheres[hitSphere].getCenter().x, pointOnSphere.y-spheres[hitSphere].getCenter().y, pointOnSphere.z-spheres[hitSphere].getCenter().z);
				//pointOnSphere.add(normal.multiply(.1).toPoint());
				Ray reflection=ray.calculateReflection(new Ray(spheres[hitSphere].getCenter(),normal), pointOnSphere);
				color=calculateRay(reflection,spheres,light,bounces+1);
			}else {// if not reflective, check shadows and end.
				if(checkShadow(pointOnSphere,spheres,light)) {
					return spheres[hitSphere].material.darken(6);
				}else {
					return spheres[hitSphere].material.color.brighter();
				}
			}
		}	
		return color;
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

	public static void draw(Color[][] colorIn) {
		for(int i=0;i<renderWidth;i++) {
			for(int j=0;j<renderHeight;j++) {
				StdDraw.setPenColor(colorIn[i][j]);
				StdDraw.point(i, j);
			}
		}
		StdDraw.show();
	}

	private static boolean checkFor(int key) {
		if (StdDraw.isKeyPressed(key)) {
			return true;
		}
		else {
			return false;
		}
	}

	public static void main(String[] args) {
		StdDraw.setCanvasSize(renderWidth*renderScale,renderHeight*renderScale);
		StdDraw.setXscale(0,renderWidth);
		StdDraw.setYscale(0,renderHeight);
		StdDraw.setPenColor(new Color(0,0,0));
		StdDraw.filledRectangle(renderWidth/2,renderHeight/2,renderWidth,renderHeight);
		StdDraw.setPenRadius(0);
		StdDraw.enableDoubleBuffering();
		double camX=0;
		double camY=1;
		double camZ=-8;

		double camRotX=0;
		double camRotY=90;
		double camRotZ=0;

		double camZoom=3;
		Point camLocation=new Point(camX,camY,camZ);
		Camera camera=new Camera(camLocation,camRotX,camRotY,camRotZ,renderWidth,renderHeight,camZoom);

		/*Point circlingSphereCenter=new Point(0,0,0);
		Material circlingSphereMaterial=new Material(true);
		Sphere circlingSphere=new Sphere(circlingSphereCenter,.15,circlingSphereMaterial);

		Point bouncingSphereCenter=new Point(0,0,0);
		Material bouncingSphereMaterial=new Material(true);
		Sphere bouncingSphere=new Sphere(bouncingSphereCenter,.3,bouncingSphereMaterial);

		Point floorSphereCenter=new Point(0,-1001,0);
		Material floorMaterial=new Material(false);
		Sphere floorSphere=new Sphere(floorSphereCenter,1000,floorMaterial);
		floorSphere.setColor(0, 255, 255);
		Point sideSphere1Center=new Point(-.5,0,0);
		Material sideSphere1Material=new Material(false);
		Sphere sideSphere1=new Sphere(sideSphere1Center,.1,sideSphere1Material);
		sideSphere1.setColor(255, 255, 0);
		Point sideSphere2Center=new Point(0.5,0,0);
		Material sideSphere2Material=new Material(false);
		Sphere sideSphere2=new Sphere(sideSphere2Center,.1,sideSphere2Material);
		sideSphere2.setColor(255, 0, 255);

		Sphere[] spheres= {circlingSphere,bouncingSphere,floorSphere,sideSphere1,sideSphere2};
		 */

		Sphere[] spheres=Sphere.generateFloorSpheres(10);
		Point light=new Point(0,5,0);
		//double i=0;
		while(true) {
			//System.out.println("Frame done");
			StdDraw.setPenColor(new Color(0,0,0));
			StdDraw.filledRectangle(renderWidth/2,renderHeight/2,renderWidth,renderHeight);
			//i+=.01;
			if (checkFor(KeyEvent.VK_W)) {
				camZ+=(speed*camY/2)*Math.sin(Math.toRadians(camRotY));
				camX+=(speed*camY/2)*Math.cos(Math.toRadians(camRotY));
			}
			if (checkFor(KeyEvent.VK_S)) {
				camZ-=(speed*camY/2)*Math.sin(Math.toRadians(camRotY));
				camX-=(speed*camY/2)*Math.cos(Math.toRadians(camRotY));
			}
			if (checkFor(KeyEvent.VK_A)) {
				camX+=(speed*camY/2)*Math.sin(Math.toRadians(camRotY));
				camZ-=(speed*camY/2)*Math.cos(Math.toRadians(camRotY));
			}
			if (checkFor(KeyEvent.VK_D)) {
				camX-=(speed*camY/2)*Math.sin(Math.toRadians(camRotY));
				camZ+=(speed*camY/2)*Math.cos(Math.toRadians(camRotY));
			}
			if (checkFor(KeyEvent.VK_SHIFT)) {
				camY+=speed*camY/2;
			}
			if (checkFor(KeyEvent.VK_CONTROL)) {
				camY-=speed*camY/2;
			}

			if (checkFor(KeyEvent.VK_UP)) {
				camRotX-=speed*30/camZoom;
			}
			if (checkFor(KeyEvent.VK_DOWN)) {
				camRotX+=speed*30/camZoom;
			}
			if (checkFor(KeyEvent.VK_R)) {
				camZoom+=speed*camZoom;
			}
			if (checkFor(KeyEvent.VK_F)) {
				camZoom-=speed*camZoom;
			}
			
			if (checkFor(KeyEvent.VK_LEFT)) {
				camRotY-=speed*30/camZoom;
			}
			if (checkFor(KeyEvent.VK_RIGHT)) {
				camRotY+=speed*30/camZoom;
			}
			camera.setLocation(new Point(camX,camY,camZ));
			camera.setxAngle(camRotX);
			camera.setyAngle(camRotY);
			camera.setzAngle(camRotZ);
			camera.setZoom(camZoom);
			//light.setX(Math.cos(i));
			//light.setZ(Math.sin(i+Math.PI)*2);
			/*spheres[0].setX(Math.sin(i+Math.PI)/2);
			spheres[0].setZ(Math.cos(i+Math.PI)/2);
			spheres[0].setY(Math.sin(2*i)/2);
			spheres[1].setY(Math.sin(4*i)/10);
			spheres[3].setZ(Math.sin(i)/3);
			spheres[4].setZ(Math.sin(i+Math.PI)/3);
			 */
			//camera.setxAngle(Math.sin(i)*16);
			//camLocation.setY(Math.sin(i)/1);

			long startTime = System.currentTimeMillis();

			Ray[][] cameraRays=camera.generateRays();
			Color[][]color=calculateRays(cameraRays,spheres,light);
			draw(color);

			System.out.println(count);
			long endTime = System.currentTimeMillis();

			System.out.println(1/((endTime - startTime)/1000.0) + " fps");
			StdDraw.setPenColor(Color.white);
			StdDraw.textLeft(10, 10, 1/((endTime - startTime)/1000.0) + " fps");
			StdDraw.show();
			
			
		}
	}
}
