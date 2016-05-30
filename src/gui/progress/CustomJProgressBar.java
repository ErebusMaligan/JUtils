package gui.progress;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: May 14, 2015, 10:36:34 PM 
 */
public class CustomJProgressBar extends EnhancedJProgressBar {

	private static final long serialVersionUID = 1L;
	
	public enum SliderStyle { CLAMP, BAR, POINTER, BAR_AND_CLAMP };
	
	protected boolean showMajorTicks = true;
	
	protected boolean showMinorTicks = true;
	
	protected int majSeg = 8;
	
	protected int minSeg = 4;
	
	protected float majW = 1.0f;
	
	protected float majL = 1f / 4f;
	
	protected float minW = 1.0f;
	
	protected float minL = 1f / 8f;
	
	protected Color tickColor = Color.BLACK;
	
	protected Color sliderColor;
	
	protected boolean drawSlider = true;
	
	protected int sliderWidth = 8;
	
	protected double arc = 4.0;
	
	private SliderStyle sliderStyle = SliderStyle.CLAMP;
	
	public CustomJProgressBar( int min, int max ) {
		this( HORIZONTAL, min, max );
	}
	
	public CustomJProgressBar( int orient, int min, int max ) {
		this( orient, min, max, SliderStyle.CLAMP );
	}
	
	public CustomJProgressBar( int orient, int min, int max, SliderStyle style ) {
		super( orient, min, max );
		this.sliderStyle = style;
		this.setBackground( barShadow );		
	}
	
	public void setSliderWidth( int sliderWidth ) {
		this.sliderWidth = sliderWidth;
	}
	
	public void setMinorTickSegments( int minSeg ) {
		this.minSeg = minSeg;
	}
	
	public void setMajorTickSegments( int majSeg ) {
		this.majSeg = majSeg;
	}
	
	public void setTickColor( Color tickColor ) {
		this.tickColor = tickColor;
	}
	
	public void setSliderColor( Color sliderColor ) {
		this.sliderColor = sliderColor;
	}
	
	public void setSliderStyle( SliderStyle sliderStyle ) {
		this.sliderStyle = sliderStyle;
	}
	
	public void setShowSlider( boolean drawSlider ) {
		this.drawSlider = drawSlider;
	}
	
	public void setShowMajorTicks( boolean showMajorTicks ) {
		this.showMajorTicks = showMajorTicks;
	}
	
	public void setShowMinorTicks( boolean showMinorTicks ) {
		this.showMinorTicks = showMinorTicks;
	}
	
	@Override
	protected void paintComponent( Graphics g ) {
		super.paintComponent( g );
		Graphics2D g2 = (Graphics2D)g;
		Rectangle r = new Rectangle( 0, 0, getSize().width, getSize().height );
		drawMajorTicks( r, g2 );
		drawMinorTicks( r, g2 );
		drawSliders( r, g2 );
	}
	
	protected void drawMajorTicks( Rectangle r, Graphics2D g2 ) {
		if ( showMajorTicks ) {
			int[] mticks = calculateTickMarks( majSeg, 0, (getOrientation() == HORIZONTAL ? r.width : r.height ), true );
			for ( int i : mticks ) {
				g2.setStroke( new BasicStroke( majW ) );
				g2.setPaint( tickColor );
				if ( getOrientation() == HORIZONTAL ) {
					g2.drawLine( i, 0, i, (int)( r.height * majL ) );
					g2.drawLine( i, r.height, i, (int)( r.height * ( 1 - majL ) ) );  //the + insets shouldn't be needed here, but it is, otherwise the tick lines appear to float off the top
				} else {
					g2.drawLine( 0, i, (int)( r.width * majL ), i );
					g2.drawLine( r.width, i, (int)( r.width * ( 1 - majL ) ), i );
				}
			}
		}
	}
	
	protected void drawMinorTicks( Rectangle r, Graphics2D g2 ) {
		if ( showMinorTicks ) {
			int[] mticks = calculateTickMarks( majSeg, 0, (getOrientation() == HORIZONTAL ? r.width : r.height ), true );
			for ( int m = 1; m < mticks.length; m++ ) {
				for ( int i : calculateTickMarks( minSeg, mticks[ m - 1 ], mticks[ m ], false) ) {
					g2.setStroke( new BasicStroke( minW ) );
					g2.setPaint( tickColor );
					if ( getOrientation() == HORIZONTAL ) {
						g2.drawLine( i, 0, i, (int)( r.height * minL ) );
						g2.drawLine( i, r.height, i, (int)( r.height * ( 1 - minL ) ) );
					} else {
						g2.drawLine( 0, i, (int)( r.width * minL ), i );
						g2.drawLine( r.width, i, (int)( r.width * ( 1 - minL ) ), i );
					}
				}
			}
		}
	}
	
