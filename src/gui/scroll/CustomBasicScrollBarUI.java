package gui.scroll;

import gui.button.CustomButtonUI;
import icon.creator.TriangleIconCreator;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;


/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Jul 30, 2015, 9:19:37 AM 
 */
public class CustomBasicScrollBarUI extends BasicScrollBarUI {
	
	private Color bg;
	
	private Color fg;
	
	private Icon[] arrows;
	
	public CustomBasicScrollBarUI( Color bg, Color fg ) {
		this.bg = bg;
		this.fg = fg;
	}
	
	@Override
	protected JButton createIncreaseButton( int orientation ) {
		JButton b = createButton();
		arrows = new TriangleIconCreator().getIcons( bg, fg, scrollBarWidth / 2, scrollBarWidth / 2 );
		b.setIcon( orientation == EAST ? arrows[ TriangleIconCreator.Orientation.RIGHT.toIndex() ] : arrows[ TriangleIconCreator.Orientation.DOWN.toIndex() ] );
		return b;
	}
	
	@Override
	protected JButton createDecreaseButton( int orientation ) {
		JButton b = createButton();
		arrows = new TriangleIconCreator().getIcons( bg, fg, scrollBarWidth / 2, scrollBarWidth / 2 );
		b.setIcon( orientation == WEST ? arrows[ TriangleIconCreator.Orientation.LEFT.toIndex() ] : arrows[ TriangleIconCreator.Orientation.UP.toIndex() ] );
		return b;
	}
	
	private JButton createButton() {
		JButton b = new JButton();
		b.setPreferredSize( new Dimension( scrollBarWidth, scrollBarWidth ) );
		b.addMouseListener( buttonListener );
		b.setUI( new CustomButtonUI() );
		b.setBackground( bg );
		b.setForeground( fg );
		b.setBorder( BorderFactory.createLineBorder( fg ) );
		return b;
	}
	
	
	@Override
	protected void paintThumb( Graphics g, JComponent c, Rectangle thumbBounds ) {
		Graphics2D g2 = (Graphics2D)g;
		Shape original = g.getClip();
		
		//fill most of the shape
		Point2D start = null;
		Point2D end = null;
		if ( thumbBounds.width > thumbBounds.height ) {
			start = new Point2D.Double( thumbBounds.x + thumbBounds.width / 2, thumbBounds.y );
			end = new Point2D.Double( thumbBounds.x + thumbBounds.width / 2, thumbBounds.height + thumbBounds.y );
		} else {
			start = new Point2D.Double( thumbBounds.x, thumbBounds.y / 2 + thumbBounds.y );
			end = new Point2D.Double( thumbBounds.width + thumbBounds.x, thumbBounds.y / 2 + thumbBounds.y );
		}
		g2.setPaint( new LinearGradientPaint( start, end, new float[] { 0f, .3f, 1f }, new Color[] { bg, bg, fg } ) );
		g2.fill( thumbBounds );		
		
		//outline
		g2.setPaint( fg );
		Rectangle2D r = new Rectangle2D.Double( thumbBounds.getX(), thumbBounds.getY(), thumbBounds.width - 1, thumbBounds.height - 1 );
		g2.setStroke( new BasicStroke( 1 ) );
		g2.draw( r );
		
		//circle in the middle\
		g2.setPaint( new LinearGradientPaint( start, end, new float[] { 0f, .3f, 1f }, new Color[] { fg, fg, bg } ) );  //invert compared to the full shape fill
		Double dia = scrollBarWidth / 2d;
		Ellipse2D e = new Ellipse2D.Double( thumbBounds.getCenterX() - dia / 2, thumbBounds.getCenterY() - dia / 2, dia, dia );
		g2.fill( e );
		g.setClip( original );
	}
	
	@Override
	protected void paintTrack( Graphics g, JComponent c, Rectangle trackBounds ) {
		Graphics2D g2 = (Graphics2D)g;
		Point2D start = null;
		Point2D end = null;
		if ( trackBounds.width > trackBounds.height ) {
			start = new Point2D.Double( trackBounds.getCenterX(), 0 );
			end = new Point2D.Double( trackBounds.getCenterX(), trackBounds.getHeight() );
		} else {
			start = new Point2D.Double( 0, trackBounds.getCenterY() );
			end = new Point2D.Double( trackBounds.getWidth(), trackBounds.getCenterY() );			
		}
		if ( !start.equals( end ) ) { //this happens in certain circumstances if the window the scroll bar is in shrinks far enough
			g2.setPaint( new LinearGradientPaint( start, end, new float[] { 0f, .15f, .35f, .5f, .65f, .85f, 1f }, new Color[] { fg, bg, bg, fg, bg, bg, fg } ) );
		}
		g2.fill( trackBounds );
	}
}