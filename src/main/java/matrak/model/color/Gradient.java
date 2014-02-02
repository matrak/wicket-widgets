package matrak.model.color;

import java.util.ArrayList;
import java.util.List;

import matrak.paging.Pagination;

// http://harmoniccode.blogspot.de/2011/04/bilinear-color-interpolation.html
public final class Gradient {
	
	public static class Color 
	{
		final int red, green, blue;
		final String hex;
		
		public Color(int r, int g, int b) {
			this.red = r;
			this.green = g;
			this.blue = b;
			this.hex = Integer.toHexString(red) + Integer.toHexString(green) + Integer.toHexString(blue);
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
	
	public static Pagination<Color> getColorRow(int row, ColorDistance columnDistance, ColorDistance rowDistance) {
		int colorsPerRow = 255 / columnDistance.dist;
		
		Pagination<Color> pg = new Pagination<Gradient.Color>(row, colorsPerRow);
		int red, blue;
		
		red  = 255 - (row * colorsPerRow);
		blue = (row * colorsPerRow);
		
		List<Color> colors = new ArrayList<Gradient.Color>(colorsPerRow);
		
		for(int green = 0; green < 256; green += columnDistance.dist) {
			colors.add(new Color(red, blue, green));
		}
		
		pg.setItems(colors, 255 * 255);
		
		return pg;
	}
	
}