	protected void drawSliders( Rectangle r, Graphics2D g2 ) {
		if ( drawSlider ) {
			if ( sliderStyle == SliderStyle.CLAMP ) {
				drawClampSliders( r, g2 );
			} else if ( sliderStyle == SliderStyle.BAR ) {
				drawBarSlider( r, g2 );
			} else if ( sliderStyle == SliderStyle.BAR_AND_CLAMP ) {
				drawBarSlider( r, g2 );
				drawClampSliders( r, g2 );				
			}
		}
	}
	
	protected void drawBarSlider( Rectangle r, Graphics2D g2 ) {
		RoundRectangle2D.Double p = new RoundRectangle2D.Double();
		Rectangle2D.Double m = new Rectangle2D.Double();
		Point2D p1 = new Point2D.Double();
		Point2D p2 = new Point2D.Double();
		if ( getOrientation() == HORIZONTAL ) {
			p.setRoundRect( calculateDrawPoint( r ) - ( sliderWidth ), 0, sliderWidth, r.height, arc, arc );
			m.setRect( r.x - p.width, r.y, p.width, r.height );
			p1.setLocation( p.x, p.height / 2 );
			p2.setLocation( p.x + p.width, p.height / 2 );
		} else {
			p.setRoundRect( 0, r.height - calculateDrawPoint( r ), r.width, sliderWidth, arc, arc );
			m.setRect( r.x, r.y + p.height, r.width, p.height  );
			p1.setLocation(  p.width / 2, p.y + p.height );
			p2.setLocation( p.width / 2, p.y );
			
		}
		Color o = sliderColor == null ? getForeground() : sliderColor;
		Color s = new Color( o.getRed(), o.getGreen(), o.getBlue(), 255 );
		Color s2 = new Color( o.getRed(), o.getGreen(), o.getBlue(), 64 );
		Color s3 = new Color( o.getRed(), o.getGreen(), o.getBlue(), 32 );
		Area a = new Area( p );
		a.subtract( new Area( m ) );
		g2.clip( a );
		g2.setPaint( new LinearGradientPaint( p1, p2, new float[] { 0f, .8f, 1f }, new Color[] { s3, s2, s }, CycleMethod.NO_CYCLE ) );
		g2.fill( p );
	}
	
	protected void drawClampSliders( Rectangle r, Graphics2D g2 ) {
		RoundRectangle2D.Double p = new RoundRectangle2D.Double();
		RoundRectangle2D.Double c = new RoundRectangle2D.Double();
		Rectangle2D.Double e = new Rectangle2D.Double();
		Rectangle2D.Double m = new Rectangle2D.Double();
		Point2D p1 = new Point2D.Double();
		Point2D p2 = new Point2D.Double();
		if ( getOrientation() == HORIZONTAL ) {
			p.setRoundRect( calculateDrawPoint( r ) - ( sliderWidth / 2 ), 0, sliderWidth, r.height, arc, arc );
			c.setRoundRect( calculateDrawPoint( r ) - ( sliderWidth / 4 ), r.height / 16, sliderWidth / 2, 7 * r.height / 8, arc, arc );
			e.setRect( e.x = calculateDrawPoint( r ) - ( sliderWidth * 2 ), r.height / 4, sliderWidth * 4, r.height / 2 );
			m.setRect( r.x - p.width, r.y, p.width, r.height );
			p1.setLocation( p.x, p.height / 2 );
			p2.setLocation( p.x + p.width, p.height / 2 );
		} else {
			p.setRoundRect( 0, r.height - calculateDrawPoint( r ) - ( sliderWidth / 2 ), r.width, sliderWidth, arc, arc );
			c.setRoundRect( r.width / 16, r.height - calculateDrawPoint( r ) - ( sliderWidth / 4 ), 7 * r.width / 8, sliderWidth / 2, arc, arc );
			e.setRect( r.width / 4, r.height - calculateDrawPoint( r ) - ( sliderWidth * 2 ), r.width / 2, sliderWidth * 4 );
			m.setRect( r.x, r.y + p.height, r.width, p.height  );
			p1.setLocation(  p.width / 2, p.y + p.height );
			p2.setLocation( p.width / 2, p.y );
		}
		Area a = new Area( p );
		a.subtract( new Area( c ) );
		a.subtract( new Area( e ) );
		a.subtract( new Area( m ) );
		g2.clip( a );
		g2.setPaint( shadowColor );
		g2.draw( p );
		g2.setPaint( new LinearGradientPaint( p1, p2, new float[] { 0f, .5f,  1f }, new Color[] { shadowColor, sliderColor == null ? getBackground() : sliderColor, shadowColor }, CycleMethod.REFLECT ) );
		g2.fill( p );
	}
	
	
	
	protected int[] calculateTickMarks( int amount, int start, int end, boolean includeEndPoints ) {
		int[] ret = new int[ amount + 1];
		for ( int i = 0; i <= amount; i++ ) {
			double x = ( (double)( end - start ) ) / (double)amount;
			if ( !( ( i == 0 && !includeEndPoints ) || ( i == amount && !includeEndPoints ) ) ) {
				ret[ i ] = (int)( ( i * x ) + start );
			}
		}
		return ret;
	}
}