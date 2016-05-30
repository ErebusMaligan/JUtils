package icon.creator;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Creates a set of up/down triangle arrows.
 * 
 * 
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Nov 5, 2015, 5:32:54 PM 
 */
public class TriangleIconCreator {
	
	public static enum Orientation { 
		UP ( 1 ), 
		DOWN ( 0 ), 
		LEFT ( 2 ), 
		RIGHT ( 3 );
		
		private int index;
		
		Orientation( int index ) {
			this.index = index;
		}
		
		public int toIndex() { return index; }
	}
	
	/**
	 * Returns a matching set of down/up icons of triangle pointers
	 * 
	 * @param bg the darkest color of the switch
	 * @param fg the light color to show when on
	 * @param width the width of the entire image
	 * @param height the height of the entire image
	 * @return array containing two elements in the order: off icon, on icon
	 */
	public Icon[] getIcons( Color bg, Color fg, int width, int height ) {
		return new Icon[] { createTriangle( bg, fg, width, height, Orientation.DOWN ), createTriangle( bg, fg, width, height, Orientation.UP ), createTriangle( bg, fg, width, height, Orientation.LEFT ), createTriangle( bg, fg, width, height, Orientation.RIGHT ) };
	}
	
	private Icon createTriangle( Color bg, Color fg, int w, int h, Orientation o ) {
		BufferedImage i = createIconImage( w, h );
		Graphics2D g = i.createGraphics();
		Shape original = g.getClip();
		Path2D p = new Path2D.Double();
		if ( o.equals( Orientation.UP ) ) {
			p.moveTo( w / 2, 0 );
			p.lineTo( 0, h );
			p.lineTo( w, h );
			p.lineTo( w / 2, 0 );
		} else if ( o.equals( Orientation.DOWN ) ) {
			p.moveTo( 0, 0 );
			p.lineTo( w, 0 );
			p.lineTo( w / 2, h );
			p.lineTo( 0, 0 );
		} else if ( o.equals( Orientation.LEFT ) ) {
			p.moveTo( w, 0 );
			p.lineTo( w, h );
			p.lineTo( 0, h / 2 );
			p.lineTo( w, 0 );
		} else {
			p.moveTo( 0, 0 );
			p.lineTo( w, h / 2 );
			p.lineTo( 0, h );
			p.lineTo( 0, 0 );			
		}
		g.clip( p );
		Paint paint = null;
		if ( o.equals( Orientation.UP ) || o.equals( Orientation.DOWN ) ) {
			paint = new GradientPaint( w, 0, fg, w, h, bg );
		} else {
			paint = new GradientPaint( 0, h, fg, w, 0, bg );
		}
		g.setPaint( paint );
		g.fillRect( 0, 0, w, h );
		g.setClip( original );
		return new ImageIcon( i );
	}
	
	private BufferedImage createIconImage( int w, int h ) {
		return new BufferedImage( w, h, BufferedImage.TYPE_INT_ARGB );
	}
}