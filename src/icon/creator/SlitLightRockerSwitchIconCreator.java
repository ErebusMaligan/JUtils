package icon.creator;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.MultipleGradientPaint;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Creates a set of on/off, lit when on, inset, rounded-rectangle rocker switches.
 * 
 * The bg parameter passed to any of the public methods is optimized for RGB values of 10 to 200.
 * 
 * Pure colors like BLACK, WHITE, RED do not work well, however
 * 
 *	new Color( 10, 10, 10 );
 *	new Color( 200, 200, 200 );
 *	new Color( 200, 0, 0 );
 *
 *	work well to achieve BLACk, WHITE, and RED -like colors (respectively) that look suitable
 * 
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Nov 5, 2015, 5:32:54 PM 
 */
public class SlitLightRockerSwitchIconCreator {
	
	private static final double f = 8d;
	
	private static final double div = f * 20d;
	
	/**
	 * Returns a matching set of off/on icons of a rocker switch with the provided parameters
	 * 
	 * @param bg the darkest color of the switch
	 * @param light the light color to show when on
	 * @param width the width of the entire image
	 * @param height the height of the entire image
	 * @return array containing two elements in the order: off icon, on icon
	 */
	public Icon[] getIcons( Color bg, Color light, int width, int height ) {
		return new Icon[] { getOff( bg, light, width, height ), getOn( bg, light, width, height ) };
	}
	
	public Icon getOn( Color bg, Color light, int w, int h ) {
		BufferedImage i = createIconImage( w, h );
		Graphics2D g = i.createGraphics();
		standard( g, bg, w, h );
		createSwitch( g, bg, light, w, h, true );
		return new ImageIcon( i );
	}
	
	public Icon getOff( Color bg, Color light, int w, int h ) {
		BufferedImage i = createIconImage( w, h );
		Graphics2D g = i.createGraphics();
		standard( g, bg, w, h );
		createSwitch( g, bg, light, w, h, false );
		return new ImageIcon( i );
	}
	
	private void standard( Graphics2D g, Color bg, int w, int h ) {
		createSwitchBorder( g, bg, w, h );
		createSwitchVoid( g, bg, w , h );
	}
	
	private RoundRectangle2D getSwitchShape( int w, int h ) {
		return new RoundRectangle2D.Double( w / f, h / f, ( f - 2 ) * w / f, ( f - 2 ) * h / f,  w / f, h / f );
	}
	
	private RoundRectangle2D getSwitchBorderShape( int w, int h ) {
		return new RoundRectangle2D.Double( 0, 0, w, h, w / f, h / f );
	}
	
	private void createSwitchVoid( Graphics2D g, Color bg, int w, int h ) {
		g.setColor( bg );
		RoundRectangle2D sw = getSwitchShape( w, h );
		double m = 1.1d;
		g.fill( new RoundRectangle2D.Double( sw.getCenterX() - sw.getWidth() * m / 2, sw.getCenterY() - sw.getHeight() * m / 2, sw.getWidth() * m, sw.getHeight() * m, sw.getArcWidth(), sw.getArcHeight() ) );
	}
	
	private void createSwitchBorder( Graphics2D g, Color bg, int w, int h ) {
		Shape original = g.getClip();
		Area a = new Area( getSwitchBorderShape( w, h ) );
		a.subtract( new Area( getSwitchShape( w, h ) ) );
		g.clip( a );
		g.setPaint( new GradientPaint( 0, 0, getBrighterColor( bg ), w, h, bg ) );
		g.fill( a );
		g.setClip( original );
	}
	
