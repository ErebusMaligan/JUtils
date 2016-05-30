package gui.button;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicButtonUI;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: May 17, 2015, 5:17:17 PM 
 */
public class CustomButtonUI extends BasicButtonUI {
	
	protected static final int borderFraction = 10;
	
	@Override
	public void paint( Graphics g, JComponent c ) {
		Graphics2D g2d = (Graphics2D)g;
		paintStandard( g2d, c );
		super.paint( g, c );
	}
	
	protected void paintStandard( Graphics2D g2d, JComponent c ) {
		Rectangle r = new Rectangle( 0, 0, c.getWidth(), c.getHeight() );
		Color bright = c.getBackground().brighter().brighter().brighter();
		Color dark = c.getBackground().darker();
		
		fillGradient( g2d, r, dark, bright );
		
		g2d.setPaint( dark );
		g2d.setStroke( new BasicStroke( r.height / borderFraction ) );
		drawBorder( g2d, r );
	}
	
	@Override
	protected void paintButtonPressed( Graphics g, AbstractButton b ) {
		Graphics2D g2d = (Graphics2D)g;
		Rectangle r = new Rectangle( 0, 0, b.getWidth(), b.getHeight() );
		Color bright = b.getBackground().brighter().brighter().brighter().brighter().brighter();
		Color dark = b.getBackground().darker().darker().darker();
		
		fillGradient( g2d, r, bright, dark );
		
		g2d.setPaint( b.getBackground() );
		g2d.setStroke( new BasicStroke( r.height / borderFraction / 2 ) );
		drawBorder( g2d, r );	
	}
	
	@Override
	protected void paintFocus( Graphics g, AbstractButton b, Rectangle viewRect, Rectangle textRect, Rectangle iconRect ) {
		Graphics2D g2d = (Graphics2D)g;
		int i = 1;
		g2d.setStroke( new BasicStroke( i, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, i, new float[] { i }, i ) );
		g2d.setPaint( Color.GRAY );
//		drawBorder( g2d, new Rectangle( ( b.getVisibleRect().width - viewRect.width ) / 2 + viewRect.x, ( b.getVisibleRect().height - viewRect.height ) / 2 + viewRect.y, viewRect.width, viewRect.height ) );
		Rectangle r = b.getVisibleRect();
		drawBorder( g2d, new Rectangle( r.x + 1, r.y + 1, r.width - 1, r.height - 1 ) );
	}
	
	protected void fillGradient( Graphics2D g2d, Rectangle r, Color outer, Color inner ) {
		Point2D p1 = new Point2D.Double();
		Point2D p2 = new Point2D.Double();
		p1.setLocation( r.width / 2, 0 );
		p2.setLocation( r.width / 2, r.height );
		float[] shortSides = new float[] { 0f, .5f, 1f };
		g2d.setPaint( new LinearGradientPaint( p1, p2, shortSides, new Color[] { outer, inner, outer }, CycleMethod.REFLECT ) );
		g2d.fill( r );
	}
	
	protected void drawBorder( Graphics2D g2d, Rectangle r ) {
		g2d.drawLine( r.x, r.y, r.width, r.y );
		g2d.drawLine( r.x, r.height, r.width, r.height );
		g2d.drawLine( r.x, r.y, r.x, r.height );		
		g2d.drawLine( r.width, r.y, r.width, r.height );
	}
}