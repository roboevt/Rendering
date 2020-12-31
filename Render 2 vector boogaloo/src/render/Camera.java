package render;

public class Camera {
	public Point location;
	public int renderWidth;
	public int renderHeight;
	public double zoom;

	public Camera(Point location, int renderWidth, int renderHeight, double zoom) {
		super();
		this.location = location;
		this.renderWidth=renderWidth;
		this.renderHeight=renderHeight;
		this.zoom=zoom;
	}

	public Ray[][] generateRays(){
		Ray[][] ray=new Ray[renderWidth][renderHeight];
		
		for(int i=0;i<renderWidth;i++) {
			for(int j=0;j<renderHeight;j++) {
				double x=(i-(renderWidth/2.0))/(renderWidth/2.0);
				double y=(j-(renderHeight/2.0))/(renderHeight/2.0);	
				ray[i][j]=new Ray(location,new Vector(x,y,zoom));
				ray[i][j].setDirection(ray[i][j].getDirection().normalize());
			}
			//System.out.println(ray[i][0].getDirection().toString());
		}
		return ray;
	}
}
