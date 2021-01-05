package render;

import java.awt.Color;

public class MyThread implements Runnable{
	//Engine engine;
	int quadrant;

	public MyThread(/*Engine engine,*/int quadrant) {
		//this.engine=engine;
		this.quadrant=quadrant;
	}
	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName());
		System.out.println("Quadrant: "+quadrant);

		Engine engine1=new Engine(400,2,5);
		Engine engine2=new Engine(400,2,5);
		Engine engine3=new Engine(400,2,5);
		Engine engine4=new Engine(400,2,5);
		
		while(true) {
			while(Main.doneAll==false) {
				//System.out.println("here"+quadrant);
				StdDraw.pause(10);
			}
			//Color[][] color=new Color[400][400];
			if(quadrant==1) {
				Main.color1=engine1.calculateFrame(quadrant, Main.camLocation, Main.camRotation);
				Main.done1=true;
			}
			if(quadrant==2) {
				Main.color2=engine2.calculateFrame(quadrant, Main.camLocation, Main.camRotation);
				Main.done2=true;
			}
			if(quadrant==3) {
				Main.color3=engine3.calculateFrame(quadrant, Main.camLocation, Main.camRotation);
				Main.done3=true;
			}
			if(quadrant==4) {
				Main.color4=engine4.calculateFrame(quadrant, Main.camLocation, Main.camRotation);
				Main.done4=true;
			}
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
