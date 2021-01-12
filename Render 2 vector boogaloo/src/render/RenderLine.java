package render;

public class RenderLine implements Runnable {
	int line;
	Sphere[] spheres;

	public RenderLine(int line, Sphere[] spheres) {
		this.line=line;
		this.spheres=spheres;
	}

	@Override
	public void run() {
		Ray[] cameraRays=new Ray[Main.renderWidth];

		Engine engine=new Engine(Main.renderWidth,1,Main.maxBounces,Main.spheres);

		cameraRays=Main.camera.generateRaysLine(line);
		Main.allColor[line]=engine.calculateLine(cameraRays,line,spheres,Main.light);
		//System.out.println(Thread.currentThread().getName()+" is done");
	}

}
