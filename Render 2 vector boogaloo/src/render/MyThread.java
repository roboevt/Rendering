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

		Engine engine1=new Engine(Main.renderWidth,Main.renderScale,5,spheres);
		
		while(true) {
			while(Main.doneAll==false) {
				//System.out.println("here"+quadrant);
				//StdDraw.pause(1);
				try {
					Thread.currentThread().sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//Color[][] color=new Color[400][400];
			if(quadrant==1) {
				Main.color1=engine1.calculateFrame(quadrant, Main.camLocation, Main.camRotation,Main.camZoom);
				//System.out.println("1 done");
				Main.done1=true;
			}
			if(quadrant==2) {
				Main.color2=engine1.calculateFrame(quadrant, Main.camLocation, Main.camRotation,Main.camZoom);
				//System.out.println("2 done");
				Main.done2=true;
			}
			if(quadrant==3) {
				Main.color3=engine1.calculateFrame(quadrant, Main.camLocation, Main.camRotation,Main.camZoom);
				//System.out.println("3 done");
				Main.done3=true;
			}
			if(quadrant==4) {
				Main.color4=engine1.calculateFrame(quadrant, Main.camLocation, Main.camRotation,Main.camZoom);
				//System.out.println("4 done");
				Main.done4=true;
			}
			
			
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
