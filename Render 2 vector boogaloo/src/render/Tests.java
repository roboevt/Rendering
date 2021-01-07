package render;

import java.awt.Color;

public class Tests {
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
	public static int renderWidth=800;
	public static int renderHeight=800;
	public static int renderScale=1;
	public static double bounceHeight;
	public static double bounceSpeed;
	public static double bounceAcceleration;
	public static Color skyColor;
	public static void main(String[] args) {
		StdDraw.setCanvasSize(renderWidth*renderScale,renderHeight*renderScale);
		StdDraw.setXscale(0,renderWidth);
		StdDraw.setYscale(0,renderHeight);
		StdDraw.setPenColor(new Color(0,0,0));
		StdDraw.filledRectangle(renderWidth/2,renderHeight/2,renderWidth,renderHeight);
		StdDraw.setPenRadius(0);
		StdDraw.enableDoubleBuffering();

		while(true) {
			long startTime = System.currentTimeMillis();
			StdDraw.setPenColor(Color.white);
			for(int i=0;i<renderWidth;i++) {
				for(int j=0;j<renderHeight;j++) {
					StdDraw.pixel(i, j);

				}
			}
			StdDraw.show();
			long endTime = System.currentTimeMillis();
			System.out.println("Draw time: "+((endTime - startTime)) + " milliseconds");
			System.out.println();
			StdDraw.setPenColor(Color.black);
			for(int i=0;i<renderWidth;i++) {
				for(int j=0;j<renderHeight;j++) {
					StdDraw.pixel(i, j);

				}
			}
			StdDraw.show();
		}
	}

}
