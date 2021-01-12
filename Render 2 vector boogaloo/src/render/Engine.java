package render;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

public class Engine {
	public int renderWidth;
	public int renderHeight;
	public int renderScale;
	public int maxBounces;
	public double speed=.2;
	public static int magnitudeCount=0;
	public float camX;
	public float camY;
	public float camZ;
	public float camRotX;
	public float camRotY;
	public float camRotZ;
	public float camZoom;
	public Camera camera;
	public Sphere[] spheres;
	public PointF light;
	Ray[][] cameraRays;
	Color skyColor;
	public int castRays;

	private static JFrame frame;
	private static JLabel label;

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
		PointF camLocation=new PointF(camX,camY,camZ);
		camera=new Camera(camLocation,camRotX,camRotY,camRotZ,renderWidth,renderHeight,camZoom);
		light=new PointF(500,500,0);
		this.cameraRays=new Ray[renderWidth][renderHeight];
		skyColor=new Color(0,0,0);
		castRays=0;

	}

	public void setSkyColor(Color color) {
		this.skyColor=color;
	}

	public  Color[] calculateLine(Ray[] cameraRays, int line, Sphere[] spheres, PointF light){
		Color[] colorOut=new Color[renderWidth];
		for(int j=0;j<cameraRays.length;j++) {
			colorOut[j]=calculateRay(cameraRays[j],spheres,light,0);
		}
		return colorOut;

	}

	public  Color[][] calculateRays(Ray[][] cameraRays,int startX, int endX, int startY, int endY, Sphere[] spheres, PointF light){
		Color[][] colorOut=new Color[renderWidth][renderHeight];
		for(int i=startX;i<endX;i++) {
			for(int j=startY;j<endY;j++) {
				colorOut[i][j]=calculateRay(cameraRays[i][j],spheres,light,0);
			}
		}
		return colorOut;

	}

	private  Color calculateRay(Ray ray, Sphere[] spheres, PointF light, int bounces) { // the main attraction
		castRays++;
		Color color=skyColor;
		float distanceToSphere=ray.distanceToSpheres(spheres);
		float brightness=0;
		int hitSphere=1;
		if(distanceToSphere<Integer.MAX_VALUE-1) {//ray hits sphere
			VectorF vectorToSphere=ray.getDirection().multiply(distanceToSphere);
			PointF pointOnSphere=ray.getOrigin().add(vectorToSphere.toPointF());
			for(int i=0;i<spheres.length;i++) {
				VectorF centerToPoint=new VectorF(pointOnSphere.x-spheres[i].getCenter().x, pointOnSphere.y-spheres[i].getCenter().y, pointOnSphere.z-spheres[i].getCenter().z);
				if(Math.abs(centerToPoint.magnitude()-spheres[i].getRadius())<.001) {//point is on sphere i
					hitSphere=i;
				}
			}

			if(spheres[hitSphere].material.reflective&&bounces<maxBounces) { //if material of hit sphere is reflective, and not at max bounce limit, recurse
				VectorF normal=new VectorF(pointOnSphere.x-spheres[hitSphere].getCenter().x, pointOnSphere.y-spheres[hitSphere].getCenter().y, pointOnSphere.z-spheres[hitSphere].getCenter().z);
				//pointOnSphere.add(normal.multiply(.001).toPoint()); //the bumps the point away from the surface a bit, seems to not be necessary for some reason
				Ray reflection=ray.calculateReflection(new Ray(spheres[hitSphere].getCenter(),normal), pointOnSphere);
				color=calculateRay(reflection,spheres,light,bounces+1);

			}else {// if not reflective or already at max bounces, check shadows and end.
				if(checkShadow(pointOnSphere,spheres,light)) {
					return Color.black;
				}else {
					brightness=checkDiffuse(pointOnSphere,spheres[hitSphere],light,ray);
					//brightness=1;
					return spheres[hitSphere].material.scaleColor(brightness);
				}
			}
		}	
		return color;
	}

	private static boolean checkShadow(PointF pointOnSphere, Sphere[] spheres, PointF light) {
		VectorF vectorToLight=new VectorF(light.x-pointOnSphere.x,light.y-pointOnSphere.y,light.z-pointOnSphere.z);
		Ray rayToLight=new Ray(pointOnSphere,vectorToLight.normalize());
		if(rayToLight.distanceToSpheres(spheres)<vectorToLight.magnitude()) { //in shadow
			return true; //in shadow
		}else {
			return false; //in light
		}
	}

	private static float checkDiffuse(PointF pointOnSphere, Sphere sphere, PointF light, Ray ray) {
		VectorF lightVector=new VectorF(light.x-pointOnSphere.x,light.y-pointOnSphere.y,light.z-pointOnSphere.z);
		VectorF normal=new VectorF(pointOnSphere.x-sphere.getCenter().x, pointOnSphere.y-sphere.getCenter().y, pointOnSphere.z-sphere.getCenter().z);
		float angle=(float) Math.acos(normal.dot(lightVector)/(lightVector.magnitude()*normal.magnitude()));
		return (float) Math.cos(angle);
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

	public static void drawFast(Color[][] colorIn) {
		BufferedImage image=new BufferedImage(colorIn.length,colorIn[0].length,BufferedImage.TYPE_INT_RGB);
		WritableRaster raster =  image.getRaster();
		int[] pixel=new int[3];
		for(int x=0;x<colorIn.length;x++) {
			for(int y=0;y<colorIn[0].length;y++) {
				pixel[0]   = colorIn[x][y].getRed();     // red component
				pixel[1] = colorIn[x][y].getGreen();    // green component
				pixel[2] = colorIn[x][y].getBlue();  // blue component
				
				raster.setPixel((int)StdDraw.scaleX(x), ((int)StdDraw.scaleY(y)-1), pixel);
			}
		}
		StdDraw.display(image);
	}

	public static void display(BufferedImage image){
		if(frame==null){
			frame=new JFrame();
			frame.setTitle("stained_image");
			frame.setSize(image.getWidth(), image.getHeight());
			frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			label=new JLabel();
			label.setIcon(new ImageIcon(image));
			frame.getContentPane().add(label,BorderLayout.CENTER);
			frame.setLocationRelativeTo(null);
			frame.pack();
			frame.setVisible(true);
		}else label.setIcon(new ImageIcon(image));
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
	}
}
