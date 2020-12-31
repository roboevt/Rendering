package render;

import java.awt.Color;

public class Material {
	public boolean reflective;
	public Color color;
	
	public Material(boolean reflective) {
		this.reflective = reflective;
		this.color = new Color(255,255,255);
	}
	
	public void setColor(int r, int g, int b) {
		this.color=new Color(r,g,b);
	}
}
