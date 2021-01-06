package render;

import java.awt.Color;

public class MyThread implements Runnable{
	int quadrant;
	Ray[][] cameraRays;
	Sphere[] spheres;

	public MyThread(int quadrant,Ray[][] cameraRays, Sphere[] spheres) {
		this.quadrant=quadrant;
		this.cameraRays=cameraRays;
		this.spheres=spheres;
	}
	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName());
		System.out.println("Quadrant: "+quadrant);

		Engine engine1=new Engine(Main.renderWidth,Main.renderScale,5,Main.spheres);
		
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
				Main.color1=engine1.calculateFrame(quadrant, Main.cameraRays);
				Main.done1=true;
			}
			if(quadrant==2&&Main.done2==false) {
				Main.color2=engine1.calculateFrame(quadrant, Main.cameraRays);
				Main.done2=true;
			}
			if(quadrant==3&&Main.done3==false) {
				Main.color3=engine1.calculateFrame(quadrant, Main.cameraRays);
				Main.done3=true;
			}
			if(quadrant==4&&Main.done4==false) {
				Main.color4=engine1.calculateFrame(quadrant, Main.cameraRays);
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
