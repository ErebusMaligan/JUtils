package gui.progress;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: May 25, 2015, 3:27:28 AM 
 */
public class DigitalJProgressBar extends EnhancedJProgressBar {

	private static final long serialVersionUID = 1L;

	protected int segments = 8;
	
	public DigitalJProgressBar( int min, int max ) {
		this( HORIZONTAL, min, max );
	}
	
	public DigitalJProgressBar( int orient, int min, int max ) {
		super( orient, min, max );
		this.setBackground( barShadow );		
	}
	
	public void setSegments( int segments ) {
		this.segments = segments;
	}
	
	@Override
	protected void paintComponent( Graphics g ) {
		super.paintComponent( g );
		Graphics2D g2 = (Graphics2D)g;
		Rectangle r = new Rectangle( 0, 0, getSize().width, getSize().height );
		drawSegments( r, g2 );
	}
	
	protected void drawSegments( Rectangle r, Graphics2D g2 ) {
		Shape clip = g2.getClip();
		double end = getOrientation() == HORIZONTAL ? r.getWidth() : r.getHeight();
		double s = getOrientation() == HORIZONTAL ? r.getWidth() / segments : r.getHeight() / segments;
		double o = getOrientation() == HORIZONTAL ? r.getHeight() : r.getWidth();
		Color d = new Color( barShadow.getRed(), barShadow.getGreen(), barShadow.getBlue(), 128 );
		for ( double i = 0; i < end; i = i + s ) {
			Rectangle2D sub = getOrientation() == HORIZONTAL ? new Rectangle2D.Double( i, 0d, i + s > end ? end - i : s, o ) : new Rectangle2D.Double( 0d, i, o, i + s > end ? end - i : s );
			Rectangle2D cut = null;
			for ( Double factor : new Double[] { s / 8, s / 4, 1 * s / 5 } ) {
				cut = new Rectangle2D.Double( sub.getX() + factor, sub.getY() + factor, sub.getWidth() - ( 2 * factor ), sub.getHeight() - ( 2 * factor ) );
				Area a = new Area( sub );
				a.subtract( new Area( cut ) );
				g2.clip( a );
				g2.setPaint( d );
				g2.fill( sub );
			}
			g2.setClip( clip );
			g2.setPaint( new Color( 255, 255, 255, 24 ) );
			g2.fill( cut );
		}
	}
}