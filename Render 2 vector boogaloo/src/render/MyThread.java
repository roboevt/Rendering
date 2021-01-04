package render;

import java.awt.Color;

public class MyThread implements Runnable{
	Engine engine;
	int quadrant;

	public MyThread(Engine engine,int quadrant) {
		this.engine=engine;
		this.quadrant=quadrant;
	}
	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName());
		System.out.println("Quadrant: "+quadrant);
		while(true) {
			while(Main.doneAll==false) {
				//System.out.println("here"+quadrant);
				StdDraw.pause(100);
			}
			Color[][] color=new Color[400][400];
			color=engine.calculateFrame(quadrant, Main.camLocation, Main.camRotation);
			if(quadrant==1) {
				Main.color1=color;
				Main.done1=true;
			}
			if(quadrant==2) {
				Main.color2=color;
				Main.done2=true;
			}
			if(quadrant==3) {
				Main.color3=color;
				Main.done3=true;
			}
			if(quadrant==4) {
				Main.color4=color;
				Main.done4=true;
			}
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
