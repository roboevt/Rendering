package render;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JFrame;

public class Main extends Canvas{

	public static void main(String[] args) {
		/*JFrame frame = new JFrame("Spheres rendering");
		Canvas canvas = new Main();
		canvas.setSize(800, 800);
		System.out.println(frame.isDoubleBuffered());
		frame.dou
		frame.add(canvas);
		frame.pack();
		frame.setVisible(true);
		 */
		Engine engine=new Engine(200,5,5);
		while(true) {
			long startTime = System.currentTimeMillis();
			System.out.println("Frame start");
			engine.calculateFrame();
			long endTime = System.currentTimeMillis();
			System.out.println("Frame end, total time: "+((endTime - startTime)) + " milliseconds");
			System.out.println();
		}
	}
/*
	public void paint(Graphics g) {
		Engine engine=new Engine(800,1,5);
		Ray[][] cameraRays=engine.camera.generateRays();
		Color[][]color=Engine.calculateRays(cameraRays,engine.spheres,engine.light);

		for(int i=0;i<800;i++) {
			for(int j=0;j<800;j++) {
				g.setColor(color[i][j]);
				g.drawLine(i,j,i,j);
			}
		}
	}*/
}
