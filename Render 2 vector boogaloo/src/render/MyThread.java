package render;

import java.awt.Color;

public class MyThread implements Runnable{
	//Engine engine;
	int quadrant;
	Sphere[] spheres;

	public MyThread(/*Engine engine,*/int quadrant, Sphere[] spheres) {
		//this.engine=engine;
		this.quadrant=quadrant;
		this.spheres=spheres;
	}
	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName());
		System.out.println("Quadrant: "+quadrant);

		Engine engine1=new Engine(400,2,5,spheres);
		
		while(true) {
			while(Main.doneAll==false) {
				//System.out.println("here"+quadrant);
				StdDraw.pause(1);
			}
			//Color[][] color=new Color[400][400];
			if(quadrant==1) {
				Main.color1=engine1.calculateFrame(quadrant, Main.camLocation, Main.camRotation,Main.camZoom);
				Main.done1=true;
			}
			if(quadrant==2) {
				Main.color2=engine1.calculateFrame(quadrant, Main.camLocation, Main.camRotation,Main.camZoom);
				Main.done2=true;
			}
			if(quadrant==3) {
				Main.color3=engine1.calculateFrame(quadrant, Main.camLocation, Main.camRotation,Main.camZoom);
				Main.done3=true;
			}
			if(quadrant==4) {
				Main.color4=engine1.calculateFrame(quadrant, Main.camLocation, Main.camRotation,Main.camZoom);
				Main.done4=true;
			}
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
