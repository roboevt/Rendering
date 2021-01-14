package render;

public class RenderLine implements Runnable {
	int line;

	public RenderLine(int line) {
		this.line=line;
	}

	@Override
	public void run() {
		Ray[] cameraRays=new Ray[Main.renderWidth];
		Engine engine=new Engine(Main.renderWidth,1,Main.maxBounces);
		cameraRays=Main.camera.generateRaysLine(line);
		Main.allColor[line]=engine.calculateLine(cameraRays,line,Main.objects,Main.light);
	}
}
