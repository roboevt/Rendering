package render;

import java.awt.Color;

public class Engine {
	static int renderWidth=200;
	static int renderHeight=200;
	static int renderScale=4;

	public static double[][] calculateRays(Ray[][] cameraRays, Sphere[] spheres, Point light){
		double[][] brightness=new double[renderWidth][renderHeight];
		boolean inShadow=false;

		for(Sphere sphere:spheres) {
			int x=-1;
			int y=0;
			for(Ray[] array : cameraRays) {
				y=-1;
				x++;
				for(Ray ray : array) {
					y++;
					double distanceToSphere=ray.distanceToSphere(sphere);
					if(distanceToSphere<9999) {//ray hits sphere
						inShadow=false;
						for(int i=0;i<spheres.length;i++) {
							Vector vectorToSphere=ray.getDirection().multiply(distanceToSphere);
							Point pointOnSphere=ray.getOrigin().add(vectorToSphere.toPoint());
							Vector normalVector=new Vector(pointOnSphere.x-spheres[i].getCenter().x,pointOnSphere.y-spheres[i].getCenter().y,pointOnSphere.z-spheres[i].getCenter().z);
							pointOnSphere.add((normalVector.multiply(.01)).toPoint());

							Vector vectorToLight=new Vector(light.x-pointOnSphere.x,light.y-pointOnSphere.y,light.z-pointOnSphere.z);
							Ray rayToLight=new Ray(pointOnSphere,vectorToLight.normalize());

							
							if(rayToLight.distanceToSphere(spheres[i])<vectorToLight.magnitude()) { //in shadow
								inShadow=true;
							}
						}
						if(inShadow) {
							brightness[x][y]=0;
						}else {
							brightness[x][y]=255;
						}
					}
				}
			}
		}
		return brightness;
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
		StdDraw.setPenRadius(.1);
		StdDraw.setCanvasSize(renderWidth*renderScale,renderHeight*renderScale);
		StdDraw.setXscale(0,renderWidth);
		StdDraw.setYscale(0,renderHeight);
		StdDraw.setPenColor(new Color(0,0,0));
		StdDraw.filledRectangle(renderWidth/2,renderHeight/2,renderWidth,renderHeight);
		StdDraw.enableDoubleBuffering();

		Point camLocation=new Point(0,0,-10);
		Camera camera=new Camera(camLocation,renderWidth,renderHeight,10);

		Point sphere1Center=new Point(0,0,0);
		Sphere sphere1=new Sphere(sphere1Center,.1);
		Point sphere2Center=new Point(0,0,0);
		Sphere sphere2=new Sphere(sphere2Center,.1);
		Point floorSphereCenter=new Point(0,-101,0);
		Sphere floorSphere=new Sphere(floorSphereCenter,100);
		Sphere[] spheres= {sphere1,sphere2,floorSphere};
		

		Point light=new Point(0,10,0);
		double i=0;
		while(true) {
			System.out.println("Frame done");
			i+=.05;
			//spheres[0].setX(Math.sin(i)/2);
			spheres[0].setZ(Math.cos(i)/2);
			spheres[0].setY(Math.sin(i)/2);
			Ray[][] cameraRays=camera.generateRays();
			double[][]brightness=calculateRays(cameraRays,spheres,light);
			draw(brightness);
		}
	}

}
