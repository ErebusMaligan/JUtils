package statics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Jul 26, 2015, 11:38:38 PM 
 */
public class ImageUtils {
	
	public static Image getScaledImage( Image srcImg, int w, int h ) {
	    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
	    Graphics2D g2 = resizedImg.createGraphics();
	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, w, h, null);
	    g2.dispose();        
	    return resizedImg;
	}
	
	public static void createGrid( Graphics2D g, int x, int y, Rectangle2D b, int segments, Color c ) {
		g.setColor( c );
		for ( int i = 0; i <= segments; i++ ) {
			g.drawLine( (int)b.getWidth() / segments * i + x, 0 + y, (int)b.getWidth() / segments * i + x, (int)b.getHeight() + y ); //vertical
			g.drawLine( 0 + x, (int)b.getHeight() / segments * i + y, (int)b.getWidth() + x, (int)b.getHeight() / segments * i + y ); //horizontal
		}
	}
}