package render;

import java.awt.Color;

public class Render implements Runnable{
	int quadrant;
	Sphere[] spheres;
	Color skyColor;
	Engine engine;

	public Render(int quadrant, Sphere[] spheres) {
		this.quadrant=quadrant;
		this.spheres=spheres;
	}
	
	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName());
		System.out.println("Quadrant: "+quadrant);

		engine=new Engine(Main.renderWidth,Main.renderScale,Main.maxBounces,Main.spheres);
		engine.setSkyColor(Main.skyColor);
		
		Ray[][] cameraRays=new Ray[Main.renderWidth][Main.renderHeight];
		
		//Color[][] color=new Color[Main.renderWidth][Main.renderHeight];
		
		while(true) {
			//long startTime = System.currentTimeMillis();
			while(Main.doneAll==true) {
				//System.out.println("here"+quadrant);
				//StdDraw.pause(1);
				try {
					Thread.currentThread().sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			long startTime = System.currentTimeMillis();
			//Color[][] color=new Color[400][400];
			if(quadrant==1&&Main.done1==false) {
				cameraRays=Main.camera.generateRaysQuadrant(1);
				Main.color1=engine.calculateFrame(quadrant, cameraRays);
				//color=engine.calculateFrame(quadrant, cameraRays);
				Main.done1=true;
			}
			if(quadrant==2&&Main.done2==false) {
				cameraRays=Main.camera.generateRaysQuadrant(2);
				Main.color2=engine.calculateFrame(quadrant, cameraRays);
				Main.done2=true;
			}
			if(quadrant==3&&Main.done3==false) {
				cameraRays=Main.camera.generateRaysQuadrant(3);
				Main.color3=engine.calculateFrame(quadrant, cameraRays);
				Main.done3=true;
			}
			if(quadrant==4&&Main.done4==false) {
				cameraRays=Main.camera.generateRaysQuadrant(4);
				Main.color4=engine.calculateFrame(quadrant, cameraRays);
				Main.done4=true;
			}
			long endTime = System.currentTimeMillis();
			System.out.println("Calculate quadrant "+quadrant+" time: "+((endTime - startTime)) + " milliseconds");
			while(Main.doneAll==false) {
				try {
					Thread.currentThread().sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
