package gui.progress;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

import javax.swing.JProgressBar;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: May 25, 2015, 5:24:17 AM 
 */
public abstract class EnhancedJProgressBar extends JProgressBar {

	private static final long serialVersionUID = 1L;

	protected Color shadowColor = Color.DARK_GRAY;
	
	protected Color barShadow = Color.BLACK;
	
	protected int iValue = 0;
	
	protected int iWidth = 0;
	
	protected Thread animationThread;
	
	protected int direction = 1;
	
	protected boolean painting = false;
	
	protected int min2 = -1;
	
	protected int min3 = -1;
	
	protected Color f2 = null;
	
	protected Color f3 = null;
	
	private float[] longSides = new float[] { 0f, .5f, 1f };
	
	public EnhancedJProgressBar( int min, int max ) {
		this( HORIZONTAL, min, max );
	}
	
	public EnhancedJProgressBar( int orient, int min, int max ) {
		super( orient, min, max );
		this.setBackground( barShadow );		
	}
	
	public void setShadowColor( Color shadowColor ) {
		this.shadowColor = shadowColor;
	}
	
	public void setBarShadow( Color barShadow ) {
		this.barShadow = barShadow;
	}
	
	public void setSectionTwo( Color f2, int min2 ) throws Exception {
		if ( min2 < this.getMinimum() || min2 > this.getMaximum() || f2 == null ) {
			throw new Exception( "Color must not be null and value must be greater than minimum and less than maximum of zone 1" );
		}
		this.f2 = f2;
		this.min2 = min2;
	}
	
	public void setSectionThree( Color f3, int min3 ) throws Exception {
		if ( min2 == -1 && f2 == null ) {
			throw new Exception( "Zone 2 values must be set first" );
		}
		if ( min3 < min2 || min3 > this.getMaximum() || f3 == null ) {
			throw new Exception( "Color must not be null and value must be greater than minimum and less than maximum of zone 2" );
		}
		this.f3 = f3;
		this.min3 = min3;
	}
	
	@Override
	public void setIndeterminate( boolean newValue ) {
		super.setIndeterminate( newValue );
		if ( newValue ) {
			if ( animationThread == null ) {
				animationThread = new AnimationThread();
				animationThread.start();
			}
		} else {
			animationThread = null;
		}
	}
	
	@Override
	protected void paintComponent( Graphics g ) {
		super.paintComponent( g );
		Graphics2D g2 = (Graphics2D)g;
		Rectangle r = new Rectangle( 0, 0, getSize().width, getSize().height );
		drawBars( r, g2 );
	}
	
	protected void drawBars( Rectangle r, Graphics2D g2 ) {
		float[] shortSides = new float[] { 0f, .2f, .8f,  1f };
		Point2D p1 = new Point2D.Double();
		Point2D p2 = new Point2D.Double();
		if ( getOrientation() == HORIZONTAL ) {
			p1.setLocation( r.width / 2, r.y );
			p2.setLocation( p1.getX(), r.height );			
		} else {
			p1.setLocation( r.x, r.height / 2 );
			p2.setLocation( r.width, p1.getY() );
		}
		g2.setPaint( new LinearGradientPaint( p1, p2, shortSides, new Color[] { shadowColor, getBackground(), getBackground(), shadowColor }, CycleMethod.REFLECT ) );
		g2.fill( r );
		g2.setPaint( new LinearGradientPaint( p1, p2, longSides, new Color[] { barShadow, getForeground(), barShadow }, CycleMethod.REFLECT ) );
		iValue = calculateDrawPoint( r );
		if ( isIndeterminate() ) {
			if ( getOrientation() == HORIZONTAL ) {
				g2.fillRect( iValue, r.y, iWidth, r.height );	
			} else {
				g2.fillRect( r.x, r.height - iValue - iWidth, r.width, iWidth );
			}
		} else {
			if ( min2 != -1 ) {
				if ( min3 != -1 ) {
					if ( getValue() > min3 ) {
						drawBarRect( g2, r, p1, p2, f3 );
						iValue = calculateDrawPoint( r, (double)min3 );
					}
				}
				if ( getValue() > min2 ) {
					drawBarRect( g2, r, p1, p2, f2 );
					iValue = calculateDrawPoint( r, (double)min2 );
				}
				drawBarRect( g2, r, p1, p2, getForeground() );
			} else {
				drawBarRect( g2, r, p1, p2, getForeground() );
			}
		}
	}
	
	private void drawBarRect( Graphics2D g2, Rectangle r, Point2D p1, Point2D p2, Color c ) {
		g2.setPaint( new LinearGradientPaint( p1, p2, longSides, new Color[] { barShadow, c, barShadow }, CycleMethod.REFLECT ) );
		g2.fillRect( r.x, ( getOrientation() == HORIZONTAL ? r.y : r.height - iValue ), ( getOrientation() == HORIZONTAL ? iValue : r.width ), r.height );
	}
	
	protected int calculateDrawPoint( Rectangle r ) {
		return calculateDrawPoint( r, null );
	}
	
	protected int calculateDrawPoint( Rectangle r, Double i ) {
		int ret = 0;
		if ( isIndeterminate() ) {
			if ( !painting ) {
				int max = getOrientation() == HORIZONTAL ? getSize().width : getSize().height;
				ret = iValue + ( direction * iWidth );
				if ( ret + iWidth >= max ) {
					ret = max - iWidth;
					direction = -1;
				} else if ( ret < 0 ) {
					ret = iWidth;
					direction = 1;
				}
				painting = true;
			} else {
				ret = iValue;
			}
		} else {
			if ( i == null ) {
				i = (double)this.getValue();
			}
			double d = i / (double)this.getMaximum();
			double p = ( getOrientation() == HORIZONTAL ? r.width : r.height ) * d;
			ret = Double.valueOf( p ).intValue();
		}
		return ret;
	}
	
	private class AnimationThread extends Thread {

		//these look and feel settings don't work under Nimbus for instance because repaintInterval doesn't exist.
		private int sleep = 50;//UIManager.getInt( "ProgressBar.repaintInterval" );
		
		private int cycle = 1500;//UIManager.getInt( "ProgressBar.cycleTime" );
		
		private int fraction = cycle / sleep;
		
		private int secondMultiplyer = cycle / 1000;
		
		private EnhancedJProgressBar bar = EnhancedJProgressBar.this;

		public void run() {
			while ( bar.isIndeterminate() ) {
				int max = bar.getOrientation() == HORIZONTAL ? bar.getSize().width : bar.getSize().height;
				bar.iWidth = max / fraction * secondMultiplyer;
				repaint();
				bar.painting = false;
				try {
					Thread.sleep( sleep );
				} catch ( InterruptedException e ) {
					setIndeterminate( false );
					e.printStackTrace();
				}
			}
		}
	}
}
