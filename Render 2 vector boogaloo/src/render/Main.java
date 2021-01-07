package render;

import java.awt.Color;
import java.awt.event.KeyEvent;

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
	public static boolean done1;
	public static boolean done2;
	public static boolean done3;
	public static boolean done4;
	public static boolean doneAll;
	public static Color[][] color1;
	public static Color[][] color2;
	public static Color[][] color3;
	public static Color[][] color4;
	public static Sphere[] spheres;
	public static int renderWidth=1000;
	public static int renderHeight=1000;
	public static int renderScale=1;
	public static double bounceHeight;
	public static double bounceSpeed;
	public static double bounceAcceleration;
	public static Color skyColor;
	public static int maxBounces=3;

	public static void main(String[] args) {
		StdDraw.setCanvasSize(renderWidth*renderScale,renderHeight*renderScale);
		StdDraw.setXscale(0,renderWidth);
		StdDraw.setYscale(0,renderHeight);
		StdDraw.setPenColor(new Color(0,0,0));
		StdDraw.filledRectangle(renderWidth/2,renderHeight/2,renderWidth,renderHeight);
		StdDraw.setPenRadius(0);
		StdDraw.enableDoubleBuffering();
		
		bounceHeight=2;
		bounceAcceleration=-.05;
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
		cameraRays=camera.generateRays();
		color1=new Color[renderWidth][renderHeight];
		color2=new Color[renderWidth][renderHeight];
		color3=new Color[renderWidth][renderHeight];
		color4=new Color[renderWidth][renderHeight];
		done1=false;
		done2=false;
		done3=false;
		done4=false;
		doneAll=true;
		spheres=Sphere.generateRandomSpheres(10);
		//spheres=new Sphere[3];
		//spheres[0]=new Sphere(new Point(0,-1000,0),999,new Material(false));
		//spheres[1]=new Sphere(new Point(0,bounceHeight,0),1,new Material(false));
		//spheres[2]=new Sphere(new Point(2,0,2),1,new Material(true));

		skyColor=new Color((int)(255*Math.random()),(int)(255*Math.random()),(int)(255*Math.random()));
		skyColor=Color.black;

		Thread t1=new Thread(new Render(1, spheres));
		Thread t2=new Thread(new Render(2, spheres));
		Thread t3=new Thread(new Render(3, spheres));
		Thread t4=new Thread(new Render(4, spheres));
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		while(true) {
			long startTime = System.currentTimeMillis();
			done1=false;
			done2=false;
			done3=false;
			done4=false;
			doneAll=false;//Threads start rendering here
			long startTimeCalculate = System.currentTimeMillis();
			while(done1==false||done2==false||false||done3==false||false||done4==false) {
				try {
					Thread.currentThread();
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}//threads are done rendering by here
			doneAll=true;
			long endTimeCalculate = System.currentTimeMillis();
			System.out.println("Calculate all time: "+((endTimeCalculate - startTimeCalculate)) + " milliseconds");

			long startTimeDraw = System.currentTimeMillis();
			StdDraw.setPenColor();
			StdDraw.filledRectangle(renderWidth/2,renderHeight/2,renderWidth,renderHeight);
			Engine.draw(color1,renderWidth,renderHeight);
			Engine.draw(color2,renderWidth,renderHeight);
			Engine.draw(color3,renderWidth,renderHeight);
			Engine.draw(color4,renderWidth,renderHeight);
			StdDraw.show();
			long endTimeDraw = System.currentTimeMillis();
			System.out.println("Draw all time: "+((endTimeDraw - startTimeDraw)) + " milliseconds");

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

			//cameraRays=camera.generateRays();

			long endTime = System.currentTimeMillis();
			System.out.println("Frame time: "+((endTime - startTime)) + " milliseconds");
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
}
