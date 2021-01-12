package render;

import java.awt.Color;

public class Material {
	public boolean reflective;
	public Color color;

	public Material(boolean reflective) {
		this.reflective = reflective;
		this.color = new Color(0,0,0);
	}

	public void setColor(int r, int g, int b) {
		this.color=new Color(r,g,b);
	}

	public Color darken(int darkness) {
		Color darker=this.color;
		for(int i=0;i<darkness;i++) {
			darker=darker.darker();
		}
		return darker;
	}
	public Color scaleColor(double brightness) {
		int r=(int)(color.getRed()*brightness);
		int g=(int)(color.getGreen()*brightness);
		int b=(int)(color.getBlue()*brightness);
		if(r>255) {
			r=255;
		}
		if(r<0) {
			r=0;
		}
		if(g>255) {
			g=255;
		}
		if(g<0) {
			g=0;
		}
		if(b>255) {
			b=255;
		}
		if(b<0) {
			b=0;
		}
		return new Color(r,g,b);
	}
}
