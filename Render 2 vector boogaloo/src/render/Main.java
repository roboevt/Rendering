package render;

import java.awt.Color;
import java.awt.event.KeyEvent;

public class Main {
	public static double camX;
	public static double camY;
	public static double camZ;
	public static double speed=.1;
	public static Point camLocation=new Point(0,1,-6);
	public static double camRotX;
	public static double camRotY;
	public static double camRotZ;
	public static Vector camRotation=new Vector(0,90,0);
	public static double camZoom;
	public static boolean done1;
	public static boolean done2;
	public static boolean done3;
	public static boolean done4;
	public static boolean doneAll;
	public static Color[][] color1;
	public static Color[][] color2;
	public static Color[][] color3;
	public static Color[][] color4;
	public static int renderWidth=400;
	public static int renderHeight=400;
	public static int renderScale=2;

	public static void main(String[] args) {
		StdDraw.setCanvasSize(renderWidth*renderScale,renderHeight*renderScale);
		StdDraw.setXscale(0,renderWidth);
		StdDraw.setYscale(0,renderHeight);
		StdDraw.setPenColor(new Color(0,0,0));
		StdDraw.filledRectangle(renderWidth/2,renderHeight/2,renderWidth,renderHeight);
		StdDraw.setPenRadius(0);
		StdDraw.enableDoubleBuffering();
		
		camX=0;
		camY=1;
		camZ=-6;
		camRotX=-40;
		camRotY=90;
		camRotZ=0;
		camZoom=1;
		camLocation.setX(camX);
		camLocation.setY(camY);
		camLocation.setZ(camZ);
		camRotation.setX(camRotX);
		camRotation.setY(camRotY);
		camRotation.setX(camRotZ);
		color1=new Color[400][400];
		color2=new Color[400][400];
		color3=new Color[400][400];
		color4=new Color[400][400];
		done1=false;
		done2=false;
		done3=false;
		done4=false;
		doneAll=true;
		//Engine engine1=new Engine(400,2,5);
		//Engine engine2=new Engine(400,2,5);
		//Engine engine3=new Engine(400,2,5);
		//Engine engine4=new Engine(400,2,5);
		Thread t1=new Thread(new MyThread(1));
		Thread t2=new Thread(new MyThread(2));
		Thread t3=new Thread(new MyThread(3));
		Thread t4=new Thread(new MyThread(4));
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		while(true) {
			while(done1==false&&done2==false&&done3==false&&done4==false) {
				StdDraw.pause(10);
			}
			StdDraw.setPenColor();
			StdDraw.filledRectangle(200,200,400,400);
			done1=false;
			done2=false;
			done3=false;
			done4=false;
			doneAll=true;
			Engine.draw(color1,400,400);
			Engine.draw(color2,400,400);
			Engine.draw(color3,400,400);
			Engine.draw(color4,400,400);
			StdDraw.show();
			
			doneAll=false;
			
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
				if(camZoom<10) {
					camZoom+=speed*camZoom;
				}
			}
			if (checkFor(KeyEvent.VK_F)) {
				if(camZoom>.25) {
					camZoom-=speed*camZoom;
				}
			}

			if (checkFor(KeyEvent.VK_LEFT)) {
				camRotY-=speed*30/camZoom;
			}
			if (checkFor(KeyEvent.VK_RIGHT)) {
				camRotY+=speed*30/camZoom;
			}
			camLocation.setX(camX);
			camLocation.setY(camY);
			camLocation.setZ(camZ);
			camRotation.setX(camRotX);
			camRotation.setY(camRotY);
			camRotation.setX(camRotZ);
		}
		//System.out.println("here");
		/*while(true) {
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
				if(camZoom<10) {
					camZoom+=speed*camZoom;
				}
			}
			if (checkFor(KeyEvent.VK_F)) {
				if(camZoom>.25) {
					camZoom-=speed*camZoom;
				}
			}

			if (checkFor(KeyEvent.VK_LEFT)) {
				camRotY-=speed*30/camZoom;
			}
			if (checkFor(KeyEvent.VK_RIGHT)) {
				camRotY+=speed*30/camZoom;
			}
			camLocation.setX(camX);
			camLocation.setY(camY);
			camLocation.setZ(camZ);
			camRotation.setX(camRotX);
			camRotation.setY(camRotY);
			camRotation.setX(camRotZ);
		}*/
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
