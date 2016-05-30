package icon.creator;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;



import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * This is far from perfect... but for a basic lit/unlit text image pair it works to some degree
 * 
 * Spacing is a bit off and could use further work.  Works best with all caps, and fonts greater than 20pt
 * 
 * Only tested on regular characters - probably will not work on superscript stuff, may work on subscript
 * 
 * 
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Nov 5, 2015, 5:32:54 PM 
 */
public class TextIconCreator {
	
	private static final float sf = 2f;
	
	public Icon[] getIcons( String text, Font font, Color light, Color bg ) {
		return new Icon[] { getOff( text, font, light, bg ), getOn( text, font, light, bg ) };
	}
	
	public Icon getOn( String text, Font font, Color light, Color bg ) {
		BufferedImage i = createIconImage( 1, 1 );
		Graphics2D g = i.createGraphics();
		i = drawText( g, text, font, light, bg, true );
		return new ImageIcon( i );
	}
	
	public Icon getOff( String text, Font font, Color light, Color bg ) {
		BufferedImage i = createIconImage( 1, 1 );
		Graphics2D g = i.createGraphics();
		i = drawText( g, text, font, light, bg, false );
		return new ImageIcon( i );
	}
	
	private BufferedImage drawText( Graphics2D g, String text, Font font, Color light, Color bg, boolean on ) {
				
		int iterations = font.getSize() / 6;
		
		Font f2 = font.deriveFont( font.getSize() + (float)( sf * iterations ) );
		
		int fh = g.getFontMetrics( f2 ).getHeight();
		List<BufferedImage> images = new ArrayList<>();
		
		//draw an image for each character
		for ( int i = 0; i < text.length(); i++ ) {
			char c = text.charAt( i );
			int fw = g.getFontMetrics( f2 ).charWidth( c );
			String s = String.valueOf( c );
			BufferedImage j = createIconImage( fw, fh );
			images.add( j );
			drawChar( j.createGraphics(), s, font, light, bg, fw, fh, on );
		}
		
		//concatenate each individual character image together
		int tw = 0;
		for ( BufferedImage i : images ) {
			tw += i.getWidth();
		}
		BufferedImage result = createIconImage( tw, fh );
		Graphics2D g2 = result.createGraphics();
		int offset = 0;
		for ( BufferedImage i : images ) {
			g2.drawImage( i, offset, 0, null );
			offset += i.getWidth();
		}
		return result;
	}
	
	private void drawChar( Graphics2D g, String text, Font font, Color light, Color bg, int w, int h, boolean on ) {
		Composite c = g.getComposite();
		
		int iterations = font.getSize() / 6;
		Rectangle2D area = new Rectangle2D.Double( 0, 0, w, h );
		g.setColor( bg );
		g.fill( area );
		
		g.setFont( font );
		TextLayout tl = new TextLayout( text, font, g.getFontRenderContext() );
		float ha = h / 4;
		g.setColor( Color.BLACK );
		tl.draw( g, w / 2 - tl.getVisibleAdvance() / 2, getHeight( h, ha, tl ) );
		
		g.setComposite( AlphaComposite.getInstance( AlphaComposite.SRC_ATOP, on ? .5f : .35f ) );
		g.setColor( light );
		tl.draw( g, w / 2 - tl.getVisibleAdvance() / 2, getHeight( h, ha, tl ) );
		
		if ( on ) {
			//draw smaller white to back the glow
			tl = new TextLayout( text, font.deriveFont( font.getSize() - sf ), g.getFontRenderContext() );
			g.setColor( Color.WHITE );
			tl.draw( g, w / 2 - tl.getVisibleAdvance() / 2, getHeight( h, ha, tl ) );
	
			
			g.setColor( light );
			tl.draw( g, w / 2 - tl.getVisibleAdvance() / 2, getHeight( h, ha, tl ) );
					g.setComposite( AlphaComposite.getInstance( AlphaComposite.SRC_ATOP, .05f ) );
			for ( int i = 0; i < iterations; i++ ) {
				tl = new TextLayout( text, font.deriveFont( font.getSize() + (float)(sf * i) ), g.getFontRenderContext() );
				tl.draw( g, w / 2 - tl.getVisibleAdvance() / 2, getHeight( h, ha, tl ) );
			}
		}
		
		g.setComposite( c );
	}
	
	private float getHeight( float h, float ha, TextLayout tl ) {
		float th = tl.getBaseline() + tl.getAscent() + tl.getDescent();
		float hl = th / 4;
		if ( ha != 0 ) {
			hl -= ( hl - ha ) * 2;
		}
		return h - hl;
	}

	private BufferedImage createIconImage( int w, int h ) {
		return new BufferedImage( w, h, BufferedImage.TYPE_INT_ARGB );
	}
}