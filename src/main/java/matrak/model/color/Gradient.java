package matrak.model.color;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// http://harmoniccode.blogspot.de/2011/04/bilinear-color-interpolation.html
public final class Gradient {
	
	public static class Color implements Serializable
	{
		private static final long serialVersionUID = 1L;
		
		final int red, green, blue;
		final String hex;
		
		public Color(int r, int g, int b) {
			this.red = r;
			this.green = g;
			this.blue = b;
			this.hex = toHex(red) + toHex(green) + toHex(blue);
		}
		
		public int getRed() {
			return red;
		}
		
		public int getGreen() {
			return green;
		}
		
		public int getBlue() {
			return blue;
		}
		
		public String getHexCode() {
			return hex;
		}
		
		private String toHex(int c) {
			String sc = Integer.toHexString(c);
			return sc.length() == 2 ? sc : "0" + sc;
		}
	}
	
	public enum ColorDistance {
		SMALL (5),
		MID   (10),
		BIG   (15);
		
		final int dist;
		
		private ColorDistance(int d) {
			this.dist = d;
		}
	}

	private Gradient() {
		
	}
	
	public static List<Color> getColorRow(int row, ColorDistance columnDistance, ColorDistance rowDistance) 
	{
		int colorsPerRow = 255 / columnDistance.dist;
		
		int red, blue;
		
		red  = 255 - (row * colorsPerRow);
		blue = (row * rowDistance.dist);
		
		List<Color> colors = new ArrayList<Gradient.Color>(colorsPerRow);
		
		for(int green = 0; green < 256; green += columnDistance.dist) {
			colors.add(new Color(red, green, blue));
		}
		
		return colors;
	}
	
	public static int getMaxColors(ColorDistance columnDistance, ColorDistance rowDistance) 
	{
		int rows = (255 / rowDistance.dist) - 1;
		return rows;
	}
	
}
