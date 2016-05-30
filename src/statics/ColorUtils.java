package statics;

import java.awt.Color;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Nov 5, 2015, 11:33:49 PM 
 */
public class ColorUtils {

	public static Color alignColor( Color in, int min, int max ) {
		int r = in.getRed() > max ? max : in.getRed() < min ? min : in.getRed();
		int g = in.getGreen() > max ? max : in.getGreen() < min ? min : in.getGreen();
		int b = in.getBlue() > max ? max : in.getBlue() < min ? min : in.getBlue();
		return new Color( r, g, b );
	}
	
	public static String toHex( Color c ) {
		return String.format( "%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue() );
	}
	
	public static Color toColor( String hex ) {
		return Color.decode( "#" + hex.toUpperCase() );
	}
}