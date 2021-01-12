package render;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
	public static double camX;
	public static double camY;
	public static double camZ;
	public static double speed=.2;
	public static Point camLocation=new Point(0,1,-6);
	public static double camRotX;
	public static double camRotY;
	public static double camRotZ;
	public static Vector camRotation=new Vector(0,90,0);
	public static double camZoom;
	public static Camera camera;
	public static Ray[][] cameraRays;
	public static Color[][] allColor;
	public static Sphere[] spheres;
	public static int renderWidth =1000;
	public static int renderHeight=1000;
	public static int numSpheres=10;
	public static double bounceHeight;
	public static double bounceSpeed;
	public static double bounceAcceleration;
	public static Color skyColor;
	public static int maxBounces=5;
	public static Point light=new Point(500,500,500);

	public static void main(String[] args) {
		StdDraw.setCanvasSize(renderWidth,renderHeight);
		StdDraw.setXscale(0,renderWidth);
		StdDraw.setYscale(0,renderHeight);
		//StdDraw.setPenColor(new Color(0,0,0));
		//StdDraw.filledRectangle(renderWidth/2,renderHeight/2,renderWidth,renderHeight);
		//StdDraw.setPenRadius(0);
		StdDraw.enableDoubleBuffering();

		System.setProperty("Dsun.java2d.opengl", "True"); //this doesn't seem to do much of anything
		
		allColor=new Color[renderWidth][renderHeight];
		
		bounceHeight=2;
		bounceAcceleration=-.02;
		camX=0;
		camY=1;
		camZ=-6;
		camRotX=0;
		camRotY=90;
		camRotZ=0;
		camZoom=1;
		camLocation.setX(camX);
		camLocation.setY(camY);
		camLocation.setZ(camZ);
		camRotation.setX(camRotX);
		camRotation.setY(camRotY);
		camRotation.setX(camRotZ);
		camera=new Camera(camLocation, camRotX, camRotY,camRotZ,renderWidth,renderHeight,camZoom);
		spheres=Sphere.generateRandomSpheres(numSpheres);

		skyColor=new Color((int)(255*Math.random()),(int)(255*Math.random()),(int)(255*Math.random()));
		skyColor=Color.black;

		Runnable[] tasks=new Runnable[renderWidth];
		for(int i=0;i<renderWidth;i++) {
			tasks[i]=new RenderLine(i);
		}
		System.out.println("Threads: "+Runtime.getRuntime().availableProcessors());
		
		double fps=0;
		long startTimeRender = System.currentTimeMillis();
		int f=0;
		while(true) {
			f++;
			Vector.countFast=0;
			Vector.countSlow=0;
			long startTimeFrame = System.currentTimeMillis();
			StdDraw.setPenColor(Color.white);
			StdDraw.filledRectangle(renderWidth/2,renderHeight/2,renderWidth,renderHeight);
			long startTime = System.currentTimeMillis();
			ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
			for(int i=0;i<tasks.length;i++) {
				pool.execute(tasks[i]);
			}
			pool.shutdown();
			try {
				pool.awaitTermination(10000, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			long endTime = System.currentTimeMillis();
			System.out.println("Calculation time: "+((endTime - startTime)) + " milliseconds");
			long startTimeDraw = System.currentTimeMillis();
			Engine.drawFast(allColor);
			
			long endTimeDraw = System.currentTimeMillis();
			System.out.println("Draw time: "+((endTimeDraw - startTimeDraw)) + " milliseconds");
			checkInput();
			long endTimeFrame = System.currentTimeMillis();
			fps=1000.0*1/(endTimeFrame-startTimeFrame);
			System.out.println("Total frame time: "+((endTimeFrame - startTimeFrame)) + " milliseconds");
			System.out.println("FPS: "+fps);
			long endTimeRender = System.currentTimeMillis();
			double avgFps=f/((endTimeRender-startTimeRender)/1000.0);
			System.out.println("Average FPS: "+avgFps);
			System.out.println();
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
	
	private static void checkInput() {
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
		if (checkFor(KeyEvent.VK_LEFT)) {
			camRotY-=speed*30/camZoom;
		}
		if (checkFor(KeyEvent.VK_RIGHT)) {
			camRotY+=speed*30/camZoom;
		}
		if (checkFor(KeyEvent.VK_R)) {
			if(camZoom<10) {
				camZoom+=speed*camZoom;
			}
		}
		if (checkFor(KeyEvent.VK_F)) {
			if(camZoom>.25) {
				camZoom-=speed*camZoom;
			}
		}
		bounceHeight+=bounceSpeed;
		if(bounceHeight<spheres[1].radius) {
			bounceSpeed=bounceSpeed*-1;
		}else {
			bounceSpeed+=bounceAcceleration;
		}
		for(int i=1;i<spheres.length;i++) {
			spheres[i].center.y=bounceHeight;
		}

		camLocation.setX(camX);
		camLocation.setY(camY);
		camLocation.setZ(camZ);
		camera.setLocation(camLocation);

		camera.setxAngle(camRotX);
		camera.setyAngle(camRotY);
		camera.setzAngle(camRotZ);

		camera.setZoom(camZoom);
	}
}