	private void createSwitch( Graphics2D g, Color bg, Color light, int w, int h, boolean on ) {
		Shape original = g.getClip();
		Shape sw = getSwitchShape( w, h );
		Rectangle2D swb = sw.getBounds2D();
		Rectangle2D up = new Rectangle2D.Double( swb.getX(), swb.getY(), swb.getWidth(), swb.getHeight() / 2 );
		Rectangle2D low = new Rectangle2D.Double( swb.getX(), swb.getY() + swb.getHeight() / 2, swb.getWidth(), swb.getHeight() / 2 );
		Rectangle2D up34 = new Rectangle2D.Double( low.getX(), low.getY(), low.getWidth(), low.getHeight() * 3 / 4 );
		Rectangle2D low4 = new Rectangle2D.Double( low.getX(), low.getY() + low.getHeight() * 3 / 4, low.getWidth(), low.getHeight() / 4 );
		Rectangle2D low34 = new Rectangle2D.Double( up.getX(), up.getY() + ( up.getHeight() / 4 ), up.getWidth(), up.getHeight() * 3 / 4 );
		Rectangle2D up4 = new Rectangle2D.Double( up.getX(), up.getY(), up.getWidth(), up.getHeight() / 4 );
		
		//Flat part
		Area a = new Area( sw );
		if ( on ) {
			a.subtract( new Area( low ) );
		} else {
			a.subtract( new Area( up ) );
		}
		g.clip( a );
		g.setColor( bg );
		g.fill( a );
		g.setClip( original );
		
		
		//Edge part (part sticking out toward you)
		a = new Area( sw );
		if ( on ) {
			a.subtract( new Area( up ) );
			a.subtract( new Area( up34 ) );
		} else {
			a.subtract( new Area( low ) );
			a.subtract( new Area( low34 ) );
		}
		g.clip( a );
		if ( on ) {
			g.setPaint( new LinearGradientPaint( (float)low4.getWidth() / 2, (float)low4.getY(), (float)low4.getWidth() / 2, (float)( low4.getY() + low4.getHeight() ), new float[] { 0, .2f, 1 }, new Color[] { bg, getBrighterColor( bg ), bg } ) );
			g.fill( low4 );
		} else {
			g.setPaint( new LinearGradientPaint( (float)up4.getWidth() / 2, (float)up4.getY(), (float)up4.getWidth() / 2, (float)( up4.getY() + up4.getHeight() ), new float[] { 0, .8f, 1 }, new Color[] { bg, getBrighterColor( bg ), bg } ) );
			g.fill( up4 );
		}
		g.setClip( original );
		
		
		//angled part of the switch
		a = new Area( sw );
		if ( on ) {
			a.subtract( new Area( up ) );
			a.subtract( new Area( low4 ) );
		} else {
			a.subtract( new Area( low ) );
			a.subtract( new Area( up4 ) );
		}
		g.clip( a );
		if ( on ) {
			g.setPaint( new LinearGradientPaint( (float)up34.getWidth() / 2, (float)up34.getY(), (float)up34.getWidth() / 2, (float)( up34.getY() + up34.getHeight() ), new float[] { 0, .2f, 1 }, new Color[] { bg, bg, getBrighterColor( bg ) } ) );
			g.fill( up34 );
		} else {
			g.setPaint( new LinearGradientPaint( (float)low34.getWidth() / 2, (float)low34.getY(), (float)low34.getWidth() / 2, (float)( low34.getY() + low34.getHeight() ), new float[] { 0, .8f, 1 }, new Color[] { getBrighterColor( bg ), bg, bg  } ) );
			g.fill( low34 );
		}
		g.setClip( original );
		
		//middle raised divider
		Rectangle2D mid = new Rectangle2D.Double( swb.getX(), swb.getCenterY() - swb.getHeight() / div, swb.getWidth(), 2d * swb.getHeight() / div );
		g.setPaint( new LinearGradientPaint( (float)mid.getWidth() / 2, (float)mid.getY(), (float)mid.getWidth() / 2, (float)( mid.getY() + mid.getHeight() ), new float[] { 0, .5f, 1 }, new Color[] { getBrighterColor( bg ), bg, getBrighterColor( bg ) } ) );
		g.fill( mid );
		
		//nubs
		Rectangle2D upn;
		if ( on ) {
			upn = new Rectangle2D.Double( swb.getX() + swb.getWidth() * 3d / f , up4.getCenterY() - swb.getHeight() / div, 2d * swb.getWidth() / f, 2d * swb.getHeight() / div );
		} else {
			upn = new Rectangle2D.Double( swb.getX() + swb.getWidth() * 3d / f , low34.getY() + ( low34.getHeight() / f ) - swb.getHeight() / div, 2d * swb.getWidth() / f, 2d * swb.getHeight() / div );
		}
		g.setPaint( new LinearGradientPaint( (float)upn.getWidth() / 2, (float)upn.getY(), (float)upn.getWidth() / 2, (float)( upn.getY() + upn.getHeight() ), new float[] { 0, .5f, 1 }, new Color[] { getBrighterColor( bg ), bg, getBrighterColor( bg ) } ) );
		g.fill( upn );
		
		Rectangle2D lown;
		if ( on ) {
			lown = new Rectangle2D.Double( swb.getX() + swb.getWidth() * 3d / f , up34.getHeight() + up34.getY() - ( up34.getHeight() / f ) - swb.getHeight() / div, 2d * swb.getWidth() / f, 2d * swb.getHeight() / div );
		} else {
			lown = new Rectangle2D.Double( swb.getX() + swb.getWidth() * 3d / f , low4.getCenterY() - swb.getHeight() / div, 2d * swb.getWidth() / f, 2d * swb.getHeight() / div );
		}
		g.setPaint( new LinearGradientPaint( (float)lown.getWidth() / 2, (float)lown.getY(), (float)lown.getWidth() / 2, (float)( lown.getY() + lown.getHeight() ), new float[] { 0, .5f, 1 }, new Color[] { getBrighterColor( bg ), bg, getBrighterColor( bg ) } ) );
		g.fill( lown );
		
		//Light
		Rectangle2D window;
		if ( on ) {
			//
			window = new Rectangle2D.Double( up.getCenterX() - up.getWidth() / f * 2d, up.getCenterY() - up.getHeight() / f, up.getWidth() / f * 4d, up.getHeight() / f );
			g.setPaint( new LinearGradientPaint( (float)window.getWidth() / 2, (float)window.getY(), (float)window.getWidth() / 2, (float)( window.getY() + window.getHeight() ), 
					new float[] { 0, .5f, 1 }, new Color[] { Color.DARK_GRAY, Color.GRAY, Color.LIGHT_GRAY }, MultipleGradientPaint.CycleMethod.REFLECT ) );
			g.fill( window );
			Composite c = g.getComposite();
			g.setComposite( AlphaComposite.getInstance( AlphaComposite.SRC_ATOP, .5f ) );
			g.setColor( light );
			g.fill( window );
			
			//faint white circle for LED center effect... sort of
			g.setComposite( AlphaComposite.getInstance( AlphaComposite.SRC_ATOP, .05f ) );
			double r = window.getHeight() / 3 * 2;
			Ellipse2D led = new Ellipse2D.Double( window.getCenterX() - r / 2, window.getCenterY() - r / 2, r, r );
			g.setColor( Color.WHITE );
			g.fill( led );
			g.setColor( light );
			g.fill( led );
			
			//multiple slightly increasing rectangles drawn over top of each other to create a dithering glow effect that fades toward the edges
			g.setComposite( AlphaComposite.getInstance( AlphaComposite.SRC_ATOP, .025f ) );
			double mult = .5d;
			for ( int i = 0; i < 16; i++ ) {
				Rectangle2D wglow = new Rectangle2D.Double( window.getCenterX() - window.getWidth() * mult / 2, window.getCenterY() - window.getHeight() * mult / 2, window.getWidth() * mult, window.getHeight() * mult );
				g.setColor( light );
				g.fill( wglow );
				mult += .125d;
			}
			
			g.setComposite( c );  //reset the composite to the original... whatever that is
		} else {
			window = new Rectangle2D.Double( low34.getCenterX() - low34.getWidth() / f * 2d, low34.getCenterY() - low34.getHeight() / f / 2, low34.getWidth() / f * 4d, low34.getHeight() / f * 3 / 4 );
			g.setPaint( new LinearGradientPaint( (float)window.getWidth() / 2, (float)window.getY(), (float)window.getWidth() / 2, (float)( window.getY() + window.getHeight() ), new float[] { 0, .5f, 1 }, new Color[] { Color.DARK_GRAY, Color.GRAY, Color.LIGHT_GRAY }, MultipleGradientPaint.CycleMethod.REFLECT ) );
			g.fill( window );
		}
	}
	
	private BufferedImage createIconImage( int w, int h ) {
		return new BufferedImage( w, h, BufferedImage.TYPE_INT_ARGB );
	}
	
	private Color getBrighterColor( Color bg ) {
		Color ret = bg.brighter().brighter();
		if ( bg.equals( Color.BLACK ) ) {
			for ( int i = 0; i < 5; i++ ) {
				ret = ret.brighter();
			}
		} else if ( bg.equals( Color.WHITE ) ) {
			ret = ret.darker();
		}
		return ret;
	}
}